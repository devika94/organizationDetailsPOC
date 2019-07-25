package com.organization.organizationDetails.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Relation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(RelationID.class)
public class Relation {

	@Id
	@Column(name="MANAGER_ID")
	private int manEmpID;
	
	@Id
	@Column(name="EMPLOYEE_ID")
	private int empID;
	
	@Id
	@Column(name="START_DATE")
	private Date startDate;
	
	@Id
	@Column(name="END_DATE")
	private Date endDate;
	
	@Column(name="WON")
	private int won;
	
}
