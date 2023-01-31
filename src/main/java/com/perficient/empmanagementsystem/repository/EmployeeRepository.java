package com.perficient.empmanagementsystem.repository;

import com.perficient.empmanagementsystem.dto.EmployeeDTO;
import com.perficient.empmanagementsystem.model.Employee;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, Long> {
	@Query(value="{'email': ?0}",fields = "{empId:0}")
	List<EmployeeDTO> findPasswordByEmail(String email);
	
	
	List<Employee> findByEmailStartingWith(String regexp);
	
	Employee findByEmpId(Long empId);

	
}
