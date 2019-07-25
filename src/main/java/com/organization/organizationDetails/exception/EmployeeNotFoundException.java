package com.organization.organizationDetails.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeNotFoundException extends Exception 

{
	/**
	 * 
	 */
	private static final long serialVersionUID = 353018963073372715L;
	private final String exceptionMessage;
	private final int empID;
	public EmployeeNotFoundException(String exceptionMessage, int empID) {
		super();
		this.exceptionMessage = exceptionMessage;
		this.empID = empID;
	}
	
	

	
  

 
}
