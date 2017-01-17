package com.wsh.base.utilhttp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.wsh.base.wxpay.HttpUtil;
import com.wsh.base.wxpay.XmlUtil;

public class HttpSendUtil {
	
	//企业支付 到 微信个人账户。
	/**
	 * 
	 * @author Wally
	 * @time 2016年5月23日下午6:14:02
	 * @Description 调用发送数据到 微信。并返回微信的处理结果
	 * @param
	 * @return Map<String,String>
	 */
	public static Map<String, String> SendToWecat(String baseXmlModel,String toSendUrl,Integer userid) //WxConfig.ENTERPRISEPAYMENT
			throws ClientProtocolException, IOException,Exception {

		String url = String.format(toSendUrl);                   //发送地址
		System.out.println("组装后的数据内容：= "+baseXmlModel);
		
		String content = null;
		//content = HttpUtil.doPost(url, baseXmlModel);   //发送数据，并返回响应结果。
		
		content = HttpUtil.custDoPost(url, baseXmlModel);   //发送数据，并返回响应结果。
		System.out.println("=======返回数据字符串========"+content);
		
		Map<String, String> xml = decodeXml(content);
		
		showBackDataInfo(xml);   //展示返回内容。【后面没用可以关闭】
		
		return xml;
	
	}
	
	
	//xml新解析。  针对的是  提现的xml数据。
	public static Map<String, String> decodeXml(String content) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			Document doc = DocumentHelper.parseText(content);
			Element root = doc.getRootElement();
			String returnCode = root.element("return_code").getTextTrim();
			String resultCode = root.element("result_code").getTextTrim();
			if (StringUtils.equals("SUCCESS", returnCode) && StringUtils.equals("SUCCESS", resultCode)) {
				result.put("partner_trade_no", root.element("partner_trade_no").getTextTrim());  //我们的订单号
				result.put("payment_no", root.element("payment_no").getTextTrim());  //微信流水号
				result.put("payment_time", root.element("payment_time").getTextTrim());  //支付时间
			} else {
				
				if(StringUtils.equals("FAIL", returnCode)){
					result.put("return_code", "FAIL");
					result.put("return_msg",  root.element("return_msg").getTextTrim());
				}
				if(StringUtils.equals("FAIL", resultCode)){
					result.put("result_code", "FAIL");
				}

				result.put("err_code",  root.element("err_code").getTextTrim());
				result.put("err_code_des",  root.element("err_code_des").getTextTrim());
			}
		} catch (DocumentException e) {
			result.put("return_code", "FAIL");
			result.put("return_msg", e.getMessage());
		}

		return result;
	}
	
	
	
	
	//显示  通讯返回的书籍内容。
	private static void showBackDataInfo( Map<String, String> backData){
		  Set<Map.Entry<String,String>> sets=backData.entrySet();
		  
		  for(Map.Entry<String,String> entry:sets){
			  System.out.println("====>>> 返回：key:"+entry.getKey()+" value:"+entry.getValue());
		  
		  }
		
	}
	
	
	
	
	
}
