package com.wsh.base.utilhttp.dataEntity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.wsh.base.wxpay.WxConfig;

/**
 * 
 * @ClassName: PayToCust
 * @Description:  企业付款  到 用户 钱包。  模型数据
 * @author Wally
 * @date 2016年5月23日 下午6:06:40
 * @modify Wally
 * @modifyDate 2016年5月23日 下午6:06:40
 */
//https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=14_2
public class PayToCust extends BaseXmlModel {

		//获取拼装结果。  因为接口不一样，所以这里也需要特别处理。【微信自己的接口文档，字段名都不一致，所以没办法提取】
	public String getPayToCust(PubData pubData) throws UnsupportedEncodingException{
		
		//数据量不大，没必要这么干。[数据量大，添加删除 使用 LinkedList.]
		//List<BasicNameValuePair> linkedParams = new LinkedList<BasicNameValuePair>(); //承载键值对。  //add,delete快速。
	    List<BasicNameValuePair> arrayParams = new ArrayList<BasicNameValuePair>(); //承载键值对。  //get ,set 快速。
	    
	    //基本信息。
		arrayParams.add(new BasicNameValuePair("mch_appid", WxConfig.APPID));
		arrayParams.add(new BasicNameValuePair("mchid", WxConfig.MCH_ID));
		arrayParams.add(new BasicNameValuePair("nonce_str", getNonceStr()));
		
		// partner_trade_no  商户订单号。
			arrayParams.add(new BasicNameValuePair("partner_trade_no",pubData.getOrderNo()));
		// openid  用户openid
			arrayParams.add(new BasicNameValuePair("openid",pubData.getOpenid()));
		//check_name 校验用户姓名选项,死值：   NO_CHECK：不校验真实姓名（我们使用这个）  ； FORCE_CHECK：强校验真实姓名（未实名认证的用户会校验失败，无法转账）；OPTION_CHECK：针对已实名认证的用户才校验真实姓名（未实名认证用户不校验，可以转账成功）
			arrayParams.add(new BasicNameValuePair("check_name","NO_CHECK"));
		// amount  金额。int类型，单位 分。
			arrayParams.add(new BasicNameValuePair("amount",String.valueOf(pubData.getAmount() * 100))); //把元变成分，传输。微信要求按分传送
		// desc  企业付款描述信息
			arrayParams.add(new BasicNameValuePair("desc",pubData.getDescString()));
		// spbill_create_ip  调用接口的机器IP地址。
			arrayParams.add(new BasicNameValuePair("spbill_create_ip",pubData.getIpStr()));
		// sign	  签名生成。
			String sign = getSign(arrayParams);
		
		//组装签名到list.
			arrayParams.add(new BasicNameValuePair("sign", sign));
		//返回 xml组装数据字段。
		return toXml(arrayParams);
		
	}
	
	
	
}
