package org.mm_anywhere.remoteio;

import java.io.File;

import com.sun.research.ws.wadl.Application;

public class MmAnywhereApplication extends Application {

	private String _staticDirRoot;
	private String _staticHostingRoot;
	private static MmAnywhereApplication _app;

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