<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" ng-app>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>${flag=='renewal' ? '会员续费' : '开通会员' }</title>
<%@include file="/extjsp/jsphead.jsp"%>
<link rel="stylesheet" href="<%=basePath%>/reswsh/style/weui.min.css"/>
<script type="text/javascript" src="<%=basePath%>/reswsh/js/weui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/reswsh/js/zepto.min.js"></script>
</head>
<body style=" background-color: #99999;">
<div class="weui-cells weui-cells_form" style="margin-top:0;" >
     <div class="weui-cell" id="nameDiv" style="display: ${flag == 'renewal' ? 'none' : ''}">
        <div class="weui-cell__hd"><label for="" class="weui-label">姓&nbsp;&nbsp;&nbsp;&nbsp;名</label></div>
        <div class="weui-cell__bd">
            <input class="weui-input" value="" placeholder="请输入姓名" id="name"/>
        </div>
        <div class="weui-cell__ft">
            <i class="weui-icon-warn"></i>
        </div>
    </div>

    <div class="weui-cell" id="telDiv">
        <div class="weui-cell__hd" id="_yzm_">
            <label class="weui-label">手机号</label>
        </div>
        <div class="weui-cell__bd">
            <input class="weui-input" type="tel" id="tel" placeholder="请输入手机号">
        </div>
        <div class="weui-cell__ft">
            <button class="weui-vcode-btn" id="timex">验证码</button>
        </div>
    </div>

     <div class="weui-cell" id="yzmDiv">
        <div class="weui-cell__hd"><label class="weui-label">验证码</label></div>
        <div class="weui-cell__bd">
            <input class="weui-input" type="number"  placeholder="请输入验证码" id="yzm"/>
        </div>
         <div class="weui-cell__ft">
            <i class="weui-icon-warn"></i>
        </div>
     </div>

    <div class="weui-cell" id="numDiv">
        <div class="weui-cell__hd"><label for="" class="weui-label">卡&nbsp;&nbsp;&nbsp;&nbsp;号</label></div>
        <div class="weui-cell__bd">
            <input class="weui-input" id="num" type="number" pattern="[0-9]*" value="" placeholder="请输入卡号"/>
        </div>
        <div class="weui-cell__ft">
            <i class="weui-icon-warn"></i>
        </div>
    </div>

    <div class="weui-cell" id="keyDiv">
        <div class="weui-cell__hd"><label for="" class="weui-label">密&nbsp;&nbsp;&nbsp;&nbsp;钥</label></div>
        <div class="weui-cell__bd">
            <input class="weui-input" id="key" type="number" pattern="[0-9]*" value="" placeholder="请输入密钥"/>
        </div>
        <div class="weui-cell__ft">
            <i class="weui-icon-warn"></i>
        </div>
    </div>
    <c:if test="${ empty flag }">
     <div class="weui-cell" >
        <div class="weui-cell__hd"><label for="" class="weui-label">邀请码</label></div>
        <div class="weui-cell__bd">
            <input class="weui-input" type="number" pattern="[0-9]*" value=""  id="pId" placeholder="请输入邀请码"/>
        </div>
        <div class="weui-cell__ft">
            <i class="weui-icon-warn"></i>
        </div>
    </div>
    </c:if>
</div>
 <div class="weui-btn-area">
        <a class="weui-btn weui-btn_primary" href="javascript:" id="showTooltips">确定</a>
 </div>
  <label for="weuiAgree" class="weui-agree"> <span class="weui-agree__text">
    点击“确定”按钮，即表示同意<a href="<%=basePath%>/user/showMeProtocol?comepage=4">会员服务协议</a> </span>
   </label>

<!-- loading toast -->
    <div id="loadingToast" style="display:none;">
        <div class="weui-mask_transparent"></div>
        <div class="weui-toast">
            <i class="weui-loading weui-icon_toast"></i>
            <p class="weui-toast__content">数据提交中</p>
        </div>
    </div>
<!-- loading toast -->
 <input id="basePaths" value="<%=basePath %>" type="hidden">
</body>
<script type="text/javascript">
    $(function(){
        var baPaths = $("#basePaths").val();
        $("#name,#tel,#yzm,#num,#key").blur(function(){
            var id=$(this).attr("id");
            if(id=="tel"){
                var reg_phone = /^1[3,4,5,8,7]\d{9}$/;
                if(!reg_phone.test($(this).val())){
                    $("#_yzm_").css('color','#e64340');
                }else{
                    $("#_yzm_").css('color','#000');
                    $("#"+id+"Div").removeClass("weui-cell_warn");
                }
            }

            if(!$(this).val()){
                $("#"+id+"Div").addClass("weui-cell_warn");
            }else{
                $("#"+id+"Div").removeClass("weui-cell_warn");
            }
        });


        var events = [];
            events.push("name");
            events.push("yzm");
            events.push("num");
            events.push("key");
        $("#showTooltips").click(function(){
            var flag=true;
            var $loadingToast = $('#loadingToast');
             for(var i=0,l=events.length;i<l;i++){
                   if("${flag}"=="renewal"){
                        if("name"==events[i]){
                            continue;
                        }
                    }
                  if(!$("#"+events[i]).val()){
                     $("#"+events[i]+"Div").addClass("weui-cell_warn");
                      flag=false;
                   }else{
                       $("#"+events[i]+"Div").removeClass("weui-cell_warn");
                   }
             }

            var reg_phone = /^1[3,4,5,8,7]\d{9}$/;
            if(!reg_phone.test($("#tel").val())){
               $("#telDiv").addClass("weui-cell_warn");
                flag=false;
            }else{
                $("#_yzm_").css('color','#000');
                $("#telDiv").removeClass("weui-cell_warn");
            }
            if(flag){
                if ($loadingToast.css('display') != 'none') {
                    return;
                }
                $loadingToast.fadeIn(100);
                 var pId=0;
                 if($("#pId").length>0){
                    pId=$("#pId").val();
                 }
                 $.post(baPaths+"/user/card", {
                    "num" : $("#num").val(),
                    "keyt" : $("#key").val(),
                    "ownerMobile" : $("#tel").val(),
                    "ownerName" : $("#name").val(),
                    "pId" : pId,
                    "flag" : "${flag}",
                    "verification" : $("#yzm").val()
                 }, function(data){
                     if(data){
                       if(data == "2" ){
                            location.replace(baPaths+"/user/toVipSucPage");
                       }else if(data == "3"){
                           location.replace(baPaths+"/user/toVipSucPage?flag=renewal");
                       }else if(data=="1"){
                             alert("您已经是维书会会员");
                       }else if(data=="-1"){
                           alert("读书卡卡号密钥无效");
                       }else if(data=="-2"){
                           alert("手机验证码无效");
                       }else{
                            alert("网络异常,清稍后再试!");
                            location.replace(baPaths+"/user/toUserCenterPage");
                       }
                        $loadingToast.fadeOut(100);
                     }
               });//End Post;
            }
        });

    $("#timex").bind("click", function(){
    	var phone = $("#tel").val();
    	var reg_phone = /^1[3,4,5,8,7]\d{9}$/;
    	if(!reg_phone.test(phone)){
    	    $("#_yzm_").css('color','#e64340');
    	    return ;
    	}
    	//校验是否存在
        if("${flag}"=="renewal"){//续费
            if(!checkHasMobile(phone)){
               alert("手机号码不存在");
                return;
            }
        }else{//开通会员
            if(checkHasMobile(phone)){
               alert("手机号码已经存在");
                return;
            }
        }
    	settime($(this)[0]);
        $.post(baPaths+"/user/tosendmsg", {phone : phone}, function(data){
          if(data){
            if(data == "1"){
            }
          }
        });//End Post
    });


//END
});

var countdown=60;
var timeVal;
function settime(val) {
    if (countdown == 0) {
        val.removeAttribute("disabled");
         val.innerText="验证码";
        countdown = 60;
        return;
    } else {
        val.setAttribute("disabled", true);
            val.innerText=countdown+"s";
        countdown--;
    }
    setTimeout(function() {
        settime(val);
    },1000);
}

 //校验手机号
 function checkHasMobile(data){
	 if(data.trim() == ''){
		 return false;
	 }
	 var existed=false;
	 var baspas = $("#basePaths").val()+"/user/hasBindMobile";
	 $.ajax({
	     type:'GET',
	     url: baspas,
	     async:false,
         data: {"mobile":data.trim()} ,
	     dataType: 'json',
	     success: function(red) {
	    	 if(red=="1"){
	    	    //手机号不存在
	    	    result = false;
			 }else if(red="2"){
			   //手机号已经存在
                result = true;
			 }
	     }
	});
	return result;
 }

</script>
</html>
