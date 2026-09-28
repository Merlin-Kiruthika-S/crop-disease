package com.example.crop_disease.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.crop_disease.entity.Region;

public interface RegionRepository extends JpaRepository<Region, Long> {
}