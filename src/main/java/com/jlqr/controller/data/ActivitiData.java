package com.jlqr.controller.data;

import java.util.List;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;

import com.jlqr.common.ActivitiUtil;
import com.jlqr.common.ControllerUtil;
import com.jlqr.interceptor.NewService;

public class ActivitiData extends ControllerUtil {
	
	@NewService("TaskService")
	private TaskService taskService;
	
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
