/**
 * 
 */
package org.mm_anywhere.app;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.devalbo.data.jackson.Device;
import org.devalbo.data.jackson.Devices;
import org.devalbo.data.jackson.Property;

/**
 * @author ajb
 *
 */
@Path("/dev")
public class MmAnywhereDevServer {

	// The Java method will process HTTP GET requests
	@GET 
	public Devices getDevices() {
		Devices devices = MmDevalboConverter.getMmDevicesListing();
		return devices;
	}

	@GET @Path("/{deviceId}")
	public Device getDevice(@PathParam("deviceId") String deviceId) {
		try {
	    return MmDevalboConverter.getMmDeviceListing(deviceId);
    } catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
    }
    return null;
	}

	@GET @Path("/{deviceId}/{propertyId}")
	public Property getProperty(@PathParam("deviceId") String deviceId, @PathParam("propertyId") String propertyId) { 
		try {
	    return MmDevalboConverter.getMmPropertyListing(deviceId, propertyId);
    } catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
    }
    return null;
	}
	
	@POST @Path("/{deviceId}/{propertyId}")
	@Consumes("application/x-www-form-urlencoded")
	public void post(@PathParam("deviceId") String deviceId, @PathParam("propertyId") String propertyId, @FormParam("propVal") String propVal) {
		System.out.println("Setting [" + deviceId + "][" + propertyId + "] to " + propVal);
		try {
	    MmDevalboConverter.setPropertyValue(deviceId, propertyId, propVal);
    } catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
    }
	}

}
