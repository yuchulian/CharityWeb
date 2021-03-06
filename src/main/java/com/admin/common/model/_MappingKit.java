package com.admin.common.model;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;

/**
 * Generated by JFinal, do not modify this file.
 * <pre>
 * Example:
 * public void configPlugin(Plugins me) {
 *     ActiveRecordPlugin arp = new ActiveRecordPlugin(...);
 *     _MappingKit.mapping(arp);
 *     me.add(arp);
 * }
 * </pre>
 */
public class _MappingKit {

	public static void mapping(ActiveRecordPlugin arp) {
		arp.addMapping("activity_comement_info", "id", ActivityComementInfo.class);
		arp.addMapping("activity_info", "id", ActivityInfo.class);
		arp.addMapping("activity_unit", "id", ActivityUnit.class);
		arp.addMapping("attachment_info", "id", AttachmentInfo.class);
		arp.addMapping("attend_info", "id", AttendInfo.class);
		arp.addMapping("carouse_info", "id", CarouseInfo.class);
		arp.addMapping("dictionary", "id", Dictionary.class);
		arp.addMapping("education_info", "id", EducationInfo.class);
		arp.addMapping("forum_channel", "id", ForumChannel.class);
		arp.addMapping("forum_comment", "id", ForumComment.class);
		arp.addMapping("forum_info", "id", ForumInfo.class);
		arp.addMapping("forum_manager_info", "id", ForumManagerInfo.class);
		arp.addMapping("item_info", "id", ItemInfo.class);
		arp.addMapping("login_info", "id", LoginInfo.class);
		arp.addMapping("manger_info", "id", MangerInfo.class);
		arp.addMapping("new_info", "id", NewInfo.class);
		arp.addMapping("power_info", "id", PowerInfo.class);
		arp.addMapping("public_man_info", "id", PublicManInfo.class);
		arp.addMapping("role_info", "id", RoleInfo.class);
		arp.addMapping("user_info", "id", UserInfo.class);
		arp.addMapping("wikipedia_info", "id", WikipediaInfo.class);
		arp.addMapping("work_info", "id", WorkInfo.class);
	}
}

