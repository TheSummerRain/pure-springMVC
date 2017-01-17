function submits(){
	var bas = $("#basepath").val();
	var minWithDraw = $("#minWithDraw").val();
	var subvalue=$("#subvalue").val();
	
	if($("#subvalue").val() == ''){
		alert("不要逗我嘛，最少告诉我你想取多少钱 好不好 *-* ");
		return ;
	}
	if(parseInt($("#subvalue").val()) < minWithDraw){
		alert("最低申请金额必须大于:"+minWithDraw+"元!");
		return;
	}
	
	if(parseInt($("#subvalue").val()) > 2000){
		alert("根据微信提现规则，系统采用非实名认证，单人单次不得超过2000/天");
		return;
	}
	
 	if(parseInt($("#subvalue").val()) > parseInt($("#spweibi").html())){
		alert("超出最大可提取金额！");
		return;
	}
 	
 	window.location.href=bas+"/wxpay/payToCust?subvalue="+subvalue;
 	//window.location.href=bas+"/wxpay/payToCust?subvalue=1";
 /*	$.post(bas+"/wxpay/payToCust",{subvalue:subvalue},function(data){
		if(data=='1'){
			alert("提现申请成功");
			window.location.href=bas+"/user/toUserCenterPage";
		}else if(data=='2'){
			alert("金额有误");
			return ;
		}else if(data=='3'){
			alert("维币不足");
			return;
		}else{
			alert("执行异常，请尝试刷新后从新请求");
			return;
		}
 	});*/
}