package com.wsh.base.mapperdao;

import org.apache.ibatis.annotations.Param;

import com.wsh.base.model.PopConsume;

/**
 * 
 * @package : com.wsh.base.mapperdao
 * @ClassName: IPopConsumeMapper
 * @Description: 本表：只记录 推广剩余维币 的消费记录。比如：提现操作，倘若使用的是：【推广收入的维币  】提现后需要在这里记录。
 * @author Wally
 * @date 2016年2月1日 上午11:59:52
 * @modify Wally
 * @modifyDate 2016年2月1日 上午11:59:52
 */
public interface IPopConsumeMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(PopConsume record);
    int insertSelective(PopConsume record);
    PopConsume selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(PopConsume record);
    int updateByPrimaryKey(PopConsume record);
    
    //查询用户的 推广维币 总消费。消费类型包括：提现，赞赏等等。
	Integer toSearchMyPopWb(@Param("userid")Integer userid);
    
}