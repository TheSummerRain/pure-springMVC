<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" ng-app>
<head>
<meta charset="utf-8">
<title>代理商收入详情</title>
<%@include file="/extjsp/jsphead.jsp"%>
<style>
table{width:100%;TABLE-LAYOUT: fixed}
table thead{background:#E9EAEC}
table thead td{height:40px;text-align:center;font-size:18px;border:1px solid #ccc}
tbody td{height:30px;text-align:center;border:1px solid #ccc;width:10;WORD-WRAP: break-word}
.th1{background:#FEFEFE}
.th2{background:#F6F6F6}
</style>
</head>
<body class="yue_bg">
<div class="container container3"style="position:relative">
  <i style="position:absolute;top:22px;right:23px"><img src="${ctx}/reswsh/images/dai01.png"/></i>
  <div class="usercenter" style="height: 99px;">
    <div class="left img"><img src="${us.headPic }" alt="${us.nickname}"></div>
    <div class="usercenter_info">
      <p>${us.nickname}</p>
      <c:if test="${not empty agentPreNickName}">
     	 <p>上级代理：${agentPreNickName} </p>
      </c:if>
      <p class="edit">到日期： <fmt:formatDate value="${us.agentEndDate}"/>  </p>  <!-- 到期提醒 ，提前一个月提醒 -->
    </div>
  </div>
  <ul class="usercenter_opt">
    <li>
      <a href="#">
        <p>管理费收入</p>
        <p>${managerCost}</p>
      </a>
    </li>
    <li>
      <a href="#">
        <p>佣金收入</p>
        <p>${commission}</p>
      </a>
    </li>
    <li>
      <a href="#">
        <p>总收入</p>
        <p>${managerCost+commission}</p>
      </a>
    </li>
  </ul>
</div>
  <div class="tuiguangdashi">
  
  <ul class="recharge_record">
  
  <c:forEach items="${incomeList}" var="income" varStatus="index">
 		<li>
		  <div class="left">
			<p class="font_14"> <c:if test="${income.type ==1}">管理费</c:if> <c:if test="${income.type ==2}">佣金</c:if> </p>
			<span>单号：${income.id+12345}</span>
		  </div>
		  <div class="right">
			<span class="ok">${income.income} +金额</span>
			<p class="font_14"> <fmt:formatDate value="${income.createtime}"  type="both"/> </p>
		  </div>
		</li>
 
 </c:forEach>
	</ul>
</div>

<script>
  $(".friend_level").click(function(){
    var $this = $(this);
    var $vip = $this.parents("li").next(".vip");
    if($vip.is(":visible")){
      $vip.slideUp("fast");
      $this.find(".glyphicon").removeClass("glyphicon-menu-down");
    } else {
      $vip.slideDown("fast");
      $this.find(".glyphicon").addClass("glyphicon-menu-down");
    }
  })
</script>
</body>
</html>