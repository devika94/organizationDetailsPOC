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

import com.organization.organizationDetails.exception.AllocationCreationException;
import com.organization.organizationDetails.exception.AllocationISNotFoundException;
import com.organization.organizationDetails.exception.AllocationNotFoundException;
import com.organization.organizationDetails.jsonAPI.MultiDataResponse;
import com.organization.organizationDetails.jsonAPI.SingleDataResponse;
import com.organization.organizationDetails.model.Allocation;
import com.organization.organizationDetails.model.Employee;
import com.organization.organizationDetails.model.Project;
import com.organization.organizationDetails.service.AllocationService;
import com.organization.organizationDetails.service.RelationService;

@RestController
@RequestMapping("/organization")
public class AllocationController {
	
	@Autowired
	private AllocationService allocationService;
	
	@PostMapping("/allocation")
	public SingleDataResponse<Allocation> postAllocation(@RequestBody Allocation allocation) throws AllocationCreationException
	{
		return allocationService.createAllocation(allocation);
	}
	
	@PostMapping("/allocations")
	public MultiDataResponse<Allocation> postAllocationOfEmployees(@RequestBody List<Integer> empIDs,
			@RequestParam("won") final int won, @RequestParam("startDate") final Date startDate,@RequestParam("endDate")  final Date endDate,
			@RequestParam("role") final String role) throws AllocationCreationException
	{
		return allocationService.createAllocationOfEmployees(empIDs ,won, startDate, endDate, role);
	}
	
	@GetMapping("/allocation/{empID}")
	public SingleDataResponse<Allocation> getCurrentAllocation(@PathVariable("empID") final int empID) throws AllocationISNotFoundException
	{
		return allocationService.getCurrentAllocation(empID);
	}
	
	@GetMapping("/allocations/{won}/{empID}")
	public MultiDataResponse<Allocation> getFutureAllocations(@PathVariable("won") final int won,@PathVariable("empID") final int empID) throws AllocationNotFoundException
	{
		return allocationService.getFutureAllocations(won, empID);
	}
	
	@GetMapping("/allocations/{empID}")
	public MultiDataResponse<Allocation> getAllocationHistory(@PathVariable("empID") final int empID) throws AllocationNotFoundException
	{
		return allocationService.getAllocationHistory(empID);
	}
	
	@PutMapping("/allocation/{empID}")
	public SingleDataResponse<Allocation> putAllocation(@PathVariable("empID") final int empID,@RequestBody Allocation allocation) throws AllocationISNotFoundException
	{
		return allocationService.updateAllocation(empID,allocation);
	}
	
	@PutMapping("/allocations")
	public MultiDataResponse<Allocation> putAllocations(@RequestBody List<Integer> empIDs,@RequestParam("won") final int won, @RequestParam("startDate") final Date startDate,@RequestParam("endDate")  final Date endDate,
			@RequestParam("role") final String role) throws AllocationNotFoundException
	{
		return allocationService.updateAllocations(empIDs, won, startDate, endDate ,role);
	}
	
	@DeleteMapping("/allocation/{won}/{empID}")
	public SingleDataResponse<Allocation> deleteAllocation(@PathVariable("won") final int won,@PathVariable("empID") final int empID) throws AllocationISNotFoundException
	{
		return allocationService.cancelAllocation(won,empID);
	}
	
	
}
