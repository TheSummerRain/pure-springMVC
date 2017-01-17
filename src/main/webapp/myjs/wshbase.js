
$(function(){
	var baPaths = $("#basePaths").val();
    $(".vip_t .show_year").click(function(){
      var $this = $(this);
      
      $this.parents("li").find(".vip_b").show();
      $this.parents("li").find(".glyphicon").addClass("glyphicon-menu-down");
    });
    $(".vip_b a").click(function(){
      var $this = $(this);
      var $text = $this.find("span").html();
      $this.parents("li").find(".vip_t .info").html($text);
      $this.parents("li").find(".glyphicon").removeClass("glyphicon-menu-down");
      var money = $("#payYear").html().substring(0,1);
      $("#payMoney").html(money*500);
      $this.parent().hide();
    });
    
    $("#open_member").click(function(){
    	var baPaths = $("#basePaths").val();
    	var payMoney = $("#payMoney").html();
    	var payType=$("#payType").html();
    	var payYear=$("#payYear").html().substring(0,1);
    	
    	if(payType=='微信'){
    		
    		$.post(baPaths+"/user/doOrder",{'transmoney' : payMoney*100,'payOpenVIP':'2'},function(data){
    			var dataJson=eval("("+data+")");
    			if(dataJson.isMobile=='N'){
    				alert("尚未绑定手机号，请绑定手机号");
    				window.location.href=baPaths+"/user/toupdatmobipage?comepage=2";
    				return ;
    			}
    			
    			$("#openId").val(dataJson.openid);
    			$("#orderNo").val(dataJson.orderNo);
    			$("#payFee").val(dataJson.fee);
    			$("#userId").val(dataJson.userId+"_"+dataJson.suffixType);   // 后台已经组装了：_1;_2
    			$("#time_start").val(dataJson.time_start);
    			$("#payForm").submit();
    		});
    		
    	}else{
    	 	/*$.post(baPaths+"/user/toOpenVip",{payMoney:payMoney,payYear:payYear,payType:payType},function(data){
        		if(data==1){
        			alert("");
        			window.location.href=baPaths+"/user/toupdatmobipage";
        		}else if(data==2){
        			alert("wbuz");
        		}else if(data==3){
        			window.location.href=baPaths+"/user/toVipSucPage";
        		}
        	});*/
    	}
    	
    });
    
 
    
  });