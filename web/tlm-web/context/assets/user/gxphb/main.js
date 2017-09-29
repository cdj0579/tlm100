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
			
		},shim: {
		}
	});
	
	require(['app','layout']);
	require(['domready!', 'app','datatables.bt' ], function (doc, App){
		
		var initTable1 = function(type){
			var $table1 = $('#'+type+'_table');
			dt1 = $table1.dataTable( {
				ajax: {
	        		url: basePath+"system/gxphb",
	        		data: {"type":type},
					dataSrc: function(result){
						var success = App.handlerGridLoad(result);
						console.info(result.data)
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
				pageLength: 20,
				lengthChange: false,
				dom: "<'table-scrollable't><'row'<'col-md-5 col-sm-5'i><'col-md-7 col-sm-7'p>>",
				drawCallback: function(){ App.handleTooltips();  },
	            columns: [
	                  { title: "名称",width:'60%', data: "name", render: function(data, type, full){
	                      return '<div class="tooltips" data-container="body" data-placement="top" data-original-title="'+data+'">'+data+'</div>';
	                  }},
	                  { title: "收藏次数",width:'20%', data: "num", render: function(data, type, full){
	                	 return data;
	                  }},
	                  { title: "贡献积分",width:'20%', data: "jf"}
	    	      ]
		      } );
		};
		initTable1("zsd");
		initTable1("ja");
		initTable1("xt");
		initTable1("zt");
		
	});
	
});