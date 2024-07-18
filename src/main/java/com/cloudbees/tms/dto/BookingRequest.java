package com.cloudbees.tms.dto;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookingRequest {
    private Long userId;
    @NonNull
    private String email;
    @NonNull
    private String firstName;
    private String lastName;
    @NonNull
    private Double price;
    @NonNull
    private String trainCode;
    private String sectionName;
    @NonNull
    private String fromStation;
    @NonNull
    private String toStation;
    @NonNull
    private LocalDate travelDate;
    private List<Passenger> passengers;
}
