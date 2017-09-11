package com.jlqr.common;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.task.Task;

import com.jfinal.plugin.activerecord.Record;

/**
 * 一些Activiti的公共方法
 * @author LiQiRan
 * @date 2017-09-09 15:59:48
 */
public abstract class ActivitiUtil {
	
	public static List<Record> toTaskList(List<Task> taskList) {
		List<Record> recordList = new ArrayList<Record>();
		Record record = null;
		for (Task task : taskList) {
			record = new Record();
			record.set("assignee", task.getAssignee());
			record.set("category", task.getCategory());
			record.set("createTime", task.getCreateTime());
			record.set("delegationState", task.getDelegationState());
			record.set("description", task.getDescription());
			record.set("dueDate", task.getDueDate());
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
			record.set("taskLocalVariables", task.getTaskLocalVariables());
			record.set("tenantId", task.getTenantId());
			recordList.add(record);
		}
		return recordList;
	}
	
}
