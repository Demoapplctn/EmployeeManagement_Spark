package com.perficient.empmanagementsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.MockitoAnnotations.openMocks;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.perficient.empmanagementsystem.dto.EmployeeDTO;
import com.perficient.empmanagementsystem.dto.LoginPageDTO;
import com.perficient.empmanagementsystem.exception.DuplicateEntryException;
import com.perficient.empmanagementsystem.exception.EmployeeNotFoundException;
import com.perficient.empmanagementsystem.exception.InCorrectEmailException;
import com.perficient.empmanagementsystem.exception.LoginPageErrorException;
import com.perficient.empmanagementsystem.model.Employee;
import com.perficient.empmanagementsystem.repository.EmployeeRepository;

public class EmployeeServiceImplTest {
	
	@BeforeEach
	public void init() {
		openMocks(this);
	}

	@InjectMocks
	EmployeeServiceImpl employeeService;
	
	@Mock
	EmployeeRepository employeeRepository;
	
	@Test
	 void EmployeeRegistrationDeleteAllTest(){
		
		String expected = "delete all record successfully";
		
		employeeService.employeeRegistrationDeleteAll();
		verify(employeeRepository,times(1)).deleteAll();
		
		String actual = employeeService.employeeRegistrationDeleteAll();
		assertEquals(expected, actual);
		
	}
	
	@Test
	void deleteByEmPId_ReturnOk() {
		Long empId = 12345L;
		String expected = "Employee deleted successfully";
		
		when(employeeRepository.existsByEmpId(empId)).thenReturn(true);
		employeeService.deleteByEmpId(empId);
		verify(employeeRepository,times(1)).deleteByEmpId(empId);
		
		String actual = employeeService.deleteByEmpId(empId);
		assertEquals(expected, actual);

		
	}
	
	@Test
	void deleteByEmPId_ReturnNotOk() {
	
		Long empId = 12345L;
		
		
		when(employeeRepository.existsByEmpId(empId)).thenReturn(false);
		
		EmployeeNotFoundException ex = Assertions.assertThrows(EmployeeNotFoundException.class, ()-> {
		employeeService.deleteByEmpId(empId);
		},"Employee Not found with this Id");
		Assertions.assertEquals("Employee Not found with this Id", ex.getMessage());
		
		
	}
	
	@Test
	void LoadByEmail_ReturnOk() {
		
		String email = "naga.bushan@gmail.com";
		Employee employee = getEmployeeDetails();
		
		when(employeeRepository.findByEmail(email)).thenReturn(employee);
		employeeService.loadByEmail(email);
		assertNotNull(employee);
		
	}
	
	@Test
	void LoadByEmail_ReturnNotOk() {
		
		String email = "naga.bushan@gmail.com";
		Employee employee = null;
		
		when(employeeRepository.findByEmail(email)).thenReturn(employee);
		
		EmployeeNotFoundException ex = Assertions.assertThrows(EmployeeNotFoundException.class, ()-> {
			employeeService.loadByEmail(email);
			},"Employee Not found with this Id");
			Assertions.assertEquals("Employee Not found with this Id", ex.getMessage());
	}
	
	@Test
	void EmployeeRegistration_ReturnOk() throws Exception {
		
		EmployeeDTO employeeDTO = getEmployeeDTODetails();
		
		
		Employee employee = getEmployeeDetails();
		when(employeeRepository.save(employee)).thenReturn(employee);
		employeeService.employeeRegistration(employeeDTO);
		assertNotNull(employee);
	}
	
	
	@Test
	void verifyLoginPage_ReturnOk() throws JsonMappingException, JsonProcessingException {
		
		LoginPageDTO loginPageDTO = new LoginPageDTO();
		loginPageDTO.setEmail("naga.bushan@gmail.com");
		loginPageDTO.setPassword("naga@12345");
		String firstLetter = loginPageDTO.getEmail().substring(0, 1);
		String expected = "username and password matches";
		
		Employee employee = getEmployeeDetails();
		EmployeeDTO employeeDTO = getEmployeeDTODetails();
		
		List<String> result = new ArrayList<String>();
		result.add("naga.bushan@gmail.com");
		
		
		
		List<Employee> email = new ArrayList<Employee>();
		email.add(employee);
		
		List<EmployeeDTO> value = new ArrayList<EmployeeDTO>();
		value.add(employeeDTO);
		
		
		when(employeeRepository.findByEmailStartingWith(firstLetter)).thenReturn(email);
		when(employeeRepository.findPasswordByEmail(loginPageDTO.getEmail())).thenReturn(value);
		
		String actual = employeeService.verifyLoginPage(loginPageDTO);
		
		assertEquals(expected, actual);		
				
	}
	
	@Test
	void verifyLoginPage_ReturnNotOk_EnterCorrectEmail() throws JsonMappingException, JsonProcessingException {
		
		LoginPageDTO loginPageDTO = new LoginPageDTO();
		loginPageDTO.setEmail("karthik.thilak@gmail.com");
		loginPageDTO.setPassword("karthic@12345");
		String firstLetter = loginPageDTO.getEmail().substring(0, 1);
		
		
		Employee employee = getEmployeeDetails();
		EmployeeDTO employeeDTO = getEmployeeDTODetails();
		
		List<String> result = new ArrayList<String>();
		result.add("karthik.thilak@gmail.com");
		
		
		
		List<Employee> email = new ArrayList<Employee>();
		email.add(employee);
		
		List<EmployeeDTO> value = new ArrayList<EmployeeDTO>();
		value.add(employeeDTO);
		
		
		when(employeeRepository.findByEmailStartingWith(firstLetter)).thenReturn(email);
		when(employeeRepository.findPasswordByEmail(loginPageDTO.getEmail())).thenReturn(value);
		
		InCorrectEmailException ex = Assertions.assertThrows(InCorrectEmailException.class, ()-> {
			employeeService.verifyLoginPage(loginPageDTO);
			},"Pls enter the correct email for further process:");
			Assertions.assertEquals("Pls enter the correct email for further process:", ex.getMessage());
	}
	
	@Test
	void verifyLoginPage_ReturnNotOk_EnterCorrectEmailandPassword() throws JsonMappingException, JsonProcessingException {
		
		LoginPageDTO loginPageDTO = new LoginPageDTO();
		loginPageDTO.setEmail("karthik.thilak@gmail.com");
		loginPageDTO.setPassword("karthic@12345");
		String firstLetter = loginPageDTO.getEmail().substring(0, 1);
		
		
		Employee employee = getEmployeeDetails_PasswordDifferent();
		EmployeeDTO employeeDTO = getEmployeeDTODetails_PasswordDifferent();
		
		List<String> result = new ArrayList<String>();
		result.add("karthik.thilak@gmail.com");
		
		
		
		List<Employee> email = new ArrayList<Employee>();
		email.add(employee);
		
		List<EmployeeDTO> value = new ArrayList<EmployeeDTO>();
		value.add(employeeDTO);
		
		
		when(employeeRepository.findByEmailStartingWith(firstLetter)).thenReturn(email);
		when(employeeRepository.findPasswordByEmail(loginPageDTO.getEmail())).thenReturn(value);
		
		LoginPageErrorException ex = Assertions.assertThrows(LoginPageErrorException.class, ()-> {
			employeeService.verifyLoginPage(loginPageDTO);
			},"entered email and password does not match pls provide correct details.");
			Assertions.assertEquals("entered email and password does not match pls provide correct details.", ex.getMessage());
	}
				
	
	
	private Employee getEmployeeDetails() {
		
		Employee employee = new Employee();
		employee.setEmpId(123456L);
		employee.setFirstName("naga");
		employee.setLastName("bushan");
		employee.setEmail("naga.bushan@gmail.com");
		employee.setContactNo(9790743853L);
		employee.setPassword("naga@12345");
		employee.setAdmin(false);
		
		return employee;
	}
	
private Employee getEmployeeDetails_PasswordDifferent() {
		
		Employee employee = new Employee();
		employee.setEmpId(123456L);
		employee.setFirstName("naga");
		employee.setLastName("bushan");
		employee.setEmail("karthik.thilak@gmail.com");
		employee.setContactNo(9790743853L);
		employee.setPassword("naga@12345");
		employee.setAdmin(false);
		
		return employee;
	}
	
	private EmployeeDTO getEmployeeDTODetails() throws JsonMappingException, JsonProcessingException {
		
		String response = "{\r\n"
				+ "	\"empId\": \"123456\",\r\n"
				+ "	\"firstName\": \"naga \",\r\n"
				+ "	\"lastName\": \"bushan \",\r\n"
				+ "	\"email\": \"naga.bushan@gmail.com\",\r\n"
				+ "	\"contactNo\": \"9790743853\",\r\n"
				+ "	\"employeeAddress\": "
				+ "{\r\n"
				+ "		\"address\": \"Door no.18\",\r\n"
				+ "		\"city\": \"chennai\",\r\n"
				+ "		\"state\": \"Tamilnadu\",\r\n"
				+ "		\"zipcode\": \"602024\"\r\n"
				+ "	},\r\n"
				+ "	\"password\": \"naga@12345\",\r\n"
				+ "	\"admin\": \"false\"\r\n"
				+ "}";
		
		ObjectMapper mapper = new ObjectMapper();
		EmployeeDTO employeeDTO = mapper.readValue(response, EmployeeDTO.class);
		return employeeDTO;
		
		
		
	}
	
private EmployeeDTO getEmployeeDTODetails_PasswordDifferent() throws JsonMappingException, JsonProcessingException {
		
		String response = "{\r\n"
				+ "	\"empId\": \"123456\",\r\n"
				+ "	\"firstName\": \"naga \",\r\n"
				+ "	\"lastName\": \"bushan \",\r\n"
				+ "	\"email\": \"karthik.thilak@gmail.com\",\r\n"
				+ "	\"contactNo\": \"9790743853\",\r\n"
				+ "	\"employeeAddress\": "
				+ "{\r\n"
				+ "		\"address\": \"Door no.18\",\r\n"
				+ "		\"city\": \"chennai\",\r\n"
				+ "		\"state\": \"Tamilnadu\",\r\n"
				+ "		\"zipcode\": \"602024\"\r\n"
				+ "	},\r\n"
				+ "	\"password\": \"naga@12345\",\r\n"
				+ "	\"admin\": \"false\"\r\n"
				+ "}";
		
		ObjectMapper mapper = new ObjectMapper();
		EmployeeDTO employeeDTO = mapper.readValue(response, EmployeeDTO.class);
		return employeeDTO;
		
		
		
	}
	
}




