package com.cloudbees.tms.controllers;

import com.cloudbees.tms.dto.TicketDto;
import com.cloudbees.tms.entity.Ticket;
import com.cloudbees.tms.exception.TicketNotFoundException;
import com.cloudbees.tms.service.TicketService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @GetMapping("/{pnr}")
    public Ticket getTicket(@PathVariable @NonNull String pnr) {

        Ticket ticket = ticketService.getTicketByPnr(pnr);
        if (Objects.isNull(ticket)) {
            throw new TicketNotFoundException("ticket not found with pnr: " + pnr);
        }
        return ticket;
    }

    @DeleteMapping("/{pnr}")
    public void deleteTicket(@PathVariable @NonNull String pnr) {

        ticketService.deleteTicketByPnr(pnr);
    }


    @PatchMapping("/{pnr}/seat/{newSeatNumber}")
    public Ticket updateSeat(@PathVariable String pnr, @PathVariable String newSeatNumber) {

        Ticket ticket = ticketService.getTicketByPnr(pnr);
        if (Objects.isNull(ticket)) {
            throw new TicketNotFoundException("ticket not found with pnr: " + pnr);
        }
        ticketService.UpdateSeat(ticket, newSeatNumber);
        return ticket;
    }


}