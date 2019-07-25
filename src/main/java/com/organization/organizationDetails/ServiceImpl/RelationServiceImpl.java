package com.organization.organizationDetails.ServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.organization.organizationDetails.builder.EmployeeResponseBuilder;
import com.organization.organizationDetails.builder.ProjectResponseBuilder;
import com.organization.organizationDetails.exception.AllocationNotFoundException;
import com.organization.organizationDetails.jsonAPI.Data;
import com.organization.organizationDetails.jsonAPI.MultiDataResponse;
import com.organization.organizationDetails.jsonAPI.SingleDataResponse;
import com.organization.organizationDetails.jsonAPI.Warnings;
import com.organization.organizationDetails.jsonAPIErrors.Errors;
import com.organization.organizationDetails.model.Allocation;
import com.organization.organizationDetails.model.Employee;
import com.organization.organizationDetails.model.Project;
import com.organization.organizationDetails.model.Relation;
import com.organization.organizationDetails.repository.AllocationRepository;
import com.organization.organizationDetails.repository.EmployeeRepository;
import com.organization.organizationDetails.repository.ProjectRepository;
import com.organization.organizationDetails.repository.RelationRepository;
import com.organization.organizationDetails.service.RelationService;




@Service
public class RelationServiceImpl<T> implements RelationService{

	@Autowired
	private RelationRepository relationRepository;
	
	@Autowired
	private AllocationRepository allocationRepository;

	private List<Errors> errors;
	
	private List<Warnings> warnings;

	@Override
	public SingleDataResponse<Relation> createRelation(int manEmpID, int empID) {
	if(allocationRepository.findByEmpID(manEmpID).isEmpty() || allocationRepository.findByEmpID(empID).isEmpty())
		{ 
			warnings=new ArrayList();
			warnings.add(new Warnings("Employee is not currently allocated in any won.Please allocate and then create relation"));
			return new SingleDataResponse<>(warnings, new Data(), new ArrayList<Errors>());
		
		}
		allocationRepository.findByEmpID(manEmpID).stream()
		.filter(alloc -> (new Date().compareTo(alloc.getStartDate())>=0 && new Date().compareTo(alloc.getEndDate())<=0));
	
		
		return null;
		
		
		
		
		
	}

}