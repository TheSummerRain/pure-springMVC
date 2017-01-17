package com.wsh.base.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wsh.base.mapperdao.IBookInfoMapper;
import com.wsh.base.model.BookInfo;
import com.wsh.base.service.IBookService;
import com.wsh.base.util.Pager;

/**
 * 图书服务类
 * @author Wally
 */
@Service
public class BookServiceImpl implements IBookService {
	
	//图书mapper
	@Autowired
	private IBookInfoMapper bookInfoMapper;
	
	
	//分页查询 列表
	@Override
	public Pager selectBooksList(Map<String, Object> map) {
		Pager pager = Pager.getPager(bookInfoMapper.bookInfoAllCount(map),
				(Integer) map.get("pageCount"), (Integer) map.get("pageIndex"));
		if (pager != null) {
			List<BookInfo> books = bookInfoMapper.getBookInfoList(map);
			pager.setBody(books);
		}
		return pager;
	}


	@Override
	public List<BookInfo> getAllListInfo() {
		return bookInfoMapper.getAllListInfo();
	}


	@Override
	public BookInfo getOneBookInfoByID(Integer bookid) {
		
		return bookInfoMapper.getOneBookInfoByID(bookid);
	}


	@Override
	public List<BookInfo> getFristListInfo(int begein, int bookCountNum) {
		return bookInfoMapper.getListBooksInfo(begein,bookCountNum);
	}

	
	
	
	
	
	
	
/**
* by date 2016.4.15 Wally
*********************************************以下为重构区域==任何非重构区域不允许在这里写************************************************
*/	

	/**
	 * 获取某个发布日期 之后 的所有书籍记录
	 */
	@Override
	public List<BookInfo> getBookListByAfterPubDate(Date pubDate) {
		return bookInfoMapper.getBookListByAfterPubDate(pubDate);
	}


	@Override
	public List<BookInfo> getShowBookList(List listString) {
		// TODO Auto-generated method stub
		return bookInfoMapper.getShowBookList(listString);
	}




	@Override
	public List<BookInfo> getTopThreeBooks_arr(List list) {
		// TODO Auto-generated method stub
		return bookInfoMapper.getTopThreeBooks_arr(list);
	}
	
	
	

/**
* *************************************************************************************************************************
*/
			
	
	
}
