package com.cloudbees.tms.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class Passenger {

    @NonNull
    private String firstName;
    private String lastName;
    @NonNull
    private Integer age;
}
