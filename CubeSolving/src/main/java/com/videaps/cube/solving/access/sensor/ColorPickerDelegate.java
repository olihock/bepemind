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
package com.videaps.cube.solving.access.sensor;

import lejos.nxt.I2CPort;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.ColorHTSensor;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.el.FixedValue;


/**
 * This color picker delegate read a color from any surface and returns the color ID, which 
 * can be one out of
<pre> 
	0 = red
	1 = green
	2 = blue
	3 = yellow
	4 = magenta
	5 = orange
	6 = white
	7 = black
	8 = pink
	9 = gray
	10 = light gray
	11 = dark gray
	12 = cyan
</pre>
 */
public class ColorPickerDelegate implements JavaDelegate {

	private FixedValue sensorPort;
	
	
	public void execute(DelegateExecution execution) throws Exception {
		ColorHTSensor sensor = new ColorHTSensor(getSensorPort((String) sensorPort.getValue(execution)));
		
		int colorId = sensor.getColorID();
		execution.setVariable("colorId", colorId);
	}


	private I2CPort getSensorPort(String sensorPort) {
		if("S1".equalsIgnoreCase(sensorPort)) {
			return SensorPort.S1;
		} else if("S2".equalsIgnoreCase(sensorPort)) {
			return SensorPort.S2;
		} else if("S3".equalsIgnoreCase(sensorPort)) {
			return SensorPort.S3;
		} else if("S4".equalsIgnoreCase(sensorPort)) {
			return SensorPort.S4;
		}
		return null;
	}

	
	
}
