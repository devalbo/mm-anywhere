/**
 * 
 */
package org.mm_anywhere.remoteio.resources;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import mmcorej.MMCoreJ;

import org.mm_anywhere.app.MmAnywhereAppCore;

/**
 * @author ajb
 *
 */
@Path("/app/core")
public class Core {

	@Context
	MmAnywhereAppCore _mmAnywhereCore;

	@GET 
	@Path("/snapImage")
	public Response snapImage() throws Exception {
		String imageUri = _mmAnywhereCore.snapImage();
		return Response.temporaryRedirect(new URI(imageUri)).build();
	}
	
	@GET
	@Path("/imageStream")
	public Response getImageStream() {
		StreamingOutput streamingOutput = _mmAnywhereCore.getImageStream();
		
		return Response.ok(streamingOutput).build();
	}

	@GET
	@Path("/exposure")
	public Response getExposure() {
		try {
			double exposure = _mmAnywhereCore.getExposure();
			return Response.ok(Double.toString(exposure)).build();

		} catch (Exception exp) {
			return Response.serverError().build();
		}
	}
	
	@POST
	@Path("/exposure")
	public Response setExposure(@FormParam("exposure") double exposure) {

		try {
			System.out.println("SETTING EXPOSURE: " + exposure);

			// Request new exposure; return what it was set to
			double newExposure = _mmAnywhereCore.setExposure(exposure);

			return Response.ok(Double.toString(newExposure)).build();

		} catch (Exception exp) {
			return Response.serverError().build();
		}
	}

	@GET
	@Path("/binning")
	public Response getBinning() {
		try {
			String camera = _mmAnywhereCore.getMMCore().getCameraDevice();
			String binningStr = _mmAnywhereCore.getMMCore().getProperty(camera, MMCoreJ.getG_Keyword_Binning());
			return Response.ok(binningStr).build();

		} catch (Exception exp) {
			return Response.serverError().build();
		}
	}
	
	@POST
	@Path("/binning")
	public Response setBinning(@FormParam("binning") int binning) {

		try {
			System.out.println("SETTING BINNING: " + binning);

			// Request new binning; return what it was set to
			int newBinning = _mmAnywhereCore.setBinning(binning);

			return Response.ok(Integer.toString(newBinning)).build();

		} catch (Exception exp) {
			return Response.serverError().build();
		}
	}
	
	@POST
	@Path("/open-shutter")
	public Response setShutterOpen(@FormParam("open-shutter") boolean open) throws Exception {
		boolean isOpen = _mmAnywhereCore.setShutterOpen(open);
		return Response.ok(Boolean.toString(isOpen)).build();
	}
	
	@POST
	@Path("/active-shutter")
	public Response setShutter(@FormParam("active-shutter") String shutter) {

		try {
			System.out.println("SETTING SHUTTER: " + shutter);

			// Request new shutter; return what it was set to
			String newShutter = _mmAnywhereCore.setShutter(shutter);

			return Response.ok(newShutter).build();

		} catch (Exception exp) {
			return Response.serverError().build();
		}
	}
	
	@POST
	@Path("/auto-shutter")
	public Response setAutoShutter(@FormParam("auto-shutter") boolean autoshutter) {

		try {
			System.out.println("SETTING AUTO-SHUTTER: " + autoshutter);

			// Request new auto-shutter; return what it was set to
			boolean newAutoShutter = _mmAnywhereCore.setAutoShutter(autoshutter);

			return Response.ok(Boolean.toString(newAutoShutter)).build();

		} catch (Exception exp) {
			return Response.serverError().build();
		}
	}

}
