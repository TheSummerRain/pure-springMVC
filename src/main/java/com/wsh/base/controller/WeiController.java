package com.wsh.base.controller;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wsh.base.dict.BaseDict;
import com.wsh.base.model.ArticleInfo;

/**
 * 维模块 controller。
 * @author Wally
 */

@Controller
@RequestMapping("/wei")
public class WeiController extends BaseController {

	private static final Log logger = LogFactory.getLog(WeiController.class);

	@RequestMapping("/weiList/{type}")
	public String getWeiListInfoNoPage(Model model ,HttpServletRequest request,@PathVariable Integer type){
		
		//logger.info("........获取维信息....分类..."+type);
		List<ArticleInfo> ati= articleInfoService.getWeiByLabelTypePage(type, 0, BaseDict.WEI_COUNT_NUM);
		
		model.addAttribute("listsize", ati.size());
		model.addAttribute("artList", ati);
		model.addAttribute("hashLable", BaseDict.WEI_LABLE);
		model.addAttribute("countNm", BaseDict.WEI_COUNT_NUM);
		model.addAttribute("type", type); 
		return "/wei/wsh_wei";
	}
	
	@RequestMapping("/weiList")
	public String getWeiListInfo(Model model ,HttpServletRequest request){
		//logger.info("........获取维信息.. 全部......");
		List<ArticleInfo> ati= articleInfoService.getWeiListInfoPage(0, BaseDict.WEI_COUNT_NUM);
		model.addAttribute("listsize", ati.size());
		model.addAttribute("artList", ati);
		model.addAttribute("hashLable", BaseDict.WEI_LABLE);
		model.addAttribute("countNm", BaseDict.WEI_COUNT_NUM);
		model.addAttribute("type", 9999); //代表全部
		return "/wei/wsh_wei";
	}
	
	@RequestMapping(value="/getWeiAllListPage",method={RequestMethod.POST,RequestMethod.GET})
	public void getWeiAllListPage(HttpServletRequest request,HttpServletResponse response){ //全部类型 分页
		
		int begeinnum = Integer.valueOf(request.getParameter("beginNum")).intValue();
		Integer type = Integer.valueOf(request.getParameter("type"));
			logger.info("=============================begeinnum:"+begeinnum+"===============================");
			try {
				List<ArticleInfo> ati = null;
				PrintWriter pw = response.getWriter();  
				if(type.intValue() == 9999 ){
					ati=  articleInfoService.getWeiListInfoPage(begeinnum,BaseDict.WEI_COUNT_NUM);
				}else{
					ati= articleInfoService.getWeiByLabelTypePage(type, begeinnum, BaseDict.WEI_COUNT_NUM);
				}
				String json = JSONArray.fromObject(ati).toString();   
				logger.info(json);
				pw.print(json);  
				pw.flush();  
				pw.close(); 
			} catch (Exception e) {
				logger.error("查询异常-获取书籍分页异常");
			}
	}
	
	@RequestMapping("/showOneInfo/{artid}")
	public String getWeiInfoByArtId(Model model ,HttpServletRequest request,@PathVariable Integer artid){
		String basePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();		
		ArticleInfo art= articleInfoService.getWeiInfoByArtId(artid);
		model.addAttribute("art", art);
		model.addAttribute("sharePath", basePath+BaseDict.EXP_BASEPATH_METHOD+BaseDict.SHAREPATH_METHOD);
		return "/wei/wsh_wei_info";
	}
	
	
	
}
