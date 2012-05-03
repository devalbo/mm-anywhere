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
public class Command {

  public Command() { }
  
  public Command(String cmdId, String cmdLabel, String cmdUrl) {
    commandId = cmdId;
    commandLabel = cmdLabel;
    commandUrl = cmdUrl;
  }
  
  @JsonProperty
  public String commandId;
  
  @JsonProperty
  public String commandLabel;
  
  @JsonProperty
  public String commandUrl;
  
  @JsonProperty
  public List<CmdParameter> commandParameters;
  
}
