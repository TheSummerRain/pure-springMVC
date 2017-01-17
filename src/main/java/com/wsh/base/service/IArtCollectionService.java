package com.wsh.base.service;

import java.util.List;

import com.wsh.base.model.ArtCollection;

public interface IArtCollectionService {

	
	List<ArtCollection> findArtCollecByUserId(Integer userid);
	
	
}
