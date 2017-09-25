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
	            "ZjTree": "assets/base/zj/zjTree",
	            "editZj": "assets/base/zj/editZj",
	            "editBb": "assets/base/zj/editBb"
		},shim: {}
	});
}
define(['assets/common/config'], function(config) {
	require.config(config.require);
	
	require(['app', 'layout']);
	require(['domready!','app', 'ZjTree', 'RememberBaseInfo', 'datatables.bt', 'select3'], function (doc, App, ZjTree, RememberBaseInfo){
		var $tree = $('#zj_tree');
		var $table = $('table');
		var $form = $('form');
		var dt = null;
		var currentNode = null;
		var initTree = false;
		var baseParams = {};
		
		var o = RememberBaseInfo.load();
		
		var reloadTree = function(obj){
			if(initTree == true){
				ZjTree.reload(baseParams, obj || currentNode);
			} else {
				ZjTree.init($tree, baseParams, function(treeNode){
					currentNode = treeNode;
					reload();
				}, function(treeNode){
					currentNode = treeNode;
				});
				initTree = true;
			}
		};
		var setBaseParams = function(o){
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
			}
			
			if(baseParams.kmId && baseParams.kmId > 0 
				&& baseParams.njId && baseParams.njId > 0
				&& baseParams.dqId
				&& baseParams.xq && baseParams.xq > 0){
				currentNode = null;
				reloadTree();
			}
		};
		setBaseParams(o);
		
		var getParams = function(data){
			$.extend(data, baseParams);
			var pid = -1;
			var bbId = -1;
			if(currentNode){
				pid = currentNode.id;
				bbId = currentNode.bbId;
				if(currentNode.type == 'bb'){
					pid = -1;
				}
			}
			data.pid = pid;
			data.bbId = bbId;
			return data;
		};
		
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
    		useZd: true,
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
		
		var initTable = function(){
			dt = $table.dataTable( {
				ajax: {
	        		url: basePath+"base/zj/list",
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
				dom: "<'table-scrollable't><'row'<'col-md-5 col-sm-5'i><'col-md-7 col-sm-7'p>>",
				drawCallback: function(){ App.handleTooltips();  },
	            columns: [
	                  { title: "编目", data: "bm"},
	                  { title: "章节名", data: "name"},
	    	          { title: "操作", data: "id", render: function(data, type, full){
	    	        	  return '<a href="javascript:;" class="btn red delete" data-id="'+data+'"> 删除 <i class="fa fa-remove"></i></a>'+
	    	        	  '<a href="javascript:;" class="btn blue edit"> 编辑 <i class="fa fa-edit"></i></a>';
	    	          }}
	    	      ]
		      } );
			$tableWrapper = $table.closest('.dataTables_wrapper');
			
			$tableWrapper.on('click', '.btn.delete', function(){
				var $tr = $(this).closest("tr");
				var data = dt.api().row($tr).data();
				deleteZj(data.id, data.name);
			});
			$tableWrapper.on('click', '.btn.edit', function(){
				var $tr = $(this).closest("tr");
				var data = dt.api().row($tr).data();
				editZj(data, function(){
					reloadTree();
				});
			});
		};
		
		$('.btn.add-bb').on("click", function(){
			editBb({}, function(id){
				reloadTree(ZjTree.getById("bb_"+id));
			});
		});
		
		$('.btn.edit-bb').on("click", function(){
			var node = ZjTree.getSelectedNode();
			if(node){
				if(node.type == 'bb'){
					editBb({
						id: node.bbId,
						value: node.name
					}, function(id){
						reloadTree();
					});
				} else {
					App.getAlert().warning("当前选择的不是章节版本！", "提示");
				}
			} else {
				App.getAlert().warning("请选择要编辑的章节版本！", "提示");
			}
		});
		$('.btn.delete-bb').on("click", function(){
			var node = ZjTree.getSelectedNode();
			if(node){
				if(node.type == 'bb'){
					if(node.id == -1){
						App.getAlert().warning("当前选择的是系统默认章节版本, 不能删除！", "提示");
						return false;
					}
					App.confirm({
						title: '提示信息',
						msg: '您确定要删除章节版本['+node.name+']吗？此版本的所有章节将会被一并删除!',
						okFn: function(){
							App.getJSON(basePath+'dic/bb/delete', {id: node.bbId}, function(){
								currentNode = null;
								reloadTree();
							});
						},
						cancerFn: function(){}
					});
				} else {
					App.getAlert().warning("当前选择的不是章节版本！", "提示");
				}
			} else {
				App.getAlert().warning("请选择要删除的章节版本！", "提示");
			}
		});
		
		var editBb = function(data, cb){
			App.ajaxModal({
				id: "editBb",
				scroll: true,
				width: "900",
				required: ["editBb"],
				remote: basePath+"assets/base/zj/editBb.html",
				callback: function(modal, args){
					args[0].init(data, modal, cb);
				}
			});
		};
		
		$('.btn.add').on("click", function(){
			var data = getParams({});
			editZj(data, function(id){
				reloadTree();
			});
		});
		
		var deleteZj = function(id, name){
			App.confirm({
				title: '提示信息',
				msg: '您确定要删除章节['+name+']吗？此章节的子章节将会被一并删除!',
				okFn: function(){
					App.getJSON(basePath+'base/zj/delete', {id: id}, function(){
						reloadTree();
					});
				},
				cancerFn: function(){}
			});
		};
		
		var editZj = function(data, cb){
			App.ajaxModal({
				id: "edit",
				scroll: true,
				width: "900",
				required: ["editZj"],
				remote: basePath+"assets/base/zj/editZj.html",
				callback: function(modal, args){
					if(currentNode.type != 'bb'){
						data.pBm = currentNode.bm;
					} else {
						data.pBm = null;
					}
					if(!data.xh || data.xh <= 0){
						data.xh = ZjTree.getChildMaxXh(currentNode)+1;
						data.bm = data.pBm?(data.pBm+"."+data.xh):data.xh;
					}
					args[0].init(data, modal, cb);
				}
			});
		};
		
		var reload = function(){
			if(dt){
				dt.api().ajax.reload(null, false);
			} else {
				initTable();
			}
		};
		
	});
	
});