package com.perficient.empmanagementsystem.service;

import java.util.List;

import com.perficient.empmanagementsystem.dto.EmployeeDTO;
import com.perficient.empmanagementsystem.dto.LoginPageDTO;
import com.perficient.empmanagementsystem.exception.inCorrectEmailErrorException;
import com.perficient.empmanagementsystem.exception.loginPageErrorException;
import com.perficient.empmanagementsystem.model.Employee;

public interface EmployeeService {

	public Employee employeeRegistration(EmployeeDTO employeeDTO)throws Exception;
	public String UploadEmployeeRegistration(String path);
    public String employeeRegistrationDeleteAll();//deleting all entries
    public String findByEmail(LoginPageDTO loginPageDTO) throws inCorrectEmailErrorException;//finding password from database for already registered user
    public String verifyLoginPage(LoginPageDTO loginPageDTO) throws inCorrectEmailErrorException, loginPageErrorException;//Verifying password 
    public List<String> findAllForEmail();//finding all email list
    


}
