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
package com.videaps.cube.solving.access.motor;

import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.el.FixedValue;


/**
 * This motor delegate class runs rotate functions provided by leJOS. It implements
 * the JavaDelegate, so parameters are passed in by the context. As of today, these values
 * are considered:
 * <ul>
 *  <li>Motor port: Motor port value which can be one out of A, B, C.
 *  <li>Acceleration: Value in degrees by which the motor reaches its speed.
 *  <li>Angle: Value in degrees by which the motor is turned.
 *  <li>Immediate return: Specification whether the motor turn is run synchronously (false) or asynchronously (true).
 * </ul>
 */
public class RotaterDelegate implements JavaDelegate {

	private FixedValue motorValue;
	private Expression accelerationValue;
	private Expression angleValue;
	private Expression immediateReturnValue;
	
	
	public void execute(DelegateExecution execution) throws Exception {
		NXTRegulatedMotor motor = getMotor((String) motorValue.getValue(execution));
		
		if(accelerationValue != null) {
			motor.setAcceleration((Integer) accelerationValue.getValue(execution));
		}

		int angle = 0;
		if(angleValue != null) {
			angle = (Integer) angleValue.getValue(execution);
		}
		
		// Default is waiting for the motor rotation to finish.
		boolean sync = true;
		if(immediateReturnValue != null) {
			sync = (Boolean) immediateReturnValue.getValue(execution);
		}

		motor.rotate(angle, sync);
	}

	
	private NXTRegulatedMotor getMotor(String motorPort) {
		if("A".equalsIgnoreCase(motorPort)) {
			return Motor.A;
		} else if("B".equalsIgnoreCase(motorPort)) {
			return Motor.B;
		} else if("C".equalsIgnoreCase(motorPort)) {
			return Motor.C;
		}
		return null;
	}
	
}
