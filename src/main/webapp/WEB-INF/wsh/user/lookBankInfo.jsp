<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" ng-app>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>已绑定银行卡</title>
<%@include file="/extjsp/jsphead.jsp"%>
</head>
<body class="yue_bg">
<div class="personal_data">
  <ul class="personal_data_ul">
    <li>
      <span class="left labels">姓名</span>
      <span class="right info">${bankUserName}</span>
    </li>
    <li>
      <span class="left labels">卡号</span>
      <span class="right info">${bankNo}</span>
    </li>
    <li>
      <span class="left labels">银行</span>
      <span class="right info">${bankType}</span>
    </li>
   <li>
      <span class="left labels">开户支行</span>
      <span class="right info">${subBank}</span>
    </li>
  </ul>
</div>
<div class="btn_f00">
  <p class="buttons"><a href="${ctx}/user/toUpdateBankInfo" class="btns" style="background:#00aa00;">更换银行卡号</a></p>
</div>

</body>
</html>
