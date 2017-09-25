define(['ztree'],function(){
	
	var defaultSelectedNode = null;
	var $tree = null;
	
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
				addDiyDom: function(treeId, treeNode){
					var aObj = $("#" + treeNode.tId + IDMark_A);
					var text = treeNode.name;
					if(treeNode.bm){
						text = treeNode.bm+" "+text;
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
					onTreeNodeClick(treeNode);
				}
			}
		};
		treeObj = $.fn.zTree.init($tree,setting, datas);
	};
	
	var onTreeNodeClick = function(treeNode){
		if($.isFunction(onClickCallback)){
			onClickCallback(treeNode);
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
		App.getJSON(basePath+"base/zj/tree", params, function(result){
			var rows = [{
				id: -1,
				bbId: -1,
				name: "默认版本",
				pid: -9999,
				type: 'bb',
				open: true
			}];
			if(result.bbList && result.bbList.length > 0){
				$.each(result.bbList, function(){
					rows.push({
						id: "bb_"+this.id,
						bbId: this.id,
						name: this.name,
						pid: -9999,
						type: 'bb',
						open: true
					});
				});
			}
			$.each(result.data, function(){
				this.open = true;
				if(this.bbId != -1 && this.pid == -1){
					this.pid = "bb_"+this.bbId;
				}
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
				onInit(findNodeById(defaultNode.id));
			}
		});
	};
    
	var onClickCallback = null;
	var onInit = null;
	
    return {
    	init:function(_tree, params, cb, _onInit){
    		$tree = _tree;
    		onClickCallback = cb;
    		onInit = _onInit;
    		
    		reload(params);
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