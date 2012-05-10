/**
 * 
 */
package org.mm_anywhere.remoteio.resources;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import mmcorej.CMMCore;

import org.mm_anywhere.app.MmAnywhereCore;

import com.sun.research.ws.wadl.Resource;

/**
 * @author ajb
 *
 */
@Path("/configurations/{cfgGroupId}/")
public class Configuration extends Resource {

	@Context
	MmAnywhereCore _mmAnywhereCore;

	@GET
	@Produces("text/plain")
	public String getConfigurationGroupPreset(@PathParam("cfgGroupId") String cfgGroupId) throws Exception {
		try {
			return _mmAnywhereCore.getMMCore().getCurrentConfig(cfgGroupId);
		} catch (Exception e) {
			e.printStackTrace();
		}  

		return null;
	}

	@POST
	@Produces("text/plain")
	public Response setConfigurationGroupPreset(
			@PathParam("cfgGroupId") String cfgGroupId,
			@FormParam("value") String value) 
	throws Exception 
	{
		CMMCore core = _mmAnywhereCore.getMMCore();
		core.setConfig(cfgGroupId, value);
		core.waitForConfig(cfgGroupId, value);
		return Response.ok(value).build();
	}

}
