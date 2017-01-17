package com.wsh.base.mapperdao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.wsh.base.model.UserComment;

public interface IUserCommentMapper {
	
    int insert(UserComment record);

    int insertSelective(UserComment record);

    UserComment selectByPrimaryKey(Integer cid);

    int updateByPrimaryKeySelective(UserComment record);

    int updateByPrimaryKey(UserComment record);
    
    //--------------------------------------------------------------------------------
    @Select("select * from user_comment where userid=#{userid} and artid=#{artid} order by createtime desc")
    @ResultMap("BaseResultMap")
	List<UserComment> getUserComments(@Param("userid")Integer userid, @Param("artid")Integer artid);

    @Delete(" delete from user_comment where cid = #{cid} ")
	void deleteComment(@Param("cid")Integer cid);

    @Select(" select * from user_comment where artid=#{artid} and isNice =1 ORDER BY countpra DESC,createtime DESC LIMIT #{begein},#{count} ")
    @ResultMap("BaseResultMap")
	List<UserComment> getTen_CommnetsList(@Param("artid")Integer artid,@Param("begein")int begein, @Param("count")int count);
    
    
}