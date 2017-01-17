$(function(){
	
	// 推广大使弹窗
	$("#becomesMaster").click(function(e){
		$("#wsh_content,#wsh_mask").show();
	});
	// 推广大使弹窗关闭
    $("#close_pop").click(function(){
      $("#wsh_content,#wsh_mask").hide();
    });
    
    $("#close_popquanyi").click(function(){
    	$("#vip_quanyi,#quanyi").hide();
     });
      
    
    
});
