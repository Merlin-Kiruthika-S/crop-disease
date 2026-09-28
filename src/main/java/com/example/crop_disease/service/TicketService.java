package com.example.crop_disease.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.crop_disease.entity.Farmer;
import com.example.crop_disease.entity.Officer;
import com.example.crop_disease.entity.Ticket;
import com.example.crop_disease.entity.TicketStatus;
import com.example.crop_disease.repository.FarmerRepository;
import com.example.crop_disease.repository.OfficerRepository;
import com.example.crop_disease.repository.TicketRepository;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final FarmerRepository farmerRepository;
    private final OfficerRepository officerRepository;

    public TicketService(TicketRepository ticketRepository,
                         FarmerRepository farmerRepository,
                         OfficerRepository officerRepository) {
        this.ticketRepository = ticketRepository;
        this.farmerRepository = farmerRepository;
        this.officerRepository = officerRepository;
    }

    public Ticket createTicket(Ticket ticket) {

        Farmer farmer = farmerRepository.findById(ticket.getFarmer().getId())
                .orElseThrow(() -> new RuntimeException("Farmer not found"));

        ticket.setFarmer(farmer);

        // Automatically assign an officer from the farmer's region
        Officer officer = officerRepository.findAll()
                .stream()
                .filter(o -> o.getRegion().getId().equals(farmer.getRegion().getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        "No officer available for this region"));

        ticket.setOfficer(officer);

        ticket.setStatus(TicketStatus.OPEN);
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setUpdatedAt(LocalDateTime.now());

        return ticketRepository.save(ticket);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id).orElse(null);
    }

    public Ticket closeTicket(Long ticketId, Long officerId, String recommendation) {

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        // Only the assigned officer can close the ticket
        if (!ticket.getOfficer().getId().equals(officerId)) {
            throw new RuntimeException(
                    "Only the assigned officer can close this ticket");
        }

        ticket.setRecommendation(recommendation);
        ticket.setStatus(TicketStatus.CLOSED);
        ticket.setClosedAt(LocalDateTime.now());
        ticket.setUpdatedAt(LocalDateTime.now());

        return ticketRepository.save(ticket);
    }

    public Ticket reopenTicket(Long ticketId) {

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        // Only a closed ticket can be reopened
        if (ticket.getStatus() != TicketStatus.CLOSED) {
            throw new RuntimeException(
                    "Only a closed ticket can be reopened");
        }

        ticket.setStatus(TicketStatus.REOPENED);
        ticket.setClosedAt(null);
        ticket.setUpdatedAt(LocalDateTime.now());

        return ticketRepository.save(ticket);
    }

    public List<Ticket> getEscalatedTickets() {

        LocalDateTime cutoffTime = LocalDateTime.now().minusHours(48);

        List<Ticket> tickets = ticketRepository
                .findByStatusNotAndCreatedAtBefore(
                        TicketStatus.CLOSED,
                        cutoffTime
                );

        for (Ticket ticket : tickets) {
            ticket.setStatus(TicketStatus.ESCALATED);
            ticket.setUpdatedAt(LocalDateTime.now());
        }

        return ticketRepository.saveAll(tickets);
    }
}