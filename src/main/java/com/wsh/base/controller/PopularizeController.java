package com.wsh.base.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wsh.base.dict.BaseDict;
import com.wsh.base.model.UserInfo;
import com.wsh.base.util.TokenProxy;
import com.wsh.base.util.dateUtil.DateUtil;
import com.wsh.base.util.oss.OSSUtil;
import com.wsh.base.wxpay.HttpUtil;
import com.wsh.base.wxpay.WxConfig;

@Controller
@RequestMapping("/popularize")
public class PopularizeController extends BaseController {

	private static final String OSS_KEY = "qrcode/";

	@RequestMapping("/masterPage")
	public ModelAndView masterPage(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView(getPreFix() + "tuiguangdashi");
		UserInfo user = super.getUser(request);
		if (user != null) {
			mv.addObject("AIncomeNo",BaseDict.POP_ABC_INCOME); //前三收益
			mv.addObject("DIncomeNo",BaseDict.POP_DEF_INCOME); //后三收益
			mv.addObject("nowdate", DateUtil.getDate(user.getVipEnddate()));
			if (user.getpId() != null) {
				mv.addObject("parent", userInfoService.getNickNameByUserID(user
						.getpId()));
			}
			if (user.getUserId() != null) {
				newCount(mv, user.getUserId());
			}
		}
		return mv;
	}

	//使用子查询
	public void newCount(ModelAndView mv, Integer userId){

		HashMap<String, Integer> countMap = userInfoService.getAllCountNum(userId);
		mv.addObject("p1", countMap.get("couA"));
		mv.addObject("m1", countMap.get("vipA"));
		mv.addObject("p2", countMap.get("couB"));
		mv.addObject("m2", countMap.get("vipB"));
		mv.addObject("p3", countMap.get("couC"));
		mv.addObject("m3", countMap.get("vipC"));
		
		//从此在没有 DEF级  2016.5.19 modify by Wally
		mv.addObject("p4",0);
		mv.addObject("m4",0);
		mv.addObject("p5",0);
		mv.addObject("m5",0);
		mv.addObject("p6",0);
		mv.addObject("m6",0);
		mv.addObject("hasDEF",false);
		
		
	/*	if( countMap.get("showDEF").intValue()==1){
			mv.addObject("p4", countMap.get("couD"));
			mv.addObject("m4", countMap.get("vipD"));
			mv.addObject("p5", countMap.get("couE"));
			mv.addObject("m5", countMap.get("vipE"));
			mv.addObject("p6", countMap.get("couF"));
			mv.addObject("m6", countMap.get("vipF"));
			mv.addObject("hasDEF",true);
			
		}else{
			mv.addObject("p4",0);
			mv.addObject("m4",0);
			mv.addObject("p5",0);
			mv.addObject("m5",0);
			mv.addObject("p6",0);
			mv.addObject("m6",0);
			mv.addObject("hasDEF",false);
		}*/
	
	}
	
	
	@RequestMapping("/becomesMaster")
	public ModelAndView becomesMaster(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView(getPreFix() + "tuiguangdashi");
		UserInfo user = super.getUser(request);

		if (user != null) {
			if (user.getpId() != null) {
				mv.addObject("parent", userInfoService.getUserInfoByUserID(user
						.getpId().intValue()));
			}
			if (user.getUserId() != null) {
			    //第一次为推广大使，肯定没啥数据，直接给0；
				mv.addObject("p2", 0);
				mv.addObject("m2", 0);
				mv.addObject("p3", 0);
				mv.addObject("m3", 0);
				mv.addObject("wb", 0);
				mv.addObject("popularity", 0); 
				mv.addObject("members", 0);
				mv.addObject("nowdate", DateUtil.getDate(user.getVipEnddate()));
			}
			user.setIsMaster(Boolean.TRUE);
			userInfoService.upateUserInfo(user);
		}
		return mv;
	}

	/**
	 * 
	 * @Title friendPage
	 * @author Wally
	 * @time 2016年2月23日上午9:51:28
	 * @Description 【关闭使用】
	 * @param
	 * @return ModelAndView
	 */
	@RequestMapping("/friendPage")
	public ModelAndView friendPage(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView(getPreFix() + "tuiguangdashi_friend");
		UserInfo user = super.getUser(request);
		if (user != null) {
			if (user.getUserId() != null) {
				mv.addObject("popularity",
						userInfoService.selectChild(user.getUserId(), 1));

				mv.addObject("members",
						userInfoService.filtersMembers(user.getUserId(), 1));

				mv.addObject("list",
						userInfoService.selectFirst(user.getUserId()));
			}
		}
		return mv;
	}
	
	//专门处理A。只用select
	@RequestMapping("/findAPage")
	public ModelAndView friendPage_A(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView(getPreFix() + "tuiguangdashi_friend");
		UserInfo user = super.getUser(request);
		if (user != null) {
			if (user.getUserId() != null) { 
				mv.addObject("popularity",
						userInfoService.friendPage_A_allCount(user.getUserId()));
				mv.addObject("members",
						userInfoService.friendPage_A_memberCount(user.getUserId()));
				mv.addObject("list",
						userInfoService.friendPage_A_list(user.getUserId()));
			}
		}
		return mv;
	}
	
	
	@RequestMapping("/rulePage")
	public ModelAndView rulePage() {
		ModelAndView mv = new ModelAndView(getPreFix() + "tuiguangdashi_gz");
		return mv;
	}

	@RequestMapping("/qrcodePage")
	public ModelAndView qrcodePage(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView(getPreFix() + "tuiguangdashi_tgm");
		UserInfo user = getUser(request);
		if (user == null || user.getUserId() == null) {
			return mv;
		}
		String basePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();		
		mv.addObject("user",user);
		mv.addObject("sharePath", basePath+BaseDict.EXP_BASEPATH_METHOD+BaseDict.SHAREPATH_METHOD_QRCODE+user.getUserId()); //分享时关注的路径。
	//	System.out.println( basePath+BaseDict.EXP_BASEPATH_METHOD+BaseDict.SHAREPATH_METHOD_QRCODE+user.getUserId()+"================================================");
		mv.addObject("modelValue",BaseDict.DISGUISERWHO); //伪装ID
		mv.addObject("disguisernum",BaseDict.DISGUISERNUM); //伪装ID
		if (!StringUtils.isBlank(user.getQrcode())) {
			mv.addObject("qr_url", user.getQrcode());
			return mv;
		}
		try {
			mv.addObject("qr_url", getQRCode(user.getUserId(), user.getOpenid(),request));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	
	//获取二维码。
	private String getQRCode(Integer userId, String openId,HttpServletRequest request)
			throws ClientProtocolException, IOException {
		// get ticket
		JSONObject ticket = getTicket(userId);
		if (ticket == null || ticket.get("ticket") == null) {
			return null;
		}
		// get qrcode
		@SuppressWarnings("deprecation")
		String encodedTicket = URLEncoder.encode(ticket.get("ticket")
				.toString());
		String qr_url = downloadQRCodeThenUpload(
				WxConfig.SHOWQRCODE_RUL.replace("TICKET", encodedTicket),
				openId);
		UserInfo user = new UserInfo();
		user.setOpenid(openId);
		user.setQrcode(qr_url);
		if (userInfoService.upateUserInfo(user) > 0) {
			super.setUser(userInfoService.getUserInfoByUserID(userId),request);
		}
		return qr_url;
	}
	
	

	private JSONObject getTicket(Integer userId)
			throws ClientProtocolException, IOException {
		String url = WxConfig.QRCODE_RUL.replace("TOKEN",
				TokenProxy.getCommonToken());
		JSONObject parameters = new JSONObject();
		JSONObject action_info = new JSONObject();
		JSONObject scene = new JSONObject();

		scene.element("scene_str", userId);
		scene.element("scene_id", userId);
		action_info.accumulate("scene", scene);
		parameters.accumulate("action_info", action_info);
		parameters.accumulate("action_name", "QR_LIMIT_SCENE");
		JSONObject ticket = HttpUtil.httpPost(url, parameters.toString());
		return ticket;
	}

	private static final String getPreFix() {
		return "/popularize/";
	}

	private static String downloadQRCodeThenUpload(String url, String owner) {
		File file = null;
		FileOutputStream output = null;
		CloseableHttpResponse response = null;
		try {
			file = File.createTempFile(owner, ".jpg");
			HttpGet httpGet = new HttpGet(url);
			response = HttpUtil.createSSLClientDefault().execute(httpGet);
			output = new FileOutputStream(file);
			InputStream input = null;
			if (response == null || response.getEntity() == null) {
				return null;
			}
			input = response.getEntity().getContent();
			byte b[] = new byte[1024];
			int j = 0;
			if (input == null) {
				return null;
			}
			while ((j = input.read(b)) != -1) {
				output.write(b, 0, j);
			}
			if (output != null) {
				output.flush();
				output.close();
			}
			return OSSUtil.upload(file, OSS_KEY + owner);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (file != null) {
				file.deleteOnExit();
			}
			if (response != null) {
				try {
					response.close();
				} catch (Exception e) {
					// NOOP
				}
			}
		}
		return null;
	}
	
	

	//暂时作废
	private void count(ModelAndView mv, Integer userId) {

		int p1 = userInfoService.selectChild(userId, 1);    
		int m1 = userInfoService.filtersMembers(userId, 1); //A级用户是单独页面。所以这里没必要显示。

		int p2 = userInfoService.selectChild(userId, 2);
		int m2 = userInfoService.filtersMembers(userId, 2);

		int p3 = userInfoService.selectChild(userId, 3);
		int m3 = userInfoService.filtersMembers(userId, 3);
		
		int l2p = Math.max(p2 - p1, 0);
		int l2m = Math.max(m2 - m1, 0);
		mv.addObject("p2", l2p);
		mv.addObject("m2", l2m);
		int l3p = Math.max(p3 - p2 , 0);
		int l3m = Math.max(m3 - m2 , 0);
		mv.addObject("p3", l3p);
		mv.addObject("m3", l3m);
		
		if(m1 > BaseDict.POP_A_ENOUGH_PERSON){ //A级用户满50人才计算后面的。
			int p4 = userInfoService.selectChild(userId, 4);
			int m4 = userInfoService.filtersMembers(userId, 4);
			int p5 = userInfoService.selectChild(userId, 5);
			int m5 = userInfoService.filtersMembers(userId, 5);
			int p6 = userInfoService.selectChild(userId, 6);     //我的人气 --充值和非充值。。
			int m6 = userInfoService.filtersMembers(userId, 6);  //我的人气 -- 付费人数。
			
			mv.addObject("popularity", p6);
			mv.addObject("members", m6);
			mv.addObject("hasDEF",true);
			
			int l4p = Math.max(p4 - p3, 0);
			int l4m = Math.max(m4 - m3 , 0);
			mv.addObject("p4", l4p);
			mv.addObject("m4", l4m);
			int l5p = Math.max(p5 - p4 , 0);
			int l5m = Math.max(m5 - m4, 0);
			mv.addObject("p5", l5p);
			mv.addObject("m5", l5m);
			int l6p = Math.max(p6 - p5 , 0);
			int l6m = Math.max(m6 - m5 , 0);
			mv.addObject("p6", l6p);
			mv.addObject("m6", l6m);
			
		}else{ //只显示 前三级
			mv.addObject("popularity", p3); // 如果A级不到50人，总人数就不算DEF的。只算3级。
			mv.addObject("members", m3);
			mv.addObject("hasDEF",false);
		}
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
