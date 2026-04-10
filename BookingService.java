package com.vehicleservice.service;

import com.vehicleservice.entity.Booking;
import com.vehicleservice.entity.User;
import com.vehicleservice.entity.Vehicle;
import com.vehicleservice.repository.BookingRepository;
import com.vehicleservice.repository.ServiceRepository;
import com.vehicleservice.repository.UserRepository;
import com.vehicleservice.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    public void createBooking(Long serviceId, Long vehicleId, String date, String time) {
        com.vehicleservice.entity.Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        LocalDate localDate = LocalDate.parse(date);
        LocalTime localTime = LocalTime.parse(time);
        LocalDateTime bookingDateTime = LocalDateTime.of(localDate, localTime);

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setService(service);
        booking.setVehicle(vehicle);
        booking.setBookingDate(bookingDateTime);
        booking.setStatus("PENDING");
        booking.setCreatedAt(LocalDateTime.now());

        bookingRepository.save(booking);
    }

    public void updateBookingStatus(Long bookingId, String status) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus(status);
        bookingRepository.save(booking);
    }

    public List<Booking> getBookingsByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return bookingRepository.findByUserId(user.getId());
    }
}