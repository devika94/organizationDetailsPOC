package com.organization.organizationDetails.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.organization.organizationDetails.model.Allocation;
import com.organization.organizationDetails.model.AllocationID;
import com.organization.organizationDetails.model.Project;

@Repository
public interface AllocationRepository extends JpaRepository<Allocation, AllocationID>{

	public List<Allocation> findByWonAndEmpID(int won,int empID);
	
	public List<Allocation> findByEmpID(int empID);
	
	public List<Allocation> findByEmpIDAndWonAndStartDateAndEndDate(int empID,int won,Date startDate,Date endDate);
}

