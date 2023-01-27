package com.perficient.empmanagementsystem.service;

import java.util.List;

import com.perficient.empmanagementsystem.dto.EmployeeDTO;
import com.perficient.empmanagementsystem.dto.LoginPageDTO;
import com.perficient.empmanagementsystem.model.Employee;

public interface EmployeeService {
    public Employee employeeRegistration(EmployeeDTO employeeDTO);
    public String employeeRegistrationDeleteAll();//deleting all entries
    public String findByEmail(LoginPageDTO loginPageDTO);//finding password from database for already registered user
    public String verifyLoginPage(LoginPageDTO loginPageDTO);//Verifying password 
    public List<String> findAllForEmail();
    
}
