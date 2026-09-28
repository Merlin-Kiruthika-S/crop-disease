package com.example.crop_disease.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.crop_disease.entity.Officer;
import com.example.crop_disease.service.OfficerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/officers")
public class OfficerController {

    private final OfficerService officerService;

    public OfficerController(OfficerService officerService) {
        this.officerService = officerService;
    }

    @PostMapping
    public Officer createOfficer(@Valid @RequestBody Officer officer) {
        return officerService.createOfficer(officer);
    }

    @GetMapping
    public List<Officer> getAllOfficers() {
        return officerService.getAllOfficers();
    }

    @GetMapping("/{id}")
    public Officer getOfficerById(@PathVariable Long id) {
        return officerService.getOfficerById(id);
    }
}