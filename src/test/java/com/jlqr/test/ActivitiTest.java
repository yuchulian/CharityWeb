package com.jlqr.test;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class ActivitiTest {
	/** 获得流程引擎 */
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	/*@Test
	public void initActiviti() {
		ProcessEngine processEngine = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("activiti.cfg.xml").buildProcessEngine();
	}*/

	/**
	 * 部署流程定义
	 */
	@Test
	public void createProcess() {
		Deployment deployment = processEngine.getRepositoryService()// 获取流程定义和部署相关的Service
				.createDeployment()// 创建部署对象
				.addClasspathResource("testProcess.bpmn").addClasspathResource("testProcess.png").name("请假程序").deploy();// 完成部署
//		Deployment deployment = processEngine.getRepositoryService()// 获取流程定义和部署相关的Service
//				.createDeployment()// 创建部署对象
//				.addClasspathResource("ProjectInfoProcess.bpmn").addClasspathResource("ProjectInfoProcess.png").name("请假程序").deploy();// 完成部署
		System.out.println(deployment.getId());
		System.out.println(deployment.getName());
	}

	/**
	 * 启动流程实例
	 */
	@Test
	public void startProcess() {
		String processKey = "`project_info`";
//		String processKey = "myProcess";
		ProcessInstance processInstance = processEngine.getRuntimeService()// 获取流程实例对象
				.startProcessInstanceByKey(processKey);
		System.out.println("流程实例ID：" + processInstance.getId());// 流程实例ID：101
		System.out.println("流程实例ID：" + processInstance.getProcessInstanceId());// 流程实例ID：101
		System.out.println("流程实例ID:" + processInstance.getProcessDefinitionId());// myMyHelloWorld:1:4
	}

	/**
	 * 查看个人任务
	 */
	@Test
	public void findTaskList() {
		String assignee = "张三";
		List<Task> taskList = processEngine.getTaskService().createTaskQuery().taskAssignee(assignee).list();
		if (null != taskList) {
			for (Task task : taskList) {
				System.out.println("任务ID:" + task.getId());
				System.out.println("任务的办理人:" + task.getAssignee());
				System.out.println("任务名称:" + task.getName());
				System.out.println("任务的创建时间:" + task.getCreateTime());
				System.out.println("任务ID:" + task.getId());
				System.out.println("流程实例ID:" + task.getProcessInstanceId());
				System.out.println("#####################################");
			}

		}
	}
	
	/**
	 * 完成个人任务
	 */
	@Test
	public void completeTask() {
		String taskId = "2504";
		processEngine.getTaskService().complete(taskId);
		System.out.println("完成任务："+taskId);
	}
}
