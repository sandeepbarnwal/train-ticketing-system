package com.cloudbees.tms.controllers;

import com.cloudbees.tms.dto.SeatDto;
import com.cloudbees.tms.entity.Seat;
import com.cloudbees.tms.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
public class SeatController {
    @Autowired
    private SeatService seatService;

    @GetMapping
    public List<Seat> getAllSeats() {
        return seatService.getAllSeats();
    }

    @GetMapping("/{seatId}")
    public Seat getSeatById(@PathVariable Long seatId) {
        return seatService.getSeatById(seatId);
    }

    @PostMapping
    public Seat createSeat(@RequestBody SeatDto seatDto) {
        return seatService.createSeat(seatDto);
    }

    @PutMapping("/{seatId}")
    public Seat updateSeat(@PathVariable Long seatId, @RequestBody SeatDto seatDto) {
        return seatService.updateSeat(seatId, seatDto);
    }

    @DeleteMapping("/{seatId}")
    public void deleteSeat(@PathVariable Long seatId) {
        seatService.deleteSeat(seatId);
    }
}
