package com.wsh.base.utilhttp.dataEntity;

//公共数据参数
public class PubData {

	private Integer userid;
	
	private String orderNo; // 本地订单号
	
	private String openid; //用户openid
	
	private int amount;      //金额
	
	private String ipStr;  //ip地址

	private String descString;  //详细描述
	
	private String mobile; //手机号
	
	//--------------------------------------------------------
	
	
	
	public String getOrderNo() {
		return orderNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getIpStr() {
		return ipStr;
	}

	public void setIpStr(String ipStr) {
		this.ipStr = ipStr;
	}

	public String getDescString() {
		return descString;
	}

	public void setDescString(String descString) {
		this.descString = descString;
	}
	
	
	
}
