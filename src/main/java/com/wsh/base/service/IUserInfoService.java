package com.wsh.base.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wsh.base.model.GeneralizeInfo;
import com.wsh.base.model.MyWallet;
import com.wsh.base.model.NewUserInfo;
import com.wsh.base.model.OldVipTmp;
import com.wsh.base.model.PromoterInfo;
import com.wsh.base.model.UserInfo;


/**
 * 
 * @package : com.wsh.base.service
 * @ClassName: IUserInfoService
 * @Description: 用户基础信息服务。
 * @author Wally
 * @date 2016年1月9日 下午1:31:09
 * @modify Wally
 * @modifyDate 2016年1月9日 下午1:31:09
 */
public interface IUserInfoService {

	
	//-------------------------本区域升级 2016.12.12 by wally------------------------------------------
	//新的用户更新。
	int new_upateUserInfo(NewUserInfo userInfo); 
	
	NewUserInfo new_GetUserInfoByOpenId(String openId);
	NewUserInfo getUserInfoByUnionId(String unionId);
	NewUserInfo new_GetUserInfoByUserId(Integer userid);
	
	//扫描关注的新增事件。
	void createOneUser(String fromUserName, Integer pId);
	void UpdateOneUser(String fromUserName, Integer pId);  
	
	// 钱包数据新增。一条
	void insertMyWallet(MyWallet myWallet);
	void insertMyWalletList(List<MyWallet> uList);
	void updateUserVipStatus(NewUserInfo newuf);

	void updateUserUnionId(String uniod, Integer userId);

	void insertNewUserInfo(NewUserInfo newUserInfo);
	int insertNewUserInfoBatch(List<NewUserInfo> records);

	void insertGeneraInf(GeneralizeInfo generalizeInfo);

	int addRecordBatch(List<GeneralizeInfo> records);

	//------------------------------------------------------------------
	/**
	 * 升级划分区域 
	 */
	
	
	
	
	
	//获取推广信息，根据userid。
	PromoterInfo checkIsPromoterByUserID(int userid);
	@Deprecated
	int insertSelective(UserInfo userInfo);

	//更加用户ID，获取用户信息。
	@Deprecated
	UserInfo getUserInfoByUserID(int userid);

	@Deprecated
	UserInfo getUserInfoByOpenId(String openId);

	@Deprecated
	int upateUserInfo(UserInfo userInfo);
	
	//绑定手机号操作，-更新用户手机号。
	void updateMobileInfoForUser(int userid, String mobile);
	
	/**
	 * 
	 * @Title updateBankInfoForUser
	 * @author Wally
	 * @time 2016年1月13日下午4:46:27
	 * @Description 更新用户银行卡信息
	 * @param
	 * @return void
	 * @throws Exception 
	 */
	void updateBankInfoForUser(int userid, String bankNo, String bankType,
			String name, String subBank) throws Exception;
	
	/**
	 * 
	 * @Title updateAcceptAddrForUser
	 * @author Wally
	 * @time 2016年1月13日下午4:46:10
	 * @Description 更新用户收货信息
	 * @param
	 * @return void
	 */
	void updateAcceptAddrForUser(int userid,String acceptName,String preaddr,String addr,String postcode);

	//更新 用户的生日。
	//Done
	void toUpdateBithday(Integer userId, String date);


	//Done
	@Deprecated
	int selectChild(int userid,int level);

	//Done
	@Deprecated
	int filtersMembers(int userid,int level);

	//Done
	@Deprecated
	List<UserInfo> selectFirst(Integer userid);

	//Done
	int hasBindMobile(String mobile);

	@Deprecated
	OldVipTmp selectByMobile(String mobile);

	void importOldVipInfo(Integer userId, OldVipTmp ovt);

	
	UserInfo checkVip(String openId);
	
	int clearRelation(Integer userId);

	List<UserInfo> friendPage_A_list(Integer userId);

	int friendPage_A_allCount(Integer userId);

	int friendPage_A_memberCount(Integer userId);


	//Done
	UserInfo getNickNameByUserID(Integer intValue);

	
	HashMap<String, Integer> getAllCountNum(Integer userId);

	//Done
	@Deprecated
	HashMap<String, Integer> getVipCountNum(Integer userId);

	int checkIsLiveByUserid(Integer yaoqm);

	void updateMobileInfoForUser(Integer userId, String mobile, Integer yaoqm);

	List<UserInfo> getAllUserInfo();
	List<UserInfo> getAllWithoutUnionId();

	//Add location by William Wu
	void addLocation(Map<String,String> map);
	Long getBalance(Integer userId);
	Long getTotalIncome(Integer userId);

	public Long getAVIPs(Integer userId);
	public Long getAFans(Integer userId);

	public Long getBVIPs(Integer userId);
	public Long getBFans(Integer userId);

	public Long getCVIPs(Integer userId);
	public Long getCFans(Integer userId);

	public List<Map<String,String>> getAFansDetails(Integer userId);

	public void  bindBank(Map<String,String> params);
	public void  bindAddr(Map<String,String> params);


	public List<Map<String,Object>> getIncome(Integer userId);

	public int getPId(Integer userId);

	public List<Map<String,Object>> getAllVIPs();
	public List<Map<String,Object>> getNext(Integer pId);
	public Integer getWallet(Integer userId);
	//
	public Integer joinMember(final Map<String,String> params);
	public void insertTicket(Integer userId);
	public Map<String,Object> getTicketById(Integer userId);
	public void shown(Integer userId);
	
}
