package org.mm_anywhere.app;

import java.lang.reflect.Type;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

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
					if (ve == null) {
						System.out.println("Ready to start velocity");
						ve = new VelocityEngine();
						ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "mm_anywhere");
						ve.setProperty("mm_anywhere.resource.loader.class", 
								"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
						ve.setProperty("mm_anywhere.resource.loader.path",
							"/org/mm_anywhere/ui");
						ve.setApplicationAttribute(
								"javax.servlet.ServletContext", servletContext);
						try {
							ve.init();
							System.out.println("Velocity is loaded");
						} catch (Exception e) {
							System.out.println("Error when initializing Velocity" + e);
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
