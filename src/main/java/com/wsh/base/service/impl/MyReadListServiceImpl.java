package com.wsh.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wsh.base.mapperdao.ImyReadlistMapper;
import com.wsh.base.model.MyReadlist;
import com.wsh.base.service.ImyReadListService;

@Service
public class MyReadListServiceImpl implements ImyReadListService{

	@Autowired
	private ImyReadlistMapper myreadlistMapper;
	
	@Override
	public void insertOneInfo(MyReadlist myReadlist) {
		myreadlistMapper.insertSelective(myReadlist);
	}

	@Override
	public boolean checkHasUserInfo(Integer userid) {
		MyReadlist hasit = myreadlistMapper.checkHasUserInfo(userid);
		
		if(null == hasit){
			return false;
		}else {
			return true;
		}
	}
	
	@Override
	public MyReadlist getMyReadlistByUserId(Integer userid) {
		return myreadlistMapper.checkHasUserInfo(userid);
	}

	@Override
	public void changeStatus(Integer valueOf) {
		myreadlistMapper.changeStatus(valueOf);
	}

}
