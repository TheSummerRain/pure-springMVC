package com.wsh.base.model.wechat;

public class WxJsConfig {

	/*
	 * appId: '', // 必填，公众号的唯一标识 timestamp: , // 必填，生成签名的时间戳 nonceStr: '', //
	 * 必填，生成签名的随机串 signature: '',// 必填，签名，见附录1 jsApiList: [] //
	 * 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	 */
	private String appId;
	private Long timestamp;
	private String nonceStr;
	private String signature;
	private String jsApiList;
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}

	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getJsApiList() {
		return jsApiList;
	}
	public void setJsApiList(String jsApiList) {
		this.jsApiList = jsApiList;
	}
	
}
