//var basePath = "./";
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
			location.href= App.remoteUrlPre +"index";
		});
		
		$("button[name='pk']").click(function(){
			window.location.href = basePath + "pk_result";
		})
		
		$("button[name='srfx']").click(function(){
			window.location.href = basePath + "srfx";
		})
	});
	

});