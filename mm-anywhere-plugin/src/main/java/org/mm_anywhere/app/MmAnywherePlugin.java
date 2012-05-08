/**
 * 
 */
package org.mm_anywhere.app;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import mmcorej.CMMCore;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.micromanager.api.MMPlugin;
import org.micromanager.api.ScriptInterface;
import org.micromanager.utils.MMFrame;
import org.mm_anywhere.remoteio.MmAnywhereApplication;

import com.sun.jersey.spi.container.servlet.ServletContainer;


/**
 * @author ajb
 *
 */
public class MmAnywherePlugin extends MMFrame implements MMPlugin {

	public final static String MENU_NAME = "MMRest Plugin";
	public final static String PREF_WWW_RESOURCE_PATH = "www-resource-path";
	public final static String PREF_HOST_PATH = "host-path";
	public final static String PREF_HOST_PORT = "host-port";
	public final static String PREF_AUTO_START_SERVER = "auto-start-server";
	
	public final static String CMD_START_SERVER = "Start server";
	public final static String CMD_STOP_SERVER = "Stop server";

	private static CMMCore _mmCore;

	private static MmAnywherePlugin _mmRestPlugin;

	private Server _webServer;

	private JTextField _wwwResourcePathText;
	private JTextField _hostPathText;
	private JTextField _portText;
	private JButton _startServerButton;
	private JCheckBox _autoStartServerCheckBox;

	protected int _port = 8080;
	protected String _wwwResourcePath = "c:/Users/ajb/workspace-devalbo/mm-anywhere-plugin/www";
	protected String _hostPath = "http://localhost";
	protected boolean _autoStartServer;


	public static CMMCore getMmCore() {
		return _mmCore;
	}

	public MmAnywherePlugin() {
		setTitle("MM Anywhere");
		final Preferences prefs = Preferences.userNodeForPackage(this.getClass());

		_wwwResourcePath = prefs.get(MmAnywherePlugin.PREF_WWW_RESOURCE_PATH, "c:/Users/ajb/workspace-devalbo/mm-anywhere-plugin/www");
		_hostPath = prefs.get(MmAnywherePlugin.PREF_HOST_PATH, "http://localhost");
		_port = prefs.getInt(MmAnywherePlugin.PREF_HOST_PORT, 8080);
		_autoStartServer = prefs.getBoolean(MmAnywherePlugin.PREF_AUTO_START_SERVER, false);

		addWindowListener(new WindowAdapter() {

			public void windowClosing(final WindowEvent e) {
				_wwwResourcePath = _wwwResourcePathText.getText();
				_hostPath = _hostPathText.getText();
				try {
					_port = Integer.parseInt(_portText.getText());
				} catch (Exception ex) {
					_port = 80;
				}
				_autoStartServer = _autoStartServerCheckBox.isSelected();
				
				prefs.put(MmAnywherePlugin.PREF_WWW_RESOURCE_PATH, _wwwResourcePath);
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
		resourcePanel.add(new JLabel("WWW Resource Path:"));
		_wwwResourcePathText = new JTextField(_wwwResourcePath); 
		resourcePanel.add(_wwwResourcePathText);
		dlgPanel.add(resourcePanel);
		
		JPanel hostPanel = new JPanel();
		hostPanel.setLayout(new BoxLayout(hostPanel, BoxLayout.X_AXIS));
		hostPanel.add(new JLabel("WWW Host Path:"));
		_hostPathText = new JTextField(_hostPath);
		hostPanel.add(_hostPathText);
		dlgPanel.add(hostPanel);
		
		JPanel portPanel = new JPanel(); 
		portPanel.setLayout(new BoxLayout(portPanel, BoxLayout.X_AXIS));
		portPanel.add(new JLabel("WWW Host Port:"));
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

		MmAnywhereApplication.init(_wwwResourcePath, _hostPath + ":" + _port);
		
		_webServer = new Server();
		SelectChannelConnector connector = new SelectChannelConnector();
		connector.setPort(_port);
		_webServer.addConnector(connector);

		ServletHolder sh = new ServletHolder(ServletContainer.class);
		sh.setInitParameter("com.sun.jersey.config.property.resourceConfigClass", "com.sun.jersey.api.core.PackagesResourceConfig");
		sh.setInitParameter("com.sun.jersey.config.property.packages", "jetty");
//		sh.setInitParameter("com.sun.jersey.config.property.packages", "org.ratatosk.mmrest;org.devalbo.data.jackson");
		sh.setInitParameter("com.sun.jersey.config.property.packages", "org.mm_anywhere.app;org.mm_anywhere.remoteio;org.devalbo.data.jackson");
		ServletContextHandler jerseyContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
		jerseyContext.addServlet(sh, "/mm/*");

		ResourceHandler resource_handler = new ResourceHandler();
		resource_handler.setDirectoriesListed(true);
		resource_handler.setResourceBase(_wwwResourcePath);
		resource_handler.setDirectoriesListed(false);
		resource_handler.setWelcomeFiles(new String[]{ "index.html" });

		HandlerList handlers = new HandlerList();
		handlers.setHandlers(new Handler[] { resource_handler, jerseyContext, new DefaultHandler() });
		_webServer.setHandler(handlers);

		try {
			_webServer.start();

			_startServerButton.setText(CMD_STOP_SERVER);
			_startServerButton.setActionCommand(CMD_STOP_SERVER);
			
		} catch (Exception e) {
			e.printStackTrace();
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


}
