package com.perficient.empmanagementsystem.controller;

import com.perficient.empmanagementsystem.dto.EmployeeDTO;
import com.perficient.empmanagementsystem.dto.LoginPageDTO;
import com.perficient.empmanagementsystem.model.Employee;
import com.perficient.empmanagementsystem.service.EmployeeService;
import com.perficient.empmanagementsystem.service.SequenceGeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping("/emp")
@Slf4j
public class EmployeeController {

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/registration")//registration
    public ResponseEntity<Employee> employeeRegistration(@Valid @RequestBody EmployeeDTO employeeDTO){
        log.debug("[employeeRegistration] Begin");
        employeeDTO.setEmpId(sequenceGeneratorService.generateSequence(Employee.SEQUENCE_NAME));

        return  ResponseEntity.status(HttpStatus.CREATED).body(employeeService.employeeRegistration(employeeDTO));
    }
    
    @DeleteMapping("/deleteAll")//delete all entries
    public ResponseEntity<String> DeleteEmployeeRegistration(){
        return  ResponseEntity.status(HttpStatus.OK).body(employeeService.employeeRegistrationDeleteAll());
    }
    
    @GetMapping("/emailandPassword")//retrieving password for the given email from data base
    public ResponseEntity<String>findbyEmail(@RequestBody LoginPageDTO loginPageDTO){
    	
        return  ResponseEntity.status(HttpStatus.OK).body(employeeService.findByEmail(loginPageDTO));
    }
    
    @GetMapping("/loginPageVerify")//verify the password
    public ResponseEntity<String> loginPageVerify(@RequestBody LoginPageDTO loginPageDTO){
    	
       return  ResponseEntity.status(HttpStatus.OK).body(employeeService.verifyLoginPage(loginPageDTO));
    }
    

}
