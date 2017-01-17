package com.wsh.base.mapperdao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wsh.base.model.Bill;
import com.wsh.base.model.Count;
import com.wsh.base.model.PayLog;

/**
 * 
 * @package : com.wsh.base.mapperdao
 * @ClassName: IPayLogInfoMapper
 * @Description: 支付 流水日志 mapper.  充值流水表。
 * @author Wally
 * @date 2016年1月8日 下午6:30:39
 * @modify Wally
 * @modifyDate 2016年1月8日 下午6:30:39
 */
public interface IUserPayLogMapper {

	/**
	 * 
	 * @Title payForMySelf
	 * @author Wally
	 * @time 2016年1月11日上午11:48:03
	 * @Description 给用户 自己充值。
	 * 插入流水记录
	 * @return Integer 返回插入数据库的 表 自增ID。
	 */
		Integer payForMySelf(PayLog payLogInfo) throws Exception;

	/**
	 * 
	 * @Title updateOrderStatusForUser
	 * @author Wally
	 * @time 2016年1月11日下午1:37:17
	 * @Description 更新状态字段，根据 用户ID，和订单No。
	 * 例： update table set status=[tostatus],transaction_id=[transaction_id] where userid=[userid] and orderno=[orderno] and status=[fromstatus]
	 * @param userid 用户ID。
	 * @param orderno 订单No。
	 * @param transaction_id 微信支付订单号
	 * @param fromstatus 从某种状态。
	 * @param tostatus 更新为这种状态。
	 * @return void
	 */
	void updateOrderForUser(@Param("userId")Integer userid,@Param("orderNo")String orderNo,@Param("transaction_id") String transaction_id,@Param("fromstatus") int fromstatus,@Param("tostatus") int tostatus);
	
	int insertBill(Bill bill);
	int insertBillCount(Count count);
	public int betchInsertBill(List<Bill> bills);
}
