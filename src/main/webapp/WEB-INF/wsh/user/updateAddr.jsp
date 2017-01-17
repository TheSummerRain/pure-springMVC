<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" ng-app>
<head>
<title>收货地址</title>
<%@include file="/extjsp/jsphead.jsp" %>
</head>
<body class="yue_bg">

<form action="<%=basePath%>/user/updateAcceptInfo" method="post">
<div class="input_area">
  <ul class="input_ul">
         <li><p align="center"><font color="red">请填写正确的地址</font></p></li>	
    <li>
      <input type="text" placeholder="收货人" class="input"  name="acceptName" value="${userInfo.acceptName}" maxlength="20">
    </li>
    
<%--     <li>
      <input type="text" placeholder="电话/手机" class="input" name="mobile" value="${userInfo.mobile}" maxlength="11"> <!-- 能看不能改？ -->
    </li> --%>
    
    <li>
      <input type="text" placeholder="省、市/区" class="input" name="preAddr" value="${userInfo.preAddr}" maxlength="32">
    </li>
    <li>
      <input type="text" placeholder="详细地址" class="input" name="addr" value="${userInfo.addr}" maxlength="100">
    </li>
    <li>
      <input type="text" placeholder="邮编" class="input" name="postcode" value="${userInfo.postcode}" maxlength="10"> <!-- 国际地址 -->
    </li>
    <li>
      <p class="buttons"><a href="#" class="btns">保存</a></p>
    </li>
  </ul>
  <ul class="user_notice">
    <li>温馨提示：</li>
    <li>(1)填写收货地址，便于我们为您邮寄礼品！</li>
  </ul>
</div>
</form>
<script type="text/javascript">
$(".btns").click(function(){
	$("form").submit();
});
</script>
</body>
</html>