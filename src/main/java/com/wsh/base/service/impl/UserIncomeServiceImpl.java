package com.wsh.base.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wsh.base.mapperdao.IUserIncomeMapper;
import com.wsh.base.model.UserIncome;
import com.wsh.base.service.IUserIncomeService;

@Service
public class UserIncomeServiceImpl implements IUserIncomeService{

	@Autowired
	private IUserIncomeMapper userIncomeMapper;

	@Override
	public List<UserIncome> getListIncomeByUserID(Integer userId) {
		
		return userIncomeMapper.getListIncomeByUserID(userId);
	}

	@Override
	public Integer getAllIncome_byToUserID(Integer userid) {

		Integer allincome =  userIncomeMapper.getAllIncome_byToUserID(userid);
		
		return (null == allincome? 0:allincome);
	}

	@Override
	public List<UserIncome> getAllUserIncomeInfo() {
		return userIncomeMapper.getAllUserIncomeInfo();
	}
	

	
	
	
	
	
	
}
