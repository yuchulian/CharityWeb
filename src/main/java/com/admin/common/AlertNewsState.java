package com.admin.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

import com.jfinal.plugin.cron4j.ITask;


/**
 * @author yuchulian
 *定时更新新闻数据
 *
 */
public class AlertNewsState  implements ITask{
	Logger log = Logger.getLogger(AlertNewsState.class);
	@Override
	public void run() {
		Connection con = null; 
		PreparedStatement pre = null;
		ResultSet rs = null;
		PreparedStatement updatepre = null;
		con = JdbcUtil.getConnetion();
		String sql = "select * from activity_info where activity_state in (?,?,?,?) and activity_check_state = ?";
		String updatesql = "update activity_info set activity_state = ? where id = ?";
		try {
			System.out.println("更新新闻开始....");
			log.info(new java.util.Date()+"更新新闻开始");
			pre= con.prepareStatement(sql);
			pre.setInt(1, 0);
			pre.setInt(2, 1);
			pre.setInt(3, 2);
			pre.setInt(4, 3);
			pre.setInt(5, 1);
			rs = pre.executeQuery();
			Long nowTimeStamp =Long.parseLong(TimeUtils.timeStamp());
			while(rs.next()){
				//获取id值
				int id = rs.getInt("id");
				int state = 0;
				//获取prestatement				
				//活动结束时间
				Long activityEndTime = getTimeStamp("activity_end_time", rs);
				//报名结束时间
				Long enrolEndtime = getTimeStamp("activity_enrol_endtime", rs);
				//报名开始时间
				Long enrolStarttime = getTimeStamp("activity_enrol_starttime", rs);
				//活动开始时间
				Long activityBeginTime = getTimeStamp("activity_begin_time", rs);
				if(nowTimeStamp>activityEndTime){
					state = 4;
				}else if(nowTimeStamp>enrolEndtime) {
					state = 3;
				}else if(nowTimeStamp>enrolStarttime){
					state = 2;
				}else if(nowTimeStamp>activityBeginTime){
					state = 1;
				}
				//进行修改数据
				updatepre = con.prepareStatement(updatesql);
				updatepre.setInt(1,state);
				updatepre.setInt(2, id);
				updatepre.executeUpdate();
				System.out.println("更新新闻结束....");
				log.info(new java.util.Date()+"更新新闻结束");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JdbcUtil.close(updatepre);
			JdbcUtil.close(rs);
			JdbcUtil.close(pre);
			JdbcUtil.close(con);
		}
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
	/*@Test
	public void test1(){
		Connection con = null; 
		PreparedStatement pre = null;
		ResultSet rs = null;
		PreparedStatement updatepre = null;
		con = JdbcUtil.getConnetion();
		String sql = "select * from activity_info where activity_state in (?,?,?,?) and activity_check_state = ?";
		String updatesql = "update activity_info set activity_state = ? where id = ?";
		try {
			System.out.println("更新新闻开始....");
			pre= con.prepareStatement(sql);
			pre.setInt(1, 0);
			pre.setInt(2, 1);
			pre.setInt(3, 2);
			pre.setInt(4, 3);
			pre.setInt(5, 1);
			rs = pre.executeQuery();
			Long nowTimeStamp =Long.parseLong(TimeUtils.timeStamp());
			while(rs.next()){
				//获取id值
				int id = rs.getInt("id");
				int state = 0;
				//获取prestatement				
				//活动结束时间
				Long activityEndTime = getTimeStamp("activity_end_time", rs);
				//报名结束时间
				Long enrolEndtime = getTimeStamp("activity_enrol_endtime", rs);
				//报名开始时间
				Long enrolStarttime = getTimeStamp("activity_enrol_starttime", rs);
				//活动开始时间
				Long activityBeginTime = getTimeStamp("activity_begin_time", rs);
				if(nowTimeStamp>activityEndTime){
					state = 4;
				}else if(nowTimeStamp>enrolEndtime) {
					state = 3;
				}else if(nowTimeStamp>enrolStarttime){
					state = 2;
				}else if(nowTimeStamp>activityBeginTime){
					state = 1;
				}
				//进行修改数据
				updatepre = con.prepareStatement(updatesql);
				updatepre.setInt(1,state);
				updatepre.setInt(2, id);
				updatepre.executeUpdate();
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JdbcUtil.close(updatepre);
			JdbcUtil.close(rs);
			JdbcUtil.close(pre);
			JdbcUtil.close(con);
		}
		System.out.println("更新新闻结束....");
	}*/
	//根据字段进行获取时间戳
	public Long getTimeStamp(String column,ResultSet rs){
		String time = null;
		try {
			time = rs.getString(column);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(time==null) time= "";
		return Long.parseLong(TimeUtils.date2TimeStamp(time,"yyyy-MM-dd HH:mm:ss"));		
	}
	
	
}
