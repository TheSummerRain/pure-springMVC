package com.wsh.base.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import redis.clients.jedis.Jedis;

import com.wsh.base.dict.BaseDict;
import com.wsh.base.model.ArticleInfo;
import com.wsh.base.model.BookInfo;
import com.wsh.base.model.NewUserInfo;
import com.wsh.base.model.TiplistInfo;
import com.wsh.base.model.UserInfo;
import com.wsh.base.util.RedisUtil;
import com.wsh.base.util.dateUtil.DateUtil;
import com.wsh.base.util.dateUtil.JsonDateValueProcessor;


/**
 * 书-控制器
 * @author Wally
 */
@Controller
@RequestMapping("/bookc")
public class BookController extends BaseController {
	
	private static final Log logger = LogFactory.getLog(BookController.class);
	
	
	@RequestMapping("getAllList")
	public String getAllListInfo(Model model ,HttpServletRequest request){
		List<BookInfo> lsb = bookService.getAllListInfo();
		model.addAttribute("lstb", lsb);
		return "/book/weishuList";
	}
	
	
	//第一次跳转界面，只需要这个就OK。
	@RequestMapping("/getFristList")
	public String getFirstListInfo(Model model){
		List<BookInfo> lsb =  bookService.getFristListInfo(0,BaseDict.BOOK_COUNT_NUM);
		model.addAttribute("lstb", lsb);
		model.addAttribute("countNm", BaseDict.BOOK_COUNT_NUM); //记录查询条数
		return "/book/weishuList";
	}
	
	
	@RequestMapping(value="/getBookListBYLimit",method={RequestMethod.POST,RequestMethod.GET})
	public void getBookListByLimit(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		int begeinnum = Integer.valueOf(request.getParameter("beginNum")).intValue();
	//	logger.info("=============================begeinnum:"+begeinnum+"===============================");
		try {
			PrintWriter pw = response.getWriter();  
			List<BookInfo> lsb =  bookService.getFristListInfo(begeinnum,BaseDict.BOOK_COUNT_NUM);
			JsonConfig config = new JsonConfig(); 
			config.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor("yyyy-MM-dd")); 
			String json = JSONArray.fromObject(lsb,config).toString();   
			//logger.info(json);
			pw.print(json);  
			pw.flush();  
			pw.close(); 
		} catch (Exception e) {
			logger.error("查询异常-获取书籍分页异常");
		}
	}
	
	
	
	//获取一条记录，跳转 去听书界面。
	@RequestMapping("/toThePageOfBook/{bookid}")
	public String toThePageOfBook(Model model ,HttpServletRequest request,@PathVariable Integer bookid){
		BookInfo bif =	bookService.getOneBookInfoByID(bookid);
		TiplistInfo tlf=tipListService.getTipListByBookID(bookid);
		model.addAttribute("book", bif);
		model.addAttribute("tiplist", tlf);
		//modify by Wally. find has article.
		Integer artic = articleInfoService.checkHasAritcle(bif.getBookId());
		if(artic > 0){
			model.addAttribute("canListen","Y");
		}else{
			model.addAttribute("canListen","N");
		}
		
		return "/book/ws_recommend";
	}
	
	
	
	//------------------------下面两个方法一样，后期做整改，重构吧--------------------------------------------------------------------------------------------------
	//去听书的功能界面。业务校验很多。--这里是书模块
	@RequestMapping("/gotoListenBook/{bookid}")
	public String gotoListenBook(Model model ,HttpServletRequest request,@PathVariable Integer bookid){
		
		
		NewUserInfo uf = null;
		try {
			uf=getNowUserInfo(request);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("----到用户首页-失败---");
			return BaseDict.PAGE_ERROR_JSP;
		}
		
		
		
		//12.13修改vip判断规则。
		if(null != uf && uf.getViplv() == 1 &&  DateUtil.getIntervalDaysExtend(uf.getVipendtime(), new Date()) >= 0 ){
			model.addAttribute("UserVip","Y");
		}else{
			model.addAttribute("UserVip","N");
		}
		
		/*if(null!=uf.getVipId() && !"".equals(uf.getVipId()) && null !=uf.getVipEnddate() && !"".equals(uf.getVipEnddate()) &&  DateUtil.getIntervalDaysExtend(uf.getVipEnddate(), new Date()) >= 0 ){ //是vip会员，且会员没有到期。
			model.addAttribute("UserVip","Y");
		}else{
			model.addAttribute("UserVip","N");
		}
*/		
		
		model.addAttribute("uinf", uf); 
		String basePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();		
		ArticleInfo ati= articleInfoService.getContentInfoByBookID(bookid);
		BookInfo bf=null;
		//获取书籍图片
		if(null != ati){
			bf = bookService.getOneBookInfoByID(ati.getBookId());
			model.addAttribute("ShareBook", bf.getBookPicId());
		}
		
		
		model.addAttribute("art", ati);
		model.addAttribute("isShare", false); //非分享，显示开通会员标签
		model.addAttribute("sharePath", basePath+BaseDict.EXP_BASEPATH_METHOD+BaseDict.SHAREPATH_METHOD);
		model.addAttribute("readcount",ReadCount_Readis(BaseDict.WSH_BOOK_COUNT_READ_FLAG+ati.getArtId()));	 //阅读数。
		return "/book/tingshu";
	}
	
	//根bookid查询。需要重构。
	@RequestMapping("/gotoListenBookByArtID/{artid}")
	public String gotoListenBookByArtID(Model model ,HttpServletRequest request,@PathVariable Integer artid){
		UserInfo usersession = getUser(request);
		UserInfo uf = null;
		if(null != usersession){
			try {
				uf =findUser(usersession.getUserId());  //获取最新数据。
			} catch (Exception e) {
				return BaseDict.PAGE_ERROR_JSP;
			}
		}else{
			uf= new UserInfo();
		}
		
		if(null!=uf.getVipId()&& !"".equals(uf.getVipId()) && null !=uf.getVipEnddate() && !"".equals(uf.getVipEnddate()) &&  DateUtil.getIntervalDaysExtend(uf.getVipEnddate(), new Date()) >= 0 ){ //是vip会员，且会员没有到期。
			model.addAttribute("UserVip","Y");
		}else{
			model.addAttribute("UserVip","N");
		}
		model.addAttribute("uinf", uf); 
		String basePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();		
		ArticleInfo ati= articleInfoService.getArtInfoByArtid(artid);
		model.addAttribute("art", ati);
		model.addAttribute("isShare", false); //非分享，显示开通会员标签
		model.addAttribute("sharePath", basePath+BaseDict.EXP_BASEPATH_METHOD+BaseDict.SHAREPATH_METHOD);
		model.addAttribute("readcount",ReadCount_Readis(BaseDict.WSH_BOOK_COUNT_READ_FLAG+ati.getArtId()));	 //阅读数。
		
		return "/book/tingshu";
	}
	
	//--------------------------------------------------------------------------------------------------
	
	
	
	
	
	
	//****************************************重构区域********************************************************
	//跳转到 创建 读书书单列表。
	@RequestMapping("/toCreateBookListPage")
	public String toCreateBookListPage(Model model){
		return "";
	}
	
	@RequestMapping("/createMyBookList")
	public String createBookList(Model model){
		//获取书单列表值。 21,32,44,45....
		
		//不计入数据库。记录在 redis里。如果redis里是空的就跳转到生成。如果不为空就直接取。
		//redis里可以只记录数字，也可以直接记录 名字和图片。
		
		//不查询数据库，直接从redis里取值。
		
		return ""; //预览control，不是界面。
	}
	
	
	
/**
 *临时使用--------------------------------------------------------------------------------------------------------
 */
	//批量增加阅读数。
	@RequestMapping("/addReadCount/{count}")
	public  void addReadCount(@PathVariable Integer count) {
		
		List<ArticleInfo> ar = articleInfoService.getArtList();
		for (int i = 0; i <= ar.size(); i++) {
			System.out.println(ar.get(i).getArtId());
			ReadCount(BaseDict.WSH_BOOK_COUNT_READ_FLAG+ar.get(i).getArtId());
		}
	}
	
	//查询阅读量最大的书
	@RequestMapping("/findMaxReadCount")
	public  void addReadCount() {
		
		List<ArticleInfo> ar = articleInfoService.getArtList();
		for (int i = 0; i < ar.size(); i++) {
			System.out.println( "书名:"+ar.get(i).getArtTitle()+"   ---阅读量："+
			whatBookReadMax(BaseDict.WSH_BOOK_COUNT_READ_FLAG+ar.get(i).getArtId()) );
		}
	}
	
	
	//增加阅读量
		protected int ReadCount(String key) {
			Jedis jedis = RedisUtil.getJedis();
			String val = jedis.get(key);
			int countNo =0;
			if(null != val){
				countNo = Integer.valueOf(jedis.get(key));
			}
		//  jedis.set(key, String.valueOf(countNo+18000));    //轻易别用这里
			RedisUtil.returnResource(jedis);
			return countNo+18000;
		}
	//查询最受欢迎的书。
		
		protected int whatBookReadMax(String key) {
			Jedis jedis = RedisUtil.getJedis();
			String val = jedis.get(key);
			int countNo =0;
			if(null != val){
				countNo = Integer.valueOf(jedis.get(key))-18000;  //当初加过1800次。
				
				if(countNo <= 0){
					countNo = Integer.valueOf(jedis.get(key));  //后来的书没有增加过阅读量。
				}
				
			}
		//	jedis.set(key, String.valueOf(countNo+18000));
			RedisUtil.returnResource(jedis);
			return countNo;
		}
		
//-----------------------------------------------------------------------------------------------	
	
	
	
}
