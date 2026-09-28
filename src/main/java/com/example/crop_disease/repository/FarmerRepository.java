package com.example.crop_disease.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.crop_disease.entity.Farmer;

public interface FarmerRepository extends JpaRepository<Farmer, Long> {
}