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
package com.videaps.mindstorms.ral.sensor;

import java.util.logging.Logger;

import org.activiti.engine.delegate.DelegateExecution;

import com.videaps.mindstorms.ev3.Brick;
import com.videaps.mindstorms.ral.PortDelegate;

import lejos.remote.ev3.RMISampleProvider;


public class FetchSampleDelegate extends PortDelegate {
	private static final Logger LOGGER = Logger.getLogger(FetchSampleDelegate.class.getName());

	private float currentDistance = 0.0f;
	
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		super.execute(execution);
		
		RMISampleProvider sampleProvider = Brick.getInstance().getSampleProvider(getPortValue());
		currentDistance = sampleProvider.fetchSample()[0];
		
		LOGGER.info(toString());
	}

	
	@Override
	public String toString() {
		return "FetchSampleDelegate [currentDistance=" + currentDistance + "]";
	}

}
