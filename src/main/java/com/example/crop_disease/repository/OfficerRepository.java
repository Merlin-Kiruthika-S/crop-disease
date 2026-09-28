package com.example.crop_disease.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.crop_disease.entity.Officer;

public interface OfficerRepository extends JpaRepository<Officer, Long> {
}