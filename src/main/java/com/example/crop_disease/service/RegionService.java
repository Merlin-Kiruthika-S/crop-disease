package com.example.crop_disease.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.crop_disease.entity.Region;
import com.example.crop_disease.repository.RegionRepository;

@Service
public class RegionService {

    private final RegionRepository regionRepository;

    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public Region createRegion(Region region) {
        return regionRepository.save(region);
    }

    public List<Region> getAllRegions() {
        return regionRepository.findAll();
    }

    public Region getRegionById(Long id) {
        return regionRepository.findById(id).orElse(null);
    }
}