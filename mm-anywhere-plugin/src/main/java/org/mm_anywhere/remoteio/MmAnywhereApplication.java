package org.mm_anywhere.remoteio;

import java.io.File;

import mmcorej.CMMCore;

import com.sun.research.ws.wadl.Application;

public class MmAnywhereApplication extends Application {

//	public static final String CORE_ATTRIBUTE = "core";
//	private CMMCore _core;
	private String _staticDirRoot;
	private String _staticHostingRoot;
	private static MmAnywhereApplication _app;

	//	public MicroManagerRemoteIoApplication(CMMCore core, String staticDirRoot, String staticHostingRoot) {  
	//		super();
	//		_core = core;
	//		_staticDirRoot = staticDirRoot;
	//		_staticHostingRoot = staticHostingRoot;
	//	}  

//	private MicroManagerRemoteIoApplication(CMMCore core, String staticDirRoot, String staticHostingRoot) {
//		super();
////		_core = core;
//		_staticDirRoot = staticDirRoot;
//		_staticHostingRoot = staticHostingRoot;
//	}

	public MmAnywhereApplication(String staticDir, String hostingPath) {
		_staticDirRoot = staticDir;
		_staticHostingRoot = hostingPath;
	}

	public static void init(String staticDirRoot, String staticHostingRoot) {
		_app = new MmAnywhereApplication(staticDirRoot, staticHostingRoot);
	}  

	public static MmAnywhereApplication getApp() {
		return _app;
	}

//	/** 
//	 * Creates a root Restlet that will receive all incoming calls. 
//	 */  
//	@Override  
//	public synchronized Restlet createRoot() {  
//
//		// Create a router Restlet that defines routes.
//		Router router = new Router(getContext());
//
//		// Defines a route for the resource "list of devices"  
//		Route route = router.attach("/devices", DevicesResource.class);
//		route.getTemplate().setMatchingMode(Template.MODE_EQUALS);
//		route = router.attach("/devices/", DevicesResource.class);
//		route.getTemplate().setMatchingMode(Template.MODE_EQUALS);
//
//		// Defines a route for the resource "device"
//		route = router.attach("/devices/{deviceName}", DeviceResource.class);
//		route.getTemplate().setMatchingMode(Template.MODE_EQUALS);
//		route = router.attach("/devices/{deviceName}/", DeviceResource.class);
//		route.getTemplate().setMatchingMode(Template.MODE_EQUALS);
//
//		route = router.attach("/devices/{deviceName}/properties", DeviceProperties.class);
//		route.getTemplate().setMatchingMode(Template.MODE_EQUALS);
//		route = router.attach("/devices/{deviceName}/properties/", DeviceProperties.class);
//		route.getTemplate().setMatchingMode(Template.MODE_EQUALS);
//
//		route = router.attach("/devices/{deviceName}/commands", DeviceCommands.class);
//		route.getTemplate().setMatchingMode(Template.MODE_EQUALS);
//		route = router.attach("/devices/{deviceName}/commands/", DeviceCommands.class);
//		route.getTemplate().setMatchingMode(Template.MODE_EQUALS);
//
//		route = router.attach("/devices/{deviceName}/properties/{propertyName}", DeviceProperty.class);
//		route.getTemplate().setMatchingMode(Template.MODE_EQUALS);
//		route = router.attach("/devices/{deviceName}/properties/{propertyName}/", DeviceProperty.class);
//		route.getTemplate().setMatchingMode(Template.MODE_EQUALS);
//
//		route = router.attach("/devices/{deviceName}/commands/{commandName}", DeviceCommand.class);
//		route.getTemplate().setMatchingMode(Template.MODE_EQUALS);
//		route = router.attach("/devices/{deviceName}/commands/{commandName}/", DeviceCommand.class);
//		route.getTemplate().setMatchingMode(Template.MODE_EQUALS);
//
//		return router;  
//	}  

	public String getStaticDirRoot() {
		return _staticDirRoot;
	}

	public String getImagesDir() {
		return _staticDirRoot + "/images/";
	}

	public String getVideosDir() {
		return _staticDirRoot + "/videos/";
	}

	public String getImagesHostingRoot() {
		return _staticHostingRoot + "/images/";
	}

	public String getVideosHostingRoot() {
		return _staticHostingRoot + "/videos/";
	}

	public void initStaticDirs() {
		File[] staticDirs = new File[] {
				new File(getStaticDirRoot()),
				new File(getImagesDir()),
				new File(getVideosDir()),
		};

		for (File dir : staticDirs) {
			if (!dir.exists() || !dir.isDirectory()) {
				if (!dir.mkdirs()) {
					System.err.println("Unable to create directory: " + dir);
				}
			}
		}
	}

}  