package com.perficient.empmanagementsystem.model;

import lombok.*;


import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "employee")
public class Employee implements Serializable {
    private static final long serialVersionUID = -7533904418009115422L;
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
    private Timestamp createDt;

}
