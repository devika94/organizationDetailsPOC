package com.organization.organizationDetails.exceptionHandler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.organization.organizationDetails.exception.AllocationCreationException;
import com.organization.organizationDetails.exception.AllocationISNotFoundException;
import com.organization.organizationDetails.exception.AllocationNotFoundException;
import com.organization.organizationDetails.exception.EmployeeNotFoundException;
import com.organization.organizationDetails.exception.ProjectNotFoundException;
import com.organization.organizationDetails.jsonAPI.Data;
import com.organization.organizationDetails.jsonAPI.MultiDataResponse;
import com.organization.organizationDetails.jsonAPI.SingleDataResponse;
import com.organization.organizationDetails.jsonAPI.Warnings;
import com.organization.organizationDetails.jsonAPIErrors.Errors;
import com.organization.organizationDetails.model.Allocation;
import com.organization.organizationDetails.model.Employee;
import com.organization.organizationDetails.model.Project;


@ControllerAdvice
@RestController
public class CustomExceptionHandler {

	private List<Errors> errors;

	private List<Warnings> warnings;

	
	@ExceptionHandler(value=EmployeeNotFoundException.class)
	public SingleDataResponse<Employee> exception(EmployeeNotFoundException exception)
	{
		errors=new ArrayList<>();
		errors.add(new Errors(exception.getEmpID(), HttpStatus.NOT_FOUND , exception.getExceptionMessage(), "/organization/employee"));
		return new SingleDataResponse<Employee>(new ArrayList<Warnings>(),new Data<>(), errors);	
	}
	
	@ExceptionHandler(value=ProjectNotFoundException.class)
	public SingleDataResponse<Project> exception(ProjectNotFoundException exception)
	{
		errors=new ArrayList<>();
		errors.add(new Errors(exception.getWon(), HttpStatus.NOT_FOUND , exception.getExceptionMessage(), "/organization/project"));
		return new SingleDataResponse<Project>(new ArrayList<Warnings>(),new Data<>(), errors);	
	}
	
	@ExceptionHandler(value=AllocationCreationException.class)
	public SingleDataResponse<Allocation> exception(AllocationCreationException exception) throws JsonProcessingException 
	{
		warnings=new ArrayList<Warnings>();
		errors=new ArrayList<>();
		errors.add(new Errors(exception.getWon(), HttpStatus.BAD_REQUEST , exception.getExceptionMessage(), "/organization/allocation"));
		warnings.add(new Warnings("Start date and End Date is not in between project's life span"));
		System.out.println(new ObjectMapper().writeValueAsString(new SingleDataResponse<Allocation>(warnings,new Data<>(), errors)));
		return new SingleDataResponse<Allocation>(warnings,new Data<>(), errors);	
	}
	
	@ExceptionHandler(value=AllocationISNotFoundException.class)
	public SingleDataResponse<Allocation> notFoundException(AllocationISNotFoundException exception) 
	{
		
		errors=new ArrayList<>();
		warnings=new ArrayList<>();
		errors.add(new Errors(0, HttpStatus.BAD_REQUEST , exception.getMessage(), "/organization/allocation"));
		warnings.add(new Warnings("Allocation not found"));
		return new SingleDataResponse<Allocation>(warnings,new Data<>(),errors);	
	}
	
	
	@ExceptionHandler(value=AllocationNotFoundException.class)
	public MultiDataResponse<Allocation> exception(AllocationNotFoundException exception)
	{
		errors=new ArrayList<>();
		errors.add(new Errors(0, HttpStatus.NOT_FOUND , exception.getMessage(), "/organization/allocation"));
		return new MultiDataResponse<Allocation>(new ArrayList<Warnings>(),new ArrayList<Data>(), errors);
	}
	
}
