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

import com.organization.organizationDetails.exception.EmployeeNotFoundException;
import com.organization.organizationDetails.jsonAPI.MultiDataResponse;
import com.organization.organizationDetails.jsonAPI.SingleDataResponse;
import com.organization.organizationDetails.model.Allocation;
import com.organization.organizationDetails.model.Employee;
import com.organization.organizationDetails.model.Project;

@Service
public interface ProjectService {

	public SingleDataResponse<Project> createProject(final int won,final String projectName,final Date startDate, final Date endDate );

	public MultiDataResponse<Project> createProjects(List<Project> projects);
	
	public SingleDataResponse<Project> getProject(final int won) throws EmployeeNotFoundException;
	
	public MultiDataResponse<Project> getProjects();
	
	public SingleDataResponse<Project> updateProject(final int won,Project project) throws EmployeeNotFoundException;

	public SingleDataResponse<Project> cancelProject(final int won) throws EmployeeNotFoundException;
}