/**
 * Copyright (c) 2010-2017 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.ui.cometvisu.internal;

/**
 * Global SSE broadcaster for client communication
 *
 * @author Tobias Bräutigam
 */
public class SseBroadcaster extends org.glassfish.jersey.media.sse.SseBroadcaster {
    private static final SseBroadcaster OBJ = new SseBroadcaster();

    private SseBroadcaster() {
        super();
    }

    public static SseBroadcaster getInstance() {
        return OBJ;
    }
}
