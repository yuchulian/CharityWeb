package com.jlqr.interceptor;

import java.lang.reflect.Field;
import java.util.HashMap;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jlqr.common.Log4jUtils;

public class ServaceInterceptor implements Interceptor {
	private static HashMap<String, Object> map = new HashMap<>();
	@Override
	public void intercept(Invocation invocation) {
		Class<? extends Controller> class1 = invocation.getController().getClass();
		Field[] fields = class1.getDeclaredFields();
		for (Field field : fields) {
			if(field.isAnnotationPresent(NewService.class)){
				NewService annotation = field.getAnnotation(NewService.class);
				String servicePackage = PropKit.get("servicepackage") + "." + annotation.value();
				try {
					Object service = null;
					if (map.get(servicePackage) == null ) {
						Class<?> c = Class.forName(servicePackage);
						service = c.newInstance();
						map.put(servicePackage, service);
					} else {
						service = map.get(servicePackage);
					}
					field.setAccessible(true);
					field.set(invocation.getController(), service);
				} catch (ClassNotFoundException e) {
					Log4jUtils.logger.error("NewService这个注解错误：没有" + servicePackage + "这个类",e);
				} catch (IllegalArgumentException | IllegalAccessException | InstantiationException e) {
					Log4jUtils.logger.error("",e);
				}
			}
		}
		invocation.invoke();
	}

}
