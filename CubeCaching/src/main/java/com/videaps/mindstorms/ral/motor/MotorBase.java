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

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.el.JuelExpression;

import com.videaps.mindstorms.ral.BrickBase;


public class MotorBase extends BrickBase {
	private JuelExpression acceleration;

	private Long accelerationValue = 0L;
	
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		super.execute(execution);

		if(acceleration != null) {
			accelerationValue = (Long) acceleration.getValue(execution);
		}
	}
	
	
	@Override
	public String toString() {
		return super.toString() + " MotorBase [accelerationValue=" + accelerationValue + "]";
	}


	public Long getAccelerationValue() {
		return accelerationValue;
	}

}
