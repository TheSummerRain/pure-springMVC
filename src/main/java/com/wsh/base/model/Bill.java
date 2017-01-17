package com.wsh.base.model;

import java.io.Serializable;

public class Bill implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 交易时间,公众账号ID,商户号,子商户号,设备号,微信订单号,商户订单号,用户标识,交易类型,交易状态,付款银行,货币种类,总金额,
	// 企业红包金额,微信退款单号,商户退款单号,退款金额,企业红包退款金额,退款类型,退款状态,商品名称,商户数据包,手续费,费率
	private String id;
	private String date;
	private String appId;
	private String mchId;
	private String subMchId;
	private String device;
	private String transactionId;
	private String outTradeNo;
	private String openId;
	private String tradeType;
	private String tradeStatus;
	private String bank;
	private String feeType;
	private String totalFee;
	private String luckyMoney;
	private String refundNo;
	private String outRefundNo;
	private String refundFee;
	private String refundLuckyMoney;
	private String refundType;
	private String refundStatus;
	private String body;
	private String attach;
	private String poundage;
	private String rate;

	public Bill(String data) {
		super();
		if (data == null) {
			return;
		}
		String[] str = data.split(",");
		if (data.split(",").length != 24) {
			return;
		}
		this.date = replace(str[0]);
		this.appId = replace(str[1]);
		this.mchId = replace(str[2]);
		this.subMchId = replace(str[3]);
		this.device = replace(str[4]);
		this.transactionId =replace( str[5]);
		this.outTradeNo = replace(str[6]);
		this.openId = replace(str[7]);
		this.tradeType = replace(str[8]);
		this.tradeStatus = replace(str[9]);
		this.bank = replace(str[10]);
		this.feeType = replace(str[11]);
		this.totalFee = replace(str[12]);
		this.luckyMoney = replace(str[13]);
		this.refundNo = replace(str[14]);
		this.outRefundNo = replace(str[15]);
		this.refundFee = replace(str[16]);
		this.refundLuckyMoney = replace(str[17]);
		this.refundType = replace(str[18]);
		this.refundStatus = replace(str[19]);
		this.body = replace(str[20]);
		this.attach = replace(str[21]);
		this.poundage = replace(str[22]);
		this.rate = replace(str[23]);
	}

	public String replace(String str) {
		if (str == null) {
			return null;
		}
		return str.replace("`", "");
	}
	
	public String getDate() {
		return date;
	}

	public String getAppId() {
		return appId;
	}

	public String getMchId() {
		return mchId;
	}

	public String getSubMchId() {
		return subMchId;
	}

	public String getDevice() {
		return device;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public String getOpenId() {
		return openId;
	}

	public String getTradeType() {
		return tradeType;
	}

	public String getTradeStatus() {
		return tradeStatus;
	}

	public String getBank() {
		return bank;
	}

	public String getFeeType() {
		return feeType;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public String getLuckyMoney() {
		return luckyMoney;
	}

	public String getRefundNo() {
		return refundNo;
	}

	public String getOutRefundNo() {
		return outRefundNo;
	}

	public String getRefundFee() {
		return refundFee;
	}

	public String getRefundLuckyMoney() {
		return refundLuckyMoney;
	}

	public String getRefundType() {
		return refundType;
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	public String getBody() {
		return body;
	}

	public String getAttach() {
		return attach;
	}

	public String getPoundage() {
		return poundage;
	}

	public String getRate() {
		return rate;
	}

	
	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Bill [date=" + date + ", appId=" + appId + ", mchId=" + mchId
				+ ", subMchId=" + subMchId + ", device=" + device
				+ ", transactionId=" + transactionId + ", outTradeNo="
				+ outTradeNo + ", openId=" + openId + ", tradeType="
				+ tradeType + ", tradeStatus=" + tradeStatus + ", bank=" + bank
				+ ", feeType=" + feeType + ", totalFee=" + totalFee
				+ ", luckyMoney=" + luckyMoney + ", refundNo=" + refundNo
				+ ", outRefundNo=" + outRefundNo + ", refundFee=" + refundFee
				+ ", refundLuckyMoney=" + refundLuckyMoney + ", refundType="
				+ refundType + ", refundStatus=" + refundStatus + ", body="
				+ body + ", attach=" + attach + ", poundage=" + poundage
				+ ", rate=" + rate + "]";
	}

}
