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
			"viewBz": "assets/lxrgl/viewBz"
		},shim: {
		}
	});
	
	require(['app','layout','demo']);
	require(['domready!', 'app', 'datatables.bt', "select3", 'jquery.fileupload'], function (doc, App){
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
	                  { title: "学生性别", width: "10%", data: "xingbie", render: function(data, type, full){
	    	        	  if(data == 1){
	    	        		  return "女";
	    	        	  } else if(data == 2){
	    	        		  return "男";
	    	        	  } else {
	    	        		  return "保密";
	    	        	  }
	    	          }},
	                  { title: "所在学校", width: "23%", data: "xuexiao"},
	                  { title: "所在地区", width: "10%", data: "dqName"},
	                  { title: "所在年级", width: "8%", data: "nianji", render: function(data, type, full){
	    	        	  if(data && data > 0){
	    	        		  return data+"年级";
	    	        	  } else {
	    	        		  return "";
	    	        	  }
	    	          }},
	                  { title: "所在班级", width: "8%", data: "banji", render: function(data, type, full){
	    	        	  if(data && data > 0){
	    	        		  return data+"班";
	    	        	  } else {
	    	        		  return "";
	    	        	  }
	    	          }},
	                  { title: "联系人", width: "13%", data: "lianxiren"},
	    	          { title: "操作", width: "12%", data: "id", render: function(data, type, full){
	    	        	  return '<a href="javascript:;" class="btn blue edit"> 编辑 <i class="fa fa-edit"></i></a>'+
	    	        	  '<a href="javascript:;" class="btn red delete"> 删除 <i class="fa fa-remove"></i></a>'+
	    	        	  '<a href="javascript:;" class="btn yellow view"> 查看备注 <i class="fa fa-search"></i></a>';
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
			$tableWrapper.on("click", ".btn.delete", function(){
				var $tr = $(this).closest("tr");
				var data = dt.api().row($tr).data();
				App.confirm({
					title: '提示信息',
					msg: '您确定要删除此联系人吗？',
					okFn: function(){
						App.post(basePath+'lxrgl/delete', {id: data.id}, function(){
							reload();
						});
					},
					cancerFn: function(){}
				});
			});
			$tableWrapper.on("click", ".btn.view", function(){
				var $tr = $(this).closest("tr");
				var data = dt.api().row($tr).data();
				App.ajaxModal({
					id: "view",
					scroll: true,
					width: "1000",
					required: ["viewBz"],
					remote: basePath+"assets/lxrgl/viewBz.html",
					callback: function(modal, args){
						args[0].init(data.beizhu?eval('(' + data.beizhu + ')'):{}, modal);
					}
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
		
		$('.btn-groups .fileinput-button input').fileupload({
            disableImageResize: false,
            autoUpload: true,
            disableImageResize: /Android(?!.*Chrome)|Opera/.test(window.navigator.userAgent),
            maxFileSize: 5000000,
            acceptFileTypes: /(\.|\/)(xls)$/i,
            url: basePath+'lxrgl/upload',
            dataType: 'json',
            /*add: function (e, data) {
                //data.context = $('<p/>').text('Uploading...').appendTo(document.body);
                data.submit();
            },*/
            done: function (e, data) {
            	if(data._response.result.success == true){
            		var list = data._response.result.failureList;
            		if(!list || list.length == 0){
            			App.getAlert().info("上传成功！", "提示");
            		} else {
            			App.ajaxModal({
            				id: "failureList",
            				scroll: true,
            				width: "600",
            				required: [],
            				remote: basePath+"assets/lxrgl/failureLxrs.html",
            				callback: function(modal, args){
            					modal.$element.find('.upload-msg span.font-blue').html(data._response.result.successCount);
            					modal.$element.find('.upload-msg span.font-red').html(list.length);
            					modal.$element.find('table').dataTable( {
            						data: list,
            						scrollY: "330px",
            						scrollCollapse: false,
            						paging: true,
            						pageLength: 10,
            						lengthChange: false,
            						dom: "<'table-scrollable't><'row'<'col-md-5 col-sm-5'i><'col-md-7 col-sm-7'p>>",
            						drawCallback: function(){ App.handleTooltips();  },
            			            columns: [
            			                  { title: "联系人", data: "lianxiren"},
            			                  { title: "联系电话", data: "phone"},
            			                  { title: "失败原因", data: "msg"}
            			    	      ]
            				      } );
            				}
            			});
            		}
            		reload();
            	} else {
            		App.getAlert().error(data._response.result.errors.exception, "错误");
            	}
            }
        });

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
    		allowClear: true
    	}).on("change", reload);
		$form.find('select[name="bj"]').select2({
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
		
		$('.btn.search').on("click", reload);
		
		$('.btn.add').on("click", function(){
			edit();
		});
		
		reload();
	});
	
});