<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" ng-app>
<head>
<title>个人资料</title>
<%@include file="/extjsp/jsphead.jsp" %>
    <script src="<%=basePath %>/reswsh/js/scroll/mobiscroll_002.js" type="text/javascript"></script>
    <link href="<%=basePath %>/reswsh/js/scroll/mobiscroll_002.css" rel="stylesheet" type="text/css">
    <script src="<%=basePath %>/reswsh/js/scroll/mobiscroll_004.js" type="text/javascript"></script>
    <script src="<%=basePath %>/reswsh/js/scroll/mobiscroll.js" type="text/javascript"></script>
    <script src="<%=basePath %>/reswsh/js/scroll//mobiscroll_003.js" type="text/javascript"></script>
    <link href="<%=basePath %>/reswsh/js/scroll/mobiscroll.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
</head>
<input id="basePaths" value="<%=basePath %>" type="hidden">
<body class="yue_bg">
<ul class="msz_text user_center">
  <li class="user_img">
    <a>
      <span class="left labels">头像</span>
      <span class="right info"><img src="${userInfo.headPic}" alt=""></span>
    </a>           
  </li>
  <li>
    <a>
      <span class="left labels">用户名</span>
      <span class="right info">${userInfo.nickname}</span>
    </a>           
  </li>
  <li>
    <a>
      <span class="left labels">性别</span>
      <span class="right info">
      <c:if test="${userInfo.gender ==1 }">男  </c:if>
      <c:if test="${userInfo.gender == 2}">	女     </c:if>
      </span>
    </a>           
  </li>
  <li style="position:relative;">
    <a href="javascript:;">
      <span class="left labels">年龄</span>
      <span class="glyphicon glyphicon-menu-right"></span>
      <input type="text" id="scroller" name="scroller" value="">
      <span class="right info" id="nl">${age}</span>
    </a>           
  </li>
</ul>
<ul class="msz_text user_center">
  <li>
      <c:if test="${isMobile eq true}">
       <a href="${ctx}/user/toupdatmobipage?comepage=1"> 
          <span class="left labels">手机号</span>
          <span class="glyphicon glyphicon-menu-right"></span>
     	  <span class="right info"> ${mobile} 点击更换</span> 
       </a>  
      </c:if>
      
      <c:if test="${isMobile eq false}">
      <a href="${ctx}/user/toupdatmobipage?comepage=1"> 
          <span class="left labels">手机号</span>
          <span class="glyphicon glyphicon-menu-right"></span>
          <span class="right info">绑定手机号</span>
       </a>  
      </c:if>
          
  </li>

  <li>
      <c:if test="${isBank == true}">
       <a href="${ctx}/user/toBankInfoPage"> 
          <span class="left labels">银行卡</span>
          <span class="glyphicon glyphicon-menu-right"></span>
     	  <span class="right info">已绑定</span>
       </a>  
      </c:if>
      
      <c:if test="${isMaster==true}">
	      <c:if test="${isBank == false}">
	      <a href="${ctx}/user/toUpdateBankInfo"> 
	          <span class="left labels">银行卡</span>
	          <span class="glyphicon glyphicon-menu-right"></span>
	          <span class="right info">请绑定银行卡</span>
	       </a>  
	      </c:if>
      </c:if>
  </li>

  <li>
  <c:if test="${isAccept==true}">
   <a href="${ctx}/user/toAcceptInfoPage">  <!-- 跳转查看收货信息界面 -->
      <span class="left labels">收获地址</span>
      <span class="glyphicon glyphicon-menu-right"></span>
      <span class="right info">已填写</span>
    </a>   
  </c:if>
  
  <c:if test="${isAccept ==false}">
   <a href="${ctx}/user/toupdateAcceptPage"> <!-- 跳转绑定收货界面 -->
      <span class="left labels">收获地址</span>
      <span class="glyphicon glyphicon-menu-right"></span>
      <span class="right info">绑定收货地址</span>
    </a>   
  </c:if>
  </li>
  
</ul>
<script>
  $(function () {
	  
	  var basp = $("#basePaths").val();
      var currYear = (new Date()).getFullYear(); 
      var opt={};
      opt.date = {preset : 'date'};
      opt.default = {
        theme: 'mobiscroll', //皮肤样式
        display: 'bottom', //显示方式 
        layout: 'liquid',
        mode: 'scroller', //日期选择模式
        dateFormat: 'yyyy-mm-dd',
        lang: 'zh',
        startYear: "1900", //开始年份
        dayText: '日', monthText: '月', yearText: '年', //面板中年月日文字
        endYear: currYear, //结束年份
        onSelect : function(){
          var $date = $(this).val();
          var $year = $date.split("-")[0];
          $("#nl").html(currYear - $year);
          $.get(basp+"/user/toUpdateBithday",{date:$date},function (data) {
          });
        }
      };
    $("#scroller").mobiscroll($.extend(opt['date'], opt['default']));
   });
  
  wx.config({
	    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId: "${wxConfig.appId}", // 必填，公众号的唯一标识
	    timestamp:"${wxConfig.timestamp}" , // 必填，生成签名的时间戳
	    nonceStr: '${wxConfig.nonceStr}', // 必填，生成签名的随机串
	    signature: '${wxConfig.signature}',// 必填，签名，见附录1
	    jsApiList: ['onMenuShareQZone','onMenuShareWeibo','onMenuShareQQ','onMenuShareAppMessage','onMenuShareTimeline'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});

	wx.ready(function(){
		wx.hideOptionMenu();
	});
  
</script>
<jsp:include page="/extjsp/jspfoot.jsp"></jsp:include>
</body>
</html>