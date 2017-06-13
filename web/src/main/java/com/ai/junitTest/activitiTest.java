package com.ai.junitTest;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;


public class activitiTest{
	@Test
	public void test3() {
		ProcessEngine engine=ProcessEngines.getDefaultProcessEngine();
		//RuntimeService RuntimeService = processEngine.getRuntimeService();
		RepositoryService rs=engine.getRepositoryService();
		RuntimeService rse=engine.getRuntimeService();
		TaskService ts=engine.getTaskService();
		//rs.createDeployment().addClasspathResource("config/bpmn/MyProcess.bpmn").deploy();
		//rse.startProcessInstanceByKey("config/bpmn/MyProcess.bpmn");
		//List<Task> tasks = ts.createTaskQuery().taskCandidateGroup(groupA.getId()).list();
	    
		Task task=ts.createTaskQuery().singleResult();
		System.out.println("第一个流程任务完成前"+task.getName());
		ts.complete(task.getId());
		XmlBeanFactory b;
	}
}
