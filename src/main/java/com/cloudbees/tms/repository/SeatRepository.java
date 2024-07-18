package com.cloudbees.tms.repository;

import com.cloudbees.tms.entity.Seat;
import com.cloudbees.tms.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findBySectionAndAvailable(Section section, Boolean available);

    Seat findBySeatNumber(String seatNumber);
}
