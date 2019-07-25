package com.organization.organizationDetails.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.organization.organizationDetails.exception.EmployeeNotFoundException;
import com.organization.organizationDetails.jsonAPI.MultiDataResponse;
import com.organization.organizationDetails.jsonAPI.SingleDataResponse;
import com.organization.organizationDetails.model.Allocation;
import com.organization.organizationDetails.model.Employee;
import com.organization.organizationDetails.model.Project;

@Service
public interface EmployeeService {

	public SingleDataResponse<Employee> createEmployee(Employee employee);
	
	public MultiDataResponse<Employee> createEmployees(List<Employee> employees);

	public SingleDataResponse<Employee> getEmployee(final int empID) throws EmployeeNotFoundException;

	public MultiDataResponse<Employee> getEmployees();
	
	public SingleDataResponse<Employee> updateEmployee(final int empID,Employee Employee) throws EmployeeNotFoundException;
	
	public SingleDataResponse<Employee> cancelEmployee(final int empID) throws EmployeeNotFoundException;

}