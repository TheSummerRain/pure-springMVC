package com.wsh.base.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wsh.base.dict.BaseDict;
import com.wsh.base.model.BookInfo;
import com.wsh.base.model.UserComment;
import com.wsh.base.model.UserInfo;
import com.wsh.base.util.dateUtil.JsonDateValueProcessor;



@Controller
@RequestMapping("/comment")
public class CommentController extends BaseController {
	
	private static final Log logger = LogFactory.getLog(CommentController.class);
	

	@RequestMapping("/toCommentPage")
	public String toCommentPage(HttpServletRequest request,Model model,@ModelAttribute UserComment userComment){
		//接收5个参数。
		model.addAttribute("baseComt",userComment); //评论后直接用的内容。
		if(null != userComment){
			//model.addAttribute("user", userInfoService.getUserInfoByUserID(userComment.getUserid().intValue()));
			model.addAttribute("commts",userCommentService.getUserComments(userComment.getUserid(),userComment.getArtid())); //获取对本内容的所有评论。
		}else{
			return BaseDict.PAGE_ERROR_JSP;
		}
		return "/comment/wsh_comment";
	}
	
	
	@RequestMapping(value="/insertComment",method={RequestMethod.POST,RequestMethod.GET})
	public void insertComment(HttpServletRequest request ,HttpServletResponse response) throws IOException{
		String uid = request.getParameter("userid");
		if(null == uid || "".equals(uid)){
			response.getWriter().write("");
			response.getWriter().flush();
			return;
		}	
		Integer userid = Integer.valueOf(uid);
		Integer artid = Integer.valueOf(request.getParameter("artid"));
		String headpic = request.getParameter("headpic");
		String nickname = request.getParameter("nickname");
		String ctext = request.getParameter("ctext");
		
		UserComment uc = new UserComment();
		uc.setUserid(userid);
		uc.setArtid(artid);
		uc.setHeadpic(headpic);
		uc.setNickname(nickname);
		uc.setCreatetime(new Date());
		uc.setIsnice(false);
		uc.setCtext(ctext);
	
		try {
			Integer cID = userCommentService.insertComment(uc);
			response.getWriter().write(String.valueOf(cID));
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/deleteComment",method={RequestMethod.POST,RequestMethod.GET})
	public void deleteComment(HttpServletRequest request ,HttpServletResponse response) throws IOException{
		Integer cid = Integer.valueOf(request.getParameter("cid"));
		try {
			userCommentService.deleteComment(cid);
			deleteRedis(BaseDict.WSH_PL_ZAN_COUNT_FLAG+cid); //redis删除释放空间。
			response.getWriter().write("1");
		} catch (Exception e) {
			response.getWriter().write("0");
		}
		response.getWriter().flush();
	}
	
	
	@RequestMapping(value="/getTen_CommnetsList",method={RequestMethod.POST,RequestMethod.GET})
	public void getTen_CommnetsList(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String artid = request.getParameter("artid");
		PrintWriter pw = response.getWriter();  
		if(null == artid || "".equals(artid)){
			pw.close();
		}
		
		String nowUserID = ""; //默认不能为null.否则redis会报错。
		UserInfo userInfo = getUser(request);
		if(null != userInfo){
			nowUserID = userInfo.getUserId().toString();
		}
		
		String begainNo = request.getParameter("beginNum");
		try {
			List<UserComment> lsb =  userCommentService.getTen_CommnetsList(Integer.valueOf(artid),Integer.valueOf(begainNo).intValue(),10);  //精彩评论，默认只取10条。省的麻烦
			for(int i = 0; i < lsb.size(); i++){  
				HashMap<String, String> rs = getRedisZan(BaseDict.WSH_PL_ZAN_COUNT_FLAG+lsb.get(i).getCid(),nowUserID); 	
				lsb.get(i).setPlZan(Boolean.parseBoolean(rs.get("exist")));
				lsb.get(i).setCountZan(Integer.valueOf(rs.get("countALl")));
			}
			JsonConfig config = new JsonConfig(); 
			config.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor("yyyy-MM-dd")); 
			String json = JSONArray.fromObject(lsb,config).toString();   
			pw.print(json);  
			pw.flush();  
			pw.close(); 
		} catch (Exception e) {
			logger.error("查询异常-获取评论列表失败");
		}
	}
	
	@RequestMapping(value="/zanAction",method=RequestMethod.POST)
	public void zanAction(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String cid= request.getParameter("cid");
		String userid = request.getParameter("userid");
		insPLZanRedis(BaseDict.WSH_PL_ZAN_COUNT_FLAG+cid,userid);
		PrintWriter pw = response.getWriter();  
		pw.print("1");  
		pw.flush();  
		pw.close(); 
	}
	
	
	
	
/*	public String refreshComments(){
	}*/
//-----------------工具区---------------------------------------------------------------------
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
}
