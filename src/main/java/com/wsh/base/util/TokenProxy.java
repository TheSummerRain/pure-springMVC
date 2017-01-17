package com.wsh.base.util;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;

import redis.clients.jedis.Jedis;

import com.wsh.base.wxpay.AccessToken;
import com.wsh.base.wxpay.WxConfig;

/**
 * 
 * @package : com.wsh.base.util
 * @ClassName: TokenProxy
 * @Description: 获取token。touken失效时间是2个小时。
 * @author Wally
 * @date 2016年9月14日 下午5:00:44
 * @modify Wally
 * @modifyDate 2016年9月14日 下午5:00:44
 */


public class TokenProxy {
	//private static final String  COMMON_TOKEN="COMMON-TOKEN_DEV";
	private static final String  COMMON_TOKEN="COMMON-TOKEN";
	private static final Log logger = LogFactory.getLog(TokenProxy.class);
	//获取token.
	public static final String getCommonToken() {
		Jedis jedis = RedisUtil.getJedis();
		String value = jedis.get(COMMON_TOKEN);
			try {
				if (value == null || "".equals(value.trim())) {
				AccessToken token = WeixinUtil.getAccessToken(null,null);  //普通tocken获取。
				if (token != null && !"".equals(token.getToken())) {
					jedis.set(COMMON_TOKEN, token.getToken());
					// access_token的有效期目前为2个小时
					jedis.expire(COMMON_TOKEN, token.getExpiresIn()-200);
					return token.getToken();
				}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				RedisUtil.returnResource(jedis);
			}
		return value;
	}
	
	
	public static final AccessToken getOuthToken(String code,String key) {
		Jedis jedis = RedisUtil.getJedis();
		AccessToken token = null;
		try {
			byte[] bytes = jedis.get(SerializeUtil.serialize(key));
			if (bytes == null || bytes.length == 0) {
				token = WeixinUtil.getAccessToken(code,false); //授权tocken 获取，于普通的tocken不同。
				if (token != null && !"".equals(token.getToken())) {
				//	System.out.println("get token from Wx..."+token.toString());
					jedis.set(SerializeUtil.serialize(key),
							SerializeUtil.serialize(token));
				}
			}else{
				token = (AccessToken) SerializeUtil.unserialize(bytes);
				//是否需要刷新Token
				if(new AccessToken().isExpired(token) && !"".equals(token.getRefreshToken())){
					token=WeixinUtil.getAccessToken(token.getRefreshToken(), true);
					if (token != null && !"".equals(token.getToken())) {
						//System.out.println("refresh token from Wx..."+token.toString());
						jedis.set(SerializeUtil.serialize(key),
								SerializeUtil.serialize(token));
					}
				}
				if(token!=null){
					//System.out.println("get token from redis..."+token.toString());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			RedisUtil.returnResource(jedis);
		}
		return token;
	}
	
	//http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html#.E9.99.84.E5.BD.951-JS-SDK.E4.BD.BF.E7.94.A8.E6.9D.83.E9.99.90.E7.AD.BE.E5.90.8D.E7.AE.97.E6.B3.95
	//获取jsapi_ticket .类似token。7200秒。
	public static final String get_jsapi_ticket() throws ClientProtocolException, IOException{
		logger.info("........获取jsapi_ticket........");
		Jedis jedis = RedisUtil.getJedis();
		String value = jedis.get(WxConfig.JEDIS_KEY_JSAPI);
		if (value == null || "".equals(value.trim())) {
			value = WeixinUtil.getJsAPI_ticket(getCommonToken());
			logger.info("..........【ticket："+value+"】.......");
			jedis.set(WxConfig.JEDIS_KEY_JSAPI, value);
			jedis.expire(WxConfig.JEDIS_KEY_JSAPI, 7021);
			RedisUtil.returnResource(jedis);
			return value;
		}
		RedisUtil.returnResource(jedis); //释放
		return value;
	}
	
	
	
	
}
