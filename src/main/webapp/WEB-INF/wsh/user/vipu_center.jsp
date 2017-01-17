<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en" ng-app>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>维书会-会-会员</title>
<%@include file="/extjsp/jsphead.jsp" %>

</head>
<body class="yue_bg">
<div class="container container3">
  <div class="usercenter">
    <div class="left img"><img src="/baseimgs/user/userdefault.jpg" alt=""></div>
    <div class="usercenter_info">
      <p>下午好，${user.userName}</p>
      <p>会员号：${user.vipId}</p>
      <p class="edit"><a href="#">编辑资料</a></p>
    </div>
  </div>
  <ul class="usercenter_opt">
    <li>
      <a href="#">
        <p>我的积分</p>
        <p>${myIntegral}</p>
      </a>
    </li>
    <li>
      <a href="#">
        <p>我的维币</p>
        <p>${weibi}</p> <!-- 还要对钱做格式化 -->
      </a>
    </li>
    <li>
      <a href="#">
        <p>会员到期</p>
        <p>${user.vipEnddate}</p>
      </a>
    </li>
  </ul>
</div>
<ul class="msz_text usercenter_text2">
  <li>
    <a href="javascript:;"><em class="ban3">&nbsp;</em>推广大使
      <span class="glyphicon glyphicon-menu-right"></span>
    </a>           
  </li>
  <li class="vip">
    <a href="#">
      <p>${proinfo.userNum}</p>
      <p>我的人气</p>
    </a>
    <a href="#">
      <p>${proinfo.vipuserNum}</p>
      <p>付费人数</p>
    </a>
    <a href="#">
      <p>${proinfo.incomeWb}</p>
      <p>12月返币</p>
    </a>
  </li>
</ul>
<ul class="msz_text usercenter_text">
  <li>
    <a href="javascript:;"><em class="ban3 ban3_0_1">&nbsp;</em>会员权益
      <span class="glyphicon glyphicon-menu-right"></span>
    </a>           
  </li>
  <li>
    <a href="javascript:;"><em class="ban3 ban3_0_2">&nbsp;</em>收藏夹
      <span class="glyphicon glyphicon-menu-right"></span>
    </a>           
  </li>
</ul>
<jsp:include page="/extjsp/jspfoot.jsp"></jsp:include>
</body>
</html>