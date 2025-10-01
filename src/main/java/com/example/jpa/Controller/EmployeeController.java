package com.example.jpa.Controller;

import org.springframework.web.bind.annotation.*;


import com.example.jpa.service.EmployeeService;
import com.example.jpa.dto.EmployeeDto;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor

public class EmployeeController {

	private final EmployeeService employeeService ;
	@GetMapping
	public Page<EmployeeDto>getAllEmployees(Pageable pageable){
		return employeeService.getAllEmployees(pageable);
	}
	
	@PostMapping
	public EmployeeDto createEmployee(@RequestBody EmployeeDto employeeDto) {
		return employeeService.createEmployee(employeeDto);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EmployeeDto>getEmployeeById(@PathVariable UUID id){
		EmployeeDto employeeDto = employeeService.getEmployeeById(id);
		return ResponseEntity.ok(employeeDto);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable UUID id, @RequestBody EmployeeDto employeeDetailsDto){
		EmployeeDto updatedEmployee = employeeService.updateEmployee(id,employeeDetailsDto);
		return ResponseEntity.ok(updatedEmployee);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteEmployee(@PathVariable UUID id){
		employeeService.deleteEmployee(id);
		return ResponseEntity.ok().build();
	}
}
