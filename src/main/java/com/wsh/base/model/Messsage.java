package com.wsh.base.model;


public class Messsage {

	private String msg;
	private String phoneNum;
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	
	
	public Messsage() {
		super();
	}
	public Messsage(String msg, String phoneNum) {
		super();
		this.msg = msg;
		this.phoneNum = phoneNum;
	}
	
	
}
