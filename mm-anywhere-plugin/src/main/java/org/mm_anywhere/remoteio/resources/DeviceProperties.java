/**
 * 
 */
package org.mm_anywhere.remoteio.resources;

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

import mmcorej.CMMCore;
import mmcorej.StrVector;

import org.mm_anywhere.app.MmCoreUtils;
import org.mm_anywhere.remoteio.ValidationUtilities;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sun.research.ws.wadl.Resource;

/**
 * @author ajb
 *
 */
@Path("/devices/{deviceId}/properties/")
public class DeviceProperties extends Resource {

	@javax.ws.rs.core.Context UriInfo _uri;

	@GET
	@Produces("text/xml")
	public String getDeviceProperties(@PathParam("deviceId") String deviceId) throws Exception {
		ValidationUtilities.validateDeviceId(deviceId);

		CMMCore core = MmCoreUtils.getMmCore();

		DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
		Document d = docBuilder.newDocument();

		// Generate a DOM document representing the list of  
		// items.  
		Element r = d.createElement("properties");  
		d.appendChild(r);

		StrVector propertyNames = core.getDevicePropertyNames(deviceId);

		for (String propName : MmCoreUtils.getStrVectorIterator(propertyNames)) {
			Element eltProperty = d.createElement("property");  

			Element eltName = d.createElement("name");  
			eltName.appendChild(d.createTextNode(propName));  
			eltProperty.appendChild(eltName);

			Element eltSettable = d.createElement("settable");
			eltSettable.appendChild(d.createTextNode(Boolean.toString(!core.isPropertyReadOnly(deviceId, propName))));
			eltProperty.appendChild(eltSettable);

			Element eltType = d.createElement("type");
			eltType.appendChild(d.createTextNode(core.getPropertyType(deviceId, propName).toString()));  
			eltProperty.appendChild(eltType);  

			r.appendChild(eltProperty);  
		}  
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
