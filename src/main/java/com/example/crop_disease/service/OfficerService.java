package com.example.crop_disease.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.crop_disease.entity.Officer;
import com.example.crop_disease.entity.Region;
import com.example.crop_disease.repository.OfficerRepository;
import com.example.crop_disease.repository.RegionRepository;

@Service
public class OfficerService {

    private final OfficerRepository officerRepository;
    private final RegionRepository regionRepository;

    public OfficerService(OfficerRepository officerRepository,
                          RegionRepository regionRepository) {
        this.officerRepository = officerRepository;
        this.regionRepository = regionRepository;
    }

    public Officer createOfficer(Officer officer) {
        if (officer.getRegion() != null && officer.getRegion().getId() != null) {
            Region region = regionRepository.findById(officer.getRegion().getId())
                    .orElseThrow(() -> new RuntimeException("Region not found"));

            officer.setRegion(region);
        }

        return officerRepository.save(officer);
    }

    public List<Officer> getAllOfficers() {
        return officerRepository.findAll();
    }

    public Officer getOfficerById(Long id) {
        return officerRepository.findById(id).orElse(null);
    }
}