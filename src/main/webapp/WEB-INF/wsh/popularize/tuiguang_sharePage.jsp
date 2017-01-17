<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" ng-app>
<head>
<meta charset="utf-8">
<title>推广大使-推广码</title>
<%@include file="/extjsp/jsphead.jsp"%>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
</head>
<body class="tg_bg">

<div class="tuiguangma_wrap">
  <div class="tuiguangma">
    <div class="avatar">
      <p class="img"><img src="${user.headPic}" alt=""></p>
       <p><span>== 全民阅读推广大使 ==</span></p>
      <p><span>${user.nickname}</span></p>
    
    </div>
    <div class="tuiguangma_code">
      <p  style="font-size: 18px;color: green">邀请码：<span style="font-size: 22px"> <c:if test="${user.userId != modelValue}">${user.userId}</c:if><c:if test="${user.userId == modelValue}">${disguisernum}</c:if>     </span></p>
      <p><img src="${qr_url}" alt=""></p>
      <p>直接扫二维码(或长按)</p>
      <p>中国软实力研究权威李维老师每年带你读：</p>
      <p class="book">50本好书</p>
    </div>
  </div>
  <p class="notice">温馨提示：<br>1.长按二维码白色区域，将图片保存到本地，便于以后发给好友！<br> 2.您也可以直接把 邀请码告诉别人，使用邀请码注册是一样的哦！ </p>
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
	    title: '维书会| '+'${nickName}'+'| 的推广码' , // 分享标题
	    desc: '扫码即可成为'+'${nickName}'+'的好友哦！', // 分享描述
	    link: '${sharePath}',  // 分享链接
	    imgUrl: '${qr_url}', // 分享图标
	    type: '', // 分享类型,music、video或link，不填默认为link
	    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
	    success: function () { 
	    },
	    cancel: function () { 
	    }
	});
	
	wx.onMenuShareTimeline({
	    title: '维书会| '+'${nickName}'+'| 的推广码', // 分享标题
	    link: '${sharePath}', // 分享链接
	    imgUrl: '${qr_url}', // 分享图标
	    success: function () { 
	    },
	    cancel: function () { 
	    }
	});
	
});
</script>


</body>
</html>