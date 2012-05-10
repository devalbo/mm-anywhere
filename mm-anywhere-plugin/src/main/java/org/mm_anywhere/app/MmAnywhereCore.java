/**
 * 
 */
package org.mm_anywhere.app;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;

import ij.ImagePlus;
import ij.io.FileSaver;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;
import ij.process.ShortProcessor;

import javax.swing.SwingUtilities;
import javax.ws.rs.core.Response;

import org.mm_anywhere.remoteio.MmAnywhereApplication;

import mmcorej.CMMCore;

/**
 * @author ajb
 *
 */
public class MmAnywhereCore {

	private CMMCore _mmCore;

	public MmAnywhereCore(CMMCore mmCore) {
		_mmCore = mmCore;
	}

	public CMMCore getMMCore() {
		return _mmCore;
	}
	
	public String getMmAnywhereCoreUrl() {
		return MmAnywherePlugin.makeAppCoreUrl(); 
	}

	public String snapImage(final String deviceId) {

		final String originalCamera = _mmCore.getCameraDevice();
		final String fileName = Long.toString(System.currentTimeMillis()) + ".jpg";
		final String saveLocation = MmAnywhereApplication.getApp().getImagesDir() + fileName;
		String imageUri = MmAnywhereApplication.getApp().getImagesHostingRoot() + fileName;
		System.out.println("IMAGE SAVE LOCATION: " + saveLocation);
		System.out.println("IMAGE URI: " + imageUri);

		try {
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
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return imageUri;
	}
	
	public String snapImage() {

		final String fileName = Long.toString(System.currentTimeMillis()) + ".jpg";
		final String saveLocation = MmAnywhereApplication.getApp().getImagesDir() + fileName;
		String imageUri = MmAnywhereApplication.getApp().getImagesHostingRoot() + fileName;
		System.out.println("IMAGE SAVE LOCATION: " + saveLocation);
		System.out.println("IMAGE URI: " + imageUri);

		try {
			SwingUtilities.invokeAndWait(new Runnable() {

				@Override
				public void run() {
					try {
						_mmCore.snapImage();
						System.out.println("WAITING FOR SYSTEM");
						_mmCore.waitForSystem();
						System.out.println("GETTING IMAGE");
						Object img = _mmCore.getImage();
						System.out.println("IMAGE GOTTEN");
						long width = _mmCore.getImageWidth();
						long height = _mmCore.getImageHeight();

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
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return imageUri;
	}

}
