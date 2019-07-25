package com.organization.organizationDetails.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectNotFoundException extends Exception 

{
	/**
	 * 
	 */
	private static final long serialVersionUID = 353018963073372715L;
	private final String exceptionMessage;
	private final int won;
	public ProjectNotFoundException(String exceptionMessage, int won) {
		super();
		this.exceptionMessage = exceptionMessage;
		this.won = won;
	}
	
	

	
  

 
}
