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
			"editGz": "assets/fpgzgl/editGz"
		},shim: {
		}
	});
	
	require(['app','layout','demo']);
	require(['domready!', 'app', 'datatables.bt', "select3"], function (doc, App){
		var $table = $('#fpgz_table');
		var $form = $('form');
		var dt = null;
		
		var initTable = function(){
			dt = $table.dataTable( {
				ajax: {
	        		url: basePath+"fpgzgl/list",
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
	                  { title: "分配更新周期", data: "zhouqi", render: function(data, type, full){
	    	        	  return data+'天';
	    	          }},
	    	          { title: "分配单数", data: "danliang", render: function(data, type, full){
	    	        	  return data+'单/人';
	    	          }},
	                  { title: "所属学校", data: "xuexiao"},
	                  { title: "所属年级", data: "nj", render: function(data, type, full){
	    	        	  if(data && data > 0){
	    	        		  return data+"年级";
	    	        	  } else {
	    	        		  return "";
	    	        	  }
	    	          }},
	                  { title: "所属班级", data: "bj", render: function(data, type, full){
	    	        	  if(data && data > 0){
	    	        		  return data+"班";
	    	        	  } else {
	    	        		  return "";
	    	        	  }
	    	          }},
	    	          { title: "操作", data: "id", render: function(data, type, full){
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
				edit({
					id: data.id,
					zhouqi: data.zhouqi,
					danliang: data.danliang,
					xxId: data.xuexiaoId,
					nj: data.nj,
					bj: data.bj,
					dqId: data.dqId
				});
			});
			$tableWrapper.on("click", ".btn.delete", function(){
				var $tr = $(this).closest("tr");
				var data = dt.api().row($tr).data();
				App.confirm({
					title: '提示信息',
					msg: '您确定要删除此分配规则吗？',
					okFn: function(){
						App.post(basePath+'fpgzgl/delete', {id: data.id}, function(){
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
		
		var edit = function(data){
			App.ajaxModal({
				id: "edit",
				scroll: true,
				width: "900",
				required: ["editGz"],
				remote: basePath+"assets/fpgzgl/editGz.html",
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