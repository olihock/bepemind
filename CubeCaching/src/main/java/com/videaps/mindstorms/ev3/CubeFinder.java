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
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lejos.hardware.BrickFinder;
import lejos.remote.ev3.RemoteRequestEV3;
import lejos.remote.ev3.RemoteRequestSampleProvider;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;


/**
 * CubeFinder class reflects the brick model composed by all ports for motors and sensors needed.
 * Two motors for driving the cube finder, one to pick up the cube and one sensor to find the cube
 * by measuring the distance between cube and finder.
 */
public class CubeFinder extends RemoteRequestEV3 {
	private static final long serialVersionUID = 2486611391238302676L;

	private MovePilot pilot = null;
	private RemoteRequestSampleProvider distanceMeter = null;
	
	
	public CubeFinder() throws NotBoundException, IOException {
		super(BrickFinder.find("EV3")[0].getIPAddress()); // "192.168.173.203"
		
		Chassis chassis = initChassis();
		pilot = new MovePilot(chassis);

		distanceMeter = (RemoteRequestSampleProvider) createSampleProvider("S1", "lejos.hardware.sensor.EV3UltrasonicSensor", "Distance");
	}


	private Chassis initChassis() {
		RegulatedMotor leftMotor = createRegulatedMotor("A", 'L');
		Wheel leftWheel = WheeledChassis.modelWheel(leftMotor, 25.0).offset(-70);
		RegulatedMotor rightMotor = createRegulatedMotor("B", 'L');
		Wheel rightWheel = WheeledChassis.modelWheel(rightMotor, 25.0).offset(70);
		
		Chassis chassis = new WheeledChassis(new Wheel[] { leftWheel, rightWheel }, WheeledChassis.TYPE_DIFFERENTIAL);
		return chassis;
	}

	
	/**
	 * Turn left by 90 degrees.
	 */
	public void turnLeft() {
		pilot.rotateLeft();
	}
	
	
	/**
	 * Turn right by 90 degrees.
	 */
	public void turnRight() {
	}
	
	
	/**
	 * Measures the distance to an object in front of the cube. 
	 * 
	 * @return Distance in meter
	 * @throws RemoteException
	 */
	public float measureDistance() throws RemoteException {
		float[] distance = new float[1];
		distanceMeter.fetchSample(distance, 0);
		return distance[0];
	}
	
	
	public void closeDistanceMeter() {
		distanceMeter.close();
	}
	
}
