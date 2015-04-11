/**
 * Copyright (c) 2014-2015 openHAB UG (haftungsbeschraenkt) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.cups.internal.discovery;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.cups4j.CupsPrinter;
import org.eclipse.smarthome.config.discovery.AbstractDiscoveryService;
import org.eclipse.smarthome.config.discovery.DiscoveryResult;
import org.eclipse.smarthome.config.discovery.DiscoveryResultBuilder;
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.ThingUID;
import org.openhab.binding.cups.CupsBindingConstants;
import org.openhab.binding.cups.handler.CupsBridgeHandler;
import org.openhab.binding.cups.handler.PrinterStatusListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link CupsDeviceDiscoveryService} class is used to discover CUPS Printers, that a connected
 * to a CUPS server
 * 
 * @author Tobias Bräutigam
 * @since 2.0.0
 */
public class CupsDeviceDiscoveryService extends AbstractDiscoveryService
		implements PrinterStatusListener {

	private final static Logger logger = LoggerFactory
			.getLogger(CupsDeviceDiscoveryService.class);

	private CupsBridgeHandler bridgeHandler;

	public CupsDeviceDiscoveryService() throws IllegalArgumentException {
		super(CupsBindingConstants.SUPPORTED_THING_TYPES_UIDS, 10, true);
	}

	public void activate(CupsBridgeHandler cupsBridgeHandler) {
		this.bridgeHandler = cupsBridgeHandler;
		cupsBridgeHandler.registerDeviceStatusListener(this);
	}

	public void deactivate() {
		bridgeHandler.unregisterDeviceStatusListener(this);
	}

	@Override
	public Set<ThingTypeUID> getSupportedThingTypes() {
		return CupsBindingConstants.SUPPORTED_THING_TYPES_UIDS;
	}

	@Override
	public void onDeviceStateChanged(ThingUID bridge, CupsPrinter device) {
		// this can be ignored here

	}

	@Override
	public void onDeviceRemoved(Bridge bridge, CupsPrinter device) {
		// this can be ignored here

	}

	@Override
	public void onDeviceAdded(Bridge bridge, CupsPrinter device) {
		String uidName = device.getName().replaceAll("[^A-Za-z0-9_]", "_");
		logger.debug("device {} found on bridge {}", device, bridge);
		ThingTypeUID thingType = CupsBindingConstants.PRINTER_THING_TYPE;
		Map<String, Object> properties = new HashMap<String, Object>();
		// All devices need this parameter
		properties.put(CupsBindingConstants.PRINTER_PARAMETER_URL,
				device.getPrinterURL());
		
		logger.debug("Adding new cups printer with name '{}' to smarthome inbox", uidName);
		ThingUID thingUID = new ThingUID(thingType, bridge.getUID(), uidName);
		DiscoveryResult discoveryResult = DiscoveryResultBuilder
				.create(thingUID).withProperties(properties)
				.withBridge(bridge.getUID()).withLabel(device.getName())
				.build();
		thingDiscovered(discoveryResult);
	}

	@Override
	protected void startScan() {
		// this can be ignored here
	}

}
