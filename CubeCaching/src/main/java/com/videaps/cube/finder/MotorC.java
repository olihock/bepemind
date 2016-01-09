package com.videaps.cube.finder;

import java.util.logging.Logger;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class MotorC implements JavaDelegate {
	private static final Logger logger = Logger.getLogger("com.videaps.cube.finder");

	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {
		logger.finest("delegateExecution="+delegateExecution);
	}

}
