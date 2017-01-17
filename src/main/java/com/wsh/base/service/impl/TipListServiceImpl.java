package com.wsh.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wsh.base.mapperdao.ITipListMapper;
import com.wsh.base.model.TiplistInfo;
import com.wsh.base.service.ITipListInfoService;

@Service
public class TipListServiceImpl implements ITipListInfoService{

	@Autowired
	private ITipListMapper tipListMapper;
	
	
	@Override
	public TiplistInfo getTipListByBookID(Integer bookid) {
		
		return tipListMapper.getTipListByBookID(bookid);
	}

	
	
	
	
}
