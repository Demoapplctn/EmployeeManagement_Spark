package com.perficient.empmanagementsystem.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDTO {
    private int count;
    private String message;

}
