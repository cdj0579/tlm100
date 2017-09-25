define(["validate.additional", "select3", "ztree.select"], function(){
	var $form = null;
	var modal = null;
	var onSave = null;
	var contentEditor = null;
	var answerEditor = null;
	var isAdd = true;
	var addUrl = "zs/xt/add";
	var updateUrl = "zs/xt/update";
	var id = -1;
	var rwId = null;
	
	var initValidHandler = function(modal){
		var url = isAdd?addUrl:updateUrl;
    	$form.validateB({
            submitHandler: function () {
            	var selected = $form.find('span.display-zsd').data("ids") || [];
            	if(!selected || selected.length == 0){
            		App.getAlert().warning("请至少选择一个知识点！", "提示");
            	}
            	$form.find('textarea[name="content"]').val(contentEditor.getData());
            	$form.find('textarea[name="answer"]').val(answerEditor.getData());
            	$form.ajaxSubmit({
            		url: basePath+url,
            		type: "POST",
            		dataType: "json",
            		data: {
            			zsdIds: selected.join(','),
            			rwId: rwId
            		},
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
	
	var showSelectZt = function(params){
		App.ajaxModal({
			id: "selectZt",
			scroll: true,
			width: "850",
			referer: "edit",
			required: ['selectZt'],
			remote: basePath+"assets/zs/xt/selectZt.html",
			callback: function(modal, args){
				args[0].init(params, modal, function(ztId, ztName){
					$form.find('input[name="ztId"]').val(ztId);
					$form.find('input[name="ztName"]').val(ztName);
				});
			}
		});
	};
	
	var showSelectZsd = function(params){
		var $display = $form.find('span.display-zsd');
		var selected = $display.data("ids") || [];
		App.ajaxModal({
			id: "selectZsd",
			scroll: true,
			width: "800",
			referer: "edit",
			required: ['selectZsd'],
			remote: basePath+"assets/zs/xt/selectZsd.html",
			callback: function(modal, args){
				args[0].init(params, modal, selected, function(ids){
					$display.html(ids.length);
					$display.data("ids", ids)
				});
			}
		});
	};
	
	return {
		init: function(_data, _modal, _cb){
			modal = _modal;
			onSave = _cb;
			$form = modal.$element.find('form');
			id = _data?_data.id:-1;
			isAdd = !(id && id > 0);
			rwId = _data&&_data.rwId?_data.rwId:-1;
			var rwData = null;
			if(rwId > 0){
				rwData = {
						kmId: _data.kmId,
						njId: _data.njId
				};
			}
			
			$form.find('select[name="ndId"]').select3({
	    		placeholder: "请选择",
	    		autoLoad: true,
	    		tableName: "nd_dic",
	    		value: isAdd?null:_data.ndId,
	    		idField: "id",
				nameField: "name",
				typeField: "dic_type",
				typeVelue: "xtnd"
	    	});
			$form.find('select[name="typeId"]').select3({
	    		placeholder: "请选择",
	    		autoLoad: true,
	    		useZd: true,
	    		tableName: "xt_type_dic",
	    		value: isAdd?null:_data.typeId,
	    		idField: "id",
				nameField: "name"
	    	});
			
			$form.find('select[name="isOriginal"]').select2({
	    		placeholder: "请选择"
	    	}).on("change", function(){
	    		var isOriginal = $(this).val();
	    		if(isOriginal == 1){
	    			$form.hideOrShowFormItem(['yyfs'], true);
	    		} else {
	    			$form.hideOrShowFormItem(['yyfs'], false);
	    			$form.find('input[name="yyfs"]').val(1);
	    		}
	    	});
			$form.find('select[name="isShare"]').select2({
	    		placeholder: "请选择"
	    	});
			
			$form.loadForm(_data);
			var $display = $form.find('span.display-zsd');
			if(!isAdd){
				if(_data.isOriginal && _data.isOriginal>0) $form.find('select[name="isOriginal"]').trigger('change');
				if(_data.isShare && _data.isShare>0) $form.find('select[name="isShare"]').trigger('change');
				App.getJSON(basePath+'zs/xt/zsdRefs', {id: id}, function(result){
					var ids = [];
					if(result.data){
						$.each(result.data, function(){
							ids.push(this.zsdId);
						});
					}
					$display.html(ids.length);
					$display.data("ids", ids)
				});
			} else if(_data && _data.zsdIds){
				$display.html(_data.zsdIds.length);
				$display.data("ids", _data.zsdIds)
			}
			
			$('#cke_content').remove();
			$('#cke_answer').remove();
			require(["ckeditor-ckfinder"], function(){
				CKEDITOR.on( 'instanceCreated', function( event ) {
					var editor = event.editor;
					CKFinder.setupCKEditor(editor, basePath+'assets/global/plugins/ckfinder/');
					editor.on('focus', function(){
						$('.cke_button_icon.cke_button__jme_icon').css({
							backgroundSize: "auto"
						});
					});
					if(editor.name == "xt_content"){
						contentEditor = editor;
					} else if(editor.name == "xt_answer"){
						answerEditor = editor;
					}
				});
				CKEDITOR.inline('xt_content', {
					customConfig : basePath+'assets/jagl/inlineConfig.js',
					extraPlugins: 'jme'
				});
				CKEDITOR.inline('xt_answer', {
					customConfig : basePath+'assets/jagl/inlineConfig.js',
					extraPlugins: 'jme'
				});
			});
			
			$form.find('.btn.select-zt').on('click', function(){
				showSelectZt(rwData);
			});
			
			$form.find('.btn.select-zsd').on('click', function(){
				showSelectZsd(rwData);
			});
			
			initValidHandler(modal);
			initSaveHandler(modal);
		}
	};
});