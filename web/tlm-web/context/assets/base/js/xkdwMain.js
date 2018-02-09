/**
 * basePath:当前页面的相对路径，如果导入common/head.jsp,则在head.jsp中设置
 */
if(basePath || basePath == ""){
	require.config({
		baseUrl: basePath
	});
}
define(['assets/common/config'], function(config) {
	require.config(config.require);
	require.config({
		paths: {
			"setXkdw": "assets/base/js/setXkdw"
		},shim: {
		}
	});
	
	require(['app','layout','demo']);
	require(['domready!', 'app', 'datatables.bt'], function (doc, App){
		var $table = $('table');
		var dt = null;
		
		var initTable = function(){
			dt = $table.dataTable( {
				ajax: {
	        		url: basePath+"base/xkdw/list",
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
				paging: false,
				pageLength: 10,
				lengthChange: false,
				dom: "<'table-scrollable't>",
				drawCallback: function(){ App.handleTooltips();  },
	            columns: [
	                  { title: "学科", data: "kmName"},
	                  { title: "档位一", data: "level0"},
	                  { title: "档位二", data: "level1"},
	                  { title: "档位三", data: "level2"},
	    	          { title: "操作", data: "id", render: function(data, type, full){
	    	        	  return '<a href="javascript:;" class="btn blue edit"> 设置 <i class="fa fa-edit"></i></a>';
	    	          }}
	    	      ]
		      } );
			$tableWrapper = $table.closest('.dataTables_wrapper');
		};
		initTable();
		
		var reload = function(){
			dt.api().ajax.reload(null, false);
		};
		
		var edit = function(data){
			
		};
		
		$table.closest('.dataTables_wrapper').on("click", ".btn.edit", function(){
			var $tr = $(this).closest("tr");
			var data = dt.api().row($tr).data();
			App.ajaxModal({
				id: "edit",
				scroll: true,
				width: "900",
				required: ["setXkdw"],
				remote: basePath+"assets/base/setXkdw.html",
				callback: function(modal, args){
					args[0].init(data, modal, reload);
				}
			});
		});
	});
	
});