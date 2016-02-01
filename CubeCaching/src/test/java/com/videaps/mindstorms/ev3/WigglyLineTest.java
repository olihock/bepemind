/*
 Copyright (c) 2016 Videa Project Services GmbH

 Permission is hereby granted, free of charge, to any person obtaining a copy of this software 
 and associated documentation files (the "Software"), to deal in the Software without restriction, 
 including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 and/or sell copies of the Software,and to permit persons to whom the Software is furnished to do so, 
 subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all copies or substantial 
 portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT 
 NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
 IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
 WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE 
 SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package com.videaps.mindstorms.ev3;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.junit.Test;

import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.utility.Delay;


public class WigglyLineTest {

	private final String host = "192.168.173.201";

	@Test
	public void testGoStraight() throws RemoteException, MalformedURLException, NotBoundException {
		SingletonEV3 ev3 = SingletonEV3.intitialise(host);
		RMIRegulatedMotor motorB = ev3.createRegulatedMotor("B", 'L');
		RMIRegulatedMotor motorC = ev3.createRegulatedMotor("C", 'L');

		int speedB = motorB.getSpeed();
		int speedC = motorC .getSpeed();
		
		int tachoCountB = motorB.getTachoCount();
		int tachoCountC = motorC.getTachoCount();
		
		motorB.setAcceleration(360);
		motorC.setAcceleration(360);
		
		// This is the value how many time the cube finder walks the distance to the cube.
		// For example, if distance is 5, the cube finder moves 5 times 360 degrees times.
		
		int distance = 1;
		
		for(int i = 0; i < distance; i++) {
			motorB.rotate(2*360, true);
			motorC.rotate(2*360, false);
			
			int angle = 45;
			
			motorB.rotate(angle, false);
			motorB.rotate(-angle, false);

			motorC.rotate(angle, false);
			motorC.rotate(-angle, false);
			
			motorB.rotate(2*360, true);
			motorC.rotate(2*360, false);
		}
		
		Delay.msDelay(1000);
		
		motorB.rotateTo(tachoCountB, true);
		motorC.rotateTo(tachoCountC, false);
		
		motorB.close();
		motorC.close();
	}

}
