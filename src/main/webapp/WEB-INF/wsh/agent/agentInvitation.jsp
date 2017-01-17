<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en" ng-app>
<head>
<title>请填写您的推荐人</title>
<%@include file="/extjsp/jsphead.jsp" %>
<script type="text/javascript" src="${ctx}/myjs/agentPay.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
</head>
<style>
body{background:#F3F3F3}
.superior{width:100%;height:310px;overflow:hidden}
.superior p{font-size:16px;width:128px;margin:23px auto;margin-top:42px;font-weight:600}
.superior span{font-size:20px;font-weight:600;color:#00AA00;display:block;margin:23px auto;width:60px}
.input{font-size:16px;width:282px;margin:0 auto}
.superior input{padding:0px 30px;width:262px;letter-spacing:14px;text-align:center;font-size:18px}
.err{margin:0 auto;width:190px;color:red;margin-top:26px;display:block;font-size:16px;display: none;text-align:center;line-height:26px}
.verify{width:210px;background:#ccc;margin:0 auto;}
.verify_button{width:80px;border-radius:2px;background:#00AA00;margin:0 auto ;height:40px;float:left;line-height:40px;text-align:center;font-size:18px;color:#fff}
.back{margin-right:50px}
.next{background:gray;position:relative;background:#00AA00;}
.info{margin:0 auto;width:100%;color:#00AA00;margin-top:26px;display:block;font-size:16px;display: none;text-align:center}
.btn_mask{position:absolute;top:0px;left:0px;width:80px;height:40px;background:gray;opacity:1}

</style>
<body>
<div class="superior">
		<p>请输入上级代理商</p>
		<span>邀请码</span>
		<div class="input">
			<input type="text" maxlength='8' id="agentPre" onkeyup="value=value.replace(/[^\d]/g,'')">
		</div>
		
		<div class="err" style="display: block">假如您没有邀请码，请关闭当前界面，联系书童，给您开通顶层代理权限!</div>
		<div class="info"></div>
		
	</div>
	<div class="verify">
		 <div class="verify_button back" id="backFor" >关闭</div>
		<div class="verify_button next"> <div id="nextButton"> 下一步  </div> <div class="btn_mask">下一步</div></div>
	</div>
</body>

<script type="text/javascript">

$(function(){
	
<%-- 	$("#backFor").click(function(){
		var agentpre = $("#agentPre").val();
			window.location.href="<%=basePath%>/agent/ceshi ";
		
	});  --%>
	
	$("#backFor").click(function(){
		wx.closeWindow();
	});
	
	$("#nextButton").click(function(){
		var agentpre = $("#agentPre").val();
		if(confirm("邀请码：【"+agentpre+"】您确定吗？一经确定不可修改！")){
			window.location.href="<%=basePath%>/agent/toAgentAdvancePayPage?agentpre="+agentpre;
		}
	});
	
	$("#agentPre").keyup(function(){
		var agentpre = $("#agentPre").val();
		//alert(agentpre);
		$.post("<%=basePath%>/agent/checkAgentPreRight",{'agentpre':agentpre},function(data){
			//alert(data);
			if(data =="0"){
				$(".info").hide();
				$(".err").html("重要的事情说三遍，这个不存在~").show(); //邀请码不存在。
				$(".btn_mask").show();
				return;
			}else if(data == "1"){
				$(".info").hide();
				$(".err").html("TA还不是代理商，你不能填TA的邀请码哦。").show();  
				$(".btn_mask").show();
				return;
			}else if(data == "2"){
				$(".info").hide();
				$(".err").html("抱歉抱歉， 他的代理权限已经到期了，你可以提醒他哦！ ").show();  
				$(".btn_mask").show();
				return;
			}else if(data == "3"){
				$(".info").hide();
				$(".err").html("不要这么爱自己好不好！自己给自己返钱是不允许滴 ╭(╯^╰)╮").show();  
				$(".btn_mask").show();
				return;
			}else if(data =="4"){
				$(".info").hide();
				$(".err").html("你真淘气，英明神武的程序员 GG 不允许互相成为上下级   - -！").show();  
				$(".btn_mask").show();
			}else{
				$(".err").hide(); //错误隐藏。
				$(".info").html("请确认昵称："+data).show();
				$(".btn_mask").hide();
				return;
			}
		});
	});
})
</script>

</html>