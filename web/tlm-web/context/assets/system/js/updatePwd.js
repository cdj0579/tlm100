define(["validate.additional"], function(){
	
	var initValidHandler = function(modal){
		var form = modal.$element.find('form');
    	validForm = form.validateB({
            submitHandler: function () {
            	form.ajaxSubmit({
            		url: basePath+"system/updatePwd",
            		type: "POST",
            		dataType: "json",
            		success: function(result){
            			App.handlerAjaxJson(result, function(){
            				modal.hide();
            			});
            		}
            	});
            }
        });
	};
	
	var initSaveHandler = function(modal){
		var $form = modal.$element.find('form');
		modal.$element.find('.btn.save').on("click", function(){
			$form.submit();
		});
	};
	
	return {
		init: function(modal){
			initValidHandler(modal);
			initSaveHandler(modal);
		}
	};
});