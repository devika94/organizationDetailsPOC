package com.organization.organizationDetails.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="PROJECT")
public class Project{

	@Id
	@Column(name="WON")
	private int won;
	
	@Column(name="PORJECT_NAME")
	private String projectName;
	
	@Column(name="START_DATE")
	private Date startDate;
	
	@Column(name="END_DATE")
	private Date endDate;
	

	@Column(name="DELETED")
	@JsonIgnore
	//@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private boolean isDeleted;
}
