package com.perficient.empmanagementsystem.dto;

import static com.perficient.empmanagementsystem.common.CignaConstantUtils.PROVIDE_EMAIL;
import static com.perficient.empmanagementsystem.common.CignaConstantUtils.PROVIDE_EMP_ID;
import static com.perficient.empmanagementsystem.common.CignaConstantUtils.PROVIDE_FIRSTNAME;
import static com.perficient.empmanagementsystem.common.CignaConstantUtils.PROVIDE_LASTNAME;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.perficient.empmanagementsystem.model.EmployeeAddress;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponseDTO implements Serializable{
	
	private static final long serialVersionUID = 108667566181805146L;
	
    private Long empId;
    private String firstName;
    private String lastName;
    private String email;
    private long contactNo;
    private EmployeeAddress employeeAddress;
    private boolean admin;



}
