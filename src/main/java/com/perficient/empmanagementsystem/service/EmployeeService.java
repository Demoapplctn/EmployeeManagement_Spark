package com.perficient.empmanagementsystem.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.perficient.empmanagementsystem.dto.EmployeeDTO;
import com.perficient.empmanagementsystem.dto.EmployeeResponseDTO;
import com.perficient.empmanagementsystem.dto.LoginPageDTO;
import com.perficient.empmanagementsystem.dto.ResponseDTO;
import com.perficient.empmanagementsystem.exception.EmployeeNotFoundException;
import com.perficient.empmanagementsystem.exception.InCorrectEmailException;
import com.perficient.empmanagementsystem.exception.LoginPageErrorException;
import com.perficient.empmanagementsystem.model.Employee;

public interface EmployeeService {
	public Employee employeeRegistration(EmployeeDTO employeeDTO)throws Exception;
    public ResponseDTO uploadEmployeeRegistration(MultipartFile path) throws Exception;
    public String employeeRegistrationDeleteAll();//deleting all entries
    public String findByEmail(LoginPageDTO loginPageDTO) throws InCorrectEmailException;//finding password from database for already registered user
    public String verifyLoginPage(LoginPageDTO loginPageDTO) throws InCorrectEmailException, LoginPageErrorException;//Verifying password
    public List<String> findAllForEmail(LoginPageDTO loginPageDTO);//finding all email list
    public Employee loadByEmail(String email) throws EmployeeNotFoundException;
    public List<EmployeeResponseDTO> loadAllEmployee();
    public String deleteByEmpId(Long empId);
}
