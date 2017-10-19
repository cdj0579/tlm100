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
	
	require(['domready!', 'app', 'validate.additional'], function (doc, App){
		
		$(".mui-icon").click(function(){
			
			history.back();
		});
		
		
	});
	

});