package com.wsh.base.mapperdao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.wsh.base.model.WeibiLog;

/**
 * 
 * @package : com.wsh.base.mapperdao
 * @ClassName: IWBInfoMapper
 * @Description: 维币 信息  mapper    --- 维币信息表
 * @author Wally
 * @date 2016年1月9日 下午2:44:39
 * @modify Wally
 * @modifyDate 2016年1月9日 下午2:44:39
 */
public interface IWBInfoMapper {

	void minusWBFromUserID(@Param("userid")Integer userid, @Param("payMoney")Integer payMoney) throws Exception; //update

	/**
	 * @author Wally
	 * @time 2016年1月9日下午6:08:03
	 * @Description 查询我的维币 余额。
	 * @param
	 * @return Integer
	 */
	Integer toSearchMyWb(@Param("userid")Integer userid);

	/**
	 * 
	 * @Title getWBLogInfoByUserId
	 * @author Wally
	 * @time 2016年1月9日下午6:19:06
	 * @Description 获取用户的维币 流水日志。
	 * @param
	 * @return List<WeibiLog> 
	 */
	/*@Select("select * from weibi_log where user_id=#{userid} order by createtime desc")*/ // 这种方式必须要于数据库字段字段完全对应。
	List<WeibiLog> getWBLogInfoByUserId(@Param("userid")Integer userid);
	
	/**
	 * 
	 * @author Wally
	 * @time 2016年1月10日上午12:24:04
	 * @Description 增加/减少维币数据。
	 * @param
	 * @return int
	 */
	int instWbLogForUser(WeibiLog wblog);

	//查询推广 的维币信息，chanel 请出传1.如果dateNum=60,就是60天内收入。如果为null,则查询所有推广收入
	Integer findAllPopWbByidAndTime(@Param("userid")Integer userid, @Param("chanel")Integer chanel,
			@Param("dateNum") Integer dateNum); 

	
	
	

}
