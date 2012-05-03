/**
 * 
 */
package org.mm_anywhere.remoteio;

import java.io.StringWriter;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.UriInfo;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sun.research.ws.wadl.Resource;

/**
 * @author ajb
 *
 */
@Path("/devices/{deviceId}/commands/")
public class DeviceCommands extends Resource {

	@javax.ws.rs.core.Context UriInfo _uri;

	@GET
	@Produces("text/xml")
	public String getDeviceCommands(@PathParam("deviceId") String deviceId) throws Exception {
		DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
		Document d = docBuilder.newDocument();

//		Element commands = RepresentationUtilities.getDeviceCommandsElement(
//				deviceId, MicroManagerRemoteIoApplication.getApp(), d, _uri.getRequestUri().toString());
		Element commands = null;
		d.appendChild(commands);
		d.normalizeDocument();

		//set up a transformer
		TransformerFactory transfac = TransformerFactory.newInstance();
		Transformer trans = transfac.newTransformer();
		trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		trans.setOutputProperty(OutputKeys.INDENT, "yes");

		//create string from xml tree
		StringWriter sw = new StringWriter();
		StreamResult result = new StreamResult(sw);
		DOMSource source = new DOMSource(d);
		trans.transform(source, result);
		String xmlString = sw.toString();

		return xmlString;  
	}

}
