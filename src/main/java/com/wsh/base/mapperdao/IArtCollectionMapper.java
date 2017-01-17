package com.wsh.base.mapperdao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wsh.base.model.ArtCollection;

public interface IArtCollectionMapper {
    int deleteByPrimaryKey(Integer cid);

    int insert(ArtCollection record);

    int insertSelective(ArtCollection record);

    ArtCollection selectByPrimaryKey(Integer cid);

    int updateByPrimaryKeySelective(ArtCollection record);

    int updateByPrimaryKey(ArtCollection record);
    
    //查找用戶的收藏信息。
    List<ArtCollection> findUserConlection(@Param("userid") Integer userid);
    
}