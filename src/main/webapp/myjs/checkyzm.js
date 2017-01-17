 function nameValidate(name_value){
    var reg_phone = /^1[3,4,5,8,7]\d{9}$/;
    if(name_value == ""){
      $('.err_msg').html('请输入手机号码').show();
      return false;
    }else if(!reg_phone.test(name_value)){
      $('.err_msg').html('请输入正确的的手机号').show();
      return false;
    }else if(!checkHasMobile(name_value)){
      return false;
    }else{
      $(".err_msg").hide();
      return true;
    }
    return true;
  }

 //校验手机号
 function checkHasMobile(data){
	 if(data.trim() == ''){
		 return false;
	 }
	 var result = false;
	 var baspas = $("#basePaths").val()+"/user/hasBindMobile";
	 $.ajax({
	     type:'GET',
	     url: baspas,
	     async:false,
         data: {"mobile":data.trim()} ,
	     dataType: 'json',
	     success: function(red) {  
	    	 if(red=="1"){
				 $('#sendMessage').removeAttr("disabled"); 
				 result = true;
			 }else{
				 $('.err_msg').html('手机号已经存在').show();
				 $('#sendMessage').attr('disabled',"true");
				 result = false;
			 }
	     }
	});
	return result;
 }
 
 function hideMsg(){
	 $('#sendMessage').removeAttr("disabled"); 
	 $(".err_msg").hide();
     return ;
 }
 
  function fun_timedown(time) {
    $("#sendMessage").html(time + " 秒后重试");
    time = time - 1;
    if (time >= 0) { 
      setTimeout("fun_timedown(" + time + ")", 1000);
    } else {
      $("#sendMessage").html("重新发送验证码").removeClass("sendMessage_dis");
      $("#sendMessage").removeAttr("disabled");
      $("#sendMessage").unbind("click");
      $(".err_msg").hide();
      //重新绑定事件
      $("#sendMessage").bind("click", function() {
        var phone = $("#user").val();
        $.post($("#basePaths").val()+"/user/tosendmsg", {phone : phone}, function(data){
          if(data){
            if(data == "1"){
              $("#sendMessage").unbind("click").addClass("sendMessage_dis");
              $("#sendMessage").bind("click",function(){
              $(".err_msg").html("短信已经发送，请稍后再试").show();
              });
              fun_timedown(120);
            } else{
              $('.err_msg').html('请输入手机号码').show();
            }
          }
         })

      });
    }
  }

  $(function(){
	var baPaths = $("#basePaths").val();
	  
    $("#sendMessage").bind("click", function(){
    	 var phone = $("#user").val();
        if(nameValidate(phone)){
         $.post(baPaths+"/user/tosendmsg", {phone : phone}, function(data){
          if(data){
            if(data == "1"){
              $("#sendMessage").unbind("click").addClass("sendMessage_dis");
              $("#sendMessage").bind("click",function(){
              $(".err_msg").html('<em class="icon icon_5_1">&nbsp;</em>短信已经发送，请稍后再试').show();
              });
              fun_timedown(120);
            } else{
              $(".err_msg").hide();
            }
          }
         })
      } 
    });

    $("#btn_submit").click(function(){
        var yanzm=$("#yzma").val();
        var mobile =$("#user").val();
        var towhat = $("#whatpag").val();
        var yaoqm = null;
        if (typeof($("#yaoqm").val()) != "undefined") {    //如果邀请码不存在或者根本没有，就不管他。如果输入888888.6个8.就映射 955 ID
        	yaoqm = $("#yaoqm").val();
        	if(yaoqm==''|| yaoqm==null){
        		 $('.err_msg').html('请输入邀请码.').show();
        		 return;
        	}
        }  
    	if(!nameValidate(mobile)){
    		return;
      	} 
      //后面写提交逻辑。
      if(yanzm == null || yanzm==""){
    	  $('.err_msg').html('请输入验证码').show();
    	  return;
      }else{
    	  $('.err_msg').hide();
      }
      $.post(baPaths+"/user/tocheckYzm/"+yanzm+"/"+mobile,{'yaoqm':yaoqm},function(data){
    	  if(data=="1"){
    		  if(towhat.length > 0){ //如果指定跳转 支付页面
    			  if(confirm("绑定成功，跳转开通会员页面？")){
    				  window.location.href=baPaths+"/user/openMember"; //跳转开通会员界面
        		  }else{
        			  window.location.href=baPaths+"/user/toUserCenterPage";
        		  }
    		  }else{ //否则跳转 个人信息界面
    			  window.location.href=baPaths+"/user/touserinfopage"; //绑定成功，跳转用户中心。
    		  }
    	  }else if(data=="2"){
    		  alert("您是老会员,已经自动为您开通VIP,并清除上级关系.");
    		  window.location.href=baPaths+"/user/toUserCenterPage";
    	  }else{
    		  $('.err_msg').html('验证码错误,或者已经过期').show();
    	  }
      });
	  
    })

  })