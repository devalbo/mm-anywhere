/**
 * 
 */
package org.mm_anywhere.remoteio.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.mm_anywhere.app.MmDevalboConverter;
import org.mm_anywhere.data.MmAnywhere.MmDeviceListing;
import org.mm_anywhere.data.MmAnywhere.MmDeviceProperty;
import org.mm_anywhere.data.MmAnywhere.MmDevicesListing;

/**
 * @author ajb
 *
 */
@Path("/devices")
public class Devices {

	@GET 
	public MmDevicesListing getDevices() {
		MmDevicesListing devices = MmDevalboConverter.getMmDevicesListing();
		return devices;
	}

	@GET 
	@Path("/{deviceId}")
	public MmDeviceListing getDevice(@PathParam("deviceId") String deviceId) {
		try {
	    return MmDevalboConverter.getMmDeviceListing(deviceId);
    } catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
    }
    return null;
	}

	@GET 
	@Path("/{deviceId}/properties/{propertyId}")
	public MmDeviceProperty getProperty(@PathParam("deviceId") String deviceId, 
			@PathParam("propertyId") String propertyId) 
	{ 
		try {
	    return MmDevalboConverter.getMmPropertyListing(deviceId, propertyId);
    } catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
    }
    return null;
	}
	
	@POST 
	@Path("/{deviceId}/properties/{propertyId}")
	@Consumes("application/x-www-form-urlencoded")
	public void post(@PathParam("deviceId") String deviceId, 
			@PathParam("propertyId") String propertyId, @FormParam("propVal") String propVal) 
	{
		System.out.println("Setting [" + deviceId + "][" + propertyId + "] to " + propVal);
		try {
	    MmDevalboConverter.setPropertyValue(deviceId, propertyId, propVal);
    } catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
    }
	}

}
