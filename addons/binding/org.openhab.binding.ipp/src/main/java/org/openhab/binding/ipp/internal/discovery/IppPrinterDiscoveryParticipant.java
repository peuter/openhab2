/**
 * Copyright (c) 2015 openHAB UG (haftungsbeschraenkt) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.ipp.internal.discovery;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.jmdns.ServiceInfo;

import org.eclipse.smarthome.config.discovery.DiscoveryResult;
import org.eclipse.smarthome.config.discovery.DiscoveryResultBuilder;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.ThingUID;
import org.eclipse.smarthome.io.transport.mdns.discovery.MDNSDiscoveryParticipant;
import org.openhab.binding.ipp.IppBindingConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * discovers ipp printers announced by mDNS
 * 
 * @author Tobias Bräutigam
 *
 */
public class IppPrinterDiscoveryParticipant implements MDNSDiscoveryParticipant {

	private Logger logger = LoggerFactory
			.getLogger(IppPrinterDiscoveryParticipant.class);

	@Override
	public Set<ThingTypeUID> getSupportedThingTypeUIDs() {
		return Collections.singleton(IppBindingConstants.PRINTER_THING_TYPE);
	}

	@Override
	public String getServiceType() {
		return "_ipp._tcp.local.";
	}

	@Override
	public ThingUID getThingUID(ServiceInfo service) {
		if (service != null) {
			logger.trace("ServiceInfo: {}", service);
			if (service.getType() != null) {
				if (service.getType().equals(getServiceType())) {
					String uidName = getUIDName(service);
					return new ThingUID(
							IppBindingConstants.PRINTER_THING_TYPE,
							uidName);
				}
			}
		}
		return null;
	}
	
	private String getUIDName(ServiceInfo service) {
		String hostname = service.getServer().replace("." + service.getDomain()+ ".", "");
		String printerName = getPrinterName(service);
		return ((hostname.equals(printerName)) ? hostname : printerName).replaceAll("[^A-Za-z0-9_]", "_");
	}
		
	private String getHostName(ServiceInfo service) {
		return service.getServer().replace(
				"." + service.getDomain() + ".", "");
	}
	
	private String getPrinterName(ServiceInfo service) {
		String rp = service.getPropertyString("rp");
		if (rp==null) {
			return getHostName(service);
		} else if (rp.startsWith("printers/")) {
			// CUPS Server (http://<cups-server>:631/printers/<printer-name>
			return rp.substring(9);
		} else {
			// IPP Printer: read the name from Property
			return service.getPropertyString("product").replaceAll("\\(\\)", "");
		}
	}

	@Override
	public DiscoveryResult createResult(ServiceInfo service) {
		DiscoveryResult result = null;
		String rp = service.getPropertyString("rp");
		if (rp==null) {
			return null;
		}
		ThingUID uid = getThingUID(service);
		if (uid != null) {
			Map<String, Object> properties = new HashMap<>(2);
			// remove the domain from the name
			String hostname = getHostName(service);
			String label = getPrinterName(service).replaceAll("[^A-Za-z0-9_\\-\\. ]", "");
		
			int port = service.getPort();
			
			properties.put(IppBindingConstants.PRINTER_PARAMETER_URL,"http://"+hostname+":"+port+"/"+rp);
			properties.put(IppBindingConstants.PRINTER_PARAMETER_NAME,label);
			
			
			result = DiscoveryResultBuilder.create(uid)
					.withProperties(properties).withLabel(label).build();
			logger.debug(
					"Created a DiscoveryResult {} for ipp printer on host '{}' name '{}'",result,
					properties.get(IppBindingConstants.PRINTER_PARAMETER_URL),label);
		}
		return result;
	}
}
