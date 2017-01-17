package com.wsh.base.mapperdao;

import org.apache.ibatis.annotations.Param;

import com.wsh.base.model.TiplistInfo;

public interface ITipListMapper {

	TiplistInfo getTipListByBookID(@Param("bookid")Integer bookid);

}
