<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en" ng-app>
<head>
<title>开通会员</title>
<%@include file="/extjsp/jsphead.jsp" %>
<script type="text/javascript" src="${ctx}/myjs/wshbase.js"></script>
</head>
<body class="yue_bg">

<div class="avatar">
  <p class="img"><img src="${userInfo.headPic}" alt=""></p>
  <p>${userInfo.nickname}</p>
</div>

<input id="basePaths" value="<%=basePath %>" type="hidden">

<ul class="user_center wsh_vip">
  <li>
    <div class="vip_t">
      <a href="javascript:;" class="show_year">
        <span class="left labels">开通年限</span>
        <span class="glyphicon glyphicon-menu-right"></span>
       <span class="right info" id="payYear">1年</span>
      </a>
    </div>
    
    <div class="vip_b">
      <a href="#"><span>1年</span></a>
      <a href="#"><span>2年</span></a>
      <a href="#"><span>3年</span></a>
      <a href="#"><span>4年</span></a>
      <a href="#"><span>5年</span></a>
    </div>
  </li>
  <li>
  
    <div class="vip_t">
      <a href="javascript:;" class="show_year">
        <span class="left labels">支付方式</span>
        <span class="glyphicon glyphicon-menu-right"></span>
        <span class="right info" id="payType">微信</span>
      </a>
    </div>
    
    <div class="vip_b vip_b2">
      <a href="#"><span>微信</span></a>
    </div>
    
  </li>
  <li class="money">
    <a>
      <span class="left labels">金额/元</span>
      <span class="right info" id="payMoney">500</span>
    </a>           
  </li>
</ul>

<div>
	<p align="center">   <input type="checkbox" id="cbtest" checked="checked" disabled="disabled"/>  <a href="${ctx}/user/showMeProtocol"> 服务协议 </a></p>
</div>


<div class="wsh_vip_btn">
  <p class="buttons"><a href="javascript:;" class="btns" id="open_member">立即开通</a></p>
</div>
 <form action="${ctx }/wxpay/payRequestSponsor" method="post" id="payForm" >
		<input name="orderNo" value="${orderNo }" id="orderNo" type="hidden">
		<input name="openId" value="${openid}" id="openId" type="hidden">
		<input name="payFee"  id="payFee" type="hidden">
		<input name="body" value="becomes a member of book club" type="hidden">
		<input name="viewName" type="hidden" value="2" >
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
</script>
</html>


