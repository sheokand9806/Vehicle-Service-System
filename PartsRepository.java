package com.vehicleservice.repository;

import com.vehicleservice.entity.Parts;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PartsRepository {
    private final Map<Long, Parts> parts = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Optional<Parts> findById(Long id) {
        return Optional.ofNullable(parts.get(id));
    }

    public Parts save(Parts part) {
        if (part.getId() == null) {
            part.setId(Long.valueOf(idGenerator.getAndIncrement()));
        }
        parts.put(part.getId(), part);
        return part;
    }

    public List<Parts> findAll() {
        return new ArrayList<>(parts.values());
    }

    public void deleteById(Long id) {
        parts.remove(id);
    }
}
