define(["validate.additional", "select3"], function(a, b){
	var $form = null;
	var modal = null;
	var onSave = null;
	var isAdd = true;
	var addUrl = "lrygl/add";
	var updateUrl = "lrygl/update";
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
		modal.$element.find('.modal-footer .btn.save').on("click",function(){
			$form.submit();
		});
	};
	
	return {
		init: function(_data, _modal, _cb){
			modal = _modal;
			onSave = _cb;
			$form = modal.$element.find('form');
			id = _data?_data.id:-1;
			_data = _data || {};
			isAdd = !(id && id > 0);
			
			if(!isAdd){
				$form.find('input[name="username"]').attr("disabled", true);
				$form.hideOrShowFormItem(["isUpdatePwd"], true);
				var $enableSwitch = $form.find('input[name="isUpdatePwd"]');
				$form.hideOrShowFormItem(["password"], false);
				$enableSwitch.bootstrapSwitch().on("switchChange.bootstrapSwitch", function(){
					$form.hideOrShowFormItem(["password"], $enableSwitch.prop("checked"));
				});
			}
			$form.loadForm(_data);
			
			initValidHandler(modal);
			initSaveHandler(modal);
		}
	};
});