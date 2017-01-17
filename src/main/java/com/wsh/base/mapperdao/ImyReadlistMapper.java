package com.wsh.base.mapperdao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.wsh.base.model.MyReadlist;

public interface ImyReadlistMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MyReadlist record);

    int insertSelective(MyReadlist record);

    MyReadlist selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MyReadlist record);

    int updateByPrimaryKey(MyReadlist record);

    
    @Select("SELECT * from my_readlist where userid=#{userid} order by createtime desc limit 1 ")
	MyReadlist checkHasUserInfo(@Param("userid") Integer userid);

    @Update("update  my_readlist set wishes = 1 where userid=#{userid} ")
	void changeStatus(@Param("userid") Integer userid);
}