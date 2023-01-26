package com.perficient.empmanagementsystem.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class LoginPageDTO {

	 	@NotNull	
	    private String email;
	 	
	    @NotNull
	    private String password;
	    
}
