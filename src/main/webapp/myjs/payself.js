	$(function() {
			$("#payType").addClass("check");//默认微信支付，选中。
			$("#zidingyi").val(""); //清空输入框。 
			
			$(".charge_list li a").click(function() {
				var $par = $(this).parents(".charge_list");
				$par.find("a").removeClass("check");
				$(this).addClass("check");
				$("#zidingyi").val(""); 
			    $("#payMoney").val($(this).find("em").html());
			});

		})