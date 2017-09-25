define([], function() {
	var saveUrl = 'jagl/save';
	var saveTempleteUrl = 'jagl/templetes/save';
	var $form = null;
	
	var save = function(cb, params, templete){
		var url = templete==true?saveTempleteUrl:saveUrl;
		params = params || {};
		$form.ajaxSubmit({
    		url: basePath+url,
    		type: "POST",
    		dataType: "json",
    		data: params,
    		success: function(result){
    			App.handlerAjaxJson(result, function(){
    				App.getAlert().info("保存成功！", "提示");
    				if($.isFunction(cb)){
    					cb();
    				}
    			});
    		}
    	});
	};
	
	return {
		init: function(_from){
			$form = _from;
		},
		save: function(_cb, params){
			save(_cb, params, false);
		},
		saveTemplete: function(_cb, params){
			save(_cb, params, true);
		}
	};
});