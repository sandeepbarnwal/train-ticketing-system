package com.cloudbees.tms;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.cloudbees.tms.dto.Passenger;
import com.cloudbees.tms.entity.*;
import com.cloudbees.tms.exception.SeatNotAvailableException;
import com.cloudbees.tms.exception.TicketNotFoundException;
import com.cloudbees.tms.repository.*;
import com.cloudbees.tms.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class BookingServiceTest {

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserService userService;
    @Mock
    private SeatService seatService;
    @Mock
    private TrainService trainService;
    @Mock
    private SectionService sectionService;

    private User user;

    private Train train;

    private Section section;

    private Seat seat;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@gmail.com";

        user = User.builder().firstName(firstName).lastName(lastName).email(email).build();

        section = new Section();
        String sectionName = "Economy";
        section.setName(sectionName);

        String trainCode = "TRAIN123";
        train = Train.builder()
                .code(trainCode)
                .departureTime(LocalDateTime.now().toLocalTime()).build();
        train.setSections(List.of(section));

        seat = new Seat();
        seat.setSeatNumber("A1");
        seat.setSection(section);

    }

    @Test
    public void testCreateBooking_UserExists_SeatAvailable() {

        when(userService.getUserByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(trainService.getTrainByCode(train.getCode())).thenReturn(Optional.of(train));
        when(seatService.findAvailableSeat(section)).thenReturn(List.of(seat));

        Booking savedBooking = new Booking();
        when(bookingRepository.save(any(Booking.class))).thenReturn(savedBooking);
        when(seatService.save(any(Seat.class))).thenReturn(seat);

        Ticket savedTicket = new Ticket();
        when(ticketRepository.save(any(Ticket.class))).thenReturn(savedTicket);

        Long userId = 1L;
        String fromStation = "StationA";
        String toStation = "StationB";
        LocalDate travelDate = LocalDate.now();
        double price = 100.0;
        List<Passenger> passengers = new ArrayList<>();


        Booking booking = bookingService.createBooking(null, user.getFirstName(), user.getLastName(), user.getEmail(),
                price, train.getCode(), section.getName(), fromStation, toStation, travelDate, passengers);

        assertNotNull(booking);
        verify(userService, times(1)).getUserByEmail(user.getEmail());
        verify(trainService, times(1)).getTrainByCode(train.getCode());
        verify(seatService, times(1)).findAvailableSeat(section);
        verify(bookingRepository, times(1)).save(any(Booking.class));
        verify(ticketRepository, times(1)).save(any(Ticket.class));
        verify(seatService, times(2)).save(any(Seat.class));
    }


    @Test
    public void testCreateBooking_SeatNotAvailable() {

        when(userService.getUserByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(trainService.getTrainByCode(train.getCode())).thenReturn(Optional.of(train));
        //when(seatService.findAvailableSeat(section)).thenReturn(List.of(seat));

        String fromStation = "StationA";
        String toStation = "StationB";
        LocalDate travelDate = LocalDate.now();
        double price = 100.0;
        List<Passenger> passengers = new ArrayList<>();

        assertThrows(SeatNotAvailableException.class, () -> {
            Booking booking = bookingService.createBooking(null, user.getFirstName(), user.getLastName(), user.getEmail(),
                    price, train.getCode(), section.getName(), fromStation, toStation, travelDate, passengers);
        });

        verify(userService, times(1)).getUserByEmail(user.getEmail());
        verify(trainService, times(1)).getTrainByCode(train.getCode());
        verify(seatService, times(1)).findAvailableSeat(section);
    }

    @Test
    public void testDeleteBooking_BookingExists() {
        Long bookingId = 1L;

        Booking booking = new Booking();
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

        bookingService.deleteBooking(bookingId);

        verify(bookingRepository, times(1)).findById(bookingId);
        verify(bookingRepository, times(1)).delete(booking);
    }

    @Test
    public void testDeleteBooking_BookingNotFound() {
        Long bookingId = 1L;

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            bookingService.deleteBooking(bookingId);
        });

        verify(bookingRepository, times(1)).findById(bookingId);
        verify(bookingRepository, times(0)).delete(any(Booking.class));
    }

    @Test
    public void testCancelBooking_TicketExists() {
        String pnr = "PNR123";

        Ticket ticket = new Ticket();
        Booking booking = new Booking();
        when(ticketRepository.findByPnr(pnr)).thenReturn(ticket);
        when(bookingRepository.findByTickets(ticket)).thenReturn(booking);

        bookingService.cancelBooking(pnr);

        verify(ticketRepository, times(1)).findByPnr(pnr);
        verify(bookingRepository, times(1)).findByTickets(ticket);
        verify(bookingRepository, times(1)).delete(booking);
    }

    @Test
    public void testCancelBooking_TicketNotFound() {
        String pnr = "PNR123";

        when(ticketRepository.findByPnr(pnr)).thenReturn(null);

        assertThrows(TicketNotFoundException.class, () -> {
            bookingService.cancelBooking(pnr);
        });

        verify(ticketRepository, times(1)).findByPnr(pnr);
        verify(bookingRepository, times(0)).findByTickets(any(Ticket.class));
        verify(bookingRepository, times(0)).delete(any(Booking.class));
    }
}