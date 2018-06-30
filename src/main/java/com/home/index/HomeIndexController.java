package com.home.index;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.mail.Session;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.eclipse.jetty.util.StringUtil;

import com.admin.common.CaptchaRender;
import com.admin.common.ControllerUtil;
import com.admin.common.Mail;
import com.admin.common.MailUtils;
import com.admin.common.model.ActivityComement;
import com.admin.common.model.ActivityComementInfo;
import com.admin.common.model.ActivityCommentView;
import com.admin.common.model.ActivityInfo;
import com.admin.common.model.ActivityInfoView;
import com.admin.common.model.AttendInfo;
import com.admin.common.model.CarouseInfo;
import com.admin.common.model.Dictionary;
import com.admin.common.model.ForumChannel;
import com.admin.common.model.ForumComment;
import com.admin.common.model.ForumInfo;
import com.admin.common.model.ForumInfoView;
import com.admin.common.model.ForumManagerInfo;
import com.admin.common.model.NewInfo;
import com.admin.common.model.PublicManInfo;
import com.admin.common.model.UserInfo;
import com.admin.common.model.WikipediaInfo;
import com.admin.interceptor.NewService;
import com.common.service.ActivityCommentService;
import com.common.service.ActivityInfoService;
import com.common.service.CarouseInfoService;
import com.common.service.ChannelService;
import com.common.service.DictionaryService;
import com.common.service.ForumCommentService;
import com.common.service.ForumInfoService;
import com.common.service.ForumManagerService;
import com.common.service.NewsService;
import com.common.service.PublicManInfoService;
import com.common.service.UserInfoService;
import com.common.service.WikipediaInfoService;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.render.RedirectRender;
import com.jfinal.upload.UploadFile;
public class HomeIndexController extends Controller {
	@NewService("DictionaryService")
	DictionaryService dictionaryService;
	@NewService("CarouseInfoService")
	CarouseInfoService carouseInfoService;
	@NewService("NewsService")
	NewsService newsService;
	@NewService("ActivityInfoService")
	ActivityInfoService activityInfoService;
	@NewService("UserInfoService")
	UserInfoService userInfoService;
	@NewService("ForumInfoService")
	ForumInfoService forumInfoService;
	@NewService("ForumCommentService")
	ForumCommentService forumCommentService; 
	@NewService("ChannelService")
	ChannelService channelService;
	@NewService("ActivityCommentService")
	ActivityCommentService activityCommentService;
	@NewService("PublicManInfoService")
	PublicManInfoService publicManInfoService;
	@NewService("ForumManagerService")
	ForumManagerService forumManagerService;
	@NewService("WikipediaInfoService")
	WikipediaInfoService wikipediaInfoService;
	public void index(){
		//查询轮播图片
		List<CarouseInfo> imgList = null;
		//查询最新公益活动
		List<ActivityInfoView> newActivityInfoList = new ArrayList<ActivityInfoView>();
		List<ActivityInfoView> recomenActivityInfoList = new ArrayList<ActivityInfoView>();
		//查询最新一条公益人物
		PublicManInfo publicManInfo = new PublicManInfo();
		//查询最新一条公益百科
		WikipediaInfo wikipediaInfo = new WikipediaInfo();
		try {
			newActivityInfoList = activityInfoService.findActivityInfoNew();
			recomenActivityInfoList = activityInfoService.findActivityInfoRecomen();
			publicManInfo = publicManInfoService.findFristPublicManInfo();
			wikipediaInfo = wikipediaInfoService.findFristWikipediaInfo();
			imgList = carouseInfoService.findCarouseforHome();
			System.out.println(imgList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("newActivityInfoList",newActivityInfoList);
		setAttr("recomenActivityInfoList", recomenActivityInfoList);
		setAttr("publicManInfo", publicManInfo);
		setAttr("wikipediaInfo", wikipediaInfo);
		setAttr("imgList", imgList);
	}
	//查询首页标题
	public void getTitleList(){
		try {
			renderJson(dictionaryService.findDictionaryListByPId(PropKit.getInt("indexTitle")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//首页新闻列表
	public void newsInfoPaginate(){
		try {
			renderJson(newsService.newsInfoPaginate(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//首页新闻速报
	public void getFirstNews(){
		try {
			renderJson(newsService.getFirstNews());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void newsContentPage(){
		Integer id = getParaToInt("id");
		NewInfo newInfo = new NewInfo();
		newInfo = 	newsService.findNewsInfoById(id);
		setAttr("newInfo", newInfo);
	}
	//跳转公益活动
	public void activityIndex(){
		List<Dictionary> activityList = null;
		List<ActivityInfoView> activityInfoList = new ArrayList<ActivityInfoView>();
		//查询线上公益活动
		List<ActivityInfoView> onlineActList = new ArrayList<ActivityInfoView>();
		//查询线下公益活动
		List<ActivityInfoView> underlineActList = new ArrayList<ActivityInfoView>();
		try {
			activityList = dictionaryService.findDictionaryListByPId(PropKit.getInt("activity"));
			activityInfoList = activityInfoService.findTopRecomendActivity();
			onlineActList = activityInfoService.findActivityInfoByType(PropKit.getInt("onlineActivity"));
			underlineActList = activityInfoService.findActivityInfoByType(PropKit.getInt("underLineActivity"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(activityList);
		setSessionAttr("activityList", activityList);
		setAttr("activityInfoList",activityInfoList);
		setAttr("onlineActList",onlineActList);
		setAttr("underlineActList", underlineActList);
	}
	//推荐活动
	public void recomendActivity(){
		
	}
	//线上活动
	public void onlineActivity(){
		
	}
	//线下活动
	public void underLineActivity(){
		
	}
	//跳转活动详情
	public void activityInfoDetail(){
		//查询新闻
		ActivityInfoView activityInfo = new ActivityInfoView();
		Integer id = getParaToInt("id");
		activityInfo = activityInfoService.findActivityInfoViewById(id);
		setAttr("activityInfo", activityInfo);
		List<AttendInfo> attendInfoList = activityInfoService.findAttendInfoListById(id);
		List<Integer> idList = new ArrayList<>();
		for (AttendInfo attendInfo : attendInfoList) {
			idList.add(attendInfo.getUserId());
		}
		List<UserInfo> userInfoList = new ArrayList<>();
		if(idList!=null&&idList.size()>0){
			String idStr = StringUtils.join(idList,",");
			userInfoList = userInfoService.findUserInfobyIdIn(idStr);
		}
		setAttr("userInfoList",userInfoList);
	}
	//前往登录页面
	public void toLogin(){
		render("login.html");
	}
	//前往注册页面
	public void toRegist(){
		render("regist.html");
	}
	//注册页面
	public void regist(){
		HashMap<String,String> returnMsg = new HashMap<String,String>();
		UserInfo userInfo = getModel(UserInfo.class);
		String rePassword = getPara("user_rePassword");
		if(userInfo!=null){
			if(rePassword.equals(userInfo.getUserPassword())){
				UserInfo exitUser = userInfoService.findUserInfoByNickName(userInfo.getUserNickname());
				if(exitUser==null){
					String code = UUID.randomUUID().toString().replace("-", "");
					userInfo.setUserCode(code);
					try {					
						Mail mail = new Mail(PropKit.get("from"),userInfo.getUserEmail());
						String emailContent = PropKit.get("emailContent");
						String basePath = getRequest().getScheme()+"://"+getRequest().getServerName()+":"+getRequest().getServerPort()+"/active?code="+code;
						emailContent = MessageFormat.format(emailContent,basePath);
						mail.setSubject(PropKit.get("emailTitle"));
						mail.setContent(emailContent);
						Session session = MailUtils.createSession(PropKit.get("host"),PropKit.get("hostUserName"),PropKit.get("hostPassword"));
						MailUtils.send(session, mail);
						userInfoService.userInfoSave(userInfo);
						returnMsg.put("returnMsg","注册成功,请前往邮箱激活");
						returnMsg.put("returnState","success");
					} catch (Exception e) {
						returnMsg.put("returnMsg","注册异常,请重新操作");
						returnMsg.put("returnState","error");
					}
				}else{
					returnMsg.put("returnMsg","用户已存在,请换用其他账号");
					returnMsg.put("returnState","exist");
				}	
				
			}
		}
		renderJson(returnMsg);
	}
	//编写用户登录的方法
	public void login(){
		String code = getPara("code","");
		HashMap<String,String> returnMsg = new HashMap<String,String>();
		UserInfo userInfo = new UserInfo();
		UserInfo exitUser = new UserInfo();
		String inputCode = CaptchaRender.encrypt(code.toUpperCase());
		String realCode = getSessionAttr(CaptchaRender.DEFAULT_CAPTCHA_MD5_CODE_KEY);
		if(inputCode.equals(realCode)){
			try {
				//查询用户
				userInfo = getModel(UserInfo.class);
				if(userInfo!=null){
					exitUser = userInfoService.loginByNickNameAndPassword(userInfo.getUserNickname(),userInfo.getUserPassword());
					if(exitUser!=null){
						//进行判断用户是否已经激活
						if(exitUser.getUserIsRegister()==1){
							//判断用户是不是被禁用
							if(exitUser.getIsAble()==0){
								setSessionAttr("loginInfo", exitUser);
								returnMsg.put("returnMsg","登录成功");
								returnMsg.put("returnState","success");
							}else{
								returnMsg.put("returnMsg","账户被禁用");
								returnMsg.put("returnState","ableError");
							}
						}else{
							returnMsg.put("returnMsg","账户未激活");
							returnMsg.put("returnState","activeError");
						}
					}else{
						returnMsg.put("returnMsg","用户名或者密码错误");
						returnMsg.put("returnState","loginFail");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			returnMsg.put("returnMsg","验证码错误");
			returnMsg.put("returnState","ecodeFail");
		}
		renderJson(returnMsg);
		
	}
	//异步加载信息
	public void ajaxValidateFieldName(){
		String fieldId = getPara("fieldId");
		String nickName = getPara("fieldValue");
		//查询用户名是否已经被使用
		Object[] returnMsg = new Object[2];
		returnMsg[0]=fieldId;
		UserInfo userInfo = userInfoService.findUserInfoByNickName(nickName);
		System.out.println(userInfo);
		if(userInfo!=null){
			returnMsg[1] = true;
		}else {
			returnMsg[1] = false;
		}		
		renderJson(returnMsg);
	}
	//激活的方法
	public void active(){
		String code = getPara("code");
		String text = "";
		try {
			UserInfo exitUser = userInfoService.findUserInfobyCode(code);
			if(exitUser!=null){
				userInfoService.activeUser(exitUser);
				text="恭喜你,激活成功";
			}else{
				text = "激活失败,用户不存在";
			}
		} catch (Exception e) {
			e.printStackTrace();
			text="激活异常,请重新激活";
		}
		setAttr("text",text);
		render("returnMsg.html");
	}
	//进行生成用户验证码的方法
	@Clear
	public void captcha(){
		//验证码生成
		CaptchaRender captchaRender=new CaptchaRender(4);
		String md5RandonCode = captchaRender.getMd5RandonCode();
		setSessionAttr(CaptchaRender.DEFAULT_CAPTCHA_MD5_CODE_KEY, md5RandonCode);
		render(captchaRender);
	}
	//用户退出登录的方法
	public void logout(){
		setSessionAttr("loginInfo", null);
		redirect("/");
	}
	//进行跳转公益论坛的页面
	public void forumIndex(){
		//查询热帖版
		List<ForumInfoView> hotForumViewList = new ArrayList<ForumInfoView>();
		//查询精帖
		List<ForumInfoView> fineForumViewList = new ArrayList<ForumInfoView>();
		//查询新帖
		List<ForumInfoView> newForumViewList = new ArrayList<ForumInfoView>();
		
		List<ForumManagerInfo> topNoice = new ArrayList<ForumManagerInfo>();
		List<ForumManagerInfo> friendLink = new ArrayList<ForumManagerInfo>();
		List<ForumManagerInfo> forumHelp = new ArrayList<ForumManagerInfo>();
		try {
			hotForumViewList = forumInfoService.findHotForumViewTop();
			fineForumViewList = forumInfoService.findFineForumViewTop();
			newForumViewList = forumInfoService.findNewForumViewTop();
			topNoice = forumManagerService.findTopNoice();
			friendLink = forumManagerService.findTopFriendLink();
			forumHelp = forumManagerService.findTopForumHelp();
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("hotForumViewList", hotForumViewList);
		setAttr("fineForumViewList", fineForumViewList);
		setAttr("newForumViewList", newForumViewList);
		setAttr("topNoice", topNoice);
		setAttr("friendLink", friendLink);
		setAttr("forumHelp", forumHelp);
	}
	//进行发帖
	public void toAddForum(){
		List<ForumChannel> forumBigType= null;
		//查询大板块
		try {
			forumBigType = channelService.findchannelListByPId(PropKit.getInt("forumBigType"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("forumBigType",forumBigType);
		//查询用户的积分
		UserInfo userInfo = getSessionAttr("loginInfo");
		if(userInfo!=null){
			setAttr("userScore",userInfo.getUserScore());
		}
		render("addForum.html");
	}
	public void getForumSmallType(){
		List<ForumChannel> forumSmallType= null;
		String bigType = getPara("bigType");
		try {
			if(StringUtils.isNotBlank(bigType)){
				forumSmallType = channelService.findchannelListByPId(Integer.parseInt(bigType));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(forumSmallType);
	}
	public void addForum(){
		ForumInfo forumInfo = new ForumInfo();
		UserInfo exitUser = new UserInfo();
		HashMap<String,String>  returnMsg = new HashMap<String,String>();
		try {
			forumInfo = getModel(ForumInfo.class);
			if(forumInfo!=null){
				exitUser =  getSessionAttr("loginInfo");
				if(exitUser==null){
					returnMsg.put("returnState","loginError");
					returnMsg.put("returnMsg","你还没有登录,不能进行发布");
				}else{
					//判断用户的积分是不是已经足够发表内容
					Integer userScroe = exitUser.getUserScore();
					Integer forumScore  = forumInfo.getForumScore();
					if((forumScore!=null)&&(userScroe!=null)){
						boolean flag = userScroe>forumScore?true:false;
						if(flag){
							forumInfoService.saveForumInfo(forumInfo,exitUser);
							returnMsg.put("returnState","success");
							returnMsg.put("returnMsg","发布成功");
						}else{
							returnMsg.put("returnState","ScoreError");
							returnMsg.put("returnMsg","积分不足");
						}
					}else{
						returnMsg.put("returnState","ScoreError");
						returnMsg.put("returnMsg","积分处理异常");
					}
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(returnMsg);
	}
	//查看论坛详情
	public void forumInfoDetail(){
		String id = getPara("id");
		ForumInfoView forumInfo = new ForumInfoView();
		if(StringUtils.isNotBlank(id)){
			forumInfo = forumInfoService.findForumInfoViewById(Integer.parseInt(id));
		}
		setAttr("forumInfo", forumInfo);
	}
	//进行查询所有论坛的评论
	public void ForumCommentList(){
		try {
			System.out.println(forumInfoService.ForumCommentList(this));
			renderJson(forumInfoService.ForumCommentList(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//进行提交评论
	public void commitComment(){
		ForumComment forumComment = new ForumComment();
		HashMap<String,String> returnMsg = new HashMap<String,String>();
		UserInfo loginInfo = getSessionAttr("loginInfo");
		if(loginInfo==null){
			returnMsg.put("returnMsg","请先登录");
		}else{
			forumComment = getModel(ForumComment.class);
			if(forumComment!=null){
				try {
					forumCommentService.CommentSave(forumComment,loginInfo);
					returnMsg.put("returnMsg","评论成功");
					returnMsg.put("returnState","success");
				} catch (Exception e) {
					e.printStackTrace();
					returnMsg.put("returnMsg","评论异常");
				}
			}else{
				returnMsg.put("returnMsg","信息不能为空");
			}
		}
		renderJson(returnMsg);
	}
	//进行跳转到所有帖子
	public void AllPostPage(){
		String type = getPara("type");
		String name = getPara("name");
		String bigType = getPara("bigType");
		setAttr("bigType", bigType);
		setAttr("type", type);
		setAttr("name", name);
		render("allPost.html");
	}
	//进行获得所有的数据
	public void forumInfoPaginate(){
		
		try {
			renderJson(forumInfoService.forumInfoPaginate(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//跳转到板块
	public void postPlate() throws Exception{
	//查询新闻版块
		List<Record> recordList = new ArrayList<Record>();
		Record record =null;
		List<ForumChannel> channelParentList = channelService.findAllForumChannel();
		for (ForumChannel channel : channelParentList) {
			record = new Record();
			record.set("channel_name", channel.getChannelName());
			record.set("id", channel.getId());
			record.set("topticCount", channel.getChannel_topics());
			record.set("commentCount", channel.getChannelComments());
			record.set("type", channel.getId());
			if(channel.getChannelChildrenSize()>0){
				record.set("children", new ArrayList<Record>());
				recordList.add(record);
			}else{
				for (Record _record : recordList) {
					if(channel.getChannelPid()==_record.getInt("id")){
						List<Record> _recordList = (List<Record>)_record.get("children");
						_recordList.add(record);
					}
				}
			}
		}
		setAttr("recordList",recordList);
	}
	
	public void userInfo(){
		if(getSessionAttr("loginInfo")!=null){
			render("userInfo.html");
		}else{
			render("login.html");
		}
	}
	//保存用户图片信息
	public void saveUserImg(){
		HashMap<String,String> returnMsg = new HashMap<String,String>();
		try {
			int id = getParaToInt("id");
			String imgPath = getPara("imgContent");
			UserInfo userInfo = new UserInfo();
			userInfo.setId(id);
			userInfo.setUserImg(imgPath);
			userInfoService.userInfoSave(userInfo);
			userInfo = userInfoService.findUserInfoById(id);
			setSessionAttr("loginInfo", userInfo);
			returnMsg.put("returnMsg", "保存成功");
		} catch (Exception e) {
			returnMsg.put("returnMsg", "保存失败");
		}
		renderJson(returnMsg);
	}
	//修改用户信息
	public void alertUserInfo(){
		if(getSessionAttr("loginInfo")!=null){
			Integer id = getParaToInt("id");
			UserInfo userInfo = userInfoService.findUserInfoById(id);
			setAttr("userInfo", userInfo);
			render("alertUserInfo.html");
		}else{
			render("login.html");
		}
	}
	public void userInfoSave(){
		UserInfo userInfo = getModel(UserInfo.class);
		try {
			userInfoService.userInfoSave(userInfo);
			userInfo = userInfoService.findUserInfoById(userInfo.getId());
			setSessionAttr("loginInfo", userInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		redirect("userInfo");
	}
	//进行跳转修改密码页面
	public void alertUserPasswordPage(){
		if(getSessionAttr("loginInfo")!=null){
			render("alertUserPasswordPage.html");
		}else{
			render("login.html");
		}
	}
	//进行修改密码操作
	public void alertUserPassword(){
		String oldPassword = getPara("oldPassword");
		String newPassword = getPara("newPassword");
		String renewPassword = getPara("renewPassword");
		Integer id = getParaToInt("id");
		UserInfo loginUser = getSessionAttr("loginInfo");
		HashMap<String,String> returnMsg = new HashMap<String,String>();
		try {
			boolean flag = UserInfoService.checkPassword(loginUser.getUserPassword(),oldPassword);
			System.out.println(DigestUtils.md5Hex(loginUser.getUserPassword()));
			if(flag){
				//进行修改密码操作
				UserInfo findUser = UserInfoService.findUserInfoByIdAndPassword(id,DigestUtils.md5Hex(oldPassword));
				findUser.setUserPassword(DigestUtils.md5Hex(newPassword));
				userInfoService.userInfoSave(findUser);
				UserInfo existUser = userInfoService.findUserInfoById(id);
				setSessionAttr("loginInfo", existUser);
				returnMsg.put("returnMsg","修改成功");
				returnMsg.put("returnState", "success");
			}else{
				returnMsg.put("returnMsg","原始密码输入错误");
				returnMsg.put("returnState", "error");
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("returnMsg","操作异常");
			returnMsg.put("returnState", "exception");
		}
		renderJson(returnMsg);
	}
	public void attendActivity(){
		UserInfo userInfo = getSessionAttr("loginInfo");
		HashMap<String,Object> returnMsg = new HashMap<>();
		if(userInfo==null){
			returnMsg.put("returnMsg", "请先登录");
		}else{
			Integer actId = getParaToInt("actId");
			Integer userId = userInfo.getId();
			AttendInfo attendInfo = activityInfoService.findAttendInfoByUserIdAndActId(userId,actId);
			if(attendInfo==null){
				ActivityInfo activityInfo = activityInfoService.findActivityInfoById(actId);
				activityInfo.setActivityEnrolTotal(activityInfo.getActivityEnrolTotal()+1);
				try {
					
					AttendInfo userAttend = new AttendInfo();
					userAttend.setUserId(userId);
					userAttend.setActivityId(actId);
					activityInfoService.activityAttendSave(userAttend);
					activityInfoService.activityInfoSave(activityInfo);
					ActivityInfo activityInfoforNum = activityInfoService.findActivityInfoById(actId);
					returnMsg.put("returnState","success");
					returnMsg.put("returnMsg", "报名成功");
					returnMsg.put("activityNum", activityInfoforNum.getActivityEnrolTotal());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					returnMsg.put("returnState","error");
					returnMsg.put("returnMsg", "报名失败");
				}
			}else{
				returnMsg.put("returnState","again");
				returnMsg.put("returnMsg", "您已经报名成功,请勿重新操作");
			}
		}
		renderJson(returnMsg);
		
	}
	//评论进行提交的方法
	public void commentCommit(){
		HashMap<String,String> returnMsg = new HashMap<>();
		String content = getPara("content");
		Integer actId = getParaToInt("actId");
		ActivityComementInfo activityComementInfo = new ActivityComementInfo();
		activityComementInfo.setActivityId(actId);
		activityComementInfo.setContent(content);
		UserInfo loginUser = getSessionAttr("loginInfo");
		if(loginUser!=null){
			activityComementInfo.setUserId(loginUser.getId());
		}
		activityComementInfo.setContentTime(new Date());
		try {
			activityComementInfo.setContent(activityComementInfo.getContent().replaceAll("</?[^>]+>", ""));
			activityCommentService.saveActivityComment(activityComementInfo);
			returnMsg.put("returnState", "success");
			returnMsg.put("returnMsg", "评论成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("returnState", "error");
			returnMsg.put("returnMsg", "评论失败");
		}
		renderJson(returnMsg);
	}
	
	public void getCommentData(){
		Integer actId = getParaToInt("actId");
		List<ActivityCommentView> activityComementInfoList = activityCommentService.findActivityCommentViewListByActId(actId);
		renderJson(activityComementInfoList);
	}
	public void publicMan(){
		//按照查看量进行排行前7条
		List<PublicManInfo> publicManInfoList = null;
		publicManInfoList = publicManInfoService.getTopSevenPublicManInfo();
		setAttr("publicManInfoList", publicManInfoList);
	}
	public void wikipedia(){
		
	}
	public void showPublicContent(){
		Integer id = getParaToInt("id");
		PublicManInfo publicManInfo = null;
		publicManInfo = publicManInfoService.findpublicManInfoById(id);
		setAttr("publicManInfo",publicManInfo);
	}
	public void showforumManager(){
		Integer id = getParaToInt("id");
		ForumManagerInfo forumManagerInfo = null;
		forumManagerInfo = forumManagerService.findForumManagerInfoById(id);
		setAttr("forumManagerInfo",forumManagerInfo);
	}
	public void showWikipedia(){
		Integer id = getParaToInt("id");
		WikipediaInfo wikipediaInfo = null;
		wikipediaInfo = wikipediaInfoService.findwikipediaInfoById(id);
		setAttr("wikipediaInfo",wikipediaInfo);
	}
}
