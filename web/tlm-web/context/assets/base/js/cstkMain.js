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
			"editCstk": "assets/base/js/editCstk"
		},shim: {
		}
	});
	
	require(['app','layout','demo']);
	require(['domready!', 'app', 'datatables.bt', "select3"], function (doc, App){
		var $table = $('#cstk_table');
		var $search = $('#searchInp');
		var dt = null;
		
		var initTable = function(){
			dt = $table.dataTable( {
				ajax: {
	        		url: basePath+"base/cstk/list",
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
	                  { title: "学科", data: "kmName"},
	                  { title: "年级", data: "njName"},
	                  { title: "题目", data: "name"},
	    	          { title: "操作", data: "id", render: function(data, type, full){
	    	        	  return '<a href="javascript:;" class="btn red delete" data-id="'+data+'"> 删除 <i class="fa fa-remove"></i></a>'+
	    	        	  '<a href="javascript:;" class="btn blue edit"> 编辑 <i class="fa fa-edit"></i></a>';
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
		
		var edit = function(data){
			App.ajaxModal({
				id: "edit",
				scroll: true,
				width: "900",
				required: ["editCstk"],
				remote: basePath+"assets/base/editCst.html",
				callback: function(modal, args){
					args[0].init(data, modal, reload);
				}
			});
		};
		
		/*$('.btn.query').on("click", reload);*/
		
		$('.btn.add').on("click", function(){
			edit();
		});
		$table.closest('.dataTables_wrapper').on("click", ".btn.edit", function(){
			var $tr = $(this).closest("tr");
			var data = dt.api().row($tr).data();
			edit(data);
		});
		$table.closest('.dataTables_wrapper').on("click", ".btn.delete", function(){
			var id = $(this).data("id");
			App.confirm({
				title: '提示信息',
				msg: '您确定要删除此测试题吗？',
				okFn: function(){
					App.getJSON(basePath+'base/cstk/delete', {id: id}, function(){
						reload();
					});
				},
				cancerFn: function(){}
			});
		});
	});
	
});