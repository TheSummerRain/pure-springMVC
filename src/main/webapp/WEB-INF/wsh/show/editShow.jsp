<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" ng-app>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>分享秀</title>
<%@include file="/extjsp/jsphead.jsp" %>

 <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
 	<%-- <script src="<%=basePath%>/reswsh/js/showjs/jquery-1.10.1.min.js" type="text/javascript" charset="utf-8"></script> --%>
	<script src="<%=basePath%>/reswsh/js/showjs/swiper.jquery.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>/reswsh/js/showjs/swiper.animate1.0.2.min.js" type="text/javascript" charset="utf-8"></script>
	<link rel="stylesheet" href="<%=basePath%>/reswsh/css/swiper.min.css">
	<link rel="stylesheet" href="<%=basePath%>/reswsh/css/animate.min.css">
	<link rel="stylesheet" href="<%=basePath%>/reswsh/css/util.css">
 <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
</head>

<body>
<input type="hidden" id="indexPage" value="${initPage}">
		 <c:if test="${fristcome == true}">
				<div id="hide" onclick="function_mask()">
						<i><img src="<%=basePath%>/reswsh/img/share_pic.png"/></i>
				</div>
		</c:if> 
	
<div class="swiper-container safe_con">
		<div class="swiper-wrapper">
			<div class="swiper-slide"style="position:relative">
		
				
				<img class="pic_bg ani" src="<%=basePath%>/reswsh/img/pic1_bg.jpg" swiper-animate-effect="zoomIn" swiper-animate-duration="0.8s" swiper-animate-delay="0s">
				<p class="page1_txt ani" swiper-animate-effect="bounceIn" swiper-animate-duration="1s" swiper-animate-delay="1s"></p>
				<img src="<%=basePath%>/reswsh/img/up.png" class="slide_up">
			</div>
			<div class="swiper-slide">
				<img class="pic_bg ani" src="<%=basePath%>/reswsh/img/pic2_bg.png" swiper-animate-effect="fadeIn" swiper-animate-duration="1s" swiper-animate-delay="0s">
				<img class="pic2_content ani" src="<%=basePath%>/reswsh/img/pic2_content.png" swiper-animate-effect="zoomIn" swiper-animate-duration="0.6s" swiper-animate-delay="0.6s">
				<p class="page2_txt2 ani" swiper-animate-effect="fadeInLeft" swiper-animate-duration="0.8s" swiper-animate-delay="1s"><span class="img_tx">
				<img src="${usr.headPic}"></span>Hi,各位书友：<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;我是<span>${usr.nickname}</span>,于
				<span><fmt:formatDate value='${usr.vipStartdate}' type="date" dateStyle='medium' /> 
				 </span>  <c:if test="${inviter != null}"> 接受 <span>${inviter}</span>的邀请  </c:if> ,成为 <span>维书会</span> 第<span>${usr.userId+12345}</span>位会员。
				 	我已经阅读了<span>${bookscount}</span>本书，共<span>${wordsaverage}</span>万字。我已经是
				 	
				 	<span> 
				 	 	<c:if test="${bookscount >0  && bookscount <=10 }">青铜级</c:if>
					 	<c:if test="${bookscount >10  && bookscount <=20 }">白银级</c:if>
					 	<c:if test="${bookscount >20  && bookscount <=30 }">黄金级</c:if>
					 	<c:if test="${bookscount >30  && bookscount <=40 }">铂金级</c:if>
					 	<c:if test="${bookscount >40  && bookscount <=50 }">钻石级</c:if>
					 	<c:if test="${bookscount >50 }">王者级</c:if>
				 	</span>会员了。
				 </p>
			
				<div class="page2_img ani" swiper-animate-effect="fadeInRight" swiper-animate-duration="0.8s" swiper-animate-delay="1.4s">
					<p class="tx"><img class="pic_bg ani" src="${usr.qrcode}"></p><br><span>${usr.nickname}的邀请码&nbsp;&nbsp;</span>
				</div>
				
				<img src="<%=basePath%>/reswsh/img/up.png" class="slide_up">
			</div>
			<div class="swiper-slide">
				<img class="pic_bg ani" src="<%=basePath%>/reswsh/img/pic3_bg.png" swiper-animate-effect="fadeIn" swiper-animate-duration="1s" swiper-animate-delay="0s">
				<div class="book_list ani" swiper-animate-effect="fadeIn" swiper-animate-duration="0.8s" swiper-animate-delay="0.6s">
					<div class="book_box">
						<span class="circle"></span>
						<p class="ani" swiper-animate-effect="fadeInRight" swiper-animate-duration="0.8s" swiper-animate-delay="0.6s"><fmt:formatDate value='${nowDate}' type="date" dateStyle='medium' /> 
						<br>下面是我的阅读书单，<br>欢迎大家阅读！</p>
					</div>
					<c:forEach items="${lstb}" var="book" varStatus="index">
						<c:if test="${index.index % 2 == 0 }">
							<div class="book_box book_box1">
								<span class="circle"><em></em></span>
								<p class="ani" swiper-animate-effect="fadeInRight" swiper-animate-duration="0.8s" swiper-animate-delay="${index.count}s">${book.bookTitle}</p>
								<div class="ani book_read" swiper-animate-effect="fadeInLeft" swiper-animate-duration="0.8s" swiper-animate-delay="${index.count+0.2}s"><img src="${book.bookPicId}" alt=""></div>
								<div class="line ani" swiper-animate-effect="fadeIn" swiper-animate-duration="0.8s" swiper-animate-delay="${index.count+0.6}s"></div>
							</div>
						</c:if>
						<c:if test="${index.index % 2 != 0 }">
							<div class="book_box book_box2">
								<span class="circle"><em></em></span>
								<p class="ani"  swiper-animate-effect="fadeInLeft" swiper-animate-duration="0.8s" swiper-animate-delay="${index.count}s">${book.bookTitle}</p>
								<div class="book_read ani" swiper-animate-effect="fadeInRight" swiper-animate-duration="0.8s" swiper-animate-delay="${index.count+0.2}s"><img src="${book.bookPicId}" alt=""></div>
								<div class="line ani" swiper-animate-effect="fadeIn" swiper-animate-duration="0.8s" swiper-animate-delay="${index.count+0.6}s"></div>
							</div>
						</c:if>
					</c:forEach>
					
				</div>
				<a href="<%=basePath%>/extCall/getAllBookList/${usr.userId}?qcode=${usr.qrcode}&userid=${usr.userId}" class="ani book_more" swiper-animate-effect="shake" swiper-animate-duration="1s" swiper-animate-delay="4.5s">查看更多>></a>
				<img src="<%=basePath%>/reswsh/img/up.png" class="slide_up">
			</div>
			<div class="swiper-slide">
				<img class="pic4_bg ani" src="<%=basePath%>/reswsh/img/pic4_img1.png" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0.2s">
				<div class="pic4_txt">
					<p class="ani" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0.8s">跟李维老师一起读书，</p>
					<p class="ani" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="1.2s">一起成长已经有<span class="txt_num">${studyDays}</span>天了。</p>
					<p class="logo ani" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="1.6s"><img src="<%=basePath%>/reswsh/img/logo.png"></p>
				</div>
				<img src="<%=basePath%>/reswsh/img/up.png" class="slide_up">
			</div>
			
			<div class="swiper-slide">
				<img class="pic5_bg ani" src="<%=basePath%>/reswsh/img/pic5_img1.png" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0.2s">
				<div class="pic5_imgList">
					<p><img class="pic5_img2 ani" src="<%=basePath%>/reswsh/img/pic5_img2.png" swiper-animate-effect="bounceIn" swiper-animate-duration="1s" swiper-animate-delay="0.4s"></p>
					<p class="pic5_p2">
						<img class="pic5_img3 ani" src="<%=basePath%>/reswsh/img/pic5_img3.png" swiper-animate-effect="bounceInLeft" swiper-animate-duration="1s" swiper-animate-delay="0.8s">
						<img class="pic5_img4 ani" src="<%=basePath%>/reswsh/img/pic5_img4.png" swiper-animate-effect="bounceInRight" swiper-animate-duration="1s" swiper-animate-delay="1.2s">
					</p>
				<p><img class="pic5_img5 ani" id="readBook" src="<%=basePath%>/reswsh/img/pic5_img5.png" swiper-animate-effect="rotateIn" swiper-animate-duration="1s" swiper-animate-delay="1.8s" onclick="imgOnclikFunction()"></p>  
				</div>
				
				<div class="pic5_txt">
					<p class="ani" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="2s">莫等闲，白了少年头，空悲切！</p>
					  <p class="logo ani" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="2.4s"><img src="<%=basePath%>/reswsh/img/logo.png"></p>
				</div>
				<img src="<%=basePath%>/reswsh/img/up.png" class="slide_up">
			</div>
			
		</div>
	</div>

	<script type="text/javascript">
	var abc = 1;
		$(function(){
			// swiper
			var indexPage = parseInt($("#indexPage").val());
			var mySwiper = new Swiper ('.swiper-container', {
				initialSlide :indexPage,
				direction: 'vertical',
				width: window.innnerWidth,
				height: window.innerHeight,
				loop: true,
				onInit: function(swiper){ //Swiper2.x的初始化是onFirstInit
					swiperAnimateCache(swiper); //隐藏动画元素
					swiperAnimate(swiper); //初始化完成开始动画
				},
				onSlideChangeEnd: function(swiper){
					swiperAnimate(swiper); //每个slide切换结束时也运行当前slide动画
				}
			});
		})
		
		
		function imgOnclikFunction(){
			window.location.href='${urlReadBook}';
		}
		
		function function_mask(){
			var userid = ${usr.userId};
			$.post("<%=basePath%>/showMe/guideCustom",{userid:userid},function(date){
				$("#hide").remove();
			});
			
		}
		
	</script>
	
<script type="text/javascript">
 wx.config({
	    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId: "${wxConfig.appId}", // 必填，公众号的唯一标识
	    timestamp:"${wxConfig.timestamp}" , // 必填，生成签名的时间戳
	    nonceStr: '${wxConfig.nonceStr}', // 必填，生成签名的随机串
	    signature: '${wxConfig.signature}',// 必填，签名，见附录1
	    jsApiList: ['onMenuShareAppMessage','onMenuShareTimeline']  // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
 wx.ready(function(){
    wx.showOptionMenu();
 	wx.onMenuShareAppMessage({
	    title: '维书会|'+'${usr.nickname}'+'的读书书单', 
	    desc: '全民阅读，你也来晒一晒自己的书单吧！',
	    link: '${sharePath}'+'${usr.userId}',
	    imgUrl:'${usr.headPic}' ,         
	    type: '', 
	    dataUrl: '', 
	    success: function () { 
	    },
	    cancel: function () { 
	    }
	});
	 wx.onMenuShareTimeline({
		    title: '维书会|'+'${usr.nickname}'+'的读书书单', 
		    link: '${sharePath}'+'${usr.userId}', 
		    imgUrl: '${usr.headPic}', 
		    success: function () { 
		    },
		    cancel: function () { 
		    }
		});
 });
 
 wx.error(function(res){
 });
 </script>
</body>
</html>

