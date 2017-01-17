package com.wsh.base.utilhttp.dataEntity;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.message.BasicNameValuePair;

import com.wsh.base.wxpay.MD5;
import com.wsh.base.wxpay.MD5Util;
import com.wsh.base.wxpay.WxConfig;

//基础数据域  字段
public class BaseXmlModel {
	
	//xml组装
	public static String toXml(List<BasicNameValuePair> params) 
			throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		for (int i = 0; i < params.size(); i++) {
			sb.append("<" + params.get(i).getName() + ">");
			sb.append("<![CDATA["+params.get(i).getValue()+"]]>");
			sb.append("</" + params.get(i).getName() + ">");
		}
		sb.append("</xml>");
		return sb.toString();
	}

	//签名随机串
	public static String getNonceStr() throws UnsupportedEncodingException {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000))
				.getBytes());
	}

	//时间戳。
	public static long genTimeStamp() {
		return System.currentTimeMillis() / 1000;
	}
	//签名算法规则 生成
	public static String getSign(List<BasicNameValuePair> params)
			throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		Collections.sort(params, new Comparator<BasicNameValuePair>() {
			@Override
			public int compare(BasicNameValuePair o1, BasicNameValuePair o2) {
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});

		// https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=4_3
		// 第一步，设所有发送或者接收到的数据为集合M，将集合M内非空参数值的参数按照参数名ASCII码从小到大排序（字典序），
		// 使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串stringA。
		// 特别注意以下重要规则：
		// ◆ 参数名ASCII码从小到大排序（字典序）；
		// ◆ 如果参数的值为空不参与签名；
		// ◆ 参数名区分大小写；
		// ◆ 验证调用返回或微信主动通知签名时，传送的sign参数不参与签名，将生成的签名与该sign值作校验。
		// ◆ 微信接口可能增加字段，验证签名时必须支持增加的扩展字段
		for (int i = 0; i < params.size(); i++) {
			if (!StringUtils.isBlank(params.get(i).getValue())) {
				sb.append(params.get(i).getName());
				sb.append('=');
				sb.append(params.get(i).getValue());
				sb.append('&');
			}
		}
		// 第二步，在stringA最后拼接上key得到stringSignTemp字符串，并对stringSignTemp进行MD5运算，再将得到的字符串所有字符转换为大写，得到sign值signValue
		sb.append("key=");
		sb.append(WxConfig.APP_KEY);
		return MD5Util.MD5Encode(sb.toString(), "UTF-8").toUpperCase();
	}
	
	
}
