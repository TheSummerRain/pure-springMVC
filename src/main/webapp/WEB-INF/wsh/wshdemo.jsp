<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cr" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" ng-app>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>维书会-书-详情</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">

<meta name="description" content="">
<meta name="keywords" content="">

<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />

<link href="/reswsh/lib/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<link href="/reswsh/css/style.css" rel="stylesheet">

<script type="text/javascript" src="/reswsh/js/jquery.min.js"></script>
<script type="text/javascript" src="/reswsh/js/bootstrap.min.js"></script>

<!-- 视频插件的引用
<script type="text/javascript" src="/reswsh/CuPlayer/js/jquery172.js"></script>
<script type="text/javascript" src="/reswsh/CuPlayer/js/action.js"></script>
 -->
 
</head>
<body class="yue_bg">

	<!-- 	<header id="header" class="header clear">
	<div class="nav_btn_box left">
		<a href="javascript:;" class="nav_btn"> <span
			class="glyphicon glyphicon-menu-left"></span><span class="txt">返回</span>
		</a>
	</div>
	<p class="font_18 tit">维书会</p>
	</header> -->
	
	<div class="container container2">
		<div class="container_wrap">
			<h4 class="wsh_shu_info_bt">情商15课</h4>
			<p class="wsh_shu_info">
				<span>2015-12-15</span> <span>Marty Cagan 著</span> <span>解读：李维</span>
			</p>

			<div class="video" id="CuPlayer">
				<!-- <p>
				<script type="text/javascript">
					var vID = "";
					var vWidth = "100%";
					var vHeight = 400;
					var vFile = "/reswsh/CuPlayer/CuSunV2set.xml";
					var vPlayer = "/reswsh/CuPlayer/player.swf?v=2.5";
					var vPic = "/reswsh/CuPlayer/images/start.jpg";
					var vCssurl    = "/reswsh/CuPlayer/images/mini.css";
					//PC,安卓,iOS -- "http://video.bcloud.brtn.cn/18/a1/18a1e757-51b9-fa99-af35-c9bc2f481027/mp4l.mp4"
					var vMp4url ="http://v.cctv.com/flash/mp4video6/TMS/2011/01/05/cf752b1c12ce452b3040cab2f90bc265_h264818000nero_aac32-1.mp4" ;
				</script>
				<script class="CuPlayerVideo" data-mce-role="CuPlayerVideo"
					type="text/javascript" src="/reswsh/CuPlayer/js/CuSunX1.min.js">
					
				</script>
				</p> -->

				<audio controls="controls"> <source
					src="http://a.uedhome.net/weishuhui/m1.mp3" /> </audio>

			</div>
			<div class="wsh_shu_info_p">

				<!-- 在这里写 编辑器里 p内容。直接把contenc里的东西贴到这里 -->
				<p>========================================</p>

			</div>
		</div>
		<p class="zanshang">
			<a href="#" class="btns">赞赏</a>
		</p>
		<p class="zs_people">125人赞赏</p>
		<ul class="people_list clear">
		</ul>
		<p class="shengming">
			声明：由李维老师解读的书籍，获得的赞赏金额，将用于建设贫困地区图书馆计划。如需了解或支持该计划，请点击<a href="#">《大手拉小手计划》</a>了解计划详情。
		</p>
		<!-- <div class="toolbar">
			<div class="left">
				阅读<span>4004</span>
			</div>
			<div class="right">
				<a href="#"><span class="ban2">&nbsp;</span><em class="zan">290</em></a>
				<a href="#"><span class="ban2 ban2_0_1">&nbsp;</span><em
					class="xin">290</em></a> <a href="#" class="ping"><span
					class="ban2 ban2_0_2">&nbsp;</span><em class="xin">写评论</em></a>
			</div>
		</div> -->
	</div>

</body>
</html>