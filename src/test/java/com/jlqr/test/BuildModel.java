package com.jlqr.test;

import javax.sql.DataSource;

import com.admin.common.MainConfig;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.druid.DruidPlugin;

/**
 * 本 demo 仅表达最为粗浅的 jfinal 用法，更为有价值的实用的企业级用法
 * 详见 JFinal 俱乐部: http://jfinal.com/club
 * 
 * 在数据库表有任何变动时，运行一下 main 方法，极速响应变化进行代码重构
 */
public class BuildModel {
	
	public static DataSource getDataSource() {
		PropKit.use("prop_kit.properties");
		DruidPlugin druidPlugin = MainConfig.createDruidPlugin();
		druidPlugin.start();
		return druidPlugin.getDataSource();
	}
	
	public static void main(String[] args) {
		// base model 所使用的包名
		String baseModelPackageName = "com.admin.common.model.base";
		// base model 文件保存路径
		String baseModelOutputDir = PathKit.getWebRootPath() + "/src/main/java/com/admin/common/model/base";
		
		// model 所使用的包名 (MappingKit 默认使用的包名)
		String modelPackageName = "com.admin.common.model";
		// model 文件保存路径 (MappingKit 与 DataDictionary 文件默认保存路径)
		String modelOutputDir = baseModelOutputDir + "/..";
		
		// 创建生成器
		Generator generator = new Generator(getDataSource(), baseModelPackageName, baseModelOutputDir, modelPackageName, modelOutputDir);
		// 设置是否生成链式 setter 方法
		generator.setGenerateChainSetter(false);
		// 添加不需要生成的表名
		generator.addExcludedTable(
			"act_evt_log",
			"act_ge_bytearray", "act_ge_property",
			"act_hi_actinst", "act_hi_attachment", "act_hi_comment", "act_hi_detail", "act_hi_identitylink", "act_hi_procinst", "act_hi_taskinst", "act_hi_varinst",
			"act_id_group", "act_id_info", "act_id_membership", "act_id_user",
			"act_procdef_info",
			"act_re_deployment", "act_re_model", "act_re_procdef",
			"act_ru_event_subscr", "act_ru_execution", "act_ru_identitylink", "act_ru_job", "act_ru_task", "act_ru_variable"
		);
		// 设置是否在 Model 中生成 dao 对象
		generator.setGenerateDaoInModel(true);
		// 设置是否生成链式 setter 方法
		generator.setGenerateChainSetter(true);
		// 设置是否生成字典文件
		generator.setGenerateDataDictionary(false);
		// 设置需要被移除的表名前缀用于生成modelName。例如表名 "osc_user"，移除前缀 "osc_"后生成的model名为 "User"而非 OscUser
//		generator.setRemovedTableNamePrefixes("t_");
		// 生成
		generator.generate();
	}
}




