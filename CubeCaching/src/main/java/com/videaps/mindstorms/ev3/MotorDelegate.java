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

import java.util.logging.Logger;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.impl.el.FixedValue;

import lejos.robotics.RegulatedMotor;


/**
 * Motor delegate class to implement Activiti Service Task activity.
 * 
 * This delegate reads the motor port (A, B, C or D) of the EV3 brick, type (Large or Medium) 
 * and the number of rotations the motor is supposed to conduct. 
 * 
 * @author Oliver
 *
 */
public class MotorDelegate implements JavaDelegate {
	private static final Logger logger = Logger.getLogger("com.videaps.mindstorms.ev3");
	
	private FixedValue motorPort;
	private FixedValue motorType;
	private FixedValue rotationCount;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		logger.finest("entering");
		
		String motorPortValue = ""+motorPort.getValue(execution);
		String motorTypeValue = ""+motorType.getValue(execution);
		int rotationCountValue = Integer.valueOf(""+rotationCount.getValue(execution));

		MotorInfo<String, String> motorInfo = new MotorInfo<String, String>(motorPortValue, motorTypeValue);
		RegulatedMotor motor = BrickSingleton.getInstance(motorInfo).getRegulatedMotor(motorPortValue);
		motor.rotate(rotationCountValue*360, true);
		
		logger.finest("exiting");
	}
	
	

}
