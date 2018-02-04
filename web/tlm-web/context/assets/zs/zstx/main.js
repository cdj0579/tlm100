/**
 * basePath:当前页面的相对路径，如果导入common/head.jsp,则在head.jsp中设置
 */
if(basePath || basePath == ""){
	require.config({
		baseUrl: basePath
	});
	require.config({
		paths: {
	            "RememberBaseInfo": "assets/base/cookie/rememberBaseInfo",
	            "ZjZsdTree": "assets/zs/zstx/zjZsdTree",
	            "EditZsd": "assets/zs/zstx/editZsd",
				"editContent": "assets/zs/zsd/js/editContent",
				"editXt": "assets/zs/xt/js/edit",
				"selectZt": "assets/zs/xt/js/selectZt",
				"selectZsd": "assets/zs/xt/js/selectZsd",
				"Viewer": "assets/zs/reviewContent"
		},shim: {}
	});
}
define(['assets/common/config'], function(config) {
	require.config(config.require);
	
	require(['app', 'layout']);
	require(['domready!','app', 'ZjZsdTree', 'RememberBaseInfo', 'Viewer', 'datatables.bt', 'select3', 'bootstrap-select'], function (doc, App, ZjZsdTree, RememberBaseInfo, Viewer){
		var $tree = $('#zj_tree');
		var $table = $('table');
		var $form = $('form');
		var $search = $('#searchInp');
		var dt = null;
		var currentNode = null;
		var initTree = false;
		var baseParams = {};
		
		var o = RememberBaseInfo.load();
		
		var editZsd = function(data){
			data = $.extend(data, baseParams);
			App.ajaxModal({
				id: "edit",
				scroll: true,
				width: "900",
				required: ["EditZsd"],
				remote: basePath+"assets/zs/zstx/editZsd.html",
				callback: function(modal, args){
					args[0].init(data, modal, reloadTree);
				}
			});
		};
		
		var reloadTree = function(obj){
			if(initTree == true){
				ZjZsdTree.reload(baseParams, obj || currentNode);
			} else {
				ZjZsdTree.init($tree, baseParams, function(treeNode){
					currentNode = treeNode;
					if(treeNode){
						reload();
					}
				}, function(treeNode){
					currentNode = treeNode;
					if(!treeNode){
						reload();
					}
				},editZsd);
				initTree = true;
			}
		};
		var inited = false;
		var setBaseParams = function(o){
			baseParams.bbId = $form.find('select[name="bbId"]').val();
			baseParams.kmId = $form.find('select[name="kmId"]').val();
			baseParams.dqId = $form.find('select[name="dqId"]').val();
			baseParams.njId = $form.find('select[name="njId"]').val();
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
				if((inited == false || !baseParams.bbId) && o.bbId){
					baseParams.bbId = o.bbId;
					$form.find('select[name="bbId"]').val(baseParams.bbId);
				}
			}
			
			if(baseParams.kmId && baseParams.kmId > 0 
				&& baseParams.njId && baseParams.njId > 0
				&& baseParams.dqId
				&& baseParams.xq && baseParams.xq > 0
				&& baseParams.bbId){
				currentNode = null;
				reloadTree();
			}
			inited = true;
		};
		setBaseParams(o);
		
		var getParams = function(data){
			//$.extend(data, baseParams);
			var id = -99999;
			var type = 'zsd';
			if(currentNode){
				if(currentNode.type == 'zj'){
					id = currentNode.zjId;
					type = 'zj';
				} else {
					id = currentNode.id;
				}
			}
			data.id = id;
			data.type = type;
			data.stype = getContentType();
			data.sort = getSortType();
			data.loadAll = true;
			return data;
		};
		
		$form.find('select[name="bbId"]').selectpicker({
            iconBase: 'fa',
            tickIcon: 'fa-check'
        }).on("change", setBaseParams);
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
    		zdLabelText: "科目",
    		openWin: true,
    		value: baseParams.kmId?baseParams.kmId:null,
    		tableName: "km_dic",
    		idField: "id",
			nameField: "name"
    	}).on("select.select3", setBaseParams);
		$form.find('select[name="kmId"]').closest('.form-group').find('.zd-add').addClass('col-md-2');
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
		
		var $contentTypes = $('.btn-group.content-type .btn');
		$contentTypes.on("click", function(){
			var self = this;
			if(!$(this).hasClass("blue")){
				$contentTypes.each(function(){
					if(this != self){
						$(this).removeClass("blue");
					} else {
						$(this).addClass("blue");
					}
				});
			}
			reload();
		});
		var getContentType = function(){
			return $('.btn-group.content-type .btn.blue').data("type");
		};
		var $sortTypes = $('.btn-group.sort-type .btn');
		$sortTypes.on("click", function(){
			var self = this;
			if(!$(this).hasClass("red")){
				$sortTypes.each(function(){
					if(this != self){
						$(this).removeClass("red");
					} else {
						$(this).addClass("red");
					}
				});
			}
			reload();
		});
		var getSortType = function(){
			return $('.btn-group.sort-type .btn.red').data("type");
		};
		
		var initTable = function(){
			dt = $table.dataTable( {
				ajax: {
	        		url: basePath+"zs/zsd/content/search",
	        		data: function(data){
	        			getParams(data);
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
	                  { title: "内容名称", data: "name", width: "40%"},
	                  { title: "类型", data: "type", width: "20%", render: function(data, type, full){
	                	  if(data == 'zsd'){
	                		  return "知识点内容";
	                	  } else {
	                		  return "习题";
	                	  }
	    	          }},
	                  { title: "是否原创", data: "isOriginal", width: "11%", render: function(data, type, full){
	                	  if(data == 1){
	                		  return "原创";
	                	  } else {
	                		  return "非原创";
	                	  }
	    	          }},
	    	          { title: "状态", data: "isSelf", width: "9%", render: function(data, type, full){
	    	        	  if(data == true || data == "true"){
	    	        		  return '<span class="label label-info">自建</span>';
	    	        	  } else {
	    	        		  if(full.collect == "yes"){
	    	        			  return '<span class="label label-success">已收藏</span>';
	    	        		  } else {
	    	        			  return '<span class="label label-warning">未收藏</span>';
	    	        		  }
	    	        	  }
	    	          }},
	    	          { title: "操作", data: "isSelf", width: "20%", render: function(data, type, full){
	    	        	  var btns = '';
	    	        	  if(data == true || data == "true"){
	    	        		  btns += '<a href="javascript:;" class="btn blue edit"> 编辑 <i class="fa fa-edit"></i></a>';
	    	        	  } else {
	    	        		  if(full.collect == "yes"){
	    	        		  } else {
	    	        			  btns += '<a href="javascript:;" class="btn green collect"> 收藏 <i class="fa fa-star"></i></a>';
	    	        		  }
	    	        	  }
	    	        	  btns += '<a href="javascript:;" class="btn yellow review"> 预览 <i class="fa fa-search"></i></a>';
	    	        	  return btns;
	    	          }}
	    	      ]
		      } );
			$tableWrapper = $table.closest('.dataTables_wrapper');
			$tableWrapper.find('.dataTables_filter').appendTo($search);
			
			$tableWrapper.on('click', '.btn.review', function(){
				var $tr = $(this).closest("tr");
				var data = dt.api().row($tr).data();
				Viewer.view(data.id, data.type);
			});
			$tableWrapper.on('click', '.btn.collect', function(){
				var $tr = $(this).closest("tr");
				var data = dt.api().row($tr).data();
				collect(data);
			});
			$tableWrapper.on('click', '.btn.edit', function(){
				var $tr = $(this).closest("tr");
				var data = dt.api().row($tr).data();
				var type = data.type;
				var id = data.id;
				App.getJSON(basePath+"zs/getInfo", {id: id, type: type}, function(result){
					if(type == "zsd"){
						var node = ZjZsdTree.getById(result.data.pid);
						result.data.zsdName = node.name;
						editZsdContent(result.data);
					} else {
						editXt(result.data);
					}
				});
			});
		};
		
		var reload = function(){
			if(dt){
				dt.api().ajax.reload(null, true);
			} else {
				initTable();
			}
		};
		
		var collect = function(data){
			App.confirm({
				title: '提示信息',
				msg: '您确定要收藏'+(data.type=='zsd'?"知识内容":"习题")+'"'+data.name+'"吗？如果收藏需要消耗积分!',
				okFn: function(){
					App.post(basePath+'zs/collect', {id: data.id, type: data.type}, function(){
						reload();
					});
				},
				cancerFn: function(){}
			});
		};
		
		var editZsdContent = function(data){
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
		var editXt = function(data){
			App.ajaxModal({
				id: "edit",
				scroll: true,
				width: "900",
				required: ["editXt"],
				remote: basePath+"assets/zs/xt/edit.html",
				callback: function(modal, args){
					args[0].init(data, modal, reload);
				}
			});
		};
		
		$('.btn.add-zsd-content').on('click', function(){
			var data = $.extend({}, baseParams);
			if(currentNode){
				if(currentNode.type == 'zsd'){
					data.pid = currentNode.id;
					data.zsdName = currentNode.name;
					data.name = currentNode.name;
				}
			}
			editZsdContent(data);
		});
		
		$('.btn.add-xt-content').on('click', function(){
			var data = {};
			if(currentNode){
				if(currentNode.type == 'zsd'){
					data.zsdIds = [currentNode.id];
					data.name = currentNode.name;
				}
			}
			editXt(data);
		});
		
	});
	
});