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
package com.videaps.cubefinder;

import java.util.logging.Logger;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.impl.el.FixedValue;

import com.videaps.mindstorms.ev3.SingletonEV3;

import lejos.hardware.DeviceException;
import lejos.remote.ev3.RMIRegulatedMotor;


/**
 * Initialiser to set up all ports of the Cube Finder, which is port A, B, C for motors 
 * and S1 for sensor. 
 * 
 * @author Oliver
 */
public class Initialiser implements JavaDelegate {
	private static final Logger logger = Logger.getLogger(Initialiser.class.getName());

	private FixedValue motorA;
	private FixedValue motorB;
	private FixedValue motorC;
	
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		logger.finest("entering");
		
		String hostVar = ""+execution.getVariable("host");
		logger.finer("hostVar="+hostVar);
		
		SingletonEV3 ev3 = SingletonEV3.intitialise(hostVar);

		char typeA = (""+motorA.getValue(execution)).toCharArray()[0];
		try {
			ev3.createRegulatedMotor("A", typeA);
		} catch(DeviceException e) {
			logger.info("Motor A: "+e.getMessage());
		}
		
		char typeB = (""+motorB.getValue(execution)).toCharArray()[0];
		try {
			RMIRegulatedMotor motor = ev3.createRegulatedMotor("B", typeB);
			motor.setAcceleration(360);
		} catch(DeviceException e) {
			logger.info("Motor B: "+e.getMessage());
		}

		char typeC = (""+motorC.getValue(execution)).toCharArray()[0];
		try {
			RMIRegulatedMotor motor = ev3.createRegulatedMotor("C", typeC);
			motor.setAcceleration(360);
		} catch(DeviceException e) {
			logger.info("Motor C: "+e.getMessage());
		}

		ev3.createSampleProvider("S1", "lejos.hardware.sensor.EV3IRSensor", "Distance");
		
		logger.finest("exiting");
	}

}
