/**
 * 
 */
package org.mm_anywhere.app;

import java.util.ArrayList;
import java.util.List;

import mmcorej.DeviceType;
import mmcorej.PropertyType;
import mmcorej.StrVector;

import org.ratatosk.mmrest.data.MmAnywhere.MmDeviceListing;
import org.ratatosk.mmrest.data.MmAnywhere.MmDeviceProperty;
import org.ratatosk.mmrest.data.MmAnywhere.MmDevicesListing;

/**
 * @author ajb
 *
 */
public class MmProtoConverter {

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
    return MmDevicesListing.newBuilder().addAllMmDeviceListings(mmDevices).build();
  }

  private static MmDeviceListing getMmDeviceListing(String device) throws Exception {
    DeviceType deviceType = MmAnywherePlugin.getMmCore().getDeviceType(device);
    MmDeviceListing deviceListing = MmDeviceListing.newBuilder().
      setDeviceId(device).
      setDeviceUrl("http://" + device).
      setDeviceLabel(device).
      setDeviceType(deviceType.toString()).
      addAllDeviceProperties(getAllMmProperties(device)).
      build();
    return deviceListing;
  }

  private static List<MmDeviceProperty> getAllMmProperties(String device) throws Exception {
    StrVector propertyNames = MmAnywherePlugin.getMmCore().getDevicePropertyNames(device);
    List<MmDeviceProperty> mmDeviceProperties = new ArrayList<MmDeviceProperty>();
    for (String propertyName : propertyNames) {
      mmDeviceProperties.add(getMmPropertyListing(device, propertyName));
    }
    return mmDeviceProperties;
  }

  private static MmDeviceProperty getMmPropertyListing(String device, String property) throws Exception {
    PropertyType propertyType = MmAnywherePlugin.getMmCore().getPropertyType(device, property);
    String propertyValue = MmAnywherePlugin.getMmCore().getProperty(device, property);
    
    MmDeviceProperty mmDeviceProperty = MmDeviceProperty.newBuilder().
      setPropertyId(property).
      setPropertyLabel(device).
      setPropertyUrl("http://" + device + "/" + property).
      setPropertyType(propertyType.toString()).
      setPropertyValue(propertyValue).
      build();
    return mmDeviceProperty;
  }

}
