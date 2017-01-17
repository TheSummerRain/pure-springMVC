<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" ng-app>
<title>我的维币</title>
<head>
<%@include file="/extjsp/jsphead.jsp" %>
<script type="text/javascript" src="${ctx}/myjs/wshbase.js"></script>
</head>
<body class="yue_bg">

<div class="container">
  <ul class="msz_text">
    <li>
      <a href="${ctx}/user/toSearchMyWb">维币余额
        <span class="glyphicon glyphicon-menu-right"></span>
      </a>           
    </li>
     <li>
      <a href="javascript:cz()">充值
        <span class="glyphicon glyphicon-menu-right"></span>
      </a>           
    </li> 
    <li> <%-- --%> 
      <a href="${ctx}/wxpay/toWithdrawPage">提现
        <span class="glyphicon glyphicon-menu-right"></span>
      </a>           
    </li>
  </ul>
</div>

</body>
</html>

