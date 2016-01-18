/*
 * (C) Copyright 2016 Videa Project Services GmbH (http://videa-services.com)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
 * 
 * @author Oliver
 *
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
