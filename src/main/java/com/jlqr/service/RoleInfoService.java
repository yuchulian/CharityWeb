package com.jlqr.service;

import com.jlqr.common.ServiceUtil;
import com.jlqr.common.model.RoleInfo;

public class RoleInfoService extends ServiceUtil {
	public RoleInfo findRoleById(Integer id) throws Exception {
		return RoleInfo.dao.findById(id);
	}
}
