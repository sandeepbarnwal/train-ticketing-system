package com.cloudbees.tms.controllers;

import com.cloudbees.tms.dto.BookingRequest;
import com.cloudbees.tms.dto.CancelTicketResponse;
import com.cloudbees.tms.entity.Booking;
import com.cloudbees.tms.entity.Ticket;
import com.cloudbees.tms.service.BookingService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    /**
     *
     * @param request
     * @return Booking
     */
    @PostMapping
    public Booking createBooking(@RequestBody BookingRequest request) {

        return bookingService.createBooking(request.getUserId(),
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPrice(),
                request.getTrainCode(),
                request.getSectionName(),
                request.getFromStation(),
                request.getToStation(),
                request.getTravelDate(),
                request.getPassengers()
        );
    }

    /**
     *
     * @param pnr
     */
    @DeleteMapping("/cancel/{pnr}")
    public CancelTicketResponse cancelBookingByPnr(@PathVariable @NonNull String pnr) {
        bookingService.cancelBooking(pnr);
        return CancelTicketResponse.builder()
                .pnr(pnr)
                .status("Cancelled").build();
    }

    /**
     *
     * @param bookingId
     */
    @DeleteMapping("/{bookingId}")
    public void deleteBooking(@PathVariable @NonNull Long bookingId) {
        bookingService.deleteBooking(bookingId);
    }


}
