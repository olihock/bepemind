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
package com.videaps.cubefinder.delegates;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.el.JuelExpression;

import com.videaps.mindstorms.ev3.Brick;

import lejos.remote.ev3.RMIRegulatedMotor;

public class WalkDelegate implements JavaDelegate {

	/** Wheel circumference which is used to calculate the distance a wheel walks for one turn. */
	public static final int circumference = 10;
	
	private FixedValue motorPort;
	private JuelExpression immediateReturn;

	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		String motorPortValue = (String) motorPort.getValue(execution);
		Boolean immediateReturnValue = (Boolean) immediateReturn.getValue(execution);

		RMIRegulatedMotor motor = Brick.getInstance().getRegulatedMotor(motorPortValue);

		try {
			Integer distanceValue = execution.getVariable("distance", Integer.class);
			Integer rotationDegrees = ( distanceValue / circumference ) * 360;
			
			motor.rotate(rotationDegrees, immediateReturnValue);
		} catch(Exception e) {
			Brick.getInstance().closeMotors();
			Brick.getInstance().closeSensors();
			throw e;
		}

	}

}
