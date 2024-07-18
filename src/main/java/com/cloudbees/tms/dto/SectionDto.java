package com.cloudbees.tms.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class SectionDto {
    @NonNull
    private String name;
    @NonNull
    private Long trainId;
}
