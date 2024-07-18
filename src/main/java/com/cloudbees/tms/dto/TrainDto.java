package com.cloudbees.tms.dto;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalTime;

@Data
public class TrainDto {
    @NonNull
    private String name;
    @NonNull
    private String code;

    @NonNull
    private LocalTime departureTime;
    @NonNull
    private LocalTime arrivalTime;
}
