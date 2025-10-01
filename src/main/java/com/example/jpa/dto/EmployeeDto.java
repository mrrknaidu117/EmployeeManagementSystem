package com.example.jpa.dto;

import lombok.Data;
import java.util.UUID;
@Data
public class EmployeeDto {
	private UUID id;
	private String firstName;
	private String lastName;
	private String email;
	private UUID departmentId;
	private String departmentName;
}
