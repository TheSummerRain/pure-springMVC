package com.wsh.base.controller;

import com.wsh.base.constants.Cts;
import com.wsh.base.dao.CardDao;
import com.wsh.base.dict.BaseDict;
import com.wsh.base.model.*;
import com.wsh.base.service.IPayCoreService;
import com.wsh.base.service.impl.PayCoreServiceImpl;
import com.wsh.base.util.JavaSmsApi;
import com.wsh.base.util.Kit;
import com.wsh.base.util.RedisUtil;
import com.wsh.base.util.TokenProxy;
import com.wsh.base.util.dateUtil.DateUtil;
import com.wsh.base.wxpay.HttpUtil;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 代码有毒！年轻慎入 ~~~~
 * @author Wally
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	private static final Log logger = LogFactory.getLog(UserController.class);

	
	//=====================================began 【新开发区】===================================================
	/**
	 * @author Wally
	 * @time 2016年12月13日下午1:28:27
	 * @Description 测试服务是否正常。服务层
	 * @param
	 * @return String
	 */
	@RequestMapping("/ceshi")
	public String ceshi_service(Model model, HttpServletRequest request) {
		
		payCoreService.ceshi();
		
		

		return "";
	}
	
	
	
	
	
	
	
	
	//====================================end 【新开发区】=============================================
	
	
	
	/**
	 * 
	 * @author Wally
	 * @time 2016年1月7日下午10:39:11
	 * @Description 跳转会员中心
	 * @param
	 * @return String
	 * @throws Exception 
	 */
	@RequestMapping("/toUserCenterPage")
	public String toUserCenterPage(Model model, HttpServletRequest request) {

		NewUserInfo uif = null;
		try {
			 uif=getNowUserInfo(request);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("----到用户首页-失败---");
			return BaseDict.PAGE_ERROR_JSP;
		}
		
		//为了保证状态，这里去查询最新的用户数据。
		
		if (null != uif) { 
			model.addAttribute("isMaster",true);  //从此以后都是推广大使。默认的。
			
			model.addAttribute("user", uif); // 传递user对象。
			
			if (null != uif.getVipendtime()) {
				if (DateUtil.getIntervalDaysExtend(uif.getVipendtime(), new Date()) <= 30) {
					model.addAttribute("vipOutDate", true);
					
					//到期，更新用户vip等级 = 0
					if(DateUtil.getIntervalDaysExtend(uif.getVipendtime(), new Date()) <= 0){ 
						if(uif.getViplv() > 0){  //=0就不用管了。
							try {
								NewUserInfo newuf = new NewUserInfo();
								newuf.setViplv(0);
								newuf.setId(uif.getId());
								userInfoService.updateUserVipStatus(newuf);
							} catch (Exception e) {
								logger.error("更新用户vip等级失败："+uif.getId()+".时间："+DateUtil.getDate(new Date()));
							}
						}
					}
					
				} else {
					model.addAttribute("vipOutDate", false);
				}
			}
			
			/*//剩余积分。【不用了】
			Integer credits = userCreditService.toQueryUserSuCredits(uif.getId());*/
			model.addAttribute("myIntegral", 0);
			
			//剩余维币。【使用小吴接口，调用钱包数据】【不在用剩余维币，而是总维币】
			model.addAttribute("weibi",getCanWithdrawMoney(uif.getId()));
			
		}else{
			logger.error("..【error】..to user center page ,but session is null ..................."); 
			return BaseDict.PAGE_ERROR_JSP;	
		}
		return "/user/user_center";
	}

	
	
	
	// 2016.5.30 作废处理。by Wally.
	//【旧版】：总维币剩余 = 充值类型维币剩余 （作废，暂时不提供充值）  + 【推广类型维币剩余(推广总收入-推广总支出=推广类型总剩余)】 + 其他类型的维币 收支(代理商)。
	public int getALLSp_Wb(Integer userid){
		
		//充值类型的维币剩余。
		//Integer weibiNm = userWBService.toSearchMyWb(userid);
		//推广总收入。
		int sumpopwb =  countPopAll_WB(userid);
		//推广总支出
		Integer popconsume = userWBService.toSearchMy_PopWb(userid);
		//其他类型收支数据。如果有继续往后写。
		//代理商收入
		Integer agentIncome = userIncomeService.getAllIncome_byToUserID(userid);
		return (sumpopwb - popconsume) + agentIncome; /* +weibiNm */
	}
	
	
	//查询 推广剩余维币。  2016.5.30 作废处理。by Wally.
	public int getPopSp_Wb(Integer userid){
		//【推广类型维币剩余(推广总收入-推广总支出=推广类型总剩余)】 
		//推广总收入。
		int sumpopwb =  countPopAll_WB(userid);
		//推广总支出
		Integer popconsume = userWBService.toSearchMy_PopWb(userid);
		//其他类型收支数据。如果有继续往后写。
		return  (sumpopwb - (popconsume == null ?0:popconsume));
	}
	
	
	/**
	 * @author Wally
	 * @time 2016年1月9日下午5:21:50
	 * @Description 查询我的维币
	 * @return String
	 */
	@RequestMapping("/toSearchMyWb")
	public String toSearchMyWb(Model model, HttpServletRequest request) {
		UserInfo uInfo = getUser(request);

		//总维币剩余 = 充值类型维币剩余   + 【推广类型维币剩余(推广总收入-推广总支出=推广类型总剩余)】 + 其他类型的维币 收支。
		// 充值类型的维币剩余。
		Integer mywb = userWBService.toSearchMyWb(uInfo.getUserId());
		//推广总收入。
		int sumpopwb =  countPopAll_WB(uInfo.getUserId());
		//推广总支出
		Integer popconsume = userWBService.toSearchMy_PopWb(uInfo.getUserId());
		model.addAttribute("mywb", mywb.intValue() + (sumpopwb - popconsume.intValue())); // 维币余额

		// 查询维币交易流水。
		List<WeibiLog> liwibi = userWBService.getWBLogInfoByUserId(uInfo.getUserId());
		model.addAttribute("weibilog", liwibi);
		
		//-----临时-组装我的推广 剩余维币------------------------------------
		model.addAttribute("sumpopwb", sumpopwb); //推广总维币
		model.addAttribute("popconsume", popconsume == null?0:popconsume);//推广总支出
		model.addAttribute("nowdate", new Date());
		
		return "/user/myWeiBi";
		
	}
	
	// --------------------------------------------------------------------------------
	/**
	 * @Title 开通会员。
	 * @author Wally
	 * @time 2016年1月8日上午11:31:00
	 * @Description 开通会员
	 * @return String
	 */
	@RequestMapping("/openMember")
	public String openMember(Model model, HttpServletRequest request) {
		return "/user/openMember";
	}

	/**
	 * 
	 * @Title toOpenVip
	 * @author Wally
	 * @time 2016年1月8日下午4:31:07
	 * @Description 开通VIP --调用微信支付。
	 * @param
	 *            -用户ID；payMoney-应支付金额;payYear-会员年限
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/toOpenVip", method = RequestMethod.POST)
	public void toOpenVip(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String payMoney = request.getParameter("payMoney");
		String payYear = request.getParameter("payYear");
		String payType = request.getParameter("payType");

		UserInfo uf = findUser(getUser(request).getUserId()) ;
		
		logger.info("..........开通会员...........................支付方式："+payType+"....用户ID："+uf.getUserId());
		
		if(null != uf.getMobile() && !"".equals(uf.getMobile())){ //手机号有存在。

			// 二、维币余额支付。
			if (payType.equals(BaseDict.SP_WEIBI)) {
				Integer surplusWB = userWBService.toSearchMyWb(uf.getUserId());
			
				if (surplusWB < Integer.valueOf(payMoney)) {
					// 维币不足
					response.getWriter().write("2");
				} else {
					try {
						// 为用户UserId ，开启会员身份。
						payCoreService.openVIPForUserByWB(uf.getUserId(),
								Integer.valueOf(payMoney),
								Integer.valueOf(payYear), payType,
								uf.getMobile()); // 假设。这里的手机号，请从session里获取。不用校验null.方法最初有判断。
					} catch (Exception e) {
						e.printStackTrace();
					}

					logger.info(DateUtil.getDate(new Date()) + "，成功为用户："
							+ uf.getUserId() + " 开通会员权限。会员ID：" + uf.getMobile());
					response.getWriter().write("3");
				}
				response.getWriter().flush();
			}
		} else {
			logger.info(".........开通会员前请先绑定手机号.....................");
			response.getWriter().write("1"); // 没有绑定手机
			response.getWriter().flush();
		}
	}

	@RequestMapping("/toVipSucPage")
	public ModelAndView toVipSucPage(HttpServletRequest request) {
		ModelAndView mv=new ModelAndView("/xpage/open_VIP_suc");
		mv.addObject("flag",request.getParameter("p")==null ? "" : request.getParameter("p"));
		return mv;
	}

	/**
	 * 
	 * @Title toSearchMyCredits
	 * @author Wally
	 * @time 2016年1月9日下午1:26:11
	 * @Description 查询我的积分明细 流水。
	 * @param
	 * @return String
	 */
	@RequestMapping("/toSearchMyCredits")
	public String toSearchMyCredits(Model model, HttpServletRequest request) {

		UserInfo uifInfo = getUser(request);
		Integer suCredits = userCreditService.toQueryUserSuCredits(uifInfo.getUserId());
		List<CreditLog> lcidLogs = userCreditService
				.getUserCreditLogByUserID(uifInfo.getUserId());
		model.addAttribute("suCredits", suCredits); // 剩余积分。
		model.addAttribute("creditLog", lcidLogs); // 积分详情。
		return "/user/myCredits";
	}

	/**
	 * @Title toMyWBPage
	 * @author Wally
	 * @time 2016年1月9日下午5:26:21
	 * @Description 跳转到我的维币界面。
	 * @param
	 * @return String
	 */
	@RequestMapping("/toMyWBPage")
	public String toMyWBPage(Model model, HttpServletRequest request) {
		return "/pay/weiBiManager"; // 跳转到我的维币界面。
	}
	// to充值界面
	@RequestMapping("/toPayPage")
	public String toPayForUserPage(Model model, HttpServletRequest request) {
		model.addAttribute("payOpenVIP","2"); //代表开通会员的充值方式。
		return "/pay/payForSelf";
	}
	
	/**
	 * @author Wally
	 * @time 2016年1月9日下午11:28:58
	 * @Description 给自己充值
	 * @param
	 * @return String
	 * @throws IOException 
	 */
	@RequestMapping(value="/doOrder",method=RequestMethod.POST)
	public void doPayForSelfExt(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String transmoney = request.getParameter("transmoney");
		String payOpenVIP = request.getParameter("payOpenVIP"); //0-微信充值维币 下单。1-微信充值开通会员 下单 。3 = 成为代理商下单。
		Map<String, String> maps = null; // 承载返回值的map.
		UserInfo user = findUser(getUser(request).getUserId());
		if (user == null || StringUtils.isBlank(transmoney)
				|| StringUtils.isBlank(user.getOpenid())) {
			return;
		}
		//为了前端特殊处理的ID。
		String userString = null;
		if(payOpenVIP.equals("2")){  //充值开通会员，必须先开通手机号。
			if(null != user.getMobile() && !"".endsWith(user.getMobile()) ){ //开通会员的充值才需要。
				userString="2"; //代表是走的微信 充值 开通会员。
			}else{
				response.getWriter().write("{'isMobile':'N'}");
				response.getWriter().flush();
				return;
			}
		}else if( payOpenVIP.equals("3") ){  //如果显示成为代理商，肯定已经是会员，也肯定已经有手机号了。所以没必要在判断
			userString="3"; // 成为代理商
		}else {
			userString="1"; //代表纯粹的充值服务。
		}
			
		try {
				maps = payCoreService.order(Integer.parseInt(transmoney),user.getUserId(),payOpenVIP);
				response.getWriter().write("{'fee':"+transmoney+",'isMobile':'Y'"+",'time_start':"+DateUtil.getDateOrder()+",'userId':"+user.getUserId()+",'suffixType':"+userString+",'orderNo':'" + maps.get("orderno") + "','openid':'"+ user.getOpenid() + "'}");

				response.getWriter().flush();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("内部服务器下单失败" + user.getUserId() + ":"
						+ Integer.parseInt(transmoney) + ":" + new Date());
			}
	
		
	}

	/**
	 * @author Wally
	 * @time 2016年1月10日下午3:19:59
	 * @Description 跳转提现 界面
	 * @param
	 * @return String
	 */
	@RequestMapping("/toTakeCash")
	public String toTakeCash(Model model, HttpServletRequest request) {
		UserInfo uif =null;
		try {
			uif = findUser(getUser(request).getUserId());
		} catch (Exception e) {
			return BaseDict.PAGE_ERROR_JSP;	
		}
		
		if (null != uif.getBankNo() && !"".equals(uif.getBankNo())) {
			String bankinfo = BaseDict.BANKMAP.get(uif.getBanktype());
			model.addAttribute(
					"bankType",
					bankinfo.substring(0, bankinfo.length() - 1)
							+ "-尾号:"
							+ uif.getBankNo().substring(
									uif.getBankNo().length() - 4) + ")");
			
			//推广总收入。
			int sumpopwb =  countPopAll_WB(uif.getUserId());
			//推广总支出
			Integer popconsume = userWBService.toSearchMy_PopWb(uif.getUserId());
			logger.info("推广总收入："+sumpopwb+"-------推广总消费："+popconsume); 
			model.addAttribute("spWeiBi",sumpopwb - (popconsume == null ?0:popconsume) ); // 没有数据，维币就是0
			model.addAttribute("canMinToCash", BaseDict.TO_CASH_MONEY_MIN);  //允许的最小提现金额。
			
			return "/pay/wbcash";
		} else {
			return "forward:/user/toUpdateBankInfo";// 如果还没绑卡，就绑卡去
		}

	}
	
	
	

	/**
	 * 
	 * @author Wally
	 * @time 2016年1月10日下午10:19:34
	 * @Description TODO
	 * @param
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/toTixian", method = RequestMethod.POST)
	public void toTixian(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String takeMoney = request.getParameter("subvalue");
		UserInfo uf = getUser(request);
		if(null == uf){
			response.getWriter().write("0"); //超时，请刷新。
			response.getWriter().flush();
			return ;
		}
		
		logger.info("....提现开始....userid=" + uf.getUserId() + "....提现金额="
				+ takeMoney);
		//提现金额不为空。
		if(null != takeMoney && !"".equals(takeMoney)){
			
			int sppop = getPopSp_Wb(uf.getUserId());
			//校验，金额够不够。
			if(sppop - Integer.valueOf(takeMoney).intValue() < 0 ){ //剩余维币 不足
				response.getWriter().write("3"); //维币不足
				response.getWriter().flush();
				return ;
			}else{
				try {
					payCoreService.toTakeCashFromPop_SpWb(Integer.valueOf(takeMoney).intValue(),uf.getUserId());   //提现目前只针对：推广维币的提现。
					response.getWriter().write("1"); //申请成功。
					response.getWriter().flush();
					return ;
				} catch (Exception e) {
					e.printStackTrace();
					response.getWriter().write("0"); //系统异常
					response.getWriter().flush();
					return ;
				}
			}
			
		}else {
			response.getWriter().write("2"); //金额有误
			response.getWriter().flush();
			return ;	
		}
	}

	/**
	 * @Title toModiyUserInfo
	 * @author Wally
	 * @time 2016年1月13日下午3:36:51
	 * @Description 编辑用户个人信息。
	 * @param
	 * @return String
	 */
	@RequestMapping("/toModiyUserInfo")
	public String toModiyUserInfo(Model model, HttpServletRequest request) {
		return null;
	}

	/**
	 * 
	 * @Title toUserInfoPage
	 * @author Wally
	 * @time 2016年1月13日下午3:42:38
	 * @Description 跳转 编辑 用户界面。
	 * @param
	 * @return String
	 */
	@RequestMapping("/touserinfopage")
	public String toUserInfoPage(Model model, HttpServletRequest request){
		
		logger.info(".........................【【【【【【toUserInfoPage】】】】】】】】.....................");
		Integer userid=getUser(request).getUserId();
		if(null == userid){
			logger.error(" 返回 404............");
			return "404";
		}
		
		UserInfo uif=userInfoService.getUserInfoByUserID(userid);
	
		if(null == uif.getBirthdate()){
			model.addAttribute("age",0); 
		}else {
			int age = DateUtil.getYear(new Date())-DateUtil.getYear(uif.getBirthdate()); //当前日期 - 生日字段 = 年龄。
			model.addAttribute("age",age);
		}
		
		
		if(null != uif.getIsMaster() && !"".equals(uif.getIsMaster()) && uif.getIsMaster()){
			model.addAttribute("isMaster",true);
		}else{
			model.addAttribute("isMaster",false); //非推广大使，不显示 绑卡信息。
		}
		
		
		if(null != uif.getBankNo() && !"".equals(uif.getBankNo())){
			model.addAttribute("isBank",true);//绑定，不为空。
		}else{
			model.addAttribute("isBank",false); //没有绑定。
		}

		if (null != uif.getAcceptName() && !"".equals(uif.getAcceptName())) {
			model.addAttribute("isAccept", true);
		} else {
			model.addAttribute("isAccept", false); // 已经绑定 收货信息。
		}

		logger.info(".......... mobile========【" + uif.getMobile() + "】......");
		if (null != uif.getMobile() && !"".equals(uif.getMobile())) {
			model.addAttribute(
					"mobile",
					uif.getMobile().substring(0, 3)
							+ "****"
							+ uif.getMobile().substring(
									uif.getMobile().length() - 4));
			model.addAttribute("isMobile", true); // 绑定手机
			logger.info(".........................toUserInfoPage........isMobile....true.........");
		} else {
			model.addAttribute("isMobile", false);
			logger.info(".........................toUserInfoPage......isMobile...false............");
		}
		return "/user/user_info";
	}

	@RequestMapping("/toBankInfoPage")
	public String toBankInfoPage(Model model, HttpServletRequest request){
		UserInfo uf = null;
		try {
			 uf=findUser(getUser(request).getUserId());
		} catch (Exception e) {
			return BaseDict.PAGE_ERROR_JSP;
		}
		
		model.addAttribute("bankUserName",uf.getUserName());
		String bankNo = uf.getBankNo(); //请从session获取
		
		if(null != bankNo && !"".equals(bankNo)){
			model.addAttribute("bankNo",bankNo.substring(0,4)+" **** **** "+ bankNo.substring(bankNo.length()-4));	
		}
		model.addAttribute("bankType", BaseDict.BANKMAP.get(uf.getBanktype()));
		model.addAttribute("subBank", uf.getSubBank());
		return "/user/lookBankInfo";
	}
	/**
	 * @Title toUpdateBankInfo
	 * @author Wally
	 * @time 2016年1月13日下午7:09:54
	 * @Description 跳转银行绑卡信息界面。
	 * @return String
	 */
	@RequestMapping("/toUpdateBankInfo")
	public String toUpdateBankInfo(Model model, HttpServletRequest request) {
		return "/user/bindbank";
	}

	/**
	 * @Title updateBankInfo
	 * @author Wally
	 * @time 2016年1月13日下午7:07:27
	 * @Description 修改银行卡信息。
	 * @param
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping("/updateBankInfo")
	public void updateBankInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String name = request.getParameter("username");
		String bankno = request.getParameter("bankno");
		String banktype = request.getParameter("banktype");
		String subbank = request.getParameter("subbank");
		UserInfo ufi = getUser(request);
		try {
			userInfoService.updateBankInfoForUser(ufi.getUserId(), bankno,
					banktype, name, subbank);
			response.getWriter().write("1");
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("0");
		}
		response.getWriter().flush();
	}

	/**
	 * @Title toAcceptInfoPage
	 * @author Wally
	 * @time 2016年1月14日上午11:49:26
	 * @Description 查看 地址信息page.
	 * @param
	 * @return String
	 */
	@RequestMapping("/toAcceptInfoPage")
	public String toAcceptInfoPage(HttpServletRequest request) {
		return "/user/addrPage";
	}

	/**
	 * @Title toUpdateAcceptPage
	 * @author Wally
	 * @time 2016年1月14日下午12:01:12
	 * @Description 跳转更新界面。
	 * @param
	 * @return String
	 */
	@RequestMapping("/toupdateAcceptPage")
	public String toUpdateAcceptPage() {
		return "/user/updateAddr";
	}

	/**
	 * @Title updateAcceptInfo
	 * @author Wally
	 * @time 2016年1月14日上午11:56:40
	 * @Description 更新 收货信息。
	 * @param
	 * @return String
	 */
	@RequestMapping(value="/updateAcceptInfo",method=RequestMethod.POST)
	public String updateAcceptInfo(Model model,HttpServletRequest request ,UserInfo uf){
		UserInfo uif = getUser(request); //获取session的userid.
		try {
			userInfoService.updateAcceptAddrForUser(uif.getUserId(),
					uf.getAcceptName(), uf.getPreAddr(), uf.getAddr(),
					uf.getPostcode());
			model.addAttribute("resTitle", "成功");
			model.addAttribute("simpleIntro", "收货信息保存成功");
			model.addAttribute("toWhatPage", request.getContextPath()
					+ "/user/touserinfopage");
			return "/xpage/success";
		} catch (Exception e) {
			logger.error(".......用户：" + uf.getUserId() + ".修改 收货信息失败......");
			model.addAttribute("resTitle", "失败");
			model.addAttribute("simpleIntro", "保存失败，请确保上传的值正确。");
			model.addAttribute("toWhatPage", request.getContextPath()
					+ "/user/touserinfopage");
			return "/xpage/error";
		}

	}

//----------------------------更换手机---------------------------
	//请求更换手机号。

	@RequestMapping("/toupdatmobipage")
	public String toUpdateMobile(Model model,HttpServletRequest request) {
		
		
		UserInfo uInfo = getUser(request);
		UserInfo ufi=null;
		if(null != uInfo){
			ufi = findUser(uInfo.getUserId());  //需要pid所以不能用缓存。
		}
		if(null != ufi && null != ufi.getpId() && !"".equals(ufi.getpId())){
			model.addAttribute("showYaoQm", false);
		}else{
			if(null != ufi.getMobile() && !"".equals(ufi.getMobile())){ //如果不为空，肯定是编资料或者换号。
				model.addAttribute("showYaoQm", false); 
			}else{ 
				model.addAttribute("showYaoQm", true); //如果为空，肯定是新人嘛。
			}
		}
	
		String comepage = request.getParameter("comepage");
		model.addAttribute("comepage", comepage);  // comepage=1，更换手机。//应该叫做frompage.
		return "/user/bindPhone"; // 到手机 绑定界面。
	}

	/**
	 * 
	 * @Title toSendMsg
	 * @author Wally
	 * @time 2016年1月13日上午11:26:42
	 * @Description 生成短信验证码，发送短信。
	 * @param
	 * @return void
	 */
	@RequestMapping("/tosendmsg")
	public void toSendMsg(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String mobileString = request.getParameter("phone");
		String romdString = Kit.generateRandomCode();
		
		Jedis jedis = RedisUtil.getJedis();
		jedis.set("WSHM"+mobileString, romdString);
		jedis.expire("WSHM"+mobileString, 3 * 60); // 3分钟失效。
		RedisUtil.returnResource(jedis);
		String Text = "【维书会】您的验证码是"+romdString+"。如非本人操作，请忽略本短信";
		//System.out.println(Text);
		try {
			JavaSmsApi.sendSms(MSG_API_KEY, Text, mobileString);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("短信发送失败......" + mobileString);
		}
		
		  response.setCharacterEncoding("utf-8");
		  response.getWriter().write("1"); //成功
		  response.getWriter().flush();

	}

	/**
	 * @Title tocheckYzm
	 * @author Wally
	 * @time 2016年1月13日下午2:20:00
	 * @Description 验证手机验证码，绑定手机号。
	 * @param
	 * @return void
	 */
	@RequestMapping("/tocheckYzm/{yanzma}/{mobile}")
	public void tocheckYzm(Model model, HttpServletRequest request,
			HttpServletResponse response, @PathVariable String yanzma,
			@PathVariable String mobile) throws IOException {
		response.setCharacterEncoding("utf-8");
		
		UserInfo uInfo = getUser(request);
		UserInfo ufi=null;
		if(null != uInfo){
			ufi = findUser(uInfo.getUserId());  //需要pid所以不能用缓存。
		}
		
		Jedis jedis = RedisUtil.getJedis();
		String redisYzm = jedis.get("WSHM"+mobile);
		RedisUtil.returnResource(jedis); //关闭
		
		if(null == redisYzm){ // redis值过期否
			response.getWriter().write("0"); //返回失败
		}else{
			if(redisYzm.equals(yanzma)){
			//执行绑定操作。
				logger.info("校验通过.....");
				OldVipTmp ovt =	userInfoService.selectByMobile(mobile);
				if(null != ovt){ //老会员信息存在。//老会员不能有PID。所以邀请码不能使用。
					try{
						userInfoService.importOldVipInfo(ufi.getUserId(),ovt);
						setUser(userInfoService.getUserInfoByUserID(ufi.getUserId()),request); // PIDsession缓存的问题。
						response.getWriter().write("2"); //是老会员，已经自动开通VIP。
						//清除关系
					}catch(Exception e){
						logger.error("更新老会员信息失败.........");
						response.getWriter().write("0"); //返回失败
					}
					
				}else{ //没有老会员信息。正常绑定
					if(null != ufi.getpId()){  //1、pid已经存在，不能使用邀请码。
						userInfoService.updateMobileInfoForUser(ufi.getUserId(),mobile);
					}else{ //2、pid不存在,可以使用邀请码。
						Integer yaoqm = Integer.valueOf(request.getParameter("yaoqm"));
						if(null != yaoqm){
							Integer	isalive = userInfoService.checkIsLiveByUserid(yaoqm); 
							if(isalive == 1){//2.1、邀请码id 存在，且这个id已经是vip. 直接使用用户输入的邀请码
							}else{//2.2、邀请码id 不存在，判断是否是映射数字。
								if(yaoqm.equals(BaseDict.DISGUISERNUM)){  //2.3、如果映射6个8，就映射955id.
									yaoqm = BaseDict.DISGUISERWHO;  
								}else{ //2.4、所有不存在的邀请码，都映射部门id。  //如果这里不填，将来人家还可以用二维码扫描添加进去。
									yaoqm = null;   //随便写个值测试下。后面再改吧。这个“阻止用户胡乱输入”的问题必须解决。
								}
							}
							userInfoService.updateMobileInfoForUser(ufi.getUserId(),mobile,yaoqm);
						}//2.5、如果用户邀请码没有值，啥也不做。【不可能的情况】
					}
					response.getWriter().write("1");
				}
			} else {
				response.getWriter().write("0");
			}
		}
		response.getWriter().flush();
		response.getWriter().close(); // 容器会自动关闭吧。
	}

	/**
	 * 
	 * @author Wally
	 * @time 2016年1月14日下午10:37:05
	 * @Description ajax 异步 请求是否绑定手机号。
	 * @param
	 * @return void
	 * @throws IOException 
	 */
	@RequestMapping("/hasBindMobile")
	public void isBindMobile(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		String mobile = request.getParameter("mobile");
		if(null != mobile && !"".equals(mobile)){
			int sum  = userInfoService.hasBindMobile(mobile);
			if(sum==0){
				response.getWriter().write("1"); //手机号不存在，可以绑定，返回true; 
			}else{
				response.getWriter().write("2"); //手机号已经存在。
			}
		}else{
			response.getWriter().write("0"); //“获取手机号失败”手机号为空。
		}
		response.getWriter().flush();
	}

	@RequestMapping("/checkUsermobile")
	public void checkUsermobile(HttpServletRequest request,HttpServletResponse response){
		UserInfo  uf =null;
		try {
			uf = findUser(getUser(request).getUserId());
			if(null == uf){
				response.getWriter().write("0"); //异常。
			}
			if(null != uf.getMobile() && !"".equals(uf.getMobile())){ //已经绑定手机号。
				response.getWriter().write("2"); //手机号已经存在。
			}else{
				response.getWriter().write("1"); //手机号不存在，需要绑定手机号。
			}
			
		} catch (Exception e) {
			logger.error("session is error,please refres..");
			try {
				response.getWriter().write("0");
			} catch (IOException e1) {
			}
		}
	}
	
	
	
	
	@RequestMapping("/tosuccess")
	public String tosuccess(Model model, HttpServletRequest request) {
		model.addAttribute("resTitle", "成功");
		model.addAttribute("simpleIntro", "恭喜您充值成功");
		model.addAttribute("toWhatPage", request.getContextPath()
				+ "/user/toUserCenterPage");
		return "/xpage/success";
	}

	/**
	 * 
	 * @Title toUpdateBithday
	 * @author Wally
	 * @time 2016年1月16日下午2:37:34
	 * @Description 异步修改生日。
	 * @param
	 * @return void
	 */
	@RequestMapping("/toUpdateBithday")
	public void toUpdateBithday(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String bitday = request.getParameter("date");
		userInfoService.toUpdateBithday(getUser(request).getUserId(),
				DateUtil.getDate(bitday));
		response.getWriter().write(1);
		response.getWriter().flush();
		response.getWriter().close(); // 容器会自动关闭吧。
	}
	
	
	//我的收藏。
	@RequestMapping("/toMyCollectPage")
	public String toMyCollectPage(HttpServletRequest request,Model model){
		
		UserInfo ufInfo = getUser(request);
		List<ArtCollection> listArtCon = artconllectionService.findArtCollecByUserId(ufInfo.getUserId());
		model.addAttribute("listart",listArtCon);
		model.addAttribute("baseCallPath", BaseDict.EXP_BASEPATH_METHOD+BaseDict.BASECALLPATH_METHOD); //基础链接路径。
		model.addAttribute("labetype",BaseDict.ART_TYPE);
		return "/user/myCollect";
	}
	
	
	//show me protocol
	@RequestMapping("/showMeProtocol")
	public String showMeProtocol(Model model,HttpServletRequest request) {
		
		String comepage= request.getParameter("comepage");
		
		if(null != comepage && comepage.equals("1")){ //绑定手机
			model.addAttribute("fromPage", "/user/toupdatmobipage?comepage=1");
		}else if(null != comepage && comepage.equals("2")){ //开通会员
			model.addAttribute("fromPage", "/user/toupdatmobipage?comepage=2");
		}else if(null != comepage && comepage.equals("4")){
			model.addAttribute("fromPage", "/user/card");
		}else{  //开通会员界面  3.
			model.addAttribute("fromPage", "/user/openMember");
		}
		
		return "/user/protocol";
	}
	
	
	//===================================================================================================
	//老数数据转移
	
	//注意：在使用前，必须保证 微信端不在产生交易数据，或者微信端交易数据 算法也从新修正。向上踩点算法。（不在是以往的向下查询算法）
	//操作路径：http://localhost:9099/user/getEveryOneMoney
	//生成txt文件。对照：mywallet表对号入座。其他类似的：类型都是统一更新的。cmType=3；创建时间统一弄一次吧。或者通过查询 userid,vipstattime来一次 批量update.
	//执行：update mywallet set cmType  = 3;
	//执行：update mywallet mt set mt.createTime = (select uf.vipstarttime from userinfo uf where uf.id =  mt.fromUserId);批量更新日期。
	// 把代理商数据手动导入。
	// 吧提现数据，导入表。
	
	/**
	 * 生命周期：【仅限，数据转移，之后就没用了】【启用：12.14】
	 * @Title 程序办法导入数据。
	 * @author Wally
	 * @time 2016年12月14日上午11:16:11
	 * @Description 1、首先建立 ：mywallet表。构建模型基础。
	 * 				2、然后用上getEveryOneMoney()方法。把用户应得钱打入用户钱包。
	 * 				3、最后把表复制到线上库。
	 * 				4、直接使用钱包数据，就是用户的收入。
	 * 			        【5、注意：这里只能算：推广收入。提现的数据还需要导入，还有代理商的管理费收入数据也需要导入】
	 * @param
	 * @return void
	 */
	@RequestMapping("/newgetEveryOneMoney")
	public void getEveryOneMoney_new(Model model ,HttpServletRequest request){
		//开始构建数据结构。
		//这里只计算了  推广收入的50块钱。还需要 把 提现记录数据 和管理费数据 手动弄到 钱包表。
		List<UserInfo> uList = userInfoService.getAllUserInfo();
		int erro = 0; //失败次数
		for (UserInfo uf : uList) {
			if(uf.getpId() != null && uf.getVipId() !=null){
				//给直接上级返钱；                                                                                                                                                         
				try {
					userInfoService.insertMyWallet(getObjectMyWallet(uf.getUserId(),uf.getpId(),"一级好友:"+uf.getNickname().replace(",", "").replace("💯", "")+"成功开通会员",uf.getVipStartdate()));	
				} catch (Exception e) {
					
					System.out.println("INSERT into mywallet(fromUserId,toUserId,cmType,money,createTime,sourceDes) values ("+uf.getUserId()+","+uf.getpId()+",2,5000,'"+DateUtil.getDateDetail(uf.getVipStartdate())+"','"+"一级好友:"+uf.getNickname().replace(",", "")+"成功开通会员"+"');" );  //给直属上层返钱。
					erro++;
				}
				UserInfo uftmp = userInfoService.getUserInfoByUserID(uf.getpId()); //查询上面第二层;//可以用变量，速度应该会更快
				if(uftmp != null && uftmp.getpId() != null){  //不为空，说明还有第三层。
					//1、给二级返钱。
					try {
						userInfoService.insertMyWallet(getObjectMyWallet(uf.getUserId(),uftmp.getpId(),"二级好友:"+uf.getNickname().replace(",", "")+"成功开通会员",uf.getVipStartdate()));	
					} catch (Exception e) {
						System.out.println("INSERT into mywallet(fromUserId,toUserId,cmType,money,createTime,sourceDes) values ("+uf.getUserId()+","+uftmp.getpId()+",2,5000,'"+DateUtil.getDateDetail(uf.getVipStartdate())+"','"+"二级好友:"+uf.getNickname().replace(",", "")+"成功开通会员"+"');" );  //给直属上层返钱。
						erro++;
					}
					//2、继续查询。
					UserInfo uftmp3 = userInfoService.getUserInfoByUserID(uftmp.getpId());
					if(uftmp3 !=null && uftmp3.getpId() != null){
						//给3级返钱
						try {
							userInfoService.insertMyWallet(getObjectMyWallet(uf.getUserId(),uftmp3.getpId(),"三级好友:"+uf.getNickname().replace(",", "")+"成功开通会员",uf.getVipStartdate()));	
						} catch (Exception e) {
							System.out.println("INSERT into mywallet(fromUserId,toUserId,cmType,money,createTime,sourceDes) values ("+uf.getUserId()+","+uftmp3.getpId()+",2,5000,'"+DateUtil.getDateDetail(uf.getVipStartdate())+"','"+"三级好友:"+uf.getNickname().replace(",", "")+"点亮VIP"+"');" );  //给直属上层返钱。
							erro++;
						}
					}
				}
			}
		}
		//错误条数都是因为 特殊字符，用编辑器统一处理下，insert表里完事。
		System.out.println("==========一共错误条数："+erro +"  ===============================");
		
		//测试数据是否正确。
		//算算总账对不对。条数和sql查询的总结果对照  ；用下面的sql去计算。看看数据是否正确
//		select COUNT(1) from user_info where vip_id is not null and  pId=227;
//		select COUNT(1) from user_info where vip_id is not null and pId in (select user_id from user_info where  pId=227);
//		select COUNT(1) from user_info where vip_id is not null and pId in (select user_id from user_info where pId in (select user_id from user_info where pId=227));
	}
	

	//构建对象。
	private MyWallet getObjectMyWallet(Integer fromuserId,Integer toUserID,String detail,Date starDate) {
		MyWallet mw =  new MyWallet();
		mw.setMoney(5000);
		mw.setCmtype("2");
		mw.setFromuserid(fromuserId);
		mw.setTouserid(toUserID);
		mw.setSourcedes(detail);
		mw.setCreatetime(starDate);//vip startDate
		
		return mw;
	}
	
	//'1-管理费；2-佣金；3-推广；4-课件或者PPT；5-其他[如果是收入操作，必填]',
	private MyWallet getObjectMyWallet(Integer fromuserid, Integer touserid,
			String detail, Date createtime, int moeny, String cmType) {
		MyWallet mw =  new MyWallet();
		mw.setMoney(moeny);  //统统是分标示，记得转换。
		mw.setCmtype(cmType);
		mw.setFromuserid(fromuserid);
		mw.setTouserid(touserid);
		mw.setSourcedes(detail);
		mw.setCreatetime(createtime);//vip startDate
		return mw;
	}
	//转移：管理费数据到用户钱包。 12.14
	@RequestMapping("/moveManagerMoney")
	public void moveManagerMoney(Model model ,HttpServletRequest request){
		
		List<UserIncome> uIn = userIncomeService.getAllUserIncomeInfo();
		for(UserIncome uf:uIn){
			try {
				//为了防止多次插入，最好在这里再查下一次，条数>2 则跳过插入。
				userInfoService.insertMyWallet(getObjectMyWallet(uf.getFromuserid(),uf.getTouserid(),"管理费收入",uf.getCreatetime(),2000,"1"));	
			} catch (Exception e) {
				System.out.println("有失败记录：fromUserID="+uf.getFromuserid());  //给直属上层返钱。
			}
		}
		System.out.println("===============管理费钱包数据，转移完毕【一次性操作】=====================");
	}
	//转移，用户提现数据。【也是一次性使用的，防止多次使用】
		@RequestMapping("/moveTiXianData")
		public void moveTiXianData(Model model ,HttpServletRequest request){
		
			List<UserFreeze> uFreezes = userFreezeService.getAllUserFreezeInfo();
			for(UserFreeze uf : uFreezes){
				try {
					//为了防止多次插入，最好在这里再查下一次，条数>2 则跳过插入。                          										//分为单位插入
					userInfoService.insertMyWallet(getMyWallet_TiXian(uf.getUserid(),"提现",uf.getPaytime(),uf.getAmount()*100,"1","2",uf.getOrderno(),uf.getWecatno()));	
				} catch (Exception e) {
					System.out.println("有失败记录：fromUserID="+uf.getUserid());  //给直属上层返钱。
				}
			}
			System.out.println("===============提现数据，转移完毕【一次性操作】=====================");
		}
		//提现的数据对象组装
		//epType'支出类型；1-提现；2-购买；3-其他【如果是支出操作，必填】'  ;  epChannel'0-银行卡；1-支付宝；2-微信[1-提现必填]；提现渠道-提现到哪里？',
		private MyWallet getMyWallet_TiXian(Integer touserid,
				String detail, Date createtime, int moeny, String epType,
				String epChannel,String epOrderNo, String channelTransNo) {
			MyWallet mw =  new MyWallet();
			mw.setMoney(-moeny);  //统统是分标示，记得转换。 【负数标示】。
			mw.setTouserid(touserid);
			mw.setSourcedes(detail);
			mw.setCreatetime(createtime);//vip startDate
			mw.setChanneltransno(channelTransNo);
			mw.setEptype(epType);
			mw.setEporderno(epOrderNo);
			mw.setEpchannel(epChannel);
			
			return mw;
		}
	
	


	//----【用户数据迁移】--------------------------------------------------------------------------------------------------------------
	//构建用户基础信息前，先构建用户的 UNOINID。
	//批量更新用户的unionId;
	
	//首先执行语句：select * from user_info where openid is null ;  去掉 openid为空的数据。【脏数据】
		
		@RequestMapping("/newbatchUpdate")
		public void batchUpdateUserUnion_new(HttpServletRequest request){
			
			String token=TokenProxy.getCommonToken();
			String uri = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
			System.out.println("token:=="+token);

			int count = 0; //计算更新值。一共更新多少条
			long starTime=System.currentTimeMillis();
			
			List<UserInfo> uf = userInfoService.getAllUserInfo();
			for(UserInfo uo : uf){

				if(null == uo.getUnionid()){  //只有unionid为空才执行。
					uri ="https://api.weixin.qq.com/cgi-bin/user/info?access_token="+token+"&openid="+uo.getOpenid()+"&lang=zh_CN";	
					
					try {
						
						 JSONObject json = HttpUtil.doGet(uri);  //单条获取。
						 String uniod =  json.getString("unionid");
						 userInfoService.updateUserUnionId(uniod,uo.getUserId());
						 count++;
						 
					} catch (IOException e) {
						 System.out.println("更新用户代码失败：" +uo.getUserId());
					}
				}
			}
			
			long endTime=System.currentTimeMillis();
			System.out.println("==============执行完毕：共执行记录："+count+" 条====================");
			System.out.println("==============共耗时："+(endTime-starTime)+"====================");
			
			
		}	
		
		
	//迁移 【用户基础】数据.测试完成。
		@RequestMapping("/moveUserBaseInfo")
		public void moveUserBaseInfo(Model model,HttpServletRequest request){
			
			List<UserInfo> uList = userInfoService.getAllUserInfo();
			int count = 0; //计算更新值。一共更新多少条
			long starTime=System.currentTimeMillis();
			
			for (UserInfo uf : uList) {
				
				try {
					//性别字段值不太准，需要调整。直接用sql批量更新吧。
					userInfoService.insertNewUserInfo(getNewUserInfo(uf.getUserId(),uf.getVipId(),uf.getHeadPic(),uf.getMobile(),uf.getNickname(),uf.getGender(),
							uf.getVipStartdate(),uf.getVipEnddate(),null,uf.getUnionid(),uf.getOpenid()));
					count++;
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("插入失败："+uf.getUserId());
				}
				
			}
				
			long endTime=System.currentTimeMillis();
			System.out.println("==============执行完毕：共执行记录："+count+" 条====================");
			System.out.println("==============共耗时："+(endTime-starTime)+"====================");
		}
		
		
		//组装用户【基础数据对象】。
		private NewUserInfo getNewUserInfo(int userid,String VipId,String ossHeadPic,String mobile,String nickName,Integer sex , 
				Date vipStartTime , Date vipEndTime,String userStatus,String wxUnionId,String openId){
		
			NewUserInfo nu = new NewUserInfo();  //基础数据组织。
		
			if(null != VipId &&!"".equals(VipId)){  //只要这个值不为空，说明是vip。那么给给1级就行。否则就取默认值0。
				nu.setViplv(1);
			}
			nu.setId(userid);
			nu.setOssheadpic(ossHeadPic);
			nu.setMobile(mobile);
			nu.setNickname(nickName);
			
			if(null != sex){
				nu.setSex(sex);          //这里的对应关系需要明确，可以直接修改数据库，sql语句批量更新。
			}
			nu.setVipstarttime(vipStartTime);
			nu.setVipendtime(vipEndTime);
		//	nu.setUserstatus(userStatus); //使用默认值。
			nu.setWxunionid(wxUnionId);
			nu.setOpenid(openId);
			nu.setCreatedtime(new Date());
			
			return nu;
		}
	
	
	//迁移用户 关系数据。
		@RequestMapping("/moveUserGenerInfo")
		public void moveUserGenerInfo(Model model,HttpServletRequest request){
			
			List<UserInfo> uList = userInfoService.getAllUserInfo();
			int count = 0; //计算更新值。一共更新多少条
			int errorcount = 0;
			long starTime=System.currentTimeMillis();
			
			for (UserInfo uf : uList) {
				
				try {
					//性别字段值不太准，需要调整。直接用sql批量更新吧。
					userInfoService.insertGeneraInf(getGenralObject(uf.getUserId(),uf.getpId(),uf.getAgent(),uf.getAgentpre(),uf.getVipStartdate()));
					count++;
				} catch (Exception e) {
					errorcount++;
					e.printStackTrace();
					System.out.println("插入失败："+uf.getUserId());
				}
				
			}
			
			long endTime=System.currentTimeMillis();
			System.out.println("==============执行完毕：成功执行："+count+" 条====================");
			System.out.println("==============执行完毕：失败执行："+errorcount+" 条====================");
			System.out.println("==============共耗时："+(endTime-starTime)+"====================");
			
			//执行完毕后测试。
//			select COUNT(1) from user_info where vip_id is not null and  pId=227;
//			select COUNT(1) from user_info where vip_id is not null and pId in (select user_id from user_info where  pId=227);
//			select COUNT(1) from user_info where vip_id is not null and pId in (select user_id from user_info where pId in (select user_id from user_info where pId=227));
			
			
			
		}
		
		//获取一个关系对象。
		private GeneralizeInfo getGenralObject(Integer userid,Integer pid,Boolean isAgent,Integer pAgentId,Date createTime){
			GeneralizeInfo gf = new GeneralizeInfo();
			
			gf.setUserid(userid);
			
			if(null != pid){
				gf.setPid(pid);	
			}
			
			if(true == isAgent){
				gf.setIsagent(isAgent);
				gf.setAgentstatus(2);
			}
			if(null != pAgentId){
				gf.setPagentid(pAgentId);
			}
			
			gf.setCreatetime(createTime);
			
			return gf;
		}
		
		
	
//============迁移用户基础数据==========【结束】=====================================================================================




	@RequestMapping(value="/joinMembership",method = RequestMethod.GET)
	public ModelAndView joinMembership(Model model, HttpServletRequest request) {

		ModelAndView mv=new ModelAndView("/user/joinMembership");
		mv.addObject("flag","");
		Integer userId =getUser(request).getUserId();	//这里可以简化。
		try {
			if(userId!=null){
				UserInfo userInfo=findUser(userId);
				mv.addObject("user",userInfo);
			}else{
				mv.addObject("user",new UserInfo());
			}
		}catch (Exception e){
			mv.addObject("user",new UserInfo());
		}
		return mv;
	}

	@RequestMapping(value="/renewal",method = RequestMethod.GET)
	public ModelAndView renewal(Model model, HttpServletRequest request) {

		ModelAndView mv=new ModelAndView("/user/joinMembership");
		mv.addObject("flag","renewal");
		Integer userId =getUser(request).getUserId();	//这里可以简化。
		try {
			if(userId!=null){
				UserInfo userInfo=findUser(userId);
				mv.addObject("user",userInfo);
			}else{
				mv.addObject("user",new UserInfo());
			}
		}catch (Exception e){
			mv.addObject("user",new UserInfo());
		}
		return mv;
	}



	@RequestMapping(value="/card",method = RequestMethod.GET)
	public ModelAndView card(Model model, HttpServletRequest request)
	{
		Integer userId =getUser(request).getUserId();
		if(userId!=null){
			UserInfo userInfo=findUser(userId);
			if(userId!=null && userInfo.getVipId()!=null && !"".equals(userInfo.getVipId())){
				model.addAttribute("isVip","true");
			}
		}
		ModelAndView mv=new ModelAndView("/user/card");
		mv.addObject("flag",request.getParameter("p")==null ? "" : request.getParameter("p"));
		return mv;
	}

	@RequestMapping(value="/card",method = RequestMethod.POST)
	public void becameVIP(Model model, HttpServletRequest request, HttpServletResponse response,Card card) {
		PrintWriter out=null;
		Jedis jedis = RedisUtil.getJedis();
		try {
			String verificationCode=jedis.get("WSHM"+card.getOwnerMobile());
			//0=服务器异常，1=已经是会员了，-1表示卡无效，2=开通成功,-2 验证码无效,3=续费成功
			out=response.getWriter();

			if(verificationCode==null || !card.getVerification().equals(verificationCode)){
				out.print("-2");
				return;
			}
			//获取用户信息
			final Integer userId =getUser(request).getUserId();
			if(userId==null || userId ==0 ){
				out.print("0");
				return;
			}

			//UserInfo userInfo=findUser(userId);
			UserInfo userInfo=userInfoService.getUserInfoByUserID(userId);
			if(userInfo==null){
				out.print("0");
				return;
			}


			card.setOwner(userId);
			if(userInfo.getVipId()!=null && !"".equals(userInfo.getVipId().trim())
					&& (card.getFlag()==null || "".equals(card.getFlag().trim()) )){
				out.print("1");//已经是vip
				return;
			}

			// 1.校验会员卡信息
			if(!cardDao.validate(card)){
				out.print("-1");
				return;
			};

			//续费
			if(userInfo.getVipId()!=null && !"".equals(userInfo.getVipId().trim())
					&& card.getFlag()!=null && !"".equals(card.getFlag().trim())){
				if( cardDao.renewal(card)){
					out.print("3");
					jedis.del("WSHM"+card.getOwnerMobile());
					// 发送短信，无论成功还是失败，只调用，不处理。
					String text = "【维书会】尊敬的：" + "先生or女士 ," + " 您于"
							+ DateUtil.getDate(new Date()) + "在维书会续费成功。感谢您的支持！";
					JavaSmsApi.sendSms(BaseDict.MSG_API_KEY, text, card.getOwnerMobile());
				}else{
					out.print("0");
				}
				return;
			}
			// 开通会员修改会员信息
			if( cardDao.joinMembership(card)){
				jedis.del("WSHM"+card.getOwnerMobile());
				// 发送短信，无论成功还是失败，只调用，不处理。
				String text = "【维书会】尊敬的：" + "先生or女士 ," + " 您于"
						+ DateUtil.getDate(new Date()) + "成功在维书会开通会员服务。感谢您的支持！";
				JavaSmsApi.sendSms(BaseDict.MSG_API_KEY, text, card.getOwnerMobile());

				//更新session
				request.getSession()
						.setAttribute(
								Cts.USER_IN_SESSION,
								userInfoService.getUserInfoByUserID(userId));
				System.out.println("更新用户信息....");
				out.print("2");
				if(card.getpId()!=null && card.getpId() >0 && (userInfo.getpId()==null || userInfo.getpId() ==0) ) {

					if(userInfoService.friendPage_A_allCount(userId) == 0){
						//上级返钱
						ApplicationContext context = WebApplicationContextUtils
								.getWebApplicationContext(request.getSession()
										.getServletContext());
						IPayCoreService payCoreService = (IPayCoreService) context.getBean("payCoreService");

						System.out.println("开始返钱.......");
						payCoreService.earn(userId);
						System.out.println("返钱结束.......");
					}

				}
			}else{
				out.print("0");
			}
		}catch (Exception e){
			out.print("0");
		}finally{
			out.flush();
			RedisUtil.returnResource(jedis);
		}
	}


	@Autowired
	CardDao cardDao;
}
