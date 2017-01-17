package com.wsh.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wsh.base.mapperdao.IArtCollectionMapper;
import com.wsh.base.model.ArtCollection;
import com.wsh.base.service.IArtCollectionService;

@Service
public class ArtConllectionServiceImpl implements IArtCollectionService {

	@Autowired
	private IArtCollectionMapper artconllectionMapper;
	
	@Override
	public List<ArtCollection> findArtCollecByUserId(Integer userid) {
		return artconllectionMapper.findUserConlection(userid);
	}

	
	
}
