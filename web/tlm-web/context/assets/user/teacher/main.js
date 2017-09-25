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
			"edit": "assets/user/teacher/edit"
		},shim: {
		}
	});
	
	require(['app','layout','demo']);
	require(['domready!', 'app', 'datatables.bt', "select3"], function (doc, App){
		var $table = $('#teacher_table');
		var $search = $('#searchInp');
		var dt = null;
		
		var initTable = function(){
			dt = $table.dataTable( {
				ajax: {
	        		url: basePath+"user/teacher/list",
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
	                  { title: "用户编号", data: "userNo"},
	                  { title: "姓名", data: "name"},
	                  { title: "学科", data: "kmName"},
	                  { title: "授课地址", data: "skdz"},
	                  { title: "教师资格证", data: "jszgz", render: function(data, type, full){
	                	  if(data){
	                		  return '<a href="javascript:;" data-src="'+data+'" class="btn yellow show-img"><i class="fa fa-file-image-o"></i> 查看 </a>';
	                	  } else {
	                		  return '未上传';
	                	  }
	    	          }},
	                  { title: "等级证书", data: "djzs", render: function(data, type, full){
	                	  if(data){
	                		  return '<a href="javascript:;" data-src="'+data+'" class="btn yellow show-img"><i class="fa fa-file-image-o"></i> 查看 </a>';
	                	  } else {
	                		  return '未上传';
	                	  }
	    	          }},
	                  { title: "荣誉证书", data: "ryzs", render: function(data, type, full){
	                	  if(data){
	                		  return '<a href="javascript:;" data-src="'+data+'" class="btn yellow show-img"><i class="fa fa-file-image-o"></i> 查看 </a>';
	                	  } else {
	                		  return '未上传';
	                	  }
	    	          }}/*,
	    	          { title: "操作", data: "id", render: function(data, type, full){
	    	        	  return '<a href="javascript:;" class="btn blue updatePwd"> 修改密码 <i class="fa fa-edit"></i></a>';
	    	          }}*/
	    	      ]
		      } );
			$tableWrapper = $table.closest('.dataTables_wrapper');
			$tableWrapper.find('.dataTables_filter').appendTo($search);
			
			$tableWrapper.on('click', '.btn.show-img', function(){
				var data = $(this).data("src");
				showImage(data);
			});
		};
		initTable();
		
		var reload = function(){
			dt.api().ajax.reload(null, false);
		};
		
		var showImage = function(data){
			App.ajaxModal({
				id: "showImage",
				scroll: true,
				width: "900",
				required: [],
				remote: basePath+"assets/user/teacher/imageView.html",
				callback: function(modal){
					modal.$element.find('img').attr("src", data);
				}
			});
		};
		
		/*$('.btn.query').on("click", reload);*/
		
	});
	
});