var basePath = "./";
require.config({
	baseUrl: basePath
});
define(['assets/common/config'], function(config) {
	require.config(config.require);
	
	require.config({
		paths: {
		},shim: {
		}
	});
	
	require(['domready!', 'app'], function (doc, App){
		$(".mui-icon").click(function(){
			history.back();
		});
		
		App.post(App.remoteUrlPre+"getStuHeadInfo",null,function(result){
			if(result.success == true || result.success == "true"){
				$("#username").html(result.username);
				if(result.tx != null){
					top.headImg64 = result.tx;
					$("#imgHead").attr("src",result.tx);
				}
			}
		});
		
		$(".headImg").click(function(){
			location.href="./set_tx.html";
		});
		
	});
	

});