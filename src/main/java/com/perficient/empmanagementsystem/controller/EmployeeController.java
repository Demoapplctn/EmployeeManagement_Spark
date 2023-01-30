package com.perficient.empmanagementsystem.controller;

import com.perficient.empmanagementsystem.dto.EmployeeDTO;
import com.perficient.empmanagementsystem.model.Employee;
import com.perficient.empmanagementsystem.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@CrossOrigin("*")
@RestController
@RequestMapping("/emp")
@Slf4j
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Value("${file.upload-dir}")
    String FILE_DIRECTORY;


    @PostMapping(value = "/registration", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> employeeRegistration(@Valid @RequestBody EmployeeDTO employeeDTO)throws Exception{
        log.debug("[employeeRegistration] Begin");

        return  ResponseEntity.status(HttpStatus.CREATED).body(employeeService.employeeRegistration(employeeDTO));
    }

    @PostMapping(value="/uploadFile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> fileUpload(@RequestParam("File") MultipartFile file) throws IOException {
        log.debug("Uploading file Begin");
        File myFile = new File(FILE_DIRECTORY+file.getOriginalFilename());
        myFile.createNewFile();
        log.debug("File Created");
        FileOutputStream fos =new FileOutputStream(myFile);
        fos.write(file.getBytes());
        String path= String.valueOf(myFile.getAbsoluteFile());
        fos.close();
        employeeService.UploadEmployeeRegistration(path);
        return  ResponseEntity.status(HttpStatus.OK).body("File Uploaded Successfully");
    }

}
