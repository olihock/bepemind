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

import java.util.logging.Logger;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.impl.el.FixedValue;


/**
 * Motor delegate class to implement Activiti Service Task activity.
 * 
 * This delegate reads the motor port (A, B, C or D), motor type (Large or Medium)
 * and the number of rotations the motor is supposed to conduct from the process
 * and starts the motor. It stops automatically after the given number of rotations.
 */
public class MotorDelegate implements JavaDelegate {
	private static final Logger logger = Logger.getLogger(MotorDelegate.class.getName());
	
	public static final String FUNCTION_ROTATE = "rotate";
	public static final String FUNCTION_START = "start";
	public static final String FUNCTION_STOP = "stop";
	
	private FixedValue motorPort;
	private FixedValue motorFunction;
	private FixedValue rotationCount;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		logger.finest("entering");
		
		String motorPortValue = ""+motorPort.getValue(execution);
		String motorFunctionValue = ""+motorFunction.getValue(execution);

		if(FUNCTION_ROTATE.equals(motorFunctionValue)) {
			int rotationCountValue = Integer.valueOf(""+rotationCount.getValue(execution));
			SingletonEV3.getInstance().getRegulatedMotor(motorPortValue).rotate(rotationCountValue*360, true);
		} else if(FUNCTION_START.equals(motorFunctionValue)) {
			SingletonEV3.getInstance().getRegulatedMotor(motorPortValue).forward();
		} else if(FUNCTION_STOP.equals(motorFunctionValue)) {
			SingletonEV3.getInstance().getRegulatedMotor(motorPortValue).stop(true);
		}
		
		logger.finest("exiting");
	}
	
	

}
