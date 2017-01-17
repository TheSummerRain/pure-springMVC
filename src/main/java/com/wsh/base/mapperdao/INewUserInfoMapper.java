package com.wsh.base.mapperdao;

import org.apache.ibatis.annotations.Param;

import com.wsh.base.model.NewUserInfo;

import java.util.List;

/**
 * 
 * @package : com.wsh.base.mapperdao
 * @ClassName: INewUserInfoMapper
 * @Description: 新的用户结构表的 mapper.
 * @author Wally
 * @date 2016年12月12日 下午3:15:08
 * @modify Wally
 * @modifyDate 2016年12月12日 下午3:15:08
 */

public interface INewUserInfoMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(NewUserInfo record);
    int addRecordBatch(List<NewUserInfo> records);

    int insertSelective(NewUserInfo record);

    NewUserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NewUserInfo record);

    int updateByPrimaryKeyWithBLOBs(NewUserInfo record);

    int updateByPrimaryKey(NewUserInfo record);

    
    //授权，根据openid更新数据信息。
	int newUpateUserInfo(NewUserInfo userInfo);
	//根据openid获取用户基础信息。
	NewUserInfo new_GetUserInfoByOpenId(@Param("openId") String openId);
	//第一次插入用户数据。
	Integer createOneUserInfo(NewUserInfo userinfo);
	//根据userid获取用户基础信息。
	NewUserInfo new_GetUserInfoByUserId(@Param("userid") Integer userid);

    NewUserInfo getUserInfoByUnionId(@Param("unionId") String unionId);
}