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
	
	
	private Brick(String host, 
			char motorAType, 
			char motorBType, 
			char motorCType
	) throws RemoteException, MalformedURLException, NotBoundException {
		super(host);
		motorMap.put("A", super.createRegulatedMotor("A", motorAType));
		motorMap.put("B", super.createRegulatedMotor("B", motorBType));
		motorMap.put("C", super.createRegulatedMotor("C", motorCType));
		// TODO Oliver Add motor D creation.
		sensorMap.put("S1", super.createSampleProvider("S1", "lejos.hardware.sensor.EV3IRSensor", "Distance"));
		// TODO Oliver Add sensor 2 creation.
		// TODO Oliver Add sensor 3 creation.
	}
	
	public static Brick intitialise(
			String host,
			char motorAType,
			char motorBType,
			char motorCType
		) throws RemoteException, MalformedURLException, NotBoundException {
		if(instance == null) {
			instance = new Brick(host, motorAType, motorBType, motorCType);
		}
		return instance;
	}
	
	public static Brick getInstance() {
		if(instance == null) {
			throw new IllegalArgumentException("This Singleton needs to be initialised. Call getInstance(host, aType, bType, cType) first.");
		}
		return instance; 
	}
	
	public void closeMotors() throws RemoteException {
		for(RMIRegulatedMotor motor : motorMap.values()) {
			motor.close();
		}
	}
	
	public void closeSensors() throws RemoteException {
		for(RMISampleProvider sensor : sensorMap.values()) {
			sensor.close();
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
