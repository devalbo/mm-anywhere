/**
 * 
 */
package org.mm_anywhere.app;

import java.net.URI;

import javax.swing.SwingUtilities;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import mmcorej.CMMCore;
import mmcorej.DeviceType;

import org.micromanager.acquisition.AcquisitionData;
import org.mm_anywhere.remoteio.MmAnywhereApplication;
import org.mm_anywhere.remoteio.ValidationUtilities;

/**
 * @author ajb
 *
 */
@Path("/dev/{deviceId}/commands/snapImage/")
public class SnapImage {

	@javax.ws.rs.core.Context UriInfo _uri;

	@GET
//	@Produces("text/plain")
//	public String doError() throws Exception {
//		throw new HTTPException(400);
//	}
//
//	@POST
//	@Produces("text/plain")
	public Response execute(final @PathParam("deviceId") String deviceId) throws Exception {
		final CMMCore core = MmAnywherePlugin.getMmCore();

		ValidationUtilities.validateDeviceType(deviceId, DeviceType.CameraDevice);

		final String originalCamera = core.getCameraDevice();
		final String fileName = Long.toString(System.currentTimeMillis()) + ".tiff";
		final String saveLocation = MmAnywhereApplication.getApp().getImagesDir() + fileName;
		String imageUri = MmAnywhereApplication.getApp().getImagesHostingRoot() + fileName;
		System.out.println("IMAGE SAVE LOCATION: " + saveLocation);
		System.out.println("IMAGE URI: " + imageUri);

		SwingUtilities.invokeAndWait(new Runnable() {

			@Override
			public void run() {
				try {
					core.setCameraDevice(deviceId);
					core.snapImage();
					System.out.println("WAITING FOR SYSTEM");
					core.waitForSystem();
					System.out.println("GETTING IMAGE");
					Object img = core.getImage();
					System.out.println("IMAGE GOTTEN");
					long width = core.getImageWidth();
					long height = core.getImageHeight();
					core.setCameraDevice(originalCamera);
					
					// save as TIFF
//					AcquisitionData.saveImageFile(saveLocation, img, (int)width, (int)height);
					System.out.println("IMAGE SAVED");
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			
		});

		return Response.temporaryRedirect(new URI(imageUri)).build();

//		return imageUri;
	}
}
