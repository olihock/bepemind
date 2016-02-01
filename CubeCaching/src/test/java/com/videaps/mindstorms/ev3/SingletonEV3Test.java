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
import java.util.List;

import org.junit.Test;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RMISampleProvider;
import lejos.remote.ev3.RemoteEV3;
import lejos.utility.Delay;


public class SingletonEV3Test {

	private final String host = "192.168.173.201";
	
	@Test
	public void test() throws NotBoundException, RemoteException, MalformedURLException {
		
		RMIRegulatedMotor motorB = SingletonEV3.intitialise(host).createRegulatedMotor("B", 'L');
		RMIRegulatedMotor motorC = SingletonEV3.intitialise(host).createRegulatedMotor("C", 'L');

		motorB.rotate(360, true);
		motorC.rotate(360);

		motorB.close();
		motorC.close();
	}

	@Test
	public void testTwoMotors() throws RemoteException, MalformedURLException, NotBoundException {
		
		RemoteEV3 ev3 = new RemoteEV3(host);
		System.out.println(ev3);
		System.out.println("host="+ev3.getHost());
		
		
		RMIRegulatedMotor motorB = ev3.createRegulatedMotor("B", 'L');
		RMIRegulatedMotor motorC = ev3.createRegulatedMotor("C", 'L');

		motorB.rotate(360, true);
		motorC.rotate(360);

		motorB.close();
		motorC.close();
		
		ev3 = new RemoteEV3(host);
		System.out.println(ev3);
	}

	
	@Test
	public void testSampleProviderDistance() throws RemoteException, MalformedURLException, NotBoundException, InterruptedException {
		SingletonEV3 ev3 = SingletonEV3.intitialise(host);
		RMISampleProvider sampleProvider = ev3.createSampleProvider("S1", "lejos.hardware.sensor.EV3IRSensor", "Distance");
		
		for(int i = 0; i < 25; i++) {
			float[] samples = sampleProvider.fetchSample();
			for(float sample : samples) {
				System.out.println(sample);
			}
			Thread.sleep(1000);
		}

		sampleProvider.close();
	}
	
	
	@Test
	public void testIrSensor() throws RemoteException, MalformedURLException, NotBoundException {
		SingletonEV3 ev3 = SingletonEV3.intitialise(host);
		
		Port port = ev3.getPort("S1");
		
		EV3IRSensor sensor = new EV3IRSensor(port);
		List<String> modes = sensor.getAvailableModes();
		System.out.println(modes);
		System.out.println("mode="+sensor.getMode("Seek"));
		System.out.println("currentMode="+sensor.getCurrentMode());
		sensor.setCurrentMode("Seek");
		System.out.println("currentMode="+sensor.getCurrentMode());
		float[] samples = new float[sensor.sampleSize()];
		sensor.fetchSample(samples, 0);
		for(float sample : samples) {
			System.out.print(sample+" ");
		}
		
		sensor.close();
	}
	
	
	@Test
	public void testMediumMotor() throws RemoteException, MalformedURLException, NotBoundException {
		SingletonEV3 ev3 = SingletonEV3.intitialise(host);
		RMIRegulatedMotor motor = ev3.createRegulatedMotor("A", 'M');

		int angle = 360;
		System.out.println("angle="+angle);
		motor.rotate(angle);
		Delay.msDelay(5000);
		motor.rotate(-angle);
		
		motor.close();
	}
}
