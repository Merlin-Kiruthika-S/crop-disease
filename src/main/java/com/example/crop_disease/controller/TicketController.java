package com.example.crop_disease.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.crop_disease.entity.Ticket;
import com.example.crop_disease.service.TicketService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public Ticket createTicket(@Valid @RequestBody Ticket ticket) {
        return ticketService.createTicket(ticket);
    }

    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @GetMapping("/{id}")
    public Ticket getTicketById(@PathVariable Long id) {
        return ticketService.getTicketById(id);
    }

    @PutMapping("/{ticketId}/close")
    public Ticket closeTicket(
            @PathVariable Long ticketId,
            @RequestParam Long officerId,
            @RequestParam String recommendation) {

        return ticketService.closeTicket(
                ticketId,
                officerId,
                recommendation
        );
    }

    @PutMapping("/{ticketId}/reopen")
    public Ticket reopenTicket(@PathVariable Long ticketId) {
        return ticketService.reopenTicket(ticketId);
    }

    @GetMapping("/escalated")
    public List<Ticket> getEscalatedTickets() {
        return ticketService.getEscalatedTickets();
    }
}