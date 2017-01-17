package com.wsh.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wsh.base.mapperdao.IUserCreditMapper;
import com.wsh.base.model.CreditLog;
import com.wsh.base.service.IUserCreditService;

/**
 * 
 * @package : com.wsh.base.service.impl
 * @ClassName: UserCreditServiceImpl
 * @Description:  用户 积分 服务类
 * @author Wally
 * @date 2016年1月9日 下午1:34:22
 * @modify Wally
 * @modifyDate 2016年1月9日 下午1:34:22
 */
@Service
public class UserCreditServiceImpl implements IUserCreditService {

	@Autowired
	private IUserCreditMapper userCreditMapper;
	
	
	@Override
	public Integer toQueryUserSuCredits(Integer userid) {
		Integer spcredits = userCreditMapper.toQueryUserSuCredits(userid);
		return (null == spcredits ? 0:spcredits);
	}


	@Override
	public List<CreditLog> getUserCreditLogByUserID(Integer userid) {
		return userCreditMapper.getUserCreditLogByUserID(userid);
	}


	@Override
	public int instCreditLogForUser(CreditLog creditLog) {
		int logid= userCreditMapper.instCreditLogForUser(creditLog);
		return creditLog.getCreditId();
	}

	
	
	
	
}
