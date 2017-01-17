package com.wsh.base.mapperdao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.wsh.base.model.ArticleInfo;

public interface IArticleInfoMapper {

	//获取一条记录。
	ArticleInfo getOneInfoByArtId(@Param("artid")Integer artid);
	
//---------------------【书模块 Mapper】------------------------------------------------------

	ArticleInfo getContentInfoByBookID(@Param("bookid")Integer bookid);
	
//--------------------【维模块Mpapper】---------------------------------------------------
	//根据 分类标签获取 列表数据。
	List<ArticleInfo> getWeiListInfoByLabelType(@Param("type") Integer type);
	
	List<ArticleInfo> getWeiListInfoNoPage();
	
	ArticleInfo getWeiInfoByArtId(@Param("artid")Integer artid);

	//校验是否有关联的文章。
	Integer checkHasAritcle(@Param("bookid") Integer bookId);

	@Select("select art_id,art_title,art_type,art_label,art_intro,art_author,art_publishTime,wei_picUrl from article_info where  art_status =1 and art_type=0 order by art_publishTime desc limit #{begein},#{countnum}")
	@ResultMap("BaseResultMap")
	List<ArticleInfo> getWeiListInfoPage(@Param("begein")int begein, @Param("countnum")int countnum);

	@Select("select art_id,art_title,art_type,art_label,art_intro,art_author,art_publishTime,wei_picUrl from article_info where  art_status =1 and art_type=0 and art_label=#{labeltype} order by art_publishTime desc limit #{begein},#{countnum}")
	@ResultMap("BaseResultMap")
	List<ArticleInfo> getWeiByLabelTypePage( @Param("labeltype")Integer labeltype, @Param("begein")int begein, @Param("countnum")int countnum);

	@Select("select * from article_info where  art_status =1 and art_type=1 ")
	@ResultMap("BaseResultMap")
	List<ArticleInfo> getArtList();
	

}
