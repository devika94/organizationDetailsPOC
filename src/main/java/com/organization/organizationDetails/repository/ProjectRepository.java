package com.organization.organizationDetails.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.organization.organizationDetails.model.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
	
	public Project findByWonAndIsDeleted(int won,boolean deleted);
	

}
