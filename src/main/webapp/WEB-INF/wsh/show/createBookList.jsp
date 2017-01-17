<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" ng-app>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择阅读书籍</title>
<%@include file="/extjsp/jsphead.jsp" %>
 <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script src="<%=basePath%>/reswsh/js/showjs/swiper.jquery.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>/reswsh/js/showjs/swiper.animate1.0.2.min.js" type="text/javascript" charset="utf-8"></script>
	<link rel="stylesheet" href="<%=basePath%>/reswsh/css/swiper.min.css">
	<link rel="stylesheet" href="<%=basePath%>/reswsh/css/animate.min.css">
	<link rel="stylesheet" href="<%=basePath%>/reswsh/css/util.css">
</head>
<body class="book_list_p">
<body class="book_check">
	<p class="book_check_head"> <font color="green" size="5">请勾选您已经阅读过的书</font> </p>
	<p  class="book_check_btn"><a id="selectall" href="javascript:;">一键全选,保存并预览</a></p>
	<div class="book_check_list">
		<ul>
		<c:forEach items="${lisb}" var="book">
			<li>
				<div class="img">
					<img  src="${book.bookPicId}" alt="">
					<span class="check" imgId="${book.bookId}"></span>
				</div>
			</li>
		</c:forEach>
		</ul>
	</div>
	<p class="book_check_btn"><a href="javascript:;">保存并生成预览</a></p>

	<script type="text/javascript">
	$(function(){
		
		$("#selectall").click(function(){
			$(".book_check_list ul").find(".check").addClass("checked");
		});
		
		$(".book_check_list ul li").click(function(){
			var $this = $(this);
			if($this.find(".check").hasClass("checked")){
				$this.find(".check").removeClass("checked");
			} else {
				$this.find(".check").addClass("checked");
			}
		});
		
		$(".book_check_btn").click(function(){
			//var arr = [];
			var aaa = '';
			$(".book_check_list ul li").find(".checked").each(function(i){
				//arr.push($(this).attr("imgId"));
				aaa=aaa+$(this).attr("imgId")+",";
			})
			if(aaa == ''){
				alert("请至少选择一本");
				return;
			}
			//arr = arr.join(",");
			window.location.href="<%=basePath%>/showMe/createMyBookList?arrb="+aaa;
		})
	
	})
		
	</script>
	
	
</body>
</html>