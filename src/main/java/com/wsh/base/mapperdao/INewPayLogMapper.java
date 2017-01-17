package com.wsh.base.mapperdao;

import com.wsh.base.model.NewPayLog;

public interface INewPayLogMapper {
    int deleteByPrimaryKey(Integer payid);

    int insert(NewPayLog record);

    int insertSelective(NewPayLog record);

    NewPayLog selectByPrimaryKey(Integer payid);

    int updateByPrimaryKeySelective(NewPayLog record);

    int updateByPrimaryKey(NewPayLog record);

	void updatePayLogStatus(NewPayLog pLog);
}