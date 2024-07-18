package com.cloudbees.tms.repository;

import com.cloudbees.tms.entity.Booking;
import com.cloudbees.tms.entity.Ticket;
import com.cloudbees.tms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
   public  Booking findByTickets(Ticket ticket);
}