define(['datatables.bt'], function(){
	var modal = null;
	var onSelected = null;
	var $table = null;
	var dt = null;
	
	var initTable = function(){
		dt = $table.dataTable( {
			ajax: {
				url: basePath+"jagl/templetes/list",
				dataSrc: function(result){
					var success = App.handlerGridLoad(result);
					var rows = [];
					if(success == true){
						rows = result.data;
					}
					rows.splice(0, 0, {
						name: '系统默认模板',
						type: 'system'
					});
					return rows;
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
			drawCallback: function(){ App.handleTooltips();  },
			columns: [
			          { title: "模板", data: "name", width: "70%"},
			          { title: "操作", data: "id", width: "30%", render: function(data, type, full){
			        	  return '<a href="javascript:;" class="btn green import"><i class="fa fa-arrow-circle-left"></i> 导入</a>';
			          }}
			]
		} );
		$table.closest('.dataTables_wrapper').on("click", ".btn.import", function(){
			var data = dt.api().row($(this).closest('tr')).data();
			var cb = function(html){
				if($.isFunction(onSelected)){
					onSelected(html);
				}
				modal.hide();
			};
			if(data.type == 'system'){
				$.ajax({
					url: basePath+'assets/jagl/templetes/defaultTemplete.html',
					dataType: 'html',
					success: function(result){
						cb(result);
					}
				});
			} else {
				cb(data.content);
			}
		});
	};
	
	return {
		init: function(_modal, _cb){
			modal = _modal;
			onSelected = _cb;
			$table = modal.$element.find('table');
			
			initTable();
		}
	};
});