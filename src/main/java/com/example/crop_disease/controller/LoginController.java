package com.example.crop_disease.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.crop_disease.entity.Farmer;
import com.example.crop_disease.entity.Officer;
import com.example.crop_disease.entity.Region;
import com.example.crop_disease.repository.FarmerRepository;
import com.example.crop_disease.repository.OfficerRepository;
import com.example.crop_disease.repository.RegionRepository;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private final FarmerRepository farmerRepository;
    private final OfficerRepository officerRepository;
    private final RegionRepository regionRepository;

    public LoginController(
            FarmerRepository farmerRepository,
            OfficerRepository officerRepository,
            RegionRepository regionRepository
    ) {
        this.farmerRepository = farmerRepository;
        this.officerRepository = officerRepository;
        this.regionRepository = regionRepository;
    }


    // =====================================================
    // FARMER REGISTER
    // =====================================================

    @PostMapping("/farmer/register")
    public ResponseEntity<?> farmerRegister(
            @RequestBody Farmer farmer
    ) {

        if (farmer.getName() == null ||
                farmer.getName().isBlank()) {

            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "error",
                            "Full name is required"
                    ));
        }


        if (farmer.getPhone() == null ||
                farmer.getPhone().isBlank()) {

            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "error",
                            "Phone number is required"
                    ));
        }


        if (farmer.getEmail() == null ||
                farmer.getEmail().isBlank()) {

            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "error",
                            "Email is required"
                    ));
        }


        if (farmer.getUsername() == null ||
                farmer.getUsername().isBlank()) {

            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "error",
                            "Username is required"
                    ));
        }


        if (farmer.getPassword() == null ||
                farmer.getPassword().isBlank()) {

            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "error",
                            "Password is required"
                    ));
        }


        if (farmerRepository
                .findAll()
                .stream()
                .anyMatch(existing ->
                        existing.getUsername() != null &&
                        existing.getUsername()
                                .equalsIgnoreCase(
                                        farmer.getUsername()
                                )
                )) {

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of(
                            "error",
                            "Username already exists"
                    ));
        }


        if (farmer.getRegion() == null ||
                farmer.getRegion().getId() == null) {

            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "error",
                            "Region is required"
                    ));
        }


        Region region =
                regionRepository
                        .findById(
                                farmer.getRegion().getId()
                        )
                        .orElse(null);


        if (region == null) {

            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "error",
                            "Selected region not found"
                    ));
        }


        farmer.setRegion(region);


        Farmer savedFarmer =
                farmerRepository.save(farmer);


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        Map.of(
                                "message",
                                "Farmer account created successfully",

                                "id",
                                savedFarmer.getId(),

                                "name",
                                savedFarmer.getName(),

                                "username",
                                savedFarmer.getUsername(),

                                "role",
                                "FARMER"
                        )
                );
    }


    // =====================================================
    // OFFICER REGISTER
    // =====================================================

    @PostMapping("/officer/register")
    public ResponseEntity<?> officerRegister(
            @RequestBody Officer officer
    ) {

        if (officer.getName() == null ||
                officer.getName().isBlank()) {

            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "error",
                            "Full name is required"
                    ));
        }


        if (officer.getPhone() == null ||
                officer.getPhone().isBlank()) {

            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "error",
                            "Phone number is required"
                    ));
        }


        if (officer.getEmail() == null ||
                officer.getEmail().isBlank()) {

            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "error",
                            "Email is required"
                    ));
        }


        if (officer.getUsername() == null ||
                officer.getUsername().isBlank()) {

            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "error",
                            "Username is required"
                    ));
        }


        if (officer.getPassword() == null ||
                officer.getPassword().isBlank()) {

            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "error",
                            "Password is required"
                    ));
        }


        if (officerRepository
                .findAll()
                .stream()
                .anyMatch(existing ->
                        existing.getUsername() != null &&
                        existing.getUsername()
                                .equalsIgnoreCase(
                                        officer.getUsername()
                                )
                )) {

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of(
                            "error",
                            "Username already exists"
                    ));
        }


        if (officer.getRegion() == null ||
                officer.getRegion().getId() == null) {

            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "error",
                            "Region is required"
                    ));
        }


        Region region =
                regionRepository
                        .findById(
                                officer.getRegion().getId()
                        )
                        .orElse(null);


        if (region == null) {

            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "error",
                            "Selected region not found"
                    ));
        }


        officer.setRegion(region);


        Officer savedOfficer =
                officerRepository.save(officer);


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        Map.of(
                                "message",
                                "Officer account created successfully",

                                "id",
                                savedOfficer.getId(),

                                "name",
                                savedOfficer.getName(),

                                "username",
                                savedOfficer.getUsername(),

                                "role",
                                "OFFICER"
                        )
                );
    }


    // =====================================================
    // FARMER LOGIN
    // =====================================================

    @PostMapping("/farmer/login")
    public ResponseEntity<?> farmerLogin(
            @RequestBody Map<String, String> loginData
    ) {

        String username =
                loginData.get("username");

        String password =
                loginData.get("password");


        if (username == null ||
                password == null) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            Map.of(
                                    "error",
                                    "Username and password are required"
                            )
                    );
        }


        Farmer farmer =
                farmerRepository
                        .findAll()
                        .stream()
                        .filter(f ->
                                f.getUsername() != null &&
                                f.getPassword() != null &&
                                f.getUsername()
                                        .equals(username) &&
                                f.getPassword()
                                        .equals(password)
                        )
                        .findFirst()
                        .orElse(null);


        if (farmer == null) {

            return ResponseEntity
                    .status(
                            HttpStatus.UNAUTHORIZED
                    )
                    .body(
                            Map.of(
                                    "error",
                                    "Invalid farmer username or password"
                            )
                    );
        }


        return ResponseEntity.ok(
                Map.of(
                        "message",
                        "Farmer login successful",

                        "role",
                        "FARMER",

                        "id",
                        farmer.getId(),

                        "name",
                        farmer.getName(),

                        "username",
                        farmer.getUsername()
                )
        );
    }


    // =====================================================
    // OFFICER LOGIN
    // =====================================================

    @PostMapping("/officer/login")
    public ResponseEntity<?> officerLogin(
            @RequestBody Map<String, String> loginData
    ) {

        String username =
                loginData.get("username");

        String password =
                loginData.get("password");


        if (username == null ||
                password == null) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            Map.of(
                                    "error",
                                    "Username and password are required"
                            )
                    );
        }


        Officer officer =
                officerRepository
                        .findAll()
                        .stream()
                        .filter(o ->
                                o.getUsername() != null &&
                                o.getPassword() != null &&
                                o.getUsername()
                                        .equals(username) &&
                                o.getPassword()
                                        .equals(password)
                        )
                        .findFirst()
                        .orElse(null);


        if (officer == null) {

            return ResponseEntity
                    .status(
                            HttpStatus.UNAUTHORIZED
                    )
                    .body(
                            Map.of(
                                    "error",
                                    "Invalid officer username or password"
                            )
                    );
        }


        return ResponseEntity.ok(
                Map.of(
                        "message",
                        "Officer login successful",

                        "role",
                        "OFFICER",

                        "id",
                        officer.getId(),

                        "name",
                        officer.getName(),

                        "username",
                        officer.getUsername()
                )
        );
    }
}