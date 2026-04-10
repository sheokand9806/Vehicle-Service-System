package com.vehicleservice.service;

import com.vehicleservice.dto.ServiceDTO;
import com.vehicleservice.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    public List<ServiceDTO> getAllServices() {
        return serviceRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ServiceDTO getServiceById(Long id) {
        com.vehicleservice.entity.Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found"));
        return convertToDTO(service);
    }

    public ServiceDTO createService(ServiceDTO serviceDTO) {
        com.vehicleservice.entity.Service service = new com.vehicleservice.entity.Service();
        service.setName(serviceDTO.getName());
        service.setDescription(serviceDTO.getDescription());
        service.setPrice(serviceDTO.getPrice());
        service.setDurationMinutes(serviceDTO.getDurationMinutes());

        com.vehicleservice.entity.Service savedService = serviceRepository.save(service);
        return convertToDTO(savedService);
    }

    private ServiceDTO convertToDTO(com.vehicleservice.entity.Service service) {
        return new ServiceDTO(
                service.getId(),
                service.getName(),
                service.getDescription(),
                service.getPrice(),
                service.getDurationMinutes()
        );
    }
}