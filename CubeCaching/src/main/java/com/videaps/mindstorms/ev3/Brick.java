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
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import lejos.hardware.BrickFinder;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RMISampleProvider;
import lejos.remote.ev3.RemoteEV3;


/**
 * This singleton class reflects the EV3 brick which needs to statically instantiated.
 * Motor and sensor ports can be created by it.
 */
public class Brick extends RemoteEV3 {
	private static final Logger LOGGER = Logger.getLogger(Brick.class.getName());

	private static Brick instance = null;
	
	private Map<String, RMIRegulatedMotor> motorMap = new HashMap<String, RMIRegulatedMotor>();
	private Map<String, RMISampleProvider> sensorMap = new HashMap<String, RMISampleProvider>();
	
	
	private Brick() throws RemoteException, MalformedURLException, NotBoundException {
		super(BrickFinder.find("EV3")[0].getIPAddress());
		
		motorMap.put("A", createRegulatedMotor("A", 'L'));
		motorMap.put("B", createRegulatedMotor("B", 'L'));
		motorMap.put("C", createRegulatedMotor("C", 'M'));
		motorMap.put("D", createRegulatedMotor("D", 'M'));
		
		sensorMap.put("S1", createSampleProvider("S1", "lejos.hardware.sensor.EV3UltrasonicSensor", "Distance"));
	}
	
	
	public static Brick getInstance() {
		if(instance == null) {
			try {
				instance = new Brick();
			} catch (RemoteException | MalformedURLException | NotBoundException e) {
				e.printStackTrace();
			}
		}
		return instance; 
	}
	
	
	public void closeMotors() {
		try {
			for(RMIRegulatedMotor motor : motorMap.values()) {
				motor.close();
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	
	public void closeSensors() {
		try {
			for(RMISampleProvider sensor : sensorMap.values()) {
				sensor.close();
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	
	public RMIRegulatedMotor getRegulatedMotor(String portName) {
		RMIRegulatedMotor motor = motorMap.get(portName);
		return motor;
	}
	
	
	public RMISampleProvider getSampleProvider(String portName) {
		RMISampleProvider sensor = sensorMap.get(portName);
		return sensor;
	}

	
	/**
	 * A kind of dummy class to just wait for all motors to complete their movements,
	 * including a stalled motor situation.
	 */
	public void waitForMotors(String... motorPorts) throws RemoteException {
		for(String motorPort : motorPorts) {
			LOGGER.info("motorPort="+motorPort);
			RMIRegulatedMotor motor = getRegulatedMotor(motorPort);
			if(motor != null) {
				LOGGER.info("motor="+motor);
				motor.waitComplete();
			}
		}
	}

}
