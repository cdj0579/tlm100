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
	require(['domready!', 'app', "validate.additional"], function (doc, App){
		var $form = $('form');
		var initValidHandler = function(){
	    	validForm = $form.validateB({
	            submitHandler: function () {
	            	$form.ajaxSubmit({
	            		url: basePath+"config",
	            		type: "POST",
	            		dataType: "json",
	            		success: function(result){
	            			App.handlerAjaxJson(result, function(){
	            				App.getAlert().info("保存成功！", "提示");
	            			});
	            		}
	            	});
	            }
	        });
		};
		
		var initSaveHandler = function(){
			$form.find('.btn.save').on("click", function(){
				$form.submit();
			});
		};
		
		initValidHandler();
		initSaveHandler();
	});
});