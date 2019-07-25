package com.organization.organizationDetails.builder;

import java.util.ArrayList;
import java.util.List;
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
public class RelationResponseBuilder<T> {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	List<String> links=new ArrayList<>();
	
	public Data transformEmployee(Employee employee)
	{
		employeeRepository.save(employee);
		 Data data=new Data<T>(employee.getEmpID(), "employee",((T) employee), (new Relationships("employee","project","v1")),
				links);
		System.out.println("data elements:"+data);
		return data;
	}
	
	/*@Transactional
	public List<Data> transformEmployees(List<Employee> employees)
	{
		List<Data> listData=new ArrayList<>();
		employees.stream().forEach(employee ->listData.add(transformEmployee(employee)));
		return listData;
	}*/
}
