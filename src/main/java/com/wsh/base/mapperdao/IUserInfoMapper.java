package com.wsh.base.mapperdao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.wsh.base.model.NewUserInfo;
import com.wsh.base.model.UserInfo;

public interface IUserInfoMapper {

	int deleteByPrimaryKey(Integer userId);

	int insert(UserInfo record);

	UserInfo selectByPrimaryKey(@Param("userId") Integer userId);

	int updateByPrimaryKeySelective(UserInfo record);

	int updateByPrimaryKey(UserInfo record);

	int insertSelective(UserInfo userInfo);

	Integer isExists(@Param("openId")String openId);

	UserInfo getUserInfoByOpenId(@Param("openId")String openId);
	int upateUserInfo(UserInfo userInfo);

	/**
	 * 
	 * @Title updateUserVIPInfoByUserID
	 * @author Wally
	 * @param mobile
	 *            就是要更新vip字段的值。
	 * @time 2016年1月8日下午6:53:58
	 * @Description 更新用户的VIP信息。
	 * @param
	 * @return void
	 */
	void updateUserVIPInfoByUserID(@Param("userid") Integer userid,
			@Param("vipID") String vipID,
			@Param("vipStartDate") Date vipStartDate,
			@Param("vipEndDate") Date vipEndDate);

	
	/**
	 * @Title updateMobileInfoForUser
	 * @author Wally
	 * @time 2016年1月13日上午11:41:39
	 * @Description TODO
	 * @param
	 * @return void
	 */
	void updateMobileInfoForUser(@Param("userid")int userid, @Param("mobile")String mobile);

	/**
	 * @Title updateBankInfoForUser
	 * @author Wally
	 * @time 2016年1月13日下午4:28:13
	 * @Description 更新用户银行卡号 
	 * @param name -这里指的是 user_name字段。
	 * @return void
	 */
	void updateBankInfoForUser(@Param("userid") int userid, @Param("bankNo")String bankNo, @Param("bankType")String bankType,
			@Param("username") String username ,@Param("subBank") String subBank);

	/**
	 * 
	 * @Title updateAcceptAddrForUser
	 * @author Wally
	 * @time 2016年1月13日下午4:29:13
	 * @Description  更新用户 收货信息。
	 * @param
	 * @return void
	 */
	void updateAcceptAddrForUser(@Param("userid")int userid,@Param("acceptName") String acceptName, @Param("preaddr")String preaddr,
			@Param("addr")String addr, @Param("postcode")String postcode);

	/**
	 * 
	 * @Title toUpdateBithday
	 * @author Wally
	 * @time 2016年1月16日下午2:33:18
	 * @Description 更新用户生日。
	 * @param
	 * @return void
	 */
	void toUpdateBithday(@Param("userId")Integer userId, @Param("date")String date);
	
	
	String selectChild(@Param("userid") int userid,@Param("level")int level);
	int filtersMembers(@Param("userid") int userid,@Param("level")int level);
	List<UserInfo> selectFirst(Integer userid);

	int hasBindMobileExt(@Param("mobile") String mobile);
	
	UserInfo checkVip(String openId);
	
	int clearRelation(@Param("userId")Integer userId);

	
	@Select(" select nickname,vip_id from user_info where pId=#{userId} and nickname is not null")
	@ResultMap("BaseResultMap")
	List<UserInfo> friendPage_A_list(@Param("userId") Integer userId);

	@Select(" select COUNT(1) from user_info where pId=#{userId} and nickname is not null")
	int friendPage_A_allCount(@Param("userId") Integer userId);

	@Select(" select COUNT(1) from user_info where pId=#{userId} and vip_id is not null ")
	int friendPage_A_memberCount(@Param("userId") Integer userId);

	@Select(" select * from user_info where user_id=#{userId}")
	@ResultMap("BaseResultMap")
	UserInfo getNickNameByUserID(@Param("userId") Integer userId);
	
	//判断是某个id是否是会员，是否存在
	@Select("select count(1) from userinfo where id=#{userId} and vipLv>0")
	int checkIsLiveByUserid(@Param("userId") Integer userId);
	
	@Update("update userinfo set mobile=#{mobile},pId=#{yaoqm} where user_id=#{userid} ")
	void updateYaoQmMobileById(@Param("userid")int userid, @Param("mobile")String mobile, @Param("yaoqm")Integer yaoqm);
	
	
	//-----------------三级分销专用---【需要注意in后面最多不能超过1000个】-等待处理--------------------------------------------------------------
	@Select(" select COUNT(1) from user_info where pId=#{userId} and nickname is not null")
	int getAll_A_Count(@Param("userId") Integer userId);
	@Select(" select COUNT(1) from user_info where vip_id is not null and  pId=#{userId}")
	int getAll_A_VipCount(@Param("userId") Integer userId);

	@Select(" select COUNT(1) from user_info where nickname is not null and pId in (select user_id from user_info where pId=#{userId})")
	int getAll_B_Count(@Param("userId") Integer userId);
	@Select(" select COUNT(1) from user_info where vip_id is not null and pId in (select user_id from user_info where pId=#{userId}) ")
	int getAll_B_VipCount(@Param("userId") Integer userId);
	
	@Select(" select COUNT(1) from user_info where nickname is not null and pId in (select user_id from user_info where pId in (select user_id from user_info where pId=#{userId} )) ")
	int getAll_C_Count(@Param("userId") Integer userId);
	@Select(" select COUNT(1) from user_info where vip_id is not null and pId in (select user_id from user_info where pId in (select user_id from user_info where pId=#{userId} )) ")
	int getAll_C_VipCount(@Param("userId") Integer userId);

	
	// DEF 三层准备注解。已经不再需要。
	/*@Select(" select COUNT(1) from user_info where pId in(select user_id from user_info where pId in (select user_id from user_info where pId in (select user_id from user_info where pId=#{userId}))) ")
	int getAll_D_Count(@Param("userId")Integer userId);
	@Select(" select COUNT(1) from user_info where vip_id is not null and pId in(select user_id from user_info where pId in (select user_id from user_info where pId in (select user_id from user_info where pId=#{userId}))) ")
	int getAll_D_VipCount(@Param("userId")Integer userId);

	@Select(" select COUNT(1) from user_info where pId in(select user_id from user_info where pId in(select user_id from user_info where pId in (select user_id from user_info where pId in (select user_id from user_info where pId=#{userId})))) ")
	int getAll_E_VipCount(@Param("userId")Integer userId);
	@Select(" select COUNT(1) from user_info where vip_id is not null and pId in(select user_id from user_info where pId in(select user_id from user_info where pId in (select user_id from user_info where pId in (select user_id from user_info where pId=#{userId})))) ")
	int getAll_E_Count(@Param("userId")Integer userId);
	
	@Select(" select count(1) from user_info where pId in (select user_id from user_info where pId in(select user_id from user_info where pId in(select user_id from user_info where pId in (select user_id from user_info where pId in (select user_id from user_info where pId=#{userId}))))) ")
	int getAll_F_Count(@Param("userId")Integer userId);
	@Select(" select count(1) from user_info where vip_id is not null and pId in (select user_id from user_info where pId in(select user_id from user_info where pId in(select user_id from user_info where pId in (select user_id from user_info where pId in (select user_id from user_info where pId=#{userId}))))) ")
	int getAll_F_VipCount(@Param("userId")Integer userId);
*/
	





													/**
													 * ======================重构区域==============================
													 */
	//-------------------------------------------------------------------------------------------------------------------------------
	/**
	 * @author Wally
	 * @time 2016年5月6日上午10:37:26
	 * @Description 根据用户id，把用户升级为代理商。代理商状态修改为1.添加代理商 起始日期。
	 * @param
	 * @return void
	 */
	@Update( "update  user_info set agent=#{status} ,agentStartdate=#{datenow},agentEnddate=#{vipEndDate},agentpre=#{agentPre} where user_id=#{userId}" )
	void updateUserAgentInfoByUserID(@Param("userId")int userId, @Param("status")int status,  @Param("datenow")Date datenow,
			 @Param("vipEndDate")Date vipEndDate, @Param("agentPre")int agentPre);
	
	
	// DEF 三层都不要了。暂时先这样。稍后全部注掉。
	@Select(" select 0 ")
	int getAll_D_Count(@Param("userId")Integer userId);
	@Select(" select 0 ")
	int getAll_D_VipCount(@Param("userId")Integer userId);

	@Select(" select 0 ")
	int getAll_E_VipCount(@Param("userId")Integer userId);
	@Select(" select 0 ")
	int getAll_E_Count(@Param("userId")Integer userId);
	
	@Select(" select 0 ")
	int getAll_F_Count(@Param("userId")Integer userId);
	@Select(" select 0  ")
	int getAll_F_VipCount(@Param("userId")Integer userId);

	
	//向上查询。
	/**
	 * 
	 * @author Wally
	 * @time 2016年5月18日下午2:47:26
	 * @Description  查询从当前节点往上的X层数据链。
	 * @param userid 当前节点ID。
	 * @param level 查询多少层
	 * @param upOrDown 向上查还是向下查。1-向下。2-向上
	 * @return String
	 */
	String selectFather(@Param("userid") int userid,@Param("level")int level,@Param("upOrDown")int upOrDown);

	
	
	
	
	
	/**
	 * 
	 * @author Wally
	 * @time 2016年5月18日下午3:27:17
	 * @Description 查询某个节点的上级关系链中 的代理商（最近原则，离被查询节点最近的代理商）【附加条件：是会员，且是代理商】
	 * @param fatherList 关系链。这个是根据某个节点查询出的上级关系链列表。
	 * @param agentManagementTier 需要取得的层数。limit字段使用。满足条件的记录的前几条。
	 * 
	 * @return List<UserInfo>
	 */
	List<UserInfo> selectPreAgent(@Param("list") List<Integer> list,@Param("agentTier")int agentTier);
	List<UserInfo> getAllUserInfo();
	List<UserInfo> getAllWithoutUnionId();



	//	批量更新用户的 uniondId 【这个用的就是老表】
		@Update( "update  user_info set unionid=#{unionid} where user_id=#{userId}" )
		void updateUserUnionId(@Param("unionid")String unionid, @Param("userId")Integer userId);
//=============【新版-began】=================================================================================
	
	
	
	
	//把用户更新为会员。
	@Update( "update userinfo set vipLv = #{vipLv} ,vipStartTime =#{vipStartTime},vipEndTime=#{vipEndTime} where id = #{id}" )
	void upate_NewUserInfo(@Param("id")Integer id, @Param("vipStartTime") Date vipStartTime,  @Param("vipEndTime") Date vipEndTime, @Param("vipLv") int vipLv);


}