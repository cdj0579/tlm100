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
				ordering: true,
				scrollY: "330px",
				scrollCollapse: false,
				paging: true,
				pageLength: 10,
				lengthChange: false,
				dom: "f<'table-scrollable't><'row'<'col-md-5 col-sm-5'i><'col-md-7 col-sm-7'p>>",
				drawCallback: function(){ App.handleTooltips();  },
				columnDefs: [{
					"targets": 0,
				     "orderable": true
				},{
					"targets": [1,2,3,4,5],
				     "orderable": false
				}],
	            columns: [
	                  { title: "创建时间", width: "15%", data: "shijian"},
	    	          { title: "分配单数", width: "10%", data: "danliang", render: function(data, type, full){
	    	        	  return data+'单/人';
	    	          }},
	    	          { title: "所属地区", width: "10%", data: "lxrDqName"},
	                  { title: "所属学校", width: "20%", data: "xuexiaoId", render: function(data, type, full){
	    	        	  if(data){
	    	        		  if(data.indexOf(",") != -1){
	    	        			  return "多个";
	    	        		  } else {
	    	        			  return full.xuexiao;
	    	        		  }
	    	        	  } else {
	    	        		  return "";
	    	        	  }
	    	          }},
	    	          { title: "备注", width: "30%", data: "beizhu", render: function(data, type, full){
	    	        	  if(data && data.length > 20){
	    	        		  return '<div class="tooltips" data-container="body" data-placement="top" data-original-title="'+data+'">'+data+'</button>';
	    	        	  } else {
	    	        		  return data;
	    	        	  }
	    	          }},
	                  /*{ title: "所属年级", data: "nj", render: function(data, type, full){
	    	        	  if(data){
	    	        		  if(data.indexOf(",") != -1){
	    	        			  return "多个";
	    	        		  } else {
	    	        			  return data+"年级";
	    	        		  }
	    	        	  } else {
	    	        		  return "";
	    	        	  }
	    	          }},
	                  { title: "所属班级", data: "bj", render: function(data, type, full){
	    	        	  if(data){
	    	        		  if(data.indexOf(",") != -1){
	    	        			  return "多个";
	    	        		  } else {
	    	        			  return data+"班";
	    	        		  }
	    	        	  } else {
	    	        		  return "";
	    	        	  }
	    	          }},*/
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
				edit({
					id: data.id,
					beizhu: data.beizhu,
					zhouqi: data.zhouqi,
					danliang: data.danliang,
					xxId: data.xuexiaoId?data.xuexiaoId.split(","):null,
					nj: data.nj?data.nj.split(","):null,
					bj: data.bj?data.bj.split(","):null,
					dqId: data.dqId,
					lxrDqId: data.lxrDqId,
					lryId: data.lryId
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
		
		var updateZhouqi = function(data){
			App.ajaxModal({
				id: "updateZhouqi",
				scroll: true,
				width: "600",
				required: [],
				remote: basePath+"assets/fpgzgl/updateZhouqi.html",
				callback: function(modal, args){
					var $form = modal.$element.find("form");
					App.getJSON(basePath+"fpgzgl/getZhouqi", {}, function(result){
						$form.loadForm({
							zhouqi: result.zhouqi>0?result.zhouqi:7
						});
					});
					
					$form.validateB({
			            submitHandler: function () {
			            	$form.ajaxSubmit({
			            		url: basePath+"fpgzgl/updateZhouqi",
			            		type: "POST",
			            		dataType: "json",
			            		success: function(result){
			            			App.handlerAjaxJson(result, function(){
			            				modal.hide();
			            			});
			            		}
			            	});
			            }
			        });
					
					modal.$element.find('.modal-footer .btn.save').on("click",function(){
						$form.submit();
					});
				}
			});
		};
		
		$('.btn.search').on("click", reload);
		
		$('.btn.add').on("click", function(){
			edit();
		});
		
		$('.btn.setting').on("click", function(){
			updateZhouqi();
		});
		
		reload();
	});
	
});