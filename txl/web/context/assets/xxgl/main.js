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
			"editXx": "assets/xxgl/editXx"
		},shim: {
		}
	});
	
	require(['app','layout','demo']);
	require(['domready!', 'app', 'datatables.bt', "select3"], function (doc, App){
		var $table = $('#xx_table');
		var $form = $('form');
		var dt = null;
		
		var initTable = function(){
			dt = $table.dataTable( {
				ajax: {
	        		url: basePath+"xxgl/list",
	        		data: function(data){
	        			data.dqId = $form.find('select[name="dqId"]').val();
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
				scrollY: "330px",
				scrollCollapse: false,
				paging: true,
				pageLength: 10,
				lengthChange: false,
				dom: "f<'table-scrollable't><'row'<'col-md-5 col-sm-5'i><'col-md-7 col-sm-7'p>>",
				drawCallback: function(){ App.handleTooltips();  },
	            columns: [
	                  { title: "所属地区", width: "15%", data: "dqName"},
	                  { title: "学校名称", width: "25%", data: "name"},
	                  { title: "备注", width: "45%", data: "beizhu"},
	    	          { title: "操作", width: "15%", data: "id", render: function(data, type, full){
	    	        	  return '<a href="javascript:;" class="btn blue edit"> 编辑 <i class="fa fa-edit"></i></a>'+
	    	        	  '<a href="javascript:;" class="btn red delete"> 删除 <i class="fa fa-remove"></i></a>';
	    	          }}
	    	      ]
		      } );
			$tableWrapper = $table.closest('.dataTables_wrapper');
			$tableWrapper.find('.dataTables_filter').appendTo($('.search-inp'));
			$tableWrapper.on("click", ".btn.edit", function(){
				var $tr = $(this).closest("tr");
				var data = dt.api().row($tr).data();
				edit(data);
			});
			$tableWrapper.on("click", ".btn.delete", function(){
				var $tr = $(this).closest("tr");
				var data = dt.api().row($tr).data();
				App.confirm({
					title: '提示信息',
					msg: '您确定要删除此学校吗？',
					okFn: function(){
						App.post(basePath+'xxgl/delete', {id: data.id}, function(){
							reload();
						});
					},
					cancerFn: function(){}
				});
			});
		};

		var reload = function(){
			if(dt){
				dt.api().ajax.reload(null, false);
			} else {
				initTable();
			}
		};
		
		$form.find('select[name="dqId"]').select3({
    		placeholder: "请选择",
    		autoLoad: true,
    		allowClear: true,
    		tableName: "xzqh",
    		idField: "code",
			nameField: "name",
			typeField: "pid",
			typeVelue: "330500"
    	}).on('select.select3', reload);
		
		var edit = function(data){
			App.ajaxModal({
				id: "edit",
				scroll: true,
				width: "900",
				required: ["editXx"],
				remote: basePath+"assets/xxgl/editXx.html",
				callback: function(modal, args){
					args[0].init(data, modal, reload);
				}
			});
		};
		
		$('.btn.search').on("click", reload);
		
		$('.btn.add').on("click", function(){
			edit();
		});
		
		reload();
	});
	
});