define(['ztree'],function(){
	
	var defaultSelectedNode = null;
	var $tree = null;
	var editZsd = null;
	
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
				addHoverDom: addHoverDom,
				removeHoverDom: removeHoverDom,
				addDiyDom: function(treeId, treeNode){
					var aObj = $("#" + treeNode.tId + IDMark_A);
					var text = treeNode.name;
					if(treeNode.type == "zj"){
						if(treeNode.bm){
							text = '<span class="bold">'+treeNode.bm+" "+text+'</span>';
						}
					} else {
						if(treeNode.isSelf == true){
							aObj.prepend('<span class="fa fa-diamond font-blue"></span>');
						} else {
							aObj.prepend('<span class="fa fa-share-alt font-grey"></span>');
						}
					}
					if(treeNode.type == "zsd" && treeNode.isSelf == true){
						aObj.prepend("<span class='btn blue edit-zsd' data-id='"+treeNode.id+"' data-name='"+treeNode.name
								+"' data-desc='"+treeNode.desc+"' data-ks='"+treeNode.ks+"' data-ndid='"+treeNode.ndId+"'><i class='fa fa-edit'></i> 修改 </span>");
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
			edit: {
				enable: true,
				showRemoveBtn: false,
				showRenameBtn: false,
				//renameTitle: "修改知识点名称",
				drag: {
					isCopy: false,
					isMove: true,
					prev: dropCheck,
					inner: false,
					next: dropCheck
				}
			},
			callback: {
				beforeClick: function(treeId, treeNode) {
				},
				beforeDrag: beforeDrag,
				beforeDrop: beforeDrop,
				onDrop: onDrop,
				onClick: function(e, treeId, treeNode){
					return onTreeNodeClick(treeNode);
				}
			}
		};
		treeObj = $.fn.zTree.init($tree,setting, datas);
	};
	
	function initEvent($tree){
		$tree.on("click", 'span.btn.edit-zsd', function(e){
			e.stopPropagation()
			var id = $(this).data("id");
			var name = $(this).data("name");
			var desc = $(this).data("desc");
			var ks = $(this).data("ks");
			var ndId = $(this).data("ndid");
			editZsd({
				id: id,
				name: name,
				ndId: ndId,
				ks: ks,
				desc: desc
			});
		});
		$tree.on("click", 'span.btn.add-zsd', function(e){
			e.stopPropagation()
			var zjId = $(this).data("id");
			editZsd({
				zjId: zjId
			});
		});
	}
	
	//只能在同一章节内拖拽
	function dropCheck(treeId, nodes, targetNode) {
		var pNode = nodes[0].getParentNode();
		var isZjNode = "zj" == nodes[0].type;
		if (!isZjNode && pNode && pNode == targetNode.getParentNode()) {
			return true;
		} else {
			return false;
		}
	}
	function beforeDrag(treeId, treeNodes) {
		for (var i=0,l=treeNodes.length; i<l; i++) {
			if (treeNodes[i].drag === false) {
				return false;
			}
		}
		return true;
	}
	function beforeDrop(treeId, treeNodes, targetNode, moveType) {
		if("inner" == moveType){
			return false;
		}
		return true;
	}
	//在这里保存排序信息
	function onDrop(event, treeId, treeNodes, dd, ddd){
		var pNode = treeNodes[0].getParentNode();
		if(pNode){
			saveZsdNoes(pNode);
		}
	}
	function showRenameBtn(treeId, treeNode) {
		if(treeNode.type == "zsd" && treeNode.isSelf == true){
			return true;
		} else {
			return false;
		}
	}
	function addHoverDom(treeId, treeNode) {
		if("zj" != treeNode.type) return;
		var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
		var addStr = "<span class='btn green add-zsd' data-id='"+treeNode.zjId+"' id='addBtn_" + treeNode.tId
			+ "' title='添加知识点' onfocus='this.blur();'><i class='fa fa-plus'></i> 添加 </span>";
		sObj.after(addStr);
	};
	function removeHoverDom(treeId, treeNode) {
		$("#addBtn_"+treeNode.tId).unbind().remove();
	};
	
	var saveZsdNoes = function(zjNode){
		if(zjNode.children && zjNode.children.length > 0){
			var datas = [];
			var zjId = null;
			for(var i=0;i<zjNode.children.length;i++){
				var node = zjNode.children[i];
				zjId = node.zjId;
				datas.push({
					zsdId: node.id,
					xh: i
				});
			}
			App.post(basePath+"zs/zsd/sort", {datas: window.JSON.stringify(datas),zjId:zjId}, function(result){
				App.getAlert().info("保存知识点顺序成功！", "提示");
			},function(result){
				App.getAlert().warning("保存知识点顺序失败！", "提示");
				//reload({});
			});
		}
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
    	init:function(_tree, params, cb, _onInit, _editZsd){
    		$tree = _tree;
    		onClickCallback = cb;
    		onInit = _onInit;
    		editZsd = _editZsd;
    		
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