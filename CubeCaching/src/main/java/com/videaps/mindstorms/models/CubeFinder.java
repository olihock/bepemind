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
package com.videaps.mindstorms.models;

import java.rmi.RemoteException;
import java.util.logging.Logger;

import com.videaps.mindstorms.ev3.Brick;

import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RMISampleProvider;


/**
 * CubeFinder class reflects the brick model composed by all ports for motors and sensors needed.
 * Two motors for driving the cube finder, one to pick up the cube and one sensor to find the cube
 * by measuring the distance between cube and finder.
 */
public class CubeFinder implements IModel {
	private static final Logger LOGGER = Logger.getLogger(CubeFinder.class.getName());

	private static final double CIRCUMFERENCE_CM = 3.8 * 3.14159; // circumference [cm] = diameter [cm] * pi
	private static final int RIGHT_ANGLE_DEGREES = 495; 
	private static final int ACCELERATION = 500;

	private final int X_AXIS = 100;
	private final int Y_AXIS = 100;
	private final int X_LEG = 20;

	private RMIRegulatedMotor leftMotor = null; // Port A
	private RMIRegulatedMotor rightMotor = null; // Port B
	private RMISampleProvider distanceMeter = null; // Port S1
	
	
	public CubeFinder() {
		leftMotor = Brick.getInstance().getRegulatedMotor("A");
		rightMotor = Brick.getInstance().getRegulatedMotor("B");
		
		try {
			leftMotor.setAcceleration(ACCELERATION);
			rightMotor.setAcceleration(ACCELERATION);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		distanceMeter = Brick.getInstance().getSampleProvider("S1");
	}

	
	@Override
	public void run() {
		try {
			// Move forward for n meters and scan environment (stop if cube is found)
			// Turn right by 90 degrees
			// Move forward for (m < n) meters
			// Turn right by 90 degrees
			// Move forward for n meters and scan environment (stop if cube is found)
			// Turn left by 90 degrees
			// Move forward for (m < n) meters
			// Turn left by 90 degrees
			
			int xLegCount = X_AXIS / X_LEG; 
			int yAxisAngle = calculateAngle(Y_AXIS);
			int xLegAngle = calculateAngle(X_LEG);
			
			for(int i = 0; i < xLegCount/2; i++) {
				moveForward(yAxisAngle);
				turnRight();
				moveForward(xLegAngle);
				turnRight();
				moveForward(yAxisAngle);
				turnLeft();
				moveForward(xLegAngle);
				turnLeft();
			}
		} catch(Exception e) {
			Brick.getInstance().closeMotors();
			Brick.getInstance().closeSensors();
		}
	}
	
	
	@Override
	public void shutdown() {
		Brick.getInstance().closeMotors();
		LOGGER.info("closeMotors="+true);
		Brick.getInstance().closeSensors();
		LOGGER.info("closeSensors="+true);
	}
	
	
	private int calculateAngle(int centimeters) {
		LOGGER.info("centimeters="+centimeters);
		
		double numberOfTurns = centimeters / CIRCUMFERENCE_CM;
		double angle = numberOfTurns * 360;
		
		LOGGER.info("angle="+angle);
		return (int)angle;
	}
	

	private void moveForward(int angle) throws RemoteException {
		LOGGER.info("angle="+angle);
		
		leftMotor.rotate(angle, true);
		rightMotor.rotate(angle, false);
	}

	
	private void turnLeft() throws RemoteException {
		LOGGER.info("RIGHT_ANGLE_DEGREES="+RIGHT_ANGLE_DEGREES);
		
		leftMotor.rotate(-RIGHT_ANGLE_DEGREES, true);
		rightMotor.rotate(RIGHT_ANGLE_DEGREES, false);
	}

	
	private void turnRight() throws RemoteException {
		LOGGER.info("RIGHT_ANGLE_DEGREES="+RIGHT_ANGLE_DEGREES);
		
		leftMotor.rotate(RIGHT_ANGLE_DEGREES, true);
		rightMotor.rotate(-RIGHT_ANGLE_DEGREES, false);
	}


	private void stop() throws RemoteException {
		leftMotor.stop(true);
		rightMotor.stop(true);
	}

	
	private float scan() throws RemoteException {
		float distance = distanceMeter.fetchSample()[0];
		return distance;
	}
	
	
}
