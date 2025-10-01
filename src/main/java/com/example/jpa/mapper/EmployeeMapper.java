package com.example.jpa.mapper;

import com.example.jpa.dto.EmployeeDto;
import com.example.jpa.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "department.name", target = "departmentName")
    EmployeeDto toDto(Employee employee);

    @Mapping(target = "department", ignore = true)
    Employee toEntity(EmployeeDto employeeDto);
}