package com.wsh.base.service;

import java.util.List;

import com.wsh.base.model.CreditLog;

/**
 * 
 * @package : com.wsh.base.service
 * @ClassName: IUserCreditService
 * @Description: 用户积分服务 接口
 * @author Wally
 * @date 2016年1月9日 下午1:34:51
 * @modify Wally
 * @modifyDate 2016年1月9日 下午1:34:51
 */
public interface IUserCreditService {

	/**
	 * 
	 * @Title toQueryUserSuCredits
	 * @author Wally
	 * @time 2016年1月9日下午2:43:02
	 * @Description 查询用户最新的剩余积分 数据。
	 * @param
	 * @return int
	 */
	Integer toQueryUserSuCredits(Integer userid);

	/**
	 * 
	 * @Title getUserCreditLogByUserID
	 * @author Wally
	 * @time 2016年1月9日下午2:57:34
	 * @Description 获取用户 积分列表。
	 * @param
	 * @return List<CreditLog> --注意，创建日期要倒序排列。
	 */
	List<CreditLog> getUserCreditLogByUserID(Integer userid);
	
	/**
	 * 
	 * @Title instCreditLogForUser
	 * @author Wally
	 * @time 2016年1月9日下午2:59:58
	 * @Description 为某个用户 插入 一条 积分 获得 或 消费 流水。
	 * @param 积分对象实体
	 * @return int - 生成的流水ID。
	 */
	int instCreditLogForUser(CreditLog creditLog);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
