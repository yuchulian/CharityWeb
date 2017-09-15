package com.jlqr.controller.page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;

import com.jlqr.common.ControllerUtil;
import com.jlqr.interceptor.NewService;
import com.jlqr.service.PowerInfoService;

public class ActivitiPage extends ControllerUtil {

	@NewService("PowerInfoService")
	private PowerInfoService powerInfoService;
	
	@NewService("FormService")
	private FormService formService;
	
	@NewService("TaskService")
	private TaskService taskService;
	
	@NewService("RuntimeService")
	private RuntimeService runtimeService;
	
	@NewService("RepositoryService")
	private RepositoryService repositoryService;

	/* 部署流程定义 ************************************************************************************************************************************************************/
	/**
	 * 部署流程定义首页
	 */
	public void deploymentIndex() {
//		render("deploymentIndex.html");
	}
	
	/**
	 * 部署流程定义表单页面
	 */
	public void deploymentForm() {
//		render("deploymentForm.html");
	}

	
	
	/* 任务管理 ************************************************************************************************************************************************************/
	/**
	 * 查询根据当前登录人获取任务
	 */
	public void taskIndex() {
//		render("taskIndex.html");
	}
	
	/**
	 * 任务表单页面
	 */
	public void taskForm() {
//		render("deploymentForm.html");
	}
	
	/**
	 * 加载任务办理界面
	 */
	public void formKeyForm() {
		HashMap<String,Object> activitiMap = getReturnMap();
		String processDefinitionId = getPara("processDefinitionId"), taskDefinitionKey = getPara("taskId");
		String taskFormKey = formService.getTaskFormKey(processDefinitionId, taskDefinitionKey);
		
		//获取businessKey
		Task task = taskService.createTaskQuery().taskId(taskDefinitionKey).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		String businessKey = processInstance.getBusinessKey();
		
		//获取当前活动对象
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
		String activityId = processInstance.getActivityId();
		ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);
		
		//获取流程定义实体对象
		List<PvmTransition> pvmTransitionList = activityImpl.getOutgoingTransitions();
		
		//获取sequenceFlow
		List<String> sequenceFlowList = new ArrayList<String>();
		String sequenceFlow = "";
		for (PvmTransition pvmTransition : pvmTransitionList) {
			sequenceFlow = (String) pvmTransition.getProperty("name");
			if(StringUtils.isNotBlank(sequenceFlow)) {
				sequenceFlowList.add(sequenceFlow);
			} else {
				sequenceFlowList.add("确定");
			}
		}
		
		//获取批注列表
		List<Comment> commentList = taskService.getProcessInstanceComments(processInstanceId);
		
		activitiMap.put("returnState", "success");
		activitiMap.put("returnMsg", "操作成功");

		activitiMap.put("businessKey", businessKey);
		activitiMap.put("commentList", commentList);
		setSessionAttr("activitiMap", activitiMap);
		redirect(taskFormKey);//页面重定向
		
	}

	/*  ************************************************************************************************************************************************************/
	
}