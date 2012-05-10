/**
 * 
 */
package org.mm_anywhere.remoteio.resources;

import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import mmcorej.DeviceType;

import org.mm_anywhere.app.MmAnywhereCore;
import org.mm_anywhere.remoteio.ValidationUtilities;

/**
 * @author ajb
 *
 */
@Path("/devices/{deviceId}/commands/snapImage/")
public class CmdSnapImage {

	@Context 
	UriInfo _uri;
	
	@Context
	MmAnywhereCore _mmAnywhereCore;

	@GET
	@Produces("text/plain")
	public Response execute(final @PathParam("deviceId") String deviceId) throws Exception {
		ValidationUtilities.validateDeviceType(deviceId, DeviceType.CameraDevice);
		
		String imageUri = _mmAnywhereCore.snapImage(deviceId);

		return Response.temporaryRedirect(new URI(imageUri)).build();
	}
	
}
