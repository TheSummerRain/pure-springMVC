<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" ng-app>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>维书会-书</title>
<%@include file="/extjsp/jsphead.jsp" %>

<link rel="stylesheet" type="text/css" href="<%=basePath%>/reswsh/css/scrollbar.css">
<script type="application/javascript" src="<%=basePath%>/reswsh/js/iscroll.js"></script>
 <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
</head>
<input type="hidden" id="countNm" value="${countNm}">
<body>
	<!-- 前期也不会有那么多书，所以滚动加载，分页实现，不做 -->
	<div id="wrapper" class="container">
		<ul class="shu_list">
			<c:forEach items="${lstb}" var="book">
				<li class="clear"><a href="<%=basePath %>/bookc/toThePageOfBook/${book.bookId}">
						<div class="left">
						<c:if test="${book.bookPicId==null}">
							<img src="<%=basePath%>/reswsh/images/book_base_pic.jpg" alt="">
						</c:if>
						<c:if test="${book.bookPicId !=null}">
							<img src="${book.bookPicId}" alt="">
						</c:if>
							
						</div>
						<div>
							<h4>${book.bookTitle}</h4>
							<p class="date">
								<span class="left">去听解读</span> <span class="right"><fmt:formatDate
										value='${book.bookPubdate}' type="date" dateStyle='medium' /></span>
							</p>
							<p>${book.bookIntro}</p>
						</div>
					</a>
				</li>
			</c:forEach>
		</ul>
	</div>
	<jsp:include page="/extjsp/jspfoot.jsp"></jsp:include>

<script type="text/javascript">
$(function () { //本人习惯这样写了
	var countNum = $("#countNm").val();
	var beginNum = 0;
    $(window).scroll(function () {
        var bot = 50; //bot是底部距离的高度
        if ((bot + $(window).scrollTop()) >= ($(document).height() - $(window).height())) {
           //当底部基本距离+滚动的高度〉=文档的高度-窗体的高度时；
        	beginNum=Number(beginNum)+Number(countNum);
            $.ajax({   
                type: 'post',   
                url: "<%=basePath%>/bookc/getBookListBYLimit",   
                data: {"beginNum":beginNum},   
                success: function(data){   
                	var jsonObj=eval("("+data+")");  
                    $.each(jsonObj, function(i,item){   
                       // alert(item.bookAuthor+","+item.bookTitle);   
                        $(".shu_list").append( "<li class='clear'> "
                        +"<a href='<%=basePath%>/bookc/toThePageOfBook/"+item.bookId+"'>"
                        +"<div class='left'>"
                        +"<img src='"+item.bookPicId+"' alt=''>"
                        +"</div>"
                        +"<div>"
                        +"<h4>"+item.bookTitle+"</h4>"
                        +"<p class='date'>"
                        +"<span class='left'>去听解读</span>"
                        +"<span class='right'>"+item.bookPubdate+"</span>"
                        +"</p>"
                        +"<p>"+item.bookIntro+"</p>"
                        +"</div>"
                        +"</a>"
                        +"</li>");
                    });   
                },   
                error: function(){   
                    return;   
                }   
            });  
        }
    });
});

</script>
	
<script type="text/javascript">
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
	wx.error(function(res){
	});
	</script>
	
</body>
</html>

