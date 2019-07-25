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
import com.organization.organizationDetails.model.Allocation;
import com.organization.organizationDetails.model.Employee;
import com.organization.organizationDetails.repository.AllocationRepository;
import com.organization.organizationDetails.repository.EmployeeRepository;

@Component
public class AllocationResponseBuilder<T> {

	@Autowired
	private AllocationRepository allocationRepository;
	
	List<String> links=new ArrayList<>();
	
	@Transactional
	public Data transformAllocation(Allocation allocation)
	{
		allocationRepository.save(allocation);
		
		 Data data=new Data<T>(allocation.getEmpID(), "allocation",((T) allocation), (new Relationships("organization","allocation","v1")),
				links);
		System.out.println("data elements:"+data);
		return data;
		//return null;
	}
	
	/*@Transactional
	public List<Data> transformAllocations(List<Allocation> allocations)
	{
		List<Data> listData=new ArrayList<>();
		allocations.stream().forEach(allocation ->listData.add(transformAllocation(allocation)));
	return listData;
	}*/
}
