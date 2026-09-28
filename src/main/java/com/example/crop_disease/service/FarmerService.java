package com.example.crop_disease.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.crop_disease.entity.Farmer;
import com.example.crop_disease.entity.Region;
import com.example.crop_disease.repository.FarmerRepository;
import com.example.crop_disease.repository.RegionRepository;

@Service
public class FarmerService {

    private final FarmerRepository farmerRepository;
    private final RegionRepository regionRepository;

    public FarmerService(FarmerRepository farmerRepository,
                         RegionRepository regionRepository) {
        this.farmerRepository = farmerRepository;
        this.regionRepository = regionRepository;
    }

    public Farmer createFarmer(Farmer farmer) {
        if (farmer.getRegion() != null && farmer.getRegion().getId() != null) {
            Region region = regionRepository.findById(farmer.getRegion().getId())
                    .orElseThrow(() -> new RuntimeException("Region not found"));

            farmer.setRegion(region);
        }

        return farmerRepository.save(farmer);
    }

    public List<Farmer> getAllFarmers() {
        return farmerRepository.findAll();
    }

    public Farmer getFarmerById(Long id) {
        return farmerRepository.findById(id).orElse(null);
    }
}