package com.organization.organizationDetails.jsonAPI;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"id", "type", "attributes", "relationships", "links"})
@AllArgsConstructor
@NoArgsConstructor
@lombok.Data
public class Data<T> {

//	@JsonProperty("id")
    private int id;

//    @JsonProperty
    private String type;
    
//    @JsonProperty
    private T attributes;
    
//   @JsonProperty
    private Relationships relationships;
    
//   @JsonProperty
    private List<String> links;
   

	/*public Data(@JsonProperty("id") int id,@JsonProperty("type") String type,
			@JsonProperty("attributes")T attributes,@JsonProperty("relationships") Relationships relationships,
			@JsonProperty("links") List<String> links,@JsonProperty("meta") String meta) {
		super();
		this.id = id;
		this.type = type;
		this.attributes = attributes;
		this.relationships = relationships;
		this.links = links;
		this.meta = meta;
		
	}*/
   public Data(int id)
   {
	   this.id=id;
	   
   }
    }
