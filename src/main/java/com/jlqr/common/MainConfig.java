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
import com.jlqr.controller.data.BlogController;
import com.jlqr.controller.data.DictionaryData;
import com.jlqr.controller.data.EmployInfoData;
import com.jlqr.controller.data.EmployViewData;
import com.jlqr.controller.data.PowerInfoData;
import com.jlqr.controller.data.RoleInfoData;
import com.jlqr.controller.page.DictionaryPage;
import com.jlqr.controller.page.EmployInfoPage;
import com.jlqr.controller.page.PowerInfoPage;
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
		me.setBaseViewPath("/WEB-INF");
		me.add("/", IndexController.class, "/page/index");	// 第三个参数为该Controller的视图存放路径
		me.add("/blog", BlogController.class, "/page/blog");			// 第三个参数省略时默认与第一个参数值相同，在此即为 "/blog"
		me.add("/powerInfoPage", PowerInfoPage.class, "/page/powerInfo");
		me.add("/powerInfoData", PowerInfoData.class, "/page/powerInfo");
		me.add("/dictionaryPage", DictionaryPage.class, "/page/dictionary");
		me.add("/dictionaryData", DictionaryData.class, "/page/dictionary");
		me.add("/employInfoPage", EmployInfoPage.class, "/page/employInfo");
		me.add("/employInfoData", EmployInfoData.class, "/page/employInfo");
		me.add("/roleInfoPage", RoleInfoPage.class, "/page/roleInfo");
		me.add("/roleInfoData", RoleInfoData.class, "/page/roleInfo");
	}
	
	public void configEngine(Engine me) {
		me.addSharedFunction("/WEB-INF/page/common/_layout.html");
		me.addSharedFunction("/WEB-INF/page/common/_paginate.html");
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
