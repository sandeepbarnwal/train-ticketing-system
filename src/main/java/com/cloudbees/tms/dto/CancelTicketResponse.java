package com.cloudbees.tms.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CancelTicketResponse {
    private String pnr;
    private String status;
}
