
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
    	var agentPre = $("#agentPre").html();
    	
    //	alert("邀请码："+agentPre);
    	
    	$.post(baPaths+"/user/doOrder",{'transmoney' : payMoney*100,'payOpenVIP':'3'},function(data){   // 'payOpenVIP':'3' 成为代理商。方法最好提取。
    			var dataJson=eval("("+data+")");
    			/*if(dataJson.isMobile=='N'){
    				alert("尚未绑定手机号，请绑定手机号");
    				window.location.href=baPaths+"/user/toupdatmobipage?comepage=2";
    				return ;
    			}*/
    			$("#openId").val(dataJson.openid);
    			$("#orderNo").val(dataJson.orderNo);
    			$("#payFee").val(dataJson.fee);
    			$("#attach").val(dataJson.userId+"_"+dataJson.suffixType+"_"+agentPre);   // 组装：用户ID_3_父代理ID
    			$("#time_start").val(dataJson.time_start);
    			$("#payForm").submit();
    			
    		});
    		
    	
    });
    
 
    
  });