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
import com.organization.organizationDetails.jsonAPI.MultiDataResponse;
import com.organization.organizationDetails.jsonAPI.SingleDataResponse;
import com.organization.organizationDetails.model.Allocation;
import com.organization.organizationDetails.model.Employee;
import com.organization.organizationDetails.model.Project;
import com.organization.organizationDetails.model.Relation;
import com.organization.organizationDetails.service.RelationService;

@RestController
@RequestMapping("/organization")
public class RelationController {
	
	@Autowired
	private RelationService retailService;

	@PostMapping(value="/relation/manager/{manEmpID}/employee/{empID}")
	public SingleDataResponse<Relation> postRelation(@PathVariable("manEmpID") final int manEmpID,@PathVariable("empID") final int empID)
	{
		return retailService.createRelation(manEmpID, empID);
	}
	
	}
