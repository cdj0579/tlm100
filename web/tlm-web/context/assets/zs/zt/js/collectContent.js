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
				url: basePath+"zs/zt/otherUserContents",
	            type: "POST",
        		data: function(data){
        			data.name = $form.find('input[name="name"]').val();
        			data.contentName = $form.find('input[name="contentName"]').val();
        			data.userName = $form.find('input[name="userName"]').val();
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
			dom: "<'table-scrollable't><'row'<'col-md-5 col-sm-5'i><'col-md-7 col-sm-7'p>>",
			drawCallback: function(){ App.handleTooltips();  App.initUniform();},
            columns: [
                  { title: '<input type="checkbox" class="group-checkable"/>', width: "8%", data: "id", render: function(data, type, full){
                	  	if(full.collected == "true" || full.collected == true){
                	  		return '<span class="font-green"><i class="fa fa-check"></i>&nbsp;</span>';
                	  	} else {
                	  		return '<input type="checkbox" class="checkboxes" value="'+data+'" name="'+data+'" />';
                	  	}
				  }},
				  { title: "专题", data: "zt", render: function(data, type, full){
                	  return data.name;
                  }},
                  { title: "内容简介", data: "name"},
                  { title: "贡献教师", width: "12%", data: "userName"},
    	          { title: "操作", data: "id", render: function(data, type, full){
    	        	  return '<a href="javascript:void(0);" class="btn yellow review"><i class="fa fa-search-plus"></i> 预览</a>';
    	          }}
    	      ]
	      } );
		$tableWrapper = $table.closest('.dataTables_wrapper');
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
		$table.closest('.dataTables_wrapper').on("change", 'tr td input.checkboxes', function(){
			var $tr = $(this).closest('tr');
			var data = dt.api().row($tr).data();
			if($(this).prop("checked") == true){
				$(this).closest("tr").addClass("selected");
			} else {
				$(this).closest("tr").removeClass("selected");
			}
		});
		$tableWrapper.on('click', '.btn.review', function(){
			var $tr = $(this).closest("tr");
			var data = dt.api().row($tr).data();
			Viewer.view(data.id, "zt");
		});
	};
	
	var reload = function(){
		dt.api().ajax.reload(null, false);
	};
	
	return {
		init: function(_modal, _baseParams, _cb){
			modal = _modal;
			onSelected = _cb;
			baseParams = _baseParams;
			dt = null;
			$table = modal.$element.find('table');
			$form = modal.$element.find('form');
			
			modal.$element.find('.btn.collect').on('click', function(){
				var datas = dt.api().rows('.selected').data();
				if(datas.length > 0){
					var rows = [];
					$.each(datas, function(){
						rows.push({
							id: this.id,
							yyfs: this.yyfs,
							userNo: this.userNo
						});
					});
					App.post(basePath+"zs/ztContent/collect", $.extend({}, baseParam, {
							datas: window.JSON.stringify(rows)
						}),function(result){
						if($.isFunction(onSelected)){
							onSelected(datas);
						}
						modal.hide();
					});
				} else {
					App.getAlert({
						positionClass: 'toast-top-center'
					}).warning("请选择要收藏的专题内容！", "提示");
				}
			});
			
			modal.$element.find('.btn.search').on("click",function(){
				reload();
			});
			
			initTable();
		}
	};
});