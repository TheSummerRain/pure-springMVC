<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<!-- 微信浏览器缓存解决 -->
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="description" content="">
<meta name="keywords" content="">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<link href="<%=basePath%>/reswsh/lib/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
 <link href="<%=basePath%>/reswsh/css/style.css?t="<%=System.currentTimeMillis()%> rel="stylesheet">
<%-- <link href="<%=basePath%>/reswsh/css/style.css" rel="stylesheet"> --%>
<script type="text/javascript" src="<%=basePath%>/reswsh/js/jquery.min.js"></script>
<!--
<script type="text/javascript" src="<%=basePath%>/reswsh/js/rem.js"></script>
-->
<script type="text/javascript" src="<%=basePath%>/reswsh/js/bootstrap.min.js" root="${ctx}" id="_root_"></script>

<!-- 业务js -->
<script type="text/javascript" src="<%=basePath%>/myjs/popularize.js" ></script>
