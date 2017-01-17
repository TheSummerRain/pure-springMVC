package com.wsh.base.intercepter;


import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.http.client.ClientProtocolException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.wsh.base.constants.Cts;
import com.wsh.base.model.NewUserInfo;
import com.wsh.base.model.UserInfo;
import com.wsh.base.model.wechat.WxJsConfig;
import com.wsh.base.service.IUserInfoService;
import com.wsh.base.util.TokenProxy;
import com.wsh.base.util.WeixinUtil;
import com.wsh.base.wxpay.AccessToken;
import com.wsh.base.wxpay.HttpUtil;
import com.wsh.base.wxpay.WxConfig;

public class Authorization implements HandlerInterceptor  {

	
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse arg1,
			Object arg2, ModelAndView mv) throws Exception {
		if(mv!=null){
			mv.addObject("ctx",request.getContextPath());
		}
		String param = null;
		String basePath=null;
		if(request.getQueryString()==null){
			 basePath = request.getRequestURL().toString();
		}else{
			param=request.getQueryString();
			basePath = request.getRequestURL().toString()+"?"+param;
		}
		WxJsConfig wc = WeixinUtil.getJsSdkConfig(basePath);
		if(null != wc && null !=mv){
			mv.addObject("wxConfig",wc);
		}
	}
	
	
	/**
	 * 新版本preHandel
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object object) throws Exception {

		ApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(request.getSession()
						.getServletContext());
		IUserInfoService userInfoService = (IUserInfoService) context
				.getBean("UserInfoService");
		
		String ua = request.getHeader("user-agent").toLowerCase();
		//从微信服务器发起的动作，会先调用一次这个方法，判断通不通。通了之后才会走下面的。
		if (request.getParameter("signature") != null && request.getParameter("timestamp") != null 	&& request.getParameter("nonce") != null) {
			return true;
		}
		
		//上线用这里
		/*if (ua.toLowerCase().indexOf("micromessenger") == -1 ) {
			return false;
		}*/
		
		//本地用这里
		if (ua.toLowerCase().indexOf("micromessenger") != 0 ) {
			System.out.println(".....no mobile request..........IP:..."+request.getMethod());
			request.getSession()
			.setAttribute(
					Cts.USER_IN_SESSION,
					userInfoService
					.new_GetUserInfoByOpenId("oxoAruKbGePcjWUebsUAy_G_YfRo"));   //【小吴接口】
			return true;
		}
		
		//查找session是否有值。
		NewUserInfo userSession = (NewUserInfo) request.getSession().getAttribute(Cts.USER_IN_SESSION);
		if (userSession != null) {
			return true;
		}
		
		//【授权更新】用户基础数据。
		NewUserInfo userInfo = newAuth(request,response,request.getSession().getId());
		if(userInfo != null){
			if (userInfoService.new_upateUserInfo(userInfo) == 1) {
				request.getSession().setAttribute(Cts.USER_IN_SESSION,userInfoService
						.new_GetUserInfoByOpenId(userInfo.getOpenid()));                    //从新的数据表中获取数据。
			}
		}
		
		return true;
	}
	
	
	//新的授权代码：12.12 ;只增不改
	@SuppressWarnings("unused")
	private NewUserInfo newAuth(HttpServletRequest request,HttpServletResponse response,String key)
			throws ClientProtocolException, IOException {
		
		String code = request.getParameter("_c_o_d_e_");
		if(code==null){
			return null;
		}
		
		NewUserInfo user =null;
		AccessToken token = null;
		token = TokenProxy.getOuthToken(code,key);
		if (token != null && token.getOpenid()!=null) {
			String url=WxConfig.USERINFO_URL.replace("ACCESS_TOKEN", token.getToken()).replace("OPENID",
					token.getOpenid());
			
			JSONObject userInfo = HttpUtil.doGet(url);
			//https://api.weixin.qq.com/sns/userinfo?access_token=dL83AQEsp90A19EKQEhlZjkguEACNiryrUMOCqbX3K3wKIFCmPEjT8oOGEqz2ArWONCmnA7-Y5jUCzscpjaRmOGXFhmVVEOlHCgrL7Yjk8o&openid=oxoAruMi_Gv22n3YDDzCQFHs4P60&lang=zh_CN
			
			if (userInfo != null) {
				
				user=new NewUserInfo();
				//用户的地址信息这里暂时没有收录。
					
				if (userInfo.get("sex") != null) {
					user.setSex(userInfo.getInt("sex"));
				}
				if (userInfo.get("headimgurl") != null) {
					user.setOssheadpic(userInfo.getString("headimgurl"));
				}
				if (userInfo.get("nickname") != null) {
					user.setNickname(userInfo.getString("nickname"));
				}
				if (userInfo.get("openid") != null) {
					user.setOpenid(userInfo.getString("openid"));
				}
				if (userInfo.get("unionid") != null) {
					user.setWxunionid(userInfo.getString("unionid"));
				}
				user.setLastlandingtime(new Date());  //更新最后一次登录时间。
			}
		}
		return user;
	}
	
	
//====================【下面】==【老版本代码】===================================================================================================	
	/**
	 * 
	 * @Title preHandle_old
	 * @author Wally
	 * @time 2016年12月13日下午4:40:03
	 * @Description 老版本的 preHandle.  【当还没有导入新的用户基础数据时，就用这里。】
	 * @param
	 * @return boolean
	 */
	public boolean preHandle_old(HttpServletRequest request, HttpServletResponse response,
			Object object) throws Exception {

		ApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(request.getSession()
						.getServletContext());
		IUserInfoService userInfoService = (IUserInfoService) context
				.getBean("UserInfoService");
		
		String ua = request.getHeader("user-agent").toLowerCase();
		//System.out.println("ua");
		//从微信服务器发起的动作，会先调用一次这个方法，判断通不通。通了之后才会走下面的。
		if (request.getParameter("signature") != null && request.getParameter("timestamp") != null 	&& request.getParameter("nonce") != null) {
			return true;
		}
		
		//上线用这里
		/*if (ua.toLowerCase().indexOf("micromessenger") == -1 ) {
			return false;
		}*/
		
		//本地用这里
		if (ua.toLowerCase().indexOf("micromessenger") != 0 ) {
			System.out.println(".....no mobile request..........IP:..."+request.getRemoteAddr());
			request.getSession()
			.setAttribute(
					Cts.USER_IN_SESSION,
					userInfoService
					.getUserInfoByOpenId("oxoAruKbGePcjWUebsUAy_G_YfRo"));   //原始版本
		}
		
		//查找session是否有值。
		UserInfo userSession = (UserInfo) request.getSession().getAttribute(Cts.USER_IN_SESSION);
		if (userSession != null) {
			return true;
		}
		
		UserInfo userInfo = auth(request,response,request.getSession().getId());
		if(userInfo != null){
			if (userInfoService.upateUserInfo(userInfo) == 1) {
				request.getSession().setAttribute(Cts.USER_IN_SESSION,userInfoService
						.getUserInfoByOpenId(userInfo.getOpenid())); //这里拿openid。如果我删除了，要他们拿openid取数据，必然是空。
			}
		}
		
		return true;
	}
	
	
	//老的授权代码。
	private UserInfo auth(HttpServletRequest request,HttpServletResponse response,String key)
			throws ClientProtocolException, IOException {
		
		String code = request.getParameter("_c_o_d_e_");
		if(code==null){
			return null;
		}
		
		UserInfo user =null;
		AccessToken token = null;
		token = TokenProxy.getOuthToken(code,key);
		
		System.out.println("code=="+code);
		System.out.println("key=="+key);
		
		if (token != null && token.getOpenid()!=null) {
		
			String url=WxConfig.USERINFO_URL.replace("ACCESS_TOKEN", token.getToken()).replace("OPENID",
					token.getOpenid());
			//2016.9.14 add by Wally
			System.out.println("获取用户基本信息："+url);
			
			JSONObject userInfo = HttpUtil.doGet(url);
			
			if (userInfo != null) {
				user=new UserInfo();
				if (userInfo.get("city") != null) {
					user.setCity(userInfo.getString("city"));
				}
				if (userInfo.get("province") != null) {
					user.setProvince(userInfo.getString("province"));
				}
				if (userInfo.get("country") != null) {
					user.setCountry(userInfo.getString("country"));
				}
				if (userInfo.get("sex") != null) {
					// 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
					user.setGender(userInfo.getInt("sex"));
				}
				if (userInfo.get("headimgurl") != null) {
					user.setHeadPic(userInfo.getString("headimgurl"));
				}
				if (userInfo.get("openid") != null) {
					user.setOpenid(userInfo.getString("openid"));
				}
				if (userInfo.get("unionid") != null) {
					user.setUnionid(userInfo.getString("unionid"));
				}
				if (userInfo.get("nickname") != null) {
					user.setNickname(userInfo.getString("nickname"));
				}
				
			}
		}
		return user;
	}

	
	
}

