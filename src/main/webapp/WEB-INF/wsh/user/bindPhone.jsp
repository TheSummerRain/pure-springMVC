<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" ng-app>
<head>
<title>绑定手机号</title>
<%@include file="/extjsp/jsphead.jsp" %>
<script type="text/javascript" src="${ctx}/myjs/checkyzm.js"></script>
</head>
<body class="yue_bg">
<input id="basePaths" value="<%=basePath %>" type="hidden">
 <input id="whatpag" value="${comepage}" type="hidden">
	<div class="input_area" style="padding-top: 10px">
		
		<ul class="input_ul">
			
			<c:if test="${showYaoQm == true}">
				<li>
					<input type="text" placeholder="邀请码(必填,老会员请随便输入一个数字)" class="input" id="yaoqm" onclick="hideMsg()" onkeyup="value=value.replace(/[^\d]/g,'')" maxlength="10">
				</li>
			</c:if>	
				
			<li><input type="hidden" value="${userid}" id="userid">
				<input type="text" placeholder="手机号" class="input" id="user" onclick="hideMsg()">
			</li>
			<li class="bind_telphone"><input type="text" placeholder="短信验证码"
				class="input" maxlength="6" id="yzma"> 
				<button class="sendMessage" id="sendMessage">发送验证码</button>
			<li>
		
			<div>
				<p align="center">   <input type="checkbox" id="cbtest" checked="checked" disabled="disabled"/>  <a href="${ctx}/user/showMeProtocol?comepage=${comepage}"> 服务协议 </a></p>
			</div>
				<div class="err_msg"></div>
				<p class="buttons">
					<a href="javascript:;" class="btns" id="btn_submit">保存</a>
				</p>
			</li>
		</ul>
	</div>
</body>
</html>