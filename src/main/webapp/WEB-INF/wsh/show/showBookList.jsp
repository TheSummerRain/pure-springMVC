<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" ng-app>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>已经解读书籍</title>
<%@include file="/extjsp/jsphead.jsp" %>
 <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script src="<%=basePath%>/reswsh/js/showjs/swiper.jquery.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>/reswsh/js/showjs/swiper.animate1.0.2.min.js" type="text/javascript" charset="utf-8"></script>
	<link rel="stylesheet" href="<%=basePath%>/reswsh/css/swiper.min.css">
	<link rel="stylesheet" href="<%=basePath%>/reswsh/css/animate.min.css">
	<link rel="stylesheet" href="<%=basePath%>/reswsh/css/util.css">
</head>

<body class="book_list_p">
	<div class="book_list">
		<div class="book_box">
			<span class="circle"></span>
			<p class="ani"  swiper-animate-effect="fadeInLeft" swiper-animate-duration="0.8s" swiper-animate-delay="2s">我的阅读书单</p>
		</div>
		<% 	int key = 1; %>
		<c:forEach items="${lstb}" var="book" varStatus="index">
		<% key=key+1; %>
						<c:if test="${index.count % 2 == 0 }">
							<div class="book_box book_box1">
								<span class="circle"><em>${index.count}</em></span>
								<p class="ani" swiper-animate-effect="fadeInRight" swiper-animate-duration="0.8s" swiper-animate-delay="${index.count}s">${book.bookTitle}</p>
								<div class="ani book_read" swiper-animate-effect="fadeInLeft" swiper-animate-duration="0.8s" swiper-animate-delay="${index.count+0.2}s"><img src="${book.bookPicId}" alt=""></div>
								<div class="line ani" swiper-animate-effect="fadeIn" swiper-animate-duration="0.8s" swiper-animate-delay="${index.count+0.6}s"></div>
							</div>
						</c:if>
						<c:if test="${index.count % 2 != 0 }">
							<div class="book_box book_box2">
								<span class="circle"><em>${index.count}</em></span>
								<p class="ani"  swiper-animate-effect="fadeInLeft" swiper-animate-duration="0.8s" swiper-animate-delay="${index.count}s">${book.bookTitle}</p>
								<div class="book_read ani" swiper-animate-effect="fadeInRight" swiper-animate-duration="0.8s" swiper-animate-delay="${index.count+0.2}s"><img src="${book.bookPicId}" alt=""></div>
								<div class="line ani" swiper-animate-effect="fadeIn" swiper-animate-duration="0.8s" swiper-animate-delay="${index.count+0.6}s"></div>
							</div>
						</c:if>
		</c:forEach>
		
		<% if(key % 2 == 0){ %>
			<div class="book_box book_box1">
									<span class="circle"><em></em></span>
									<p class="ani" swiper-animate-effect="fadeInRight" swiper-animate-duration="0.8s" swiper-animate-delay="2s">常按识别我的邀请码<br>快快加入维书会吧</p>
									<div class="ani book_read" swiper-animate-effect="fadeInLeft" swiper-animate-duration="0.8s" swiper-animate-delay="2s"><img src="${qcode}" alt=""></div>
									<div class="line ani" swiper-animate-effect="fadeIn" swiper-animate-duration="0.8s" swiper-animate-delay="2s"></div>
			</div>
		<% }else{ %>
			<div class="book_box book_box2"> 
								<span class="circle"><em></em></span>
								<p class="ani"  swiper-animate-effect="fadeInLeft" swiper-animate-duration="0.8s" swiper-animate-delay="2s">常按识别我的邀请码<br>快快加入维书会吧</p>
								<div class="book_read ani" swiper-animate-effect="fadeInRight" swiper-animate-duration="0.8s" swiper-animate-delay="2s"><img src="${qcode}" alt=""></div>
								<div class="line ani" swiper-animate-effect="fadeIn" swiper-animate-duration="0.8s" swiper-animate-delay="2s"></div>
			</div>
		<% } %>
		<a href="<%=basePath%>/extCall/showMeBookList/${userid}?backupList=2" style="padding-right: 10px;background:#00aa00;border-radius:5px;color:#fff"> 返回>> </a>		
	</div>
		
</body>

</html>