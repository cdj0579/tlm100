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
			"editLxr": "assets/lxrgl/editLxr",
			"viewLxr": "assets/lxrgl/viewLxr",
			"viewBz": "assets/lxrgl/viewBz"
		},shim: {
		}
	});
	
	require(['app','layout','demo']);
	require(['domready!', 'app', 'datatables.bt', "select3"], function (doc, App){
		var $table = $('#lxr_table');
		var $form = $('form');
		var dt = null;
		
		var initTable = function(){
			dt = $table.dataTable( {
				ajax: {
	        		url: basePath+"lxrgl/list",
	        		data: function(data){
	        			data.dqId = $form.find('select[name="dqId"]').val();
	        			data.xxId = $form.find('select[name="xxId"]').val();
	        			data.nj = $form.find('select[name="nj"]').val();
	        			data.bj = $form.find('select[name="bj"]').val();
	        			data.status = $form.find('select[name="status"]').val();
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
	                  { title: "学生姓名", width: "12%", data: "xingming"},
	                  { title: "所在学校", width: "20%", data: "xuexiao"},
	                  { title: "所在地区", width: "14%", data: "dqName"},
	                  { title: "联系人", width: "12%", data: "lianxiren"},
	                  { title: "使用者", width: "12%", data: "shiyongzhe"},
	                  { title: "分配状态", width: "15%", data: "status", render: function(data, type, full){
	    	        	  if(data == 1){
	    	        		  return '<span class="badge badge-info badge-roundless">已分配</span>';
	    	        	  } else if(data == 2){
	    	        		  return '<span class="badge badge-danger badge-roundless">已关注</span>';
	    	        	  } else if(data == 3){
	    	        		  return '<span class="badge badge-success badge-roundless">已共享</span>';
	    	        	  } else {
	    	        		  return '<span class="badge badge-default badge-roundless">未分配</span>';
	    	        	  }
	    	          }},
	    	          { title: "操作", width: "15%", data: "id", render: function(data, type, full){
	    	        	  var html = "";
	    	        	  if(full.status == 1){
	    	        		  
	    	        	  } else if(full.status == 2){
	    	        		  
	    	        	  } else if(full.status == 3){
	    	        		  html = '<a href="javascript:;" class="btn green cancer-qianyue"> 取消共享 <i class="fa fa-reply"></i></a>';
	    	        	  } else {
	    	        		  html = '<a href="javascript:;" class="btn blue edit"> 编辑 <i class="fa fa-edit"></i></a>';
	    	        	  }
	    	        	  html += '<a href="javascript:;" class="btn yellow view"> 查看 <i class="fa fa-search"></i></a>';
	    	        	  return html;
	    	          }}
	    	      ]
		      } );
			$tableWrapper = $table.closest('.dataTables_wrapper');
			$tableWrapper.find('.dataTables_filter').appendTo($('.search-inp'));
			$tableWrapper.on("click", ".btn.view", function(){
				var $tr = $(this).closest("tr");
				var data = dt.api().row($tr).data();
				view({
					id: data.id,
					lianxiren: data.lianxiren,
					phone: data.phone,
					name: data.xingming,
					xb: data.xingbie,
					xxId: data.xuexiaoId,
					xxMc: data.xuexiao,
					dqId: data.dqId,
					dqName: data.dqName,
					status: data.status,
					shiyongzhe: data.shiyongzhe,
					shijian: data.shijian,
					nj: data.nianji,
					bj: data.banji,
					beizhu: data.beizhu
				});
			});
			$tableWrapper.on("click", ".btn.edit", function(){
				var $tr = $(this).closest("tr");
				var data = dt.api().row($tr).data();
				edit({
					id: data.id,
					lianxiren: data.lianxiren,
					phone: data.phone,
					name: data.xingming,
					xb: data.xingbie,
					xxId: data.xuexiaoId,
					dqId: data.dqId,
					nj: data.nianji,
					bj: data.banji,
					beizhu: data.beizhu
				});
			});
			$tableWrapper.on("click", ".btn.cancer-qianyue", function(){
				var $tr = $(this).closest("tr");
				var data = dt.api().row($tr).data();
				App.confirm({
					title: '提示信息',
					msg: '您确定要取消共享信息吗？',
					okFn: function(){
						App.post(basePath+'lxrgl/cancerQianyue', {id: data.id}, function(){
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
		$form.find('select[name="xxId"]').select3({
    		placeholder: "请选择",
    		autoLoad: true,
    		allowClear: true,
    		tableName: "txl_xuexiao",
    		idField: "id",
			nameField: "xuexiaoming"/*,
			typeField: "pid",
			typeVelue: "330500"*/
    	}).on('select.select3', reload);
		$form.find('select[name="nj"]').select2({
    		placeholder: "请选择",
    		value: -1,
    		allowClear: true
    	}).on("change", reload);
		$form.find('select[name="bj"]').select2({
    		placeholder: "请选择",
    		value: -1,
    		allowClear: true
    	}).on("change", reload);
		$form.find('select[name="status"]').select2({
    		placeholder: "请选择",
    		allowClear: true
    	}).on("change", reload);
		
		var edit = function(data){
			App.ajaxModal({
				id: "edit",
				scroll: true,
				width: "900",
				required: ["editLxr"],
				remote: basePath+"assets/lxrgl/editLxr.html",
				callback: function(modal, args){
					args[0].init(data, modal, reload);
				}
			});
		};
		
		var view = function(data){
			App.ajaxModal({
				id: "view",
				scroll: true,
				width: "900",
				required: ["viewLxr"],
				remote: basePath+"assets/lxrgl/viewLxr.html",
				callback: function(modal, args){
					args[0].init(data, modal);
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