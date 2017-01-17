package com.wsh.base.mapperdao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.wsh.base.model.CreditLog;

/**
 * 
 * @package : com.wsh.base.mapperdao
 * @ClassName: IUserCreditMapper
 * @Description: 用户 积分 信息 mapper .积分表--credit_log.table
 * @author Wally
 * @date 2016年1月9日 下午2:46:05
 * @modify Wally
 * @modifyDate 2016年1月9日 下午2:46:05
 */
public interface IUserCreditMapper {

	/**
	 * 
	 * @Title toQueryUserSuCredits
	 * @author Wally
	 * @time 2016年1月9日下午2:49:21
	 * @Description 【查询】  用户  最新 的  【剩余积分】。通过最后一次 操作日期来判断。
	 * @param
	 * @return int
	 */
	@Select("select sp_credit from credit_log where user_id=#{userid} order by createtime desc LIMIT 1")
	Integer toQueryUserSuCredits(@Param("userid")Integer userid);

	/**
	 * 
	 * @Title getUserCreditLogByUserID
	 * @author Wally
	 * @time 2016年1月9日下午2:58:46
	 * @Description 【获取】 某个 用户 的 积分 【列表】详情。
	 * @param
	 * @return List<CreditLog>
	 */
	@Select("select * from credit_log where user_id=#{userid} order by createtime desc ")
	List<CreditLog> getUserCreditLogByUserID(Integer userid);

	/**
	 * 
	 * @Title instCreditLogForUser
	 * @author Wally
	 * @time 2016年1月9日下午3:02:51
	 * @Description 为  【某个用户】【 增加】一条 积分 流水。【赚取，和消费 都是一条记录】只不过 剩余积分加减了而已。
	 * @param
	 * @return int
	 */
	int instCreditLogForUser(CreditLog creditLog);

	
	
	
	
}
