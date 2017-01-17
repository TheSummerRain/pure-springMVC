package com.wsh.base.service;

import com.wsh.base.model.MyReadlist;

public interface ImyReadListService {

	/**
	 * 生成一条记录。
	 */
	public void insertOneInfo(MyReadlist MyReadlist);
	
	//判断是否已经有记录
	public boolean checkHasUserInfo(Integer userid);

	MyReadlist getMyReadlistByUserId(Integer userid);

	public void changeStatus(Integer valueOf);
	
}
