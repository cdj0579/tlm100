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
		},shim: {
		}
	});
	
	require(['app','layout','demo']);
	require(['domready!', 'app', 'datatables.bt'], function (doc, App){
		var $table = $('table');
		var $search = $('#searchInp');
		var dt = null;
		
		var initTable = function(){
			dt = $table.dataTable( {
				ajax: {
	        		url: basePath+"zs/zstx/users",
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
				drawCallback: function(){ App.handleTooltips();  },
	            columns: [
	                  { title: "用户编号", data: "user_no"},
	                  { title: "姓名", data: "name"},
	                  { title: "积分", data: "jf"},
	                  { title: "修改时间", data: "modify_time"},
	                  { title: "授课地址", data: "skdz"},
	    	          { title: "", data: "id", render: function(data, type, full){
	    	        	  return '<a href="'+basePath+'zs/zstx/teacher?user='+full.user_no+'" class="btn blue"> 进入 <i class="fa fa-link"></i></a>';
	    	          }}
	    	      ]
		      } );
			$tableWrapper = $table.closest('.dataTables_wrapper');
			$tableWrapper.find('.dataTables_filter').appendTo($search);
		};
		initTable();
		
		var reload = function(){
			dt.api().ajax.reload(null, false);
		};
		
	});
	
});