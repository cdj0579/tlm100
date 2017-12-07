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
		App.post(App.remoteUrlPre+"getStuHeadInfo",null,function(result){
			if(result.success == true || result.success == "true"){
				$("#username").html(result.realName);
				if(result.tx != null){
					$("img.img-circle").attr("src",result.tx);
				}
			}
		});
	});
	
});