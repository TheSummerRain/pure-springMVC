package com.wsh.base.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wsh.base.dict.BaseDict;
import com.wsh.base.model.ArticleInfo;
import com.wsh.base.model.BookInfo;
import com.wsh.base.model.MyReadlist;
import com.wsh.base.model.UserInfo;
import com.wsh.base.util.dateUtil.DateUtil;
import com.wsh.base.wxpay.WxConfig;

//控制外部跳转的访问。
@Controller
@RequestMapping(BaseDict.EXP_BASEPATH_METHOD)
public class ExternalCallController extends BaseController {
	private static final Log logger = LogFactory.getLog(ExternalCallController.class);
	//收藏跳转。
	@RequestMapping(BaseDict.BASECALLPATH_METHOD+"{artid}")
	public String toMyConllectionPage(HttpServletRequest request,Model model,@PathVariable Integer artid) {
		
		//取出art数据。
		ArticleInfo af = articleInfoService.getArtInfoByArtid(artid);
		if(null == af){
			model.addAttribute("resTitle",BaseDict.USER_ERROR);
			model.addAttribute("simpleIntro", BaseDict.NO_DATA);
			model.addAttribute("toWhatPage","/user/toMyCollectPage");
			return "/xpage/error";
		}else {
			if (af.getArtType()==0) {
				//维类型，直接数据处理，返回。
				model.addAttribute("art", af);
				return "/wei/wsh_wei_info";
			}else{ 
				//书类型。
				//判断会员是否到期。
				UserInfo uf = getUser(request);
				if(null == uf){
					return "/xpage/toUserCenterError";
				}
				if(null == uf.getVipId() || (DateUtil.getIntervalDaysExtend(uf.getVipEnddate(), new Date()) < 0)){ //会员到期。没有会员号，都不可以查看收费文章。
					model.addAttribute("UserVip","N");
				}else{
					//跳转界面显示。
					model.addAttribute("UserVip","Y");
				}
				model.addAttribute("art", af);
				return "/book/tingshu";
			}
		}
	}
	
	
	//分享跳转的链接。
	@RequestMapping(value=BaseDict.SHAREPATH_METHOD+"{artid}",method={RequestMethod.GET,RequestMethod.POST})
	public String expSharePage(Model model,HttpServletRequest request,@PathVariable Integer artid){
		logger.info("============查看分享链接==============id:"+artid);
		ArticleInfo  aif =  articleInfoService.getArtInfoByArtid(artid);
		String basePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();	
		if(null != aif){
			model.addAttribute("art",aif);
			if(null !=  aif.getArtType()){
				if(aif.getArtType() == 1){ //书。统一都是免费。
					model.addAttribute("isShare", true); //分享,关闭：开通会员
					model.addAttribute("UserVip","N");
					model.addAttribute("uinf",null); 
					model.addAttribute("sharePath", basePath+BaseDict.EXP_BASEPATH_METHOD+BaseDict.SHAREPATH_METHOD);
					model.addAttribute("readcount",ReadCount_Readis(BaseDict.WSH_BOOK_COUNT_READ_FLAG+aif.getArtId()));	 //阅读数。
					return "/book/tingshu";
				}else{ //维
					model.addAttribute("sharePath", basePath+BaseDict.EXP_BASEPATH_METHOD+BaseDict.SHAREPATH_METHOD);
					return "/wei/wsh_wei_info";		
				}
			}else {
				return BaseDict.PAGE_ERROR_JSP; //统一配置。
			}
		}else{
			return BaseDict.PAGE_ERROR_JSP; //统一配置。
		}
	}
	
	
	
	//分享 我的推广二维码。
	@RequestMapping(BaseDict.SHAREPATH_METHOD_QRCODE+"{userid}")
	public String toQrMyTuiGuangCode(Model model,HttpServletRequest request,@PathVariable Integer userid){
			
		UserInfo ufInfo= userInfoService.getUserInfoByUserID(userid);  //根据分享的ID，获取书籍，不可用session。
		if(null == ufInfo){
			return BaseDict.PAGE_ERROR_JSP;
		}else{
			model.addAttribute("user", ufInfo); //只穿必要字段，防止信息泄露,以后改。
			model.addAttribute("nickName",ufInfo.getNickname());
			model.addAttribute("userpic",ufInfo.getHeadPic() );
			model.addAttribute("qr_url", ufInfo.getQrcode());
			
			model.addAttribute("modelValue",BaseDict.DISGUISERWHO); //伪装ID
			model.addAttribute("disguisernum",BaseDict.DISGUISERNUM); //伪装ID
		
			model.addAttribute("sharePath", getBasePath(request)+BaseDict.EXP_BASEPATH_METHOD+BaseDict.SHAREPATH_METHOD_QRCODE+ufInfo.getUserId()); //分享时关注的路径。
			
			return "/popularize/tuiguang_sharePage";  //为了防止在分享  而单独出界面。
			
		}
	}
	
	//获取书单
	@RequestMapping("/getAllBookList/{userid}")
	public String getAllBookList(Model model,@PathVariable Integer userid,HttpServletRequest request){
		
		MyReadlist myReadlist = myReadListService.getMyReadlistByUserId(userid);
		if(null == myReadlist){
			return BaseDict.PAGE_ERROR_JSP;
		}
		List list = Arrays.asList(myReadlist.getReadlist().split(","));
		List<BookInfo> bf = bookService.getShowBookList(list);
		model.addAttribute("lstb",bf);
		model.addAttribute("qcode",request.getParameter("qcode"));
		model.addAttribute("initPage", 2);  //初始化页面：初次加载都是从0开始。
		model.addAttribute("userid",Integer.valueOf(request.getParameter("userid")));
		return "/show/showBookList";
	}
	
	@RequestMapping(BaseDict.SHAREPATH_SHOWMEBOOKLIST+"{userid}")
	public String showMeBookList(Model model,HttpServletRequest request,@PathVariable Integer userid){
		
		UserInfo ufInfo= userInfoService.getUserInfoByUserID(userid);  //根据分享的ID，获取书籍，不可用session。
		if(null == ufInfo){
			return BaseDict.PAGE_ERROR_JSP;
		}else{
			
			MyReadlist myReadlist = myReadListService.getMyReadlistByUserId(userid);
			if(null == myReadlist){
				return BaseDict.PAGE_ERROR_JSP;
			}
			model.addAttribute("fristcome", false); //是否是第一次，分享的统一都不需要了。
			String[] arrlist=myReadlist.getReadlist().split(","); //读了多少书
			List list = Arrays.asList(arrlist);
			model.addAttribute("lstb", bookService.getTopThreeBooks_arr(list));
			model.addAttribute("wordsaverage", BaseDict.WORDSAVERAGE * arrlist.length);//字数
			model.addAttribute("bookscount",arrlist.length); //阅读了多少书籍
			model.addAttribute("studyDays",DateUtil.getIntervalDaysExtend(new Date(),ufInfo.getVipStartdate()));
			model.addAttribute("usr", ufInfo);
			model.addAttribute("nowDate", new Date());
			model.addAttribute("sharePath", getBasePath(request)+BaseDict.EXP_BASEPATH_METHOD+BaseDict.SHAREPATH_SHOWMEBOOKLIST);
			if(null != ufInfo.getpId()){
				model.addAttribute("inviter", userInfoService.getNickNameByUserID(ufInfo.getpId()).getNickname());
			}else{
				model.addAttribute("inviter", null);
			}
			
			if(null == request.getParameter("backupList")){
				model.addAttribute("initPage", 0);  //初始化页面：初次加载都是从0开始。
			}else{
				model.addAttribute("initPage", 2);  //初始化页面：初次加载都是从0开始。
			}
			model.addAttribute("urlReadBook", WxConfig.ACCESS_CODE_URL
					.replace("APPID", WxConfig.APPID)
					.replace(
							"REDIRECT_URI",
							java.net.URLEncoder
									.encode("http://weshuhui.com/BookClub/Wx/loading/bookc_getFristList"))
					.replace("SCOPE", WxConfig.SCOPE).replace("STATE", "bookclub"));
			
			return "/show/editShow";	
		}
	}
	
	//获取基础路径。
	protected String getBasePath(HttpServletRequest request) {
		return request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();		
	}
	
}
