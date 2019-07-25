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

import com.organization.organizationDetails.exception.EmployeeNotFoundException;
import com.organization.organizationDetails.jsonAPI.MultiDataResponse;
import com.organization.organizationDetails.jsonAPI.SingleDataResponse;
import com.organization.organizationDetails.model.Allocation;
import com.organization.organizationDetails.model.Employee;
import com.organization.organizationDetails.model.Project;
import com.organization.organizationDetails.service.ProjectService;
import com.organization.organizationDetails.service.RelationService;

@RestController
@RequestMapping("/organization")
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;

	@PostMapping("/project")
	public SingleDataResponse<Project> postProject(@RequestParam("won") final int won, @RequestParam("projectName") final String projectName,@RequestParam("startDate")  final Date startDate,@RequestParam("endDate")  final Date endDate )
	{
		return projectService.createProject(won, projectName, startDate, endDate);
	}

	@PostMapping("/projects")
	public MultiDataResponse<Project> postProjects(@RequestBody List<Project> projects)
	{
		return projectService.createProjects(projects);
	}

	@GetMapping("/project/{won}")
	public SingleDataResponse<Project> getProject(@PathVariable("won") final int won) throws EmployeeNotFoundException
	{
		return projectService.getProject(won);
	}

	@GetMapping("/projects")
	public MultiDataResponse<Project> getProjects() throws Exception
	{
		return projectService.getProjects();
	}
	
	@PutMapping("/project/{won}")
	public SingleDataResponse<Project> putProject(@PathVariable("won") final int won,@RequestBody Project project) throws EmployeeNotFoundException
	{
		return projectService.updateProject(won,project);
	}

	@DeleteMapping("/project/{won}")
	public SingleDataResponse<Project> deleteProject(@PathVariable("won") final int won) throws EmployeeNotFoundException
	{
		return projectService.cancelProject(won);
	}
}
