package org.mm_anywhere.remoteio;

import java.io.File;

import com.sun.research.ws.wadl.Application;

public class MmAnywhereApplication extends Application {

	private String _hostingDirRoot;
	private String _staticHostingUrlRoot;
	private static MmAnywhereApplication _app;

	public MmAnywhereApplication(String staticDir, String hostingPath) {
		_hostingDirRoot = staticDir;
		_staticHostingUrlRoot = hostingPath;
		initStaticDirs();
	}

	public static void init(String staticDirRoot, String staticHostingRoot) {
		_app = new MmAnywhereApplication(staticDirRoot, staticHostingRoot);
	}  

	public static MmAnywhereApplication getApp() {
		return _app;
	}

	public String getStaticDirRoot() {
		return _hostingDirRoot;
	}

	public String getImagesDir() {
		return _hostingDirRoot + "/images/";
	}

	public String getVideosDir() {
		return _hostingDirRoot + "/videos/";
	}

	public String getImagesHostingRoot() {
		return _staticHostingUrlRoot + "/images/";
	}

	public String getVideosHostingRoot() {
		return _staticHostingUrlRoot + "/videos/";
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