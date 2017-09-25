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
	require(['domready!', 'app', 'RememberBaseInfo', 'datatables.bt', "select3"], function (doc, App, RememberBaseInfo){
		var $table = $('table');
		var $form = $('form');
		var $search = $('#searchInp');
		var dt = null;
		var baseParams = {};
		
		var o = RememberBaseInfo.load();
		
		var initTable = function(){
			dt = $table.dataTable( {
				ajax: {
	        		url: basePath+"jfrw/rw/list",
	        		data: function(data){
	        			$.extend(data, baseParams);
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
	                  { title: "科目", data: "kmName"},
	                  { title: "年级", data: "njName"},
	                  { title: "状态", data: "status", render: function(data, type, full){
	                	  if(data == 3){
	                		  return '<font class="font-grey-cascade">已关闭</font>';
	                	  } else {
	                		  if(data == 2 || full.fulfilNum >= full.maxNum){
	                			  return '<font class="font-green">已完成</font>';
	                		  } else {
	                			  return '进行中: <font class="font-yellow">'+full.fulfilNum+'</font>/<font class="font-green">'+full.maxNum+'</font>';
	                		  }
	                	  }
	                  }},
	    	          { title: "操作", data: "status", render: function(data, type, full){
	    	        	  if(data == 3){
	                		 
	                	  } else {
	                		  if(data == 2 || full.fulfilNum >= full.maxNum){
	                			  
	                		  } else {
	                			  return '<a href="javascript:;" class="btn red close-rm"> <i class="fa fa-close"></i> 关闭</a>';
	                		  }
	                	  }
	    	        	  return "";
	    	          }}
	    	      ]
		      } );
			$tableWrapper = $table.closest('.dataTables_wrapper');
			$tableWrapper.find('.dataTables_filter').appendTo($search);
			$table.closest('.dataTables_wrapper').on("click", ".btn.close-rm", function(){
				var $tr = $(this).closest("tr");
				var data = dt.api().row($tr).data();
				App.confirm({
					title: '提示信息',
					msg: '您确定要关闭任务['+data.name+']吗？',
					okFn: function(){
						App.getJSON(basePath+'jfrw/rw/close', {id: data.id}, function(){
							reload();
						});
					},
					cancerFn: function(){}
				});
			});
		};
		
		var setBaseParams = function(){
			baseParams.kmId = $form.find('select[name="kmId"]').val();
			baseParams.njId = $form.find('select[name="njId"]').val();
			baseParams.status = $form.find('select[name="status"]').val();
			
			if(o && !$.isEmptyObject(o)){
				if(!baseParams.kmId && o.kmId){
					baseParams.kmId = o.kmId;
				}
				if(!baseParams.njId && o.njId){
					baseParams.njId = o.njId;
				}
			}
		};
		
		setBaseParams(o);
		
		$form.find('select[name="kmId"]').select3({
    		placeholder: "请选择",
    		autoLoad: true,
    		value: baseParams.kmId?baseParams.kmId:null,
    		tableName: "km_dic",
    		idField: "id",
			nameField: "name"
    	}).on("select.select3", setBaseParams);
		$form.find('select[name="njId"]').select3({
    		placeholder: "请选择",
    		autoLoad: true,
    		value: baseParams.njId?baseParams.njId:null,
    		tableName: "nj_dic",
    		idField: "id",
			nameField: "name"
    	}).on("select.select3", setBaseParams);
		$form.find('select[name="status"]').select2({
    		placeholder: "请选择"
    	}).on("change", setBaseParams);
		
		RememberBaseInfo.init($form);
		
		var reload = function(){
			dt.api().ajax.reload(null, false);
		};
		
		initTable();
		
		$('.btn.search').on("click", reload);
	});
	
});