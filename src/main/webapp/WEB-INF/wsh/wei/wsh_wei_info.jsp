<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" ng-app>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${art.artTitle}</title>
<%@include file="/extjsp/jsphead.jsp" %>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<!-- 如果需要视频，请参考demo版本。 -->

</head>
<body class="yue_bg">

	<div class="container container2">
		<div class="container_wrap">
			<h4 class="wsh_shu_info_bt">${art.artTitle}</h4>
			<p class="wsh_shu_info">
				<span><fmt:formatDate value='${art.artPublishtime}' type="date" dateStyle='medium' /></span> <span>${art.artAuthor}</span>
			</p> 

			<div class="wsh_shu_info_p">
				<!-- 在这里写 编辑器里 p内容。直接把contenc里的东西贴到这里 -->
					${art.content}
			</div>
		</div>
		
		<p class="shengming">
			声明：<a href="#"> 文章由维书会工作人员精心整理分享，未经允许，严禁转载</a>
		</p>
	</div>

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
	wx.showOptionMenu();
	wx.onMenuShareAppMessage({
	    title: '维书会|'+'${art.artTitle}' , // 分享标题
	    desc: '${art.artIntro}', // 分享描述
	    link: '${sharePath}${art.artId}',  // 分享链接
	    imgUrl: '${art.wei_picUrl}', // 分享图标
	    type: '', // 分享类型,music、video或link，不填默认为link
	    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
	    success: function () { 
	    },
	    cancel: function () { 
	    }
	});
	
	wx.onMenuShareTimeline({
	    title: '维书会|'+'${art.artTitle}', // 分享标题
	    link: '${sharePath}${art.artId}', // 分享链接
	    imgUrl: '${art.wei_picUrl}', // 分享图标
	    success: function () { 
	    },
	    cancel: function () { 
	    }
	});
	
});
</script>
</body>
</html>