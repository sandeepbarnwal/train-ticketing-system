package com.cloudbees.tms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    //TODO: make it embeddbable
    private String passengerFirstName;

    private String passengerLastName;

    private LocalDateTime departureTime;

    private String pnr;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    @JsonBackReference
    private Booking booking;


//    //TODO: no need to refer object. instead take train code
//    @ManyToOne
//    @JoinColumn(name = "train_id", nullable = false)
//    @JsonBackReference
//    private Train train;

    private String trainCode;

//    //TODO: no need to refer object. instead take section name
//    @ManyToOne
//    @JoinColumn(name = "section_id", nullable = false)
//    @JsonBackReference
//    private Section section;

    private String sectionName;

    private String seatNumber;

    private String startStop;

    private String endStop;

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", passengerFirstName='" + passengerFirstName + '\'' +
                ", passengerLastName='" + passengerLastName + '\'' +
                ", departureTime=" + departureTime +
                ", pnr='" + pnr + '\'' +
                ", trainCode='" + trainCode + '\'' +
                ", sectionName='" + sectionName + '\'' +
                ", seatNumber='" + seatNumber + '\'' +
                ", startStop='" + startStop + '\'' +
                ", endStop='" + endStop + '\'' +
                '}';
    }
}
