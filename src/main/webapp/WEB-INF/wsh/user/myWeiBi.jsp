<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" ng-app>
<title>维币余额</title>
<%@include file="/extjsp/jsphead.jsp"%>
<script type="text/javascript" src="${ctx}/myjs/wshbase.js"></script>
<script type="text/javascript"
	src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
</head>

<body class="yue_bg">

	<div class="wsh_bar">
		<span> ${null == mywb ? 0: mywb} 维币</span>
	</div>
	<div class="container">
		<ul class="recharge_record">

			<div>
			<c:if test="${sumpopwb >0}">
				<li>
					<div class="left">
						<p class="font_14">推广类</p>
						<span>总收入：${sumpopwb}</span>
					</div>
					<div class="right">
						<span class="ok">+ ${sumpopwb} 维币</span>
						<p class="font_14">
							<fmt:formatDate value='${nowdate}' type="date" />
						</p>
					</div>
				</li>
			</c:if>
			<c:if test="${popconsume>0}">
				<li>
					<div class="left">
						<p class="font_14">推广类</p>
						<span>提现：${popconsume}</span>
					</div>
					<div class="right">
						<span class="wzf">- ${popconsume} 维币</span>
						<p class="font_14">
							<fmt:formatDate value='${nowdate}' type="date" />
						</p>
					</div>
				</li>
				</c:if>
				<hr style="height: 3px; border: none; border-top: 1px ridge #00AA00; margin-top: 0px" />
			</div>

			<c:forEach items="${weibilog}" var="wei">
				<li>
					<div class="left">
						<p class="font_14">${wei.intro}</p>
						<span>余额：${wei.spWeibi}</span>
					</div>
					<div class="right">
						<c:if test="${wei.income != null && wei.income != ''}">
							<span class="ok">+ ${wei.income} 维币</span>
						</c:if>
						<c:if test="${wei.expend != null && wei.expend != ''}">
							<span class="wzf">- ${wei.expend} 维币</span>
						</c:if>
						<p class="font_14">
							<fmt:formatDate value='${wei.createtime}' type="date" />
						</p>
					</div>
				</li>

			</c:forEach>




		</ul>
		<div class="question">
			<a href="#"><em class="ban">&nbsp;</em>维币规则</a>
		</div>
		<!--   <a href="#" class="load_more">上拉加载更多</a> -->
	</div>


	<script type="text/javascript">
		wx.config({
			debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
			appId : "${wxConfig.appId}", // 必填，公众号的唯一标识
			timestamp : "${wxConfig.timestamp}", // 必填，生成签名的时间戳
			nonceStr : '${wxConfig.nonceStr}', // 必填，生成签名的随机串
			signature : '${wxConfig.signature}',// 必填，签名，见附录1
			jsApiList : [ 'onMenuShareQZone', 'onMenuShareWeibo',
					'onMenuShareQQ', 'onMenuShareAppMessage',
					'onMenuShareTimeline' ]
		// 必填，需要使用的JS接口列表，所有JS接口列表见附录2
		});

		wx.ready(function() {
			//alert("回调成功....");
			//alert(location.href.split('#')[0]);
			wx.hideOptionMenu();
		});
		wx.error(function(res) {
			//alert("调用失败");
		});
	</script>


</body>
</html>
