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

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Logger;

import com.videaps.mindstorms.ral.motor.RotateDelegate;

import lejos.hardware.BrickFinder;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RMISampleProvider;
import lejos.remote.ev3.RemoteEV3;


/**
 * CubeFinder class reflects the brick model composed by all ports for motors and sensors needed.
 * Two motors for driving the cube finder, one to pick up the cube and one sensor to find the cube
 * by measuring the distance between cube and finder.
 */
public class CubeFinder extends RemoteEV3 {
	private static final Logger LOGGER = Logger.getLogger(RotateDelegate.class.getName());

	RMIRegulatedMotor leftMotor = null;
	RMIRegulatedMotor rightMotor = null;
	RMISampleProvider distanceMeter = null;
	
	
	public CubeFinder() throws NotBoundException, IOException {
		super(BrickFinder.find("EV3")[0].getIPAddress()); 
		
		leftMotor = createRegulatedMotor("A", 'L');
		rightMotor = createRegulatedMotor("B", 'L');
		
		distanceMeter = createSampleProvider("S1", "lejos.hardware.sensor.EV3UltrasonicSensor", "Distance");
	}


	public void move(int angle) throws RemoteException, MalformedURLException, NotBoundException {
		leftMotor.rotate(angle, true);
		rightMotor.rotate(angle, true);
	}
	
	
	public void findSampleAndStop() throws RemoteException {
		forward();
		doWhileDistanceLessThan(0.25f);
		stop();
	}


	private void stop() throws RemoteException {
		leftMotor.stop(true);
		rightMotor.stop(true);
	}


	private void forward() throws RemoteException {
		leftMotor.forward();
		rightMotor.forward();
	}
	
	/**
	 * Measures the distance to an object in front of the cube. 
	 * 
	 * @return Distance in meter
	 * @throws RemoteException
	 */
	private float measureDistance() throws RemoteException {
		float distance = distanceMeter.fetchSample()[0];
		return distance;
	}
	
	
	private boolean doWhileDistanceLessThan(float meters) throws RemoteException {
		float distance = measureDistance();
		while(distance >= meters) {
			distance = measureDistance();
			LOGGER.info("distance="+distance);
		}
		return true;
	}
	
	
	/**
	 * Turn left by 90 degrees.
	 */
	public void turnLeft() {
	}
	
	
	/**
	 * Turn right by 90 degrees.
	 */
	public void turnRight() {
	}
	

	/**
	 * Free the motor ports again.
	 * 
	 * @throws RemoteException
	 */
	public void shutdownPorts() throws RemoteException {
		leftMotor.close();
		rightMotor.close();
		distanceMeter.close();
		LOGGER.info("shutdownPorts="+true);
	}
	
}
