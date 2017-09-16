package com.jlqr.common;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;
import com.jlqr.common.model.EmployView;
import com.jlqr.common.model._MappingKit;
import com.jlqr.controller.data.ActivitiData;
import com.jlqr.controller.data.DictionaryData;
import com.jlqr.controller.data.DownloadData;
import com.jlqr.controller.data.EmployInfoData;
import com.jlqr.controller.data.PowerInfoData;
import com.jlqr.controller.data.ProjectInfoData;
import com.jlqr.controller.data.RoleInfoData;
import com.jlqr.controller.data.UploadData;
import com.jlqr.controller.page.ActivitiPage;
import com.jlqr.controller.page.DictionaryPage;
import com.jlqr.controller.page.EmployInfoPage;
import com.jlqr.controller.page.PowerInfoPage;
import com.jlqr.controller.page.ProjectInfoPage;
import com.jlqr.controller.page.RoleInfoPage;
import com.jlqr.index.IndexController;
import com.jlqr.interceptor.ServaceInterceptor;

/**
 * 本 demo 仅表达最为粗浅的 jfinal 用法，更为有价值的实用的企业级用法
 * 详见 JFinal 俱乐部: http://jfinal.com/club
 * 
 * API引导式配置
 */
public class MainConfig extends JFinalConfig {
	
	/**
	 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 * 
	 * 使用本方法启动过第一次以后，会在开发工具的 debug、run config 中自动生成
	 * 一条启动配置，可对该自动生成的配置再添加额外的配置项，例如 VM argument 可配置为：
	 * -XX:PermSize=64M -XX:MaxPermSize=256M
	 */
	public static void main(String[] args) {
		/**
		 * 特别注意：Eclipse 之下建议的启动方式
		 */
		JFinal.start("src/main/webapp", 80, "/", 5);
		
		/**
		 * 特别注意：IDEA 之下建议的启动方式，仅比 eclipse 之下少了最后一个参数
		 */
		// JFinal.start("src/main/webapp", 80, "/");
	}
	
	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		// 加载少量必要配置，随后可用PropKit.get(...)获取值
		PropKit.use("prop_kit.properties");
		me.setDevMode(PropKit.getBoolean("devMode", false));
	}
	
	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		me.setBaseViewPath("/WEB-INF/page");
		me.add("/", IndexController.class, "/index");	// 第三个参数为该Controller的视图存放路径
		me.add("/powerInfoPage", PowerInfoPage.class, "/powerInfo");
		me.add("/powerInfoData", PowerInfoData.class, "/powerInfo");
		me.add("/dictionaryPage", DictionaryPage.class, "/dictionary");
		me.add("/dictionaryData", DictionaryData.class, "/dictionary");
		me.add("/employInfoPage", EmployInfoPage.class, "/employInfo");
		me.add("/employInfoData", EmployInfoData.class, "/employInfo");
		me.add("/roleInfoPage", RoleInfoPage.class, "/roleInfo");
		me.add("/roleInfoData", RoleInfoData.class, "/roleInfo");
		me.add("/projectInfoPage", ProjectInfoPage.class, "/projectInfo");
		me.add("/projectInfoData", ProjectInfoData.class, "/projectInfo");

		me.add("/uploadData", UploadData.class);
		me.add("/downloadData", DownloadData.class);
		
		me.add("/activitiPage", ActivitiPage.class, "/activiti");//用于流程操作
		me.add("/activitiData", ActivitiData.class);//用于流程操作, "/activiti"
	}
	
	public void configEngine(Engine me) {
		me.addSharedFunction("/WEB-INF/page/common/_layout.html");
	}
	
	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		// 配置 druid 数据库连接池插件
		DruidPlugin druidPlugin = new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
		me.add(druidPlugin);
		
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		
		// 显示sql记录
		arp.setShowSql(PropKit.getBoolean("devMode", false));
		
		// 所有映射在 MappingKit 中自动化搞定
		_MappingKit.mapping(arp);
		arp.addMapping("employ_view","id",EmployView.class);
		me.add(arp);
	}
	
	public static DruidPlugin createDruidPlugin() {
		return new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
	}
	
	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		me.add(new SessionInViewInterceptor());
		me.add(new ServaceInterceptor());
	}
	
	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
		
	}
}
