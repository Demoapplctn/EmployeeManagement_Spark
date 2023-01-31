package com.perficient.empmanagementsystem.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeAddress {
    private String address;
    private String city;
    private String state;
    private String zipcode;
}
