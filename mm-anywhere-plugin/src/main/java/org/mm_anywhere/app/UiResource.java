package org.mm_anywhere.app;

import java.io.InputStream;
import java.io.StringWriter;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

@Path("/ui")
public class UiResource {
	
	private static final String TEMPLATE_PATH = "/org/mm_anywhere/ui/";
	
	public static String LOGGER_NAME = UiResource.class.getName();

	@Context
	VelocityEngine velocity;

	public UiResource() {
	}

	@GET
	@Path("/devices")
	public String getDevicesUi() {
		Logger log = Logger.getLogger(LOGGER_NAME);
		if (velocity != null) {
			log.info("Found Velocity Engine");
		} else {
			log.error("Velocity Engine not found");
		}
		VelocityContext context = new VelocityContext();
		StringWriter sw = null;
		try {
			context.put("devices", MmDevalboConverter.getMmDevicesListing());
			Template template = velocity.getTemplate(TEMPLATE_PATH + "devices.vm");
			sw = new StringWriter();
			template.merge(context, sw);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return sw.toString();
	}
	
	@GET
	@Path("/configurations")
	public String getConfigurationUi() {
		Logger log = Logger.getLogger(LOGGER_NAME);
		if (velocity != null) {
			log.info("Found Velocity Engine");
		} else {
			log.error("Velocity Engine not found");
		}
		VelocityContext context = new VelocityContext();
		StringWriter sw = null;
		try {
			context.put("configurations", MmDevalboConverter.getAllMmConfigurations().getMmConfigGroupsList());
			Template template = velocity.getTemplate(TEMPLATE_PATH + "configurations.vm");
			sw = new StringWriter();
			template.merge(context, sw);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return sw.toString();
	}
	
	@GET
	@Path("/acquisition")
	public String getAcquisitionUi() {
		Logger log = Logger.getLogger(LOGGER_NAME);
		if (velocity != null) {
			log.info("Found Velocity Engine");
		} else {
			log.error("Velocity Engine not found");
		}
		VelocityContext context = new VelocityContext();
		StringWriter sw = null;
		try {
			Template template = velocity.getTemplate(TEMPLATE_PATH + "acquisition.vm");
			sw = new StringWriter();
			template.merge(context, sw);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return sw.toString();
	}
	
	@GET
	@Path("/css/{cssresource}")
	public InputStream getCssResource(@PathParam("cssresource") String cssResource) {
		return getClass().getResourceAsStream(TEMPLATE_PATH + "css/" + cssResource);
	}
	
	@GET
	@Path("/js/{jsresource}")
	@Produces("application/javascript")
	public InputStream getJsResource(@PathParam("jsresource") String jsResource) {
		return getClass().getResourceAsStream(TEMPLATE_PATH + "js/" + jsResource);
	}
	
}
