function pushtheinfo(){	
	var baPaths = $("#basePaths").val();
	var username = $("#name").val();
	var bankno = $("#bankno").val();
	var banktype = $("#banktype").val();
	var subbank = $("#subBank").val();
	/* ${ctx} */	
	$.post(baPaths+"/user/updateBankInfo",{username:username,bankno:bankno,banktype:banktype,subbank:subbank},function(data){
		if(data == 1){
			window.location.href=baPaths+"/user/touserinfopage";
		}
	});
}