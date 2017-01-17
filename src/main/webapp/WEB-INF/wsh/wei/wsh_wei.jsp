<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" ng-app>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>维书会-维</title>
<%@include file="/extjsp/jsphead.jsp" %>
 <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
</head>
<body>
<input type="hidden" id="countNm" value="${countNm}">
<input type="hidden" id="type" value="${type}">

<div class="container">
  <ul class="wei_list">
    
    <c:if test="${listsize == 0}">
    <p align="center"> <font color="red">没有数据</font>  </p>
    </c:if>
    
    <c:forEach items="${artList}" var="art">
    <li>
      <div class="li_wrap">
        <a href="<%=basePath %>/wei/showOneInfo/${art.artId}" class="a_area">
          <p> <img src="${art.wei_picUrl}" alt=""></p>
          <h4>${art.artTitle}</h4>
          <p class="txt">${art.artIntro}</p>
        </a>
    <%--     <div class="wei_opt">
          <div class="left">
          <span class="ban">&nbsp;</span>  	
        	 ${hashLable[art.artLabel]}
		 </div>
          <div class="right">
           <!--  <a href="#"><span class="ban ban_0_1">&nbsp;</span><em class="ping">33</em></a>
            <a href="#"><span class="ban ban_0_2">&nbsp;</span><em class="zan">90</em></a> -->
          </div>
        </div> --%>
      </div>
    </li>
    </c:forEach>
  </ul>
  
</div>
	<jsp:include page="/extjsp/jspfoot.jsp"></jsp:include>
	
	
	
<script type="text/javascript">
$(function () { //本人习惯这样写了
	var countNum = $("#countNm").val();
	var beginNum = 0;
	var type =$("#type").val();
	var lables=new Array("","简介","作品","推荐","会员心得","公益","荐书");
    $(window).scroll(function () {
        var bot = 50; //bot是底部距离的高度
        if ((bot + $(window).scrollTop()) >= ($(document).height() - $(window).height())) {
           //当底部基本距离+滚动的高度〉=文档的高度-窗体的高度时；
        	beginNum=Number(beginNum)+Number(countNum);
        	//需要判断是否需要带分类
            $.ajax({   
                type: 'post',   
                url: "<%=basePath%>/wei/getWeiAllListPage",        
                data: {"beginNum":beginNum,"type":type},   
                success: function(data){   
                	var jsonObj=eval("("+data+")");  
                    $.each(jsonObj, function(i,item){ 
                        $(".wei_list").append( "<li>"
                        +"<div class='li_wrap'>"
                        +"<a href='<%=basePath %>/wei/showOneInfo/"+item.artId+"' class='a_area'>"
                        +" <p> <img src='"+item.wei_picUrl+"' alt=''></p>"
                        +"<h4>"+item.artTitle+"</h4>"
                        +" <p class='txt'>"+item.artIntro+"</p>"
                        +" </a>"
                        +" <div class='wei_opt'>"
                        +" <div class='left'>"
                        +" <span class='ban'>&nbsp;</span>"+lables[item.artLabel]
                        +"  </div> "
                        +" <div class='right'>"
                        +"  <a href='#'><span class='ban ban_0_1'>&nbsp;</span><em class='ping'>33</em></a> "
                        +"  <a href='#'><span class='ban ban_0_2'>&nbsp;</span><em class='zan'>90</em></a> "
                        +"</div>"
                        +"</div>"
                        +"</div>"
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

</script>
</body>
</html>