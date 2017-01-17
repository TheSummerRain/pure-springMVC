package com.wsh.base.mapperdao;

import org.apache.ibatis.annotations.Param;

import com.wsh.base.model.PromoterInfo;

/**
 * 推广员信息表
 * @author Wally
 *
 */
public interface IPromoterInfoMapper {

	//获取一条 推广信息。根据userid
	PromoterInfo getPromoterInfoByUserID(@Param("userid")int userid);

	
	
	
}
