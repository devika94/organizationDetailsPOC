package com.organization.organizationDetails.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.organization.organizationDetails.jsonAPI.Data;
import com.organization.organizationDetails.jsonAPI.Relationship;
import com.organization.organizationDetails.jsonAPI.Relationships;
import com.organization.organizationDetails.jsonAPI.Warnings;
import com.organization.organizationDetails.model.Employee;

import com.organization.organizationDetails.repository.EmployeeRepository;

@Component
public class EmployeeResponseBuilder<T> {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	List<String> links=new ArrayList<>();
	
	@Transactional
	public Data transformEmployee(Employee employee)
	{
		 Data data=new Data<T>(employee.getEmpID(), "employee",((T) employee), (new Relationships("employee","project","v1")),
				links);
		System.out.println("data elements:"+data);
		return data;
	}
	
	public Optional<Employee> updateEmployeeDetails(Employee emp,Optional<Employee> employee)
	{
		if(!(emp.getEmpExp()==null || emp.getEmpExp().isEmpty()))
				employee.get().setEmpExp(emp.getEmpExp());
		if(!(emp.getEmpName()==null || emp.getEmpName().isEmpty()))
			employee.get().setEmpName(emp.getEmpName());
		if(!(emp.getEmpRole()==null || emp.getEmpRole().isEmpty()))
			employee.get().setEmpRole(emp.getEmpRole());
		employee.get().setTagged(emp.isTagged());
		employee.get().setEmpRole(emp.getEmpRole());
		employee.get().setDeleted(emp.isDeleted());
		employeeRepository.save(employee.get());
		return employee ;
	}
	
	/*@Transactional
	public List<Data> transformEmployees(List<Employee> employees)
	{
		List<Data> listData=new ArrayList<>();
		employees.stream().forEach(employee ->listData.add(transformEmployee(employee)));
		return listData;
	}*/
}
