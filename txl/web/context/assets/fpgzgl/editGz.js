define(["validate.additional", "select3"], function(a, b){
	var $form = null;
	var modal = null;
	var onSave = null;
	var isAdd = true;
	var addUrl = "fpgzgl/add";
	var updateUrl = "fpgzgl/update";
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
			_data = _data || {
				zhouqi: 7,
				danliang: 20
			};
			isAdd = !(id && id > 0);
			
			if(!isAdd){
				/*$form.find('input[name="username"]').attr("disabled", true);
				$form.hideOrShowFormItem(["isUpdatePwd"], true);
				var $enableSwitch = $form.find('input[name="isUpdatePwd"]');
				$form.hideOrShowFormItem(["password"], false);
				$enableSwitch.bootstrapSwitch().on("switchChange.bootstrapSwitch", function(){
					$form.hideOrShowFormItem(["password"], $enableSwitch.prop("checked"));
				});*/
			}
			$form.find('select[name="dqId"]').select3({
	    		placeholder: "请选择",
	    		autoLoad: true,
	    		value: _data.dqId || null,
	    		tableName: "xzqh",
	    		idField: "code",
				nameField: "name",
				typeField: "pid",
				typeVelue: "330500"
	    	});
			$form.find('select[name="xxId"]').select3({
	    		placeholder: "请选择",
	    		autoLoad: true,
	    		allowClear: true,
	    		value: _data.xxId || null,
	    		tableName: "txl_xuexiao",
	    		idField: "id",
				nameField: "xuexiaoming"/*,
				typeField: "pid",
				typeVelue: "330500"*/
	    	});
			$form.find('select[name="nj"]').select2({
	    		placeholder: "请选择",
	    		allowClear: true
	    	});
			$form.find('select[name="bj"]').select2({
	    		placeholder: "请选择",
	    		allowClear: true
	    	});
			
			$form.loadForm(_data);
			if(_data.nj > 0)$form.find('select[name="nj"]').trigger('change');
			if(_data.bj > 0)$form.find('select[name="bj"]').trigger('change');
			
			initValidHandler(modal);
			initSaveHandler(modal);
		}
	};
});