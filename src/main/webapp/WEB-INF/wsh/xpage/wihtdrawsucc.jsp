<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html lang="en" ng-app>
<head>
<title>执行结果</title>
<%@include file="/extjsp/jsphead.jsp" %>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
</head>
<body class="yue_bg">
<div class="wsh_cash">
  <ul class="input_ul">
    <li class="cash_success">
      <p class="img"><img src="${ctx}/reswsh/images/success.png" alt="" width="70"></p>
      <p class="p1">提现成功</p>  <!-- 标题描述 -->
      <p class="p2">请到：微信-我-钱包-零钱 查看收入</p> <!-- 简单描述 10个字以内 -->
    </li>
    <li>	
          <p  class="buttons"><a href="javascript:colsePage()"  class="btns"  >关闭</a></p>
    </li>
  </ul>
</div>
<script type="text/javascript">
function colsePage(){
	wx.closeWindow();
}
</script>
</body>
</html>