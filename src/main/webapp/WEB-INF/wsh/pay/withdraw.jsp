<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" ng-app>
<head>
<title>提现</title>
<%@include file="/extjsp/jsphead.jsp" %>
<script type="text/javascript" src="${ctx}/myjs/tixian.js"></script>
</head>

<body class="yue_bg">
<input type="hidden" id="basepath" value="<%=basePath%>">

<div class="wsh_cash">
  <ul class="input_ul">
    <li class="card">
      <span class="left labels">提现到</span>
      <span><font color="red">微信零钱</font><font color="green">(微信-我-钱包-零钱)</font></span>
      <input id="minWithDraw" type="hidden" value="${minWithDraw}">	
    </li>
    
    <li class="card">
      <span class="left labels">金额(元)</span>
    <c:if test="${isStatusOk == true}">
      <input type="text" placeholder="可提现  ${can_use} 元" id="subvalue" onkeyup="value=value.replace(/[^\d]/g,'')"> <!-- 提现走冻结记录。同时扣减自身维币。冻结记录要记录 维币流水表的 交易编号。方便对照 -->
    </c:if>  
     <c:if test="${isStatusOk == false}">
      <input type="text" placeholder="您的余额，少于 ${minWithDraw}，不可提现！" readonly="readonly"> 
    </c:if>  
    </li>
    <li class="wbye">
      <span class="left">维币余额：<em id="spweibi">${can_use}</em></span>
    </li>
    <li>
  
	  <c:if test="${isStatusOk == false}">
		  <p class="buttons"><a href="#" class="btns" style="background: gray">不可提交</a></p>
	  </c:if>
	  <c:if test="${isStatusOk == true}">
	      <p class="buttons"><a href="javascript:submits();" class="btns">提交</a></p>
	  </c:if>
  
    </li>
  </ul>
  <ul class="user_notice">
    <li class="tit">温馨提示：</li>
    <li>(1)提现功能申请后，倘若网络顺畅，提现的钱会直接到您的微信钱包哦,查看请依次点击：<font color="red">微信-我-钱包-零钱</font></li>
    <li>(2)微信规定：给同一个实名用户付款，单笔单日限额2W/2W;给同一个非实名用户付款，单笔单日限额2000/2000。</li>
    <li>(3)针对无零钱账户的历史客户端版本，资金将进入用户的红包账户，微信支付无消息通知用户，企业可选择自行触达用户。</li>
    <li>(4)最低提现申请额度为：${minWithDraw} 元；</li>
  </ul>
</div>

</body>
</html>