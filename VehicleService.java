package com.vehicleservice.service;

import com.vehicleservice.entity.User;
import com.vehicleservice.entity.Vehicle;
import com.vehicleservice.repository.UserRepository;
import com.vehicleservice.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private UserRepository userRepository;

    public Vehicle addVehicle(Long userId, Vehicle vehicle) {
        // Validate input
        if (vehicle.getYear() <= 0 || vehicle.getYear() > 2100) {
            throw new RuntimeException("Year must be between 1 and 2100");
        }
        if (vehicle.getLicensePlate() == null || vehicle.getLicensePlate().trim().isEmpty()) {
            throw new RuntimeException("License plate is required");
        }
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        vehicle.setUser(user);
        
        if (vehicleRepository.existsByLicensePlate(vehicle.getLicensePlate())) {
            throw new RuntimeException("License plate already exists");
        }
        return vehicleRepository.save(vehicle);
    }

    public List<Vehicle> getVehiclesByUser(Long userId) {
        return vehicleRepository.findByUserId(userId);
    }

    public Vehicle updateVehicle(Long vehicleId, Vehicle updatedVehicle) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        vehicle.setYear(updatedVehicle.getYear());
        vehicle.setLicensePlate(updatedVehicle.getLicensePlate());
        return vehicleRepository.save(vehicle);
    }

    public void deleteVehicle(Long vehicleId) {
        vehicleRepository.deleteById(vehicleId);
    }
}