package com.wsh.base.service;

import java.util.List;

import com.wsh.base.model.UserIncome;

public interface IUserIncomeService {

	List<UserIncome> getListIncomeByUserID(Integer userId);

	Integer getAllIncome_byToUserID(Integer userid);

	List<UserIncome> getAllUserIncomeInfo();

}
