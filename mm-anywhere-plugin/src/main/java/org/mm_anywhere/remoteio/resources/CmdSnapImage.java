/**
 * 
 */
package org.mm_anywhere.remoteio.resources;

import ij.ImagePlus;
import ij.io.FileSaver;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;
import ij.process.ShortProcessor;

import java.net.URI;

import javax.swing.SwingUtilities;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.mm_anywhere.remoteio.MmAnywhereApplication;
import org.mm_anywhere.remoteio.ValidationUtilities;

import mmcorej.CMMCore;
import mmcorej.DeviceType;

/**
 * @author ajb
 *
 */
@Path("/devices/{deviceId}/commands/snapImage/")
public class CmdSnapImage {

	@Context 
	UriInfo _uri;
	
	@Context
	CMMCore _mmCore;


//	@GET
//	@Produces("text/plain")
//	public String doError() throws Exception {
//		throw new HTTPException(400);
//	}

	@GET
	@Produces("text/plain")
	public Response execute(final @PathParam("deviceId") String deviceId) throws Exception {
		ValidationUtilities.validateDeviceType(deviceId, DeviceType.CameraDevice);

		final String originalCamera = _mmCore.getCameraDevice();
		final String fileName = Long.toString(System.currentTimeMillis()) + ".jpg";
		final String saveLocation = MmAnywhereApplication.getApp().getImagesDir() + fileName;
		String imageUri = MmAnywhereApplication.getApp().getImagesHostingRoot() + fileName;
		System.out.println("IMAGE SAVE LOCATION: " + saveLocation);
		System.out.println("IMAGE URI: " + imageUri);

		SwingUtilities.invokeAndWait(new Runnable() {

			@Override
			public void run() {
				try {
					_mmCore.setCameraDevice(deviceId);
					_mmCore.snapImage();
					System.out.println("WAITING FOR SYSTEM");
					_mmCore.waitForSystem();
					System.out.println("GETTING IMAGE");
					Object img = _mmCore.getImage();
					System.out.println("IMAGE GOTTEN");
					long width = _mmCore.getImageWidth();
					long height = _mmCore.getImageHeight();
					_mmCore.setCameraDevice(originalCamera);
					
					long byteDepth = _mmCore.getBytesPerPixel();
					ImageProcessor ip = null;
					if (byteDepth == 1) {
					   ip  = new ByteProcessor((int)width, (int)height);
					   ip.setPixels(img);
					} else if (byteDepth==16) {
					   ip = new ShortProcessor((int)width, (int)height);
					   ip.setPixels(img);
					} else {
//					   console.message("Unknown byte depth.");
					   return;
					}
					ImagePlus imp = new ImagePlus(fileName, ip);
					FileSaver fs = new FileSaver(imp);
					fs.saveAsJpeg(saveLocation);
					
					System.out.println("IMAGE SAVED");
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			
		});

		return Response.temporaryRedirect(new URI(imageUri)).build();
	}
	
}
