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

import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;


/**
 * This singleton class reflects the EV3 brick which needs to statically instantiated.
 * Motor and sensor ports can be created by it.
 */
public class SingletonEV3 extends RemoteEV3 {
	private static SingletonEV3 instance = null;
	
	private Map<String, RMIRegulatedMotor> motorMap = new HashMap<String, RMIRegulatedMotor>();
	
	
	private SingletonEV3(String host) throws RemoteException, MalformedURLException, NotBoundException {
		super(host);
	}
	
	public static SingletonEV3 intitialise(String host) throws RemoteException, MalformedURLException, NotBoundException {
		if(instance == null) {
			instance = new SingletonEV3(host);
		}
		return instance;
	}
	
	public static SingletonEV3 getInstance() {
		if(instance == null) {
			throw new IllegalArgumentException("This Singleton needs to be initialised. Call getInstance(host) first.");
		}
		return instance; 
	}
	
	
	@Override
	public RMIRegulatedMotor createRegulatedMotor(String portName, char motorType) {
		RMIRegulatedMotor motor = motorMap.get(portName);
		if(motor == null) {
			motor = super.createRegulatedMotor(portName, motorType);
			motorMap.put(portName, motor);
		}
		return motor;
	}
	
	
	public RMIRegulatedMotor getRegulatedMotor(String portName) {
		RMIRegulatedMotor motor = motorMap.get(portName);
		return motor;
	}

}
