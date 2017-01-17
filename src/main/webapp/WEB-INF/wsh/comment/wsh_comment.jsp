<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en" ng-app>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>评论</title>
<%@include file="/extjsp/jsphead.jsp" %>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<link href="https://res.wx.qq.com/open/libs/weui/0.4.0/weui.css" rel="stylesheet">
</head>
<body style="background-color: #EAF5EA">

<p class="pl_title">评论：${baseComt.arttitle}</p>
<div class="pl_content">
	<textarea id="textareaVal" class="weui_textarea" placeholder="评论内容将由公众号筛选后显示，对所有人可见。"rows="4"></textarea>
	<div class="weui_textarea_counter"><span id="countUserWords">0</span>/200</div>
</div>
<div class="pl_button"><a href="javascript:submittext()" class="btns">提交</a></div>
	<div class="pl_tit" ><span style="background:#EAF5EA">我的评论</span></div>
	  <ul class="pl_list" style="margin-left: 15px;">
	  	<p id="myNewPl"></p>
	    <c:forEach items="${commts}" var="coms" varStatus="indexNo">
		    <li id="${indexNo.index+1}_li">
		      <div class="left img">
		        <img src="${coms.headpic}" alt="">
		      </div>
		      <div class="pl_info">
		        <div class="pl_info_t">
		          <span class="name left">${coms.nickname}</span>
		        </div>
		        <p style="padding-right: 10px;">${coms.ctext}</p>
		        <p class="date"> <fmt:formatDate value='${coms.createtime}' type="date"	dateStyle='medium' />
		           &nbsp;&nbsp;<a href="javascript:delByID('${coms.cid}','${indexNo.index+1}_li')">删除</a> 
		        </p>	
		      </div>
		    </li>
	    </c:forEach>
	  </ul>
	  
	 <div class="weui_dialog_alert" id="dialog2" style="display: none;">
        <div class="weui_mask"></div>
        <div class="weui_dialog">
            <div class="weui_dialog_hd"><strong class="weui_dialog_title">警告</strong></div>
            <div class="weui_dialog_bd">请填写评论内容，否则无法提交</div>
            <div class="weui_dialog_ft">
                <a href="javascript:surebutton();" class="weui_btn_dialog primary">确定</a>
            </div>
        </div>
    </div>
    <div id="toast" style="display: none;">
        <div class="weui_mask_transparent"></div>
        <div class="weui_toast">
            <i class="weui_icon_toast"></i>
            <p class="weui_toast_content" id="infotext" style="color: white;">提交成功</p>
        </div>
    </div>
    <form action="${ctx}/comment/toCommentPage" id="hidform" method="post">
		<input type="hidden" id="userid"  name="userid" value="${baseComt.userid}">
		<input type="hidden" id="headpic" name="headpic" value="${baseComt.headpic}">
		<input type="hidden" id="nickname" name="nickname" value="${baseComt.nickname}">
		<input type="hidden" id="artid" name="artid" value="${baseComt.artid}">
		<input type="hidden" name="arttitle" name="arttitle" value="${art.artTitle}">
	</form>
</body>


<script type="text/javascript">
wx.config({
    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
    appId: "${wxConfig.appId}", // 必填，公众号的唯一标识
    timestamp:"${wxConfig.timestamp}" , // 必填，生成签名的时间戳
    nonceStr: '${wxConfig.nonceStr}', // 必填，生成签名的随机串
    signature: '${wxConfig.signature}',// 必填，签名，见附录1
    jsApiList: ['onMenuShareQZone','onMenuShareWeibo','onMenuShareQQ','onMenuShareAppMessage','onMenuShareTimeline'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
});
wx.ready(function(){
	wx.hideOptionMenu(); //不可刷新，不可分享。
});
	$(function(){
		var countNo = 200; //于上面的保持一致。		
		$("#textareaVal").keyup(function(){
			var tmpcount = $("#textareaVal").val().length;
			if(tmpcount > countNo){
				var num = $(this).val().substr(0, countNo);
				$(this).val(num);
				return;
			}else{
				$("#countUserWords").text(tmpcount)
			}
		});
	});
</script>

<script type="text/javascript">
	var liNo = 9999;
	var myDate = new Date();
	
	function submittext(){
		if($("#textareaVal").val().length == 0){
			return;
		}
		$.post("${ctx}/comment/insertComment",
			  {"userid":$("#userid").val(),
				"headpic":$("#headpic").val(),
				"nickname":$("#nickname").val(),
				"artid":$("#artid").val(),
				"ctext":$("#textareaVal").val()
			  },
			  function(data){
				if(null != data && "" != data){
					liNo=Number(liNo)+1;
					$("#myNewPl").after("<li id='"+liNo+"_li'>"
							+"<div class='left img'>"
							+"<img src='${baseComt.headpic}' alt=''>"
						    +"</div>"
						    +"<div class='pl_info'>"   
						    +"<div class='pl_info_t'>"  
						    +"<span class='name left'>${baseComt.nickname}</span>"  
						    +"</div>"   
						    +"<p style='padding-right: 10px;'>"+$("#textareaVal").val()+"</p>"      
						    +"<p class='date'>"+myDate.getFullYear()+"-"+(myDate.getMonth()+1)+"-"+myDate.getDate()   
						    +"&nbsp;&nbsp;"+"<a href=javascript:delByID("+data+",'"+liNo+"_li')>删除</a>"   
						    +"</p>"    
						    +"</div>"       
						    +"</li>"
					);
					$("#textareaVal").val(""); //清空
				}
		});
	}
	function hideTosat(){$("#toast").hide();}
	//关闭dialog
	function surebutton(){$('#dialog2').hide();}
	function delByID(cid,li_id){
		$.post("${ctx}/comment/deleteComment",{"cid":cid},function(data){
			if(data =='1' ){
				$("#"+li_id).remove();
			}
		});
	}
</script>

</html>