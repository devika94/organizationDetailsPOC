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

import com.organization.organizationDetails.exception.AllocationCreationException;
import com.organization.organizationDetails.exception.AllocationISNotFoundException;
import com.organization.organizationDetails.exception.AllocationNotFoundException;
import com.organization.organizationDetails.jsonAPI.MultiDataResponse;
import com.organization.organizationDetails.jsonAPI.SingleDataResponse;
import com.organization.organizationDetails.model.Allocation;
import com.organization.organizationDetails.model.Employee;
import com.organization.organizationDetails.model.Project;

@Service
public interface AllocationService {
	
	public SingleDataResponse<Allocation> createAllocation(Allocation allocation) throws AllocationCreationException;

	public MultiDataResponse<Allocation> createAllocationOfEmployees(List<Integer> empIDs,final int won,final Date startDate,final Date endDate,
			final String role) throws AllocationCreationException;

	public SingleDataResponse<Allocation> getCurrentAllocation(@PathVariable("empID") final int empID) throws AllocationISNotFoundException;
	
	public MultiDataResponse<Allocation> getFutureAllocations(int won,int empID) throws AllocationNotFoundException;
	
	public MultiDataResponse<Allocation> getAllocationHistory(final int empID) throws AllocationNotFoundException;
	
	public SingleDataResponse<Allocation> updateAllocation(final int empID,Allocation allocation) throws AllocationISNotFoundException;
	
	public MultiDataResponse<Allocation> updateAllocations(List<Integer> empIDs,final int won,final Date startDate,final Date endDate,final String role
			) throws AllocationNotFoundException;
	
	public SingleDataResponse<Allocation> cancelAllocation(final int won,final int empID) throws AllocationISNotFoundException;
}