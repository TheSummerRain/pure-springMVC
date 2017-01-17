package com.wsh.base.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.crypto.Data;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;
import org.codehaus.jackson.map.deser.ValueInstantiators.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mchange.v1.lang.GentleThread;
import com.wsh.base.dict.BaseDict;
import com.wsh.base.mapperdao.IFreezeLogMapper;
import com.wsh.base.mapperdao.IGeneralizeInfoMapper;
import com.wsh.base.mapperdao.IMyWalletMapper;
import com.wsh.base.mapperdao.INewPayLogMapper;
import com.wsh.base.mapperdao.INewUserInfoMapper;
import com.wsh.base.mapperdao.IPopConsumeMapper;
import com.wsh.base.mapperdao.IUserCreditMapper;
import com.wsh.base.mapperdao.IUserFreezeMapper;
import com.wsh.base.mapperdao.IUserIncomeMapper;
import com.wsh.base.mapperdao.IUserInfoMapper;
import com.wsh.base.mapperdao.IUserPayLogMapper;
import com.wsh.base.mapperdao.IWBInfoMapper;
import com.wsh.base.model.Bill;
import com.wsh.base.model.Count;
import com.wsh.base.model.CreditLog;
import com.wsh.base.model.FreezeWeiBi;
import com.wsh.base.model.GeneralizeInfo;
import com.wsh.base.model.MyWallet;
import com.wsh.base.model.NewPayLog;
import com.wsh.base.model.NewUserInfo;
import com.wsh.base.model.PayLog;
import com.wsh.base.model.PopConsume;
import com.wsh.base.model.UserFreeze;
import com.wsh.base.model.UserIncome;
import com.wsh.base.model.UserInfo;
import com.wsh.base.model.WeibiLog;
import com.wsh.base.service.IPayCoreService;
import com.wsh.base.util.JavaSmsApi;
import com.wsh.base.util.SerialNumUtil;
import com.wsh.base.util.dateUtil.DateUtil;
import com.wsh.base.utilhttp.HttpSendUtil;
import com.wsh.base.utilhttp.dataEntity.PayToCust;
import com.wsh.base.utilhttp.dataEntity.PubData;
import com.wsh.base.wxpay.WxConfig;

/**
 * 
 * @package : com.wsh.base.service.impl
 * @ClassName: PayCoreService
 * @Description: 核心支付服务
 * @author Wally
 * @date 2016年1月8日 下午5:55:28
 * @modify Wally
 * @modifyDate 2016年1月8日 下午5:55:28
 */

@Service("payCoreService")
public class PayCoreServiceImpl implements IPayCoreService {

	private static final Log logger = LogFactory
			.getLog(PayCoreServiceImpl.class);

	// Mapper注册区域。
	@Autowired
	private IWBInfoMapper wbInfoMapper; // 维币mapper
	@Autowired
	private IUserInfoMapper userInfoMapper; // 用户mapper
	
	@Autowired
	private INewUserInfoMapper newUserInfoMapper;  //新用户mapper.
	
	@Autowired
	private IUserPayLogMapper userPayLogMapper; // 支付流水记录。
	
	@Autowired
	private INewPayLogMapper newPayLogMapper; // 新的支付交易流水表。    //可以不用这个，继续沿用老的支付流水，反正就只有微信端的程序才会用，今后改版再说。【预留】
	
	@Autowired
	private IMyWalletMapper mywalletMapper;  //我的钱包。 12.16
	
	@Autowired
	private IGeneralizeInfoMapper genInfoMapper; //新的关系表数据。
	
	
	@Autowired
	private IFreezeLogMapper freezeLogMapper; // 冻结日志【作废】

	
	@Autowired
	private IUserCreditMapper userCreditMapper; // 用户积分mapper.

	@Autowired
	private IPopConsumeMapper popConsumeMapper; // 推广 消费记录。【作废处理】

	@Autowired
	private IUserIncomeMapper userIncomeMapper; // 用户收入表。管理费，佣金。

	@Autowired
	private IUserFreezeMapper userFreezeMapper; //用户提现流水表。
	
	@Autowired
	private TaskExecutor taskExecutor;

	
	
	
	/**
	 * 测试服务是否正常。数据操作是否正常。
	 */
	@Override
	public void ceshi(){
		
		Integer userId  = 24745;
		//查询的是generalizeinfo 表，这是新表数据。
		String fatherString = genInfoMapper.selectFatherNew(userId,BaseDict.AGENT_RELATION_TIER, 2); 
		
		if(null != fatherString && !"$,".equals(fatherString)){
			List fatherList = Arrays.asList(fatherString.substring(3)
					.split(","));
			
			
			List<MyWallet> uList =  new ArrayList<MyWallet>();
			
			//这里只依靠倒序，无法解决所有风险。假如用户之间的扫描关系就没有按照从小到大往后排，那么就无法保证最近原则。切记切记。

			NewUserInfo fianlUserInfo  = newUserInfoMapper.new_GetUserInfoByUserId(userId);   //查询：24450
			GeneralizeInfo ntm = genInfoMapper.selectByPrimaryKey(userId);
			if(null == ntm.getPid()){
				return;   //如果没有PID，直接返回。
			}//24450.pid = 24447
			
			//查询链条上 ，3个vip上层。一人返50块。 //保证必须是vip.//尴尬就在就近原则，这里没法直接保证用户从小到大，所以排序limit 3也用不了了。
			List<GeneralizeInfo> preVip = genInfoMapper.selectPreUser(fatherList, 100); //结果： 24447,23165,3897,310,227
		
			for (GeneralizeInfo gf : preVip) {
				//保证最近原则。必须循环判断了。因为上下关系 不可能完全从小到大排序。
				if(ntm.getPid().intValue() == gf.getUserid().intValue()){
					uList.add(crtMyWalletObj(userId,gf.getUserid(),"3",BaseDict.POP_ABC_INCOME,"一级好友："+fianlUserInfo.getNickname()+"点亮VIP.")); //'3-推广费； fromuserid=  userId.   toUserId= gf.getUserId = 24447 [一层]
					//新循环。从头拿数据。
					if(null != gf.getPid()){
						for(GeneralizeInfo gf1 : preVip){
							if(gf.getPid().intValue() == gf1.getUserid().intValue()){   // gf.getPid = 2316;
								uList.add(crtMyWalletObj(userId,gf1.getUserid(),"3",BaseDict.POP_ABC_INCOME,"二级好友："+fianlUserInfo.getNickname()+"点亮VIP.")); //'3-推广费； fromuserid=  userId.   toUserId= gf1.getUserId = 23165 [二级]
								if(null != gf1.getPid()){
									for(GeneralizeInfo gf2 : preVip){ // gf1.getpid = 3897;
										if( gf1.getPid().intValue() == gf2.getUserid().intValue()){
											uList.add(crtMyWalletObj(userId,gf2.getUserid(),"3",BaseDict.POP_ABC_INCOME,"三级好友："+fianlUserInfo.getNickname()+"点亮VIP.")); //'3-推广费； fromuserid=  userId.  toUserId= gf2.getuserId = 3897 [三级]
											break;
										}
									}
								}
								break;
							}
						}
					}
					break;
				}
			}// end for ,推广费用计算完成。

			int tmpagent = 0;
			//管理费计算。【鉴于目前代理商人数少，暂时先这样，其实按照就近原则，这样写是有一定风险的】
			for (GeneralizeInfo ag : preVip) {
				
				if(ag.getIsagent()){
					if(tmpagent < 3){
						uList.add(crtMyWalletObj(userId,ag.getUserid(),"1",BaseDict.AGENT_MANAGEMENT_COST,"管理费"));  // 1-管理费。fromuser = userId,touserid = ag.getUserid.	
						tmpagent++;
					}
				}
				
			}
			
			if(uList.size() > 0){
				mywalletMapper.insertMyWalletList(uList);  //批量插入
			}
		}
	}
		
		// 创建一个钱包对象。
		public MyWallet crtMyWalletObj(Integer fromuserId,Integer toUserId,String cmType ,Integer money,String sourceDes) {
			MyWallet mw = new MyWallet();
			
			//walId,fromUserId, toUserId, cmType, money, createTime,sourceDes
			mw.setFromuserid(fromuserId);
			mw.setTouserid(toUserId);
			mw.setCmtype(cmType);
			mw.setMoney(money*100);
			mw.setCreatetime(new Date());
			mw.setSourcedes(sourceDes);
			
			return mw;
		}

	
	
	/**
	 * 剩余维币开通会员
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String openVIPForUserByWB(Integer userid, Integer payMoney,
			Integer payYear, String payType, String mobile) throws Exception {
		// System.out.println("..service....当前执行的线程名称........"+Thread.currentThread().getName());

		// 从维币余额扣除维币。
		int spWeibi = wbInfoMapper.toSearchMyWb(userid); // 查询维币剩余
		Date datenow = new Date();
		logger.info("用户：" + userid + ".剩余维币...." + spWeibi + ".当前时间...."
				+ DateUtil.getDate(datenow));
		WeibiLog wbl = new WeibiLog();
		wbl.setIntro(BaseDict.OPENVIP);
		wbl.setExpend(payMoney); // 消费维币数量.填这个字段，就是减少
		wbl.setSpWeibi(spWeibi - payMoney);// 剩余维币
		wbl.setUserId(userid);
		wbl.setCreatetime(datenow);

		Integer theno = wbInfoMapper.instWbLogForUser(wbl);// 减少维币。
		logger.info("用户：" + userid + "-开通会员...." + payYear + "年,充值维币:"
				+ payMoney + "...流水ID：" + wbl.getWeibId());
		// 有个问题：维币开通会员，需要在 流水表里记录吗？？？？？感觉木有必要。

		// 生成一个VIP账号。生成规则，用什么生成呢？手机号直接绑定吧。【这里要判断下，如果已有VIP则是续费；如果没有VIP字段，则是新开。需要生产一个VIP数字。最好是手机号生产，所以需要先绑定手机。】
		Date vipEndDate = DateUtil.addYear(datenow, payYear); // 会员到期日。
		userInfoMapper.updateUserVIPInfoByUserID(userid, mobile, datenow,
				vipEndDate); // 更新会员信息。

		// 调用开通会员送积分 方法。【开通会员送 50 积分。】
		insertOneCredits(userid, "开会员送维币", 50, 0);

		try {
			// 发送短信，无论成功还是失败，只调用，不处理。
			String text = "【维书会】尊敬的：" + "先生or女士 ," + " 您于"
					+ DateUtil.getDate(datenow) + "成功在维书会开通会员服务。感谢您的支持！";
			JavaSmsApi.sendSms(BaseDict.MSG_API_KEY, text, mobile);
		} catch (Exception e) {
			logger.error("用户：" + userid + "==短信调用发送失败....."
					+ DateUtil.getDate(datenow));
		}
		return mobile;
	}

	/**
	 * 
	 * @Title insertOneCredits
	 * @author Wally
	 * @time 2016年1月12日下午3:39:28
	 * @Description 增加一条积分记录
	 * @param desc
	 *            积分规则描述，比如 开通会员增加积分。
	 * @param add
	 *            如果是增加积分，就调用这个。 【二选一，另外一个 必需填0】
	 * @param minus
	 *            如果是减少积分就调用这个。【二选一，另外一个 必须填0】
	 * @return Integer
	 */
	public Integer insertOneCredits(Integer userid, String desc, Integer add,
			Integer minus) {
		// ---------------------积分增加模块----------------------------------------------------------------------------------------
		// 支付成功后 ，为用户一条 积分信息。
		// 查询 用户剩余积分。累加。
		Integer surjf = userCreditMapper.toQueryUserSuCredits(userid);
		CreditLog creditLog = new CreditLog();
		if (minus > 0) { // 说明是走的减法。
			if ((null != surjf ? surjf : 0) - minus < 0) { // 不够减。
				logger.error("用户：" + userid + "..剩余积分：" + surjf + ".不够减去.."
						+ minus + "......." + DateUtil.getDate(new Date()));
				return null;
			} else {
				creditLog.setExpend(minus);// 减少
				creditLog.setSpCredit(surjf - minus); // 走减法，并且够减。
			}
		}
		if (add > 0) { // 走加法
			creditLog.setIncome(add); // 增加
			creditLog.setSpCredit(null != surjf ? surjf + add : add); // 如果没有积分数据，那么剩余积分就是本次新增的50.否则就+50
		}
		// 如果都不为空，说明方法调用错误。
		if (creditLog.getExpend() != null && creditLog.getIncome() != null) {
			logger.error("请求方法调用错误......");
			return null;
		}
		creditLog.setUserId(userid);
		creditLog.setIntro(desc); // 这里是可以走：键值对 配置的。需要定义 积分规则。
		creditLog.setCreatetime(new Date());

		userCreditMapper.instCreditLogForUser(creditLog);
		logger.info("用户：" + userid + ".积分【加-减】...数据操作ID："
				+ creditLog.getCreditId());
		return creditLog.getCreditId();// 增加记录的ID。
	}

	
	
	//插入用户收入表。【老版本，作废处理：12.16】
	public void doRunnableExecute(final Integer userId) {
		try {
			taskExecutor.execute(new Runnable() {
				public void run() {
					String fatherString = userInfoMapper.selectFather(userId,BaseDict.AGENT_RELATION_TIER, 2); 
					if(null != fatherString && !"$,".equals(fatherString)){
						List fatherList = Arrays.asList(fatherString.substring(3)
								.split(","));
						
						//这里最好在加上一个循环。因为这里会查出从当前UIserID节点开始向上的X层（目前是100层）关系链数据。如果太大，恐怕，后面in函数无法工作。
						//所以最好把：fatherList的size做下判断。如果，数组大于100,就停下计算一次。如果100个也撑不下，就换50个变量查一次。这样相当于：分段截取查询。
						
						// 查询 父亲链 上 面 X 层 代理商。需要给他们返钱。
						List<UserInfo> lsuser = userInfoMapper.selectPreAgent(fatherList, BaseDict.AGENT_MANAGEMENT_TIER);
						List<UserIncome> uList =  new ArrayList<UserIncome>();
						for (int i = 0; i < lsuser.size(); i++) {
							//循环一个对象，list插入。
							uList.add(createIncomeObject(userId,lsuser.get(i).getUserId(),BaseDict.AGENT_MANAGEMENT_COST,1)); //1-管理费，2-佣金
						}
						
						if(lsuser.size() > 0){  //如果数组为空，那就会报错。  12.16，modify by jack ,如果这里不增加这个判断，遇见空数组就会报错。
							userIncomeMapper.insertManagerCost(uList); //批量插入结果
						}
					}
				}
				// 创建一个收入对象。
				public UserIncome createIncomeObject(int fromUserId, int toUserID,
						int income, int type) {
					UserIncome uc = new UserIncome();
					uc.setCreatetime(new Date());
					uc.setFromuserid(fromUserId);
					uc.setTouserid(toUserID);
					uc.setIncome(income);
					uc.setType(type);
					return uc;
				}
			});
		} catch (Exception e) {
			logger.error("...................线程启动失败................");
		}
	}

	
	/**
	 * 
	 * @Title 向用户钱包返钱。要考虑的可多了
	 * @author Wally
	 * @time 2016年12月16日下午5:32:57
	 * @Description 这个可复杂了。
	 * @param
	 * @return void
	 */
	//新版，向代理商返钱。向用户钱包返钱。【启用】
	public void new_doRunnableExecute(final Integer userId){
		
		//全部向钱包反数据。【向钱包插入数据。】
		//1、向用户反 推广费。50元，三级。
		//2、在这个过程中，要反管理费。
		try {
			taskExecutor.execute(new Runnable() {
				public void run() {
					
					//Integer userId  = 24745;
					//查询的是generalizeinfo 表，这是新表数据。
					String fatherString = genInfoMapper.selectFatherNew(userId,BaseDict.AGENT_RELATION_TIER, 2); 
					
					if(null != fatherString && !"$,".equals(fatherString)){
						List fatherList = Arrays.asList(fatherString.substring(3)
								.split(","));
						
						List<MyWallet> uList =  new ArrayList<MyWallet>();
						//这里只依靠倒序，无法解决所有风险。假如用户之间的扫描关系就没有按照从小到大往后排，那么就无法保证最近原则。切记切记。
						NewUserInfo fianlUserInfo  = newUserInfoMapper.new_GetUserInfoByUserId(userId);   //查询：24450
						GeneralizeInfo ntm = genInfoMapper.selectByPrimaryKey(userId);
						if(null == ntm.getPid()){
							return;   //如果没有PID，直接返回。
						}//24450.pid = 24447
						
						//查询链条上 ，3个vip上层。一人返50块。 //保证必须是vip.//尴尬就在就近原则，这里没法直接保证用户从小到大，所以排序limit 3也用不了了。
						List<GeneralizeInfo> preVip = genInfoMapper.selectPreUser(fatherList, 100); //结果： 24447,23165,3897,310,227
					
						for (GeneralizeInfo gf : preVip) {
							//保证最近原则。必须循环判断了。因为上下关系 不可能完全从小到大排序。
							if(ntm.getPid().intValue() == gf.getUserid().intValue()){
								uList.add(crtMyWalletObj(userId,gf.getUserid(),"3",BaseDict.POP_ABC_INCOME,"一级好友："+fianlUserInfo.getNickname()+"点亮VIP.")); //'3-推广费； fromuserid=  userId.   toUserId= gf.getUserId = 24447 [一层]
								//新循环。从头拿数据。
								if(null != gf.getPid()){
									for(GeneralizeInfo gf1 : preVip){
										if(gf.getPid().intValue() == gf1.getUserid().intValue()){   // gf.getPid = 2316;
											uList.add(crtMyWalletObj(userId,gf1.getUserid(),"3",BaseDict.POP_ABC_INCOME,"二级好友："+fianlUserInfo.getNickname()+"点亮VIP.")); //'3-推广费； fromuserid=  userId.   toUserId= gf1.getUserId = 23165 [二级]
											if(null != gf1.getPid()){
												for(GeneralizeInfo gf2 : preVip){ // gf1.getpid = 3897;
													if( gf1.getPid().intValue() == gf2.getUserid().intValue()){
														uList.add(crtMyWalletObj(userId,gf2.getUserid(),"3",BaseDict.POP_ABC_INCOME,"三级好友："+fianlUserInfo.getNickname()+"点亮VIP.")); //'3-推广费； fromuserid=  userId.  toUserId= gf2.getuserId = 3897 [三级]
														break;
													}
												}
											}
											break;
										}
									}
								}
								break;
							}
						}// end for ,推广费用计算完成。

						int tmpagent = 0;
						//管理费计算。【鉴于目前代理商人数少，暂时先这样，其实按照就近原则，这样写是有一定风险的】
						for (GeneralizeInfo ag : preVip) {
							
							if(ag.getIsagent()){
								if(tmpagent < 3){
									uList.add(crtMyWalletObj(userId,ag.getUserid(),"1",BaseDict.AGENT_MANAGEMENT_COST,"管理费"));  // 1-管理费。fromuser = userId,touserid = ag.getUserid.	
									tmpagent++;
								}
							}
							
						}
						
						if(uList.size() > 0){
							mywalletMapper.insertMyWalletList(uList);  //批量插入
						}
					}
					
				}
				
				// 创建一个钱包对象。
				public MyWallet crtMyWalletObj(Integer fromuserId,Integer toUserId,String cmType ,Integer money,String sourceDes) {
					MyWallet mw = new MyWallet();
					
					//walId,fromUserId, toUserId, cmType, money, createTime,sourceDes
					mw.setFromuserid(fromuserId);
					mw.setTouserid(toUserId);
					mw.setCmtype(cmType);
					mw.setMoney(money*100);
					mw.setCreatetime(new Date());
					mw.setSourcedes(sourceDes);
					
					return mw;
				}
				
			});
		} catch (Exception e) {
			logger.error("...................线程启动失败................");
		}
		
	}
	
	
	
	/**
	 * 微信支付，开通会员，新 数据模型。 12.16 add by wally.【启用】
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String new_openVIP(Integer userid, Integer payMoney,
			Integer payYear, String payType, String orderno,
			String transaction_id, Integer status) throws Exception {
		
		NewUserInfo userInfo = newUserInfoMapper.new_GetUserInfoByUserId(userid);
		if (null == userInfo) {
			return null;
		}
		
		if("".equals(orderno) || null == orderno){ //如果本地订单为空，那么直接返回
			return null;
		}

		try {
			//修改 支付记录表的状态。这里【继续沿用】老的 pay_log表。新表payLog，不管。今后升级可以再统一。【这里不改表结构，也不影响程序使用】
			userPayLogMapper.updateOrderForUser(userid, orderno,
					transaction_id, 0, status); // 从创建状态变为：1成功，或2失败
		} catch (Exception e) {
			logger.error("....开通会员修改订单失败....." + "订单号：" + orderno + "...用户ID："
					+ userid);
		}		
		
		
		//2、开通会员，修改会员等级。
		Date datenow =  new Date(); 
		Date vipEndDate = DateUtil.addYear(datenow, payYear); // 会员到期日。
		System.out.println("......开通会员,id=" + userInfo.getMobile() + "........开始日期：" + DateUtil.getDate(datenow) + ".....结束日期：" + vipEndDate);
		
		try {
			userInfoMapper.upate_NewUserInfo(userid,datenow,vipEndDate,1);  //更新为会员
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(".....更新会员信息失败.......用户ID：" + userid + "...mobile:"
					+ userInfo.getMobile());
		}
		
		//2.1、发送短信告知。
		try {
			// 发送短信，无论成功还是失败，只调用，不处理。
			String text = "【维书会】尊敬的：" + "先生或女士 ," + " 您于"
					+ DateUtil.getDate(datenow) + "成功在维书会开通会员服务。感谢您的支持！";
			JavaSmsApi
					.sendSms(BaseDict.MSG_API_KEY, text, userInfo.getMobile());
		} catch (Exception e) {
			logger.error("用户：" + userid + "开通会员，短信调用发送失败.....");
		}
		
		
		//3、向上返钱。[向上级，返推广费，和向代理商反20块钱佣金]
		new_doRunnableExecute(userid);
		return null;
	}


	
	
	
	
	/**
	 * 微信支付开通会员
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String openVIPForUserByWX(Integer userid, Integer payMoney,
			Integer payYear, String payType, String orderno,
			String transaction_id, Integer status) throws Exception {

		UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userid);
		if (null == userInfo) {
			return null;
		}

		try {
			userPayLogMapper.updateOrderForUser(userid, orderno,
					transaction_id, 0, status); // 从创建状态变为：1成功，或2失败
		} catch (Exception e) {
			logger.error("....开通会员修改订单失败....." + "订单号：" + orderno + "...用户ID："
					+ userid);
		}

		// 给用户VIP账号,修改到期日期。
		Date datenow =  new Date(); 
		
		// 2016.9.21 delete by Wally  ;如果是续费，则按当前日期计算到日期。
		// 生成一个VIP账号。生成规则，用什么生成呢？手机号直接绑定吧。【这里要判断下，如果已有VIP则是续费；如果没有VIP字段，则是新开。需要生产一个VIP数字。最好是手机号生产，所以需要先绑定手机。】
		//判断是否是续费。
	/*	if(null != userInfo.getVipEnddate()){
			datenow = userInfo.getVipEnddate();
		}else{
			 datenow = new Date();
		}*/
		Date vipEndDate = DateUtil.addYear(datenow, payYear); // 会员到期日。
		logger.info("......开通会员,id+" + userInfo.getMobile() + "........开始日期："
				+ DateUtil.getDate(datenow) + ".....结束日期：" + vipEndDate);

		System.out.println("......开通会员,id+" + userInfo.getMobile() + "........开始日期：" + DateUtil.getDate(datenow) + ".....结束日期：" + vipEndDate);
		try {
			userInfoMapper.updateUserVIPInfoByUserID(userid,
					userInfo.getMobile(), datenow, vipEndDate); // 更新会员信息。

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(".....更新会员信息失败.......用户ID：" + userid + "...mobile:"
					+ userInfo.getMobile());
		}

		// 调用开通会员送积分 方法。【开通会员送 50 积分。】[需要管他是新开通还是 续费吗？]
		// insertOneCredits(userid,"开会员送维币",50,0);

		try {
			// 发送短信，无论成功还是失败，只调用，不处理。
			String text = "【维书会】尊敬的：" + "先生or女士 ," + " 您于"
					+ DateUtil.getDate(datenow) + "成功在维书会开通会员服务。感谢您的支持！";
			JavaSmsApi
					.sendSms(BaseDict.MSG_API_KEY, text, userInfo.getMobile());
		} catch (Exception e) {
			logger.error("用户：" + userid + "开通会员，短信调用发送失败.....");
		}
		// 线程执行。 //返管理费。
		doRunnableExecute(userid);
		return null;
	}

	/**
	 * @author Wally
	 * @time 2016年1月11日下午3:09:38
	 * @Description 封装一个PayLog对象
	 * @return PayLog
	 */
	// //某个人，某时间，通过什么渠道，做了什么业务，支付多少钱，订单号多少,状态。
	public PayLog getPayLogInfo(Integer userid, Date paydate, String busType,
			Integer payMoney, String orderno, int status) {
		PayLog pLog = new PayLog();
		pLog.setUserId(userid);
		pLog.setPaydate(paydate);
		pLog.setBustype(busType);
		pLog.setTransmoney(payMoney);
		pLog.setOrderno(orderno);
		pLog.setStatus(status);
		return pLog;
	}

	/**
	 * 1、启动微信，提交 给自己充值 下单。 重要：请考虑，微信充值成功，而我们数据执行错误的情况。 【暂时-作废。采用下面的order方法。】
	 */
	@Override
	public Map<String, Object> startPaySelf(Integer payMoney, int userid)
			throws Exception {
		// 原Order方法。
		return null;
	}

	/**
	 * 下单
	 */
	@Override
	public Map<String, String> order(Integer payMoney, int userid,
			String payOpenVIP) {
		Map<String, String> retnMap = new HashMap<String, String>(); // 定义返回值
		try {
			// 1-微信充值维币，需要增加维币数量。2-微信充值开通会员，需要回调里开通会员；3-成为代理商，类似2
			logger.info("用户：" + userid + ".......【下单开始】.........下单类型："
					+ payOpenVIP + "...");
			// 订单流水头字母
			String payHeadr = null;
			if (payOpenVIP.equals("2")) {
				payHeadr = "KT"; // 充值开通会员。
			} else if (payOpenVIP.equals("3")) {
				payHeadr = "DL"; // 代理商
			} else {
				payHeadr = "CZ";
			}

			Date paydate = new Date();
			String orderno = SerialNumUtil
					.getOrderNo(userid, paydate, payHeadr); // 生成本地订单号。
			logger.info("用户：" + userid + "....订单创建...分配订单号..." + orderno);

			// 生成订单 支付日志。 状态。初始0-创建；1-更新 支付成功。2-支付失败。3-交易中【也可以不要】。
			logger.info("记入 本地交易流水表......");
			PayLog pLog = getPayLogInfo(userid, paydate, payHeadr, payMoney,
					orderno, 0);
			try {
				Integer payno = userPayLogMapper.payForMySelf(pLog);
				logger.info("记入 本地交易流水OK.....本地表ID：" + pLog.getPayid());
			} catch (Exception e) {
				logger.info("......记入订单流水失败................");
			}
			logger.info("......微信预支付    .........【下单结束】......................");

			retnMap.put("orderno", orderno);
			retnMap.put("paydate", DateUtil.getDate(paydate));
			retnMap.put("payno", pLog.getPayid() + "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retnMap;
	}

	/**
	 * 2、【确切的】【收到 微信服务器返回的结果】 后 。根据结果 ，做出相应的 选择性操作。
	 * 
	 * @Title detailWeCatReturn
	 * @author Wally
	 * @time 2016年1月11日下午1:24:25
	 * @Description 当微信服务器返回 充值结果后，调用这个方法，更新交易流水状态。微信支付交易流水号。以及其他操作
	 * @param orderNo
	 *            商户订单号
	 * @param retuncode
	 *            返回码
	 * @param returndec
	 *            返回信息描述
	 * @param transaction_id
	 *            微信支付订单号
	 * @return String
	 */
	@SuppressWarnings("unused")
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String detailWeCatReturn(Integer userid, String orderNo,
			String retuncode, String returndec, String transaction_id,
			Date paydate, Integer payMoney) throws Exception {

		// 根据返回的订单号。-是商户订单号。
		logger.info("....微信状态返回....." + retuncode + "-" + returndec);

		if (true) { // 这里是根据微信返回处理的。如果成功就是0-1状态；如果失败就是0-2状态。
			userPayLogMapper.updateOrderForUser(userid, orderNo,
					transaction_id, 0, 1);
			// 如果成功，就直接增加维币就是了。可以调用下面的方法：

			insertWBInfoByParam(userid, paydate, payMoney, "微信充值", orderNo);

			// System.out.println(Integer.valueOf("ssdfsf111"));
			// //测试事务回滚。注意，新插入的语句，事务没有结束时看不见的。
			logger.info("短信通知......接口......通知用户");

			logger.info(".....用户：" + userid + " 充值服务....结束..." + "成功");

		} else {
			userPayLogMapper.updateOrderForUser(userid, orderNo,
					transaction_id, 0, 2);
			logger.info(".....用户：" + userid + " 充值服务....结束..." + "-失败-"
					+ "..失败原因：" + returndec);
		}

		return null;
	}

	/**
	 * 3、依据 微信 返回结果的 【后续操作】--【这个可以重写，做个通用的】
	 * 
	 * @Title insertWBInfo
	 * @author Wally
	 * @time 2016年1月11日下午1:13:59
	 * @Description 插入维币记录
	 * @param
	 * @return Integer
	 */
	@Override
	public Integer insertWBInfoByParam(Integer userid, Date paydate,
			Integer payMoney, String businessType, String orderNo) {

		logger.info("...增加维币..开始....");
		Integer spweibi = wbInfoMapper.toSearchMyWb(userid);
		logger.info("...查询用户:" + userid + " 剩余维币=" + spweibi + "....");

		WeibiLog wblog = new WeibiLog();
		wblog.setCreatetime(paydate);
		wblog.setIncome(payMoney);
		wblog.setIntro(businessType);
		wblog.setUserId(userid);
		wblog.setOrderno(orderNo); // 充值记录要记录订单号，方便以后查询。 2016.1.11 Wally Add.
		// 如果没有查到维币，那么本次充值金额就是剩余维币数。如果查到了，就在剩余维币的基础上增加本次维币数量。
		wblog.setSpWeibi((null == spweibi ? payMoney : payMoney + spweibi));

		int insid = wbInfoMapper.instWbLogForUser(wblog);
		logger.info("...增加用户 :" + userid + " ...维币数量:" + payMoney + "....订单ID："
				+ orderNo + "...数据ID=" + insid);
		// 在这里继续，可以增加 积分 数据等。
		return insid;
	}

	/**
	 * 推广 维币 --才能提现。充值类型的维币不能提现。用户充值，本来还扣手续费，如果在能提现，我们岂不是赔本。
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void toTakeCashFromPop_SpWb(int intValue, Integer userId) {
		// 插入冻结流水。
		FreezeWeiBi fwBi = getFreezeWeiBi(userId, intValue, new Date(), null, 0);
		freezeLogMapper.instFreezeLogForUser(fwBi);
		// 插入。pop_consume 推广消费流水。
		PopConsume pc = new PopConsume();
		pc.setCreatetime(new Date());
		pc.setMoney(intValue);
		pc.setOpt("提现"); // 提现
		pc.setUserid(userId);
		pc.setOrderid(fwBi.getFreId());
		popConsumeMapper.insertSelective(pc);
	}

	/**
	 * 提现操作
	 * 
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String detailTiXian(Integer tixianMoney, Integer userid) {
		// 查询剩余维币
		Integer spweibi = wbInfoMapper.toSearchMyWb(userid);
		if (spweibi - tixianMoney < 0) { // 如果剩余维币，不足以满足提现维币数量
			return "0"; // 失败返回0
		}

		// 插入维币 冻结 log.
		WeibiLog wblog = new WeibiLog();
		wblog.setExpend(tixianMoney); // 设置消费 维币数量。冻结掉的
		wblog.setIntro("提现冻结");
		wblog.setUserId(userid);
		wblog.setCreatetime(new Date());
		wblog.setSpWeibi(spweibi - tixianMoney); // 剩余维币，执行减法。
		try {
			wbInfoMapper.instWbLogForUser(wblog);
			logger.info("..用户：" + userid + "...提现  维币 流水编号...."
					+ wblog.getWeibId());
		} catch (Exception e) {
			logger.error("....插入冻结维币数据.....错误....");
			e.printStackTrace();
		}

		try {
			FreezeWeiBi fwBi = getFreezeWeiBi(userid, tixianMoney, new Date(),
					wblog.getWeibId(), 0);
			freezeLogMapper.instFreezeLogForUser(fwBi);
			logger.info("..用户：" + userid + ".......冻结流水表编号....."
					+ fwBi.getFreId());
		} catch (Exception e) {
			logger.error("....创建冻结流水失败...............");
		}
		// 插入 冻结流水表。状态为 创建。工作人员扣款口，为已扣款。【需要管理台】

		return "1"; // 返回这样的值不好，明天修改下。

	}

	// 取得 冻结流水编号。
	public FreezeWeiBi getFreezeWeiBi(Integer userid, Integer tixianMoney,
			Date date, Integer weibNo, Integer status) {
		FreezeWeiBi fwb = new FreezeWeiBi();
		fwb.setCreatetime(date);
		fwb.setUserId(userid);
		fwb.setStatus(0); // 0-创建
		fwb.setFreMoney(tixianMoney);
		fwb.setWeibiId(weibNo);
		return fwb;
	}

	/**
	 * 下载账单
	 */
	@Transactional(readOnly = false)
	public int insertBill(Bill bill) {
		return userPayLogMapper.insertBill(bill);
	}

	@Transactional(readOnly = false)
	public int insertBillCount(Count count) {
		return userPayLogMapper.insertBillCount(count);
	}

	public int betchInsertBill(List<Bill> bills) {
		return userPayLogMapper.betchInsertBill(bills);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void openAgentForUserByWX(int userId, int money, int payYear,
			String spWeibi, String orderno, String transaction_id, int status,
			int agentPre) {
		UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userId);
		if (null == userInfo) {
			return;
		}
		try {
			userPayLogMapper.updateOrderForUser(userId, orderno,
					transaction_id, 0, status); // 从创建状态变为：1成功，或2失败
		} catch (Exception e) {
			logger.info("..........微信开通会员..充值流水交易状态 修改失败......");
		}

		Date datenow = new Date();
		Date vipEndDate = DateUtil.addYear(datenow, 100); // 代理到期日。目前是无限期。100年足够多了。12.16.
		try {
			userInfoMapper.updateUserAgentInfoByUserID(userId, 1, datenow,
					vipEndDate, agentPre); // 更新会员信息。 // 状态1=代理商标志位； 更新 上级代理ID。
		} catch (Exception e) {
			logger.error(".....开通代理商失败........." + "......会员....id+"
					+ userInfo.getMobile());
		}
		// 调用开通会员送积分 方法。【开通会员送 50 积分。】[需要管他是新开通还是 续费吗？]
		// insertOneCredits(userid,"开会员送维币",50,0);

		UserIncome uIncome = new UserIncome();
		uIncome.setCreatetime(new Date());
		uIncome.setFromuserid(userInfo.getUserId());
	/*	uIncome.setIncome(BaseDict.AGENT_NEEDPAY
				/ BaseDict.AGENT_COMMISSION_RATE); // 收入= 代理费*佣金比例（这里是整数所以用除法）
*/	
		uIncome.setIncome(10575); // 收入= 代理费*佣金比例（这里是整数所以用除法）
		uIncome.setTouserid(agentPre);
		uIncome.setType(2);
		try {
			userIncomeMapper.insertSelective(uIncome);
		} catch (Exception e) {
			// 失败仅仅记录。到时候根据log查询，手动处理吧。
			logger.error("........返佣金 失败........." + uIncome.getCreatetime()
					+ "....从用户：" + uIncome.getFromuserid() + "....到用户："
					+ uIncome.getTouserid());
		}

		try {
			// 发送短信，无论成功还是失败，只调用，不处理。
			String text = "【维书会】尊敬的：" + userInfo.getNickname() + ",您于"
					+ DateUtil.getDate(datenow) + "，使用手机号 "
					+ userInfo.getMobile() + " ，成功在维书会 升级为代理商！您自己的邀请码是："
					+ userInfo.getUserId() + "。感谢您的支持！ ";
			System.out.println(text);
			JavaSmsApi
					.sendSms(BaseDict.MSG_API_KEY, text, userInfo.getMobile());
		} catch (Exception e) {
			logger.error("用户：" + userId + "==短信调用发送失败....."
					+ DateUtil.getDate(datenow));
		}
	}

	/**
	 * 企业支付到  用户。
	 * 开启事务吧。
	 */
	@Override
	public Map<String, String> payToCustomer(PubData pubData) {
		Map<String, String> backData =  new HashMap<String, String>();
		try {
		//1、发送数据，并接受返回结果
			   backData = HttpSendUtil.SendToWecat(new PayToCust().getPayToCust(pubData),WxConfig.ENTERPRISEPAYMENT,pubData.getUserid());
		//2、返回结果处理。【如果执行失败，则不能返回空。成功返回空】
			  
			   if(null == backData){
				   backData =  new HashMap<String, String>();
				   backData.put("return_msg", "系统异常,请稍后再试");
				   return backData;
			   }
			   //通讯处理失败
			   if (StringUtils.equalsIgnoreCase("FAIL", backData.get("return_code"))) {
					System.out.println("================通讯失败======================");
					String text = "重要提醒： 用户ID为 "+ pubData.getUserid() +" 的娃子，提现失败喽！错误代码："+backData.get("return_code")+"， 错误描述："+backData.get("return_msg");
					JavaSmsApi.sendSms(BaseDict.MSG_API_KEY, text, BaseDict.MANAGERMOBILE);
					return backData;
				}
				//通讯成功，业务失败
				if (StringUtils.equalsIgnoreCase("FAIL", backData.get("result_code"))) {
					System.out.println("================通讯成功-业务失败=====================");
					String text = "重要提醒： 用户ID为 "+ pubData.getUserid() +" 的娃子，提现失败喽！错误代码："+backData.get("err_code")+"， 错误描述："+backData.get("err_code_des");
					JavaSmsApi.sendSms(BaseDict.MSG_API_KEY, text, BaseDict.MANAGERMOBILE);
					return backData;  
				}
				//数据正常返回,获取三个重要数据。
				String orderno = backData.get("partner_trade_no"); //我们的流水号。  pubData.getOrderNo(); 
				String wecatno = backData.get("payment_no"); //他们的流水号。
				String payTime = backData.get("payment_time"); //微信付款成功的时间。
				
				//保证不出现垃圾数据,如果没有这个值，一定返回失败了。
				if(StringUtils.isBlank(wecatno)){
					return backData;
				}
				
				//组装：提现表 数据。表还没建立。
				System.out.println("================插入消费流水========================");
				UserFreeze uf = new UserFreeze();
				uf.setAmount(pubData.getAmount());
				uf.setOrderno(orderno);
				uf.setPaytime(DateUtil.StringToDate(payTime));
				uf.setUserid(pubData.getUserid());
				uf.setWecatno(wecatno);
				//插入数据表。
				try {
					userFreezeMapper.insertSelective(uf);
				} catch (Exception e) {
					logger.error("插入 提现流水表失败........用户id"+pubData.getUserid()+"错误内容："+e.getMessage());
				}
				//调用短信通知。提现成功。
				String text ="提现通知：尊敬的用户，您于"+DateUtil.getDateDetail(new Date())+"成功提现剩余维币："+pubData.getAmount()+" 元。交易略有延迟，请留意自己的微信钱包！";
				System.out.println(text);
				JavaSmsApi.sendSms(BaseDict.MSG_API_KEY, text, pubData.getMobile()); 
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error("=========用户提现失败==系统异常============id="+pubData.getUserid()+"==提现金额"+pubData.getAmount()+"..."+e.getMessage());
		}
		return null;  //执行成功返回null就行了。事情都做完了。
	}

	public void earn(final Integer userId){
		doRunnableExecute(userId);
	}

}
