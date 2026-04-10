package com.vehicleservice.repository;

import com.vehicleservice.entity.Technician;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TechnicianRepository {
    private final Map<Long, Technician> technicians = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<Technician> findByExpertiseAndAvailable(String expertise, boolean available) {
        List<Technician> result = new ArrayList<>();
        for (Technician technician : technicians.values()) {
            if (technician.getExpertise().equals(expertise) && technician.isAvailable() == available) {
                result.add(technician);
            }
        }
        return result;
    }

    public Optional<Technician> findById(Long id) {
        return Optional.ofNullable(technicians.get(id));
    }

    public Technician save(Technician technician) {
        if (technician.getId() == null) {
            technician.setId(Long.valueOf(idGenerator.getAndIncrement()));
        }
        technicians.put(technician.getId(), technician);
        return technician;
    }

    public List<Technician> findAll() {
        return new ArrayList<>(technicians.values());
    }

    public void deleteById(Long id) {
        technicians.remove(id);
    }
}
