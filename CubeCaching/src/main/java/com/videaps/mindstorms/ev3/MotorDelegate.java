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
	public static final String FUNCTION_ROTATE_TO = "rotateTo";
	
	private FixedValue motorPort;
	private FixedValue motorFunction;
	private FixedValue tachoCount;
	private FixedValue immediateReturn;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		logger.finest("entering");
		
		String motorPortValue = ""+motorPort.getValue(execution);
		String motorFunctionValue = ""+motorFunction.getValue(execution);
		boolean immediateReturnValue = Boolean.valueOf(""+immediateReturn.getValue(execution));
		
		// This variable has been set in the Wheeler process, as it belongs to the motors
		// that drive the Cube Finder. For example, one turn of the wheels gets the wheeler 10 cm forward.
		String circumferenceVariable = ""+execution.getVariable("circumference");
		// FIXME Oliver geVariable return null, even though it is set in the dataObject of the calling process Wheeler.

		if(FUNCTION_ROTATE.equals(motorFunctionValue)) {
			// This variable has been set in the super process, as it defines the rectangle within the 
			// cube finder searches for the cube. For example it can be 100cm x 60cm. Here, only the distance
			// of one axis is set at a time, either x-axis or y-axis.
			Integer distanceValue = execution.getVariable("distance", Integer.class);
			Integer rotationDegrees = ( distanceValue / Integer.valueOf(circumferenceVariable) ) * 360;
			Brick.getInstance().getRegulatedMotor(motorPortValue).rotate(rotationDegrees, immediateReturnValue);
		} else if(FUNCTION_START.equals(motorFunctionValue)) {
			Brick.getInstance().getRegulatedMotor(motorPortValue).forward();
		} else if(FUNCTION_STOP.equals(motorFunctionValue)) {
			Brick.getInstance().getRegulatedMotor(motorPortValue).stop(immediateReturnValue);
		} else if(FUNCTION_ROTATE_TO.equals(motorFunctionValue)) {
			int tachoCountValue = Integer.valueOf(""+tachoCount.getValue(execution));
			Brick.getInstance().getRegulatedMotor(motorPortValue).rotateTo(tachoCountValue, immediateReturnValue);
		}
		
		logger.finest("exiting");
	}
	
	

}
