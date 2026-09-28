package com.example.crop_disease.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.crop_disease.entity.Ticket;
import com.example.crop_disease.entity.TicketStatus;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByStatusNotAndCreatedAtBefore(
            TicketStatus status,
            LocalDateTime time
    );
}