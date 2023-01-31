package com.perficient.empmanagementsystem.dto;

import javax.validation.constraints.NotNull;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeAddressDTO {

    private String address;
	@NotNull
    private String city;
	@NotNull
    private String state;
	@NotNull
    private String zipcode;
}
