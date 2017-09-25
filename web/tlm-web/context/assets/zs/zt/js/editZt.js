define(["validate.additional", "select3", "ztree.select", "RememberBaseInfo"], function(a, b, c, RememberBaseInfo){
	var $form = null;
	var modal = null;
	var onSave = null;
	var isAdd = true;
	var addUrl = "zs/zt/add";
	var updateUrl = "zs/zt/update";
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
			
			if(isAdd){
				var o = RememberBaseInfo.load();
				$.extend(_data, o);
			}
			
			$form.find('select[name="kmId"]').select3({
	    		placeholder: "请选择",
	    		autoLoad: true,
	    		useZd: true,
	    		tableName: "km_dic",
	    		value: _data.kmId || null,
	    		idField: "id",
				nameField: "name"
	    	});
			$form.find('select[name="njId"]').select3({
	    		placeholder: "请选择",
	    		autoLoad: true,
	    		tableName: "nj_dic",
	    		value: _data.njId || null,
	    		idField: "id",
				nameField: "name"
	    	});
			$form.find('select[name="qzqm"]').select2({
	    		placeholder: "请选择"
	    	});
			$form.find('select[name="ndId"]').select3({
	    		placeholder: "请选择",
	    		autoLoad: true,
	    		tableName: "nd_dic",
	    		value: isAdd?null:_data.ndId,
	    		idField: "id",
				nameField: "name",
				typeField: "dic_type",
				typeVelue: "ztnd"
	    	});
			
			if(isAdd){
				RememberBaseInfo.init($form);
			}
			$form.loadForm(_data);
			if(_data.qzqm > 0)$form.find('select[name="qzqm"]').trigger('change');
			
			initValidHandler(modal);
			initSaveHandler(modal);
		}
	};
});