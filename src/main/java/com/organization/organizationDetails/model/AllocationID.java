package com.organization.organizationDetails.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AllocationID implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -252339062590342594L;
	private int empID;
	private int won;
	private Date startDate;
	private Date endDate;
	private String role;
	
	
}
