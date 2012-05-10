/**
 * 
 */
package org.mm_anywhere.remoteio.resources;

import java.net.URI;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import mmcorej.CMMCore;

import org.mm_anywhere.app.MmAnywhereCore;

/**
 * @author ajb
 *
 */
@Path("/app/core")
public class Core {

	@Context
	MmAnywhereCore _mmAnywhereCore;

	@GET 
	@Path("/snapImage")
	public Response snapImage() throws Exception {
		String imageUri = _mmAnywhereCore.snapImage();
		return Response.temporaryRedirect(new URI(imageUri)).build();
	}

	@POST
	@Path("/exposure")
	public Response setExposure(@FormParam("exposure") double exposure) {
		CMMCore core = _mmAnywhereCore.getMMCore();

		try {
//			if (!isLiveModeOn()) {
				core.setExposure(exposure);
				System.out.println("SETTING EXPOSURE: " + exposure);
//			} else {
//				liveModeTimer_.stop();
//				core.setExposure(NumberUtils.displayStringToDouble(exposure);
//				try {
//					liveModeTimer_.begin();
//				} catch (Exception e) {
//					ReportingUtils.showError("Couldn't restart live mode");
//					liveModeTimer_.stop();
//				}
//			}

			// Return the new exposure time
			double newExposure = core.getExposure();

			return Response.ok(Double.toString(newExposure)).build();

		} catch (Exception exp) {
			return Response.serverError().build();
		}

	}
	
	@POST
	@Path("/exposure2")
	public Response setExposure2() {
		CMMCore core = _mmAnywhereCore.getMMCore();

		try {
//			if (!isLiveModeOn()) {
				System.out.println("SETTING EXPOSURE2");
//			} else {
//				liveModeTimer_.stop();
//				core.setExposure(NumberUtils.displayStringToDouble(exposure);
//				try {
//					liveModeTimer_.begin();
//				} catch (Exception e) {
//					ReportingUtils.showError("Couldn't restart live mode");
//					liveModeTimer_.stop();
//				}
//			}

			// Return the new exposure time
			double newExposure = core.getExposure();

			return Response.ok(Double.toString(newExposure)).build();

		} catch (Exception exp) {
			return Response.serverError().build();
		}

	}

}
