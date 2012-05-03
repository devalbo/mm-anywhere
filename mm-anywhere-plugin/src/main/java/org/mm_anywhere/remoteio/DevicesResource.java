/**
 * 
 */
package org.mm_anywhere.remoteio;

import java.io.StringWriter;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
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

import org.mm_anywhere.app.MmCoreUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sun.research.ws.wadl.Resource;

/**
 * @author ajb
 *
 */
@Path("/devices/")
public class DevicesResource extends Resource {

	@javax.ws.rs.core.Context UriInfo _uri;

	@GET
	@Produces("text/xml")
	public String getDevices() throws Exception {
		CMMCore core = MmCoreUtils.getMmCore();

		// Generate a DOM document representing the list of  
		// items.  
		DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
		Document d = docBuilder.newDocument();

		Element r = d.createElement("devices");  
		d.appendChild(r);
		for (String device : MmCoreUtils.getStrVectorIterator(core.getLoadedDevices())) {
			Element eltItem = d.createElement("device");  

			Element eltName = d.createElement("id");  
			eltName.appendChild(d.createTextNode(device));  
			eltItem.appendChild(eltName);  

			Element eltDescription = d.createElement("type");
			String deviceType = core.getDeviceType(device).toString();
			eltDescription.appendChild(d.createTextNode(deviceType));  
			eltItem.appendChild(eltDescription);

			Element eltRoles = d.createElement("applicationRoles");
			Element eltMmApp = d.createElement("application-MicroManager");
			Element eltMmRole = d.createElement("role");
			eltMmRole.appendChild(d.createTextNode(deviceType));
			eltMmApp.appendChild(eltMmRole);
			eltRoles.appendChild(eltMmApp);
			eltItem.appendChild(eltRoles);

			String uriRoot = _uri.getRequestUri().toString();
			Element eltUri = d.createElement("uri");
			if (!uriRoot.endsWith("/")) {
				uriRoot += "/";
			}
			eltUri.appendChild(d.createTextNode(uriRoot + device));
			eltItem.appendChild(eltUri);

			r.appendChild(eltItem);  
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
