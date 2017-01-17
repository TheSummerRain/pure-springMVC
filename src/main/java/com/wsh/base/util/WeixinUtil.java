package com.wsh.base.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;

import com.wsh.base.model.wechat.WxJsConfig;
import com.wsh.base.model.wechat.menu.Button;
import com.wsh.base.model.wechat.menu.ComplexButton;
import com.wsh.base.model.wechat.menu.Menu;
import com.wsh.base.model.wechat.menu.ViewButton;
import com.wsh.base.wxpay.AccessToken;
import com.wsh.base.wxpay.HttpUtil;
import com.wsh.base.wxpay.Utils;
import com.wsh.base.wxpay.WxConfig;

public class WeixinUtil {
	private static final Log logger = LogFactory.getLog(WeixinUtil.class);

	private static final String newUrl=WxConfig.DOMAIN+"Wx/";


	//获取jssdk ticket.
	public static String getJsAPI_ticket(String tocken) throws ClientProtocolException, IOException{
		logger.info("..................获取ticket...............");
		String requestUrl = WxConfig.JSAPI_TICKET_URL.replace("ACCESS_TOKEN",tocken);
		JSONObject jsonObject = HttpUtil.doGet(requestUrl);
		//System.out.println(".....【jsonObject:】【"+jsonObject+"】......");
		if(null != jsonObject){
			if(jsonObject.get("errmsg").equals("ok")){
			}else{
				logger.info("...请求jsapi_ticket 失败："+jsonObject.get("errmsg"));
				return null;
			}
			if (jsonObject.get("ticket") != null) {
				return jsonObject.getString("ticket");
			}
		}
		return null;
	}
	
	
	
	
	
	public static AccessToken getAccessToken(String codeOrRefreshToken,Boolean isRefreshToken)
			throws ClientProtocolException, IOException {
		//System.out.println("............【获取accessToken 开始】........................"); 
		AccessToken accessToken = null;
		String requestUrl = WxConfig.COMMON_ACCESS_TOKEN_URL.replace("APPID",
				WxConfig.APPID).replace("APPSECRET", WxConfig.APP_SECRET);

		if (codeOrRefreshToken != null && !"".equals(codeOrRefreshToken.trim())) {
			if(isRefreshToken==null || !isRefreshToken){
				requestUrl = WxConfig.OAUTH_ACCESS_TOKEN_URL
						.replace("APPID", WxConfig.APPID)
						.replace("SECRET", WxConfig.APP_SECRET)
						.replace("CODE", codeOrRefreshToken);
			}else{
				requestUrl=WxConfig.REFRESH_TOKEN_URL.replace("APPID",WxConfig.APPID).replace("REFRESH_TOKEN", codeOrRefreshToken);
			}
		}
		JSONObject jsonObject = HttpUtil.doGet(requestUrl);
		// 如果请求成功
		if (null != jsonObject) {
			if(jsonObject.get("errmsg")!=null){
				return null;
			}
			accessToken = new AccessToken();
			accessToken.setToken(jsonObject.getString("access_token"));
			accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
			// auth
			if (jsonObject.get("openid") != null) {
				accessToken.setOpenid(jsonObject.getString("openid"));
			}
			if (jsonObject.get("refresh_token") != null) {
				accessToken.setRefreshToken(jsonObject
						.getString("refresh_token"));
			}
			if (jsonObject.get("scope") != null) {
				accessToken.setScope(jsonObject.getString("scope"));
			}
			if (jsonObject.get("unionid") != null) {
				accessToken.setUnionid(jsonObject.getString("unionid"));
			}
		}
		return accessToken;
	}

	
	
	public static int createMenu(Menu menu, String accessToken)
			throws ClientProtocolException, IOException {
		int result = 0;
		// 拼装创建菜单的url
		String url = WxConfig.MENU_CREATE_URL.replace("ACCESS_TOKEN",
				accessToken);
		// 将菜单对象转换成json字符串
		String jsonMenu = JSONObject.fromObject(menu).toString();
		// 调用接口创建菜单
		JSONObject jsonObject = HttpUtil.httpPost(url, jsonMenu);

		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
			}
		}

		return result;
	}

	@SuppressWarnings("deprecation")
	private static Menu getMenu() {

		String state="NewWx";
	
		ViewButton btn11 = new ViewButton();
		btn11.setName("【维】动态");
		btn11.setType("view");
		btn11.setUrl(WxConfig.ACCESS_CODE_URL
				.replace("APPID", WxConfig.APPID)
				.replace(
						"REDIRECT_URI",
						java.net.URLEncoder
								.encode(newUrl+"loading/wei_weiList"))
				.replace("SCOPE", WxConfig.SCOPE).replace("STATE", state));

		
		
		
		
		ViewButton btn21 = new ViewButton();
		btn21.setName("【书】籍解读");
		btn21.setType("view");
		btn21.setUrl(WxConfig.ACCESS_CODE_URL
				.replace("APPID", WxConfig.APPID)
				.replace(
						"REDIRECT_URI",
						java.net.URLEncoder
								.encode(newUrl+"loading/bookc_getFristList"))
				.replace("SCOPE", WxConfig.SCOPE).replace("STATE",state));

		
		
		ViewButton btn31 = new ViewButton();
		btn31.setName("我的账号");
		btn31.setType("view");
		btn31.setUrl(WxConfig.ACCESS_CODE_URL
				.replace("APPID", WxConfig.APPID)
				.replace(
						"REDIRECT_URI",
						java.net.URLEncoder
								.encode(newUrl+"loading/user_toUserCenterPage"))
				.replace("SCOPE", WxConfig.SCOPE).replace("STATE", state));


		ViewButton btn32 = new ViewButton();
		btn32.setName("开通会员");
		btn32.setType("view");
		btn32.setUrl(WxConfig.ACCESS_CODE_URL
				.replace("APPID", WxConfig.APPID)
				.replace(
						"REDIRECT_URI",
						java.net.URLEncoder
								.encode(newUrl+"loading/user_joinMembership"))
				.replace("SCOPE", WxConfig.SCOPE).replace("STATE", state));


		ViewButton btn33 = new ViewButton();
		btn33.setName("会员续费");
		btn33.setType("view");
		btn33.setUrl(WxConfig.ACCESS_CODE_URL
				.replace("APPID", WxConfig.APPID)
				.replace(
						"REDIRECT_URI",
						java.net.URLEncoder
								.encode(newUrl+"loading/user_renewal"))
				.replace("SCOPE", WxConfig.SCOPE).replace("STATE", state));
		
		
		ComplexButton btn1=new ComplexButton();
		btn1.setName("【维】动态");
		btn1.setSub_button(new Button[]{btn11});
		
		ComplexButton btn2=new ComplexButton();
		btn2.setName("【书】籍解读");
		btn2.setSub_button(new Button[]{btn21});
		
		ComplexButton btn3=new ComplexButton();
		btn3.setName("会员");
		btn3.setSub_button(new Button[]{btn33,btn32,btn31});
		
		
		
		
		
		Menu menu = new Menu();
		menu.setButton(new Button[] { btn11,btn21,btn3 });

		return menu;
	}

	public static void main(String[] args) {
		String at=TokenProxy.getCommonToken();
		System.out.println(at);
		try {
			if (at != null) {
				WeixinUtil.createMenu(getMenu(),at);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// getAll();
	}
	
	
	public static void getAll() {

	//	Map<String, String> map = new HashMap<String, String>();
		String uri = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		uri = uri.replace("ACCESS_TOKEN", TokenProxy.getCommonToken()).replace(
				"OPENID", "oxoAruKbGePcjWUebsUAy_G_YfRo");
		/*uri = uri.replace("ACCESS_TOKEN", TokenProxy.getCommonToken()).replace(
				"NEXT_OPENID", "oxoAruFEzu9RB8fTlvMX4yW7xztA"); *///oxoAruFEzu9RB8fTlvMX4yW7xztA   //NEXT_OPENID
		try {
			JSONObject json = HttpUtil.doGet(uri);
			System.out.println(json);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	//	map.put("result", "successful");
	}
	
	
	//获取jsconfig签名。
		public static WxJsConfig getJsSdkConfig(String URlpath) throws Exception {
			//签名生成与校验。
			String jsapi_ticket = TokenProxy.get_jsapi_ticket();
		//	System.out.println(".....ticket=="+jsapi_ticket);
			if(null == jsapi_ticket){
				return null;
			}
			long timer = Utils.genTimeStamp();
			String nonceSt =Utils.genNonceStr();
			
			//ascll 从小到大。
			String preSha1 = "jsapi_ticket="+jsapi_ticket+"&noncestr="+nonceSt+"&timestamp="+timer+"&url="+URlpath;
		//	System.err.println("Sha1签名前--preSha1=="+preSha1);
			//sha1签名。
			String sha1Value = new SHA1().getDigestOfString(preSha1.getBytes());
			//System.out.println("Sha1加密后--sha1Value=="+sha1Value);
			
			WxJsConfig wxc = new WxJsConfig();
			wxc.setAppId(WxConfig.APPID);
			wxc.setTimestamp(timer);
			wxc.setNonceStr(nonceSt);
			wxc.setJsApiList(WxConfig.JSAPI_SHARE_FUNTION_LIST);
			wxc.setSignature(sha1Value);
			return wxc;
		}
	
	
}
