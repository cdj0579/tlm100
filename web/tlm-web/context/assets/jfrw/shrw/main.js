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
			"Viewer": "assets/zs/reviewContent"
		},shim: {
		}
	});
	
	require(['app','layout','demo']);
	require(['domready!', 'app', 'Viewer', 'datatables.bt', "select3"], function (doc, App, Viewer){
		var $table = $('table');
		var $form = $('form');
		var $search = $('#searchInp');
		var kmList = null;
		var njList = null;
		var dt = null;
		
		var initTable = function(){
			dt = $table.dataTable( {
				ajax: {
	        		url: basePath+"jfrw/rw/dshrw",
	        		data: function(data){
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
	                  { title: "任务名称", data: "rwName", render: function(data, type, full){
	                      return '<div class="tooltips" data-container="body" data-placement="top" data-original-title="'+full.rwDesc+'">'+data+'</div>';
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
	                  { title: "完成用户", data: "userName", render: function(data, type, full){
	                	  return data;
	                  }},
	                  { title: "完成时间", data: "modifyTime"},
	                  { title: "状态", data: "status", render: function(data, type, full){
	                	  if(data == 2){
	    	        		  return '<span class="badge badge-success badge-roundless">审核通过</span>';
	    	        	  } else if(data == 3){
	    	        		  return '<span class="badge badge-danger badge-roundless">审核不通过</span>';
	    	        	  } else {
	    	        		  return '<a href="javascript:;" class="btn blue shrm"> <i class="fa fa-edit"></i> 审核</a>';
	    	        	  }
	                  }}
	    	      ]
		      } );
			$tableWrapper = $table.closest('.dataTables_wrapper');
			$tableWrapper.find('.dataTables_filter').appendTo($search);
			$table.closest('.dataTables_wrapper').on("click", ".btn.shrm", function(){
				var $tr = $(this).closest("tr");
				var data = dt.api().row($tr).data();
				App.ajaxModal({
					id: "shrwWin",
					scroll: true,
					width: "900",
					required: [],
					remote: basePath+"assets/jfrw/shrw/review.html",
					callback: function(modal, args){
						initShrwWin(data, modal);
					}
				});
			});
		};
		
		var initShrwWin = function(data, modal){
			var $form = modal.$element.find("form");
			var d = {
					id: data.id,
					type: data.type,
					rwName: data.rwName,
					sourceName: data.sourceName,
					userName: data.userName,
					rwDesc: data.rwDesc,
					userNo: data.userNo,
					contentName: data.contentName
			};
			var $label = $form.find('.form-group.source label.control-label');
			var $content = $form.find('.form-group.content .cke_textarea_inline span');
			$content.html(d.contentName);
			var typeStr = 'zsd';
			if(d.type == 0){
				d.rwTypeName = "习题任务";
				$label.html("所属知识点");
				typeStr = 'xt';
    	    } else if(d.type == 1){
    	    	d.rwTypeName = "知识点任务";
				$label.html("所属知识点");
    	    } else if(d.type == 2){
    	    	d.rwTypeName = "专题任务";
				$label.html("所属专题");
				typeStr = 'zt';
    	    }
			$form.on('click', '.btn.review', function(){
				Viewer.view(data.contentId, typeStr);
			});
			$form.loadForm(d);
			
			$form.validateB({
	            submitHandler: function () {
	            	$form.ajaxSubmit({
	            		url: basePath+"jfrw/shrw",
	            		type: "POST",
	            		dataType: "json",
	            		success: function(result){
	            			reload();
	            			modal.hide();
	            		}
	            	});
	            }
	        });
			modal.$element.find('.btn.save').on("click",function(){
				$form.submit();
			});
		};
		
		var reload = function(){
			dt.api().ajax.reload(null, false);
		};
		
		$form.find('select[name="status"]').select2({
    		placeholder: "请选择"
    	}).on("change", reload);
		
		App.getJSON(basePath+"dic/all", {types: ["km", "nj"]}, function(result){
			kmList = result.km;
			njList = result.nj;
			initTable();
		});
	});
	
});