package com.cloudbees.tms.controllers;

import com.cloudbees.tms.dto.SeatPassengerViewResponseDto;
import com.cloudbees.tms.dto.SectionDto;
import com.cloudbees.tms.entity.Seat;
import com.cloudbees.tms.entity.Section;
import com.cloudbees.tms.entity.Ticket;
import com.cloudbees.tms.service.SectionService;
import com.cloudbees.tms.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sections")
public class SectionController {
    @Autowired
    private SectionService sectionService;

    @Autowired
    private TicketService ticketService;

    @GetMapping
    public List<Section> getAllSections() {
        return sectionService.getAllSections();
    }

    @GetMapping("/{sectionId}")
    public Section getSectionById(@PathVariable Long sectionId) {
        return sectionService.getSectionById(sectionId);
    }
    @GetMapping("/view/{sectionId}")
    public List<SeatPassengerViewResponseDto> getSeatPassengerDetailsbySectionId(@PathVariable Long sectionId) {
        Section section = sectionService.getSectionById(sectionId);
        List<String> seats = section.getSeats().stream().map(Seat::getSeatNumber).toList();
        List<SeatPassengerViewResponseDto> seatPassengerViewResponseDtos = new ArrayList<>();
        seats.forEach(seat -> {
            Ticket ticket = ticketService.getTicketBySeatNumber(seat);
            seatPassengerViewResponseDtos.add(SeatPassengerViewResponseDto.builder()
                    .seatNumber(seat)
                    .PassengerName(Objects.isNull(ticket) ?"" : ticket.getPassengerFirstName() + " "
                            + ticket.getPassengerLastName())
                    .build());
        });
        return seatPassengerViewResponseDtos;
    }

    @PostMapping
    public Section createSection(@RequestBody SectionDto sectionDto) {
        return sectionService.createSection(sectionDto);
    }

    @PutMapping("/{sectionId}")
    public Section updateSection(@PathVariable Long sectionId, @RequestBody SectionDto sectionDto) {
        return sectionService.updateSection(sectionId, sectionDto);
    }

    @DeleteMapping("/{sectionId}")
    public void deleteSection(@PathVariable Long sectionId) {
        sectionService.deleteSection(sectionId);
    }
}
