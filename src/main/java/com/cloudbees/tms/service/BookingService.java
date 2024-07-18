package com.cloudbees.tms.service;

import com.cloudbees.tms.common.TrainPnrGenerator;
import com.cloudbees.tms.dto.Passenger;
import com.cloudbees.tms.entity.*;
import com.cloudbees.tms.exception.SeatNotAvailableException;
import com.cloudbees.tms.exception.TicketNotFoundException;
import com.cloudbees.tms.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private SeatService seatService;
    @Autowired
    private TrainService trainService;

    @Autowired
    private SectionService sectionService;

    private HashMap<String, String> seatavailablityMap = new HashMap<>();

    @Transactional
    public Booking createBooking(Long userId, String firstName, String lastName, String email,
                                 double price, String trainCode, String sectionName, String fromStation,
                                 String toStation, LocalDate travelDate, List<Passenger> passengers) {
        // only one passenger per ticket for now. validate.

        //check user with user id or else search by email.
        Optional<User> userOptional = userService.getUserByEmail(email);
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
        } else {
            user = User.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email).build();
             userService.createUser(user);
        }

        //Find the train
        Optional<Train> trainOptional = trainService.getTrainByCode(trainCode);
        if (trainOptional.isEmpty()) {
            throw new RuntimeException("Train not found");
        }
        Train train = trainOptional.get();
        //set preferred section
        Section preferredSection = null;
        if(sectionName != null && !sectionName.isEmpty()) {
            Optional<Section> sectionOptional = train.getSections().stream().filter(section ->
                    section.getName().equalsIgnoreCase(sectionName)).findFirst();
            if (sectionOptional.isPresent()) {
                preferredSection = sectionOptional.get();
            } else {
                throw new RuntimeException("No such section found in train: " + train.getCode());
            }
        }
        Optional<Seat> seatOptional;
        if (!Objects.isNull(preferredSection)) {
            seatOptional = findAvailableSeat(preferredSection);
        } else {
            seatOptional = findAvailableSeat(train);
        }

        if (seatOptional.isEmpty()) {
            throw new SeatNotAvailableException("No seats available");
        }

        Seat avaialbleSeat = seatOptional.get();

        //create booking
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setBookingTime(LocalDateTime.now());
        //TODO: price module
        booking.setPricePaid(price);
        booking = bookingRepository.save(booking);

        //reserve seat.
        avaialbleSeat.setAvailable(false);
        seatService.save(avaialbleSeat);


        //create ticket
        Ticket ticket = new Ticket();
        ticket.setTrainCode(train.getCode());
        ticket.setSectionName(Objects.isNull(preferredSection)? avaialbleSeat.getSection().getName() : preferredSection.getName());
        ticket.setStartStop(fromStation);
        ticket.setEndStop(toStation);
        ticket.setPassengerFirstName(passengers.isEmpty()? user.getFirstName(): passengers.get(0).getFirstName());
        ticket.setPassengerLastName(passengers.isEmpty()? user.getLastName(): passengers.get(0).getLastName());
        ticket.setDepartureTime(travelDate.atTime(train.getDepartureTime()));
        ticket.setPnr(TrainPnrGenerator.generatePnr());
        ticket.setSeatNumber(avaialbleSeat.getSeatNumber());
        ticket.setBooking(booking);
        ticket = ticketRepository.save(ticket);

        booking.setTickets(Collections.singletonList(ticket));
        seatService.save(avaialbleSeat);

        return booking;
    }

    private Optional<Seat> findAvailableSeat(Train train) {
            for (Section section : train.getSections()) {
                Optional<Seat> seatOptional = findAvailableSeat(section);
                if (seatOptional.isPresent()) {
                    return seatOptional;
                }
            }
            return Optional.empty();
        }

    private Optional<Seat> findAvailableSeat(Section preferredSection) {

        return seatService.findAvailableSeat(preferredSection).stream().findAny();

    }

    private List<Seat> findAvailableSeat(Section preferredSection, int seats) {
        //check for seat availablity for given no of passengers
        return List.of();

    }


    public void deleteBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        bookingRepository.delete(booking);
    }

    //TODO: test this

    /**
     *
     * @param pnr
     */
    public void cancelBooking(String pnr) {
        Ticket  ticket = ticketRepository.findByPnr(pnr);
        if (Objects.isNull(ticket)) {
            throw new TicketNotFoundException("No tickets found with pnr: " + pnr);
        }
        Booking booking = bookingRepository.findByTickets(ticket);
        bookingRepository.delete(booking);
    }
}
