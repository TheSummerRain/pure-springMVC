package com.wsh.base.mapperdao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.wsh.base.model.BookInfo;

public interface IBookInfoMapper {

	//查询总记录数
	int bookInfoAllCount(Map<String, Object> map);

	//列表查询。
	List<BookInfo> getBookInfoList(Map<String, Object> map);

	List<BookInfo> getAllListInfo();
	
	

	BookInfo getOneBookInfoByID(@Param("bookId")Integer bookId);
	
	@Select("select * from book_info where status=1 order by book_pubdate desc LIMIT #{begein},#{bookCountNum} ")
	@ResultMap("BaseResultMap")
	List<BookInfo> getListBooksInfo(@Param("begein")int begein,@Param("bookCountNum")int bookCountNum);

	
	
	
	
	
/**
 * 	********************************************************重构区域*****************************************************
 */
	
	
	/**
	 * 
	 * @author Wally
	 * @time 2016年4月15日下午5:41:15
	 * @Description 获取 某个发布 日期 之后 的所有书籍
	 * @param
	 * @return List<BookInfo>
	 */
	@Select("select * from book_info where book_pubdate >= #{pubDate}")
	@ResultMap("BaseResultMap")
	List<BookInfo> getBookListByAfterPubDate(@Param("pubDate")Date pubDate);

	/**
	 * 
	 * @author Wally
	 * @time 2016年4月20日下午5:11:16
	 * @Description 获取所有书籍数据
	 * @param
	 * @return List<BookInfo>
	 */
	List<BookInfo> getShowBookList(List list);

	
	List<BookInfo> getTopThreeBooks_arr(List list);
	
/**
* 	********************************************************重构区域*****************************************************
*/
		
	
	
	
	
	
	
	
}
