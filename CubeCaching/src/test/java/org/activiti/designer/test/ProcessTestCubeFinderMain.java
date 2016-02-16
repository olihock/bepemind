package org.activiti.designer.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.rmi.RemoteException;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;

import com.videaps.mindstorms.ev3.Brick;

public class ProcessTestCubeFinderMain {

	private String mainFileName = "C:\\Users\\Oliver\\Development\\github\\bepemind\\CubeCaching\\src\\main\\resources\\diagrams\\cubefinder\\CubeFinderMain.bpmn";
	private String searchFileName = "C:\\Users\\Oliver\\Development\\github\\bepemind\\CubeCaching\\src\\main\\resources\\diagrams\\cubefinder\\Search.bpmn";
	private String walkFileName = "C:\\Users\\Oliver\\Development\\github\\bepemind\\CubeCaching\\src\\main\\resources\\diagrams\\cubefinder\\Walk.bpmn";
	private String turnFileName = "C:\\Users\\Oliver\\Development\\github\\bepemind\\CubeCaching\\src\\main\\resources\\diagrams\\cubefinder\\Turn.bpmn";
	private String walkRightAndDownFileName = "C:\\Users\\Oliver\\Development\\github\\bepemind\\CubeCaching\\src\\main\\resources\\diagrams\\cubefinder\\WalkRightAndUp.bpmn";
	private String walkRightAndUpFileName = "C:\\Users\\Oliver\\Development\\github\\bepemind\\CubeCaching\\src\\main\\resources\\diagrams\\cubefinder\\WalkRightAndDown.bpmn";
	private String scanFileName = "C:\\Users\\Oliver\\Development\\github\\bepemind\\CubeCaching\\src\\main\\resources\\diagrams\\cubefinder\\Scan.bpmn";

	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();

	@Test
	public void startProcess() throws RemoteException {
		RepositoryService repositoryService = activitiRule.getRepositoryService();

		try {
			repositoryService.createDeployment()
				.addInputStream("cubeFinderMain.bpmn20.xml", new FileInputStream(mainFileName))
				.addInputStream("search.bpmn20.xml", new FileInputStream(searchFileName))
				.addInputStream("walk.bpmn20.xml", new FileInputStream(walkFileName))
				.addInputStream("turn.bpmn20.xml", new FileInputStream(turnFileName))
				.addInputStream("walkRightAndDown.bpmn20.xml", new FileInputStream(walkRightAndDownFileName))
				.addInputStream("walkRightAndUp.bpmn20.xml", new FileInputStream(walkRightAndUpFileName))
				.addInputStream("scan.bpmn20.xml", new FileInputStream(scanFileName))
			.deploy();
		} catch (FileNotFoundException e) {
			e.printStackTrace(System.err);
			assertFalse(e.getMessage(), true);
		}
		
		try {
			RuntimeService runtimeService = activitiRule.getRuntimeService();
			Map<String, Object> variableMap = new HashMap<String, Object>();
			variableMap.put("name", "Activiti");
			ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("cubeFinderMain", variableMap);
			assertNotNull(processInstance.getId());
			System.out.println("id " + processInstance.getId() + " " + processInstance.getProcessDefinitionId());
		} catch(Exception e) {
			Brick.getInstance().closeMotors();
			Brick.getInstance().closeSensors();
			e.printStackTrace(System.err);
			assertFalse(e.getMessage(), true);
		}
	}
}