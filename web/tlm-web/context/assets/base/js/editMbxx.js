define(["validate.additional", "select3", "ztree.select"], function(){
	var $form = null;
	var modal = null;
	var $table = null;
	var dt = null;
	var onSave = null;
	var isAdd = true;
	var addUrl = "base/mbxx/add";
	var updateUrl = "base/mbxx/update";
	var id = -1;
	
	var initValidHandler = function(modal){
		var url = isAdd?addUrl:updateUrl;
    	$form.validateB({
            submitHandler: function () {
            	var rows = dt.api().rows().data();
            	var datas = [];
            	var msg = null;
            	$.each(rows, function(){
            		datas.push({
            			id: this.id,
            			kmId: this.kmId,
            			mbfs: this.mbfs,
            			mf: this.mf
            		});
            		if(this.mbfs <= 0){
            			msg = "未设置科目["+this.kmName+"]的目标分数！";
            			return false;
            		}
            	});
            	if(msg){
            		App.getAlert().warning(msg, "警告");
            		return false;
            	}
            	$form.ajaxSubmit({
            		url: basePath+url,
            		type: "POST",
            		dataType: "json",
            		data: {
            			mbf: JSON.stringify(datas)
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
		modal.$element.find('.btn.save').on("click",function(){
			$form.submit();
		});
	};
	
	var initTable = function(data){
		dt = $table.dataTable( {
			data: data,
			deferRender: false,
			ordering: false,
			scrollY: "150px",
			scrollCollapse: false,
			paging: true,
			pageLength: 10,
			lengthChange: false,
			dom: "<'table-scrollable't><'row'<'col-md-5 col-sm-5'i><'col-md-7 col-sm-7'p>>",
			drawCallback: function(){ App.handleTooltips();  },
            columns: [
                  { title: "科目", data: "kmName"},
                  { title: "目标分", data: "mbfs"},
                  { title: "满分", data: "mf"},
    	          { title: "操作", data: "id", render: function(data, type, full){
    	        	  return '<a href="javascript:;" class="btn blue edit"> 编辑 <i class="fa fa-edit"></i></a>';
    	          }}
    	      ]
	      } );
		var $tableWrapper = $table.closest('.dataTables_wrapper');
		$tableWrapper.on('click', '.btn.edit', function(){
			var $tr = $(this).closest('tr');
			showMfWin($tr);
		});
	};
	
	var showMfWin = function($tr){
		App.ajaxModal({
			id: "editMbf",
			scroll: true,
			width: "600",
			referer: modal.$element,
			remote: basePath+"assets/base/editMbf.html",
			callback: function(modal1, args){
				var $optionForm = modal1.$element.find('form');
				var data = dt.api().row($tr).data();
				$optionForm.loadForm(data);
				
				$optionForm.validateB({
		            submitHandler: function () {
		            	var data = {
		            		id: parseInt($optionForm.find('input[name="id"]').val()),
	            			kmId: parseInt($optionForm.find('input[name="kmId"]').val()),
	            			kmName: $optionForm.find('input[name="kmName"]').val(),
	            			mbfs: parseInt($optionForm.find('input[name="mbfs"]').val()),
	            			mf: parseInt($optionForm.find('input[name="mf"]').val())
		            	};
		            	dt.api().row($tr).data(data).draw();
		            	modal1.hide();
		            }
		        });
				
				modal1.$element.find('.btn.save').on("click",function(){
					$optionForm.submit();
				});
			}
		});
	};
	
	var initSelectItems = function($form, data){
		$form.find('select[name="dqId"]').select3({
    		placeholder: "请选择",
    		autoLoad: true,
    		value: data&&data.dqId?data.dqId:null,
    		tableName: "xzqh",
    		idField: "code",
			nameField: "name",
			typeField: "pid",
			typeVelue: "330500"
    	})
	};
	
	return {
		init: function(_data, _modal, _cb){
			modal = _modal;
			onSave = _cb;
			$form = modal.$element.find('form');
			$table = modal.$element.find('table');
			id = _data?_data.id:-1;
			isAdd = !(id && id > 0);
			
			if(isAdd){
				App.getJSON(basePath+'dic/get', {tableName: "km_dic", nameField: "name", idField: "id"}, function(result){
					var datas = [];
					$.each(result.data, function(){
						datas.push({
							id: -1,
							kmId: this.id,
							kmName: this.name,
							mbfs: 80,
							mf: 100
						});
					});
					initTable(datas);
				});
				initSelectItems($form, {});
			} else {
				App.getJSON(basePath+'base/mbxx/'+id, {}, function(result){
					$form.loadForm(result.data);
					
					initSelectItems($form, result.data);
					
					initTable(result.data.mbfList);
				});
			}
			
			initValidHandler(modal);
			initSaveHandler(modal);
		}
	};
});