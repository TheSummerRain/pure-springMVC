package com.wsh.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wsh.base.mapperdao.IArticleInfoMapper;
import com.wsh.base.model.ArticleInfo;
import com.wsh.base.service.IArticleInfoService;

@Service
public class ArticleInfoServiceImpl implements IArticleInfoService{

	@Autowired
	private  IArticleInfoMapper articleInfoMapper;

	/**
	 * 获取内容。
	 */
	@Override
	public ArticleInfo getContentInfoByBookID(Integer bookid) {
		return articleInfoMapper.getContentInfoByBookID(bookid);
	}

	//获取无分页的维数据列表
	@Override
	public List<ArticleInfo> getWeiListInfoNoPage() {
		return articleInfoMapper.getWeiListInfoNoPage();
	}

	//获取一条维信息，根据artid
	@Override
	public ArticleInfo getWeiInfoByArtId(Integer artid) {
		return articleInfoMapper.getWeiInfoByArtId(artid);
	}

	//根据维的分类标签，获取列表数据。
	@Override
	public List<ArticleInfo> getWeiListInfoByLabelType(Integer type) {
		return articleInfoMapper.getWeiListInfoByLabelType(type);
	}

	@Override
	public Integer checkHasAritcle(Integer bookId) {
		// TODO Auto-generated method stub
		return articleInfoMapper.checkHasAritcle(bookId);
	}

	//获取一条记录。
	@Override
	public ArticleInfo getArtInfoByArtid(Integer artid) {
		return articleInfoMapper.getOneInfoByArtId(artid);
	}

	@Override
	public List<ArticleInfo> getWeiListInfoPage(int begein, int countnum) {
		return articleInfoMapper.getWeiListInfoPage(begein,countnum);
	}

	@Override
	public List<ArticleInfo> getWeiByLabelTypePage(Integer labeltype,
			int begein, int countnum) {
		return articleInfoMapper.getWeiByLabelTypePage(labeltype,begein,countnum);
	}

	@Override
	public List<ArticleInfo> getArtList() {
		// TODO Auto-generated method stub
		return articleInfoMapper.getArtList();
	}
	
	
}
