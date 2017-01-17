<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" ng-app>
 
<head>
<title>维书会-会</title>
<%@include file="/extjsp/jsphead.jsp" %>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
</head>
<body class="yue_bg">
	<div class="container container3">
		<div class="usercenter">
			<!-- 判断图片路径是否为空，为空就使用默认图片 -->
			<div class="left img">
				<img src="${user.ossheadpic }" alt="${user.nickname}">
			</div>
			<div class="usercenter_info">
				<p>下午好，${user.nickname}</p>
				<p>会员等级：<c:if test="${user.viplv==0}"> 无 </c:if> <c:if test="${user.viplv==1}"> 一级会员</c:if>
				
				</p>
				<p class="edit">
					<a href="<%=basePath%>/user/touserinfopage">编辑资料</a>
				</p>
			</div>
		</div>
		<ul class="usercenter_opt">
			<li><a href="<%=basePath%>/user/toSearchMyCredits">
					<p>我的积分</p>
					<p>0</p>    <!-- 这里没用了。 -->
			</a></li>
			<li><a href="#"> <!-- <%=basePath%>/user/toMyWBPage -->
					<p>我的维币</p>  
					<p>${weibi}</p>  <!-- 直接从钱包取数据就行了。 -->
			</a></li>
			<li>
			<c:if test="${ user.viplv == 0}">
					<a href="javascript:toopenmember()">     
						<p style="margin-top:10px;font-size: 20px;color: yellow;"><font style="border-bottom: 1px solid yellow">开通会员</font></p>
					</a>
				</c:if>
				
			      <c:if test="${ user.viplv == 1 }">
					<c:if test="${vipOutDate==true}">
						<!-- 会员到期 -->
						<a href="<%=basePath%>/user/openMember"> 
							<p>会员到期日</p>
							<p>
								<fmt:formatDate value='${user.vipendtime}' type="date"
									dateStyle='medium' /> <font color="red">点我续费</font>
							</p>
						</a>
					</c:if>
					<c:if test="${vipOutDate==false}">
						<!-- 没有到期，则只显示 -->
						<a> <!-- 纯粹为了格式，增加A标签 -->
							<p>会员到期日</p>
							<p>
								<fmt:formatDate value='${user.vipendtime}' type="date"
									dateStyle='medium' />
							</p>
						</a>
					</c:if>
				</c:if>
			</li>
		</ul>
	</div>
	
<c:if test="${user.viplv==1  && isMaster }">
<ul class="msz_text usercenter_text2">

<!-- 这里需要改的地方比较多 -->

  <li>
    <a href="${ctx}/popularize/masterPage"><em class="ban3">&nbsp;</em>推广大使
      <span class="glyphicon glyphicon-menu-right"></span>
    </a>           
  </li>
  
 
   	<%-- <c:if test="${user.agent == false }">
		 	<li> <a href="javascript:becomeAgent();"><em class="ban3 ban3_0_1">&nbsp;</em>成为代理商
				<span class="glyphicon glyphicon-menu-right"></span> </a></li>
	</c:if>
	<c:if test="${user.agent == true }">
		 	<li> <a href="javascript:toAgentCenter();"><em class="ban3 ban3_0_2">&nbsp;</em>代理收入
				<span class="glyphicon glyphicon-menu-right"></span> </a></li>
	</c:if>
   --%>
   
     <li><a href="<%=basePath%>/wxpay/toWithdrawPage"><em class="ban3 ban3_0_3">&nbsp;</em>提现
				<span class="glyphicon glyphicon-menu-right"></span> </a></li>	
 
  

  
</ul>
</c:if>
	<ul class="msz_text usercenter_text">
		<li><a href="javascript:vipquanyi();"><em class="ban3 ban3_0_4">&nbsp;</em>会员权益
				<span class="glyphicon glyphicon-menu-right"></span> </a></li>
		  
		  <!-- Wally add by 2016.4.12 秀一秀 -->	
		   <c:if test="${user.vipId != null && user.vipId !='' }">	
			<li><a href="javascript:toShowMyStudyPage();"><em class="ban3 ban3_0_5">&nbsp;</em>晒晒我读的书
				<span class="glyphicon glyphicon-menu-right"></span> </a></li>	
			</c:if>	
			
			<li><a href="javascript:announcement();"><em class="ban3 ban3_0_6">&nbsp;</em>重要公告
				<span class="glyphicon glyphicon-menu-right"></span> </a></li>	
			
			
	</ul>
		
	
	<c:if test="${!isMaster}">
	<div class="tuiguang"  id="becomesMaster">
		<a href="javscript:void(0);"><img src="<%=basePath %>/reswsh/images/img2.png" alt=""  ></a>
	</div>
	</c:if>
<jsp:include page="/extjsp/jspfoot.jsp"></jsp:include>


<c:if test="${empty user.vipId  && !isMaster }">
<div class="wsh_mask" id="wsh_mask"></div>
<div class="wsh_content" id="wsh_content">
  <div class="pop_tit">
    <h4>提示</h4>
    <p class="pop_body">你还不是会员哦！成为会员才能申请成为推广大使。</p>
    <div class="pop_btn">
      <a href="javascript:void(0);" class="close_pop" id="close_pop">放弃申请</a>
      <a href="${ctx }/user/openMember">成为会员</a>
    </div>
  </div>
</div>	
</c:if>

<%-- 
<c:if test="${not empty user.vipId  && !isMaster }">
<div class="wsh_mask" id="wsh_mask"></div>
<div class="wsh_content" id="wsh_content">
  <div class="pop_tit">
    <h4>提示</h4>
    <p class="pop_body">我为“维书会”代言,<br>
                        践行“全民阅读·大手拉小手”！<br>
                        以维书会为火炬<br>          
       	 点燃全民阅读的热情.<br>
                       在我们读书精进的同时,<br>
       	给山区的孩子带去读书的希望！<br>
                       你确定成为“全民阅读”推广大使吗？   <br>
        </p>
    <div class="pop_btn">
      <a href="javascript:void(0);" class="close_pop" id="close_pop">放弃申请</a>
      <a href="${ctx}/popularize/becomesMaster">推广大使</a>
    </div>
  </div>
</div>	
</c:if> --%>

<div class="wsh_mask" id="quanyi"></div>
<div class="wsh_content" id="vip_quanyi">
  <div class="pop_tit">
    <h4>提示</h4>
    <p class="pop_body">
    	1、一年精彩解读50本书.<br>
    	2、优先获得地方站组织的书友会名额.<br>
    	3、每位会员将得到一套精美骨瓷杯礼品哦！<strong>记得填写收货地址.</strong><br>
    	<font color="red">注：会员头像旁边->【编辑资料】->【绑定收货地址】.</font>
    </p>
    <div class="pop_btn">
      <a href="javascript:void(0);" id="close_popquanyi" style="float: right;width: 100%"> 确定</a>
    </div>
  </div>
</div>

<!-- 重要公告 -->
<div class="wsh_content" id="announcement_z">
  <div class="pop_tit">
    <h4>5月20号更新公告</h4>
    <p class="pop_body">
    	1、针对 推广大使人气 数据规则做了从新调整,从最初包含空数据到现在过滤空数据，所以可能您的人气数据有所下降，这个不影响您的收入，请不用担心。<br>
    	<!-- 2、开通了代理商功能，会员用户可以继续升级代理商权限，享受更多待遇哦。<br> -->
    </p>
    <div class="pop_btn">
      <a href="javascript:hideAnnouncement();" id="hideannoun_btn" style="float: right;width: 100%"> 确定</a>
    </div>
  </div>
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
	wx.hideOptionMenu();
});
function toopenmember(){
	$.post("<%=basePath%>/user/checkUsermobile",{},function(data){
		if(data=='2'){
			window.location.href="<%=basePath%>/user/joinMembership";
		}else if(data=='1'){
			if(confirm("请先绑定手机号！")){
				window.location.href="<%=basePath%>/user/toupdatmobipage?comepage=2";
			}
		}else{
			alert("请求异常，请稍后再试！");
		}
	});
}

function quanyi(){
	alert("敬请期待");
}
function vipquanyi(){
	$("#vip_quanyi,#quanyi").show();
}

function announcement(){
	$("#announcement_z").show();
}
function hideAnnouncement (){
	$("#announcement_z").hide();
}


function becomeAgent(){
	window.location.href="<%=basePath%>/agent/toAgentProtocolPage";
}

function toAgentCenter(){
	window.location.href="<%=basePath%>/agent/toAgentCenter";
}



function toShowMyStudyPage(){
	window.location.href="<%=basePath%>/showMe/tocreateMyBookList";
}

</script>

</body>
</html>