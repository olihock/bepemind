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
package com.videaps.mindstorms.ral.motor;

import java.util.logging.Logger;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.el.JuelExpression;

import com.videaps.mindstorms.ev3.Brick;
import com.videaps.mindstorms.ral.PortDelegate;

import lejos.remote.ev3.RMIRegulatedMotor;


public class RotateDelegate extends PortDelegate {
	private static final Logger LOGGER = Logger.getLogger(RotateDelegate.class.getName());

	private JuelExpression acceleration;
	private JuelExpression angle;
	private JuelExpression immediateReturn;


	@Override
	public void execute(DelegateExecution execution) throws Exception {
		super.execute(execution);
		LOGGER.info(toString());
		
		int accelerationValue = mapAcceleration(execution);
		int angleValue = mapAngle(execution);
		boolean immediateReturnValue = mapImmediateReturn(execution);
		
		RMIRegulatedMotor motor = Brick.getInstance().getRegulatedMotor(getPortValue());
		motor.setAcceleration(accelerationValue);
		motor.rotate(angleValue, immediateReturnValue);
	}


	private boolean mapImmediateReturn(DelegateExecution execution) {
		Boolean immediateReturnValue = Boolean.FALSE;
		if(immediateReturn != null) {
			Object immediateReturnObject = immediateReturn.getValue(execution);
			if(immediateReturnObject != null) {
				immediateReturnValue = (Boolean) immediateReturnObject;
			}
		}
		return immediateReturnValue;
	}


	private int mapAngle(DelegateExecution execution) {
		Long angleValue = 0L;
		if(angle != null) {
			Object angleObject = angle.getValue(execution);
			if(angleObject instanceof Number) {
				angleValue = ((Number)angleObject).longValue();
			} else {
				angleValue = (Long) angleObject;
			}
		}
		return angleValue.intValue();
	}


	private int mapAcceleration(DelegateExecution execution) {
		int accelerationValue = Integer.MAX_VALUE;
		if(acceleration != null) {
			accelerationValue = ((Long) acceleration.getValue(execution)).intValue();
		}
		return accelerationValue;
	}
	

	@Override
	public String toString() {
		return "RotateDelegate [acceleration=" + acceleration + ", angle=" + angle + ", immediateReturn="
				+ immediateReturn + "]";
	}

}


