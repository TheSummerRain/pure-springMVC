package com.wsh.base.mapperdao;

import com.wsh.base.model.NewUserInfo;

public interface NewUserInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NewUserInfo record);

    int insertSelective(NewUserInfo record);

    NewUserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NewUserInfo record);

    int updateByPrimaryKeyWithBLOBs(NewUserInfo record);

    int updateByPrimaryKey(NewUserInfo record);
}