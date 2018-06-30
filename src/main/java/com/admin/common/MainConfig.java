package com.admin.common;

import java.io.InputStream;
import java.util.Properties;

import com.admin.common.model.ActivityCommentView;
import com.admin.common.model.ActivityInfoView;
import com.admin.common.model.AttendUserInfoView;
import com.admin.common.model.ForumChannel;
import com.admin.common.model.ForumCommentView;
import com.admin.common.model.ForumInfoView;
import com.admin.common.model.LoginInfoView;
import com.admin.common.model.MangerInfoView;
import com.admin.common.model._MappingKit;
import com.admin.controller.data.ActivityInfoData;
import com.admin.controller.data.CarouseInfoData;
import com.admin.controller.data.ChannelData;
import com.admin.controller.data.DictionaryData;
import com.admin.controller.data.DownloadData;
import com.admin.controller.data.ForumInfoData;
import com.admin.controller.data.ForumManagerData;
import com.admin.controller.data.MangerInfoData;
import com.admin.controller.data.NewsInfoData;
import com.admin.controller.data.PowerInfoData;
import com.admin.controller.data.PublicManInfoData;
import com.admin.controller.data.RoleInfoData;
import com.admin.controller.data.UploadData;
import com.admin.controller.data.UserInfoData;
import com.admin.controller.data.WikipediaInfoData;
import com.admin.controller.page.ActivityInfoPage;
import com.admin.controller.page.CarouseInfoPage;
import com.admin.controller.page.ChannelPage;
import com.admin.controller.page.DictionaryPage;
import com.admin.controller.page.ForumInfoPage;
import com.admin.controller.page.ForumManagerPage;
import com.admin.controller.page.MangerInfoPage;
import com.admin.controller.page.NewsInfoPage;
import com.admin.controller.page.PowerInfoPage;
import com.admin.controller.page.PublicManInfoPage;
import com.admin.controller.page.RoleInfoPage;
import com.admin.controller.page.UserInfoPage;
import com.admin.controller.page.WikipediaInfoPage;
import com.admin.index.IndexController;
import com.admin.interceptor.ServaceInterceptor;
import com.home.index.HomeIndexController;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.cron4j.Cron4jPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;

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
		JFinal.start("src/main/webapp", 8080, "/", 5);
		
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
		//后台部分
		me.add("/Admin", IndexController.class, "/Admin/index");	// 第三个参数为该Controller的视图存放路径
		me.add("/Admin/powerInfoPage", PowerInfoPage.class, "/Admin/powerInfo");
		me.add("/Admin/powerInfoData", PowerInfoData.class, "/Admin/powerInfo");
		me.add("/Admin/dictionaryPage", DictionaryPage.class, "/Admin/dictionary");
		me.add("/Admin/dictionaryData", DictionaryData.class, "/Admin/dictionary");
		me.add("/Admin/mangerInfoPage", MangerInfoPage.class, "/Admin/mangerInfo");
		me.add("/Admin/mangerInfoData", MangerInfoData.class, "/Admin/mangerInfo");
		me.add("/Admin/roleInfoPage", RoleInfoPage.class, "/Admin/roleInfo");
		me.add("/Admin/roleInfoData", RoleInfoData.class, "/Admin/roleInfo");
		me.add("/Admin/carouseInfoData",CarouseInfoData.class,"/Admin/carouseInfo");
		me.add("/Admin/carouseInfoPage",CarouseInfoPage.class,"/Admin/carouseInfo");
		me.add("/Admin/newsInfoPage",NewsInfoPage.class,"/Admin/newsInfo");
		me.add("/Admin/newsInfoData",NewsInfoData.class,"/Admin/newsInfo");
		me.add("/Admin/activityInfoData",ActivityInfoData.class,"/Admin/activityInfo");
		me.add("/Admin/activityInfoPage",ActivityInfoPage.class,"/Admin/activityInfo");
		
		me.add("/Admin/forumInfoData",ForumInfoData.class,"/Admin/forumInfo");
		me.add("/Admin/forumInfoPage",ForumInfoPage.class,"/Admin/forumInfo");
		
		me.add("/Admin/channelData",ChannelData.class,"/Admin/forumInfo");
		me.add("/Admin/channelPage",ChannelPage.class,"/Admin/forumInfo");
		
		me.add("/Admin/userInfoData",UserInfoData.class,"/Admin/userInfo");
		me.add("/Admin/userInfoPage",UserInfoPage.class,"/Admin/userInfo");
		
		me.add("/Admin/publicManInfoData",PublicManInfoData.class,"/Admin/publicManInfo");
		me.add("/Admin/publicManInfoPage",PublicManInfoPage.class,"/Admin/publicManInfo");
		
		me.add("/Admin/wikipediaInfoData",WikipediaInfoData.class,"/Admin/wikipediaInfo");
		me.add("/Admin/wikipediaInfoPage",WikipediaInfoPage.class,"/Admin/wikipediaInfo");
		
		me.add("/Admin/forumManagerData",ForumManagerData.class,"/Admin/forumManager");
		me.add("/Admin/forumManagerPage",ForumManagerPage.class,"/Admin/forumManager");
		
		me.add("/uploadData", UploadData.class);
		me.add("/downloadData", DownloadData.class);
		//前台部分
		me.add("/", HomeIndexController.class, "/Home/index");
		
		
	}
	
	public void configEngine(Engine me) {
		me.addSharedFunction("/WEB-INF/page/Admin/common/_layout.html");
		me.addSharedFunction("/WEB-INF/page/Admin/common/_header.html");
		me.addSharedFunction("/WEB-INF/page/Admin/common/_activityPage.html");
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
		//设置定时器
		me.add(new Cron4jPlugin(PropKit.use("job.properties")));
		
		// 所有映射在 MappingKit 中自动化搞定
		_MappingKit.mapping(arp);
		arp.addMapping("login_info_view", "id", LoginInfoView.class);
		arp.addMapping("manger_info_view", "id", MangerInfoView.class);
		arp.addMapping("activity_info_view", "id", ActivityInfoView.class);
		arp.addMapping("forum_info_view", "id", ForumInfoView.class);
		arp.addMapping("forum_comment_view","id", ForumCommentView.class);
		arp.addMapping("activity_comment_view","id", ActivityCommentView.class);
		arp.addMapping("attend_user_info","id", AttendUserInfoView.class);
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
		me.add(new ClearActivitiMapHandler());
	}
}
