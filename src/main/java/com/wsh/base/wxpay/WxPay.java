package com.wsh.base.wxpay;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.wsh.base.util.dateUtil.DateUtil;

public class WxPay implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String orderNo;
	private String payFee;
	private String body;
	private String detail;
	private String attach;
	private String openId;
	private String spbillIp;
	private String timeStarted;
	private String timeExpire;
	private String viewName;//1-维币充值。2-开通会员。
	
	
	public String getViewName() {
		return viewName;
	}
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getPayFee() {
		return payFee;
	}
	public void setPayFee(String payFee) {
		this.payFee = payFee;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getSpbillIp() {
		return spbillIp;
	}
	public void setSpbillIp(String spbillIp) {
		this.spbillIp = spbillIp;
	}
	
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	@Override
	public String toString() {
		return "WxPay [orderNo=" + orderNo + ", payFee=" + payFee + ", body="
				+ body + ", attach=" + attach + ", openId=" + openId
				+ ", spbillIp=" + spbillIp + "]";
	}
	public String getTimeStarted() {
		return timeStarted;
	}
	public void setTimeStarted(String timeStarted) {
		this.timeStarted = timeStarted;
		if(StringUtils.isNotBlank(timeStarted)){
			this.timeExpire=DateUtil.addMinute(timeStarted, 10);
		}
	}
	public String getTimeExpire() {
		return timeExpire;
	}
	public void setTimeExpire(String timeExpire) {
		this.timeExpire = timeExpire;
	}

}
