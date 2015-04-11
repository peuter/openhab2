/**
 * Copyright (c) 2014 openHAB UG (haftungsbeschraenkt) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.cups.handler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.cups4j.CupsPrinter;
import org.cups4j.WhichJobsEnum;
import org.eclipse.smarthome.config.core.Configuration;
import org.eclipse.smarthome.core.library.types.DecimalType;
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingUID;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.RefreshType;
import org.openhab.binding.cups.CupsBindingConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link CupsPrinterHandler} is responsible for handling commands, which are sent
 * to one of the channels.
 * 
 * @author Tobias Braeutigam - Initial contribution
 */
public class CupsPrinterHandler extends BaseThingHandler implements
		PrinterStatusListener {

	private Logger logger = LoggerFactory.getLogger(CupsPrinterHandler.class);

	private URL url;
	
	private int refresh = 60; // refresh every minute as default
	ScheduledFuture<?> refreshJob;
	
	private CupsBridgeHandler bridgeHandler;

	public CupsPrinterHandler(Thing thing) {
		super(thing);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize() {
		Configuration config = getThing().getConfiguration();
		try {
			Object obj = config.get(CupsBindingConstants.PRINTER_PARAMETER_URL);
			if (obj instanceof URL)
				url = (URL) obj;
			else if (obj instanceof String)
				url = new URL((String)obj) ;
		} catch (MalformedURLException e) {
			logger.error("malformed url {}, printer thing creation failed",config.get(CupsBindingConstants.PRINTER_PARAMETER_URL));
		}
		// until we get an update put the Thing offline
		updateStatus(ThingStatus.OFFLINE);
		deviceOnlineWatchdog();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.smarthome.core.thing.binding.BaseThingHandler#dispose()
	 */
	@Override
	public void dispose() {
		if (refreshJob != null && !refreshJob.isCancelled()) {
			refreshJob.cancel(true);
			refreshJob = null;
		}
		logger.debug("CupsPrinterHandler {} disposed.", url);
		super.dispose();
	}

	private void deviceOnlineWatchdog() {
		Runnable runnable = new Runnable() {
			public void run() {
				try {
					CupsBridgeHandler bridgeHandler = getBridgeHandler();
					if (bridgeHandler.getClient().getPrinter(url)!=null) {
						updateStatus(ThingStatus.ONLINE);
					} else {
						updateStatus(ThingStatus.OFFLINE);
					}
				} catch (Exception e) {
					logger.debug("Exception occurred during execution: {}",
							e.getMessage(), e);
					
				}
			}
		};
		refreshJob = scheduler.scheduleAtFixedRate(runnable, 0, refresh,
				TimeUnit.SECONDS);
	}

	@Override
	public void handleCommand(ChannelUID channelUID, Command command) {
		if (command instanceof RefreshType) {
			CupsBridgeHandler bridge = getBridgeHandler();
			if (bridge!=null) {
				bridge.handleCommand(channelUID, command);
			}
			return;
		}
		// All Cups Channels are read only
	}
	
	private synchronized CupsBridgeHandler getBridgeHandler() {
		if (this.bridgeHandler == null) {
			Bridge bridge = getBridge();
			if (bridge == null) {
				logger.debug("Required bridge not defined for device {}.", url);
				return null;
			}
			ThingHandler handler = bridge.getHandler();
			if (handler instanceof CupsBridgeHandler) {
				this.bridgeHandler = (CupsBridgeHandler) handler;
				this.bridgeHandler.registerDeviceStatusListener(this);
			} else {
				logger.debug("No available bridge handler found for device {} bridge {} .", url, bridge.getUID());
				return null;
			}
		}
		return this.bridgeHandler;
		}

	@Override
	public void onDeviceStateChanged(ThingUID bridge, CupsPrinter device) {
		if (device.getPrinterURL().equals(url)) {
			try {
				updateState(new ChannelUID(getThing().getUID(), CupsBindingConstants.JOBS_CHANNEL), new DecimalType(device.getJobs(WhichJobsEnum.ALL, "", false).size()));
			} catch (Exception e) {
				logger.debug("error updating jobs channel, reason: {}",e.getMessage());
			}
			try {
				updateState(new ChannelUID(getThing().getUID(), CupsBindingConstants.WAITING_JOBS_CHANNEL), new DecimalType(device.getJobs(WhichJobsEnum.NOT_COMPLETED, "", false).size()));
			} catch (Exception e) {
				logger.debug("error updating waiting-jobs channel, reason: {}",e.getMessage());
			}
			try {
				updateState(new ChannelUID(getThing().getUID(), CupsBindingConstants.DONE_JOBS_CHANNEL), new DecimalType(device.getJobs(WhichJobsEnum.COMPLETED, "", false).size()));
			} catch (Exception e) {
				logger.debug("error updating done-jobs channel, reason: {}",e.getMessage());
			}
		}
	}

	@Override
	public void onDeviceRemoved(Bridge bridge, CupsPrinter device) {
		if (device.getPrinterURL().equals(url)) {
			if (bridgeHandler!=null) {
				bridgeHandler.unregisterDeviceStatusListener(this);
				bridgeHandler = null;
			}
			getThing().setStatus(ThingStatus.OFFLINE);
		}
	}

	@Override
	public void onDeviceAdded(Bridge bridge, CupsPrinter device) {
		logger.trace("new device discovered {} by {} ",device,bridge);
	}
}
