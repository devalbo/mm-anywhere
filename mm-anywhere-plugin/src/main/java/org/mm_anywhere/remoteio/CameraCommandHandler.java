/**
 * 
 */
package org.mm_anywhere.remoteio;



/**
 * @author ajb
 *
 */
public class CameraCommandHandler extends BaseCommandHandler {

//	public CameraCommandHandler(MicroManagerRemoteIoApplication mmRioApp, String deviceName) {
//		super(mmRioApp, deviceName);
//	}
//
//	/** Returns the URI of the snapped image. */
//	@CommandHandler(returnValueName="image-url")
//	public String snapImage() throws Exception {
//		String originalCamera = getCore().getCameraDevice();
//		getCore().setCameraDevice(getDeviceName());
//
//		getCore().snapImage();
//		Object img = getCore().getImage();
//		long width = getCore().getImageWidth();
//		long height = getCore().getImageHeight();
//
//		getCore().setCameraDevice(originalCamera);
//
//		// save as TIFF
//		String fileName = Long.toString(System.currentTimeMillis()) + ".tiff";
//		String saveLocation = getMmRioApp().getImagesDir() + fileName;
//		String imageUri = getMmRioApp().getImagesHostingRoot() + fileName;
//		System.out.println("IMAGE SAVE LOCATION: " + saveLocation);
//		System.out.println("IMAGE URI: " + imageUri);
//		AcquisitionData.saveImageFile(saveLocation, img, (int)width, (int)height);
//
//		// save as JPG
//		//		String jpgFileName = Long.toString(System.currentTimeMillis()) + ".jpg";
//		//		String jpgSaveLocation = getMmRioApp().getImagesDir() + jpgFileName;
//		//		String jpgImageUri = getMmRioApp().getImagesHostingRoot() + jpgFileName;
//		//		System.out.println("IMAGE SAVE LOCATION: " + jpgSaveLocation);
//		//		System.out.println("IMAGE URI: " + jpgImageUri);
//		//		saveImageFileAsJpg(jpgSaveLocation, img, (int)width, (int)height);
//
//		return imageUri;
//	}

//	static public boolean saveImageFileAsJpg(String fname, Object img, int width, int height) {
//		ImageProcessor ip;
//		if (img instanceof byte[]) {
//			ip = new ByteProcessor(width, height);
//			ip.setPixels((byte[])img);
//		}
//		else if (img instanceof short[]) {
//			ip = new ShortProcessor(width, height);
//			ip.setPixels((short[])img);
//		}
//		else
//			return false;
//
//		ImagePlus imp = new ImagePlus(fname, ip);
//		FileSaver fs = new FileSaver(imp);
//		return fs.saveAsJpeg(fname);
//	}

}
