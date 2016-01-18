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
package com.videaps.cubefinder;

import java.util.logging.Logger;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.impl.el.FixedValue;

import com.videaps.mindstorms.ev3.SingletonEV3;

import lejos.hardware.DeviceException;


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
		
		SingletonEV3.intitialise(hostVar);

		char typeA = (""+motorA.getValue(execution)).toCharArray()[0];
		try {
			SingletonEV3.getInstance().createRegulatedMotor("A", typeA);
		} catch(DeviceException e) {
			logger.info("Motor A: "+e.getMessage());
		}
		
		char typeB = (""+motorB.getValue(execution)).toCharArray()[0];
		try {
			SingletonEV3.getInstance().createRegulatedMotor("B", typeB);
		} catch(DeviceException e) {
			logger.info("Motor B: "+e.getMessage());
		}

		char typeC = (""+motorC.getValue(execution)).toCharArray()[0];
		try {
			SingletonEV3.getInstance().createRegulatedMotor("C", typeC);
		} catch(DeviceException e) {
			logger.info("Motor C: "+e.getMessage());
		}

		// TODO Oliver Initialise sensor port here.
		
		logger.finest("exiting");
	}

}
