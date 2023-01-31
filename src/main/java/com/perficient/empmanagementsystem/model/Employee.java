package com.perficient.empmanagementsystem.model;

import lombok.*;


import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "employee")
public class Employee {
    @Indexed(unique = true)
    private Long empId;
    private String firstName;
    private String lastName;
    @Indexed(unique = true)
    private String email;
    private long contactNo;
    private EmployeeAddress employeeAddress;
    private String password;
    private boolean admin;

}
