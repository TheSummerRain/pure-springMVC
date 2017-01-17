<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en" ng-app>
<head>
<title>${flag=='renewal' ? '会员续费' : '开通会员' }</title>
<%@include file="/extjsp/jsphead.jsp" %>
<script type="text/javascript" src="${ctx}/myjs/wshbase.js"></script>
</head>
<body class="yue_bg">

<div class="avatar">
  <p class="img"><img src="${userInfo.headPic}" alt=""></p>
  <p>${userInfo.nickname}</p>
</div>

<input id="basePaths" value="<%=basePath %>" type="hidden">
<div class="wsh_vip_btn">
  <c:if test="${flag=='renewal'}">
    <c:if test="${user.vipId==null || user.vipId==''}">
        <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;亲爱的${user.nickname}，您还不是会员！您可能是想要&nbsp;<a href="joinMembership"><b>开通会员</b></a></span><br/>
        <br/>
        <p class="buttons"><a href="javascript:void(0);" class="btns" id="weixin"  style="background:#999;border: 1px solid #999;">微信续费</a></p>
        <br/>
        <p class="buttons"><a href="javascript:void(0);" class="btns" id="card" style="background:#999;border: 1px solid #999;">读书卡续费</a></p>
    </c:if>

    <c:if test="${ not empty user.vipId }">
        <p class="buttons" style="background:#999"><a href="javascript:void(0);" class="btns" id="weixin" style="background:#999;border: 1px solid #999;" >微信续费</a></p>
        <br/>
        <p class="buttons"><a href="card?p=${flag}" class="btns" id="card">读书卡续费</a></p>
    </c:if>
  </c:if>


  <c:if test="${ empty flag}">
    <c:if test="${user.vipId==null || user.vipId==''}">
        <p class="buttons"><a href="openMember" class="btns" id="weixin"  >微信开通</a></p>
        <br/>
        <p class="buttons"><a href="card" class="btns" id="card">读书卡开通</a></p>
    </c:if>
     <c:if test="${ not empty user.vipId }">
            <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;亲爱的${user.nickname}，您已经是会员了！您可能是想要&nbsp;<a href="renewal"><b>续费</b></a></span><br/>
            <br/>
            <p class="buttons"><a href="javascript:void(0);" class="btns" id="weixin" style="background:#999;border: 1px solid #999;" >微信开通</a></p>
            <br/>
            <p class="buttons"><a href="javascript:void(0);" class="btns" id="card" style="background:#999;border: 1px solid #999;">读书卡开通</a></p>
        </c:if>
  </c:if>


</div>
</body>
</html>


