package com.wsh.base.mapperdao;

import com.wsh.base.model.MyWallet;

public interface MyWalletMapper {
    int deleteByPrimaryKey(Integer walid);

    int insert(MyWallet record);

    int insertSelective(MyWallet record);

    MyWallet selectByPrimaryKey(Integer walid);

    int updateByPrimaryKeySelective(MyWallet record);

    int updateByPrimaryKey(MyWallet record);
}