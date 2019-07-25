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

public class RelationID implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 8286290936595818533L;
	private int manEmpID;
	private int empID;
	private Date startDate;
	private Date endDate;
	
	
}
