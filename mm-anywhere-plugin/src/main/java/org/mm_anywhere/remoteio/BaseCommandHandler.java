/**
 * 
 */
package org.mm_anywhere.remoteio;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import mmcorej.CMMCore;

import org.mm_anywhere.app.MmAnywherePlugin;
import org.mm_anywhere.app.MmCoreUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * @author ajb
 *
 */
public abstract class BaseCommandHandler {

//	private MicroManagerRemoteIoApplication _mmRioApp;
	private String _deviceName;

//	public BaseCommandHandler(MicroManagerRemoteIoApplication mmRioApp, String deviceName) {
//		_mmRioApp = mmRioApp;
//		_deviceName = deviceName;
//	}
	
//	public CommandResult handleCommand(String commandName, Representation representation) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
//		for (Method method : getClass().getMethods()) {
//			if (method.getAnnotation(CommandHandler.class) != null && 
//					method.getName().equals(commandName)) {
//
//				Object[] args = coerceArgs(method, representation);
//				if (args != null) {
//					Object returnValue = method.invoke(this, args);
//					return new CommandResult(method, returnValue);
//				}
//			}
//		}
//		
//		return null;
//	}
	
	public String getDeviceName() {
		return _deviceName;
	}
	
	public CMMCore getCore() {
		return MmAnywherePlugin.getMmCore();
	}
	
//	public MicroManagerRemoteIoApplication getMmRioApp() {
//		return _mmRioApp;
//	}
	
//	public static Object[] coerceArgs(Method method, Representation representation) {
//
//		try {
//			if (method.getParameterTypes().length == 0) {
//				return new Object[]{};
//			}
//			
//			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
//			domFactory.setNamespaceAware(true); // never forget this!
//			DocumentBuilder builder = domFactory.newDocumentBuilder();
//			Document doc = builder.parse(representation.getStream());
//
//			XPathFactory factory = XPathFactory.newInstance();
//			XPath xpath = factory.newXPath();
//
//			// make sure we have values for each parameter
//			List<String> argValues = new ArrayList<String>();
//			for (int index = 0; index < method.getParameterTypes().length; index++) {
//				XPathExpression expr = xpath.compile("//parameter[name='arg" + 
//						Integer.toString(index) + "']/value/text()");
//				Object result = expr.evaluate(doc, XPathConstants.NODESET);
//
//				NodeList nodes = (NodeList) result;
//				if (nodes.getLength() != 1) {
//					throw new RioException(Integer.toString(nodes.getLength()) + " values provided for arg" + Integer.toString(index) + ". One required.");
//				}
//
//				String value = nodes.item(0).getNodeValue();
//				argValues.add(value);
//			}
//			
//			// make sure we have the proper number of parameters
//			if (argValues.size() != method.getParameterTypes().length) {
//				throw new RioException(argValues.size() + " parameters provided. " + 
//						Integer.toString(method.getParameterTypes().length) + " required.");
//			}
//			
//			// try to coerce everything properly
//			Object[] coerced = new Object[method.getParameterTypes().length];
//			int index = 0;
//			for (Class<?> type : method.getParameterTypes()) {
//				String argStr = argValues.get(index);
//				Object arg = RemoteIoTypes.convertStringtoRioType(argStr, type);
//				coerced[index] = arg;
//				index++;
//			}
//			
//			return coerced;
//			
//		} catch (RioException e) {
//				throw e;
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RioException(e.getLocalizedMessage());
//		}
//
//	}

	
}