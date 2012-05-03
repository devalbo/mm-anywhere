/**
 * 
 */
package org.mm_anywhere.remoteio;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.UriInfo;
import javax.xml.ws.http.HTTPException;

/**
 * @author ajb
 *
 */
@Path("/devices/{deviceId}/commands/snapImage/")
public class SnapImage {

	@javax.ws.rs.core.Context UriInfo _uri;

	@GET
	@Produces("text/plain")
	public String doError() throws Exception {
		throw new HTTPException(400);
	}

//	@POST
//	@Produces("text/plain")
//	public String execute(final @PathParam("deviceId") String deviceId) throws Exception {
//		final CMMCore core = MmCoreUtils.getMmCore();
//
//		ValidationUtilities.validateDeviceType(deviceId, DeviceType.CameraDevice);
//
//		final String originalCamera = core.getCameraDevice();
//		final String fileName = Long.toString(System.currentTimeMillis()) + ".tiff";
//		final String saveLocation = MicroManagerRemoteIoApplication.getApp().getImagesDir() + fileName;
//		String imageUri = MicroManagerRemoteIoApplication.getApp().getImagesHostingRoot() + fileName;
//		System.out.println("IMAGE SAVE LOCATION: " + saveLocation);
//		System.out.println("IMAGE URI: " + imageUri);
//
//		SwingUtilities.invokeAndWait(new Runnable() {
//
//			@Override
//			public void run() {
//				try {
//					core.setCameraDevice(deviceId);
//					core.snapImage();
//					System.out.println("WAITING FOR SYSTEM");
//					core.waitForSystem();
//					System.out.println("GETTING IMAGE");
//					Object img = core.getImage();
//					System.out.println("IMAGE GOTTEN");
//					long width = core.getImageWidth();
//					long height = core.getImageHeight();
//					core.setCameraDevice(originalCamera);
//					
//					// save as TIFF
//					AcquisitionData.saveImageFile(saveLocation, img, (int)width, (int)height);
//					System.out.println("IMAGE SAVED");
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//
//			}
//			
//		});
//
//
//		return imageUri;
//	}
}
