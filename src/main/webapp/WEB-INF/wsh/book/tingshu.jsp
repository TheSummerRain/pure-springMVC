<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" ng-app>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${art.artTitle}</title>
<%@include file="/extjsp/jsphead.jsp" %>
<!-- 请考虑，会员权限的问题。视频 -->
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<link href="https://res.wx.qq.com/open/libs/weui/0.4.0/weui.css" rel="stylesheet">

<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
<%-- <script src="${ctx}/reswsh/js/weixinAudio.js"></script> --%>

<script src="${ctx}/reswsh/js/jquery.jplayer.js"></script>
<script src="${ctx}/reswsh/js/rem.js"></script>
</head>

<style type="text/css">
		*{
			margin:0;
			padding:0;
		}
		.audio{
			width: 6.8rem;
			height: 0.7rem;
			background: #000;
			margin: auto;
			border-radius: 4px;
			overflow: hidden;
			margin-top: 10px;
    		margin-bottom: 12px;
		}
		a.jp-play{
			width:0.3rem;
			height:24px;
			display:block;
			background: url("${ctx}/reswsh/img/pause.png") no-repeat;
			background-size:100%;
			margin-left: 0.28rem;
			margin-top: 0.16rem;
		}

		a.jp-pause {
			width:0.3rem;
			height:24px;
			display:block;
			background: url("${ctx}/reswsh/img/play.png") no-repeat;
			background-size:100%;
			margin-left: 0.28rem;
			margin-top: 0.16rem;
		}
		.book-title{
			width: 0.8rem;
			height: 0.68rem;
		}
		#jp_container_1{
			float: left;
			font-size: 12px;
		}
		.kjt{
			width:81%;
			height:20px;
			position:relative;
		}
		.star-time{
			width:0.6rem;
			height:0.2rem;
			font-size:0.26rem;
			color:#fff;
			position: absolute;
			top: 0.16rem;
			left: 4.2rem;
		}
		.gang{
			font-size:0.26rem;
			color:#fff;
			position: absolute;
			top: 0.16rem;
			left: 4.92rem;
		}
		.end-time{
			width:0.6rem;
			height:0.2rem;
			font-size:0.26rem;
			color:#fff;
			position: absolute;
			top: 0.16rem;
			left: 5.02rem;
		}
		.bar{
			width:3.7rem;
			background:#fff;
			position:absolute;
			height:0.12rem;
			top:0.3rem;
			left:0.12rem;
		}
		div.jp-seek-bar {
			background: #fff;
			height:100%;
			cursor: pointer;
		}
		div.jp-play-bar {
			background:#1eac50;
			width:0px;
			height:100%;
		}
		a{
			color: #fff;
		}
		
	

	</style>

<body class="yue_bg">
	<c:if test="${UserVip=='Y'}">
		<input id="ziyuan" type="hidden" value="${art.vipVedio}"/>
	</c:if>
	<c:if test="${UserVip=='N'}">
		<input id="ziyuan" type="hidden" value="${art.freeVedio}"/>
	</c:if> 
	<div class="container container2">
		<div class="container_wrap">
			<h4 class="wsh_shu_info_bt">${art.artTitle}</h4>
			<p class="wsh_shu_info">
				<span><fmt:formatDate value='${art.artPublishtime}' type="date" dateStyle='medium' /></span> 
				<span>解读：李维</span>
				<c:if test="${isShare==false}">
					<c:if test="${UserVip=='N'}">
						<a href="<%=basePath%>/user/openMember">听完整版</a>	  
			    	</c:if>
			    </c:if>
			</p>
				

		<div class="audio">
			<div class="book-title" id="jp_container_1" class="jp-audio">
				<div id="jquery_jplayer_1" class="jp-jplayer"></div>
				<div class="jp-type-single">
					<div class="jp-gui jp-interface">
						<a href="javascript:;" class="jp-play" tabindex="1"></a>
						<a href="javascript:;" class="jp-pause" tabindex="1"></a>
					</div>
				</div>
			</div>
			<!--下边是快进条-->
			<div id="jp_container_1">
				<div class="kjt jp-time-holder">
					<div class="bar jp-progress">
						<div class="jp-seek-bar">
						  <div class="jp-play-bar"></div>
						</div>
					</div>
					<div class="star-time jp-current-time"></div>
					<div class="gang">/</div>
					<div class="end-time jp-duration"></div>
				</div>
			</div>
		</div>
	

	<%-- 		<div class="video" id="CuPlayer">
				<audio controls="controls"> 
				
				<c:if test="${UserVip=='Y'}">
					<source	src="${art.vipVedio}" /> 
				</c:if>
				<c:if test="${UserVip=='N'}">
					<source	src="${art.freeVedio}" />
				</c:if>
			  </audio>
			</div> --%>
	
			<div class="wsh_shu_info_p">
			
			<c:if test="${UserVip=='Y'}">
					${art.content}
			</c:if>
				
			<c:if test="${UserVip=='N'}">
					${art.contentFree}
			</c:if>
				
			</div>
		<p class="shengming">
			声明：<a href="#"> 文章由维书会工作人员精心整理分享，未经允许，严禁转载</a>
		</p>
		
		<div class="toolbar">
	      <div class="left">阅读<span>${readcount}</span></div>
	    
	   <c:if test="${isShare == false }">  <!-- 分享页面关闭 评论功能显示。，需要客服分享 授权的问题后才能开通。 -->
	      <div class="right">
	      <!--   <a href="#"><span class="ban2">&nbsp;</span><em class="zan"></em></a> -->
	        <a href="javascript:tocommentPage()" class="ping"><span class="ban2 ban2_0_2">&nbsp;</span><em class="xin">写评论</em></a>
	      </div>
	   </c:if>
	    </div>
	</div>
	</div>
	
	
	
	<!-- ======================================评论区====================================== -->
	 <c:if test="${isShare == false }">
	<div class="wsh_shu_pinglun">
	  <div class="pl_tit"><span>精彩评论</span></div>
	  <ul class="pl_list" id="Wonderfulpl">
	 	 <p id="noPltext" style="text-align: center;margin:25px;color: #777"> </p>
	  </ul>
	</div>
	</c:if>
	
<form action="${ctx}/comment/toCommentPage" id="hidform" method="post">
	<input type="hidden" id="Fromuserid" name="userid" value="${uinf.userId}">
	<input type="hidden" name="headpic" value="${uinf.headPic}">
	<input type="hidden" name="nickname" value="${uinf.nickname}">
	<input type="hidden" name="artid" value="${art.artId}">
	<input type="hidden" name="arttitle" value="${art.artTitle}">
</form>

<div class="weui_dialog_confirm" id="dialog1" style="display: none;">
        <div class="weui_mask"></div>
        <div class="weui_dialog">
            <div class="weui_dialog_hd"><strong class="weui_dialog_title">提示</strong></div>
            <div class="weui_dialog_bd">您尚未开通会员，需要会员权限才能评论，马上开通？</div>
            <div class="weui_dialog_ft">
                <a href="javascript:hiddenDialog();" class="weui_btn_dialog default">取消</a>
                <a href="javascript:openMember();" class="weui_btn_dialog primary">确定</a>
            </div>
        </div>
 </div>
 


<script  type="text/javascript">
$(document).ready(function(){
	
	var kkxabc = $("#ziyuan").val();
	
	$("#jquery_jplayer_1").jPlayer({
		ready: function (event) {
			$(this).jPlayer("setMedia", {
				mp3:kkxabc
			});
		},
		timeupdate: function(event) {
			if(event.jPlayer.status.currentTime==0){
				time = "";
			}else {
				time = event.jPlayer.status.currentTime;
			}
		},
		swfPath: "/js",  		//存放jplayer.swf的决定路径
		solution:"html, flash", //支持的页面
		supplied: "mp3",		//支持的音频的格式
		wmode: "window"
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
	    jsApiList: ['onMenuShareAppMessage','onMenuShareTimeline']  // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
 wx.ready(function(){

    wx.showOptionMenu();
 	wx.onMenuShareAppMessage({
	    title: '维书会|'+'${art.artTitle}', 
	    desc: '${art.artIntro}',
	    link: '${sharePath}'+'${art.artId}',
	    imgUrl:'http://bookclubfree.oss-cn-beijing.aliyuncs.com/pushPIC%2F495302538226592391.jpg' ,         
	    type: '', 
	    dataUrl: '', 
	    success: function () { 
	    },
	    cancel: function () { 
	    }
	});

	 wx.onMenuShareTimeline({
		    title: '维书会|'+'${art.artTitle}', 
		    link: '${sharePath}'+'${art.artId}', 
		    imgUrl: 'http://bookclubfree.oss-cn-beijing.aliyuncs.com/pushPIC%2F495302538226592391.jpg', 
		    success: function () { 
		    },
		    cancel: function () { 
		    }
		});
 });
 
 wx.error(function(res){
 });
 </script>
 
<script type="text/javascript">
$(function () { 
	// 默认只查询10条 精选，按最新的日期选。不会显示更多。 没必要。增加压力。
	var beginNum = 0;
	var artid =${art.artId};
	var userid = null;
	if (typeof($("#Fromuserid").val()) != "undefined") { 
		userid=$("#Fromuserid").val();
	}  
	var bot = 50; //bot是底部距离的高度
    $(window).scroll(function () {
        if (( '${isShare}' != 'true') && ((bot + $(window).scrollTop()) > ($(document).height() - $(window).height()))) {
           //当底部基本距离+滚动的高度〉=文档的高度-窗体的高度时；
            $.ajax({
                type: 'post',
                async: false,
                url: "<%=basePath%>/comment/getTen_CommnetsList",   
                data: {"beginNum":beginNum,"artid":artid},   
                success: function(data){   
                	beginNum=Number(beginNum)+10;
                	var jsonObj=eval("("+data+")");  
                    $.each(jsonObj, function(i,item){   
                    	var status = "";
						if(item.plZan){
							status="<span class='right' id='zanc_"+item.cid+"'><a><img style='width: 18px;height: 16px' src='<%=basePath%>/baseimgs/user/zan.png'>&nbsp;</img>"+item.countZan+"</a></span>"; //换成已经点赞的图标。
                    	}else{
                    		status="<span class='right' id='zanc_"+item.cid+"'><a href='javascript:dianzan("+item.cid+","+userid+")'><img style='width: 18px;height: 16px' src='<%=basePath%>/baseimgs/user/weizan.png'>&nbsp;</img>"+item.countZan+"</a></span>";
                    	}                  	
                    	 $("#Wonderfulpl").append("<li>" 
                	       +"<div class='left img'>"
                	       +"<img src='"+item.headpic+"' alt=''>" 
                	       +"</div>"
                	       +"<div class='pl_info'>"
                	       +"<div class='pl_info_t'>"
                	       +"<span class='name left'>"+item.nickname+"</span>"   
                	       +status
                	       +"</div>"
                	       +"<p>"+item.ctext+"</p>" 
                	       +"<p class='date'>"+item.createtime+"</p>"	
                	       +"</div>"
                	       +"</li>");
                    });
                    if(jsonObj.length == 0){
               		 if($("#Wonderfulpl").has("li").length == 0){
               			$("#noPltext").html("暂时还没有哦，赶快去评论吧！"); 
               		 }
               	}
                }
            });
        }
    });
});


function tocommentPage(){ 
	if('${UserVip}'=='N'){// 不是会员没有：评论的权限。
		$("#dialog1").show();
		return;
	}
	document.getElementById("hidform").submit();
}

function hiddenDialog(){
	$("#dialog1").hide();
}

function openMember(){
	$("#dialog1").hide();
	$.post("<%=basePath%>/user/checkUsermobile",{},function(data){
		if(data=='2'){
			window.location.href="<%=basePath%>/user/openMember";	
		}else if(data=='1'){
			if(confirm("请先绑定手机号！")){
				window.location.href="<%=basePath%>/user/toupdatmobipage?topage=1";
			}
		}else{
			alert("请求异常，请稍后再试！");
		}
	});
}

function dianzan(cid,userid){
	 var abc = $("#zanc_"+cid).text();
	 $("#zanc_"+cid).html("<img style='width: 18px;height: 16px' src='<%=basePath%>/baseimgs/user/zan.png'>&nbsp;</img>"+(Number(abc)+1));  //换成已经点赞的图标
	 $.post('<%=basePath%>/comment/zanAction' ,{'cid':cid,'userid':userid},function(data){}); 
}
</script>
</body>
</html>