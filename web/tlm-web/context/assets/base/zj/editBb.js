define(["validate.additional", "select3", "ztree.select"], function(){
	var $form = null;
	var modal = null;
	var onSave = null;
	var isAdd = true;
	var addUrl = "dic/add";
	var updateUrl = "dic/update";
	var id = -1;
	
	var initValidHandler = function(modal){
		var url = isAdd?addUrl:updateUrl;
    	$form.validateB({
            submitHandler: function () {
            	$form.ajaxSubmit({
            		url: basePath+url,
            		type: "POST",
            		dataType: "json",
            		success: function(result){
            			App.handlerAjaxJson(result, function(){
            				if($.isFunction(onSave)){
            					onSave(result.id);
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
			id = _data?_data.id:-1;
			isAdd = !(id && id > 0);
			
			$form.loadForm(_data);
			
			initValidHandler(modal);
			initSaveHandler(modal);
		}
	};
});