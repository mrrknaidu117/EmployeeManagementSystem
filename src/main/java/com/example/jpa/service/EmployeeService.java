package com.example.jpa.service;

import com.example.jpa.dto.EmployeeDto;
import com.example.jpa.exception.ResourceNotFoundException;
import com.example.jpa.mapper.EmployeeMapper;
import com.example.jpa.model.Department;
import com.example.jpa.model.Employee;
import com.example.jpa.repository.DepartmentRepository;
import com.example.jpa.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
@RequiredArgsConstructor
public class EmployeeService {
	private final EmployeeRepository employeeRepository;
	private final EmployeeMapper employeeMapper;
	private final DepartmentRepository departmentRepository;

	
	public Page<EmployeeDto> getAllEmployees(Pageable pageable){
		Page<Employee> employeePage = employeeRepository.findAll(pageable);
		return employeePage.map(employeeMapper::toDto);
	}
	

	public EmployeeDto createEmployee(EmployeeDto employeeDto) {
		Employee employee = employeeMapper.toEntity(employeeDto);
		if (employeeDto.getDepartmentId() != null) {
			Department department = departmentRepository.findById(employeeDto.getDepartmentId())
	            .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
	        employee.setDepartment(department);
	    }
		Employee savedEmployee = employeeRepository.save(employee);
		return employeeMapper.toDto(savedEmployee);
	}
	
	public EmployeeDto getEmployeeById(UUID id) {
		Employee employee =  employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: "+id));
		return employeeMapper.toDto(employee);
	}
	
	public EmployeeDto updateEmployee(UUID id, EmployeeDto employeeDetailsDto) {
		Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));;
		
		employee.setFirstName(employeeDetailsDto.getFirstName());
		employee.setLastName(employeeDetailsDto.getLastName());
		employee.setEmail(employeeDetailsDto.getEmail());
		
		if (employeeDetailsDto.getDepartmentId() != null) {
	        Department department = departmentRepository.findById(employeeDetailsDto.getDepartmentId())
	            .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
	        employee.setDepartment(department);
	    }
		
		Employee updatedEmployee = employeeRepository.save(employee);
		return employeeMapper.toDto(updatedEmployee);
		
	}
	public void deleteEmployee(UUID id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: "+id));
		employeeRepository.delete(employee);
	}
	
	
}