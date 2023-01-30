package com.perficient.empmanagementsystem.dto;

import javax.validation.constraints.NotNull;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDTO {
	@NotNull
    private String city;
	@NotNull
    private String state;
	@NotNull
    private String zipcode;
}
