package com.perficient.empmanagementsystem.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    private String city;
    private String state;
    private String zipcode;
}
