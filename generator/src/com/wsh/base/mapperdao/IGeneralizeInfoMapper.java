package com.wsh.base.mapperdao;

import com.wsh.base.model.GeneralizeInfo;

public interface GeneralizeInfoMapper {
    int deleteByPrimaryKey(Integer userid);

    int insert(GeneralizeInfo record);

    int insertSelective(GeneralizeInfo record);

    GeneralizeInfo selectByPrimaryKey(Integer userid);

    int updateByPrimaryKeySelective(GeneralizeInfo record);

    int updateByPrimaryKey(GeneralizeInfo record);
}