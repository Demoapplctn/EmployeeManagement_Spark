package com.perficient.empmanagementsystem.controller;

import java.util.List;

import javax.validation.Valid;
import com.perficient.empmanagementsystem.common.CignaConstantUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.perficient.empmanagementsystem.dto.EmployeeDTO;
import com.perficient.empmanagementsystem.dto.EmployeeResponseDTO;
import com.perficient.empmanagementsystem.dto.LoginPageDTO;
import com.perficient.empmanagementsystem.exception.EmployeeNotFoundException;
import com.perficient.empmanagementsystem.exception.InCorrectEmailException;
import com.perficient.empmanagementsystem.exception.LoginPageErrorException;
import com.perficient.empmanagementsystem.model.Employee;
import com.perficient.empmanagementsystem.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin("*")
@RestController
@RequestMapping("/emp")
@Slf4j
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping(value = "/registration", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> employeeRegistration(@Valid @RequestBody EmployeeDTO employeeDTO) throws Exception {
        log.debug("[employeeRegistration] Begin");
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.employeeRegistration(employeeDTO));
    }

    @DeleteMapping("/deleteAll")//delete all entries
    public ResponseEntity<String> DeleteEmployeeRegistration() {
        log.debug("[DeleteEmployeeRegistration] Begin");

        return ResponseEntity.status(HttpStatus.OK).body(employeeService.employeeRegistrationDeleteAll());
    }

    @DeleteMapping("/deleteById/{empId}")
    public ResponseEntity<String> DeleteEmployeeByEmpID(@PathVariable("empId") Long empId){
        log.debug("[DeleteEmployeeByEmpID] Begin");

        return ResponseEntity.status(HttpStatus.OK).body(employeeService.deleteByEmpId(empId));
    }

    @PostMapping("/loginPageVerify")//verify the password
    public ResponseEntity<String> loginPageVerify(@RequestBody LoginPageDTO loginPageDTO) throws InCorrectEmailException, LoginPageErrorException {
        log.debug("[loginPageVerify] Begin");

        return ResponseEntity.status(HttpStatus.OK).body(employeeService.verifyLoginPage(loginPageDTO));
    }

    @PostMapping(value = "/uploadFile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> fileUpload(@RequestParam("File") MultipartFile file) throws Exception{
        log.debug("[fileUpload] Begin");
        if(file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CignaConstantUtils.PROVIDE_FILE);
        }
        else if (!CignaConstantUtils.FILE_FORMAT.equalsIgnoreCase(FilenameUtils.getExtension(file.getOriginalFilename()))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CignaConstantUtils.FILE_FORMAT_ERROR);
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(employeeService.uploadEmployeeRegistration(file));
        }

    }

    @GetMapping("/loadByEmail/{email}")
    public ResponseEntity<Employee> loadByEmail(@PathVariable String email) throws EmployeeNotFoundException {
        log.debug("loadByEmail Begin");
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.loadByEmail(email));

    }
    
    @GetMapping("/getAllEmployee")
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployee(){
    	log.debug("loadByAllEmployee");
		return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.loadAllEmployee());
    	
    }

}
