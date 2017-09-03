/**
 * Copyright (c) 2010-2017 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.ui.cometvisu.backend.beans;

/**
 * Link notification action.
 *
 * @author Tobias Bräutigam - Initial Contribution and API
 * @since 2.2.0
 */
public class LinkActionBean extends ActionBean {

    public String title;
    public String url;
    public String action;
    public boolean hidden = false;
    public boolean deleteMessageAfterExecution = false;
}
