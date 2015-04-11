/**
 * Copyright (c) 2014 openHAB UG (haftungsbeschraenkt) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.cups;

import java.util.Set;

import org.eclipse.smarthome.core.thing.ThingTypeUID;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

/**
 * The {@link CupsBinding} class defines common constants, which are 
 * used across the whole binding.
 * 
 * @author Tobias Braeutigam - Initial contribution
 */
public class CupsBindingConstants {

    public static final String BINDING_ID = "cups";
    
    // List of all Thing Type UIDs
    public final static ThingTypeUID SERVER_BRIGDE_THING_TYPE = new ThingTypeUID(BINDING_ID, "bridge");
    public final static ThingTypeUID PRINTER_THING_TYPE = new ThingTypeUID(BINDING_ID, "printer");

    // List of all Channel ids
    public final static String JOBS_CHANNEL = "jobs";
    public final static String WAITING_JOBS_CHANNEL = "waitingJobs";
    public final static String DONE_JOBS_CHANNEL = "doneJobs";

    public final static String PRINTER_PARAMETER_URL = "url";
    public final static String BRIDGE_PARAMETER_HOST = "host";
    public final static String BRIDGE_PARAMETER_PORT = "port";
    public final static String BRIDGE_PARAMETER_REFRESH_INTERVAL = "refreshInterval";
    
    public final static Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS =ImmutableSet.of(PRINTER_THING_TYPE);
    public final static Set<ThingTypeUID> SUPPORTED_BRIDGE_TYPES_UIDS =ImmutableSet.of(SERVER_BRIGDE_THING_TYPE);
    
    public final static Set<ThingTypeUID> SUPPORTED_TYPES_UIDS = Sets.union(
    		SUPPORTED_THING_TYPES_UIDS,
    		SUPPORTED_BRIDGE_TYPES_UIDS);
}
