package com.cloudbees.tms.service;

import com.cloudbees.tms.dto.SeatDto;
import com.cloudbees.tms.entity.Seat;
import com.cloudbees.tms.entity.Section;
import com.cloudbees.tms.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeatService {

    @Autowired
    SeatRepository seatRepository;

    @Autowired
    SectionService sectionService;
    public List<Seat> getAllSeats() {
        return null;
    }

    public Seat getSeatById(Long seatId) {

        return seatRepository.findById(seatId).orElseThrow(() -> new RuntimeException("No such seat found"));
    }

    public Seat createSeat(SeatDto seatDto) {
        Section section = sectionService.getSectionById(seatDto.getSectionId());
        Seat seat = Seat.builder()
                .seatNumber(seatDto.getNumber())
                //.ticket(null)
                .section(section)
                .build();
        return seatRepository.save(seat);
    }

    public Seat createSeat(Seat seat) {
        return seatRepository.save(seat);
    }

    public Seat updateSeat(Seat seat) {
        return seatRepository.save(seat);
    }

    public Seat updateSeat(Long seatId, SeatDto seatDto) {
        Seat seat = getSeatById(seatId);
        seat.setSeatNumber(seatDto.getNumber());
        Section section = sectionService.getSectionById(seatDto.getSectionId());
        seat.setSection(section);
        //seat.setTicket(null);
        return seatRepository.save(seat);
    }

    public void deleteSeat(Long seatId) {
         seatRepository.deleteById(seatId);
    }

    public List<Seat> findAvailableSeat(Section preferredSection) {
        return seatRepository.findBySectionAndAvailable(preferredSection, true);
    }


    public Seat findSeatByNumber(String number) {
        return seatRepository.findBySeatNumber(number);
    }

    public Seat save(Seat seat) {
        return seatRepository.save(seat);
    }
}
