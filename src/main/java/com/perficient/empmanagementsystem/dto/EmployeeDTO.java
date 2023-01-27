package com.perficient.empmanagementsystem.dto;

import com.perficient.empmanagementsystem.model.Address;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import static com.perficient.empmanagementsystem.common.CignaConstantUtils.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO {
    @NotNull(message = PROVIDE_EMP_ID)
    private Long empId;
    @NotNull(message = PROVIDE_FIRSTNAME)
    private String firstName;
    @NotNull(message = PROVIDE_LASTNAME)
    private String lastName;
    @NotNull(message = PROVIDE_EMAIL)
    @Email
    private String email;
    private long contactNo;
    private Address address;

    @NotNull(message = PROVIDE_PASSWORD)
    private String password;

    private boolean admin;

}
