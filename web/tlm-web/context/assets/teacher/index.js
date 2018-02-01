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
	        		url: basePath+"jfrw/rw/dlqrw",
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
	                  { title: "任务名称", data: "rwName", render: function(data, type, full){
	                      return '<div class="tooltips" data-container="body" data-placement="top" data-original-title="'+data+": "+full.rwDesc+'">'+data+'</div>';
	                  }},
	                  { title: "任务类型", data: "type", render: function(data, type, full){
	                	  if(data == 0){
	                		  return "习题任务";
	                	  } else if(data == 1){
	                		  return "知识点任务";
	                	  } else if(data == 2){
	                		  return "专题任务";
	                	  } else {
	                		  return "";
	                	  }
	                  }},
	                  { title: "所属知识点/专题", data: "sourceName", render: function(data, type, full){
	                      return '<div class="tooltips" data-container="body" data-placement="top" data-original-title="'+data+'">'+data+'</div>';
	                  }},
	                  { title:"剩余次数", data: "maxNum", render: function(data, type, full){
	                	  return '<font class="font-yellow">'+full.lqcs+'</font>/<font class="font-green">'+data+'</font>';
	                  }},
	    	          { title: "操作", data: "id", render: function(data, type, full){
	    	        	  return '<a href="javascript:;" class="btn blue lqrw"><i class="fa fa-link"></i> 领取任务</a>';
	    	          }}
	    	      ]
		      } );
			$table1.closest('.dataTables_wrapper').on("click", ".btn.lqrw", function(){
				var $tr = $(this).closest("tr");
				var data = dt1.api().row($tr).data();
				App.confirm({
					title: '提示信息',
					msg: '您确定要领取任务['+data.rwName+']吗？领取后未及时完成将会扣除积分！',
					okFn: function(){
						App.getJSON(basePath+'jfrw/rw/lqrw', {id: data.id}, function(){
							reload();
						});
					},
					cancerFn: function(){}
				});
			});
		};
		
		var initTable2 = function(){
			dt2 = $table2.dataTable( {
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
	                  { title: "任务名称", data: "rwName", render: function(data, type, full){
	                      return '<div class="tooltips" data-container="body" data-placement="top" data-original-title="'+data+" : "+full.rwDesc+'">'+data+'</div>';
	                  }},
	                  { title: "任务类型", data: "type", render: function(data, type, full){
	                	  if(data == 0){
	                		  return "习题任务";
	                	  } else if(data == 1){
	                		  return "知识点任务";
	                	  } else if(data == 2){
	                		  return "专题任务";
	                	  } else {
	                		  return "";
	                	  }
	                  }},
	                  { title: "所属知识点/专题", data: "sourceName", render: function(data, type, full){
	                      return '<div class="tooltips" data-container="body" data-placement="top" data-original-title="'+data+'">'+data+'</div>';
	                  }},
	                  { title: "领取时间", data: "insertTime"},
	    	          { title: "状态/操作", data: "status", render: function(data, type, full){
	    	        	  if(data == 1){
	    	        		  return '<span class="badge badge-warning badge-roundless">待审核</span>';
	    	        	  } else if(data == 2){
	    	        		  //return '<span class="font-grey-cascade">已获取积分<span class="font-blue">'+full.jf+'</span>。</span>';
	    	        		  return '<span class="badge badge-success badge-roundless">审核通过</span>';
	    	        	  } else if(data == 3){
	    	        		  return '<span class="badge badge-danger badge-roundless">审核不通过</span>';
	    	        	  } else if(data == 4){
	    	        		  return '<span class="badge badge-danger badge-roundless">超时未完成</span>';
	    	        	  } else if(data == 5){
	    	        		  return '<span class="badge badge-default badge-roundless">已放弃</span>';
	    	        	  } else {
	    	        		  return '<a href="javascript:;" class="btn blue wcrw"><i class="fa fa-plus"></i> 完成</a>，<a href="javascript:;" class="btn red fqrw"><i class="fa fa-mail-reply"></i> 放弃</a>';
	    	        	  }
	    	          }}
	    	      ]
		      } );
			$table2.closest('.dataTables_wrapper').on("click", ".btn.fqrw", function(){
				var $tr = $(this).closest("tr");
				var data = dt2.api().row($tr).data();
				App.confirm({
					title: '提示信息',
					msg: '您确定要放弃任务['+data.rwName+']吗？',
					okFn: function(){
						App.getJSON(basePath+'jfrw/rw/fqrw', {id: data.id}, function(){
							reload();
						});
					},
					cancerFn: function(){}
				});
			});
			$table2.closest('.dataTables_wrapper').on("click", ".btn.wcrw", function(){
				var $tr = $(this).closest("tr");
				var data = dt2.api().row($tr).data();
				var type = data.type;
				d = {
					rwId: data.id
				};
				if(type == 0){
					d.zsdId = data.sourceId;
					App.ajaxModal({
						id: "addXtRw",
						scroll: true,
						width: "900",
						required: ["addXt"],
						remote: basePath+"assets/zs/xt/edit.html",
						callback: function(modal, args){
							args[0].init(d, modal, reload);
						}
					});
				} else if(type == 1){
					d.pid = data.sourceId;
					d.zsdName = data.sourceName;
					App.ajaxModal({
						id: "addZsdRw",
						scroll: true,
						width: "900",
						required: ["addZsd"],
						remote: basePath+"assets/zs/zsd/editContent.html",
						callback: function(modal, args){
							args[0].init(d, modal, reload);
						}
					});
				} else if(type == 2){
					d.pid = data.sourceId;
					d.ztName = data.sourceName;
					App.ajaxModal({
						id: "addZtRw",
						scroll: true,
						width: "900",
						required: ["addZt"],
						remote: basePath+"assets/zs/zt/editContent.html",
						callback: function(modal, args){
							args[0].init(d, modal, reload);
						}
					});
				} else {
					App.getAlert().warning("错误的任务类型！", "警告");
				}
			});
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