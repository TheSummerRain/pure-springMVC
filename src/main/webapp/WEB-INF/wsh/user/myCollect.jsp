<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" ng-app>
<title>我的收藏</title>
<head>
<%@include file="/extjsp/jsphead.jsp" %>
<%-- <script type="text/javascript" src="${ctx}/reswsh/js/iscroll.js"></script> --%>
</head>
<!-- <script type="text/javascript">

var myScroll,
  pullUpEl, pullUpOffset,
  generatedCount = 0;
/**
 * 滚动翻页 （自定义实现此方法）
 * myScroll.refresh();    // 数据加载完成后，调用界面更新方法
 */
function pullUpAction () {
  setTimeout(function () { 
   var html = [];
   html[html.length] = '<li>';
   html[html.length] = '<a href="javascript:;">';
   html[html.length] = '  <div class="left">';
   html[html.length] = '    <p class="font_14">从0到1</p>';
   html[html.length] = '  </div>';
   html[html.length] = '  <div class="right">';
   html[html.length] = '    <span>来自“维”</span>';
   html[html.length] = '    <p>2015-12-31  14：25</p>';
   html[html.length] = '  </div>';
   html[html.length] = '</a>';
   html[html.length] = '</li>';
   html = html.join("");
   $("#thelist").append(html);
    myScroll.refresh(); 
  }, 1000); // <-- Simulate network congestion, remove setTimeout from production!
}

function loaded() {
  pullUpEl = document.getElementById('pullUp'); 
  pullUpOffset = pullUpEl.offsetHeight;
  
  myScroll = new iScroll('wrapper2', {
    scrollbarClass: 'myScrollbar', /* 重要样式 */
    useTransition: false, /* 此属性不知用意，本人从true改为false */
    // topOffset: pullDownOffset,
    onRefresh: function () {
      if (pullUpEl.className.match('loading')) {
        pullUpEl.className = '';
        pullUpEl.querySelector('.pullUpLabel').innerHTML = '上拉加载更多...';
      }
    },
    onScrollMove: function () {
      if (this.y < (this.maxScrollY - 5) && !pullUpEl.className.match('flip')) {
        pullUpEl.className = 'flip';
        pullUpEl.querySelector('.pullUpLabel').innerHTML = '松手开始更新...';
        this.maxScrollY = this.maxScrollY;
      } else if (this.y > (this.maxScrollY + 5) && pullUpEl.className.match('flip')) {
        pullUpEl.className = '';
        pullUpEl.querySelector('.pullUpLabel').innerHTML = '上拉加载更多...';
        this.maxScrollY = pullUpOffset;
      }
    },
    onScrollEnd: function () {
      if (pullUpEl.className.match('flip')) {
        pullUpEl.className = 'loading';
        pullUpEl.querySelector('.pullUpLabel').innerHTML = '加载中...';        
        pullUpAction();   // Execute custom function (ajax call?)
      }
    }
  });
  
  setTimeout(function () { document.getElementById('wrapper2').style.left = '0'; }, 800);
}

//初始化绑定iScroll控件 
document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
document.addEventListener('DOMContentLoaded', loaded, false); 

</script> -->
<body class="yue_bg">
<header id="header" class="header clear">
  <p class="font_18 tit">历史收藏</p>
</header>
<div class="container" id="wrapper2">
  <div id="scroller2">
    <ul class="recharge_record recharge_shoucang" id="thelist">
	  
	  <c:forEach items="${listart}" var="art">
	    <li>
        <a href="${ctx}${baseCallPath}${art.artid}"> <!-- 需要结合artid于自定义请求路径。 -->
          <div class="left">
            <p class="font_14">${art.title}</p>
          </div>
          <div class="right">
            <span>来自 “${labetype[art.fromType]}”</span>
            <p><fmt:formatDate value='${art.creadtime}' type="both"/></p>
          </div>
        </a>
      </li>
	  </c:forEach>
      
    </ul> 
    
  </div>
<!--   <div id="pullUp">
    <span class="pullUpLabel">上拉加载更多...</span>
  </div> -->
</div>
</body>
</html>