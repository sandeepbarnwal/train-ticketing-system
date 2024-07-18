package com.cloudbees.tms.service;


import com.cloudbees.tms.entity.Seat;
import com.cloudbees.tms.entity.Ticket;
import com.cloudbees.tms.exception.SeatNotAvailableException;
import com.cloudbees.tms.exception.TicketNotFoundException;
import com.cloudbees.tms.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TicketService {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    private SeatService seatService;

    public Ticket getTicketByPnr(String pnr) {
        return ticketRepository.findByPnr(pnr);
    }

    public void deleteTicketByPnr(String pnr) {
        Ticket ticket =  getTicketByPnr(pnr);
        if (Objects.isNull(ticket)) {
            throw new TicketNotFoundException("no ticket found with pnr: " + pnr );
        }
         ticketRepository.delete(ticket);
    }

    public Ticket getTicketBySeatNumber(String seatNumber) {
        return ticketRepository.findBySeatNumber(seatNumber);
    }

    public void UpdateSeat(Ticket ticket, String newSeatNumber) {

        Seat oldSeat = seatService.findSeatByNumber(ticket.getSeatNumber());
        Seat newSeat   = seatService.findSeatByNumber(newSeatNumber);

        if (Objects.isNull(newSeat)) {
            throw new SeatNotAvailableException("Seat not found with seat number: " + newSeatNumber);
        } else if (! newSeat.getAvailable()) {
            throw new SeatNotAvailableException("Seat not available for modification: " + newSeatNumber);
        }

        newSeat.setAvailable(false);
        seatService.save(newSeat);

        oldSeat.setAvailable(true);
        seatService.save(oldSeat);

        ticket.setSeatNumber(newSeat.getSeatNumber());
        ticketRepository.save(ticket);
    }
}
