package com.wsh.base.wxpay;

import java.security.MessageDigest;

public class MD5Util {

	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes(charsetname)));
		} catch (Exception exception) {
		}
		return resultString;
	}

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	public static void main(String[] args) {
		System.out.println(MD5Util.MD5Encode("appid=wxf923d8669d60855f&attach=136_1&body=MemberCharge&device_info=WEB&fee_type=CNY&limit_pay=no_credit&mch_id=1309307501&nonce_str=5b658d2a925565f0755e035597f8d22f&notify_url=http://weshuhui.com/BookClub/wxpay/notifyFromWx&openid=oxoAruMxrr5o_vsQBuourZlUEy7c&out_trade_no=CZ00013620160120150853092570&spbill_create_ip=111.198.239.69&total_fee=1&trade_type=JSAPI&key=LW2015wereadclub2015weshuhui2015","utf-8"));
	}
}
