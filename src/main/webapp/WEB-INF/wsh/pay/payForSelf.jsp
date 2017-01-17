<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" ng-app>
<head>
<meta charset="utf-8">
<title>充值</title>
<%@include file="/extjsp/jsphead.jsp"%>
<script type="text/javascript" src="<%=basePath%>/myjs/payself.js"></script>
</head>
<body class="yue_bg">
<input id="payMoney" type="hidden">  <!-- 充值金额 -->
	<input type="hidden" name="transmoney" id="payMoney">
	<%-- <input type="hidden" name="payVIPWX" id="payVIPWX" value="${payOpenVIP}"> --%>
	<!-- 充值金额 -->
	<div class="avatar">
		<p class="img">
			<img src="${userInfo.headPic }" alt="${userInfo.nickname}">
		</p>
		<p>${userInfo.nickname}</p>
	</div>
	<div class="charge_title">
		<span class="left">维币充值数量</span>
		<!--   <a href="#" class="right">为朋友充值</a> -->
	</div>
	<ul class="charge_list">
		<li><a href="javascript:;"><em>1</em>WB</a></li>
		<li><a href="javascript:;"><em>500</em>WB</a></li>
		<li><a href="javascript:;"><em>1000</em>WB</a></li>
		<li><a href="javascript:;">自定义维币：<em><input type="text"
					id="zidingyi"></em>WB
		</a></li>
	</ul>
	<div class="charge_title">
		<span class="left">选择支付方式</span>
	</div>
	<ul class="charge_list">
		<li><a id="payType" href="javascript:;"><em
				class="ban3 ban4_0_1">&nbsp;</em>微信支付</a></li>
	</ul>
	<div class="wsh_vip_btn">
		<p class="buttons">
			<a href="javascript:void(0);" class="btns" id="btns">提交</a>
		</p>
	</div>
	
	<form action="${ctx }/wxpay/payRequestSponsor" method="post" id="payForm" >
		<input name="orderNo" value="${orderNo }" id="orderNo" type="hidden">
		<input name="openId" value="${openid}" id="openId" type="hidden">
		<input name="payFee"  id="payFee" type="hidden">
		<input name="body" value="pay" type="hidden">
		<input name="viewName" type="hidden" value="1" >
		<input name="timeStarted" type="hidden" id="time_start">
		<input name="attach"  type="hidden" id="userId">
	</form>
</body>
<script type="text/javascript">
	function onBridgeReady() {
		WeixinJSBridge.invoke('getBrandWCPayRequest', {
			"appId" : "${WxPay.appId}", //公众号名称，由商户传入     
			"timeStamp" : "${WxPay.timeStamp}", //时间戳，自1970年以来的秒数     
			"nonceStr" : "${WxPay.nonceStr}", //随机串     
			"package" : "${WxPay.prepayId}",
			"signType" : "${WxPay.signType}", //微信签名方式：     
			"paySign" : "${WxPay.paySign}" //微信签名 
		}, function(res) {
			//alert(JSON.stringify(res));
			if (res.err_msg == "get_brand_wcpay_request:ok") {
					window.location.href="${ctx}/user/tosuccess";
			} // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。 
		});
	}

	if ('${flag}' == 'invoke') {
		if (typeof WeixinJSBridge == "undefined") {
			if (document.addEventListener) {
				document.addEventListener('WeixinJSBridgeReady', onBridgeReady,
						false);
			} else if (document.attachEvent) {
				document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
				document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
			}
		} else {
			onBridgeReady();
		}
	}

	$("#btns").click(function() {
		$.post("${ctx}/user/doOrder",{transmoney : $("#payMoney").val(),payOpenVIP:'1'},function(data){
			var dataJson=eval("("+data+")");
			$("#openId").val(dataJson.openid);
			$("#orderNo").val(dataJson.orderNo);
			$("#payFee").val(dataJson.fee);
			$("#userId").val(dataJson.userId+"_"+dataJson.suffixType);   // 后台已经组装了：_1;_2
			$("#time_start").val(dataJson.time_start);
			$("#payForm").submit();
		}).error(function(XMLHttpRequest, textStatus, errorThrown){
			/*  alert(XMLHttpRequest.status);
			 alert(XMLHttpRequest.readyState);
			 alert(textStatus);  */
		});
		
	});
</script>
</html>


