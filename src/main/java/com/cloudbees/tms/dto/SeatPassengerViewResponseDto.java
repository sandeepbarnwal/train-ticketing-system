package com.cloudbees.tms.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SeatPassengerViewResponseDto {


    private String seatNumber;
    private String PassengerName;

}
