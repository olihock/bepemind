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

public class ProcessTestSandboxMain {

	private final String path = "C:\\Users\\Oliver\\Development\\github\\bepemind\\CubeCaching\\src\\main\\resources\\diagrams\\sandbox\\";
	private String sandboxMain = path+"SandboxMain.bpmn";
	private String walking = path+"Walking.bpmn";

	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();

	@Test
	public void startProcess() throws Exception {
		RepositoryService repositoryService = activitiRule.getRepositoryService();
		
		repositoryService.createDeployment()
			.addInputStream("SandboxMain.bpmn20.xml",	new FileInputStream(sandboxMain))
			.addInputStream("Walking.bpmn20.xml",	new FileInputStream(walking))
		.deploy();
		
		RuntimeService runtimeService = activitiRule.getRuntimeService();
		Map<String, Object> variableMap = new HashMap<String, Object>();
		variableMap.put("name", "Activiti");
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("sandboxMain", variableMap);
		assertNotNull(processInstance.getId());
		System.out.println("id " + processInstance.getId() + " "
				+ processInstance.getProcessDefinitionId());
	}
}