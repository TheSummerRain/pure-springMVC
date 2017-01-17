package com.wsh.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wsh.base.mapperdao.IUserCommentMapper;
import com.wsh.base.model.BookInfo;
import com.wsh.base.model.UserComment;
import com.wsh.base.service.IUserCommentService;

@Service
public class UserCommentServiceImpl implements IUserCommentService {
	
	@Autowired
	private IUserCommentMapper usercommentMapper;
		
	@Override
	public List<UserComment> getUserComments(Integer userid, Integer artid) {
		
		return usercommentMapper.getUserComments(userid,artid);
	}

	@Override
	public Integer insertComment(UserComment uc) {
		Integer cid = usercommentMapper.insertSelective(uc);
		System.out.println("================+"+uc.getCid());
		return uc.getCid();
		
	}

	@Override
	public void deleteComment(Integer cid) {
		usercommentMapper.deleteComment(cid);
	}

	@Override
	public List<UserComment> getTen_CommnetsList(Integer artid,int i, int j) {
		
		return usercommentMapper.getTen_CommnetsList(artid,i,j);
	}

}
