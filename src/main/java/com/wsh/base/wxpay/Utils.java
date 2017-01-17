package com.wsh.base.wxpay;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Utils {

	public static JSONObject payRequestSponsor(WxPay wxPay) {
		
//		https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=7_7&index=6
		String prepayId = "";
		JSONObject data = new JSONObject();
		try {
			
//			除被扫支付场景以外，商户系统先调用该接口在微信支付服务后台生成预支付交易单
//			返回正确的预支付交易回话标识后再按扫码、JSAPI、APP等不同场景生成交易串调起支付
			prepayId = getPrepayId(wxPay);
			if(prepayId!=null && !"".equals(prepayId)){
				String packageValue = "prepay_id=" + prepayId;
				String nonceStr = genNonceStr();
				String timeStamp = String.valueOf(genTimeStamp());
				//sign start
				List<BasicNameValuePair> signParams = new LinkedList<BasicNameValuePair>();
					signParams.add(new BasicNameValuePair("appId", WxConfig.APPID));
					signParams.add(new BasicNameValuePair("timeStamp", timeStamp));
					signParams.add(new BasicNameValuePair("nonceStr", nonceStr));
					signParams.add(new BasicNameValuePair("package", packageValue));
					signParams.add(new BasicNameValuePair("signType", WxConfig.SIGNTYPE));
				String sign = paySign(signParams);
				//sign end
				
				data.accumulate("appId", WxConfig.APPID);
				data.accumulate("timeStamp", timeStamp);
				data.accumulate("nonceStr", nonceStr);
				data.accumulate("prepayId", packageValue);
				data.accumulate("signType", WxConfig.SIGNTYPE);
				data.accumulate("paySign", sign);
			
			}
		} catch (Exception e) {
			//NOOP
		}
		return data;
	}

	public static Map<String, String> queryPayOrderInfo(String transactionId,
			String orderNo) throws DocumentException, ClientProtocolException,
			IOException {
		Map<String, String> result = new HashMap<String, String>();
		String url = String.format(WxConfig.ORDERQUERY);
		String entity = orderqueryArgs(transactionId, orderNo);
		String content = null;
		content = HttpUtil.doPost(url, entity);

		Document doc = DocumentHelper.parseText(content);
		Element root = doc.getRootElement();
		Element tradeState = root.element("trade_state");
		//
		// SUCCESS—支付成功
		// REFUND—转入退款
		// NOTPAY—未支付
		// CLOSED—已关闭
		// REVOKED—已撤销（刷卡支付）
		// USERPAYING--用户支付中
		// PAYERROR--支付失败(其他原因，如银行返回失败)
		if (null != tradeState) {
			result.put("tradeState", tradeState.getTextTrim());
		}
		return result;
	}

	private static String genProductArgs(WxPay wxPay)
			throws UnsupportedEncodingException {

		String nonceStr = genNonceStr();
		List<BasicNameValuePair> packageParams = new LinkedList<BasicNameValuePair>();
		
		// https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1#
		packageParams.add(new BasicNameValuePair("appid", WxConfig.APPID));
		packageParams.add(new BasicNameValuePair("mch_id", WxConfig.MCH_ID));
		packageParams.add(new BasicNameValuePair("device_info",
				WxConfig.DEVICE_INFO));
		packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
		if(null!=wxPay && wxPay.getBody()!=null){
			packageParams.add(new BasicNameValuePair("body", wxPay.getBody()));// 商品描述
		}
		if(null!=wxPay && wxPay.getDetail()!=null){
			packageParams.add(new BasicNameValuePair("detail",wxPay.getDetail()));// 商品描述
		}
		if(wxPay!=null && wxPay.getAttach()!=null ){
			// 附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
			packageParams.add(new BasicNameValuePair("attach",wxPay.getAttach()));
		}
		if(wxPay!=null && wxPay.getOrderNo()!=null ){
			packageParams.add(new BasicNameValuePair("out_trade_no", wxPay.getOrderNo()));
		}

		packageParams.add(new BasicNameValuePair("fee_type", "CNY"));
		if(wxPay!=null && wxPay.getPayFee()!=null ){
		// 订单总金额，单位为分
		packageParams.add(new BasicNameValuePair("total_fee", wxPay.getPayFee()));
		}
		if(wxPay!=null && wxPay.getSpbillIp()!=null ){
			// APP和网页支付提交用户端ip
			packageParams.add(new BasicNameValuePair("spbill_create_ip", wxPay.getSpbillIp()));
		}
		
		if(wxPay!=null){
			if(StringUtils.isNoneBlank(wxPay.getTimeStarted()) && StringUtils.isNoneBlank(wxPay.getTimeExpire())){
				packageParams.add(new BasicNameValuePair("time_start", wxPay.getTimeStarted()));
				packageParams.add(new BasicNameValuePair("time_expire", wxPay.getTimeExpire()));
			}
		}

		// 商品标记，代金券或立减优惠功能的参数
		// packageParams.add(new BasicNameValuePair("goods_tag", ""));

		packageParams.add(new BasicNameValuePair("notify_url",
				WxConfig.NOTIFY_URL));

		packageParams.add(new BasicNameValuePair("limit_pay",
				WxConfig.LIMIT_PAY));

		packageParams.add(new BasicNameValuePair("trade_type",
				WxConfig.TRADE_TYPE));

		if ("JSAPI".equals(WxConfig.TRADE_TYPE)) {
			packageParams.add(new BasicNameValuePair("openid", wxPay.getOpenId()));
		}

		
		List<BasicNameValuePair> parameters=new ArrayList<BasicNameValuePair>();
		String sign = genPackageSign(packageParams);
		parameters.addAll(packageParams);
		parameters.add(new BasicNameValuePair("sign", sign));
		
		String xmlstring = toXml(parameters);
		return xmlstring.toString();

	}

	//组装需要发送的xml数据报文
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
		
		//20160504 WallySystem
	//	System.out.println(sb.toString());
		return sb.toString();
	}

	public static String genPackageSign(List<BasicNameValuePair> params)
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
//		System.out.println("str:"+sb.toString());
		return MD5Util.MD5Encode(sb.toString(), "utf-8").toUpperCase();
	}

	private static String paySign(List<BasicNameValuePair> params) throws UnsupportedEncodingException {
		return genPackageSign(params);
	}

	/**
	 * getPrepayId
	 * 
	 * @param orderNo
	 * @param payFee
	 * @param body
	 * @param attach
	 * @param openId
	 * @param spbillIp
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private static String getPrepayId(WxPay wxPay)
			throws ClientProtocolException, IOException {

		String url = String.format(WxConfig.UNIFIEDORDER); //统一支付下单的url接口
		String entity = genProductArgs(wxPay);  //数据域封装
		
		String content = null;
		content = HttpUtil.doPost(url, entity);
		Map<String, String> xml = XmlUtil.decodeXml(content);
		// 返回状态码 : return_code==> 通信标识，非交易标识
		if (StringUtils.equalsIgnoreCase("FAIL", xml.get("return_code"))) {
			// FAIL
		//	System.out.println( xml.get("return_msg"));
			return null;
		}

		if (StringUtils.equalsIgnoreCase("SUCCESS", xml.get("return_code"))
				&& StringUtils.equalsIgnoreCase("FAIL", xml.get("result_code"))) {
			// 通信成功,交易失败
		//	System.out.println( xml.get("return_msg"));
			return null;
		}

		return xml.get("prepay_id");
	}

	private static String orderqueryArgs(String transactionId, String orderNo)
			throws UnsupportedEncodingException {

		StringBuffer xml = new StringBuffer();
		String nonceStr = genNonceStr();
		xml.append("</xml>");
		List<BasicNameValuePair> packageParams = new LinkedList<BasicNameValuePair>();
		packageParams.add(new BasicNameValuePair("appid", WxConfig.APPID));
		packageParams.add(new BasicNameValuePair("mch_id", WxConfig.MCH_ID));
		packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
		packageParams.add(new BasicNameValuePair("out_trade_no", orderNo));
		String sign = genPackageSign(packageParams);
		packageParams.add(new BasicNameValuePair("sign", sign));
		String xmlstring = toXml(packageParams);
		return new String(xmlstring.getBytes(), WxConfig.CHARSET);
	}

	//签名随机串
	public static String genNonceStr() throws UnsupportedEncodingException {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000))
				.getBytes());
	}

	//时间戳。
	public static long genTimeStamp() {
		return System.currentTimeMillis() / 1000;
	}
}
