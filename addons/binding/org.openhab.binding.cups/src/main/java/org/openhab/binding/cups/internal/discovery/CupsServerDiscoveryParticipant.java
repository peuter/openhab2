/**
 * Copyright (c) 2015 openHAB UG (haftungsbeschraenkt) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.cups.internal.discovery;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.jmdns.ServiceInfo;

import org.eclipse.smarthome.config.discovery.DiscoveryResult;
import org.eclipse.smarthome.config.discovery.DiscoveryResultBuilder;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.ThingUID;
import org.eclipse.smarthome.io.transport.mdns.discovery.MDNSDiscoveryParticipant;
import org.openhab.binding.cups.CupsBindingConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * discovers cups servers announced by mDNS
 * 
 * @author Tobias Bräutigam
 *
 */
public class CupsServerDiscoveryParticipant implements MDNSDiscoveryParticipant {

	private Logger logger = LoggerFactory
			.getLogger(CupsServerDiscoveryParticipant.class);

	@Override
	public Set<ThingTypeUID> getSupportedThingTypeUIDs() {
		return CupsBindingConstants.SUPPORTED_THING_TYPES_UIDS;
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
					logger.debug(
							"Discovered a cups server bridge with name '{}'",
							service.getServer().replace(
									"." + service.getDomain() + ".", ""));
					return new ThingUID(
							CupsBindingConstants.SERVER_BRIGDE_THING_TYPE,
							service.getServer()
									.replace("." + service.getDomain() + ".",
											"")
									.replaceAll("[^A-Za-z0-9_]", "_"));
				}
			}
		}
		return null;
	}

	@Override
	public DiscoveryResult createResult(ServiceInfo service) {
		DiscoveryResult result = null;
		ThingUID uid = getThingUID(service);
		if (uid != null) {
			Map<String, Object> properties = new HashMap<>(2);

			String hostname = service.getServer().replace(
					"." + service.getDomain() + ".", "");
			String label = hostname;
			// remove the domain from the name

			int port = service.getPort();

			// CUPS Server with discovered
			properties
					.put(CupsBindingConstants.BRIDGE_PARAMETER_HOST, hostname);
			properties.put(CupsBindingConstants.BRIDGE_PARAMETER_PORT, port);
			result = DiscoveryResultBuilder.create(uid)
					.withProperties(properties).withLabel(label).build();
			logger.debug(
					"Created a DiscoveryResult for cups server bridge on host '{}'",
					hostname);

		}
		return result;
	}

}
