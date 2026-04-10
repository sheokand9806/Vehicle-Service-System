package com.vehicleservice.repository;

import com.vehicleservice.entity.Vehicle;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class VehicleRepository {
    private final Map<Long, Vehicle> vehicles = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<Vehicle> findByUserId(Long userId) {
        List<Vehicle> result = new ArrayList<>();
        for (Vehicle vehicle : vehicles.values()) {
            if (vehicle.getUser() != null && userId.equals(vehicle.getUser().getId())) {
                result.add(vehicle);
            }
        }
        return result;
    }

    public boolean existsByLicensePlate(String licensePlate) {
        return vehicles.values().stream()
                .anyMatch(vehicle -> licensePlate.equals(vehicle.getLicensePlate()));
    }

    public Optional<Vehicle> findById(Long id) {
        return Optional.ofNullable(vehicles.get(id));
    }

    public Vehicle save(Vehicle vehicle) {
        if (vehicle.getId() == null) {
            vehicle.setId(Long.valueOf(idGenerator.getAndIncrement()));
        }
        vehicles.put(vehicle.getId(), vehicle);
        return vehicle;
    }

    public void deleteById(Long id) {
        vehicles.remove(id);
    }

    public List<Vehicle> findAll() {
        return new ArrayList<>(vehicles.values());
    }
}
