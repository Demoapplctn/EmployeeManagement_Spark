package com.perficient.empmanagementsystem.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "Employee")
public class Employee {
    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";

    @Id
    private Long empId;
    private String firstName;
    private String lastName;
    private String email;
    private int contactNo;
    private Address address;

}
