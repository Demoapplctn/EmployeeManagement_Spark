package com.perficient.empmanagementsystem.dto;

import javax.validation.constraints.NotNull;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeAddressDTO implements Serializable {
    private static final long serialVersionUID = -4318539983594519076L;
    @NotNull
    private String address;
    @NotNull
    private String city;
    @NotNull
    private String state;
    @NotNull
    private String zipcode;
}
