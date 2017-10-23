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
		
		$("button[name='pk']").click(function(){
			window.location.href = basePath + "pk_result.html";
		})
		
		$("button[name='srfx']").click(function(){
			window.location.href = basePath + "srfx_set.html";
		})
	});
	

});