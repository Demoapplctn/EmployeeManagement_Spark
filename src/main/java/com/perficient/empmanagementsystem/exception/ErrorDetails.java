package com.perficient.empmanagementsystem.exception;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
@ToString
@Builder
public class ErrorDetails {
    private int errorCode;
    private List<String> errorMessage;
    private String details;
}
