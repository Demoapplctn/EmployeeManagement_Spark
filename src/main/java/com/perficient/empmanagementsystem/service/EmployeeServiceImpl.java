package com.perficient.empmanagementsystem.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 

import com.perficient.empmanagementsystem.dto.EmployeeDTO;
import com.perficient.empmanagementsystem.dto.LoginPageDTO;
import com.perficient.empmanagementsystem.model.Address;
import com.perficient.empmanagementsystem.model.Employee;
import com.perficient.empmanagementsystem.repository.EmployeeRepository;

import lombok.extern.slf4j.Slf4j;

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
                .password(employeeDTO.getPassword())
                .contactNo(employeeDTO.getContactNo())
                .empId(employeeDTO.getEmpId())
                .address(address)
                .password(employeeDTO.getPassword())
                .admin(employeeDTO.isAdmin())
                .build();
        return employeeRepository.save(employee);
    }
    
	@Override
	public String employeeRegistrationDeleteAll() {//deleteall
		// TODO Auto-generated method stub
		employeeRepository.deleteAll();
		return "sucess";
	}
	
	
	
	@Override
	public String verifyLoginPage(LoginPageDTO loginPageDTO) {//passwordverification
		if(findAllForEmail().contains(loginPageDTO.getEmail()))
		 if (loginPageDTO.getPassword().contentEquals(findByEmail(loginPageDTO)))
			return "username and password matches";	
			else	
			return "username and password doesnot match";
		return "email does not exist";
			
		
		
	}
	
	
	@Override
	public String findByEmail(LoginPageDTO loginPageDTO) {//retreiving password from db for given email
		if(findAllForEmail().contains(loginPageDTO.getEmail())) {
		List<EmployeeDTO> value  = employeeRepository.findPasswordByEmail(loginPageDTO.getEmail()); 
		Map<String,String> myMap = new  HashMap<String,String>();
		for(EmployeeDTO employeeDTO : value)
			myMap.put(employeeDTO.getEmail(), employeeDTO.getPassword());
		String Password = myMap.get(loginPageDTO.getEmail());
		
		return Password;
		}
		return "email id does not exit";
		
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
	
	
	
	
	}
	
	
		
	

