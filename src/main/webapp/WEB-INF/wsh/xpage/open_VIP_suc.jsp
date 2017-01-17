<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html lang="en" ng-app>
<head>
<title>执行结果</title>
<%@include file="/extjsp/jsphead.jsp" %>
</head>
<body class="yue_bg">
<div class="wsh_cash">
  <ul class="input_ul">
    <li class="cash_success">
      <p class="img"><img src="<%=basePath%>/reswsh/images/success.png" alt="" width="70"></p>
      <p class="p1">成功</p>  <!-- 标题描述 -->
      <c:if test="${ not empty flag }">
        <p class="p2">恭喜您继续和维书会学员一起成长</p> <!-- 简单描述 10个字以内 -->
      </c:if>
       <c:if test="${empty flag }">
        <p class="p2">恭喜您成为唯书会一员</p> <!-- 简单描述 10个字以内 -->
       </c:if>
    </li>
    <li>						<!-- 负载 完成按钮后续动作 路径。 -->
      <p class="buttons"><a href="<%=basePath%>/user/toUserCenterPage" class="btns">完成</a></p>
    </li>
  </ul>
</div>
</body>
</html>