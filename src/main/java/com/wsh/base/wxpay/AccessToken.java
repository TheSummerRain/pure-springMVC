package com.wsh.base.wxpay;

import java.io.Serializable;
import java.util.Date;

public class AccessToken implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String token;
	private Integer expiresIn;
	private String refreshToken;
	private String openid;
	private String scope;
	private String unionid;
	private Long startedTime;
	
	
	public AccessToken() {
		super();
		this.startedTime=new Date().getTime();
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Integer getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getUnionid() {
		return unionid;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	
	public  boolean isExpired(AccessToken token){
		if(token==null || startedTime ==null){
			return true;
		}
		if(new Date().getTime()-startedTime<=(token.getExpiresIn()-200)*1000){
			return false;
		};
		return true;
	}
	
	@Override
	public String toString() {
		return "AccessToken [token=" + token + ", expiresIn=" + expiresIn
				+ ", refreshToken=" + refreshToken + ", openid=" + openid
				+ ", scope=" + scope + ", unionid=" + unionid + "]";
	}

	
	
}
