<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" ng-app>
<head>
<meta charset="utf-8">
<title>一度人脉</title>
<%@include file="/extjsp/jsphead.jsp"%>
</head>
<body class="yue_bg">
<div class="tg_friend">
  
  <ul class="msz_text usercenter_text2">
    <li class="vip">
      <a href="#">
        <p>${popularity-members }</p>
        <p>未付费(人)</p>
      </a>
      <a href="#">
        <p>${members }</p>
        <p>已付费(人)</p>
      </a>
      <a href="#">
        <p>${members*50 }</p>
        <p>收益(维币)</p>
      </a>
    </li>
    <li>
      <a href="javascript:;" class="friend_level">一度人脉：${popularity} 人  
        <span class="glyphicon glyphicon-menu-right"></span>
      </a>   
    </li>
    <c:forEach items="${list }" var="item" varStatus="status">
    <c:choose>
    	<c:when test="${ not empty item.vipId }">
    		 <li class="friends friends_owe"> <font color="green">${status.count }.${item.nickname }(付费)</font></li>
    	</c:when>
    	<c:otherwise>
   			 <li class="friends friends_owe">${status.count }.${item.nickname }</li>
    	</c:otherwise>
    </c:choose>
    </c:forEach>
  </ul>
  
</div>

</body>
</html>