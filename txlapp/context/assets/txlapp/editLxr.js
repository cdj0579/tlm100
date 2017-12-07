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

	require(['domready!', 'app', 'validate.additional',"select3"], function(doc, App){
		var $form = null;
		var addUrl =  basePath + "fx/saveLxrInfo"
		
		var initValidHandler = function(){
			var url = addUrl;
	    	$form.validateB({
	            submitHandler: function () {
	            	$form.ajaxSubmit({
	            		url: url,
	            		type: "POST",
	            		dataType: "json",
	            		success: function(result){
	            			App.handlerAjaxJson(result, function(){
	            				$form[0].reset();
		            			App.handlerAjaxJson(result,"保存信息成功！");
	            			});
	            		}
	            	});
	            	
	            }
	        });
		};
		
		var initSaveHandler = function(){
			$form.find('#submit-btn').on("click",function(){
				$form.submit();
				return false;
			});
		};
		
		var init = function(){
			$form = $("#form_remark");
			initValidHandler();
			initSaveHandler();
		};
		
		init();
	});
});