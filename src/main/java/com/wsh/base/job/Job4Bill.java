package com.wsh.base.job;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import com.wsh.base.model.Bill;
import com.wsh.base.model.Count;
import com.wsh.base.service.IPayCoreService;
import com.wsh.base.util.ApplicationContextUtil;
import com.wsh.base.util.dateUtil.DateUtil;
import com.wsh.base.wxpay.HttpUtil;
import com.wsh.base.wxpay.Utils;
import com.wsh.base.wxpay.WxConfig;

public class Job4Bill {

	
	protected IPayCoreService payCoreService;
	
	public void work(){
		payCoreService=(IPayCoreService)ApplicationContextUtil.getApplicationContext().getBean("payCoreService");
		downloadbill();
	}
	
	
	public void downloadbill() {
		// https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_6
		// 商户可以通过该接口下载历史交易清单。比如掉单、系统错误等导致商户侧和微信侧数据不一致，通过对账单核对后可校正支付状态。
		// 注意：
		// 1、微信侧未成功下单的交易不会出现在对账单中。支付成功后撤销的交易会出现在对账单中，跟原支付单订单号一致，bill_type为REVOKED；
		// 2、微信在次日9点启动生成前一天的对账单，建议商户10点后再获取；
		// 3、对账单中涉及金额的字段单位为“元”
		List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
		try {
			parameters.add(new BasicNameValuePair("appid", WxConfig.APPID));
			parameters.add(new BasicNameValuePair("mch_id", WxConfig.MCH_ID));
			parameters.add(new BasicNameValuePair("nonce_str", Utils
					.genNonceStr()));
			parameters.add(new BasicNameValuePair("bill_date", DateUtil
					.getDateBill()));
			parameters.add(new BasicNameValuePair("bill_type", "ALL"));

			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("sign", Utils
					.genPackageSign(parameters)));
			params.addAll(parameters);
			doPost(WxConfig.DOWNLOADBILL, Utils.toXml(params));
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}
	
	
	public void doPost(String uri, String parameters) {

		File file = null;
		 CloseableHttpResponse response = null;
		try {

			 HttpPost httpost = new HttpPost(uri);
			 if (parameters != null) {
			 httpost.setEntity(new StringEntity(parameters, "UTF-8"));
			 }
			 response = HttpUtil.createSSLClientDefault().execute(httpost);
			 InputStream input = response.getEntity().getContent();
			
			 file = File.createTempFile(System.currentTimeMillis() + "",
			 ".txt");
//			 System.out.println(file.getAbsolutePath());
			 FileOutputStream output = new FileOutputStream(file);
			 byte[] b = new byte[1024];
			 int l = 0;
			 while ((l = input.read(b)) != -1) {
			 output.write(b, 0, l);
			 }
			
			 output.flush();
			 output.close();
			 response.close();
//
//			file = new File(
//					"C:/Users/Andy_Liu/AppData/Local/Temp/14534597130777397052421274388468.txt");
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), "UTF-8"));
			String line = reader.readLine();
			// 交易时间,公众账号ID
			boolean flag = false;
			List<Bill> bills = new ArrayList<Bill>();
			while (line != null) {
				line = reader.readLine();
				if (line != null) {
					if (Pattern.compile("[\u4e00-\u9fa5]+").matcher(line)
							.find()) {
						// 总交易单数,总交易额,总退款金额,总代金券或立减优惠退款金额,手续费总金额
						flag = true;
						continue;
					}
					if (flag) {
						System.out.println(new Count(line));
						payCoreService.insertBillCount(new Count(line));
					} else {
						System.out.println(new Bill(line).toString());
						// payCoreService.insertBill(new Bill(line));
						bills.add(new Bill(line));
					}

				}
			}

			if (!bills.isEmpty()) {
				payCoreService.betchInsertBill(bills);
			}

			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (file != null) {
				// file.delete();
			}
		}

	}
}
