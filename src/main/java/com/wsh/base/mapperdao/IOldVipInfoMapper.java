package com.wsh.base.mapperdao;

import org.apache.ibatis.annotations.Param;

import com.wsh.base.model.OldVipTmp;

public interface IOldVipInfoMapper {

	//
	OldVipTmp selectByMobile(@Param("mobile")String mobile);
	OldVipTmp selectByPrimaryKey(Integer id);
	 
	 int deleteByPrimaryKey(Integer id);

	    int insert(OldVipTmp record);

	    int insertSelective(OldVipTmp record);

	   

	    int updateByPrimaryKeySelective(OldVipTmp record);

	
}
