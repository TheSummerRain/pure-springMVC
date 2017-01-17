package com.wsh.base.model;

import java.io.Serializable;

public class Count implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 总交易单数,总交易额,总退款金额,总企业红包退款金额,手续费总金额
	private String transactions;
	private String total;
	private String refundTotal;
	private String luckyMoneyTotal;
	private String poundageTotal;

	public String getTransactions() {
		return transactions;
	}

	public String getTotal() {
		return total;
	}

	public String getRefundTotal() {
		return refundTotal;
	}

	public String getLuckyMoneyTotal() {
		return luckyMoneyTotal;
	}

	public String getPoundageTotal() {
		return poundageTotal;
	}

	public Count(String data) {
		super();
		if (data == null) {
			return;
		}
		String[] str = data.split(",");
		if (str.length != 5) {
			return;
		}
		this.transactions =replace(str[0]);
		this.total =replace( str[1]);
		this.refundTotal =replace( str[2]);
		this.luckyMoneyTotal = replace(str[3]);
		this.poundageTotal = replace(str[4]);
	}

	public String replace(String str) {
		if (str == null) {
			return null;
		}
		return str.replace("`", "");
	}
	
	
	@Override
	public String toString() {
		return "Count [transactions=" + transactions + ", total=" + total
				+ ", refundTotal=" + refundTotal + ", luckyMoneyTotal="
				+ luckyMoneyTotal + ", poundageTotal=" + poundageTotal + "]";
	}

}
