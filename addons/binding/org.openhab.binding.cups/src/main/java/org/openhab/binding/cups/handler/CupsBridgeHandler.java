/**
* Copyright (c) 2014 openHAB UG (haftungsbeschraenkt) and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*/
package org.openhab.binding.cups.handler;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.cups4j.CupsClient;
import org.cups4j.CupsPrinter;
import org.eclipse.smarthome.config.core.Configuration;
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.binding.BaseBridgeHandler;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.RefreshType;
import org.openhab.binding.cups.CupsBindingConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link CupsBridgeHandler} is responsible for discovering new CUPS servers
 * 
 * @author Tobias Bräutigam
 * @since 2.0.0
 */
public class CupsBridgeHandler extends BaseBridgeHandler {
	private Logger logger = LoggerFactory.getLogger(CupsBridgeHandler.class);

	private String host;
	private int port;
	private int refreshInterval;

	private CupsClient client;

	private HashSet<String> lastActiveDevices = new HashSet<String>();

	private List<PrinterStatusListener> deviceStatusListeners = new CopyOnWriteArrayList<>();

	private ScheduledFuture<?> pollingJob;
	private Runnable pollingRunnable = new Runnable() {
		@Override
		public void run() {
			doRefresh();
		}
	};

	public CupsBridgeHandler(Bridge bridge) {
		super(bridge);
	}
	
	protected void doRefresh() {
		try {
			if (client==null) {
				connect();
			}
			if (client==null) {
				// connection not possible try again in next refresh
				return;
			}
			for (CupsPrinter device : client.getPrinters()) {
				if (lastActiveDevices != null
						&& lastActiveDevices.contains(device.getName())) {
					for (PrinterStatusListener deviceStatusListener : deviceStatusListeners) {
						try {
							deviceStatusListener.onDeviceStateChanged(
									getThing().getUID(), device);
						} catch (Exception e) {
							logger.error(
									"An exception occurred while calling the DeviceStatusListener",
									e);
						}
					}
				} else {
					for (PrinterStatusListener deviceStatusListener : deviceStatusListeners) {
						try {
							deviceStatusListener.onDeviceAdded(getThing(),
									device);
							deviceStatusListener.onDeviceStateChanged(
									getThing().getUID(), device);
						} catch (Exception e) {
							logger.error(
									"An exception occurred while calling the DeviceStatusListener",
									e);
						}
						lastActiveDevices.add(device.getName());
					}
				}
			}
		} catch (Exception e) {
			logger.error("exception while getting printer from {}: {}",
					client, e.getMessage());
		}
	}
	
	public CupsClient getClient() {
		return client;
	}

	@Override
	public void handleCommand(ChannelUID channelUID, Command command) {
		if (command instanceof RefreshType) {
			logger.debug("Refresh command received.");
			doRefresh();
		} else
			logger.warn("No bridge commands defined.");
	}

	private synchronized void startAutomaticRefresh() {
		if (pollingJob == null || pollingJob.isCancelled()) {
			pollingJob = scheduler.scheduleAtFixedRate(pollingRunnable, 0,
					refreshInterval, TimeUnit.MILLISECONDS);
		}
	}
	
	private void connect() {
		if (host != null && host != "") {
			try {
				client = new CupsClient(host, port);
				
			} catch (Exception e) {
				logger.error("Couldn't connect to Cups server [Host '"
						+ host + "' Port '" + port + "']: "
						+ e.getLocalizedMessage());
			}
		} else {
			logger.warn(
					"Couldn't connect to Cups server because of missing connection parameters [Host '{}' Port '{}'].",
					host, port);
		}
	}

	@Override
	public void initialize() {
		logger.debug("Initializing Cups bridge handler.");
		Configuration conf = this.getConfig();
		super.initialize();
		this.host = String.valueOf(conf.get(CupsBindingConstants.BRIDGE_PARAMETER_HOST));
		try {
			this.port = Integer.parseInt(String.valueOf(conf
					.get(CupsBindingConstants.BRIDGE_PARAMETER_PORT)));
		} catch (Exception ex) {
			this.port = 631;
		}
		try {
			this.refreshInterval = Integer.parseInt(String.valueOf(conf
					.get(CupsBindingConstants.BRIDGE_PARAMETER_REFRESH_INTERVAL)));
		} catch (Exception ex) {
			this.refreshInterval = 30000;
		}
		connect();
		startAutomaticRefresh();
	}

	@Override
	public void dispose() {
		client = null;
		super.dispose();
		if (pollingJob!=null) {
			pollingJob.cancel(true);
			pollingJob=null;
		}
	}

	public boolean registerDeviceStatusListener(
			PrinterStatusListener deviceStatusListener) {
		if (deviceStatusListener == null) {
			throw new IllegalArgumentException(
					"It's not allowed to pass a null deviceStatusListener.");
		}
		return deviceStatusListeners.add(deviceStatusListener);
	}

	public boolean unregisterDeviceStatusListener(
			PrinterStatusListener deviceStatusListener) {
		return deviceStatusListeners.remove(deviceStatusListener);
	}
}
