/**
 * Copyright (c) 2014 openHAB UG (haftungsbeschraenkt) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.cups.internal;

import static org.openhab.binding.cups.CupsBindingConstants.*;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.openhab.binding.cups.CupsBindingConstants;
import org.openhab.binding.cups.handler.CupsBridgeHandler;
import org.openhab.binding.cups.handler.CupsPrinterHandler;
import org.openhab.binding.cups.internal.discovery.CupsDeviceDiscoveryService;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.eclipse.smarthome.config.core.Configuration;
import org.eclipse.smarthome.config.discovery.DiscoveryService;
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.ThingUID;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandlerFactory;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;

/**
 * The {@link CupsHandlerFactory} is responsible for creating things and thing
 * handlers.
 * 
 * @author Tobias Braeutigam - Initial contribution
 */
public class CupsHandlerFactory extends BaseThingHandlerFactory {
	private Logger logger = LoggerFactory.getLogger(CupsHandlerFactory.class);
	
	private Map<CupsBridgeHandler,ServiceRegistration<?>> discoveryServiceReg = new HashMap<CupsBridgeHandler,ServiceRegistration<?>>();

	@Override
	public boolean supportsThingType(ThingTypeUID thingTypeUID) {
		return CupsBindingConstants.SUPPORTED_TYPES_UIDS.contains(thingTypeUID);
	}

	@Override
	public Thing createThing(ThingTypeUID thingTypeUID,
			Configuration configuration, ThingUID thingUID, ThingUID bridgeUID) {
		logger.debug("createThing({},{},{},{})",thingTypeUID,configuration,thingUID,bridgeUID);
		if (CupsBindingConstants.SUPPORTED_BRIDGE_TYPES_UIDS
				.contains(thingTypeUID)) {
			logger.debug("creating bridge {}", thingTypeUID);
			return super.createThing(thingTypeUID, configuration, thingUID,
					null);
		}
		if (CupsBindingConstants.SUPPORTED_THING_TYPES_UIDS.contains(thingTypeUID)) {
			ThingUID deviceUID = getCupsPrinterUID(thingTypeUID, thingUID,
					configuration, bridgeUID);
			logger.debug("creating thing {} from deviceBridgeUID: {} Bridge: {}"
					, thingTypeUID,deviceUID,bridgeUID);
			return super.createThing(thingTypeUID, configuration, deviceUID,
					bridgeUID);
		}
		throw new IllegalArgumentException("The thing type {} " + thingTypeUID
				+ " is not supported by the binding.");
	}
	
	private ThingUID getCupsPrinterUID(ThingTypeUID thingTypeUID, ThingUID thingUID, Configuration configuration,
			 ThingUID bridgeUID) {
		if (thingUID == null) {
			 String name = (String) configuration.get(CupsBindingConstants.PRINTER_PARAMETER_URL);
			 thingUID = new ThingUID(thingTypeUID, name, bridgeUID.getId());
		}
		return thingUID;
	}

	@Override
	protected ThingHandler createHandler(Thing thing) {

		ThingTypeUID thingTypeUID = thing.getThingTypeUID();

		if (thingTypeUID.equals(PRINTER_THING_TYPE)) {
			return new CupsPrinterHandler(thing);
		} else if (thingTypeUID.equals(SERVER_BRIGDE_THING_TYPE)) {
			CupsBridgeHandler bridge = new CupsBridgeHandler((Bridge) thing);
			registerDeviceDiscoveryService(bridge);
			return bridge;
		}

		return null;
	}

	private void registerDeviceDiscoveryService(CupsBridgeHandler bridge) {
		CupsDeviceDiscoveryService discoveryService = new CupsDeviceDiscoveryService();
		discoveryService.activate(bridge);
		this.discoveryServiceReg.put(bridge,bundleContext.registerService(
				DiscoveryService.class.getName(), discoveryService,
				new Hashtable<String, Object>()));
	}

	@Override
	protected void removeHandler(ThingHandler thingHandler) {
		if (thingHandler instanceof CupsBridgeHandler) {
			CupsBridgeHandler bridge = (CupsBridgeHandler) thingHandler;
			if (this.discoveryServiceReg.containsKey(bridge)) {
				CupsDeviceDiscoveryService service = (CupsDeviceDiscoveryService) bundleContext
						.getService(discoveryServiceReg.get(bridge).getReference());
				service.deactivate();
				discoveryServiceReg.get(bridge).unregister();
				discoveryServiceReg.remove(bridge);
			}
		}
		super.removeHandler(thingHandler);
	}
}
