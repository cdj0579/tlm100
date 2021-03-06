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
			"editContent": "assets/zs/zsd/js/editContent"
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
	        		url: basePath+"zs/zsd/content/list",
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
	                  { title: "知识点", data: "zsd", render: function(data, type, full){
	                	  return data.name;
	                  }},
	                  { title: "内容简介", data: "name"},
	                  { title: "是否原创", data: "isOriginal", render: function(data, type, full){
	                	  if(data == 1){
	                		  return "原创";
	                	  } else {
	                		  return "非原创";
	                	  }
	                  }},
	                  /*{ title: "消耗积分数", data: "yyfs"},*/
	                  { title: "是否隐藏", data: "isShare", render: function(data, type, full){
	                	  if(data == 1){
	                		  return "显示";
	                	  } else {
	                		  return "隐藏";
	                	  }
	                  }},
	    	          { title: "操作", data: "id", render: function(data, type, full){
	    	        	  return '<a href="javascript:;" class="btn red delete"> 删除 <i class="fa fa-remove"></i></a>'+
	    	        	  '<a href="javascript:;" class="btn blue edit"> 编辑 <i class="fa fa-edit"></i></a>';
	    	          }}
	    	      ]
		      } );
			$tableWrapper = $table.closest('.dataTables_wrapper');
			$tableWrapper.find('.dataTables_filter').appendTo($search);
			$table.closest('.dataTables_wrapper').on("click", ".btn.edit", function(){
				var $tr = $(this).closest("tr");
				var data = dt.api().row($tr).data();
				data = $.extend({}, data, {
					zsdName: data.zsd.name
				});
				edit(data);
			});
			$table.closest('.dataTables_wrapper').on("click", ".btn.delete", function(){
				var $tr = $(this).closest("tr");
				var id = dt.api().row($tr).data().id;
				App.confirm({
					title: '提示信息',
					msg: '您确定要删除此知识点内容吗？',
					okFn: function(){
						App.getJSON(basePath+'zs/zsd/content/delete', {id: id}, function(){
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
			baseParams.dqId = $form.find('select[name="dqId"]').val();
			baseParams.xq = $form.find('select[name="xq"]').val();
			
			if(o && !$.isEmptyObject(o)){
				if(!baseParams.dqId && o.dqId){
					baseParams.dqId = o.dqId;
				}
				if(!baseParams.kmId && o.kmId){
					baseParams.kmId = o.kmId;
				}
				if(!baseParams.njId && o.njId){
					baseParams.njId = o.njId;
				}
				if(!baseParams.xq && o.xq){
					baseParams.xq = o.xq;
					$form.find('select[name="xq"]').val(baseParams.xq);
				}
			}
			
			if(baseParams.kmId && baseParams.kmId > 0 
				&& baseParams.njId && baseParams.njId > 0
				&& baseParams.dqId
				&& baseParams.xq && baseParams.xq > 0){
				if(dt){
					reload();
				} else {
					initTable();
				}
			}
		};
		
		setBaseParams(o);
		
		$form.find('select[name="dqId"]').select3({
    		placeholder: "请选择",
    		autoLoad: true,
    		value: baseParams.dqId?baseParams.dqId:null,
    		tableName: "xzqh",
    		idField: "code",
			nameField: "name",
			typeField: "pid",
			typeVelue: "330500"
    	}).on("select.select3", setBaseParams);
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
		$form.find('select[name="xq"]').select2({
    		placeholder: "请选择"
    	}).on("change", setBaseParams);
		
		RememberBaseInfo.init($form);
		
		var reload = function(){
			dt.api().ajax.reload(null, false);
		};
		
		var edit = function(data){
			App.ajaxModal({
				id: "editContent",
				scroll: true,
				width: "900",
				required: ["editContent"],
				remote: basePath+"assets/zs/zsd/editContent.html",
				callback: function(modal, args){
					args[0].init(data, modal, reload);
				}
			});
		};
		
		/*$('.btn.query').on("click", reload);*/
		
		$('.btn.add').on("click", function(){
			if(baseParams.kmId && baseParams.kmId > 0 
				&& baseParams.njId && baseParams.njId > 0
				&& baseParams.dqId
				&& baseParams.xq && baseParams.xq > 0){
				edit(baseParams);
			} else {
				App.getAlert().error("请先选择[地区],[科目],[年级],[学期]!", "提示");
			}
		});
	});
	
});