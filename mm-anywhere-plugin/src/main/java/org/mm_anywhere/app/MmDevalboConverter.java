/**
 * 
 */
package org.mm_anywhere.app;

import java.util.ArrayList;
import java.util.List;

import mmcorej.DeviceType;
import mmcorej.PropertyType;
import mmcorej.StrVector;

import org.devalbo.data.jackson.Command;
import org.devalbo.data.jackson.Device;
import org.devalbo.data.jackson.Devices;
import org.devalbo.data.jackson.Property;

/**
 * @author ajb
 *
 */
public class MmDevalboConverter {

  public static Devices getMmDevicesListing() {
    List<Device> mmDevices = new ArrayList<Device>();
    for (String device : MmCoreUtils.getStrVectorIterator(MmRestPlugin.getMmCore().getLoadedDevices())) {
      try {
        mmDevices.add(getMmDeviceListing(device));
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    return new Devices(mmDevices);
  }
  
  public static Device getMmDeviceListing(String device) throws Exception {
    DeviceType deviceType = MmRestPlugin.getMmCore().getDeviceType(device);
    Device d = new Device(device, 
        device, 
        deviceType.toString(),
        MmRestPlugin.makeDeviceUrl(device),
        getAllMmProperties(device),
        getAllMmCommands(device));
    return d;
  }

  private static List<Property> getAllMmProperties(String device) throws Exception {
    StrVector propertyNames = MmRestPlugin.getMmCore().getDevicePropertyNames(device);
    List<Property> mmDeviceProperties = new ArrayList<Property>();
    for (String propertyName : propertyNames) {
      mmDeviceProperties.add(getMmPropertyListing(device, propertyName));
    }
    return mmDeviceProperties;
  }
  
  private static List<Command> getAllMmCommands(String deviceId) throws Exception {
  	List<Command> mmDeviceCommands = new ArrayList<Command>();
  	DeviceType deviceType = MmRestPlugin.getMmCore().getDeviceType(deviceId);
  	if (deviceType == DeviceType.CameraDevice) {
  		Command snapImageCommand = new Command("snapImage", 
  				"Snap Image", MmRestPlugin.makeCommandUrl(deviceId, "snapImage"));
  		mmDeviceCommands.add(snapImageCommand);

  	} else if (deviceType == DeviceType.StateDevice) {
//  		handler = new StateDeviceCommandHandler(application, deviceId);
  	}
  	return mmDeviceCommands;
  }

  public static Property getMmPropertyListing(String device, String property) throws Exception {
    PropertyType propertyType = MmRestPlugin.getMmCore().getPropertyType(device, property);
    String propertyValue = MmRestPlugin.getMmCore().getProperty(device, property);
    
    Property p = new Property(property, 
        property, 
        propertyType.toString(), 
        propertyValue,
        MmRestPlugin.makePropertyUrl(device, property));
    return p;
  }
  
  public static void setPropertyValue(String device, String property, String value) throws Exception {
  	MmRestPlugin.getMmCore().setProperty(device, property, value);
  }
  
}
