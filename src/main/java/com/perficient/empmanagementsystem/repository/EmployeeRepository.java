package com.perficient.empmanagementsystem.repository;

import com.perficient.empmanagementsystem.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, Long> {


}
