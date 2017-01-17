package com.wsh.base.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.wsh.base.dict.BaseDict;
import com.wsh.base.model.GeneralizeInfo;
import com.wsh.base.model.NewUserInfo;
import com.wsh.base.model.UserInfo;
import com.wsh.base.model.wechat.response.Article;
import com.wsh.base.model.wechat.response.NewsMessage;
import com.wsh.base.model.wechat.response.TextMessage;
import com.wsh.base.service.IUserInfoService;
import redis.clients.jedis.Jedis;

public class CoreService {
	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public static String processRequest(HttpServletRequest request,IUserInfoService userInfoService) {
		String respMessage = null;
		try {
			
			// 默认返回的文本消息内容
			String respContent = "";

			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			//遍历map.
			/*System.out.println("=============微信请求，map遍历结果==============");
				for (Map.Entry<String, String> entry : requestMap.entrySet()) {
						System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
				}*/
			
			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			System.out.println("=="+msgType);
			System.out.println("=="+BaseDict.PICURL);
			String contentString = requestMap.get("Content");
			
			NewsMessage newsMessage = new NewsMessage();
			newsMessage.setToUserName(fromUserName);  
            newsMessage.setFromUserName(toUserName);  
            newsMessage.setCreateTime(new Date().getTime());  
            newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);  
            newsMessage.setFuncFlag(0);  
			
			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				// 回复文本消息
				TextMessage textMessage = new TextMessage();
				textMessage.setToUserName(fromUserName);
				textMessage.setFromUserName(toUserName);
				textMessage.setCreateTime(new Date().getTime());
				textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
				textMessage.setFuncFlag(0);


				if(contentString.equals("149f31d8f51474e9298f1553")){
					System.out.println(fromUserName+"<<<<<149f31d8f51474e9298f1553");
				}
				if(contentString.equals("客服")){
					textMessage.setContent("你好：");
					textMessage.setMsgType("transfer_customer_service");

					respMessage = MessageUtil.textMessageToXml(textMessage);

				}else if(contentString.equals("888888")){
					textMessage.setContent("1、按左边的显示菜单按钮，打开底部菜单。\n2、点击右下角的 【会】员中心\n3、点击 开通会员，会邀请您绑定手机号，在【邀请码】框内输入 888888.");
					respMessage = MessageUtil.textMessageToXml(textMessage);
				}else{
						List<Article> articleList = new ArrayList<Article>();    //读取配置文件吧。
					 	Article article = new Article();  
	                    article.setTitle("最热解读");  
	                    article.setDescription("您有任何问题，请咨询书童：18611080811。");  
	                    article.setPicUrl("http://bookclubvip.oss-cn-beijing.aliyuncs.com/222222.png");  
	                    article.setUrl(BaseDict.ARTICURL);    
	                    articleList.add(article);  
	                    // 设置图文消息个数  
	                    newsMessage.setArticleCount(articleList.size());  
	                    // 设置图文消息包含的图文集合  
	                    newsMessage.setArticles(articleList);  
	                    // 将图文消息对象转换成xml字符串  
	                    respMessage = MessageUtil.newsMessageToXml(newsMessage);  
				}
			}
			// 图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent +=fromUserName;
			}
			// 地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				respContent +=fromUserName;
			}
			// 链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent +=fromUserName;
			}
			// 音频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				respContent +=fromUserName;
			}
			// 事件推送
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				
				// 订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					
					List<Article> articleList = new ArrayList<Article>();    //读取配置文件吧。
				 	Article article = new Article();  
                    article.setTitle("最热解读");  
                    article.setDescription("您有任何问题，请咨询书童：18611080811。");  
                    article.setPicUrl("http://bookclubvip.oss-cn-beijing.aliyuncs.com/222222.png");  
                    article.setUrl(BaseDict.ARTICURL);  
                    articleList.add(article);  
                    // 设置图文消息个数  
                    newsMessage.setArticleCount(articleList.size());  
                    // 设置图文消息包含的图文集合  
                    newsMessage.setArticles(articleList);  
                    // 将图文消息对象转换成xml字符串  
                    respMessage = MessageUtil.newsMessageToXml(newsMessage);  

            		//关注更新上级函数。12.12  ；写完后放开即可。
            		try {
            			updateUserInfo_PidFunciton(requestMap,userInfoService,fromUserName);
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("occured exception when subscribe");
					}
            		
					
				}
				// 取消订阅
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					//request.getSession().invalidate();
					//取消订阅的用户，可以吧他的用户状态修改下。或者记录到其他表中，详细的记录这些数据。
				}
			
				else if(eventType.equals(MessageUtil.EVENT_TYPE_SCAN)){
					
//					如果用户还未关注公众号，则用户可以关注公众号，关注后微信会将带场景值关注事件推送给开发者。
//					如果用户已经关注公众号，则微信会将带场景值扫描事件推送给开发者。	
					
					List<Article> articleList = new ArrayList<Article>();    //读取配置文件吧。
				 	Article article = new Article();  
                    article.setTitle("最热解读");  
                    article.setDescription("您有任何问题，请咨询书童：18611080811。");  
                    article.setPicUrl("http://bookclubvip.oss-cn-beijing.aliyuncs.com/222222.png");  
                    article.setUrl(BaseDict.ARTICURL);  
                    articleList.add(article);  
                    // 设置图文消息个数  
                    newsMessage.setArticleCount(articleList.size());  
                    // 设置图文消息包含的图文集合  
                    newsMessage.setArticles(articleList);  
                    // 将图文消息对象转换成xml字符串  
                    respMessage = MessageUtil.newsMessageToXml(newsMessage);  
					System.out.println("--------扫描-------");
					//12.13 新开发 扫描规则。
					//新规则规定1：只有扫描人的：上面为空，下面也为空，才可以更新PID。否则这里不做任何事情。
                    //新规定2：用户可以有一次更改上级邀请码的机会，但是只有一次机会。【后期实现】
                	//代码都OK后，【放开这里】
				try {
					
					SCANUpadteOneUser(requestMap,userInfoService,fromUserName);
				} catch (Exception e) {
					System.out.println("occured exception when scan,openId="+fromUserName);
				}
				}else if(eventType.equals("TEMPLATESENDJOBFINISH")){
					//模板消息	
				}
				// 自定义菜单点击事件
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {

				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respMessage;
	}

	
	/**
	 * 
	 * @author Wally
	 * @time 2016年12月13日下午3:53:09
	 * @Description 扫描事件，独立操作函数。
	 */
	private static void SCANUpadteOneUser(Map<String, String> requestMap,
			IUserInfoService userInfoService, String fromUserName) {
		
		String screnStr=requestMap.get("EventKey");
		Integer pId=null;
		try {
			pId=Integer.parseInt(screnStr);
		} catch (Exception e) {
		}
		
		System.out.println("occured exception when execute SCANUpadteOneUser,openId="+fromUserName+",pId="+pId);
		//调用更新 PID的服务。具体规则，请查看具体的服务说明。 --都改完后，可以放开。
		userInfoService.UpdateOneUser(fromUserName, pId);
		
		
	}


/**
 * 
 * @Title updateUserInfo_PidFunciton
 * @author Wally
 * @time 2016年12月13日下午4:17:20
 * @Description 自己搜索关注，和扫描别人关注
 * @param
 * @return void
 */
	private static void updateUserInfo_PidFunciton(
			Map<String, String> requestMap, IUserInfoService userInfoService,String fromUserName) {

		try {
			//判断是否在App端注册过
			String unionId = "";
			Jedis jedis = RedisUtil.getJedis();
			if (jedis != null) {
				unionId = jedis.get(fromUserName);
				RedisUtil.returnResource(jedis);
				System.out.println("updateUserInfo_PidFunciton>>>>"+fromUserName);
				if (unionId != null) {
					if (userInfoService.getUserInfoByUnionId(unionId) != null) {
						return;
					}

				}
			}
		}catch (Exception e){
			e.printStackTrace();
			//nothing to do
			// continue
		}

		NewUserInfo bean= userInfoService.new_GetUserInfoByOpenId(fromUserName);
		 //这里只有当用户扫描别人二维码后才会不为空，往下执行。
		if(!"".equals(requestMap.get("EventKey"))){
			//事件KEY值，qrscene_为前缀，后面为二维码的参数值
			String screnStr=requestMap.get("EventKey");
			try {
				Integer pId=Integer.parseInt(screnStr.replace("qrscene_", ""));
				System.out.println("execute updateUserInfo_PidFunciton "+fromUserName+",pId="+pId);
			
				//新规则，只要有下线，就不能再成为别人的下线。12.12  以后的数据。加上日期判断。
				//只要有上线，可以修改一次，但是不能从这里修改。只能手动输入一回。所以，只要pid不为空，这里什么也不做。
				// 还要考虑，可能是先注册的app，后来才有的微信。【重要】
				if(null == bean){
					System.out.println("bean is null");
					//必然是新数据。第一次关注的。可以记录用户的关注时间。
					//这里直接创建新数据。只是需要同时创建：基础信息和关系信息。肯定是会有PID的。
					userInfoService.createOneUser(fromUserName,pId);
				}else{
					//什么也不做。
					//解释：这种是关注过，又取消，又扫描别人二维码关注的用户。【正常应该去再判断，不过交给扫描事件算了】
					System.out.println("bean is not null，id="+bean.getId());
				}
			} catch (Exception e) {
				e.printStackTrace();
				//NOOP
			}
		}else{  //如果为空，说明是直接关注的，所以可以直接更新数据。
			//不存在，在添加。
			if(null == bean){
				userInfoService.createOneUser(fromUserName,null); //新建一条空数据即可
			}
			//如果存在了，就什么也不做。
		}
		
	}

}



/**
 * 扫描：事件推送原代码。
 */

/*else if(eventType.equals(MessageUtil.EVENT_TYPE_SCAN)){
//	事件KEY值，是一个32位无符号整数，即创建二维码时的二维码scene_id/scene_str
//	requestMap.get("EventKey");
	//事件KEY值，qrscene_为前缀，后面为二维码的参数值
	textMessage.setContent("如果有疑问，请直接输入：【客服】-联系客服人员");
	
	List<Article> articleList = new ArrayList<Article>();    //读取配置文件吧。
 	Article article = new Article();  
    article.setTitle("最新解读");  
    article.setDescription("想参与直播的【会员】朋友，请尽快添加书童微信18611080811，帮您进入读书群，一起听直播，一起分享心得感悟！");  
    article.setPicUrl(BaseDict.PICURL);  
    article.setUrl(BaseDict.ARTICURL);  
    articleList.add(article);  
    // 设置图文消息个数  
    newsMessage.setArticleCount(articleList.size());  
    // 设置图文消息包含的图文集合  
    newsMessage.setArticles(articleList);  
    // 将图文消息对象转换成xml字符串  
    respMessage = MessageUtil.newsMessageToXml(newsMessage);  
	
    
    
	UserInfo userInfo=new UserInfo();
	String screnStr=requestMap.get("EventKey");
	Integer pId=null;
	try {
		pId=Integer.parseInt(screnStr);
		userInfo.setpId(pId);
	} catch (Exception e) {
		//NOOP
	}
	
	System.out.println("扫描事件===============PID=========="+pId);
	
	UserInfo bean=userInfoService.getUserInfoByOpenId(fromUserName);
	//System.out.println("bean==="+bean.getUserId());
	//过滤自己的扫描自己
	
//	System.out.println("===============bean.getpId()=========="+bean.getpId());
	
	if(bean!=null && pId!=null && bean.getUserId().intValue()!=pId.intValue() && bean.getpId()==null){ 
		//System.out.println("bean.getpId()==="+bean.getpId());
		//B扫描A。B是A的下级，B的pid=A。
		//但是必须排除以下两种情况。
		//(1):B的PID不等于空：说明已经扫描成为了别人的下级，此时就不能再成为A的下级。//防止恶意刷父关系取钱。
		//(2):B有下级：说明已经发展了下线，那他就是顶层。不能再关注别人。 //防止我的下级，在关注你后，全部成了你的下级。或者：互相成为上下级关系。
		if(userInfoService.selectChild(bean.getUserId().intValue(),1) == 0 ){   //上空，下空，则是可以扫描。
			UserInfo vip=userInfoService.checkVip(fromUserName); //判断 当前用户是不是老会员。是老会员则不行
			if(vip==null){
				userInfo.setOpenid(fromUserName);
			//	System.out.println("===============userInfo=========="+userInfo.getpId());
				userInfoService.upateUserInfo(userInfo);
			}
		}
	}
//模板消息	
}

*/



/**
 * 关注：事件推送源代码：
 */
/*else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
	// 事件类型
	String eventType = requestMap.get("Event");
	
	// 订阅
	if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
		//respContent = "欢迎关注维书会...如果有疑问，请直接输入：【客服】-联系客服人员";
		respContent = "欢迎如此有品味、有情怀的您关注【维书会】！\n"
				+ "众多精英在此读书精进，快快点击【会】，成为其中一员吧！\n"
				+ "【维】众多精彩文章为您开拓更宽视野！" + "【书】精彩好书解读为您呈现更多世界！\n"
				+ "【会】神秘礼物不停转，等您一一揭晓！\n"
				+ "咨询可直接发送【客服】两字，之后将您的问题发出，叮咚将会有温暖书童为您解答！\n";
		textMessage.setContent(respContent);
		
		List<Article> articleList = new ArrayList<Article>();    //读取配置文件吧。
	 	Article article = new Article();  
        article.setTitle("最新解读");  
        article.setDescription("想参与直播的【会员】朋友，请尽快添加书童微信18611080811，帮您进入读书群，一起听直播，一起分享心得感悟！");  
        article.setPicUrl(BaseDict.PICURL);  
        article.setUrl(BaseDict.ARTICURL);  
        articleList.add(article);  
       
        // 设置图文消息个数  
        newsMessage.setArticleCount(articleList.size());  
        // 设置图文消息包含的图文集合  
        newsMessage.setArticles(articleList);  
        // 将图文消息对象转换成xml字符串  
        respMessage = MessageUtil.newsMessageToXml(newsMessage);  

        
		UserInfo userInfo=new UserInfo();
		if(requestMap.get("EventKey")!=null){
			//事件KEY值，qrscene_为前缀，后面为二维码的参数值
			String screnStr=requestMap.get("EventKey");
			try {
				Integer pId=Integer.parseInt(screnStr.replace("qrscene_", ""));
				UserInfo bean=userInfoService.getUserInfoByOpenId(fromUserName);
			
				//// 首先是一个新用户bean==null;其次取消关注的老用户，但是他没有下级。
				if(bean==null || userInfoService.selectChild(bean.getUserId().intValue(),1) == 0 ){
					//是否为老用户
					UserInfo vip=userInfoService.checkVip(fromUserName);
					if(vip==null){
						userInfo.setpId(pId);
					}
				}
					
			} catch (Exception e) {
				//NOOP
			}
		}
		userInfo.setOpenid(fromUserName);
		userInfoService.insertSelective(userInfo);
	}

*/



