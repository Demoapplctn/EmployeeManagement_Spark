package com.perficient.empmanagementsystem.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perficient.empmanagementsystem.dto.EmployeeDTO;
import com.perficient.empmanagementsystem.dto.LoginPageDTO;
import com.perficient.empmanagementsystem.exception.EmployeeNotFoundException;
import com.perficient.empmanagementsystem.exception.inCorrectEmailErrorException;
import com.perficient.empmanagementsystem.exception.loginPageErrorException;
import com.perficient.empmanagementsystem.model.EmployeeAddress;
import com.perficient.empmanagementsystem.model.Employee;
import com.perficient.empmanagementsystem.repository.EmployeeRepository;

import lombok.extern.slf4j.Slf4j;

import static com.perficient.empmanagementsystem.common.CignaConstantUtils.*;


@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;
    @Override

    public Employee employeeRegistration(EmployeeDTO employeeDTO) throws Exception{

        log.debug("[employeeRegistration] start service");
        EmployeeAddress address = EmployeeAddress.builder()
				.address(employeeDTO.getEmployeeAddress().getAddress())
                .city(employeeDTO.getEmployeeAddress().getCity())
                .state(employeeDTO.getEmployeeAddress().getState())
                .zipcode(employeeDTO.getEmployeeAddress().getZipcode())
                .build();
        Employee employee=Employee.builder()
                .firstName(employeeDTO.getFirstName())
                .lastName(employeeDTO.getLastName())
                .email(employeeDTO.getEmail())
                .password(employeeDTO.getPassword())
                .contactNo(employeeDTO.getContactNo())
                .empId(employeeDTO.getEmpId())
                .employeeAddress(address)
                .password(employeeDTO.getPassword())
                .admin(employeeDTO.isAdmin())
                .build();
        return employeeRepository.save(employee);
    }

    
	@Override
	public String employeeRegistrationDeleteAll() {//deleteall
		employeeRepository.deleteAll();
		return DELETE_ALL_RECORD;
	}
	
	
	
	@Override
	public String verifyLoginPage(LoginPageDTO loginPageDTO) throws inCorrectEmailErrorException, loginPageErrorException{//passwordverification
		
		if(loginPageDTO.getEmail().isBlank()) 
			return EMAIL_ID_CANNOT_BE_EMPTY;
		
		if(findAllForEmail().contains(loginPageDTO.getEmail()))
		 if (loginPageDTO.getPassword().contentEquals(findByEmail(loginPageDTO)))
			return USERNAME_AND_PASSWORD_MATCHES;
			else	
				throw new loginPageErrorException(PROVIDE_CORRECT_EMAIL_PASSWORD);
		throw new inCorrectEmailErrorException(PROVIDE_CORRECT_EMAIL);
		
		
	}
	
	
	@Override
	public String findByEmail(LoginPageDTO loginPageDTO) throws inCorrectEmailErrorException {//retreiving password from db for given email
		if(findAllForEmail().contains(loginPageDTO.getEmail())) {
		List<EmployeeDTO> value  = employeeRepository.findPasswordByEmail(loginPageDTO.getEmail()); 
		Map<String,String> myMap = new  HashMap<String,String>();
		for(EmployeeDTO employeeDTO : value)
			myMap.put(employeeDTO.getEmail(), employeeDTO.getPassword());
		String Password = myMap.get(loginPageDTO.getEmail());
		
		return Password;
		}
		throw new inCorrectEmailErrorException(GIVEN_EMAIL_DOES_NOT_PRESENT);
		
	}

	@Override
	public List<String> findAllForEmail() {
		
		 List<Employee> email = employeeRepository.findALLEmail();
		 
		 Map<String,String> myMap1 = new HashMap<String,String>();
		 for( Employee employee : email)
			 myMap1.put(employee.getFirstName(), employee.getEmail());
		 System.out.println(myMap1);
		 List<String> result = new ArrayList(myMap1.values());
		 return result;
		
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

	@Override
	public Employee loadById(Long empId) throws EmployeeNotFoundException {
		log.debug("service loadById begin");
		Employee employee = employeeRepository.findByEmpId(empId);
		if(employee == null) {
			throw new EmployeeNotFoundException();
		} 
		return employee;
	}

}

