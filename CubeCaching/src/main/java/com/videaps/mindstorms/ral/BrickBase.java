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
package com.videaps.mindstorms.ral;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.impl.el.FixedValue;


public class BrickBase implements JavaDelegate {
	private FixedValue port;

	private String processDefinition;
	private String activityName;
	private String portValue;
	
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		processDefinition = execution.getProcessDefinitionId();
		activityName = execution.getCurrentActivityName();
		portValue = (String) port.getValue(execution);
	}


	@Override
	public String toString() {
		return "BrickBase [processDefinition=" + processDefinition + ", activityName=" + activityName + ", portValue=" + portValue + "]";
	}


	public String getProcessDefinition() {
		return processDefinition;
	}
	public String getActivityName() {
		return activityName;
	}

	public String getPortValue() {
		return portValue;
	}
}
