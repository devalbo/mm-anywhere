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

import org.ratatosk.mmrest.data.MmAnywhere.MmConfigGroup;
import org.ratatosk.mmrest.data.MmAnywhere.MmConfigGroupPreset;
import org.ratatosk.mmrest.data.MmAnywhere.MmConfigGroups;
import org.ratatosk.mmrest.data.MmAnywhere.MmDeviceCommand;
import org.ratatosk.mmrest.data.MmAnywhere.MmDeviceListing;
import org.ratatosk.mmrest.data.MmAnywhere.MmDeviceProperty;
import org.ratatosk.mmrest.data.MmAnywhere.MmDevicesListing;

/**
 * @author ajb
 *
 */
public class MmDevalboConverter {

  public static MmDevicesListing getMmDevicesListing() {
    List<MmDeviceListing> mmDevices = new ArrayList<MmDeviceListing>();
    for (String device : MmCoreUtils.getStrVectorIterator(MmAnywherePlugin.getMmCore().getLoadedDevices())) {
      try {
        mmDevices.add(getMmDeviceListing(device));
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    
    return MmDevicesListing.newBuilder().
    	addAllMmDeviceListings(mmDevices).
    	build();
  }
  
  public static MmDeviceListing getMmDeviceListing(String device) throws Exception {
    DeviceType deviceType = MmAnywherePlugin.getMmCore().getDeviceType(device);
    MmDeviceListing d = MmDeviceListing.newBuilder().
    	setDeviceId(device).
    	setDeviceLabel(device).
    	setDeviceType(deviceType.toString()).
    	setDeviceUrl(MmAnywherePlugin.makeDeviceUrl(device)).
    	addAllDeviceProperties(getAllMmProperties(device)).
    	addAllDeviceCommands(getAllMmCommands(device)).
    	build();
    return d;
  }

  private static List<MmDeviceProperty> getAllMmProperties(String device) throws Exception {
    StrVector propertyNames = MmAnywherePlugin.getMmCore().getDevicePropertyNames(device);
    List<MmDeviceProperty> mmDeviceProperties = new ArrayList<MmDeviceProperty>();
    for (String propertyName : propertyNames) {
      mmDeviceProperties.add(getMmPropertyListing(device, propertyName));
    }
    return mmDeviceProperties;
  }
  
  private static List<MmDeviceCommand> getAllMmCommands(String deviceId) throws Exception {
  	List<MmDeviceCommand> mmDeviceCommands = new ArrayList<MmDeviceCommand>();
  	DeviceType deviceType = MmAnywherePlugin.getMmCore().getDeviceType(deviceId);
  	if (deviceType == DeviceType.CameraDevice) {
  		MmDeviceCommand snapImageCommand = MmDeviceCommand.newBuilder().
  			setCommandId("snapImage").
  			setCommandLabel("Snap Image").
  			setCommandUrl(MmAnywherePlugin.makeCommandUrl(deviceId, "snapImage")).
				build();
  		mmDeviceCommands.add(snapImageCommand);

  	} else if (deviceType == DeviceType.StateDevice) {
//  		handler = new StateDeviceCommandHandler(application, deviceId);
  	}
  	return mmDeviceCommands;
  }

  public static MmDeviceProperty getMmPropertyListing(String device, String property) throws Exception {
    PropertyType propertyType = MmAnywherePlugin.getMmCore().getPropertyType(device, property);
    String propertyValue = MmAnywherePlugin.getMmCore().getProperty(device, property);
    
    MmDeviceProperty p = MmDeviceProperty.newBuilder().
    	setPropertyId(property).
    	setPropertyLabel(property).
    	setPropertyType(propertyType.toString()).
    	setPropertyValue(propertyValue).
    	setPropertyUrl(MmAnywherePlugin.makePropertyUrl(device, property)).
    	build();
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
	      		List<String> configPropertyDevices= new ArrayList<String>();
	      		List<String> configPropertyLabels = new ArrayList<String>();
	      		List<String> configPropertyValues = new ArrayList<String>();
	      		for (int i = 0; i < cfgData.size(); i++) {
	      			PropertySetting setting = cfgData.getSetting(i);
	      			configPropertyDevices.add(setting.getDeviceLabel());
	      			configPropertyLabels.add(setting.getPropertyName());
	      			configPropertyValues.add(setting.getPropertyValue());
	      		}
	      		MmConfigGroupPreset mmConfigGroupPreset = MmConfigGroupPreset.newBuilder().
	      			setPresetId(config).
	      			setPresetLabel(config).
	      			addAllPresetPropertyDevices(configPropertyDevices).
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
      		setConfigGroupUrl(MmAnywherePlugin.makeConfigGroupUrl(cfgGroup)).
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
