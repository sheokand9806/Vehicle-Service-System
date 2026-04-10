package com.vehicleservice.repository;

import com.vehicleservice.entity.Service;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ServiceRepository {
    private final Map<Long, Service> services = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<Service> findAll() {
        return new ArrayList<>(services.values());
    }

    public Optional<Service> findById(Long id) {
        return Optional.ofNullable(services.get(id));
    }

    public Service save(Service service) {
        if (service.getId() == null) {
            service.setId(Long.valueOf(idGenerator.getAndIncrement()));
        }
        services.put(service.getId(), service);
        return service;
    }

    public void deleteById(Long id) {
        services.remove(id);
    }

    public long count() {
        return services.size();
    }
}
