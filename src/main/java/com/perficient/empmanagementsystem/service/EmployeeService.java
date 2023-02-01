package com.perficient.empmanagementsystem.service;

import java.io.File;
import java.util.List;

import com.perficient.empmanagementsystem.dto.EmployeeDTO;
import com.perficient.empmanagementsystem.dto.LoginPageDTO;
import com.perficient.empmanagementsystem.exception.EmployeeNotFoundException;
import com.perficient.empmanagementsystem.exception.InCorrectEmailException;
import com.perficient.empmanagementsystem.exception.LoginPageErrorException;
import com.perficient.empmanagementsystem.model.Employee;
import org.springframework.web.multipart.MultipartFile;

public interface EmployeeService {
	public Employee employeeRegistration(EmployeeDTO employeeDTO)throws Exception;
    public String UploadEmployeeRegistration(MultipartFile path) throws Exception;
    public String employeeRegistrationDeleteAll();//deleting all entries
    public String findByEmail(LoginPageDTO loginPageDTO) throws InCorrectEmailException;//finding password from database for already registered user
    public String verifyLoginPage(LoginPageDTO loginPageDTO) throws InCorrectEmailException, LoginPageErrorException;//Verifying password
    public List<String> findAllForEmail(LoginPageDTO loginPageDTO);//finding all email list
    public Employee loadByEmail(String email) throws EmployeeNotFoundException;

}
