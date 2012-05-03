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
public class Devices implements IJacksonIo {
  
  public Devices() { }
  
  public Devices(List<Device> devs) {
    devices = devs;
  }
  
  @JsonProperty
  public List<Device> devices;

}
