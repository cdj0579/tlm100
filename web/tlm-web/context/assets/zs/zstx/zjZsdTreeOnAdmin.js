define(['ztree'],function(){
	
	var defaultSelectedNode = null;
	var $tree = null;
	var reloadTree = null;
	
	var IDMark_Switch = "_switch",
	IDMark_Icon = "_ico",
	IDMark_Span = "_span",
	IDMark_Input = "_input",
	IDMark_Check = "_check",
	IDMark_Edit = "_edit",
	IDMark_Remove = "_remove",
	IDMark_Ul = "_ul",
	IDMark_A = "_a";
	
	var treeObj = null;
	
	function filter(treeId, parentNode, responseData) {
		if(responseData){
			responseData = responseData.rows || [];
	    }
		return responseData;
	}
	var initTree = function($tree, datas){
		var setting = {
			view: {
				dblClickExpand: false,
				showLine: true,
				showIcon: false,
				selectedMulti: false,
				//addHoverDom: addHoverDom,
				//removeHoverDom: removeHoverDom,
				addDiyDom: function(treeId, treeNode){
					var aObj = $("#" + treeNode.tId + IDMark_A);
					var text = treeNode.name;
					if(treeNode.type == "zj"){
						if(treeNode.bm){
							text = '<span class="bold">'+treeNode.bm+" "+text+'</span>';
						}
					} else {
						if(treeNode.isSelf == true && treeNode.modifiedId > 0){
							aObj.prepend('<span class="fa fa-edit font-red"></span>');
							aObj.append("<span class='btn blue diedai' data-id='"+treeNode.modifiedId+"'><i class='fa fa-refresh'></i> 迭代 </span>");
						} else if(treeNode.isSelf == true && treeNode.sLevel == 1){
							aObj.prepend('<span class="fa fa-plus font-green"></span>');
							aObj.append("<span class='btn red caina' data-id='"+treeNode.id+"' data-name='"+treeNode.name
									+"'><i class='fa fa-check'></i> 采纳 </span>");
						} else {
							aObj.prepend('<span class="fa fa-diamond font-blue"></span>');
						}
					}
					$("#" + treeNode.tId + IDMark_Span).html(text);
				}
			},
			data: {
				simpleData: {
					enable:true,
					idKey: "id",
					pIdKey: "pid",
					rootPId: ""
				}
			},
			callback: {
				beforeClick: function(treeId, treeNode) {
				},
				onClick: function(e, treeId, treeNode){
					return onTreeNodeClick(treeNode);
				}
			}
		};
		treeObj = $.fn.zTree.init($tree,setting, datas);
	};
	
	function initEvent($tree){
		$tree.on("click", 'span.btn.diedai', function(e){
			e.stopPropagation()
			var id = $(this).data("id");
			App.ajaxModal({
				id: "diedaiZsd",
				scroll: true,
				width: "900",
				required: [],
				remote: basePath+"zs/zsd/diedaiZsd.modal?id="+id,
				callback: function(modal){
					$form = modal.$element.find('.modal-body form');
					$form.validateB({
			            submitHandler: function () {
			            	$form.ajaxSubmit({
			            		url: basePath+"zs/zsd/diedai",
			            		type: "POST",
			            		dataType: "json",
			            		success: function(result){
			            			App.handlerAjaxJson(result, function(){
			            				reloadTree();
			            				modal.hide();
			            			});
			            		}
			            	});
			            }
			        });
					modal.$element.find('.modal-footer .btn.save').on("click",function(){
						$form.submit();
					});
				}
			});
		});
		$tree.on("click", 'span.btn.caina', function(e){
			e.stopPropagation()
			var id = $(this).data("id");
			var name = $(this).data("name");
			App.confirm({
				title: '提示信息',
				msg: '您确定要采纳知识点['+name+'"]吗？',
				okFn: function(){
					App.post(basePath+'zs/zsd/caina', {id: id}, function(){
						reloadTree();
					});
				},
				cancerFn: function(){}
			});
		});
	}
	
	var onTreeNodeClick = function(treeNode){
		if($.isFunction(onClickCallback)){
			return onClickCallback(treeNode);
		}
	};
	
	var findNodeById = function(id){
		var node = treeObj.getNodeByParam("id", id);
		return node;
	};
	
	var selectNode = function(node){
		treeObj.selectNode(node, false);
		onTreeNodeClick(node);
	};
	
	var reload = function(params){
		params.userNo = userNo;
		App.getJSON(basePath+"base/zj/bb/tree", params, function(result){
			
			var rows = [];
			if(result.data && result.data.length > 0){
				$.each(result.data, function(){
					rows.push({
						id: "zj_"+this.id,
						zjId: this.id,
						name: this.name,
						pid: "zj_"+this.pid,
						isSelf: this.self,
						bm: this.bm,
						type: 'zj',
						drag: false,
						open: true
					});
				});
			}
			$.each(result.zsdList, function(){
				this.open = false;
				this.type = 'zsd';
				if(this.zjId != -1){
					this.pid = "zj_"+this.zjId;
				}
				this.isSelf= this.self;
				this.sLevel = this.level;
				rows.push(this);
			});
			
			initTree($tree, rows);
			var defaultNode = null;
			if(defaultSelectedNode){
				defaultNode = defaultSelectedNode;
			}
			if(!defaultNode){
				var nodes = treeObj.getNodes();
				if(nodes.length > 0){
					defaultNode = nodes[0];
				}
			}
			if(defaultNode){
				selectNode(defaultNode);
			}
			if($.isFunction(onInit)){
				var node = null;
				if(defaultNode){
					node = findNodeById(defaultNode.id)
				}
				onInit(node);
			}
		});
	};
    
	var onClickCallback = null;
	var onInit = null;
	
    return {
    	init:function(_tree, params, cb, _onInit, _reloadTree){
    		$tree = _tree;
    		onClickCallback = cb;
    		onInit = _onInit;
    		reloadTree = _reloadTree;
    		
    		reload(params);
    		
    		initEvent($tree);
    	},
    	reload: function(params, selectedNode){
    		if(selectedNode){
    			defaultSelectedNode = selectedNode;
    		} else {
    			defaultSelectedNode = null;
    		}
    		reload(params);
    	},
    	getById: function(id){
    		return findNodeById(id);
    	},
    	getSelectedNode: function(){
    		var nodes = treeObj.getSelectedNodes();
    		if(nodes && nodes.length == 1){
    			return nodes[0];
    		} else {
    			return null;
    		}
    	},
    	getChildMaxXh: function(node){
    		var max = 0;
    		if(node && node.children){
    			$.each(node.children, function(){
    				var xh = parseInt(this.xh);
    				if(xh > max){
    					max = xh;
    				}
    			});
    		}
    		return max;
    	}
    }
})