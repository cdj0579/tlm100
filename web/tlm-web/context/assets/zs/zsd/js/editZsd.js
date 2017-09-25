define(["validate.additional", "select3", "ztree.select", "RememberBaseInfo"], function(a, b, c, RememberBaseInfo){
	var $form = null;
	var modal = null;
	var onSave = null;
	var isAdd = true;
	var addUrl = "zs/zsd/add";
	var updateUrl = "zs/zsd/update";
	var id = -1;
	
	var IDMark_Switch = "_switch",
	IDMark_Icon = "_ico",
	IDMark_Span = "_span",
	IDMark_Input = "_input",
	IDMark_Check = "_check",
	IDMark_Edit = "_edit",
	IDMark_Remove = "_remove",
	IDMark_Ul = "_ul",
	IDMark_A = "_a";
	
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
			var zs = null;
			
			if(isAdd){
				var o = RememberBaseInfo.load();
				$.extend(_data, o);
			}
			
			var loadZj = function(){
				kmId = $form.find('select[name="kmId"]').val();
				dqId = $form.find('select[name="dqId"]').val();
				njId = $form.find('select[name="njId"]').val();
				xq = $form.find('select[name="xq"]').val();
				
				if(kmId && kmId > 0 && njId && njId > 0 && xq && xq > 0 && dqId){
					zs.ztreeSelect("reload", {
						kmId: kmId,
						njId: njId,
						xq: xq,
						dqId: dqId
					});
				}
			};
			$form.find('select[name="dqId"]').select3({
	    		placeholder: "请选择",
	    		autoLoad: true,
	    		value: _data.dqId || null,
	    		tableName: "xzqh",
	    		idField: "code",
				nameField: "name",
				typeField: "pid",
				typeVelue: "330500"
	    	}).on("change", loadZj);
			$form.find('select[name="kmId"]').select3({
	    		placeholder: "请选择",
	    		autoLoad: true,
	    		useZd: true,
	    		tableName: "km_dic",
	    		value: _data.kmId || null,
	    		idField: "id",
				nameField: "name"
	    	}).on("change", loadZj);
			$form.find('select[name="njId"]').select3({
	    		placeholder: "请选择",
	    		autoLoad: true,
	    		tableName: "nj_dic",
	    		value: _data.njId || null,
	    		idField: "id",
				nameField: "name"
	    	}).on("change", loadZj);
			$form.find('select[name="xq"]').select2({
	    		placeholder: "请选择"
	    	}).on("change", loadZj);
			zs = $form.find('input[name="zjId"]').ztreeSelect({
				url: basePath+"base/zj/tree",
				value: isAdd?null:_data.zjId,
				treeView: {
					dblClickExpand: false,
					showLine: true,
					showIcon: false,
					selectedMulti: false,
					addDiyDom: function(treeId, treeNode){
						var aObj = $("#" + treeNode.tId + IDMark_A);
						var text = treeNode.name;
						if(treeNode.bm){
							text = treeNode.bm+" "+text;
						}
						$("#" + treeNode.tId + IDMark_Span).html(text);
					}
				},
				autoLoad: false,
				dataParam: function(result){
					var rows = [{
						id: -1,
						bbId: -1,
						name: "默认版本",
						pid: -9999,
						type: 'bb',
						open: true
					}];
					if(result.bbList && result.bbList.length > 0){
						$.each(result.bbList, function(){
							rows.push({
								id: "bb_"+this.id,
								bbId: this.id,
								name: this.name,
								pid: -9999,
								type: 'bb',
								open: true
							});
						});
					}
					$.each(result.data, function(){
						this.open = true;
						if(this.bbId != -1 && this.pid == -1){
							this.pid = "bb_"+this.bbId;
						}
						rows.push(this);
					});
					return rows;
				},
				valueParam: "id"
			});
			$form.find('select[name="ndId"]').select3({
	    		placeholder: "请选择",
	    		autoLoad: true,
	    		tableName: "nd_dic",
	    		value: isAdd?null:_data.ndId,
	    		idField: "id",
				nameField: "name",
				typeField: "dic_type",
				typeVelue: "zsdnd"
	    	});
			
			if(isAdd){
				RememberBaseInfo.init($form);
			}
			$form.loadForm(_data);
			if(_data.xq > 0)$form.find('select[name="xq"]').trigger('change');
			setTimeout(loadZj, 500);
			
			initValidHandler(modal);
			initSaveHandler(modal);
		}
	};
});