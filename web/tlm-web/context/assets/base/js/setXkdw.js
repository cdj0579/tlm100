define(["validate.additional", "select3", "ztree.select"], function(){
	var $form = null;
	var modal = null;
	var onSave = null;
	
	var initValidHandler = function(modal){
    	$form.validateB({
            submitHandler: function () {
            	$form.ajaxSubmit({
            		url: basePath+"base/xkdw/update",
            		type: "POST",
            		dataType: "json",
            		success: function(result){
            			App.handlerAjaxJson(result, function(){
            				if($.isFunction(onSave)){
            					onSave();
            				}
            				modal.hide();
            			});
            		}
            	});
            }
        });
	};
	
	var initSaveHandler = function(modal){
		modal.$element.find('.btn.save').on("click",function(){
			$form.submit();
		});
	};
	
	return {
		init: function(_data, _modal, _cb){
			modal = _modal;
			onSave = _cb;
			$form = modal.$element.find('form');
			
			$form.loadForm(_data);
			
			initValidHandler(modal);
			initSaveHandler(modal);
		}
	};
});