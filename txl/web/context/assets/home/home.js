/**
 * basePath:当前页面的相对路径，如果导入common/head.jsp,则在head.jsp中设置
 */
if(basePath || basePath == ""){
	require.config({
		baseUrl: basePath
	});
}
define(['assets/common/config'], function(config) {
	require.config(config.require);
	require.config({
		paths: {
		},shim: {
		}
	});
	
	require(['app','layout','demo']);
	require(['domready!', 'app'], function (doc, App){
		
	});
	
});