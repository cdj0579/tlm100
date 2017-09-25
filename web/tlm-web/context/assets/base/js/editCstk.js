define(["validate.additional", "select3", "ztree.select"], function(){
	var $form = null;
	var modal = null;
	var $table = null;
	var dt = null;
	var onSave = null;
	var isAdd = true;
	var addUrl = "base/cstk/add";
	var updateUrl = "base/cstk/update";
	var id = -1;
	
	var initValidHandler = function(modal){
		var url = isAdd?addUrl:updateUrl;
    	$form.validateB({
            submitHandler: function () {
            	var rows = dt.api().rows().data();
            	var datas = [];
            	var noAnswer = true;
            	$.each(rows, function(){
            		datas.push({
            			option: this.option,
            			name: this.name
            		});
            		if(this.option == $form.find('select[name="answer"]').val()){
            			noAnswer = false;
            		}
            	});
            	if(noAnswer){
            		App.getAlert().warning("答案是不存在的选项！", "警告");
            		return false;
            	}
            	$form.ajaxSubmit({
            		url: basePath+url,
            		type: "POST",
            		dataType: "json",
            		data: {
            			options: JSON.stringify(datas)
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
                  { title: "序号", data: "option", width: "15%"},
                  { title: "名称", data: "name", width: "25%"},
                  /*{ title: "是否答案", data: "option", width: "25%", render: function(data, type, full){
                	  if(data == $form.find('select[name="answer"]').val()){
                		  return '<i class="fa fa-check-circle font-green"></i>';
                	  } else {
                		  return '';
                	  }
                  }},*/
    	          { title: "操作", data: "id", width: "35%", render: function(data, type, full){
    	        	  return '<a href="javascript:;" class="btn blue edit"> 编辑 <i class="fa fa-edit"></i></a>'+
    	        	  '<a href="javascript:;" class="btn red delete"> 删除 <i class="fa fa-remove"></i></a>';
    	          }}
    	      ]
	      } );
		var $tableWrapper = $table.closest('.dataTables_wrapper');
		$form.find('.btn.add').on('click', function(){
			showOptionWin(null);
		});
		$tableWrapper.on('click', '.btn.edit', function(){
			var $tr = $(this).closest('tr');
			showOptionWin($tr);
		});
		$tableWrapper.on('click', '.btn.delete', function(){
			var $tr = $(this).closest('tr');
			var data = dt.api().row($tr).data();
			App.confirm({
				title: '提示信息',
				msg: '您确定要删除选项['+data.option+']吗？',
				okFn: function(){
					dt.api().row($tr).remove().draw();
				},
				cancerFn: function(){}
			});
		});
	};
	
	var showOptionWin = function($tr){
		App.ajaxModal({
			id: "editOption",
			scroll: true,
			width: "600",
			referer: modal.$element,
			remote: basePath+"assets/base/editOption.html",
			callback: function(modal1, args){
				var $optionForm = modal1.$element.find('form');
				$optionForm.find('select[name="option"]').select2();
				
				if($tr){
					var data = dt.api().row($tr).data();
					$optionForm.loadForm(data);
					$optionForm.find('select[name="option"]').trigger('change');
				}
				
				$optionForm.validateB({
		            submitHandler: function () {
		            	var data = {
	            			option: $optionForm.find('select[name="option"]').val(),
	            			name: $optionForm.find('textarea[name="name"]').val()
		            	};
		            	if($tr){
		            		dt.api().row($tr).data(data).draw();
		            	} else {
		            		dt.api().row.add(data).draw();
		            	}
		            	modal1.hide();
		            }
		        });
				
				modal1.$element.find('.btn.save').on("click",function(){
					$optionForm.submit();
				});
			}
		});
	};
	
	return {
		init: function(_data, _modal, _cb){
			modal = _modal;
			onSave = _cb;
			$form = modal.$element.find('form');
			$table = modal.$element.find('table');
			id = _data?_data.id:-1;
			isAdd = !(id && id > 0);
			
			$form.find('select[name="kmId"]').select3({
	    		placeholder: "请选择",
	    		autoLoad: true,
	    		tableName: "km_dic",
	    		value: isAdd?null:_data.kmId,
	    		idField: "id",
				nameField: "name"
	    	});
			$form.find('select[name="njId"]').select3({
	    		placeholder: "请选择",
	    		autoLoad: true,
	    		tableName: "nj_dic",
	    		value: isAdd?null:_data.njId,
	    		idField: "id",
				nameField: "name"
	    	});
			
			if(isAdd){
				initTable([]);
			} else {
				App.getJSON(basePath+'base/cstk/'+id, {}, function(result){
					$form.loadForm(result.data);
					initTable(result.data.options);
				});
			}
			
			initValidHandler(modal);
			initSaveHandler(modal);
		}
	};
});