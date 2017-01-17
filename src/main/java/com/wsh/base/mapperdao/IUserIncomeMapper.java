package com.wsh.base.mapperdao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.wsh.base.model.UserIncome;

public interface IUserIncomeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserIncome record);

    int insertSelective(UserIncome record);

    UserIncome selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserIncome record);

    int updateByPrimaryKey(UserIncome record);

	void insertManagerCost(List<UserIncome> list);

	
	@Select(" select * from user_income where toUserID = #{userId}")
	@ResultMap("BaseResultMap")
	List<UserIncome> getListIncomeByUserID(@Param("userId")Integer userId);

	@Select(" select SUM(income) from user_income where toUserID = #{userId} ")
	Integer getAllIncome_byToUserID(@Param("userId")Integer userid);
	
	@Select(" select * from user_income ")
	@ResultMap("BaseResultMap")
	List<UserIncome> getAllUserIncomeInfo();
	
}