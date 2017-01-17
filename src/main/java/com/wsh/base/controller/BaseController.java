package com.wsh.base.controller;

import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import redis.clients.jedis.Jedis;

import com.wsh.base.constants.Cts;
import com.wsh.base.dict.BaseDict;
import com.wsh.base.model.NewUserInfo;
import com.wsh.base.model.UserInfo;
import com.wsh.base.service.IArtCollectionService;
import com.wsh.base.service.IArticleInfoService;
import com.wsh.base.service.IBookService;
import com.wsh.base.service.IPayCoreService;
import com.wsh.base.service.ITipListInfoService;
import com.wsh.base.service.IUserCommentService;
import com.wsh.base.service.IUserCreditService;
import com.wsh.base.service.IUserFreezeService;
import com.wsh.base.service.IUserIncomeService;
import com.wsh.base.service.IUserInfoService;
import com.wsh.base.service.IUserWBService;
import com.wsh.base.service.ImyReadListService;
import com.wsh.base.util.RedisUtil;




@Controller
public class BaseController {

	// 共有参数注册。--分页-每次请求条数
	protected static final int pageCount = 10;
	protected static final  String MSG_API_KEY = "f504ef1d5a17091064e33e3a9a369037"; //短信接口apikey
	
	// =======服务注册区===========================
	@Autowired
	protected IBookService bookService; // 书籍服务

	@Autowired
	protected ITipListInfoService tipListService; // 推荐理由服务

	@Autowired
	protected IArticleInfoService articleInfoService; // 听书 服务接口

	@Autowired
	protected IPayCoreService payCoreService; // 核心支付 服務

	// --------用户服务-----------------------
	@Autowired
	protected IUserInfoService userInfoService; // 用户基础信息 服务接口

	@Autowired
	protected IUserCreditService userCreditService; // 用户积分 服务

	@Autowired
	protected IUserWBService userWBService;

	@Autowired
	protected IArtCollectionService artconllectionService; //收藏，【功能取消】

	@Autowired
	protected IUserCommentService userCommentService;  //评论服务
	
	@Autowired
	protected ImyReadListService myReadListService;
	
	@Autowired
	protected IUserIncomeService userIncomeService;//管理费收入。后期推广收入也弄进来。
	@Autowired
	protected IUserFreezeService userFreezeService; //  提现列表
	
/*	//Added by William start
	private HttpServletRequest request;
	
	public HttpServletRequest getRequest() {
		return request;
	}
	@Resource
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}*/

	// 获取servlet上下文
	WebApplicationContext webApplicationContext = ContextLoader
			.getCurrentWebApplicationContext();
	
	
	// 12.12 修改by wally  [作废]
	protected  NewUserInfo getUser(HttpServletRequest request){
		if(request.getSession().getAttribute(Cts.USER_IN_SESSION)!=null){
			return (NewUserInfo)request.getSession().getAttribute(Cts.USER_IN_SESSION);
		}
		return null;
	}
	
	//获取最新的userID。应对字段变更。后期需要优化。【作废处理】
		protected NewUserInfo findUser(Integer userid) {
			return userInfoService.new_GetUserInfoByUserId(userid);
		}
	
		
	protected  void setUser(UserInfo userInfo,HttpServletRequest request){
		if(request!=null && userInfo!=null){
			request.getSession().setAttribute(Cts.USER_IN_SESSION, userInfo);
		}
	}
	
	//返回数据库中最新的 user信息。效率比 ，get,set session如何？
	protected NewUserInfo getNowUserInfo(HttpServletRequest request) {
		if(request.getSession().getAttribute(Cts.USER_IN_SESSION)!=null){
			NewUserInfo uf = (NewUserInfo)request.getSession().getAttribute(Cts.USER_IN_SESSION);
			return userInfoService.new_GetUserInfoByUserId(uf.getId());
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//Added by William end
	/**
	 * 
	 * @Title getUser
	 * @author Wally
	 * @time 2016年1月12日下午1:52:35
	 * @Description 获取用户session对象信息。
	 * @param
	 * @return UserInfo
	 */
/*	protected  UserInfo getUser(HttpServletRequest request){
	
		if(request.getSession().getAttribute(Cts.USER_IN_SESSION)!=null){
			return (UserInfo)request.getSession().getAttribute(Cts.USER_IN_SESSION);
		}
		return null;
	}
	protected  void setUser(UserInfo userInfo,HttpServletRequest request){
		if(request!=null && userInfo!=null){
			request.getSession().setAttribute(Cts.USER_IN_SESSION, userInfo);
		}
	}
	
	//返回数据库中最新的 user信息。效率比 ，get,set session如何？
	protected UserInfo getNowUserInfo(HttpServletRequest request) {
		if(request.getSession().getAttribute(Cts.USER_IN_SESSION)!=null){
			UserInfo uf = (UserInfo)request.getSession().getAttribute(Cts.USER_IN_SESSION);
			return userInfoService.getUserInfoByUserID(uf.getUserId());
		}
		return null;
	}
	
	
	//获取最新的userID。应对字段变更。后期需要优化。【作废处理】
	protected UserInfo findUser(Integer userid) {
		return userInfoService.getUserInfoByUserID(userid);
	}
	*/
	
	protected int getWb(UserInfo userInfo){
		return 0; 
	}
	
	protected void outPrintFail(PrintWriter out) {
		if (out != null) {
			out.print("FAIL");
		}
	}
	
	protected void outPrintSuccess(PrintWriter out) {
		if (out != null) {
			out.print("SUCCESS");
		}
	}
	
	protected  final String getCode(HttpServletRequest request){
		return request.getParameter("code");
	}
	
	protected  final boolean isFromWx(HttpServletRequest request){
		String code = getCode(request);
		return code==null || "".equals(code.trim()) ? false :true;
	}
	
	//阅读量
	protected int ReadCount_Readis(String key) {
		Jedis jedis = RedisUtil.getJedis();
		String val = jedis.get(key);
		int countNo =0;
		if(null != val){
			countNo = Integer.valueOf(jedis.get(key));
		}
		jedis.set(key, String.valueOf(countNo+1));
		RedisUtil.returnResource(jedis);
		return countNo+1;
	}
	
	
		//2016.5.30 后续启用。 by Wally.
		//【新版】 总剩余维币，可提现 =  推广总收入+代理商总收入 - 总提现（注意，体现表不提供状态，默认只要添加到提现表，就算是提现成功。）。
		public int getCanWithdrawMoney(Integer userid){
			int sumpopwb =  countPopAll_WB(userid);
			Integer agentIncome = userIncomeService.getAllIncome_byToUserID(userid);
			// 总提现金额。
			Integer WithdrawMoney = userFreezeService.sumWithDarwByUserID(userid);
			return sumpopwb + agentIncome - WithdrawMoney;
		}
		
	
	
	//推广维币 总数额
	protected int countPopAll_WB(Integer userId){
		Jedis jedis = RedisUtil.getJedis();  //池子有多大。
		String spwb = jedis.get(BaseDict.POP_WSH_WB_PRE+userId);

		//直接取redis里查，如果不为空，就直接返回。
		if(null != spwb && !"".equals(spwb)){
			RedisUtil.returnResource(jedis); //关闭
			return Integer.valueOf(spwb).intValue();
		}else{
			HashMap<String, Integer> resuMap = userInfoService.getVipCountNum(userId); //付费人数
			//写入redis.
			//设置20分钟失效时间。
			//返回总数。
			int tmpAbcVal =(resuMap.get("vipA").intValue()+ resuMap.get("vipB").intValue()+resuMap.get("vipC").intValue())*BaseDict.POP_ABC_INCOME;
			//int tmpDefval =(resuMap.get("vipD").intValue()+ resuMap.get("vipE").intValue()+resuMap.get("vipF").intValue())*BaseDict.POP_DEF_INCOME;
			jedis.set(BaseDict.POP_WSH_WB_PRE+userId, String.valueOf(tmpAbcVal));
			jedis.expire(BaseDict.POP_WSH_WB_PRE+userId, 20*60); // 20分钟失效。
			RedisUtil.returnResource(jedis); //关闭
			return tmpAbcVal; //推广总收入
		}
	}
	
	
	//获取点赞数和当前用户是否已经点赞。
	public HashMap<String, String> getRedisZan(String key,String userid){
		Jedis jedis = RedisUtil.getJedis();
		HashMap<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("exist", jedis.sismember(key, userid).toString()); //userid是否存在。
		resultMap.put("countALl", String.valueOf(jedis.scard(key))); //记录数。
		RedisUtil.returnResource(jedis); //关闭
		return resultMap;
	}
	
	//删除某个key. //目前只对 点赞使用。后期通用
	public void deleteRedis(String key){
		Jedis jedis = RedisUtil.getJedis(); 
		jedis.del(key);
		RedisUtil.returnResource(jedis); //关闭
	}
	//点赞用户插入。
	public void insPLZanRedis(String key,String userid){
		Jedis jedis = RedisUtil.getJedis(); 
		jedis.sadd(key, userid);
		RedisUtil.returnResource(jedis); //关闭
	}
	
	protected String viewLoadPage(HttpServletRequest request, org.springframework.ui.Model model){
		String code = getCode(request);
		model.addAttribute("view",  request.getServletPath());
		model.addAttribute("code",code);
		return "/loading";
	}
	

	
}
