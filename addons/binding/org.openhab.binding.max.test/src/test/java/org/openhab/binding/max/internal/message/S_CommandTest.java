/**
 * Copyright (c) 2014-2015 openHAB UG (haftungsbeschraenkt) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.max.internal.message;

import static org.junit.Assert.*;

import org.apache.commons.net.util.Base64;
import org.junit.Test;
import org.openhab.binding.max.internal.Utils;

/**
* Tests cases for {@link S_Command}.
* 
* @author Andreas Heil (info@aheil.de)
* @author Marcel Verpaalen - OH2 Version and updates
* @since 1.4.0
*/
public class S_CommandTest {

	public final String foo = "s:AARAAAAAAP4wAaiLix8=\r\n";
	
	@Test
	public void PrefixTest() {
		S_Command scmd = new S_Command("0b0da3", 1, ThermostatModeType.MANUAL, 20.0);
		
		String commandStr = scmd.getCommandString();
		
		String prefix = commandStr.substring(0, 2);
		
		assertEquals("s:", prefix);
		
	}
	
	@Test
	public void BaseCommandTest() {
		S_Command scmd = new S_Command("0b0da3", 1, ThermostatModeType.MANUAL, 20.0);
		
		String commandStr = scmd.getCommandString();
		
		String base64Data = commandStr.substring(3);
		byte[] bytes = Base64.decodeBase64(base64Data.getBytes());
		
		int[] data = new int[bytes.length];

		for (int i = 0; i < bytes.length; i++) {
			data[i] = bytes[i] & 0xFF;
		}
		
		String decodedString = Utils.toHex(data);
		assertEquals("s:AARAAAAACw2jAWg=\r\n", commandStr);
		assertEquals("011000000002C368C05A", decodedString);
		
	}

	@Test
	public void boostModeTest() {
		S_Command scmd = new S_Command("0b0da3", 1, ThermostatModeType.BOOST, 21.0);
		
		String commandStr = scmd.getCommandString();
			
		assertEquals("s:AARAAAAACw2jAeo=\r\n", commandStr);
		
	}
	
	@Test
	public void autotModeTest() {
		S_Command scmd = new S_Command("0b0da3", 1, ThermostatModeType.AUTOMATIC, 0);
		
		String commandStr = scmd.getCommandString();
			
		assertEquals("s:AARAAAAACw2jAQA=\r\n", commandStr);
		
	}
}
