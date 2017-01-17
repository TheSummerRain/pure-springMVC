package com.wsh.base.controller;

public class TextMain {

	public static void main(String[] args) {
		
		String bank ="INSERT into mywallet(fromUserId,toUserId,cmType,money,createTime,sourceDes) values (1346,793,2,50000,'2016-02-06 06:20:34','一级好友:🐱  周慧 🌻成功开通会员');";
		
		System.out.println(bank.replace(" 🌻", ""));
	}
	
}
