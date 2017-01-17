<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" ng-app>
<title>剩余积分</title>
<head>
<%@include file="/extjsp/jsphead.jsp" %>
<script type="text/javascript" src="${ctx}/myjs/wshbase.js"></script>
 <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
</head>

<body class="yue_bg">

	<div class="wsh_bar">
		<span>${null == suCredits ? 0:suCredits} 积分</span>
	</div>
	<div class="container">
		<ul class="recharge_record">

			<c:forEach items="${creditLog}" var="log">
				<li>
					<div class="left">
						<p class="font_14">${log.intro}</p>
						<span>交易成功</span>
					</div>
					<div class="right">
						<c:if test="${log.income != null && log.income != ''}">
							<span class="ok">+ ${log.income} 积分</span>
						</c:if>
						<c:if test="${log.expend != null && log.expend != ''}">
							<span class="wzf">- ${log.expend} 积分</span>
						</c:if>
						<p class="font_14"><fmt:formatDate value='${log.createtime}' type="both"/></p>
					</div>
				</li>
			</c:forEach>
		</ul>
		<div class="question">
			<a href="#"><em class="ban">&nbsp;</em>积分规则</a>
		</div>
		<!--  <a href="#" class="load_more">查看更多>></a> -->
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
		//alert("回调成功....");
		//alert(location.href.split('#')[0]);
		wx.hideOptionMenu();
	});
	wx.error(function(res){
		//alert("调用失败");
	});

	
	</script>
	
</body>
</html>