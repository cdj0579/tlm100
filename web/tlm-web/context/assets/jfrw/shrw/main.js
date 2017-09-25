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
			"RememberBaseInfo": "assets/base/cookie/rememberBaseInfo"
		},shim: {
		}
	});
	
	require(['app','layout','demo']);
	require(['domready!', 'app', 'datatables.bt', "select3"], function (doc, App){
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
	                  { title: "任务名称", data: "parent", render: function(data, type, full){
	                      return '<div class="tooltips" data-container="body" data-placement="top" data-original-title="'+data.desc+'">'+data.name+'</div>';
	                  }},
	                  { title: "任务类型", data: "parent", render: function(data, type, full){
	                	  if(data.type == 1){
	                		  return "习题任务";
	                	  } else if(data.type == 2){
	                		  return "知识内容任务";
	                	  } else if(data.type == 3){
	                		  return "专题内容任务";
	                	  } else {
	                		  return "";
	                	  }
	                  }},
	                  { title: "科目", data: "parent", render: function(data, type, full){
	                	  var v = data.kmId;
	                	  $.each(kmList, function(){
	                		  if(v == this.id){
	                			  v = this.name;
	                			  return false;
	                		  }
	                	  });
	                	  return v;
	                  }},
	                  { title: "年级", data: "parent", render: function(data, type, full){
	                	  var v = data.njId;
	                	  $.each(njList, function(){
	                		  if(v == this.id){
	                			  v = this.name;
	                			  return false;
	                		  }
	                	  });
	                	  return v;
	                  }},
	                  { title: "完成用户", data: "user", render: function(data, type, full){
	                	  return data.name;
	                  }},
	                  { title: "状态", data: "status", render: function(data, type, full){
	                	  if(data == 1){
	    	        		  return '<span class="font-grey-cascade">已获取积分<span class="font-blue">'+full.parent.jf+'</span>。</span>';
	    	        	  } else if(data == 2){
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
					type: data.parent.type,
					rwName: data.parent.name,
					userName: data.user.name,
					rwDesc: data.parent.desc,
					userNo: data.user.userNo,
					rwJf: data.parent.jf
			};
			App.getJSON(basePath+'jfrw/rw/content', {contentId: data.contentId, type: d.type}, function(result){
				var $label = $form.find('.form-group.content label.control-label');
				var $content = $form.find('.form-group.content .cke_textarea_inline');
				if(d.type == 1){
					d.rwTypeName = "习题任务";
					d.xtName = result.data.name;
					$label.html("习题内容");
					$content.html(result.data.content);
					$form.hideOrShowFormItem(['xtName'], true);
	    	    } else if(d.type == 2){
	    	    	d.rwTypeName = "知识内容任务";
	    	    	d.zsdContentName = result.data.name;
					$label.html("知识点内容");
					$content.html(result.data.content);
	    	    	$form.hideOrShowFormItem(['zsdContentName'], true);
	    	    } else if(d.type == 3){
	    	    	d.rwTypeName = "专题内容任务";
	    	    	d.ztContentName = result.data.name;
					$label.html("专题内容");
					$content.html(result.data.content);
	    	    	$form.hideOrShowFormItem(['ztContentName'], true);
	    	    }
				$form.loadForm(d);
			});
			
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