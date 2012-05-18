/**
 * 
 */
package org.mm_anywhere.app;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import mmcorej.CMMCore;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.micromanager.api.MMPlugin;
import org.micromanager.api.ScriptInterface;
import org.micromanager.utils.MMFrame;
import org.mm_anywhere.remoteio.MmAnywhereApplication;

import com.sun.jersey.api.core.ClassNamesResourceConfig;
import com.sun.jersey.spi.container.servlet.ServletContainer;


/**
 * @author ajb
 *
 */
public class MmAnywherePlugin extends MMFrame implements MMPlugin {

  public static String menuName = "MmAnywhere Plugin";
  public static String tooltipDescription = "Web enabled Micro-Manager";

	public final static String MENU_NAME = "MmAnywhere Plugin";
	public final static String PREF_WWW_HOSTING_DATA_PATH = "www-hosting-data-dir";
	public final static String PREF_HOST_PATH = "host-path";
	public final static String PREF_HOST_PORT = "host-port";
	public final static String PREF_AUTO_START_SERVER = "auto-start-server";
	
	public final static String CMD_START_SERVER = "Start server";
	public final static String CMD_STOP_SERVER = "Stop server";

	private static CMMCore _mmCore;
	private static MmAnywhereAppCore _mmAnywhereCore;

	private static MmAnywherePlugin _mmRestPlugin;

	private Server _webServer;

	private JTextField _wwwHostingDirectoryPathText;
	private JTextField _hostPathText;
	private JTextField _portText;
	private JButton _startServerButton;
	private JCheckBox _autoStartServerCheckBox;

	protected int _port = 8080;
	protected String _wwwHostingDataDirectoryPath = ".";
	protected String _hostPath = "http://localhost";
	protected boolean _autoStartServer;


	public static CMMCore getMmCore() {
		return _mmCore;
	}
	
	public static MmAnywhereAppCore getMmAnywhereAppCore() {
		return _mmAnywhereCore;
	}

	public MmAnywherePlugin() {
		setTitle("MM Anywhere");
		final Preferences prefs = Preferences.userNodeForPackage(this.getClass());

		File userDirectory = new File(System.getProperty("user.home"));
    File mmAnywhereDir = new File(userDirectory, ".mmanywhere");
    
		_wwwHostingDataDirectoryPath = prefs.get(MmAnywherePlugin.PREF_WWW_HOSTING_DATA_PATH, mmAnywhereDir.getAbsolutePath());
		_hostPath = prefs.get(MmAnywherePlugin.PREF_HOST_PATH, "http://localhost");
		_port = prefs.getInt(MmAnywherePlugin.PREF_HOST_PORT, 8080);
		_autoStartServer = prefs.getBoolean(MmAnywherePlugin.PREF_AUTO_START_SERVER, false);

		addWindowListener(new WindowAdapter() {

			public void windowClosing(final WindowEvent e) {
				_wwwHostingDataDirectoryPath = _wwwHostingDirectoryPathText.getText();
				_hostPath = _hostPathText.getText();
				try {
					_port = Integer.parseInt(_portText.getText());
				} catch (Exception ex) {
					_port = 80;
				}
				_autoStartServer = _autoStartServerCheckBox.isSelected();
				
				prefs.put(MmAnywherePlugin.PREF_WWW_HOSTING_DATA_PATH, _wwwHostingDataDirectoryPath);
				prefs.put(MmAnywherePlugin.PREF_HOST_PATH, _hostPath);
				prefs.putInt(MmAnywherePlugin.PREF_HOST_PORT, _port);
				prefs.putBoolean(MmAnywherePlugin.PREF_AUTO_START_SERVER, _autoStartServer);
				try {
	        prefs.flush();
        } catch (BackingStoreException e1) {
	        // TODO Auto-generated catch block
	        e1.printStackTrace();
        }
			}
		});

		JPanel dlgPanel = new JPanel();
		dlgPanel.setLayout(new BoxLayout(dlgPanel, BoxLayout.Y_AXIS));
//		dlgPanel.setLayout(new GridLayout(0, 2));

		JPanel resourcePanel = new JPanel();
		resourcePanel.setLayout(new BoxLayout(resourcePanel, BoxLayout.X_AXIS));
		resourcePanel.add(new JLabel("WWW Hosting Directory:"));
		_wwwHostingDirectoryPathText = new JTextField(_wwwHostingDataDirectoryPath); 
		resourcePanel.add(_wwwHostingDirectoryPathText);
		dlgPanel.add(resourcePanel);
		
		JPanel hostPanel = new JPanel();
		hostPanel.setLayout(new BoxLayout(hostPanel, BoxLayout.X_AXIS));
		hostPanel.add(new JLabel("WWW Host Path:"));
		_hostPathText = new JTextField(_hostPath);
		hostPanel.add(_hostPathText);
		dlgPanel.add(hostPanel);
		
		JPanel portPanel = new JPanel(); 
		portPanel.setLayout(new BoxLayout(portPanel, BoxLayout.X_AXIS));
		portPanel.add(new JLabel("WWW Server Port:"));
		_portText = new JTextField(Integer.toString(_port));
		portPanel.add(_portText);
		dlgPanel.add(portPanel);
		
		_startServerButton = new JButton(CMD_START_SERVER);
		_startServerButton.setActionCommand(CMD_START_SERVER);
		_startServerButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (_startServerButton.getActionCommand().equals(CMD_START_SERVER)) {
					startWebServer();
				} else if (_startServerButton.getActionCommand().equals(CMD_STOP_SERVER)) {
					stopWebServer();
				}
			}
		});
		dlgPanel.add(_startServerButton);
		
		_autoStartServerCheckBox = new JCheckBox("Auto-start server", _autoStartServer); 
		dlgPanel.add(_autoStartServerCheckBox);
		
		add(dlgPanel);
		setPreferredSize(new Dimension(500, 150));
		pack();
	}
	
	@Override
	public void dispose() {
		stopWebServer();
	}

	@Override
	public void setApp(ScriptInterface app) {
		_mmCore = app.getMMCore();
		_mmAnywhereCore = new MmAnywhereAppCore(_mmCore);
		_mmRestPlugin = this;

		if (_autoStartServer) {
			startWebServer();
		}
	}

	@Override
	public void configurationChanged() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getInfo() {
		System.out.println("Plugin getInfo()");
		return null;
	}

	@Override
	public String getVersion() {
		System.out.println("Plugin getVersion()");
		return null;
	}

	@Override
	public String getCopyright() {
		System.out.println("Plugin getCopyright()");
		return null;
	}

	private void startWebServer() {

		MmAnywhereApplication.init(_wwwHostingDataDirectoryPath, _hostPath + ":" + _port);
		
		_webServer = new Server();
		SelectChannelConnector connector = new SelectChannelConnector();
		connector.setPort(_port);
		_webServer.addConnector(connector);

		ServletHolder sh = new ServletHolder(ServletContainer.class);
//		sh.setInitParameter("com.sun.jersey.config.property.resourceConfigClass", 
//				PackagesResourceConfig.class.getCanonicalName());
//		sh.setInitParameter(PackagesResourceConfig.PROPERTY_PACKAGES, 
//				"org.mm_anywhere.app;org.mm_anywhere.remoteio.resources");
		
		sh.setInitParameter("com.sun.jersey.config.property.resourceConfigClass", 
				ClassNamesResourceConfig.class.getCanonicalName());
		sh.setInitParameter(ClassNamesResourceConfig.PROPERTY_CLASSNAMES, 
			  "org.mm_anywhere.app.UiResource;" +
			  "org.mm_anywhere.remoteio.resources.Devices;" +
			  "org.mm_anywhere.remoteio.resources.Core;" +
			  "org.mm_anywhere.remoteio.resources.Configuration;" +
			  "org.mm_anywhere.remoteio.resources.CmdSnapImage;" +
			  "org.mm_anywhere.app.VelocityResolver;" +
//			  "org.mm_anywhere.app.ProtobufWriter;" +
			  "org.mm_anywhere.app.MmAnywhereCoreResolver;"
//			  "org.mm_anywhere.app.ProtobufReader;"
				);

		
//	sh.setInitParameter("com.sun.jersey.config.property.packages", 
//			"jetty");

		ServletContextHandler jerseyContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
		jerseyContext.addServlet(sh, "/mm/*");
		
		ResourceHandler resource_handler = new ResourceHandler();
//		resource_handler.setDirectoriesListed(true);
		resource_handler.setResourceBase(_wwwHostingDataDirectoryPath);
		resource_handler.setDirectoriesListed(false);
//		resource_handler.setWelcomeFiles(new String[]{ "index.html" });

		AbstractHandler defaultHandler = new AbstractHandler() {
			
			@Override
			public void handle(String target, Request baseRequest,
			    HttpServletRequest request, HttpServletResponse response)
			    throws IOException, ServletException 
	    {
				response.encodeRedirectURL("/mm/ui/acquisition");
				baseRequest.setHandled(true);
			}
		};
		
		HandlerList handlers = new HandlerList();
//		handlers.setHandlers(new Handler[] { resource_handler, jerseyContext, new DefaultHandler() });
		handlers.setHandlers(new Handler[] { jerseyContext, resource_handler, defaultHandler });
		_webServer.setHandler(handlers);

		try {
			_webServer.start();

			_startServerButton.setText(CMD_STOP_SERVER);
			_startServerButton.setActionCommand(CMD_STOP_SERVER);
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
			for (StackTraceElement ste : e.getStackTrace()) {
				JOptionPane.showMessageDialog(null, ste);
			}
			e.printStackTrace();
		}
		
		try {
	    sh.getServlet();
    } catch (ServletException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
    }

	}

	private void stopWebServer() {
		System.out.println("Stopping web server");
		try {
			_webServer.stop();
			_startServerButton.setText(CMD_START_SERVER);
			_startServerButton.setActionCommand(CMD_START_SERVER);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String getHostPath() {
		return _hostPath + ":" + _port + "/";
	}

	public static String makeDeviceUrl(String deviceId) {
		return _mmRestPlugin.getHostPath() + "mm/devices/" + deviceId;
	}

	public static String makePropertyUrl(String deviceId, String propertyId) {
		return _mmRestPlugin.getHostPath() + "mm/devices/" + deviceId + "/properties/" + propertyId;
	}
	
	public static String makeCommandUrl(String deviceId, String commandId) {
		return _mmRestPlugin.getHostPath() + "mm/devices/" + deviceId + "/commands/" + commandId;
	}
	
	public static String makeConfigGroupUrl(String configGroupId) {
		return _mmRestPlugin.getHostPath() + "mm/configurations/" + configGroupId;
	}
	
	public static String makeAppCoreUrl() {
		return _mmRestPlugin.getHostPath() + "mm/app/core";
	}


}
