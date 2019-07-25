package com.organization.organizationDetails.jsonAPI;

import java.util.List;

import com.fasterxml.jackson.annotation.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Relationship {
   
    @JsonIgnore
    private String resourceType;
    
   // @JsonProperty("related")
    private String related;

}
