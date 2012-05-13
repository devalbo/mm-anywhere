package org.mm_anywhere.app;

import java.lang.reflect.Type;

import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.sun.jersey.core.spi.component.ComponentContext;
import com.sun.jersey.core.spi.component.ComponentScope;
import com.sun.jersey.spi.inject.Injectable;
import com.sun.jersey.spi.inject.InjectableProvider;

@Provider
public class MmAnywhereCoreResolver implements InjectableProvider<Context, Type> {
	public static String LOGGER_NAME = MmAnywhereCoreResolver.class.getName();

	private MmAnywhereAppCore mmCore;

	public Injectable<MmAnywhereAppCore> getInjectable(ComponentContext arg0,
			Context arg1, Type c) {
		if (c.equals(MmAnywhereAppCore.class)) {
			return new Injectable<MmAnywhereAppCore>() {
				public MmAnywhereAppCore getValue() {
					BasicConfigurator.configure();
					Logger log = Logger.getLogger(LOGGER_NAME);
					if (mmCore == null) {
						log.info("Getting MmCore");
						mmCore = MmAnywherePlugin.getMmAnywhereAppCore();
					}
					return mmCore;
				}
			};
		}
		return null;
	}

	public ComponentScope getScope() {
		return ComponentScope.Singleton;
	}
}
