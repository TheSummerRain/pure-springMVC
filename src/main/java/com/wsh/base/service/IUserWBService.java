package com.wsh.base.service;

import java.util.List;

import com.wsh.base.model.WeibiLog;

public interface IUserWBService {

	/**
	 * @Title toSearchMyWb
	 * @author Wally
	 * @time 2016年1月9日下午6:15:31
	 * @Description 查询 我的 维币 剩余 。
	 * @param 用户ID。
	 * @return Integer --剩余维币
	 */
	Integer toSearchMyWb(Integer userid);

	/**
	 * @Title getWBLogInfoByUserId
	 * @author Wally
	 * @time 2016年1月9日下午6:17:43
	 * @Description 获取用户的 维币 流水记录。
	 * @param
	 * @return List<WeibiLog>  某个用户的维币流水 
	 */
	List<WeibiLog> getWBLogInfoByUserId(Integer userid);

	/**
	 * 
	 * @Title select
	 * @author Wally
	 * @time 2016年1月30日下午5:01:49
	 * @Description <!-- 查询某种类型的 在某段时间内 的 总收益 。时间是一个范围值。是过去（天数） -->
	 * @param
	 * @return Integer
	 */
	Integer findAllWbIncomeByUid(Integer userid,Integer chanel,Integer dateNum);

	Integer toSearchMy_PopWb(Integer userid);
	
	

}
