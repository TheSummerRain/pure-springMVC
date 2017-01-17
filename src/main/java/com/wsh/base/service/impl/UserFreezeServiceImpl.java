package com.wsh.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wsh.base.mapperdao.IUserFreezeMapper;
import com.wsh.base.model.UserFreeze;
import com.wsh.base.service.IUserFreezeService;

//提现服务类
@Service
public class UserFreezeServiceImpl implements IUserFreezeService {

	@Autowired
	private IUserFreezeMapper userFreezeMapper;

	@Override
	public Integer sumWithDarwByUserID(Integer userid) {
		
		Integer sumMoney =  userFreezeMapper.sumWithDarwByUserID(userid);
		return (null == sumMoney ? 0 : sumMoney);
	}

	@Override
	public List<UserFreeze> getAllUserFreezeInfo() {
		// TODO Auto-generated method stub
		return userFreezeMapper.getAllUserFreezeInfo();
	}
	
	
	
	
	
	
}
