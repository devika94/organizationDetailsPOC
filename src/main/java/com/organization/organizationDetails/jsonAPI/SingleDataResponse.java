package com.organization.organizationDetails.jsonAPI;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.organization.organizationDetails.jsonAPIErrors.Errors;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;



@JsonInclude(JsonInclude.Include.NON_EMPTY)
//@JsonSerialize
@JsonPropertyOrder({"meta","data", "errors"})
@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class SingleDataResponse<T> {
	 //@JsonProperty
    private List<Warnings> meta;
	
	//@JsonProperty("data")
	private Data data;
	
	//@JsonProperty("errors")
	private List<Errors> errors;

	
}
