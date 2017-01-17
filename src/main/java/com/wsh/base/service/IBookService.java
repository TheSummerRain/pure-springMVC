package com.wsh.base.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.wsh.base.model.BookInfo;
import com.wsh.base.util.Pager;

public interface IBookService {

	//查询书籍列表-分页-【条件，不需要】
	Pager selectBooksList(Map<String, Object> queryCondition);

	List<BookInfo> getAllListInfo();
	
	BookInfo getOneBookInfoByID(Integer bookid);

	List<BookInfo> getFristListInfo(int i, int bookCountNum);
	
	
/**
* by date 2016.4.15 Wally
*********************************************以下为重构区域==任何非重构区域不允许在这里写************************************************
*/
	//获取某个发布日期【之后】的所有书籍列表
	List<BookInfo> getBookListByAfterPubDate(Date pubDate);
 
	List<BookInfo> getShowBookList(List list);

	List<BookInfo> getTopThreeBooks_arr(List list);
	
	
/**
* *************************************************************************************************************************
*/
	
}
