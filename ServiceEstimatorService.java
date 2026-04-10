package com.vehicleservice.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServiceEstimatorService {

    private static final Map<String, Double> symptomCosts = new HashMap<>();

    static {
        symptomCosts.put("squeaky brakes", 1500.0);
        symptomCosts.put("engine overheating", 3000.0);
        symptomCosts.put("battery not starting", 2000.0);
        symptomCosts.put("flat tire", 500.0);
        symptomCosts.put("oil leak", 2500.0);
        symptomCosts.put("strange noise", 1000.0);
    }

    public double estimateCost(List<String> symptoms) {
        return symptoms.stream()
                .mapToDouble(symptom -> symptomCosts.getOrDefault(symptom.toLowerCase(), 500.0))
                .sum();
    }
}