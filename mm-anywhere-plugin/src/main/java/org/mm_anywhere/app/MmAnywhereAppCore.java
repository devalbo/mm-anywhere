/**
 * 
 */
package org.mm_anywhere.app;

import ij.ImagePlus;
import ij.io.FileSaver;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;
import ij.process.ShortProcessor;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Timer;

import javax.swing.SwingUtilities;
import javax.ws.rs.core.StreamingOutput;

import mmcorej.CMMCore;
import mmcorej.DeviceType;
import mmcorej.MMCoreJ;
import mmcorej.StrVector;

import org.micromanager.MMStudioMainFrame;
import org.micromanager.acquisition.LiveModeTimer;
import org.micromanager.utils.ReportingUtils;
import org.mm_anywhere.remoteio.MmAnywhereApplication;

/**
 * Based on logic found in {@link MMStudioMainFrame}
 * 
 * @author ajb
 * 
 */
public class MmAnywhereAppCore extends Timer {

	private LiveModeTimer _liveModeTimer;
	private CMMCore _mmCore;

	public MmAnywhereAppCore(CMMCore mmCore) {
		_mmCore = mmCore;
	}

	public CMMCore getMMCore() {
		return _mmCore;
	}

	public String getMmAnywhereAppCoreUrl() {
		return MmAnywherePlugin.makeAppCoreUrl();
	}

	public boolean isLiveModeOn() {
		return _liveModeTimer != null && _liveModeTimer.isRunning();
	}

	public String snapImage(final String deviceId) {

		final String originalCamera = _mmCore.getCameraDevice();
		final String fileName = Long.toString(System.currentTimeMillis()) + ".jpg";
		final String saveLocation = MmAnywhereApplication.getApp().getImagesDir()
		    + fileName;
		String imageUri = MmAnywhereApplication.getApp().getImagesHostingRoot()
		    + fileName;
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
							ip = new ByteProcessor((int) width, (int) height);
							ip.setPixels(img);
						} else if (byteDepth == 16) {
							ip = new ShortProcessor((int) width, (int) height);
							ip.setPixels(img);
						} else {
							// console.message("Unknown byte depth.");
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
		final String saveLocation = MmAnywhereApplication.getApp().getImagesDir()
		    + fileName;
		String imageUri = MmAnywhereApplication.getApp().getImagesHostingRoot()
		    + fileName;
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
							ip = new ByteProcessor((int) width, (int) height);
							ip.setPixels(img);
						} else if (byteDepth == 16) {
							ip = new ShortProcessor((int) width, (int) height);
							ip.setPixels(img);
						} else {
							// console.message("Unknown byte depth.");
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

	public StreamingOutput getImageStream() {
		// TODO Auto-generated method stub
		return null;
	}

	public int setBinning(int binning) throws Exception {
		try {
			boolean liveRunning = false;
			if (isLiveModeOn()) {
				liveRunning = true;
				enableLiveMode(false);
			}

			if (isCameraAvailable()) {
				_mmCore.setProperty(_mmCore.getCameraDevice(),
				    MMCoreJ.getG_Keyword_Binning(), Integer.toString(binning));
			}

			if (liveRunning) {
				enableLiveMode(true);
			}

			return binning;

		} catch (Exception e) {
			throw e;
		}
	}

	public String getBinning() throws Exception {
		return _mmCore.getProperty(_mmCore.getCameraDevice(),
		    MMCoreJ.getG_Keyword_Binning());
	}

	public List<String> getAllowedBinningValues() throws Exception {
		StrVector binSizes = _mmCore.getAllowedPropertyValues(
		    _mmCore.getCameraDevice(), MMCoreJ.getG_Keyword_Binning());
		return MmCoreUtils.getStrVectorList(binSizes);
	}

	public double setExposure(double exposure) throws Exception {

		try {
			if (!isLiveModeOn()) {
				_mmCore.setExposure(exposure);
			} else {
				_liveModeTimer.stop();
				_mmCore.setExposure(exposure);

				try {
					_liveModeTimer.begin();
				} catch (Exception e) {
					_liveModeTimer.stop();
					throw new Exception("Couldn't restart live mode");
				}
			}

			// Return the new exposure time
			return _mmCore.getExposure();

		} catch (Exception exp) {
			throw exp;
		}

	}

	public double getExposure() throws Exception {
		return _mmCore.getExposure();
	}

	private void enableLiveMode(boolean enable) {
		if (enable == isLiveModeOn()) {
			return;
		}
		if (enable) {
			try {
				if (_mmCore.getCameraDevice().length() == 0) {
					throw new Exception("No camera configured");
				}
				if (_liveModeTimer == null) {
					_liveModeTimer = new LiveModeTimer(33);
				}
				_liveModeTimer.begin();

			} catch (Exception e) {
				ReportingUtils.showError(e);
				_liveModeTimer.stop();
				return;
			}

		} else {
			_liveModeTimer.stop();
		}
	}

	private boolean isCameraAvailable() {
		return null != _mmCore.getCameraDevice();
	}

	public List<String> getAvailableShutters() {
		StrVector shutters = _mmCore
		    .getLoadedDevicesOfType(DeviceType.ShutterDevice);
		return MmCoreUtils.getStrVectorList(shutters);
	}

	public String setShutter(String shutter) throws Exception {
		_mmCore.setShutterDevice(shutter);
		return getShutter();
	}

	public String getShutter() {
		return _mmCore.getShutterDevice();
	}

	public boolean setShutterOpen(boolean open) throws Exception {
		_mmCore.setShutterOpen(open);
		return getShutterOpen();
	}

	public boolean getShutterOpen() throws Exception {
		return _mmCore.getShutterOpen();
	}

	public boolean setAutoShutter(boolean autoShutter) {
		_mmCore.setAutoShutter(autoShutter);
		return getAutoShutter();
	}

	public boolean getAutoShutter() {
		return _mmCore.getAutoShutter();
	}

}
