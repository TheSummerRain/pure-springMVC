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
      <span class="left labels">储蓄卡</span>
      <span>${bankType}</span>
      <input id="cancash" type="hidden" value="${canMinToCash}">	
    </li>
    
    <li class="card">
      <span class="left labels">金额(元)</span>
    <c:if test="${spWeiBi >= canMinToCash}">
      <input type="text" placeholder="可提现${spWeiBi}元" id="subvalue" onkeyup="value=value.replace(/[^\d]/g,'')"> <!-- 提现走冻结记录。同时扣减自身维币。冻结记录要记录 维币流水表的 交易编号。方便对照 -->
    </c:if>  
     <c:if test="${spWeiBi < canMinToCash}">
      <input type="text" placeholder="少于 ${canMinToCash} 维币，不可提现" readonly="readonly"> 
    </c:if>  
    </li>
    <li class="wbye">
      <span class="left">维币余额：<em id="spweibi">${spWeiBi}</em></span>
      <a href="${ctx}/user/toUpdateBankInfo" class="right">更换银行卡</a> 
    </li>
    <li>
      <p class="buttons"><a href="javascript:submits();" class="btns">提交</a></p>
    </li>
  </ul>
  <ul class="user_notice">
    <li class="tit">提现说明：</li>
    <li>(1)书童每周一处理上周的所有提现，到账时间为3个工作日内；</li>
    <li>(2)提现目前支持农业银行、建设银行、招商银行；</li>
    <li>(3)提现：只支持 <a>推广赚取的维币</a> 转换成RMB；</li>
    <li>(4)最低提现申请额度为：${canMinToCash} 元；</li>
  </ul>
</div>

</body>
</html>