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
	                  { title: "任务名称", width: "20%", data: "name"},
	                  { title: "任务描述", width: "50%", data: "desc", render: function(data, type, full){
	                      return '<div class="tooltips" data-container="body" data-placement="top" data-original-title="'+data+'">'+data+'</div>';
	                  }},
	                  { title: "状态", width: "20%", data: "status", render: function(data, type, full){
	                	  if(data == 2){
	                		  return '<font class="font-grey-cascade">已关闭</font>';
	                	  } else {
	                		  var count = (full.maxNum*full.rwCount);
	                		  if(data == 1 || full.fulfilNum >= count){
	                			  return '<font class="font-green">已完成</font>';
	                		  } else {
	                			  return '进行中: <font class="font-yellow">'+full.fulfilNum+'</font>/<font class="font-green">'+count+'</font>';
	                		  }
	                	  }
	                  }},
	    	          { title: "操作", width: "10%", data: "status", render: function(data, type, full){
	    	        	  if(data == 2){
	                		 
	                	  } else {
	                		  if(data == 1 || full.fulfilNum >= full.maxNum){
	                			  
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
			baseParams.status = $form.find('select[name="status"]').val();
			reload();
		};
		
		$form.find('select[name="status"]').select2({
    		placeholder: "请选择"
    	}).on("change", setBaseParams);
		
		var reload = function(){
			dt.api().ajax.reload(null, false);
		};
		
		initTable();
	});
	
});