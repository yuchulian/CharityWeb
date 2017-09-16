package com.jlqr.controller.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;

import com.jfinal.kit.PropKit;
import com.jlqr.common.ActivitiUtil;
import com.jlqr.common.ControllerUtil;
import com.jlqr.common.model.EmployInfo;
import com.jlqr.common.model.LoginInfo;
import com.jlqr.interceptor.NewService;

public class ActivitiData extends ControllerUtil {
	
	@NewService("TaskService")
	private TaskService taskService;

	@NewService("RepositoryService")
	private RepositoryService repositoryService;
	
	@NewService("RuntimeService")
	private RuntimeService runtimeService;
	
	@NewService("HistoryService")
	private HistoryService historyService;

	/* 部署流程定义 ************************************************************************************************************************************************************/
	/**
	 * 部署对象列表
	 */
	public void deploymentList() {
		List<Deployment> deploymentList = repositoryService.createDeploymentQuery().orderByDeploymenTime().desc().list();
		renderJson(ActivitiUtil.toDeploymentList(deploymentList));
	}
	
	/**
	 * 部署流程定义
	 */
	public void deploymentSave() {
		HashMap<String, Object> returnMap = getReturnMap();
		String fileName = StringUtils.trim(getPara("fileName")), filePath = StringUtils.trim(getPara("filePath"));
		File file = new File(PropKit.get("downloadPath")+filePath);
		try {
			ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));
			repositoryService.createDeployment().name(fileName).addZipInputStream(zipInputStream).deploy();
			returnMap.put("returnState", "success");
			returnMap.put("returnMsg", "部署成功");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		renderJson(returnMap);
	}
	
	/**
	 * 查看流程定义图片
	 */
	public void rocessDefinitionByDeploymentId() {
		String deploymentId = getPara("deploymentId");
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).singleResult();
		renderJson(processDefinition);
	}
	
	/**
	 * 查看流程图
	 */
	public void resourceAsStream() {
		String deploymentId = getPara("deploymentId"), imageName = getPara("imageName");
		InputStream in = repositoryService.getResourceAsStream(deploymentId, imageName);
//		OutputStream out = ServletActionContext.getResponse().getOutputStream();
//		for(int b=-1; (b=in.read())!=-1;){
//			out.write(b);
//		}
//		out.close();
	}
	
	/**
	 * 删除流程定义
	 */
	public void deploymentDelete() {
		HashMap<String, Object> returnMap = getReturnMap();
		String deploymentId = getPara("deploymentId");
		repositoryService.deleteDeployment(deploymentId, true);
		returnMap.put("returnState", "success");
		returnMap.put("returnMsg", "删除成功");
		renderJson(returnMap);
	}
	
	/* 任务管理 ************************************************************************************************************************************************************/
	

	/**
	 * 查询根据当前登录人获取任务
	 */
	public void taskList() {
		LoginInfo loginInfo = getSessionAttr("loginInfo");
		List<Task> taskList = taskService.createTaskQuery().taskAssignee(loginInfo.getId().toString()).orderByTaskCreateTime().desc().list();
		renderJson(ActivitiUtil.toTaskList(taskList));
	}
	
	/**
	 * 启动流程
	 */
	public void startProcess() {
		HashMap<String, Object> returnMap = getReturnMap();
		String processDefinitionId = getPara("processDefinitionId"), id = getPara("id");
		LoginInfo loginInfo = getSessionAttr("loginInfo");
		
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("loginId", loginInfo.getId());
		runtimeService.startProcessInstanceByKey(processDefinitionId, processDefinitionId+","+id, variables);
		returnMap.put("returnState", "success");
		returnMap.put("returnMsg", "启动成功");
		renderJson(returnMap);
	}
	
	/**
	 * 任务办理
	 */
	public void taskComplete() {
		HashMap<String, Object> returnMap = getReturnMap();
		String taskId = getPara("taskId"), id = getPara("id"), message = getPara("message"), sequenceFlow = getPara("sequenceFlow");
		EmployInfo employInfo = getSessionAttr("employInfo");
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		
		//添加批注
		if(StringUtils.isNotBlank(message)) {
			Authentication.setAuthenticatedUserId(employInfo.getEmployName());
			taskService.addComment(taskId, processInstanceId, message);
		}

		//完成任务
		Map<String, Object> variables = new HashMap<String, Object>();
		if(!StringUtils.equals("确定", sequenceFlow)) {
			variables.put("loginId", employInfo.getId());//需要改的…………………………………………………………………………………………………………
			variables.put("sequenceFlow", sequenceFlow);
		}
		taskService.complete(taskId, variables);
		
		//判断流程是否结束 需要修改为监听
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		if(null == processInstance) {
			//将业务表中的状态修改为通过
		}
		
		returnMap.put("returnState", "success");
		returnMap.put("returnMsg", "任务办理成功");
		renderJson(returnMap);
	}
	
	/**
	 * 获取历史批注列表
	 */
	public void commentList() {
		String processDefinitionId = getPara("processDefinitionId"), id = getPara("id");
		
		//获取历史流程实例
		HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(processDefinitionId+","+id).singleResult();
		String processInstanceId = historicProcessInstance.getId();
		
		List<Comment> commentList = taskService.getProcessInstanceComments(processInstanceId);
		renderJson(commentList);
	}

	/**
	 * 查看当前流程实例的任务进度
	 */
	public void taskActivity() {
		HashMap<String, Object> returnMap = getReturnMap();
		String taskId = getPara("taskId");
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processDefinitionId = task.getProcessDefinitionId();
		
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
		String deploymentId = processDefinition.getDeploymentId();
		String diagramResourceName = processDefinition.getDiagramResourceName();
		
		//根据以上信息，获取流程图。略……
		
		//获取当前任务的坐标
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
		String processInstanceId = task.getProcessInstanceId();
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		String activityId = processInstance.getActivityId();
		ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);
		returnMap.put("x", activityImpl.getX());
		returnMap.put("y", activityImpl.getY());
		returnMap.put("width", activityImpl.getWidth());
		returnMap.put("height", activityImpl.getHeight());
		
		returnMap.put("returnState", "success");
		returnMap.put("returnMsg", "获取成功");
		renderJson(returnMap);
	}
	
	/*************************************************************************************************************************************************************/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public void index() {
		String assignee = "张三";
		List<Task> taskList = taskService.createTaskQuery().taskAssignee(assignee).list();
		/*if (null != taskList) {
			for (Task task : taskList) {
				System.out.println("任务ID:" + task.getId());
				System.out.println("任务的办理人:" + task.getAssignee());
				System.out.println("任务名称:" + task.getName());
				System.out.println("任务的创建时间:" + task.getCreateTime());
				System.out.println("任务ID:" + task.getId());
				System.out.println("流程实例ID:" + task.getProcessInstanceId());
				System.out.println("#####################################");
			}

		}*/
//		JSONArray fromObject = JSONArray.fromObject(taskList);
		renderJson(ActivitiUtil.toTaskList(taskList));
	}
	
	public void findTaskList() {
		String assignee = "张三";
		List<Task> taskList = taskService.createTaskQuery().taskAssignee(assignee).list();
		/*if (null != taskList) {
			for (Task task : taskList) {
				System.out.println("任务ID:" + task.getId());
				System.out.println("任务的办理人:" + task.getAssignee());
				System.out.println("任务名称:" + task.getName());
				System.out.println("任务的创建时间:" + task.getCreateTime());
				System.out.println("任务ID:" + task.getId());
				System.out.println("流程实例ID:" + task.getProcessInstanceId());
				System.out.println("#####################################");
			}

		}*/
		renderJson("[3,4,9]");
	}
	
	
	
	
	
}
