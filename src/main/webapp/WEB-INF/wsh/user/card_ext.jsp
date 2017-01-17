<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" ng-app>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>成为会员</title>
<%@include file="/extjsp/jsphead.jsp"%>
<link rel="stylesheet" href="${ctx}/reswsh/style/weui.min.css"/>
<script src="${ctx}/reswsh/js/jquery-weui.js" ></script>


	<style type="text/css">
		*{margin: 0;padding: 0}
		body{
			font-size: 12px;
		}
		.information{
			width: 90%;
			padding: 0px 5%;
			height: 3.7rem;
			margin-top: 0.42rem;
			overflow: hidden;
		}
		.title{
			width: 6.7rem;
			height: 0.32rem;
			font-size: 0.3rem;
			margin-bottom: 0.52rem;
		}
		.title span{
			display: block;
			float: left;
			color: #787676;
		}
		.title span:nth-child(2){
			display: block;
			float: left;
			width: 3.08rem;
			text-align: center;
		}
		.border{
			width: 1.8rem;
			height: 1px;
			background: #464242;
			margin-top: 0.15rem;
		}
		.information-name{
			width: 6rem;
			height: 1rem;
			border: 1px solid #e5e5e5;
			margin: auto;
			border-radius: 0.16rem;
		}
		.information-name span{
			margin-top: 0.3rem;
			margin-left: 0.4rem;
			font-size: 0.3rem;
			width: 1.6rem;
			display: block;
			float: left;
		}
		.information-name span b{
			font-weight: normal;
			float: right;
		}
		.information-name input{
			margin-top: 0.16rem;
			display: block;
			float: left;
			border: none;
			height: 0.6rem;
		}
		.phone{
			margin-top: 0.3rem;
		}
		.cards{
			height: 2.42rem;
			width: 90%;
			padding: 0px 5%;
			height: 2.4rem;
			overflow: hidden;
		}
		.cards-number{
			width: 6rem;
			height: 1rem;
			border: 1px solid #e5e5e5;
			margin: auto;
			border-radius: 0.16rem;
		}
		.cards-number input{
			display: block;
			margin: 0.2rem auto 0;
			height: 0.6rem;
			border: none;
		}
		.key{
			height: 2.42rem;
			width: 90%;
			padding: 0px 5%;
			height: 2.52rem;
			overflow: hidden;
		}
		.key-number{
			width: 6rem;
			height: 1rem;
			margin: auto;
		}
		.key-number ul li{
			list-style: none;
			width: 1.28rem;
			height: 0.98rem;
			float: left;
			border: 1px solid #e5e5e5;
			margin-left: 0.14rem;
			border-radius: 0.16rem;
		}
		.key-number ul li:nth(1)-child{
			margin-left: 0;
		}
		.key-number input{
			width: 1.1rem;
			display: block;
			margin: auto;
			height: 0.6rem;
			margin-top: 0.16rem;
			border: none;
		}
		.invite{
			height: 2.42rem;
			width: 90%;
			padding: 0px 5%;
			height: 2.82rem;
			overflow: hidden;
		}
		.invite-number{
			width: 6rem;
			height: 1rem;
			border: 1px solid #e5e5e5;
			margin: auto;
			border-radius: 0.16rem;
		}
		.invite-number input{
			display: block;
			margin: 0.2rem auto 0;
			height: 0.6rem;
			border: none;
		}
		.agreement{
			height: 2.42rem;
			width: 90%;
			padding: 0px 5%;
			height: 0.9rem;
			overflow: hidden;
			font-size: 0.28rem;
		}
		.agreement input{
			width: 0.35rem;
			height: 0.35rem;

		}
		.span{
			text-decoration: underline;
		}
		.btn_abc{
			width: 6rem;
			height: 1rem;
			background: #00aa00;
			font-size: 0.32rem;
			border: none;
			margin: auto;
			text-align: center;
			line-height: 1rem;
			display: block;
			color: #fff;
		}
		#checked{
		   width:0.3rem;
		   height:0.3rem;
		}

	  input:required:invalid, input:focus:invalid {
                background-image: url(${ctx}/reswsh/images/invalid.png);
                background-position: right 0.18rem;
                background-repeat: no-repeat;

                display: inline-block;
                    vertical-align: middle;
                    font: normal normal normal 14px/1 "weui";
                    font-size: inherit;
                    text-rendering: auto;
                    -webkit-font-smoothing: antialiased

              }
              input:required:valid {
                background-image: url(${ctx}/reswsh/images/valid.png);
                background-position: right 0.18rem;
                background-repeat: no-repeat;
              }

	</style>

</head>
<body class="yue_bg">
<span id="_tips_"></span>
<form id="_form_">
<div class="information" >
    <div class="title">
        <span class="border"></span>
        <span>为哪个手机充值？</span>
        <span class="border"></span>
    </div>
    <div class="information-name">
        <span>姓<b>名：</b></span>
        <input type="text" name="ownerName" required="required"  style="width:3.6rem;" >
    </div>
    <div class="information-name phone">
        <span>手机号码：</span>
        <input type="email" name="ownerMobile" required="required"  oninvalid="this.setCustomValidity('手机号码必须正确填写')"/>
    </div>
</div>
<div class="cards">
    <div class="title">
        <span class="border"></span>
        <span>读书卡编号</span>
        <span class="border"></span>
    </div>
    <div class="cards-number">
        <input type="text" name="num" required="required"  oninvalid="this.setCustomValidity('姓名必须正确填写')">
    </div>
</div>
<div class="key">
    <div class="title">
        <span class="border"></span>
        <span>读书卡秘钥</span>
        <span class="border"></span>
    </div>
    <div class="key-number">
        <ul>
            <li><input type="" id="keyt1" name="keyt1" index="1" maxlength="4"></li>
            <li><input type="" id="keyt2" name="keyt2" index="2" maxlength="4"></li>
            <li><input type="" id="keyt3" name="keyt3" index="3" maxlength="4"></li>
            <li><input type="" id="keyt4" name="keyt4" index="4" maxlength="4"></li>
        </ul>
    </div>
</div>
<div class="invite">
    <div class="title">
        <span class="border"></span>
        <span>邀请码(选填)</span>
        <span class="border"></span>
    </div>
    <div class="invite-number">
        <input type="text" name="pId">
    </div>
</div>
<div class="agreement">
    <input type="checkbox" checked="checked" id="checked" >
    <label for="checked" style="font-weight:normal"> <span> 阅读并同意<span class="span"><a href="${ctx}/user/showMeProtocol" style='text-decoration:none;' > 服务协议 </a></span></span></label>
</div>
<button class="btn_abc" id="bth_" type="button">确定</button>
</form>
</div>
</body>
<script type="text/javascript">



    $(function(){
        $("#bth_").click(function(){
            $("#checked").is(":checked");
            $.toptip('警告', 'warning');
        });

        $("input[name^='keyt']").keyup(function(){
             if($(this).val().length==4){
                var index=$(this).attr("index");
                $("#keyt"+(parseInt(index)+1)).focus();
             };
        });


    });

</script>
</html>
