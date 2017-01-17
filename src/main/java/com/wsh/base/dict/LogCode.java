package com.wsh.base.dict;

/**
 * 
 * @package : com.wsh.base.dict
 * @ClassName: ErrCode
 * @Description: 错误编码
 * @author Wally
 * @date 2016年1月11日 下午3:36:33
 * @modify Wally
 * @modifyDate 2016年1月11日 下午3:36:33
 */
public enum LogCode {
	
	CZ_OK("充值OK",0001),
	CZ_TIPS("根据银行卡不同，充值到账时间将在2小时内完成",0002),
	CZ_FAILE("充值失败",0003),
	CZ_FAILETIPS("充值失败，请联系管理员",0004),
	TIME_OUT("请求超时", 1000),       //超時
	FORMAT_ERROR("格式错误", 10001), //格式错误
	OTHERERROR("其他错误",9999);
	
	
	
	
	
	
	
	
	
	
	private String msg;
	private int code;
	private LogCode(String msg, int code) {
		this.msg = msg;
		this.code = code;
	}
	public int getCode() {
		return code;
	}
	public  String getMsg() {
		return this.msg;
	}
	
}
