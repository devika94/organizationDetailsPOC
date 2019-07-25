package com.organization.organizationDetails.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.organization.organizationDetails.model.Employee;
import com.organization.organizationDetails.model.Project;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
	
	public Employee findByEmpIDAndIsDeleted(int empID,boolean deleted);

}
