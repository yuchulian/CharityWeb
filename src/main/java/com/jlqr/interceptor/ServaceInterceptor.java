package com.jlqr.interceptor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jlqr.common.SystemUtil;

public class ServaceInterceptor implements Interceptor {
	private static HashMap<String, Object> map = new HashMap<>();

	@Override
	public void intercept(Invocation invocation) {
		Class<? extends Controller> class1 = invocation.getController().getClass();

		Field[] fields = class1.getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(NewService.class)) {
				NewService annotation = field.getAnnotation(NewService.class);

				String value = annotation.value();

				if (",DynamicBpmnService,FormService,HistoryService,IdentityService,ManagementService,RepositoryService,RuntimeService,TaskService,".indexOf("," + value + ",") > -1) {
					try {
						Class clazz = SystemUtil.processEngine.getClass();
						Method method = clazz.getMethod("get" + value);
						Object o;
						o = method.invoke(SystemUtil.processEngine);
						field.setAccessible(true);
						field.set(invocation.getController(), o);
					} catch (IllegalAccessException e) {
						SystemUtil.logger.error("", e);
					} catch (IllegalArgumentException e) {
						SystemUtil.logger.error("", e);
					} catch (InvocationTargetException e) {
						SystemUtil.logger.error("", e);
					} catch (NoSuchMethodException e) {
						SystemUtil.logger.error("", e);
					} catch (SecurityException e) {
						SystemUtil.logger.error("", e);
					}
				} else {
					String servicePackage = PropKit.get("servicepackage") + "." + annotation.value();
					try {
						Object service = null;
						if (map.get(servicePackage) == null) {
							Class<?> c = Class.forName(servicePackage);
							service = c.newInstance();
							map.put(servicePackage, service);
						} else {
							service = map.get(servicePackage);
						}
						field.setAccessible(true);
						field.set(invocation.getController(), service);
					} catch (ClassNotFoundException e) {
						SystemUtil.logger.error("NewService这个注解错误：没有" + servicePackage + "这个类", e);
					} catch (IllegalArgumentException | IllegalAccessException | InstantiationException e) {
						SystemUtil.logger.error("", e);
					}
				}

			}
		}
		invocation.invoke();
	}

}
