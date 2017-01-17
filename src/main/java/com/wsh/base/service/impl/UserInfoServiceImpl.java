package com.wsh.base.service.impl;


import java.security.PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wsh.base.dao.ExpansionRepository;
import com.wsh.base.dao.LocationDao;
import com.wsh.base.dao.WalletRepository;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wsh.base.mapperdao.IGeneralizeInfoMapper;
import com.wsh.base.mapperdao.IMyWalletMapper;
import com.wsh.base.mapperdao.INewUserInfoMapper;
import com.wsh.base.mapperdao.IOldVipInfoMapper;
import com.wsh.base.mapperdao.IPromoterInfoMapper;
import com.wsh.base.mapperdao.IUserInfoMapper;
import com.wsh.base.model.GeneralizeInfo;
import com.wsh.base.model.MyWallet;
import com.wsh.base.model.NewUserInfo;
import com.wsh.base.model.OldVipTmp;
import com.wsh.base.model.PromoterInfo;
import com.wsh.base.model.UserInfo;
import com.wsh.base.service.IUserInfoService;
import com.wsh.base.util.dateUtil.DateUtil;

@Service("UserInfoService")
public class UserInfoServiceImpl implements IUserInfoService {
	@Autowired
	private IUserInfoMapper userInfoMapper;
	
	//--新增 新的数据结构接口。12.12
	@Autowired
	private INewUserInfoMapper newUserInfoMapper;
	
	@Autowired
	private IGeneralizeInfoMapper generalizeinfoMapper;
	
	@Autowired
	private IMyWalletMapper myWalletMapper;
	
	//-------------------------------------------
	
	@Autowired
	private IPromoterInfoMapper promoterInfoMapper;
	@Autowired
	private IOldVipInfoMapper oldVipMapper;
	
	//------------------------------------------------------------------------
	//数据升级区域。12.12  //查询用户数据，统一使用小吴接口。

	//授权更新。12.13测试通过
	@Override
	public int new_upateUserInfo(NewUserInfo userInfo) {
		return newUserInfoMapper.newUpateUserInfo(userInfo);
	}
	
	//测试通过 12.13
	public NewUserInfo new_GetUserInfoByOpenId(String openid){
		NewUserInfo nInfo =  newUserInfoMapper.new_GetUserInfoByOpenId(openid);
		return nInfo;
	}
	
	@Override
	public NewUserInfo new_GetUserInfoByUserId(Integer userid) {
		NewUserInfo nInfo =  newUserInfoMapper.new_GetUserInfoByUserId(userid);
		if(nInfo==null){
			return nInfo;
		}
		//查询银行卡信息
		 Map<String,Object> temp=expansionRepository.getBankByUserId(userid);
		if(temp!=null){
			if(temp.get("bkNo")!=null){
				nInfo.setBankNo(temp.get("bkNo").toString());
			}
		}

		// 收货地址

		Map<String,Object> tmp=expansionRepository.getAddrByUserId(userid);
		if(tmp!=null){
			if(tmp.get("addrDetail")!=null){
				nInfo.setAcceptName(tmp.get("addrDetail").toString());
			}
		}

		//是否是代理商
		GeneralizeInfo generalizeInfo=generalizeinfoMapper.selectByPrimaryKey(userid);
		if(generalizeInfo!=null && generalizeInfo.getIsagent()!=null && generalizeInfo.getIsagent()){
			nInfo.setAgent(Boolean.TRUE);
			nInfo.setAgentpre(generalizeInfo.getPagentid());
			//nInfo.setAgentEndDate();
		}
		return nInfo;
	}

	public int getPId(Integer userId){
		return expansionRepository.getPId(userId);
	}
	
	//更新用户的vip等级。
	@Override
	public void updateUserVipStatus(NewUserInfo newuf) {
		newUserInfoMapper.updateByPrimaryKeySelective(newuf);
	}


	
	//测试通过 12.13
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void createOneUser(String fromUserName, Integer pId) {
			//新数据，带PID更新。
		GeneralizeInfo gInfo = new GeneralizeInfo();
		NewUserInfo userinfo = new NewUserInfo();
		System.out.println("关注事件，新增用户.........."+fromUserName+"  PID="+pId);
		//先插入userInfo,不然gInfo没有userid.基础数据更新。

		//虽然不可能，但是防止意外，一旦存在就返回。不做更新。
		NewUserInfo nuInfo = newUserInfoMapper.new_GetUserInfoByOpenId(fromUserName);
		if(null != nuInfo){
			return;
		}
		 
		userinfo.setCreatedtime(new Date());
		userinfo.setOpenid(fromUserName);
		userinfo.setLastlandingtime(new Date());
		
		newUserInfoMapper.insertSelective(userinfo);  //插入用户基础表
		
		if(userinfo.getId() !=null ){
			System.out.println("插入用户ID：==="+userinfo.getId());
			gInfo.setUserid(userinfo.getId());
			if(null != pId){
				gInfo.setPid(pId);	
			}
			gInfo.setCreatetime(new Date());
			
			generalizeinfoMapper.insertSelective(gInfo);  //同步更新关系表。
			
		}
		
	}
	
	/**
	 * 
	 * @Title UpdateOneUser
	 * @author Wally
	 * @time 2016年12月13日下午3:15:43
	 * @Description 针对：扫描事件 的 服务。
	 * @param
	 * @return void
	 */
	@Override
	public void UpdateOneUser(String fromUserName, Integer pId) {
		
		System.out.println("扫描事件服务，被扫描人ID="+pId+"  ......扫描人：openid=="+fromUserName);
		
		//规则：扫描人的PID为空，并且没有下线。只有这种情况，在这里才能更新PID。否则统统什么也不做。
		
		NewUserInfo newUserInfo = new_GetUserInfoByUserId(pId);
		if(newUserInfo.getViplv() > 0){
			//为了安全：这里要求，被扫描人的 ID ，必须是vip.后期可以放开。[这里的问题在于，只要放开就意味着，任何人都可以收集粉丝。]	

			NewUserInfo op_uf  = new_GetUserInfoByOpenId(fromUserName); 
			if(null != op_uf){ //存在
				GeneralizeInfo gf =  generalizeinfoMapper.selectByPrimaryKey(op_uf.getId());
				if(null != gf){
					if(null == gf.getPid()){  //上空。否则不做事情
						//判断扫描人的数据，是否下空。如果下不为空，也不做事情。必须同时满足：上空下空。
						if(generalizeinfoMapper.checkHasChild(op_uf.getId()) == 0 ){ //下线为0.则可以更新PID。其他任何情况都不做任何事情
							//此时，扫描人的，上空，下空，可以更新PID了。只有这种情况才更新。其他情况一律不管。
							GeneralizeInfo gfinInfo = new GeneralizeInfo();
							gfinInfo.setPid(pId);
							gfinInfo.setUserid(op_uf.getId());
							generalizeinfoMapper.updateByPrimaryKeySelective(gfinInfo);
						}
					}
				}else{
					System.out.println("异常数据：关系表中不存在这样的数据，userid="+op_uf.getId());
				}
				
			}
			
		}
		
		
	}

	
	/**
	 * 钱包数据插入
	 */
	@Override
	public void insertMyWallet(MyWallet myWallet) {
		myWalletMapper.insertSelective(myWallet);
	}
	public void insertMyWalletList(List<MyWallet> uList){
		myWalletMapper.insertMyWalletList(uList);
	}
	

	@Override
	public void updateUserUnionId(String uniod, Integer userId) {
		userInfoMapper.updateUserUnionId(uniod,userId);		
	}

	//插入一条新数据。
	@Override
	public void insertNewUserInfo(NewUserInfo newUserInfo) {
		newUserInfoMapper.insertSelective(newUserInfo);
	}


	public int insertNewUserInfoBatch(List<NewUserInfo> records){
		return  newUserInfoMapper.addRecordBatch(records);
	}

	//插入用户关系表
	@Override
	public void insertGeneraInf(GeneralizeInfo generalizeInfo) {
		generalizeinfoMapper.insertSelective(generalizeInfo);
	}

	public int addRecordBatch(List<GeneralizeInfo> records){
		return  generalizeinfoMapper.addRecordBatch(records);
	}



	//Added by William Wu
	public  void addLocation(Map<String,String> map){
		locationDao.addAddress(map);
	}


	public NewUserInfo getUserInfoByUnionId(String unionId){
		return newUserInfoMapper.getUserInfoByUnionId(unionId);
	}

	public Long getBalance(Integer userId){
		return  walletRepository.getBalance(userId);
	}


	public List<Map<String,Object>> getIncome(Integer userId){
		return walletRepository.getIncome(userId);
	}


	public Long getAVIPs(Integer userId){
		return expansionRepository.getAVIPs(userId);
	}
	public Long getAFans(Integer userId){
		return expansionRepository.getAFans(userId);
	}

	public Long getBVIPs(Integer userId){
		return expansionRepository.getBVIPs(userId);
	}
	public Long getBFans(Integer userId){
		return expansionRepository.getBFans(userId);
	}

	public Long getCVIPs(Integer userId){
		return expansionRepository.getCVIPs(userId);
	}
	public Long getCFans(Integer userId){
		return expansionRepository.getCFans(userId);
	}


	public Long getTotalIncome(Integer userId){
		return walletRepository.getTotalIncome(userId);
	}


	public List<Map<String,String>> getAFansDetails(Integer userId){
		return  expansionRepository.getAFansDetails(userId);
	}


	public void  bindBank(Map<String,String> params){
		 expansionRepository.bindBank(params);
	}

	public void  bindAddr(Map<String,String> params){
		expansionRepository.bindAddr(params);
	}

	@Autowired
	LocationDao locationDao;

	@Autowired
	WalletRepository walletRepository;

	@Autowired
	ExpansionRepository expansionRepository;
	
//============【END】===【 升级区域】======================================================================================	
//------------------------------------------------------------------------------------------------------
	
	@Override
	public UserInfo getUserInfoByUserID(int userid) {
		return userInfoMapper.selectByPrimaryKey(userid);
	}


	@Override
	public PromoterInfo checkIsPromoterByUserID(int userid) {
		return promoterInfoMapper.getPromoterInfoByUserID(userid);
	}

	
	@Transactional(readOnly=false)
	public int insertSelective(UserInfo userInfo){
		Integer bean=userInfoMapper.isExists(userInfo.getOpenid());
		if(bean!=null){
			return 1;
		}
		return userInfoMapper.insertSelective(userInfo);
	}


	@Override
	public UserInfo getUserInfoByOpenId(String openId) {
		return userInfoMapper.getUserInfoByOpenId(openId);
	}

	@Override
	public void updateMobileInfoForUser(int userid, String mobile) {
		userInfoMapper.updateMobileInfoForUser(userid,mobile);
	}


	@Override
	public void updateBankInfoForUser(int userid, String bankNo,
			String bankType, String name,String subBank)throws Exception {
		userInfoMapper.updateBankInfoForUser(userid,bankNo,bankType,name,subBank) ;
	}
	
	@Override
	public void updateAcceptAddrForUser(int userid, String acceptName,
			String preaddr, String addr, String postcode) {
		userInfoMapper.updateAcceptAddrForUser(userid,acceptName,preaddr,addr,postcode);
	}
	
	@Transactional(readOnly=false)
	public int upateUserInfo(UserInfo userInfo){
		return userInfoMapper.upateUserInfo(userInfo);

	}


	//更行用户生日
	@Override
	public void toUpdateBithday(Integer userId, String date) {
		
		userInfoMapper.toUpdateBithday(userId,date);
		
	}

	public int selectChild(int userid,int level){
		String result=userInfoMapper.selectChild(userid,level);
		if(result==null || "".equals(result.trim())){
			return 0;
		}
		System.out.println("==================="+result.split(","));
		return Math.max(result.split(",").length-2, 0);
	}

	@Override
	public int filtersMembers(int userid, int level) {
		return userInfoMapper.filtersMembers(userid, level);
	}

	public List<UserInfo> selectFirst(Integer userid){
		return userInfoMapper.selectFirst(userid);
	}


	@Override
	public int hasBindMobile(String mobile) {

		// 查看是否已经绑定手机号
		return userInfoMapper.hasBindMobileExt(mobile);
	}


	@Override
	public OldVipTmp selectByMobile(String mobile) {
		return oldVipMapper.selectByMobile(mobile);
	}


	//使用老会员信息更新用户信息。
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void importOldVipInfo(Integer userId, OldVipTmp ovt) {
		
		OldVipTmp ovv = new OldVipTmp();
		ovv.setId(ovt.getId());
		ovv.setStatus(true);
		oldVipMapper.updateByPrimaryKeySelective(ovv); //更新 老会员表信息，已经使用。
		
		UserInfo uf = new UserInfo();
		uf.setUserId(userId);
		uf.setVipId(ovt.getMobile());
		uf.setMobile(ovt.getMobile());
		uf.setVipStartdate(ovt.getEnddate()); //这里最好换成start day.
		uf.setVipEnddate(DateUtil.addYear(ovt.getEnddate(), 1));	 //到期日+1
		userInfoMapper.updateByPrimaryKeySelective(uf); //更新 userinfo表。
		
		userInfoMapper.clearRelation(userId); //清除关系
	}
	
	public UserInfo checkVip(String openId){
		return userInfoMapper.checkVip(openId);
	}
	public int clearRelation(Integer userId){
		return userInfoMapper.clearRelation(userId);
	}


	@Override
	public List<UserInfo> friendPage_A_list(Integer userId) {
		// TODO Auto-generated method stub
		return userInfoMapper.friendPage_A_list(userId);
	}


	@Override
	public int friendPage_A_allCount(Integer userId) {
		// TODO Auto-generated method stub
		return userInfoMapper.friendPage_A_allCount(userId);
	}


	@Override
	public int friendPage_A_memberCount(Integer userId) {
		// TODO Auto-generated method stub
		return userInfoMapper.friendPage_A_memberCount(userId);
	}


	@Override
	public UserInfo getNickNameByUserID(Integer userid) {

		//return userInfoMapper.selectByPrimaryKey(userid);
		NewUserInfo newUserInfo=newUserInfoMapper.selectByPrimaryKey(userid);
		UserInfo userInfo=new UserInfo();
		if(newUserInfo!=null){
			userInfo.setNickname(newUserInfo.getNickname());
		}
		return userInfo;
	}

	
	//如果这里能去掉：我的人气就又能节省很多时间。其实也没啥用
	@Override
	public HashMap<String, Integer> getAllCountNum(Integer userId) {
			
		HashMap<String, Integer> resultMap = new HashMap<String, Integer>();
		//根据userid取第一层。判断是否大于50。
		int couA =	userInfoMapper.getAll_A_Count(userId);
		int vipA =	userInfoMapper.getAll_A_VipCount(userId);
		int couB = userInfoMapper.getAll_B_Count(userId);
		int vipB =	userInfoMapper.getAll_B_VipCount(userId);
		int couC = userInfoMapper.getAll_C_Count(userId);
		int vipC =	userInfoMapper.getAll_C_VipCount(userId);
		resultMap.put("couA", couA);
		resultMap.put("vipA", vipA);
		resultMap.put("couB", couB);
		resultMap.put("vipB", vipB);
		resultMap.put("couC", couC);
		resultMap.put("vipC", vipC);
		
		resultMap.put("showDEF", 0); //没啥用的属性，可以去掉了。
		
		/*if(vipA > BaseDict.POP_A_ENOUGH_PERSON){ //A >50 个付费会员，就增加
			int couD = userInfoMapper.getAll_D_Count(userId);
			int vipD =	userInfoMapper.getAll_D_VipCount(userId);
			int couE = userInfoMapper.getAll_E_Count(userId);
			int vipE =	userInfoMapper.getAll_E_VipCount(userId);
			int couF = userInfoMapper.getAll_F_Count(userId);
			int vipF =	userInfoMapper.getAll_F_VipCount(userId);
			
			resultMap.put("couD", couD);
			resultMap.put("vipD", vipD);
			resultMap.put("couE", couE);
			resultMap.put("vipE", vipE);
			resultMap.put("couF", couF);
			resultMap.put("vipF", vipF);
			resultMap.put("showDEF", 1);   //以后DEF等级也不要了。 2016.5.19 by Wally modify.
		}else{
			resultMap.put("showDEF", 0);
		}*/
		
		return resultMap;
		
	}


	//查询 所有 vip数据，不查询 总人气。没必要查询人气。加快速度，只查询vip人数。
	@Override
	public HashMap<String, Integer> getVipCountNum(Integer userId) {
		HashMap<String, Integer> resultMap = new HashMap<String, Integer>();
		int vipA =	userInfoMapper.getAll_A_VipCount(userId);
		int vipB =	userInfoMapper.getAll_B_VipCount(userId);
		int vipC =	userInfoMapper.getAll_C_VipCount(userId);
		resultMap.put("vipA", vipA);
		resultMap.put("vipB", vipB);
		resultMap.put("vipC", vipC);
		resultMap.put("vipD", 0);
		resultMap.put("vipE", 0);
		resultMap.put("vipF", 0);
		return resultMap;
	/*	if(vipA > BaseDict.POP_A_ENOUGH_PERSON){ //A >50 个付费会员，就增加
			int vipD =	userInfoMapper.getAll_D_VipCount(userId);
			int vipE =	userInfoMapper.getAll_E_VipCount(userId);
			int vipF =	userInfoMapper.getAll_F_VipCount(userId);
			resultMap.put("vipD", vipD);
			resultMap.put("vipE", vipE);
			resultMap.put("vipF", vipF);
			resultMap.put("vipD", 0);
			resultMap.put("vipE", 0);
			resultMap.put("vipF", 0);
		}else{
			
		}*/
	
	}


	@Override
	public int checkIsLiveByUserid(Integer yaoqm) {
		return userInfoMapper.checkIsLiveByUserid(yaoqm);
	}


	@Override
	public void updateMobileInfoForUser(Integer userId, String mobile,
			Integer yaoqm) {
		userInfoMapper.updateYaoQmMobileById(userId,mobile,yaoqm);
	}
//==============================================================

	@Override
	public List<UserInfo> getAllUserInfo() {
		
		return userInfoMapper.getAllUserInfo();
	}

	public List<UserInfo> getAllWithoutUnionId(){
		return userInfoMapper.getAllWithoutUnionId();
	}

	public List<Map<String,Object>> getNext(Integer pId){
		return expansionRepository.getNext(pId);
	}
	public List<Map<String,Object>> getAllVIPs(){
		return expansionRepository.getAllVIPs();
	}
	public Integer getWallet(Integer userId){
		return expansionRepository.getWallet(userId);
	}

	public Integer joinMember(final Map<String,String> params){
		return expansionRepository.joinMember(params);
	}
	public void insertTicket(Integer userId){
		expansionRepository.insertTicket(userId);
	}
	public Map<String,Object> getTicketById(Integer userId){
		return  expansionRepository.getTicketById(userId);
	}

	public void shown(Integer userId){
		 expansionRepository.shown(userId);
	}
}
