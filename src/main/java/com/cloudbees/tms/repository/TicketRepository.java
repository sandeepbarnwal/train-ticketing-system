package com.cloudbees.tms.repository;


import com.cloudbees.tms.entity.Section;
import com.cloudbees.tms.entity.Ticket;
import com.cloudbees.tms.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    public Ticket findByPnr(String pnr);

    public Ticket findBySeatNumber(String seatNumber);
}
