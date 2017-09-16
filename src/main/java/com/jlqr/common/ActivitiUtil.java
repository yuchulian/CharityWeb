package com.jlqr.common;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;

import com.jfinal.plugin.activerecord.Record;

/**
 * 一些Activiti的公共方法
 * @author LiQiRan
 * @date 2017-09-09 15:59:48
 */
public abstract class ActivitiUtil {
	
	/**
	 * 转换任务集合格式
	 * @param taskList
	 * @return
	 */
	public static List<Record> toTaskList(List<Task> taskList) {
		List<Record> recordList = new ArrayList<Record>();
		Record record = null;
		if(null != taskList) {
			for (Task task : taskList) {
				record = new Record();
				record.set("assignee", task.getAssignee());
				record.set("category", task.getCategory());
				record.set("createTime", SystemUtil.formatTime(task.getCreateTime()));
				record.set("delegationState", task.getDelegationState());
				record.set("description", task.getDescription());
				record.set("dueDate", SystemUtil.formatTime(task.getDueDate()));
				record.set("executionId", task.getExecutionId());
				record.set("formKey", task.getFormKey());
				record.set("id", task.getId());
				record.set("name", task.getName());
				record.set("owner", task.getOwner());
				record.set("parentTaskId", task.getParentTaskId());
				record.set("priority", task.getPriority());
				record.set("processDefinitionId", task.getProcessDefinitionId());
				record.set("processInstanceId", task.getProcessInstanceId());
				record.set("taskDefinitionKey", task.getTaskDefinitionKey());
//				record.set("taskLocalVariables", task.getTaskLocalVariables());
				record.set("tenantId", task.getTenantId());
				recordList.add(record);
			}
		}
		return recordList;
	}
	
	/**
	 * 转换部署流程集合格式
	 * @param deploymentList
	 * @return
	 */
	public static List<Record> toDeploymentList(List<Deployment> deploymentList) {
		List<Record> recordList = new ArrayList<Record>();
		Record record = null;
		if(null != deploymentList) {
			for (Deployment deployment : deploymentList) {
				record = new Record();
				record.set("gategory", deployment.getCategory());
				record.set("deploymentTime", SystemUtil.formatTime(deployment.getDeploymentTime()));
				record.set("id", deployment.getId());
				record.set("name", deployment.getName());
				record.set("tenantId", deployment.getTenantId());
				recordList.add(record);
			}
		}
		return recordList;
	
	}
	
	/**
	 * 转换流程定义集合格式
	 * @param processDefinitionList
	 * @return
	 */
	public static List<Record> toProcessDefinitionList(List<ProcessDefinition> processDefinitionList) {
		List<Record> recordList = new ArrayList<Record>();
		Record record = null;
		if(null != processDefinitionList) {
			for (ProcessDefinition processDefinition : processDefinitionList) {
				record = new Record();
				record.set("category", processDefinition.getCategory());
				record.set("deploymentId", processDefinition.getDeploymentId());
				record.set("description", processDefinition.getDescription());
				record.set("diagramResourceName", processDefinition.getDiagramResourceName());
				record.set("id", processDefinition.getId());
				record.set("key", processDefinition.getKey());
				record.set("name", processDefinition.getName());
				record.set("resourceName", processDefinition.getResourceName());
				record.set("tenantId", processDefinition.getTenantId());
				record.set("version", processDefinition.getVersion());
				recordList.add(record);
			}
		}
		return recordList;
		
	}
	
	/**
	 * 转换批注集合格式
	 * @param commentList
	 * @return
	 */
	public static List<Record> toCommentList(List<Comment> commentList) {
		List<Record> recordList = new ArrayList<Record>();
		Record record = null;
		if(null != commentList) {
			for (Comment comment : commentList) {
				record = new Record();
				record.set("fullMessage", comment.getFullMessage());
				record.set("id", comment.getId());
				record.set("processInstanceId", comment.getProcessInstanceId());
				record.set("taskId", comment.getTaskId());
				record.set("time", SystemUtil.formatTime(comment.getTime()));
				record.set("type", comment.getType());
				record.set("userId", comment.getUserId());
				recordList.add(record);
			}
		}
		return recordList;
		
	}
	
}
