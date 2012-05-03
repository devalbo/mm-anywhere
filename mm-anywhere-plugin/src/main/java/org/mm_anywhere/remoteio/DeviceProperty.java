/**
 * 
 */
package org.mm_anywhere.remoteio;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import mmcorej.CMMCore;

import org.mm_anywhere.app.MmCoreUtils;

import com.sun.research.ws.wadl.Resource;

/**
 * @author ajb
 *
 */
@Path("/devices/{deviceId}/properties/{propertyId}/")
public class DeviceProperty extends Resource {

	@javax.ws.rs.core.Context UriInfo _uri;

	@GET
	@Produces("text/plain")
	public String getDevicePropertyValue(@PathParam("deviceId") String deviceId, @PathParam("propertyId") String propertyId) throws Exception {
		// Generate the right representation according to its media type.  
		CMMCore core = MmCoreUtils.getMmCore();
		try {
			String value = core.getProperty(deviceId, propertyId);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
		}  

		return null;
	}

	@PUT
	@Consumes("text/plain")
	@Produces("text/plain")
	public javax.ws.rs.core.Response setDevicePropertyValue(@PathParam("deviceId") String deviceId, 
			@PathParam("propertyId") String propertyId, String value) 
	throws Exception {
		CMMCore core = MmCoreUtils.getMmCore();
		core.setProperty(deviceId, propertyId, value);
		return Response.ok(value).build();
	}

}
