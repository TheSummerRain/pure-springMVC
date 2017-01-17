package com.wsh.base.service;

import com.wsh.base.model.TiplistInfo;

/**
 * 推荐列表
 * @author Wally
 *
 */
public interface ITipListInfoService {

	TiplistInfo getTipListByBookID(Integer bookid);

	
	
}
