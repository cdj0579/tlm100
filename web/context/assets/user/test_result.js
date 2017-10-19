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
			alert("pk");
		})
		
		$("button[name='srfx']").click(function(){
			alert("分析");
		})
	});
	

});