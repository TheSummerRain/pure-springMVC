package com.wsh.base.service;

import java.util.List;

import com.wsh.base.model.UserFreeze;

public interface IUserFreezeService {

	/**
	 * 
	 * @author Wally
	 * @time 2016年5月30日下午5:31:15
	 * @Description 查询用户的总提现记录
	 * @param
	 * @return Integer
	 */
	Integer sumWithDarwByUserID(Integer userid);

	List<UserFreeze> getAllUserFreezeInfo();

}
