/**
 * 
 */
package org.mm_anywhere.remoteio;

import mmcorej.DeviceType;

import org.mm_anywhere.app.MmCoreUtils;
import org.mm_anywhere.app.MmAnywherePlugin;

/**
 * @author ajb
 *
 */
public class ValidationUtilities {

	public static void validateDeviceId(String deviceId) {
		if (deviceId == null || deviceId.equals("")) {
			throw new ValidationException("No deviceId provided.");
		}
		
		for (String device : MmCoreUtils.getStrVectorIterator(MmAnywherePlugin.getMmCore().getLoadedDevices())) {
			if (deviceId.equals(device)) {
				return;
			}
		}
		
		throw new ValidationException("No device found for deviceId '" + deviceId + "'.");
	}
	
	public static void validateDeviceType(String deviceId, DeviceType deviceType) {
		validateDeviceId(deviceId);
		try {
			if (MmAnywherePlugin.getMmCore().getDeviceType(deviceId) != deviceType) {
				throw new ValidationException("Device '" + deviceId + "' must be of type " + deviceType + ".");
			}
		} catch (Exception e) {
			throw new ValidationException("Error validating device type: " + e.getLocalizedMessage());
		}
	}
	
}
