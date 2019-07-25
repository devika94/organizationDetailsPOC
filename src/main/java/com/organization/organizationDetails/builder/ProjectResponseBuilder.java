package com.organization.organizationDetails.builder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.organization.organizationDetails.jsonAPI.Data;
import com.organization.organizationDetails.jsonAPI.Relationships;
import com.organization.organizationDetails.jsonAPI.Warnings;
import com.organization.organizationDetails.model.Employee;
import com.organization.organizationDetails.model.Project;
import com.organization.organizationDetails.repository.ProjectRepository;

@Component
public class ProjectResponseBuilder<T> {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	List<String> links=new ArrayList<>();
	
	@Transactional
	public Data transformProject(Project project)
	{
		Data data=new Data<T>(project.getWon(), "project",((T) project), (new Relationships("project","employee","v1")),
				links);
		projectRepository.save(project);
		return data;
	}
	
	public Optional<Project> updateProjectDetails(Project proj,Optional<Project> project)
	{
		if(!(proj.getProjectName()==null || proj.getProjectName().isEmpty()))
					project.get().setProjectName(proj.getProjectName());
		if(!(proj.getStartDate()==null))
			project.get().setStartDate(proj.getStartDate());
		if(!(proj.getEndDate()==null))
			project.get().setEndDate(proj.getEndDate());
		project.get().setDeleted(proj.isDeleted());
		projectRepository.save(project.get());
		return project ;
	}
	/*@Transactional
	public List<Data> transformProjects(List<Project> projects)
	{
		List<Data> listData=new ArrayList<>();
		projects.stream().forEach(project ->listData.add(transformProject(project)));
		return listData;
	}*/
}
