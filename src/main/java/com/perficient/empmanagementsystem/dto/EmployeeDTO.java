package com.perficient.empmanagementsystem.dto;

import com.perficient.empmanagementsystem.model.EmployeeAddress;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import static com.perficient.empmanagementsystem.common.CignaConstantUtils.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO implements Serializable {
    private static final long serialVersionUID = -4507215921695703300L;
    @NotNull(message = PROVIDE_EMP_ID)

    private Long empId;
    @NotNull(message = PROVIDE_FIRSTNAME)
    private String firstName;
    @NotNull(message = PROVIDE_LASTNAME)
    private String lastName;
    @NotNull(message = PROVIDE_EMAIL)
    @Email
    private String email;
    @NotNull
    private long contactNo;
    @Valid
    @NotNull
    private EmployeeAddress employeeAddress;

    @NotNull(message = PROVIDE_PASSWORD)
    private String password;

    private boolean admin;

}
