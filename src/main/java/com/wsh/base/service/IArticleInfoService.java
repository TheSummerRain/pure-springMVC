package com.wsh.base.service;

import java.util.List;

import com.wsh.base.model.ArticleInfo;

/**
 * 
 * @package : com.wsh.base.service
 * @ClassName: IArticleInfoService
 * @Description: 文章基础信息服务
 * @author Wally
 * @date 2016年1月9日 下午1:31:28
 * @modify Wally
 * @modifyDate 2016年1月9日 下午1:31:28
 */
public interface IArticleInfoService {

	 ArticleInfo getContentInfoByBookID(Integer bookid);
	 ArticleInfo getWeiInfoByArtId(Integer artid);

	 List<ArticleInfo> getWeiListInfoNoPage();
	 
	 List<ArticleInfo> getWeiListInfoPage(int begein,int countnum);
	 
	 /**
	  * 
	  * @Title getWeiListInfoByLabelType
	  * @author Wally
	  * @time 2016年1月16日下午12:25:41
	  * @Description 根据标签分类获取 维列表信息。
	  * @param
	  * @return List<ArticleInfo>
	  */
	 List<ArticleInfo> getWeiListInfoByLabelType(Integer labeltype);
	 
	 List<ArticleInfo> getWeiByLabelTypePage(Integer labeltype,int begein,int countnum);
	 
	 /**
	  * 
	  * @Title checkHasAritcle
	  * @author Wally
	  * @time 2016年1月23日下午12:48:12
	  * @Description 校验你是否有关联文章。
	  * @param
	  * @return Integer
	  */
	 Integer checkHasAritcle(Integer bookId);
	 
	 
	 ArticleInfo getArtInfoByArtid(Integer artid);
	 
	 List<ArticleInfo> getArtList();

}
