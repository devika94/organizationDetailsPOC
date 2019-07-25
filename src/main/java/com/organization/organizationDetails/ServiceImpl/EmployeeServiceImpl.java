package com.organization.organizationDetails.ServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.organization.organizationDetails.builder.EmployeeResponseBuilder;
import com.organization.organizationDetails.builder.ProjectResponseBuilder;
import com.organization.organizationDetails.exception.EmployeeNotFoundException;
import com.organization.organizationDetails.jsonAPI.Data;
import com.organization.organizationDetails.jsonAPI.MultiDataResponse;
import com.organization.organizationDetails.jsonAPI.SingleDataResponse;
import com.organization.organizationDetails.jsonAPI.Warnings;
import com.organization.organizationDetails.jsonAPIErrors.Errors;
import com.organization.organizationDetails.model.Allocation;
import com.organization.organizationDetails.model.Employee;
import com.organization.organizationDetails.model.Project;
import com.organization.organizationDetails.repository.AllocationRepository;
import com.organization.organizationDetails.repository.EmployeeRepository;
import com.organization.organizationDetails.repository.ProjectRepository;
import com.organization.organizationDetails.service.EmployeeService;
import com.organization.organizationDetails.service.RelationService;


@Service
public class EmployeeServiceImpl<T> implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private EmployeeResponseBuilder<T> employeeResponseBuilder;

	private List<Data> dataList;
	private List<Warnings> warnings;
	
	@Override
	public SingleDataResponse<Employee> createEmployee(Employee employee) 
	{
		employeeRepository.save(employee);
		return new SingleDataResponse<>(new ArrayList<Warnings>(),employeeResponseBuilder.transformEmployee(employee),new ArrayList<Errors>());
	}

	@Override
	public MultiDataResponse<Employee> createEmployees(List<Employee> employees)
	{
		dataList=new ArrayList<>();
		employees.stream().map(employee -> 
			 								employeeResponseBuilder.transformEmployee(employeeRepository.save(employee)))
							.forEach(data -> 
											dataList.add(data)
								); 
		return new MultiDataResponse<Employee>(new ArrayList<Warnings>(),dataList,new ArrayList<Errors>());
		//employees.stream().map(employee -> employeeRepository.save(employee));
		/*employees.stream().forEach(employee -> { employeeRepository.save(employee);
		dataList.add(employeeResponseBuilder.transformEmployee(employee));
		});*/
		//.map(employee -> dataList.add(employeeResponseBuilder.transformEmployee(employee)))
		//.forEach(employee -> employeeRepository.save(employee));
		//map(employee -> dataList.add(employeeResponseBuilder.transformEmployee(employee))).
		//employeeRepository.saveAll(employees);
		/*employees.stream().forEach(employee -> {
		employeeRepository.save(employee);
		dataList.add(employeeResponseBuilder.transformEmployee(employee));
		});*/
			//dataList.stream().map(data -> new ArrayList<>());
		//return new MultiDataResponse<>(employeeResponseBuilder.transformEmployees(employees),errors);
		
		/*employees.stream().forEach(employee -> 
		 dataList.add(employeeResponseBuilder.transformEmployee(employeeRepository.save(employee))));*/
	}

	@Override
	public SingleDataResponse<Employee> getEmployee(int empID) throws EmployeeNotFoundException 
	{
		Optional<Employee> employee=employeeRepository.findById(empID);
		warnings=new ArrayList<>();
		if(!employee.isPresent())
			throw new EmployeeNotFoundException("Employee not found", empID);
		if(employee.isPresent() && employee.get().isDeleted()==true)
		warnings.add(new Warnings("Employee Left the job, Is an ex-employee"));
		return new SingleDataResponse<>(warnings,employeeResponseBuilder.transformEmployee(employee.get()),new ArrayList<Errors>());
	}
	
	@Override
	public MultiDataResponse<Employee> getEmployees() {
		dataList=new ArrayList<>();
		employeeRepository.findAll().stream().filter(employee -> !(employee.isDeleted())).forEach(employee -> dataList.add(employeeResponseBuilder.transformEmployee(employee)));
		return new MultiDataResponse<>(new ArrayList<Warnings>(),dataList,new ArrayList<Errors>());
	}
	
	@Override
	public SingleDataResponse<Employee> updateEmployee(int empID,Employee emp) throws EmployeeNotFoundException
	{
		Optional<Employee> employee=employeeRepository.findById(empID);
		warnings=new ArrayList<>();
		if(!employee.isPresent())
			throw new EmployeeNotFoundException("Employee not found", empID);
		if(employee.isPresent() && employee.get().isDeleted()==true)
		{
			warnings.add(new Warnings("Employee Left the job, Is an ex-employee and details can't be updated"));
			return new SingleDataResponse<>(warnings,new Data<>(),new ArrayList<Errors>()) ;
		}
		return new SingleDataResponse<>(new ArrayList<Warnings>(),employeeResponseBuilder.transformEmployee(employeeResponseBuilder.updateEmployeeDetails(emp,employee).get()),new ArrayList<Errors>());
	}

	@Override
	public SingleDataResponse<Employee> cancelEmployee(int empID) throws EmployeeNotFoundException
	{
		Optional<Employee> employee=employeeRepository.findById(empID);
		if(!employee.isPresent())
			throw new EmployeeNotFoundException("Employee doesn't exist,Incorrect employee ID", empID);
		employee.get().setDeleted(true);
		employeeRepository.save(employee.get());
		return new SingleDataResponse<>(new ArrayList<Warnings>(),new Data<>(employee.get().getEmpID()),new ArrayList<Errors>());	
	}	
}