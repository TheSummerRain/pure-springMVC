package com.wsh.base.controller;

import java.util.Arrays;
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
import com.wsh.base.model.BookInfo;
import com.wsh.base.model.MyReadlist;
import com.wsh.base.model.UserInfo;
import com.wsh.base.util.dateUtil.DateUtil;
import com.wsh.base.wxpay.WxConfig;

/**
 * 
 * @Description: 读书秀一秀 控制器
 * @author Wally
 * @date 2016年4月12日 下午5:18:23
 */

@Controller
@RequestMapping("/showMe")
public class StudyShowController extends ActivityBaseController {

	private static final Log logger = LogFactory.getLog(StudyShowController.class);
	
	//跳转到秀一秀界面。
	@RequestMapping("/toShowMyStudyPage")
	public String toShowMyStudyPage(Model model,HttpServletRequest request){
		logger.info("....................toShowMyStudyPage..................");
		UserInfo uf = getNowUserInfo(request);
		if(null == uf){
			return BaseDict.PAGE_ERROR_JSP;
		}
		//判断是否存在记录。存在就直接获取。不存在就跳到添加界面。
		MyReadlist myReadlist = myReadListService.getMyReadlistByUserId(uf.getUserId());
	/*	if(null == myReadlist){
			return "redirect:/showMe/tocreateMyBookList";
		}*/
		
		if(null == myReadlist.getWishes()){  //第一次进来需要分享。
			model.addAttribute("fristcome", true);
		}else{
			model.addAttribute("fristcome", false);
		}
		
		String[] arrlist=myReadlist.getReadlist().split(","); //读了多少书
		List list = Arrays.asList(arrlist);
		model.addAttribute("lstb", bookService.getTopThreeBooks_arr(list));
		
		model.addAttribute("wordsaverage", BaseDict.WORDSAVERAGE* arrlist.length );//字数
		model.addAttribute("bookscount",arrlist.length); //阅读了多少书籍
		//阅读几本书，从这里来查询了。
		
		model.addAttribute("studyDays",DateUtil.getIntervalDaysExtend(new Date(),uf.getVipStartdate()));
		model.addAttribute("usr", uf);
		model.addAttribute("nowDate", new Date());
		String basePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();		
		model.addAttribute("sharePath", basePath+BaseDict.EXP_BASEPATH_METHOD+BaseDict.SHAREPATH_SHOWMEBOOKLIST);
		
		if(null != uf.getpId()){
			model.addAttribute("inviter", userInfoService.getNickNameByUserID(uf.getpId()).getNickname());
		}else{
			model.addAttribute("inviter", null);
		}
			
		model.addAttribute("urlReadBook", WxConfig.ACCESS_CODE_URL
				.replace("APPID", WxConfig.APPID)
				.replace(
						"REDIRECT_URI",
						java.net.URLEncoder
								.encode("http://weshuhui.com/BookClub/Wx/loading/bookc_getFristList"))
				.replace("SCOPE", WxConfig.SCOPE).replace("STATE", "bookclub"));
		model.addAttribute("initPage", 0);  //初始化页面：初次加载都是从0开始。
		
		return "/show/editShow";
	}
	
		
	@RequestMapping("/tocreateMyBookList")
	public String tocreateMyBookList(Model model){
		List<BookInfo> lsBookInfos = bookService.getAllListInfo();
		model.addAttribute("lisb",lsBookInfos);
		return "/show/createBookList";
	}
	
	@RequestMapping("/createMyBookList")
	public String createMyBookList(Model model,HttpServletRequest request){
		
		String arrlist = request.getParameter("arrb");
		
		if (null==arrlist || "".equals(arrlist)) {
			return BaseDict.PAGE_ERROR_JSP;
		}
		
		UserInfo uf = getNowUserInfo(request);
		if(null == uf){
			return BaseDict.PAGE_ERROR_JSP;
		}
		
		MyReadlist redlist = new MyReadlist();
		redlist.setCreatetime(new Date());
		redlist.setReadlist(arrlist.substring(0,arrlist.length()-1));
		redlist.setUserid(uf.getUserId());
		//redlist.setWishes("sometall");
		myReadListService.insertOneInfo(redlist);
		return "redirect:/showMe/toShowMyStudyPage";
	}
	
	@RequestMapping(value="/guideCustom",method={RequestMethod.POST,RequestMethod.GET})
	public void guideCustom(HttpServletRequest request,HttpServletResponse response){
		
		String userid = request.getParameter("userid");
		
		myReadListService.changeStatus(Integer.valueOf(userid));
		
	}
	
	
	
	
}
