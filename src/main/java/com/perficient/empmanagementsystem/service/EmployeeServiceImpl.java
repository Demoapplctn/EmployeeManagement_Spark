package com.perficient.empmanagementsystem.service;


import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.opencsv.CSVReader;
import com.perficient.empmanagementsystem.common.CignaConstantUtils;
import com.perficient.empmanagementsystem.dto.FileUploadResponseDTO;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.opencsv.CSVReader;
import com.perficient.empmanagementsystem.common.CignaConstantUtils;
import com.perficient.empmanagementsystem.dto.EmployeeDTO;
import com.perficient.empmanagementsystem.dto.EmployeeResponseDTO;
import com.perficient.empmanagementsystem.dto.LoginPageDTO;
import com.perficient.empmanagementsystem.exception.DuplicateEntryException;
import com.perficient.empmanagementsystem.exception.EmployeeNotFoundException;
import com.perficient.empmanagementsystem.exception.InCorrectEmailException;
import com.perficient.empmanagementsystem.exception.LoginPageErrorException;
import com.perficient.empmanagementsystem.model.Employee;
import com.perficient.empmanagementsystem.model.EmployeeAddress;
import com.perficient.empmanagementsystem.repository.EmployeeRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import static com.perficient.empmanagementsystem.common.CignaConstantUtils.*;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Value("${spring.data.mongodb.database}")
    String DATABASE;
    @Value("${spring.data.mongodb.collection}")
    String COLLECTION;
    @Value("${spring.data.mongodb.uri}")
    String URI;
    @Value("${file.upload-dir}")
    String FILE_DIRECTORY;
    @Value("${file.upload-dir-new}")
    String FILE_PATH;

    @Override
    public Employee employeeRegistration(EmployeeDTO employeeDTO) throws Exception {
        log.debug("[employeeRegistration] start service");
        Employee employee = null;

        EmployeeAddress address = EmployeeAddress.builder()
                .address(employeeDTO.getEmployeeAddress().getAddress())
                .city(employeeDTO.getEmployeeAddress().getCity())
                .state(employeeDTO.getEmployeeAddress().getState())
                .zipcode(employeeDTO.getEmployeeAddress().getZipcode())
                .build();
        employee = Employee.builder()
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
        try {
            employeeRepository.save(employee);
        } catch (Exception e) {
            String errorMessage = EMPLOYEE_EMAIL_EXIST;
            log.error(errorMessage);
            throw new DuplicateEntryException(errorMessage);
        }
        return employee;
    }


    @Override
    public String employeeRegistrationDeleteAll() {//deleteall
        employeeRepository.deleteAll();
        return DELETE_ALL_RECORD;
    }

    @Override
    public String verifyLoginPage(LoginPageDTO loginPageDTO) throws InCorrectEmailException, LoginPageErrorException {//passwordverification
        log.info("given email id :" + loginPageDTO.getEmail());

        if (loginPageDTO.getEmail().isEmpty())
            return EMAIL_ID_CANNOT_BE_EMPTY;

        if (findAllForEmail(loginPageDTO).contains(loginPageDTO.getEmail()))
            if (loginPageDTO.getPassword().contentEquals(findByEmail(loginPageDTO)))
                return USERNAME_AND_PASSWORD_MATCHES;
            else
                throw new LoginPageErrorException(PROVIDE_CORRECT_EMAIL_PASSWORD);
        throw new InCorrectEmailException(PROVIDE_CORRECT_EMAIL);

    }

    @Override
    public String findByEmail(LoginPageDTO loginPageDTO) throws InCorrectEmailException {//retreiving password from db for given email
        log.info("given email id :" + loginPageDTO.getEmail());

        if (findAllForEmail(loginPageDTO).contains(loginPageDTO.getEmail())) {
            List<EmployeeDTO> value = employeeRepository.findPasswordByEmail(loginPageDTO.getEmail());
            log.debug("password for the mail id:" + loginPageDTO.getEmail() + "returned from DB");

            Map<String, String> myMap = new HashMap<String, String>();
            for (EmployeeDTO employeeDTO : value)
                myMap.put(employeeDTO.getEmail(), employeeDTO.getPassword());
            String Password = myMap.get(loginPageDTO.getEmail());

            return Password;
        }
        log.error(GIVEN_EMAIL_DOES_NOT_PRESENT);
        throw new InCorrectEmailException(GIVEN_EMAIL_DOES_NOT_PRESENT);
    }

    @Override
    public List<String> findAllForEmail(LoginPageDTO loginPageDTO) {

        String firstLetter = loginPageDTO.getEmail().substring(0, 1);

        List<Employee> email = employeeRepository.findByEmailStartingWith(firstLetter);

        Map<String, String> myMap1 = new HashMap<String, String>();
        for (Employee employee : email)
            myMap1.put(employee.getFirstName(), employee.getEmail());

        List<String> result = new ArrayList(myMap1.values());
        log.info("list of email starting with :" + firstLetter + result);
        return result;

    }

    @Override
    public FileUploadResponseDTO uploadEmployeeRegistration(MultipartFile file) throws Exception {
        FileUploadResponseDTO responseDTO = new FileUploadResponseDTO();
        File myFile = createFile(file);
        int count = 0;
        if (validateFileHeader(myFile)) {
            String path = String.valueOf(myFile.getAbsoluteFile());
            SparkSession spark = SparkSession.builder().master("local[1]").getOrCreate();
            Dataset<Row> csv = spark.read().format("csv").option("header", "true").load(path);
            long longCount = csv.count();
            JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());
            try {
                csv.write().mode(SaveMode.Append).format("com.mongodb.spark.sql.DefaultSource")
                        .option("spark.mongodb.input.uri", URI)
                        .option("spark.mongodb.output.uri", URI)
                        .option("database", DATABASE)
                        .option("collection", COLLECTION)
                        .save();
            } catch (Exception e) {
                String errorMessage = EMPLOYEE_EMAIL_EXIST;
                log.error(errorMessage);
                throw new DuplicateEntryException(errorMessage);
            } finally {
                jsc.close();
            }
            responseDTO.setCount((int) longCount);
            responseDTO.setMessage(CignaConstantUtils.UPLOAD_SUCCESS_MESSAGE);
            return responseDTO;
        } else {
            responseDTO.setCount(count);
            responseDTO.setMessage(CignaConstantUtils.HEADER_MISMATCH);
            return responseDTO;
        }

    }

    private boolean validateFileHeader(File file) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(file));
        String[] header = reader.readNext();
        List<String> headerList = new ArrayList<>();
        for (String result : header) {
            headerList.add(result);
        }
        Map<String, String> fileHeaderMap = buildFileHeader();
        if (header.length == fileHeaderMap.size()) {
            if (fileHeaderMap.keySet().containsAll(headerList)) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    private Map<String, String> buildFileHeader() {

        Map<String, String> fileHeaderMap = new LinkedHashMap<>();
        fileHeaderMap.put("empId", "EMP_ID");
        fileHeaderMap.put("firstName", "FIRST_NAME");
        fileHeaderMap.put("lastName", "LAST_NAME");
        fileHeaderMap.put("email", "EMAIL");
        fileHeaderMap.put("contactNo", "contactNo");
        fileHeaderMap.put("address", "ADDRESS");
        fileHeaderMap.put("password", "PASSWORD");
        fileHeaderMap.put("admin", "ADMIN");
        return fileHeaderMap;
    }

    @Override
    public Employee loadByEmail(String email) throws EmployeeNotFoundException {
        log.debug("service loadById begin");
        Employee employee = employeeRepository.findByEmail(email);
        if (employee == null) {
            throw new EmployeeNotFoundException(PROVIDE_CORRECT_ID);
        }
        return employee;
    }

    @Override
    public String deleteByEmpId(Long empId) throws EmployeeNotFoundException {
        log.debug("service deleteById begin");
        if(employeeRepository.existsByEmpId(empId)){
            employeeRepository.deleteByEmpId(empId);
        }else{
            throw new EmployeeNotFoundException(PROVIDE_CORRECT_ID);
        }

        return DELETE_EMPLOYEE;
    }

    public File createFile(MultipartFile file) throws Exception {

        String username = System.getProperty("user.name");
        File myFile = null;
        File dir = new File(FILE_DIRECTORY + username + FILE_PATH);
        if (!dir.exists() && dir.mkdir()) {
            log.debug("Folder Created");
            myFile = new File(dir + file.getOriginalFilename());
            myFile.createNewFile();
            log.debug("File Created");
            FileOutputStream fos = new FileOutputStream(myFile);
            fos.write(file.getBytes());
            fos.close();
            return myFile;
        } else {
            myFile = new File(FILE_DIRECTORY + username + FILE_PATH + file.getOriginalFilename());
            myFile.createNewFile();
            log.debug("File Created");
            FileOutputStream fos = new FileOutputStream(myFile);
            fos.write(file.getBytes());
            fos.close();
            return myFile;
        }
    }


	@Override
	public List<EmployeeResponseDTO> loadAllEmployee() {
		return employeeRepository.findAll()
								.stream()
								.map(this::convertEntitytoDTO)
								.collect(Collectors.toList());
	
	}
	
	private EmployeeResponseDTO convertEntitytoDTO(Employee employee) {
		EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO();
		employeeResponseDTO.setEmpId(employee.getEmpId());
		employeeResponseDTO.setFirstName(employee.getFirstName());
		employeeResponseDTO.setLastName(employee.getLastName());
		employeeResponseDTO.setContactNo(employee.getContactNo());
		employeeResponseDTO.setEmail(employee.getEmail());
		employeeResponseDTO.setEmployeeAddress(employee.getEmployeeAddress());
		return employeeResponseDTO;
		
	}

}

