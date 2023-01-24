package com.perficient.empmanagementsystem.service;

import com.perficient.empmanagementsystem.dto.EmployeeDTO;
import com.perficient.empmanagementsystem.model.Address;
import com.perficient.empmanagementsystem.model.Employee;
import com.perficient.empmanagementsystem.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    public Employee employeeRegistration(EmployeeDTO employeeDTO) {
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
                .build();
        return employeeRepository.save(employee);


    }
}
