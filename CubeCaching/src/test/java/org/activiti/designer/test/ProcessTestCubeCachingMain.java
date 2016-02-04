package org.activiti.designer.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.io.FileInputStream;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;

public class ProcessTestCubeCachingMain {

	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();

	@Test
	public void startProcess() throws Exception {
		RepositoryService repositoryService = activitiRule.getRepositoryService();
		
		repositoryService.createDeployment()
			.addInputStream("cubeCachingMain.bpmn20.xml", new FileInputStream(ProcessTestConfig.BPMN_CUBE_CACHING_MAIN))
			.addInputStream("Wheeler.bpmn20.xml", new FileInputStream(ProcessTestConfig.BPMN_WHEELER))
		.deploy();
		
		RuntimeService runtimeService = activitiRule.getRuntimeService();
		Map<String, Object> variableMap = new HashMap<String, Object>();
		variableMap.put("name", "Activiti");
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("cubeCachingMain", variableMap);
		assertNotNull(processInstance.getId());
		System.out.println("id " + processInstance.getId() + " " + processInstance.getProcessDefinitionId());
	}
	
}