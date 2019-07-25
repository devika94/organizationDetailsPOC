package com.organization.organizationDetails.controller;

import java.util.Date;
import java.util.List;

import org.hibernate.boot.model.source.spi.SingularAttributeSourceEmbedded;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.organization.organizationDetails.exception.EmployeeNotFoundException;
import com.organization.organizationDetails.jsonAPI.MultiDataResponse;
import com.organization.organizationDetails.jsonAPI.SingleDataResponse;
import com.organization.organizationDetails.model.Allocation;
import com.organization.organizationDetails.model.Employee;
import com.organization.organizationDetails.model.Project;
import com.organization.organizationDetails.service.EmployeeService;
import com.organization.organizationDetails.service.RelationService;

@RestController
@RequestMapping("/organization")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;

	@PostMapping(value="/employee")
	public SingleDataResponse<Employee> postEmployee(@RequestBody Employee employee)
	{
		return employeeService.createEmployee(employee);
	}

	@PostMapping("/employees")
	public MultiDataResponse<Employee> postEmployees(@RequestBody List<Employee> employees)
	{
		return employeeService.createEmployees(employees);
	}

	@GetMapping("/employee/{empID}")
	public SingleDataResponse<Employee> getEmployee(@PathVariable("empID") final int empID) throws EmployeeNotFoundException
	{
		return employeeService.getEmployee(empID);
	}
	
	@GetMapping("/employees")
	public MultiDataResponse<Employee> getEmployees() throws Exception
	{
		return employeeService.getEmployees();
	}

	@PutMapping("employee/{empID}")
	public SingleDataResponse<Employee> putEmployee(@PathVariable("empID") final int empID, @RequestBody Employee employee) throws EmployeeNotFoundException
	{
		return employeeService.updateEmployee(empID,employee);
	}

	@DeleteMapping("employee/{empID}")
	public SingleDataResponse<Employee> deleteEmployee(@PathVariable("empID") final int empID) throws EmployeeNotFoundException
	{
		return employeeService.cancelEmployee(empID);
	}
	
}
