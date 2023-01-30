package com.perficient.empmanagementsystem.model;

import lombok.*;


import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "Employee")
public class Employee {
    private Long empId;
    private String firstName;
    private String lastName;
    private String email;
    
    private long contactNo;
    private Address address;
    private String password;
    private boolean admin;


}
