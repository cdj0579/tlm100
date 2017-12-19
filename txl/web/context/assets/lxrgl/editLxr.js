define(["validate.additional", "select3"], function(a, b){
	var $form = null;
	var modal = null;
	var onSave = null;
	var isAdd = true;
	var addUrl = "lxrgl/add";
	var updateUrl = "lxrgl/update";
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
	
	var reloadXxSelect = function($el, dqId){
		$el.reload(null, {
			dqId: dqId
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
			
			$form.find('select[name="dqId"]').select3({
	    		placeholder: "请选择",
	    		allowClear: true,
	    		autoLoad: true,
	    		value: _data.dqId || null,
	    		tableName: "xzqh",
	    		idField: "code",
				nameField: "name",
				typeField: "pid",
				typeVelue: "330500"
	    	}).on('select.select3', function(){
	    		reloadXxSelect($xxSelect, $(this).val());
	    	});
			var $xxSelect = $form.find('select[name="xxId"]').select3({
	    		placeholder: "请选择",
	    		autoLoad: false,
	    		allowClear: true,
	    		value: _data.xxId || null,
	    		getUrl: basePath+"xxgl/list",
	    		idField: "id",
				nameField: "xuexiaoming"
	    	});
			reloadXxSelect($xxSelect, _data.dqId || null);
			$form.find('select[name="nj"]').select2({
	    		placeholder: "请选择",
	    		allowClear: true
	    	});
			$form.find('select[name="bj"]').select2({
	    		placeholder: "请选择",
	    		allowClear: true
	    	});
			$form.find('select[name="xb"]').select2({
	    		placeholder: "请选择"
	    	});
			
			$form.loadForm(_data);
			if(_data.nj > 0)$form.find('select[name="nj"]').trigger('change');
			if(_data.bj > 0)$form.find('select[name="bj"]').trigger('change');
			if(_data.xb > 0)$form.find('select[name="xb"]').trigger('change');
			
			initValidHandler(modal);
			initSaveHandler(modal);
		}
	};
});