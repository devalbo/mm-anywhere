/**
 * 
 */
package org.mm_anywhere.app;

import java.util.ArrayList;
import java.util.List;

import mmcorej.Configuration;
import mmcorej.DeviceType;
import mmcorej.PropertySetting;
import mmcorej.PropertyType;
import mmcorej.StrVector;

import org.devalbo.data.jackson.Command;
import org.devalbo.data.jackson.Device;
import org.devalbo.data.jackson.Devices;
import org.devalbo.data.jackson.Property;
import org.ratatosk.mmrest.data.MmAnywhere.MmConfigGroup;
import org.ratatosk.mmrest.data.MmAnywhere.MmConfigGroupPreset;
import org.ratatosk.mmrest.data.MmAnywhere.MmConfigGroups;

/**
 * @author ajb
 *
 */
public class MmDevalboConverter {

  public static Devices getMmDevicesListing() {
    List<Device> mmDevices = new ArrayList<Device>();
    for (String device : MmCoreUtils.getStrVectorIterator(MmAnywherePlugin.getMmCore().getLoadedDevices())) {
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
    DeviceType deviceType = MmAnywherePlugin.getMmCore().getDeviceType(device);
    Device d = new Device(device, 
        device, 
        deviceType.toString(),
        MmAnywherePlugin.makeDeviceUrl(device),
        getAllMmProperties(device),
        getAllMmCommands(device));
    return d;
  }

  private static List<Property> getAllMmProperties(String device) throws Exception {
    StrVector propertyNames = MmAnywherePlugin.getMmCore().getDevicePropertyNames(device);
    List<Property> mmDeviceProperties = new ArrayList<Property>();
    for (String propertyName : propertyNames) {
      mmDeviceProperties.add(getMmPropertyListing(device, propertyName));
    }
    return mmDeviceProperties;
  }
  
  private static List<Command> getAllMmCommands(String deviceId) throws Exception {
  	List<Command> mmDeviceCommands = new ArrayList<Command>();
  	DeviceType deviceType = MmAnywherePlugin.getMmCore().getDeviceType(deviceId);
  	if (deviceType == DeviceType.CameraDevice) {
  		Command snapImageCommand = new Command("snapImage", 
  				"Snap Image", MmAnywherePlugin.makeCommandUrl(deviceId, "snapImage"));
  		mmDeviceCommands.add(snapImageCommand);

  	} else if (deviceType == DeviceType.StateDevice) {
//  		handler = new StateDeviceCommandHandler(application, deviceId);
  	}
  	return mmDeviceCommands;
  }

  public static Property getMmPropertyListing(String device, String property) throws Exception {
    PropertyType propertyType = MmAnywherePlugin.getMmCore().getPropertyType(device, property);
    String propertyValue = MmAnywherePlugin.getMmCore().getProperty(device, property);
    
    Property p = new Property(property, 
        property, 
        propertyType.toString(), 
        propertyValue,
        MmAnywherePlugin.makePropertyUrl(device, property));
    return p;
  }
  
  public static void setPropertyValue(String device, String property, String value) throws Exception {
  	MmAnywherePlugin.getMmCore().setProperty(device, property, value);
  }
  
  public static MmConfigGroups getAllMmConfigurations() {
  	List<MmConfigGroup> mmConfigGroups = new ArrayList<MmConfigGroup>();
    for (String cfgGroup : MmCoreUtils.getStrVectorIterator(MmAnywherePlugin.getMmCore().getAvailableConfigGroups())) {
      try {
      	List<MmConfigGroupPreset> mmConfigGroupPresets = new ArrayList<MmConfigGroupPreset>();
      	for(String config : MmCoreUtils.getStrVectorIterator(MmAnywherePlugin.getMmCore().getAvailableConfigs(cfgGroup))) {
	      		Configuration cfgData = MmAnywherePlugin.getMmCore().getConfigData(cfgGroup, config);
	      		List<String> configPropertyLabels = new ArrayList<String>();
	      		List<String> configPropertyValues = new ArrayList<String>();
	      		for (int i = 0; i < cfgData.size(); i++) {
	      			PropertySetting setting = cfgData.getSetting(i);
	      			configPropertyLabels.add(setting.getPropertyName());
	      			configPropertyValues.add(setting.getPropertyValue());
	      		}
	      		MmConfigGroupPreset mmConfigGroupPreset = MmConfigGroupPreset.newBuilder().
	      			setPresetId(config).
	      			setPresetLabel(config).
	      			addAllPresetPropertyLabels(configPropertyLabels).
	      			addAllPresetPropertyValues(configPropertyValues).
	      			build();
	      		mmConfigGroupPresets.add(mmConfigGroupPreset);
      	}
      	String currentPreset = MmAnywherePlugin.getMmCore().getCurrentConfig(cfgGroup);
				MmConfigGroup mmConfigGroup = MmConfigGroup.newBuilder().
      		setConfigGroupId(cfgGroup).
      		setConfigGroupLabel(cfgGroup).
      		setCurrentPreset(currentPreset).
      		addAllConfigGroupPresets(mmConfigGroupPresets).
      		build();
				mmConfigGroups.add(mmConfigGroup);
				
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    
    return MmConfigGroups.newBuilder().
    	addAllMmConfigGroups(mmConfigGroups).
    	build();

  }
  
}
