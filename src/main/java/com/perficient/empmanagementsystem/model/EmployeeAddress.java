package com.perficient.empmanagementsystem.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeAddress implements Serializable {
    private static final long serialVersionUID = -5713076133969253515L;
    private String address;
    private String city;
    private String state;
    private String zipcode;
}
