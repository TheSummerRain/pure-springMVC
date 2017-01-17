package com.wsh.base.mapperdao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.wsh.base.model.UserFreeze;

public interface IUserFreezeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserFreeze record);

    int insertSelective(UserFreeze record);

    UserFreeze selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserFreeze record);

    int updateByPrimaryKey(UserFreeze record);

    
    /**
     * 
     * @author Wally
     * @time 2016年5月30日下午5:35:17
     * @Description 查询用户的 提现总额
     * @param
     * @return Integer
     */
    @Select(" select SUM(amount) from user_freeze where userid=#{userid} ")
	Integer sumWithDarwByUserID(@Param("userid")Integer userid);

    @Select(" select * from user_freeze ")
    @ResultMap("BaseResultMap")
	List<UserFreeze> getAllUserFreezeInfo();
	
	
}