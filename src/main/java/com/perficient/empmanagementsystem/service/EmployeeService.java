package com.perficient.empmanagementsystem.service;

import com.perficient.empmanagementsystem.dto.EmployeeDTO;
import com.perficient.empmanagementsystem.model.Employee;

public interface EmployeeService {
    public Employee employeeRegistration(EmployeeDTO employeeDTO)throws Exception;
}
