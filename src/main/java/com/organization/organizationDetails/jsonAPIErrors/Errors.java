package com.organization.organizationDetails.jsonAPIErrors;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Errors {

	  @JsonProperty("id")
	  private  int id;
	  
	  @JsonProperty("status")
	   private HttpStatus httpStatusCode;

	    @JsonProperty("detail")
	    private String detail;

	    @JsonProperty("source")
	    private String source;
	  
}
