/**
 * Copyright (c) 2014-2015 openHAB UG (haftungsbeschraenkt) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.braviatv;

import org.eclipse.smarthome.core.thing.ThingTypeUID;

/**
 * The {@link BraviaTvBinding} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Tobias Bräutigam - Initial contribution
 */
public class BraviaTvBindingConstants {

    public static final String BINDING_ID = "braviatv";

    // List of all Thing Type UIDs
    public final static ThingTypeUID THING_TYPE_TV = new ThingTypeUID(BINDING_ID, "BraviaTv");

    // List of all Channel ids
    public final static String COMMAND_CHANNEL = "command";

    // configuration
    public final static String TV_PARAMETER_IP = "ipAddress";
    public final static String TV_PARAMETER_AUTH = "auth";

    // Some definitions for the IRCC service
    public final static String IRCC_SERVICE_ID = "urn:schemas-sony-com:service:IRCC:1";
    public final static String IRCC_SEND_ACTION = "X_SendIRCC";
    public final static String IRCC_PATH = "/sony/IRCC";
    public final static String IRCC_ARG_KEY = "IRCCCode";
    /**
     * Receive timeout when requesting data from device
     */
    public final static int HTTP_RECEIVE_TIMEOUT = 7000;
}