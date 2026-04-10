package com.vehicleservice.controller;

import com.vehicleservice.service.ServiceEstimatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/estimator")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class ServiceEstimatorController {

    @Autowired
    private ServiceEstimatorService serviceEstimatorService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> estimate(@RequestBody Map<String, Object> request) {
        List<String> symptoms = (List<String>) request.get("symptoms");
        double cost = serviceEstimatorService.estimateCost(symptoms);
        Map<String, Object> response = Map.of("estimatedCost", cost);
        return ResponseEntity.ok(response);
    }
}