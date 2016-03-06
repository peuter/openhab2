/**
 * Copyright (c) 2014-2015 openHAB UG (haftungsbeschraenkt) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.braviatv.handler;

import static org.openhab.binding.braviatv.BraviaTvBindingConstants.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.smarthome.config.core.Configuration;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingStatusDetail;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.types.Command;
import org.openhab.binding.braviatv.internal.BraviaCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * The {@link BraviaTvHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Tobias Bräutigam - Initial contribution
 */
public class BraviaTvHandler extends BaseThingHandler {

    private Logger logger = LoggerFactory.getLogger(BraviaTvHandler.class);

    private URL irccUrl;
    private String irccAuth;
    private String irccPSK;

    public BraviaTvHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        if (channelUID.getId().equals(COMMAND_CHANNEL)) {
            try {
                BraviaCommand cmd = BraviaCommand.valueOf(command.toString().toUpperCase());
                String code = cmd.getCode();
                this.sendUPnPcommand(code);
            } catch (IOException | SAXException e) {
                updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, e.toString());
            } catch (IllegalArgumentException e) {
                logger.error("unknown command " + command);
            }

        }
    }

    @Override
    public void initialize() {
        Configuration config = getThing().getConfiguration();
        try {
            String ip = (String) config.get(TV_PARAMETER_IP);
            this.irccUrl = new URL("http://" + ip + IRCC_PATH);
            this.irccAuth = (String) config.get(TV_PARAMETER_AUTH);
            this.irccPSK = (String) config.get(TV_PARAMETER_PSK);
            updateStatus(ThingStatus.ONLINE);
        } catch (MalformedURLException e) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR, e.getMessage());
        }
    }

    /**
     * Send command to TV
     *
     * see: https://github.com/breunigs/bravia-auth-and-remote.git
     */
    private boolean sendUPnPcommand(String code) throws IOException, SAXException {
        StringBuilder soapBody = new StringBuilder();

        soapBody.append("<?xml version=\"1.0\"?><s:Envelope " + "xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\" "
                + "s:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">" + "<s:Body>" + "<m:"
                + IRCC_SEND_ACTION + " xmlns:m=\"" + IRCC_SERVICE_ID + "\">" + "<" + IRCC_ARG_KEY + ">" + code + "</"
                + IRCC_ARG_KEY + ">" + "</m:" + IRCC_SEND_ACTION + ">" + "</s:Body></s:Envelope>");

        HttpURLConnection conn = (HttpURLConnection) this.irccUrl.openConnection();

        conn.setRequestMethod("POST");
        conn.setReadTimeout(HTTP_RECEIVE_TIMEOUT);
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
        conn.setRequestProperty("SOAPAction", "\"" + IRCC_SERVICE_ID + "#" + IRCC_SEND_ACTION + "\"");
        if (this.irccAuth.length() > 0) {
            conn.setRequestProperty("Cookie", "auth=" + this.irccAuth);
        } else {
            conn.setRequestProperty("X-Auth-PSK", this.irccPSK);
        }
        conn.setRequestProperty("Connection", "Close");

        byte[] soapBodyBytes = soapBody.toString().getBytes();
        logger.debug(soapBody.toString());

        conn.setRequestProperty("Content-Length", String.valueOf(soapBodyBytes.length));

        conn.getOutputStream().write(soapBodyBytes);
        conn.disconnect();
        return (conn.getResponseCode() == 200);
    }
}
