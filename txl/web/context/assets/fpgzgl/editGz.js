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
            	var $fenpeiDq = $form.find('input[name="fenpeiDq"]');
            	var $display = $('span.display-num');
            	var data = {
            			syzIds: $display.data("ids") || []
            	};
            	if(!$fenpeiDq.is(':checked')){
            		var syzIds = $display.data("ids") || [];
            		if(syzIds.length == 0){
            			App.getAlert().warning("请至少选择一个使用者！", "提示");
            			return;
            		}
            		$form.find('select[name="dqId"]').val(null);
            		data.syzIds = syzIds.join(",");
            	}
            	$form.ajaxSubmit({
            		url: basePath+url,
            		type: "POST",
            		dataType: "json",
            		data: data,
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
	
	var isSelected = function(id){
		var $display = $('span.display-num');
		var ids = $display.data("ids") || [];
		return ids.join(",").indexOf(id) != -1;
	};
	
	var setSyzIds = function(ids){
		var $display = $('span.display-num');
		$display.data("ids", ids);
		$display.html(ids.length);
	};
	
	var showSelectSyzWin = function(){
		App.ajaxModal({
			id: "selectSyz",
			referer: "edit",
			scroll: true,
			width: "950",
			required: [],
			remote: basePath+"assets/fpgzgl/selectSyz.html",
			callback: function(modal1, args){
				var $table = modal1.$element.find('table');
				var dt = $table.dataTable( {
					ajax: {
		        		url: basePath+"syzgl/list",
						dataSrc: function(result){
							var success = App.handlerGridLoad(result);
							if(success == true){
								return result.data;
							} else {
								return [];
							}
						}
		        	},
					deferRender: false,
					ordering: false,
					scrollY: "330px",
					scrollCollapse: false,
					paging: true,
					pageLength: 10,
					lengthChange: false,
					dom: "f<'table-scrollable't><'row'<'col-md-5 col-sm-5'i><'col-md-7 col-sm-7'p>>",
					drawCallback: function(){ 
						App.handleTooltips();  
						var checkboxes = $table.find('tr td input.checkboxes');
						checkboxes.each(function(){
							var id = parseInt($(this).val());
							if(isSelected(id)){
								$(this).closest('tr').addClass('checked');
								$(this).attr("checked", true);
							}
						});
						App.initUniform();
					},
		            columns: [
							{ title: '<input type="checkbox" class="group-checkable"/>', className: "center", width: "8%", data: "id", render: function(data, type, full){
								return '<input type="checkbox" class="checkboxes" value="'+data+'"/>';
							}, createdCell: function(td){
							  $(td).attr("align", "center");
							}},
		                  { title: "用户编号", data: "userNo"},
		                  { title: "姓名", data: "name"},
		                  { title: "所属地区", data: "dqName"}
		    	      ]
			      } );
				$tableWrapper = $table.closest('.dataTables_wrapper');
				$tableWrapper.find('.dataTables_filter').appendTo(modal1.$element.find('.search-inp'));
				$table.closest('.dataTables_wrapper').on("change", '.group-checkable', function(){
					var checkboxes = $table.find('tr td input.checkboxes');
					var checked = false;
					if($(this).prop("checked")){
						checked = true;
						checkboxes.closest('tr').addClass('checked');
					}else{
						checked = false;
						checkboxes.closest('tr').removeClass('checked');
					}
					checkboxes.attr("checked", checked);
					checkboxes.uniform();
				});
				$table.closest('.dataTables_wrapper').on("change", 'tr td input.checkboxes', function(){
					var $tr = $(this).closest('tr');
					if($(this).prop("checked") == true){
						$tr.addClass('checked');
					} else {
						$tr.removeClass('checked');
					}
				});
				modal1.$element.find('.modal-footer .btn.save').on("click",function(){
					var datas = dt.api().rows(['.checked']).data();
					if(datas.length == 0){
						App.getAlert().warning("请至少选择一个使用者！", "提示");
						return false;
					}
					var ids = [];
					$.each(datas, function(){
						ids.push(this.id);
					});
					setSyzIds(ids);
					modal1.hide();
				});
			}
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
			_data = _data || {
				zhouqi: 7,
				danliang: 20
			};
			isAdd = !(id && id > 0);
			if(!isAdd && !(_data.dqId && _data.dqId != "")){
				_data.fenpeiDq = false;
				App.getJSON(basePath+'fpgzgl/syzlist', {id: id}, function(result){
					setSyzIds(result.syzIds);
				});
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
			$form.find('select[name="lxrDqId"]').select3({
	    		placeholder: "请选择",
	    		autoLoad: true,
	    		allowClear: true,
	    		value: _data.lxrDqId || null,
	    		tableName: "xzqh",
	    		idField: "code",
				nameField: "name",
				typeField: "pid",
				typeVelue: "330500"
	    	}).on('select.select3', function(){
	    		reloadXxSelect($xxSelect, $(this).val());
	    	});
			$form.find('select[name="lryId"]').select3({
	    		placeholder: "请选择",
	    		allowClear: true,
	    		autoLoad: true,
	    		value: _data.lryId || null,
	    		getUrl: basePath+"lrygl/list",
	    		idField: "id",
				nameField: "name"
	    	});
			var $xxSelect = $form.find('select[name="xxId"]').select3({
	    		placeholder: "请选择",
	    		autoLoad: false,
	    		allowClear: true,
	    		value: _data.xxId || null,
	    		getUrl: basePath+"xxgl/list",
	    		idField: "id",
				nameField: "xuexiaoming"/*,
				typeField: "pid",
				typeVelue: "330500"*/
	    	});
			reloadXxSelect($xxSelect, _data.lxrDqId || null);
			$form.find('select[name="nj"]').select2({
	    		placeholder: "请选择",
	    		allowClear: true
	    	});
			$form.find('select[name="bj"]').select2({
	    		placeholder: "请选择",
	    		allowClear: true
	    	});
			var $enableSwitch = $form.find('input[name="fenpeiDq"]');
			$enableSwitch.bootstrapSwitch().on("switchChange.bootstrapSwitch", function(){
				var checked = $enableSwitch.prop("checked");
				$form.hideOrShowFormItem(["dqId"], checked);
				$form.hideOrShowFormItem(["syztmp"], !checked);
			});
			$form.loadForm(_data);
			if(_data.nj)$form.find('select[name="nj"]').trigger('change');
			if(_data.bj)$form.find('select[name="bj"]').trigger('change');
			if(_data.fenpeiDq == false) $enableSwitch.trigger('change');
			
			$form.find('.btn.select-syz').on("click", function(){
				showSelectSyzWin();
			});
			
			initValidHandler(modal);
			initSaveHandler(modal);
		}
	};
});