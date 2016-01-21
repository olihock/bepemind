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

import lejos.remote.ev3.RMISampleProvider;
import lejos.utility.Delay;


/**
 * This sensor delegate continuously scans what's in front of the infrared sensor and stops when
 * the given distance is succeeded.
 */
public class SensorDelegate implements JavaDelegate {
	private static final Logger logger = Logger.getLogger(SensorDelegate.class.getName());
	
	private FixedValue stopDistance;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		logger.entering(this.getClass().getName(), "execute");
		
		float stopDistanceValue = Float.valueOf(""+stopDistance.getValue(execution));
		float currentDistance = stopDistanceValue + 1;

		while(currentDistance > stopDistanceValue) {
			RMISampleProvider sampleProvider = SingletonEV3.getInstance().getSampleProvider("S1");
			currentDistance = sampleProvider.fetchSample()[0];
			logger.info("currentDistance="+currentDistance);
			Delay.msDelay(100);
		}
	}

}
