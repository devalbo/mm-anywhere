package org.mm_anywhere.app;

import java.lang.reflect.Type;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;

import com.sun.jersey.core.spi.component.ComponentContext;
import com.sun.jersey.core.spi.component.ComponentScope;
import com.sun.jersey.spi.inject.Injectable;
import com.sun.jersey.spi.inject.InjectableProvider;

@Provider
public class VelocityResolver implements InjectableProvider<Context, Type> {
	public static String LOGGER_NAME = VelocityResolver.class.getName();

	@Context
	private ServletContext servletContext;

	private VelocityEngine ve;

	public Injectable<VelocityEngine> getInjectable(ComponentContext arg0,
			Context arg1, Type c) {
		if (c.equals(VelocityEngine.class)) {
			return new Injectable<VelocityEngine>() {
				public VelocityEngine getValue() {
					BasicConfigurator.configure();
					Logger log = Logger.getLogger(LOGGER_NAME);
					if (ve == null) {
						log.info("Ready to start velocity");
						ve = new VelocityEngine();
						ve.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS,
										"org.apache.velocity.runtime.log.Log4JLogChute");
						ve.setProperty("runtime.log.logsystem.log4j.logger",
								LOGGER_NAME);
//						ve.setProperty(RuntimeConstants.RESOURCE_LOADER,
//								"webapp");
//						ve.setProperty("webapp.resource.loader.class",
//										"org.apache.velocity.tools.view.servlet.WebappLoader");
//						ve.setProperty("webapp.resource.loader.path",
//								"/WEB-INF/mytemplates/");
						ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "mm_anywhere");
						ve.setProperty("mm_anywhere.resource.loader.class", 
								"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
						ve.setProperty("mm_anywhere.resource.loader.path",
							"/org/mm_anywhere/ui");
						ve.setApplicationAttribute(
								"javax.servlet.ServletContext", servletContext);
						try {
							ve.init();
							log.info("Velocity is loaded");
						} catch (Exception e) {
							log.error("Error when initializing Velocity", e);
						}
					}
					return ve;
				}
			};
		}
		return null;
	}

	public ComponentScope getScope() {
		return ComponentScope.Singleton;
	}
}
