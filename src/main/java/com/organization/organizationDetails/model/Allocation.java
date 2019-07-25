package com.organization.organizationDetails.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="ALLOCATION")
@IdClass(AllocationID.class)

public class Allocation{


	@Id
	@Column(name="EMPLOYEE_ID")
	private int empID;
	
	@Id
	@Column(name="WON")
	private int won;

	@Id
	@Column(name="START_DATE")
	private Date startDate;
	
	@Id
	@Column(name="END_DATE")
	private Date endDate;
	
	@Id
	@Column(name="ROLE")
	private String role;
	
	@JsonIgnore
	//@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Column(name="DELETED")
	private boolean isDeleted;
	/*@Column(name="ALLOCATION_HISTORY")
	private List<AllocationHistory> allocationHistory;*/
}