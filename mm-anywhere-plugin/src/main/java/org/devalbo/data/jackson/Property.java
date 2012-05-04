/**
 * 
 */
package org.devalbo.data.jackson;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author ajb
 *
 */
public class Property implements IJacksonIo {

  public Property() { }
  
  public Property(String propId, String propLabel, String propType, String propValue, String propUrl) {
    propertyId = propId;
    propertyLabel = propLabel;
    propertyType = propType;
    propertyValue = propValue;
    propertyUrl = propUrl;
  }
  
  @JsonProperty
  public String propertyId;
  
  @JsonProperty
  public String propertyValue;
  
  @JsonProperty
  public String propertyLabel;
  
  @JsonProperty
  public String propertyType;
  
  @JsonProperty
  public String propertyUrl;

  public String getPropertyId() {
  	return propertyId;
  }
  
  public String getPropertyValue() {
  	return propertyValue;
  }
  
  public String getPropertyLabel() {
  	return propertyLabel;
  }
  
  public String getPropertyType() {
  	return propertyType;
  }
  
  public String getPropertyUrl() {
  	return propertyUrl;
  }
  
}
