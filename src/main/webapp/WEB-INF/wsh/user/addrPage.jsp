<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en" ng-app>
<head>
<title>收货地址</title>

<%@include file="/extjsp/jsphead.jsp" %>

</head>
<body class="yue_bg">
<div class="personal_data">
  <ul class="personal_data_ul">
    <li>
      <span class="left labels">收货人</span>
      <span class="right info">${userInfo.acceptName}</span>
    </li>
<%--     <li>
      <span class="left labels">电话/手机</span>
      <span class="right info">${userInfo.mobile}</span>
    </li> --%>
    <li>
      <span class="left labels">省/市/区</span>
      <span class="right info">${userInfo.preAddr}</span>
    </li>
    <li>
      <span class="left labels">详细地址</span>
      <span class="right info">${userInfo.addr}</span>
    </li>
    <li>
      <span class="left labels">邮编</span>
      <span class="right info">${userInfo.postcode}</span>
    </li>
  </ul>
</div>
<div class="btn_f00">
  <p class="buttons"><a href="<%=basePath%>/user/toupdateAcceptPage" class="btns" style="background:#00aa00;">更改收货地址</a></p>
</div>

</body>
</html>