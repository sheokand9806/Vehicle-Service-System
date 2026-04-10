package com.vehicleservice.repository;

import com.vehicleservice.entity.Booking;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class BookingRepository {
    private final Map<Long, Booking> bookings = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<Booking> findByUserId(Long userId) {
        List<Booking> result = new ArrayList<>();
        for (Booking booking : bookings.values()) {
            if (booking.getUser() != null && userId.equals(booking.getUser().getId())) {
                result.add(booking);
            }
        }
        return result;
    }

    public Optional<Booking> findById(Long id) {
        return Optional.ofNullable(bookings.get(id));
    }

    public Booking save(Booking booking) {
        if (booking.getId() == null) {
            booking.setId(Long.valueOf(idGenerator.getAndIncrement()));
        }
        bookings.put(booking.getId(), booking);
        return booking;
    }

    public List<Booking> findAll() {
        return new ArrayList<>(bookings.values());
    }

    public void deleteById(Long id) {
        bookings.remove(id);
    }
}
