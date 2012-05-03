/**
 * 
 */
package org.devalbo.data.jackson;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author ajb
 *
 */
public class Device implements IJacksonIo {
  
  public Device() { }
  
  public Device(String devId, String devLabel, String devType, String devUrl, 
  		List<Property> devProperties, List<Command> devCommands) 
  {
    deviceId = devId;
    deviceLabel = devLabel;
    deviceType = devType;
    deviceUrl = devUrl;
    deviceProperties = devProperties;
    deviceCommands = devCommands;
  }
  
  @JsonProperty
  public String deviceId;
  
  @JsonProperty
  public String deviceLabel;
  
  @JsonProperty
  public String deviceType;
  
  @JsonProperty
  public String deviceUrl;
  
  @JsonProperty
  public List<Property> deviceProperties;
  
  @JsonProperty
  public List<Command> deviceCommands;
  
  public String getDeviceLabel() {
  	return deviceLabel;
  }

}
