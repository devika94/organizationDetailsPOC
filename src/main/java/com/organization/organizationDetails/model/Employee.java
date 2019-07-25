package com.organization.organizationDetails.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

@Table(name="EMPLOYEE")
public class Employee{
	
	@Id
	@Column(name="EMPLOYEE_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int empID;
	
	@Column(name="EMPLOYEE_NAME")
	private String empName;
	
	@Column(name="EMPLOYEE_ROLE")
	private String empRole;
	
	@Column(name="EMPLOYEE_EXPERIENCE")
	private String empExp;

	@Column(name="TAGGED")
	private boolean isTagged;
	
	@Column(name="MANAGER")
	private boolean isManager;
	
	@Column(name="DELETED")
	@JsonIgnore
	//@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private boolean isDeleted;
	
}
