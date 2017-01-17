package com.wsh.base.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wsh.base.dict.BaseDict;
import com.wsh.base.model.UserIncome;
import com.wsh.base.model.UserInfo;
import com.wsh.base.util.dateUtil.DateUtil;

/**
 * 
 * @Description: 代理商控制机
 * @author Wally
 * @date 2016年5月4日 下午3:21:30
 * @modify Wally
 * @modifyDate 2016年5月4日 下午3:21:30
 */
@Controller
@RequestMapping("/agent")
public class AgentController extends BaseController {
	private static final Log logger = LogFactory.getLog(AgentController.class);
	
	@RequestMapping("/toAgentProtocolPage")
	public String toAgentProtocolPage(Model model){
		return "/agent/agentProtocol";
	}
	
	@RequestMapping("/toAgentAdvancePayPage")
	public String toAgentAdvancePayPage(Model model,HttpServletRequest request){
		String agentPre  =  request.getParameter("agentpre");
		model.addAttribute("agentPre", Integer.valueOf(agentPre));
		
		return "/agent/agentAdvancePay";
	}
	
	@RequestMapping("/toAgentInvationPage")
	public String toAgentInvationPage(Model model) {
		
		// 判断是否已经有了推广码。
		//判断是否可以成为代理商。
		//判断 是否该显示
		
		return "/agent/agentInvitation";	
	}
	
	/**
	 * @time 2016年5月18日下午6:12:31
	 * @Description 测试用  -- 管理费。
	 * @param
	 * @return void
	 */
/*	@RequestMapping("/ceshi")
	public String ceshi(Model model,HttpServletRequest request){
		
		UserInfo uf  = getUser(request);
		try {
			payCoreService.openVIPForUserByWX(uf.getUserId(), 500, 1,
					BaseDict.SP_WEIBI,"11111","22222222",1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}*/
	
	
	@RequestMapping(value="/checkAgentPreRight",method={RequestMethod.POST,RequestMethod.GET})
	public void checkAgentPreRight(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String agentPre = request.getParameter("agentpre");
		
		if(null == agentPre || "".equals(agentPre)){
			response.getWriter().write("0");
			response.getWriter().flush();
			return ;  //防止为空，下面get不到。
		}
		
		UserInfo ufInfo = userInfoService.getUserInfoByUserID(Integer.valueOf(agentPre));
		if(null == ufInfo){
			response.getWriter().write("0"); 
			response.getWriter().flush();
			return ; 
		}
		
		if(!ufInfo.getAgent()){
			response.getWriter().write("1"); 
			response.getWriter().flush();
			return ;
		}
		
		if(DateUtil.getIntervalDaysExtend(ufInfo.getAgentEndDate(), new Date()) <= 0){
			response.getWriter().write("2"); 
			response.getWriter().flush();
			return ;
		}
		
		if(Integer.valueOf(agentPre).intValue() - getUser(request).getUserId().intValue() == 0){
			response.getWriter().write("3");
			response.getWriter().flush();
			return ;
		}
		
		if(null != ufInfo.getAgentpre() && (ufInfo.getAgentpre().intValue() - getUser(request).getUserId().intValue() == 0) ){  //不可以互相成为上下级
			response.getWriter().write("4");
			response.getWriter().flush();
			return ;
		}
		
		//正确
		response.getWriter().write(ufInfo.getNickname()); 
		response.getWriter().flush();
		return ;
		
	}
	
	
	//=============================================================================
	@RequestMapping("/toAgentCenter")
	public String toAgentCenter(Model model,HttpServletRequest request) {
		//获取用户信息。
		UserInfo ufInfo  = getNowUserInfo(request);
		if(null == ufInfo){
			return BaseDict.PAGE_ERROR_JSP;
		}
		model.addAttribute("us", ufInfo);
		
		if(null != ufInfo.getAgentpre()){
			model.addAttribute("agentPreNickName", userInfoService.getNickNameByUserID(ufInfo.getAgentpre()).getNickname());
		}
		//获取 某段时间以后的收入记录详情。
		List<UserIncome> incomeList = userIncomeService.getListIncomeByUserID(ufInfo.getUserId());
		model.addAttribute("incomeList",incomeList);	
		
		int managerCost  = 0;
		int commission = 0;
		for (int i = 0; i < incomeList.size(); i++) {
			if(incomeList.get(i).getType() == 1){  //管理费
				managerCost = managerCost+incomeList.get(i).getIncome();
			}
			if(incomeList.get(i).getType() == 2){  //佣金
				commission = commission+incomeList.get(i).getIncome();
			}
		}
		
		model.addAttribute("managerCost", managerCost);
		model.addAttribute("commission", commission);
		
		return "/agent/agentCenter";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
