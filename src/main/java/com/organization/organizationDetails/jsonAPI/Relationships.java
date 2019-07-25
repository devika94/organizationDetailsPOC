package com.organization.organizationDetails.jsonAPI;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NoArgsConstructor
@AllArgsConstructor
public class Relationships {
	
	/*@JsonProperty("relatiosnhips")
	private Relationship relatiosnhips;*/
	
    @JsonIgnore
    private String resourceType;
    
    //@JsonProperty("related")
    private String related;
	
	//@JsonProperty("version")
	private String version;
}
