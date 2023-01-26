package com.perficient.empmanagementsystem.dto;

import com.perficient.empmanagementsystem.model.Address;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO {
	
    private Long empId;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String email;
    
    @NotNull
    private String password;
    @NotNull
    private long contactNo;
    @NotNull
    private Address address;
}
