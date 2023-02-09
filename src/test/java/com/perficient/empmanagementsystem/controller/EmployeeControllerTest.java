package com.perficient.empmanagementsystem.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.perficient.empmanagementsystem.dto.EmployeeDTO;
import com.perficient.empmanagementsystem.dto.EmployeeResponseDTO;
import com.perficient.empmanagementsystem.dto.LoginPageDTO;
import com.perficient.empmanagementsystem.model.Employee;
import com.perficient.empmanagementsystem.service.EmployeeService;


@WebMvcTest
public class EmployeeControllerTest {
	
	
	@Autowired
	MockMvc mvc;
	
	@InjectMocks
	EmployeeController employeeController;
	
	@MockBean
	EmployeeService employeeService;
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	@Test
	void EmployeeRegistration_ReturnOk() throws Exception {
		
		Employee employee = getEmployeeDetails();
		EmployeeDTO employeeDTO = getEmployeeDTODetails();
		
		Mockito.when(employeeService.employeeRegistration(employeeDTO)).thenReturn(employee);
		String json = mapper.writeValueAsString(employeeDTO);
		
		mvc.perform(MockMvcRequestBuilders.post("/emp/registration")
				.contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isCreated());
		
	}
	
	@Test
	void EmployeeRegistration_ReturnNotOk() throws Exception {
		
		mvc.perform(MockMvcRequestBuilders.post("/emp/registration")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
						
		
	}
	
	@Test
	void DeleteEmployeeRegistration_returnOk() throws Exception {
		
	Mockito.when(employeeService.employeeRegistrationDeleteAll()).thenReturn("delete all record successfully");
	
	MvcResult requestResult = mvc.perform(MockMvcRequestBuilders.delete("/emp/deleteAll")
			.contentType(MediaType.APPLICATION_JSON))
	.andExpect(status().isOk()).andExpect(status().isOk()).andReturn();
	
	String result = requestResult.getResponse().getContentAsString();
	
	assertEquals(result, "delete all record successfully");
	
	}
	
	@Test
	void DeleteEmployeeByEmpId_returnOk() throws Exception {
		
		Long empId = 1234567L;
		
	Mockito.when(employeeService.deleteByEmpId(empId)).thenReturn("Employee deleted successfully");
	
	MvcResult requestResult = mvc.perform(MockMvcRequestBuilders.delete("/emp/deleteById/{empId}",1234567L)
			.contentType(MediaType.APPLICATION_JSON))
	.andExpect(status().isOk()).andExpect(status().isOk()).andReturn();
	
	String result = requestResult.getResponse().getContentAsString();
	
	assertEquals(result, "Employee deleted successfully");
	
	}
	
	
	@Test
	void DeleteEmployeeByEmpId_returnNotOk() throws Exception {
	
	mvc.perform(MockMvcRequestBuilders.delete("/emp/deleteById/")
			.contentType(MediaType.APPLICATION_JSON))
	.andExpect(status().is4xxClientError());
	}
	
	
	@Test
	void LoginPageVerify_returnOk() throws Exception {
		
		LoginPageDTO loginPageDTO = new LoginPageDTO();
		loginPageDTO.setEmail("naga@gmail.com");
		loginPageDTO.setPassword("12345");
		
		
	Mockito.when(employeeService.verifyLoginPage(loginPageDTO)).thenReturn("username and password matches");
	
	String json = mapper.writeValueAsString(loginPageDTO);
	
	mvc.perform(MockMvcRequestBuilders.post("/emp/loginPageVerify")
			.contentType(MediaType.APPLICATION_JSON).content(json))
	.andExpect(status().isOk());
	
	
	
	}
	
	@Test
	void LoginPageVerify_returnNotOk() throws Exception {
	
	mvc.perform(MockMvcRequestBuilders.post("/emp/loginPageVerify")
			.contentType(MediaType.APPLICATION_JSON))
	.andExpect(status().is4xxClientError());

	}
	
	@Test
	void LoadByEmail_ReturnOk() throws Exception {
		
		String email = "naga@gmail.com";
		Employee employee = getEmployeeDetails();
		
	Mockito.when(employeeService.loadByEmail(email)).thenReturn(employee);
	
	mvc.perform(MockMvcRequestBuilders.get("/emp/loadByEmail/{email}","naga@gmail.com")
			.contentType(MediaType.APPLICATION_JSON))
	.andExpect(status().isOk());
	}
	
	
	@Test
	void LoadByEmail_ReturnNotOk() throws Exception {
		
	mvc.perform(MockMvcRequestBuilders.get("/emp/loadByEmail")
			.contentType(MediaType.APPLICATION_JSON))
	.andExpect(status().is4xxClientError());
	
	}

	
	@Test
	void GetAllEmlployee_ReturnOk() throws Exception {
		
		List<EmployeeResponseDTO> employeeResponseDTO = new ArrayList<EmployeeResponseDTO>();
		employeeResponseDTO.add(getEmployeeResponseDTODetails());
		
	Mockito.when(employeeService.loadAllEmployee()).thenReturn(employeeResponseDTO);
	
	mvc.perform(MockMvcRequestBuilders.get("/emp/getAllEmployee")
			.contentType(MediaType.APPLICATION_JSON))
	.andExpect(status().isOk());
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
	
	private EmployeeResponseDTO getEmployeeResponseDTODetails() throws JsonMappingException, JsonProcessingException {
		
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
				+ "	\"admin\": \"false\"\r\n"
				+ "}";
		
		ObjectMapper mapper = new ObjectMapper();
		EmployeeResponseDTO employeeResponseDTO =  mapper.readValue(response, EmployeeResponseDTO.class);
		return employeeResponseDTO;	
	}
	

}
