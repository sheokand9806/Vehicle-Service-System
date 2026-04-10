package com.vehicleservice.controller;

import com.vehicleservice.entity.Booking;
import com.vehicleservice.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bookings")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<String> createBooking(@RequestBody Map<String, Object> request) {
        Long serviceId = Long.valueOf(request.get("serviceId").toString());
        Long vehicleId = Long.valueOf(request.get("vehicleId").toString());
        String date = request.get("date").toString();
        String time = request.get("time").toString();

        bookingService.createBooking(serviceId, vehicleId, date, time);
        return ResponseEntity.ok("Booking created successfully");
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String status = request.get("status");
        bookingService.updateBookingStatus(id, status);
        return ResponseEntity.ok("Status updated successfully");
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getBookings(Authentication authentication) {
        String username = authentication.getName();
        List<Booking> bookings = bookingService.getBookingsByUser(username);
        return ResponseEntity.ok(bookings);
    }
}