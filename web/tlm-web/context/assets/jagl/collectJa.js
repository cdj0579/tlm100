define(['datatables.bt', "select3"], function(a, b, RememberBaseInfo){
	var modal = null;
	var onSelected = null;
	var $table = null;
	var $form = null;
	var dt = null;
	var baseParams = {};
	
	var initTable = function(){
		dt = $table.dataTable( {
			ajax: {
        		url: basePath+"zs/xt/globalList",
        		url: basePath+"zs/xt/list",
        		data: function(data){
        			data.global = true;
        			$.extend(data, baseParams);
        		},
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
			scrollY: "200px",
			scrollCollapse: false,
			paging: true,
			pageLength: 10,
			lengthChange: false,
			dom: "f<'table-scrollable't><'row'<'col-md-5 col-sm-5'i><'col-md-7 col-sm-7'p>>",
			drawCallback: function(){ App.handleTooltips();  App.initUniform();},
            columns: [
                  { title: '<input type="checkbox" class="group-checkable"/>', className: "center", width: "8%", data: "id", render: function(data, type, full){
                	  	if(isCollected(data)){
                	  		return '<input type="checkbox" class="checkboxes" value="'+data+'" name="'+data+'" />';
                	  	} else {
                	  		return '<span class="font-green"><i class="fa fa-check"></i>&nbsp;</span>';
                	  	}
				  }, createdCell: function(td){
					  $(td).attr("align", "center");
				  }},
				  { title: "名称", data: "name"},
                  { title: "创建时间", data: "insertTime"},
                  { title: "更新时间", data: "modifyTime"},
    	          { title: "操作", data: "id", render: function(data, type, full){
    	        	  return '<a href="javascript:;" class="btn yellow look"><i class="fa fa-search-plus"></i> 预览</a>';
    	          }}
    	      ]
	      } );
		$tableWrapper = $table.closest('.dataTables_wrapper');
		$tableWrapper.find('.dataTables_filter').appendTo($search);
		$table.closest('.dataTables_wrapper').on("click", ".btn.look", function(){
			var data = dt.api().row($(this).closest("tr")).data();
			App.getAlert({
				positionClass: 'toast-top-center'
			}).info(data.content, "["+data.name+"]习题");
		});
		$table.closest('.dataTables_wrapper').on("change", '.group-checkable', function(){
			var checkboxes = $table.find('tr td input.checkboxes');
			var checked = false;
			if($(this).prop("checked")){
				checked = true;
			}else{
				checked = false;
			}
			checkboxes.attr("checked", checked);
			checkboxes.uniform();
			checkboxes.trigger("change");
		});
		/*$table.closest('.dataTables_wrapper').on("change", 'tr td input.checkboxes', function(){
			var $tr = $(this).closest('tr');
			var data = dt.api().row($tr).data();
			if($(this).prop("checked") == true){
				$selectedContents.cachesSelectedContents("add", data.id, data);
			} else {
				$selectedContents.cachesSelectedContents("remove", data.id);
			}
		});*/
	};
	
	var reload = function(){
		dt.api().ajax.reload(null, false);
	};
	
	return {
		init: function(_modal, _cb){
			modal = _modal;
			onSelected = _cb;
			dt = null;
			$table = modal.$element.find('table');
			$form = modal.$element.find('form');
			
			modal.$element.find('.btn.collect').on('click', function(){
				if($.isFunction(onSelected)){
					onSelected();
				}
				modal.hide();
			});
		}
	};
});