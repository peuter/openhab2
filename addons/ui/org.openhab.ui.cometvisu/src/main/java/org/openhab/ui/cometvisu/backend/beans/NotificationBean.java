/**
 * Copyright (c) 2010-2017 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.ui.cometvisu.backend.beans;

import java.util.Map;

/**
 * Notification bead to send messages to CometVisu clients NotificationCenter.
 *
 * @author Tobias Bräutigam - Initial Contribution and API
 * @since 2.2.0
 */
public class NotificationBean {
    public static enum Severity {
        low,
        normal,
        high,
        urgent
    }

    public static enum Target {
        speech,
        popup,
        notificationCenter,
        toast,
        systemNotification
    }

    public String topic = "cv.backend";
    public String title;
    public Target target;
    public String message;
    public Severity severity = Severity.normal;
    public boolean skipInitial = false;
    public boolean deletable = true;
    public boolean unique = false;
    public int progress;
    public String icon;
    public String iconClasses;
    public Map<String, ActionBean> actions;

}