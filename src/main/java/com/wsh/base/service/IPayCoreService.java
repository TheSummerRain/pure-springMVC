package com.wsh.base.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.wsh.base.model.Bill;
import com.wsh.base.model.Count;
import com.wsh.base.utilhttp.dataEntity.PubData;

/**
 * 
 * @package : com.wsh.base.service
 * @ClassName: IPayCoreService
 * @Description: 核心 支付 服务接口。所有维币，钱的处理，都在这里。这可是系统最NB的接口了。
 * @author Wally
 * @date 2016年1月8日 下午5:51:27
 * @modify Wally
 * @modifyDate 2016年1月8日 下午5:51:27
 */

public interface IPayCoreService {

	/**
	 * 
	 * @Title openVIPForUser
	 * @author Wally
	 * @param payType 支付方式 
	 * @param payYear 开通年数，决定会员到期日
	 * @param payMoney 支付了多少钱
	 * @param mobile 
	 * @time 2016年1月8日下午6:06:04
	 * @Description 给用户UserID,开启 会员权限。使用    【剩余维币的方式】      开通。
	 * @param
	 * @return String
	 * @throws Exception 
	 */
	String openVIPForUserByWB(Integer userid, Integer payMoney,
			Integer payYear, String payType, String mobile) throws Exception;

	
	/**
	 * 
	 * @Title openVIPForUser
	 * @author Wally
	 * @param payType 支付方式 
	 * @param payYear 开通年数，决定会员到期日
	 * @param payMoney 支付了多少钱
	 * @time 2016年1月8日下午6:06:04
	 * @Description 给用户UserID,开启 会员权限。使用    【微信渠道】      开通。
	 * @param
	 * @return String
	 * @throws Exception 
	 */
	String openVIPForUserByWX(Integer userid, Integer payMoney,
			Integer payYear, String payType, String orderno,
			String transaction_id, Integer status) throws Exception;

	//-----------------------------充值 begain-------------------------------------------
	/**
	 * 1、调用充值
	 * @author Wally
	 * @time 2016年1月11日下午2:19:58
	 * @Description 开启微信 充值 服务。
	 * @param
	 * @return Map<String,Object>
	 */
	Map<String, Object> startPaySelf(Integer payMoney, int userid) throws Exception;
	public Map<String,String> order(Integer payMoney, int userid, String payOpenVIP);
		
	/**
	 * 2、微信服务器 成功返回响应后 执行。
	 * @author Wally
	 * @time 2016年1月11日下午1:28:42
	 * @Description 当微信服务器返回 充值结果后，调用这个方法，更新交易流水状态。微信支付交易流水号。
	 * @param 
	 * @return String
	 * @throws Exception 
	 */
	String detailWeCatReturn(Integer userid, String orderNo, String retuncode,
			String returndec, String transaction_id, Date paydate,
			Integer payMoney) throws Exception;
	/**
	 * 3、根据响应结果，执行响应的操作。--这里只需要更新维币数量。
	 * @author Wally
	 * @time 2016年1月11日下午2:03:30
	 * @Description 插入维币 流水信息。
	 * @param
	 * @return Integer
	 */
	Integer insertWBInfoByParam(Integer userid, Date paydate, Integer payMoney,
			String businessType, String orderNo) throws Exception;
	
	//------------------------------充值  end---------------------------------------------------------------
	
	
	/**
	 * 
	 * @author Wally
	 * @time 2016年1月10日下午10:30:53
	 * @Description 提现操作
	 * @param 
	 * @return String -跳转界面
	 */
	String detailTiXian(Integer tixianMoney, Integer userid);


	/**
	 * 下载账单
	 * @param bill
	 * @return
	 */
	int insertBill(Bill bill);
	int insertBillCount(Count count);
	int betchInsertBill(List<Bill> bills);


	void toTakeCashFromPop_SpWb(int intValue, Integer userId);


	/**
	 * 
	 * @author Wally
	 * @time 2016年5月5日下午2:31:26
	 * @Description 开通代理
	 * @param userid 用户id。
	 * @param money 充值金额
	 * year 开通年限。
	 * spWeibi 剩余维币 --无用。
	 * orderNo 本地订单号
	 * transid 微信订单号。
	 * agentPre 上级代理商 ID。要给这个ID返佣金
	 * 
	 * @return void
	 */
	void openAgentForUserByWX(int userId, int money, int year, String spWeibi,
			String orderNo, String transid, int status,int agentPre);


	/**
	 * 
	 * @Title payToCustomer
	 * @author Wally
	 * @time 2016年5月23日下午6:21:55
	 * @Description 企业支付到用户。
	 * @param
	 * @return void
	 */
	Map<String, String> payToCustomer(PubData pubData);



	public void earn(final Integer userId);


	String new_openVIP(Integer userid, Integer payMoney, Integer payYear,
			String payType, String orderno, String transaction_id,
			Integer status) throws Exception;


	void ceshi();


	

}
