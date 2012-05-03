/**
 * 
 */
package org.mm_anywhere.remoteio;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import mmcorej.CMMCore;
import mmcorej.DeviceType;

import org.mm_anywhere.app.MmCoreUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author ajb
 *
 */
public class RepresentationUtilities {

	public static Element getDevicePropertiesElement(String deviceName, CMMCore core, Document doc, String uriRoot) throws Exception {
		Element properties = doc.createElement("properties");

		for (String propertyName : MmCoreUtils.getStrVectorIterator(core.getDevicePropertyNames(deviceName))) {
			Element propertyNode = doc.createElement("property");

			Element id = doc.createElement("id");
			id.setTextContent(propertyName);
			propertyNode.appendChild(id);

			Element eltUri = doc.createElement("uri");
			if (!uriRoot.endsWith("/")) {
				uriRoot += "/";
			}
			eltUri.appendChild(doc.createTextNode(uriRoot + "properties/" + propertyName));
			propertyNode.appendChild(eltUri);

			id.setTextContent(propertyName);
			propertyNode.appendChild(id);

			Element eltType = doc.createElement("type");
			eltType.setTextContent(RemoteIoTypes.getRioTypeForMmType(
					core.getPropertyType(deviceName, propertyName)));
			propertyNode.appendChild(eltType);

			Element eltSettable = doc.createElement("settable");
			eltSettable.setTextContent(Boolean.toString(!core.isPropertyReadOnly(deviceName, propertyName)));
			propertyNode.appendChild(eltSettable);

			Element eltTypeMetadata = doc.createElement("type-metadata");
			addPropertyMetadataElements(core, deviceName, propertyName, doc, eltTypeMetadata);
			propertyNode.appendChild(eltTypeMetadata);

			properties.appendChild(propertyNode);
		}

		return properties;
	}

	private static void addPropertyMetadataElements(CMMCore core, String devId, String propName, Document d, Element eltTypeMetadata) throws Exception {

		String rioType = RemoteIoTypes.getRioTypeForMmType(core.getPropertyType(devId, propName));
		if ((rioType == RemoteIoTypes.RIO_FLOAT || rioType == RemoteIoTypes.RIO_INTEGER) && 
				core.hasPropertyLimits(devId, propName)) 
		{
			Element lowerLimit = d.createElement("lowerlimit");
			lowerLimit.setTextContent(Double.toString(core.getPropertyLowerLimit(devId, propName)).toString());

			Element upperLimit = d.createElement("upperlimit");
			upperLimit.setTextContent(Double.toString(core.getPropertyUpperLimit(devId, propName)).toString());

			eltTypeMetadata.appendChild(lowerLimit);
			eltTypeMetadata.appendChild(upperLimit);
		}
	}

//	public static Element getDeviceCommandsElement(String deviceName, MicroManagerRemoteIoApplication mmRioApp, 
//			Document doc, String uriRoot) throws Exception 
//			{
//
//		List<Element> commands = new ArrayList<Element>();
//		Element commandsNode = doc.createElement("commands");
//
//		String commandsUriRoot = uriRoot;
//		if (!commandsUriRoot.endsWith("/")) {
//			commandsUriRoot += "/";
//		}
//
//		if (DeviceType.CameraDevice == MmCoreUtils.getMmCore().getDeviceType(deviceName)) {
//			commands.addAll(getCommandElements(doc, new CameraCommandHandler(mmRioApp, deviceName), commandsUriRoot));
//		} else if (DeviceType.StateDevice == MmCoreUtils.getMmCore().getDeviceType(deviceName)) {
//			commands.addAll(getCommandElements(doc, new StateDeviceCommandHandler(mmRioApp, deviceName), commandsUriRoot));
//		}
//
//		for (Element command : commands) {
//			commandsNode.appendChild(command);
//		}
//
//		return commandsNode;
//			}

//	private static List<Element> getCommandElements(Document doc, BaseCommandHandler provider, String commandsUriRoot) {
//		assert commandsUriRoot.endsWith("/commands/");
//
//		List<Element> commandList = new ArrayList<Element>();
//
//		// for now, require each command to have a unique name; don't worry about method signatures at this point
//		List<Method> commandHandlerMethods = new ArrayList<Method>();
//		for (Method method : provider.getClass().getMethods()) {
//			if (method.getAnnotation(CommandHandler.class) != null) {
//				for (Method cmd : commandHandlerMethods) {
//					if (method.getName().equals(cmd.getName())) {
//						throw new RioException("Multiple commands named '" + method.getName() + 
//								"' found for device '" + provider.getDeviceName() + "'");
//					}
//				}
//				commandHandlerMethods.add(method);
//			}
//		}
//
//		for (Method method : provider.getClass().getMethods()) {
//			if (method.getAnnotation(CommandHandler.class) != null) {
//				Element command = doc.createElement("command");
//				String methodName = method.getName();
//				Element commandName = doc.createElement("name");
//				Element commandUri = doc.createElement("uri");
//				commandName.setTextContent(methodName);
//				commandUri.setTextContent(commandsUriRoot + methodName);
//				command.appendChild(commandName);
//				command.appendChild(commandUri);
//
//				Element commandParameters = doc.createElement("parameters");
//				int i = 0;
//				for (Class<?> type : method.getParameterTypes()) {
//					Element commandParameter = doc.createElement("parameter");
//					Element parameterName = doc.createElement("name");
//					Element parameterType = doc.createElement("type");
//					Element parameterDefaultValue = doc.createElement("default");
//
//					parameterName.setTextContent("arg" + Integer.toString(i));
//					parameterType.setTextContent(RemoteIoTypes.getRioTypeForClass(type));
//
//					commandParameter.appendChild(parameterName);
//					commandParameter.appendChild(parameterType);
//					commandParameter.appendChild(parameterDefaultValue);
//					commandParameters.appendChild(commandParameter);
//					i++;
//				}
//				command.appendChild(commandParameters);
//
//				Element commandReturnValues = doc.createElement("returnValues");
//				Element commandReturnValue = doc.createElement("returnValue");
//				Element commandReturnValueType = doc.createElement("type");
//				Element commandReturnValueName = doc.createElement("name");
//
//				commandReturnValueName.setTextContent(method.getAnnotation(CommandHandler.class).returnValueName());
//				commandReturnValueType.setTextContent(RemoteIoTypes.getRioTypeForClass(method.getReturnType()));
//				commandReturnValue.appendChild(commandReturnValueName);
//				commandReturnValue.appendChild(commandReturnValueType);
//				commandReturnValues.appendChild(commandReturnValue);
//				command.appendChild(commandReturnValues);
//
//				commandList.add(command);
//			}
//		}
//
//		return commandList;		
//	}

}
