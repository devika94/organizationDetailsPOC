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
import com.organization.organizationDetails.service.ProjectService;
import com.organization.organizationDetails.service.RelationService;




@Service
public class ProjectServiceImpl<T> implements ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private ProjectResponseBuilder<T> projectResponseBuilder;

	private List<Data> dataList;
	private List<Warnings> warnings;

	@Override
	public SingleDataResponse<Project> createProject(int won,String projectName, Date startDate, Date endDate) {
		return new SingleDataResponse<>(new ArrayList<Warnings>(),projectResponseBuilder.transformProject(new Project(won, projectName, startDate, endDate, false)),new ArrayList<Errors>());
	}

	@Override
	public MultiDataResponse<Project> createProjects(List<Project> projects) {
		dataList=new ArrayList<>();
		projects.stream().map(project -> 
			 							projectResponseBuilder.transformProject(projectRepository.save(project)))
						.forEach(data -> 
										dataList.add(data)
							); 
		return new MultiDataResponse<Project>(new ArrayList<Warnings>(),dataList,new ArrayList<Errors>());	
	}

	@Override
	public SingleDataResponse<Project> getProject(int won) throws EmployeeNotFoundException 
	{
		Optional<Project> project=projectRepository.findById(won);
		if(!project.isPresent())
			throw new EmployeeNotFoundException("Project not found", won);
		/*{  
			errors.add(new Errors(won, HttpStatus.BAD_REQUEST , "Project not found", "Incorrect project won"));
			return new SingleDataResponse<>(warnings,dataList.stream().reduce(new Data() ,(a,b) -> {a.equals(b); return a;}),errors);
		}*/
		if(project.isPresent() && project.get().isDeleted()==true)
			warnings.add(new Warnings("Project is expired, Project completed"));
		//errors.add(new Errors(won, HttpStatus.BAD_REQUEST , "Project is expired", "Project completed"));
		return new SingleDataResponse<>(warnings,projectResponseBuilder.transformProject(project.get()),new ArrayList<Errors>());	
	}
	
	@Override
	public MultiDataResponse<Project> getProjects()
	{
		dataList=new ArrayList<>();
		projectRepository.findAll().stream().filter(project -> !(project.isDeleted())).forEach(project -> dataList.add(projectResponseBuilder.transformProject(project)));
		return new MultiDataResponse<>(new ArrayList<Warnings>(),dataList,new ArrayList<Errors>());
	}
	
	@Override
	public SingleDataResponse<Project> updateProject(int won,Project proj) throws EmployeeNotFoundException
	{
		Optional<Project> project=projectRepository.findById(won);
		if(!project.isPresent())
			throw new EmployeeNotFoundException("Project not found", won);
		/*{  
			errors.add(new Errors(won, HttpStatus.BAD_REQUEST , "Project not found", "Incorrect project won"));
			return new SingleDataResponse<>(warnings,new Data<>(),errors);
		}*/
		if(project.isPresent() && project.get().isDeleted()==true)	
		{
			warnings.add(new Warnings("Project not found and details can't be updated"));
			return new SingleDataResponse<>(warnings,new Data<>(),new ArrayList<Errors>()) ;
		}
		/*{
			errors.add(new Errors(won, HttpStatus.BAD_REQUEST , "Project not found and details can't be updated", "Incorrect project won"));
			return new SingleDataResponse<>(warnings,new Data<>(),errors) ;
		}*/
		return new SingleDataResponse<>(new ArrayList<Warnings>(),projectResponseBuilder.transformProject(projectResponseBuilder.updateProjectDetails(proj,project).get()),new ArrayList<Errors>());
	}

	@Override
	public SingleDataResponse<Project> cancelProject(int won) throws EmployeeNotFoundException
	{
		Optional<Project> project=projectRepository.findById(won);
		if(!project.isPresent())
			throw new EmployeeNotFoundException("Project not found", won);
		/*{
			errors=new ArrayList<>();
			errors.add(new Errors(won, HttpStatus.BAD_REQUEST , "Project not found", "Incorrect project won"));
			return new SingleDataResponse<>(warnings,new Data<>(),errors) ;
		}*/
		project.get().setDeleted(true);
		projectRepository.save(project.get());
			/*if(!(emp.getEmpExp()==null || emp.getEmpExp().isEmpty()))
					{
						employee.get().setEmpExp(emp.getEmpExp());
					}*/
		return new SingleDataResponse<>(new ArrayList<Warnings>(),new Data<>(project.get().getWon()),new ArrayList<Errors>());	
	}

}