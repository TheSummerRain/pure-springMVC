package com.wsh.base.service;

import java.util.List;

import com.wsh.base.model.UserComment;


public interface IUserCommentService {

	List<UserComment> getUserComments(Integer userid, Integer artid);

	Integer insertComment(UserComment uc);
	
	void deleteComment(Integer cid);

	List<UserComment> getTen_CommnetsList(Integer i, int j, int k);

}
