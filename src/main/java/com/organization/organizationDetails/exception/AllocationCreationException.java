package com.organization.organizationDetails.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllocationCreationException extends Exception 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5314127818982818160L;

	private final String exceptionMessage;
	private final int won;
	public AllocationCreationException(String exceptionMessage, int won) {
		super();
		this.exceptionMessage = exceptionMessage;
		this.won = won;
	}
	
/*	public AllocationCreationException(String message,int id) {
		
		super(message);
		System.out.println("inside exception 2");
		// TODO Auto-generated constructor stub
	}*/

 
}
