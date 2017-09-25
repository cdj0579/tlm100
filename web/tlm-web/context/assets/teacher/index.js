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
			"RememberBaseInfo": "assets/base/cookie/rememberBaseInfo",
			"addZsd": "assets/zs/zsd/js/editContent",
			"addZt": "assets/zs/zt/js/editContent",
			"addXt": "assets/zs/xt/js/edit",
			"selectZt": "assets/zs/xt/js/selectZt",
			"selectZsd": "assets/zs/xt/js/selectZsd"
		},shim: {
		}
	});
	
	require(['app','layout','demo']);
	require(['domready!', 'app', 'datatables.bt'], function (doc, App){
		var $table1 = $('#table1');
		var $table2 = $('#table2');
		var dt1 = null;
		var dt2 = null;
		
		var initTable1 = function(){
			dt1 = $table1.dataTable( {
				ajax: {
	        		url: basePath+"jfrw/rw/dwcrw",
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
				paging: true,
				pageLength: 10,
				lengthChange: false,
				dom: "<'table-scrollable't><'row'<'col-md-5 col-sm-5'i><'col-md-7 col-sm-7'p>>",
				drawCallback: function(){ App.handleTooltips();  },
	            columns: [
	                  { title: "任务名称", data: "name", render: function(data, type, full){
	                      return '<div class="tooltips" data-container="body" data-placement="top" data-original-title="'+full.desc+'">'+data+'</div>';
	                  }},
	                  { title: "任务类型", data: "type", render: function(data, type, full){
	                	  if(data == 1){
	                		  return "习题任务";
	                	  } else if(data == 2){
	                		  return "知识内容任务";
	                	  } else if(data == 3){
	                		  return "专题内容任务";
	                	  } else {
	                		  return "";
	                	  }
	                  }},
	                  { title: "年级", data: "njName"},
	    	          { title: "操作", data: "id", render: function(data, type, full){
	    	        	  return '<a href="javascript:;" class="btn blue wcrw"><i class="fa fa-link"></i> 完成任务</a>';
	    	          }}
	    	      ]
		      } );
			$table1.closest('.dataTables_wrapper').on("click", ".btn.wcrw", function(){
				var $tr = $(this).closest("tr");
				var data = dt1.api().row($tr).data();
				var type = data.type;
				data = {
					rwId: data.id,
					dqId: data.dqId,
					kmId: data.kmId,
					njId: data.njId,
					xq: data.xq,
					qzqm: data.qzqm
				};
				if(type == 1){
					App.ajaxModal({
						id: "addXtRw",
						scroll: true,
						width: "900",
						required: ["addXt"],
						remote: basePath+"assets/zs/xt/edit.html",
						callback: function(modal, args){
							args[0].init(data, modal, reload);
						}
					});
				} else if(type == 2){
					App.ajaxModal({
						id: "addZsdRw",
						scroll: true,
						width: "900",
						required: ["addZsd"],
						remote: basePath+"assets/zs/zsd/editContent.html",
						callback: function(modal, args){
							args[0].init(data, modal, reload);
						}
					});
				} else if(type == 3){
					App.ajaxModal({
						id: "addZtRw",
						scroll: true,
						width: "900",
						required: ["addZt"],
						remote: basePath+"assets/zs/zt/editContent.html",
						callback: function(modal, args){
							args[0].init(data, modal, reload);
						}
					});
				} else {
					App.getAlert().warning("错误的任务类型！", "警告");
				}
			});
		};
		
		var initTable2 = function(){
			dt2 = $table2.dataTable( {
				ajax: {
	        		url: basePath+"jfrw/rw/ywcrw",
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
				paging: true,
				pageLength: 10,
				lengthChange: false,
				dom: "<'table-scrollable't><'row'<'col-md-5 col-sm-5'i><'col-md-7 col-sm-7'p>>",
				drawCallback: function(){ App.handleTooltips();  },
	            columns: [
	                  { title: "任务名称", data: "name", render: function(data, type, full){
	                      return '<div class="tooltips" data-container="body" data-placement="top" data-original-title="'+full.desc+'">'+data+'</div>';
	                  }},
	                  { title: "任务类型", data: "type", render: function(data, type, full){
	                	  if(data == 1){
	                		  return "习题任务";
	                	  } else if(data == 2){
	                		  return "知识内容任务";
	                	  } else if(data == 3){
	                		  return "专题内容任务";
	                	  } else {
	                		  return "";
	                	  }
	                  }},
	                  { title: "年级", data: "njName"},
	    	          { title: "状态", data: "userStatus", render: function(data, type, full){
	    	        	  if(data == 1){
	    	        		  return '<span class="font-grey-cascade">已获取积分<span class="font-blue">'+full.jf+'</span>。</span>';
	    	        	  } else if(data == 2){
	    	        		  return '<span class="badge badge-danger badge-roundless">审核不通过</span>';
	    	        	  } else {
	    	        		  return '<span class="badge badge-warning badge-roundless">待审核</span>';
	    	        	  }
	    	          }}
	    	      ]
		      } );
		};
		
		var reload = function(type){
			if(type == 1){
				dt1.api().ajax.reload(null, false);
			} else if(type == 2){
				dt2.api().ajax.reload(null, false);
			} else {
				dt2.api().ajax.reload(null, false);
				dt1.api().ajax.reload(null, false);
			} 
		};
		
		initTable1();
		initTable2();
		
		$('.btn.reload1').on("click", function(){
			reload(1);
		});
		$('.btn.reload2').on("click", function(){
			reload(2);
		});
	});
	
});