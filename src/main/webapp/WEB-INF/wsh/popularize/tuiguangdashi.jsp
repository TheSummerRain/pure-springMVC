<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" ng-app>
<head>
<meta charset="utf-8">
<title>推广大使详情</title>
<%@include file="/extjsp/jsphead.jsp"%>
</head>
<body class="yue_bg">
<div class="container container3" style="position:relative">
 <i style="position:absolute;top:22px;right:23px"><img src="${ctx}/reswsh/images/tui01.png"/></i>
  <div class="usercenter" style="height: 99px;">
    <div class="left img"><img src="${userInfo.headPic }" alt="${userInfo.nickname}"></div>
    <div class="usercenter_info">
      <p>${userInfo.nickname}</p>
      <c:if test="${not empty parent.nickname}">
     	 <p>推荐人：${parent.nickname} </p>
      </c:if>
      <p class="edit">到日期：${nowdate}</p>
    </div>
  </div>
  <ul class="usercenter_opt">
    <li>
      <a href="#">
        <p>我的人气</p>
        <p>${p1+p2+p3+p4+p5+p6}</p>
      </a>
    </li>
    <li>
      <a href="#">
        <p>付费人数</p>
        <p>${m1+m2+m3+m4+m5+m6}</p>
      </a>
    </li>
    <li>
      <a href="#">
        <p>推广总收入</p>
        <p>${(m1+m2+m3)*AIncomeNo+(m4+m5+m6)*DIncomeNo}</p>
      </a>
    </li>
  </ul>
</div>
<div class="tuiguangdashi">
  <ul class="msz_text usercenter_text usercenter_tg">
    <li>
      <a href="${ctx }/popularize/qrcodePage"><em class="ban3_2">&nbsp;</em>我的推广码
        <span class="glyphicon glyphicon-menu-right"></span>
      </a>           
    </li>
 <%--    <li>
      <a href="${ctx }/popularize/rulePage"><em class="ban3_2 ban3_2_gz">&nbsp;</em>推广规则
        <span class="glyphicon glyphicon-menu-right"></span>
      </a>           
    </li> --%>
  </ul>
  <ul class="msz_text usercenter_text2">
    <li>
      <a href="${ctx }/popularize/findAPage" class="friend_level"><em class="level level_a">A</em>一度人脉
        <span class="glyphicon glyphicon-menu-right"></span>
      </a>           
    </li>
    <li>
      <a href="javascript:;" class="friend_level"><em class="level level_b">B</em>二度人脉
        <span class="glyphicon glyphicon-menu-right"></span>
      </a>           
    </li>
    <li class="vip">
      <a href="#">
        <p>人数</p>
        <p>${p2}</p>
      </a>
      <a href="#">
        <p>付费人数</p>
        <p>${m2}</p>
      </a>
      <a href="#">
        <p>收益(维币)</p>
        <p>${m2*AIncomeNo}</p>
      </a>
    </li>
    <li>
      <a href="javascript:;" class="friend_level"><em class="level level_c">C</em>三度人脉
        <span class="glyphicon glyphicon-menu-right"></span>
      </a>           
    </li>
    <li class="vip">
      <a href="#">
        <p>人数</p>
        <p>${p3}</p>
      </a>
      <a href="#">
        <p>付费人数</p>
        <p>${m3}</p>
      </a>
      <a href="#">
        <p>收益(维币)</p>
        <p>${m3*AIncomeNo}</p>
      </a>
    </li>
  </ul>
  
  
  <!-- =====================DEF等级====================== -->
  <c:if test="${hasDEF == true}">
  <ul class="msz_text usercenter_text2">
    <li>
      <a href="javascript:;" class="friend_level"><em class="level level_d">D</em>4度人脉
        <span class="glyphicon glyphicon-menu-right"></span>
      </a>           
    </li>
    <li class="vip">
      <a href="#">
        <p>人数</p>
         <p>${p4}</p>
      </a>
      <a href="#">
        <p>付费人数</p>
        <p>${m4}</p>
      </a>
      <a href="#">
        <p>收益(维币)</p>
        <p>${m4*DIncomeNo}</p>
      </a>
    </li>
    <li>
      <a href="javascript:;" class="friend_level"><em class="level level_e">E</em>5度人脉
        <span class="glyphicon glyphicon-menu-right"></span>
      </a>           
    </li>
    <li class="vip">
      <a href="#">
        <p>人数</p>
       <p>${p5}</p>
      </a>
      <a href="#">
        <p>付费人数</p>
       <p>${m5}</p>
      </a>
      <a href="#">
        <p>收益</p>
        <p>${m5*DIncomeNo}</p>
      </a>
    </li>
    <li>
      <a href="javascript:;" class="friend_level"><em class="level level_f">F</em>6度人脉
        <span class="glyphicon glyphicon-menu-right"></span>
      </a>           
    </li>
    <li class="vip">
      <a href="#">
        <p>人数</p>
        <p>${p6}</p>
      </a>
      <a href="#">
        <p>付费人数</p>
		<p>${m6}</p>
      </a>
      <a href="#">
        <p>收益</p>
        <p>${m6*DIncomeNo}</p>
      </a>
      
    </li>
  </ul>
  </c:if>
  
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