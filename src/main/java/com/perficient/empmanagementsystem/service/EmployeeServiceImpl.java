package com.perficient.empmanagementsystem.service;

import com.mongodb.spark.MongoSpark;
import com.perficient.empmanagementsystem.dto.EmployeeDTO;
import com.perficient.empmanagementsystem.model.Address;
import com.perficient.empmanagementsystem.model.Employee;
import com.perficient.empmanagementsystem.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.sql.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.spark.api.java.JavaSparkContext;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    public Employee employeeRegistration(EmployeeDTO employeeDTO) throws Exception{
        log.debug("[employeeRegistration] start service");
        Address address =Address.builder()
                .city(employeeDTO.getAddress().getCity())
                .state(employeeDTO.getAddress().getState())
                .zipcode(employeeDTO.getAddress().getZipcode())
                .build();
        Employee employee=Employee.builder()
                .firstName(employeeDTO.getFirstName())
                .lastName(employeeDTO.getLastName())
                .email(employeeDTO.getEmail())
                .contactNo(employeeDTO.getContactNo())
                .empId(employeeDTO.getEmpId())
                .address(address)
                .password(employeeDTO.getPassword())
                .admin(employeeDTO.isAdmin())
                .build();
        return employeeRepository.save(employee);
    }

    @Override
    public String UploadEmployeeRegistration(String path) {

        SparkSession spark = SparkSession.builder().master("local[1]")
                .getOrCreate();

        Dataset<Row> csv =  spark.read().format("csv").option("header","true").load(path);

        JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());

        csv.write().mode(SaveMode.Append).format("com.mongodb.spark.sql.DefaultSource").option("spark.mongodb.input.uri", "mongodb://127.0.0.1/")
                .option("spark.mongodb.output.uri", "mongodb://127.0.0.1/")
                .option("database", "EmployeeDB")
                .option("collection", "Employee")
                .save();
        jsc.close();
        return "File Saved successfully";
    }

}
