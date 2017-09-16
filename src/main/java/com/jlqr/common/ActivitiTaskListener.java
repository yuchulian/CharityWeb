package com.jlqr.common;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class ActivitiTaskListener implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
		System.out.println("任务监听："+delegateTask.getExecutionId());
//		LoginInfo loginInfo = getSessionAttr("loginInfo");
//		delegateTask.setAssignee(assignee);
	}

}
