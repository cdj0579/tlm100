define(['ztree'],function(){
	
	var defaultSelectedNodeId = null;
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
				showIcon: true,
				selectedMulti: false/*,
				addDiyDom: function(treeId, treeNode){
					var aObj = $("#" + treeNode.tId + IDMark_A);
					var text = treeNode.name;
					if(treeNode.bm){
						text = treeNode.bm+" "+text;
					}
					$("#" + treeNode.tId + IDMark_Span).html(text);
				}*/
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
	
	var reload = function(){
		App.getJSON(basePath+"jagl/dir/tree", {type: type}, function(result){
			var rows = [{
				id: -1,
				name: "全部",
				pid: -9999,
				isParent: true,
				open: true
			}];
			$.each(result.data, function(){
				this.open = true;
				this.isParent = true,
				rows.push(this);
			});
			initTree($tree, rows);
			var defaultNode = null;
			if(defaultSelectedNodeId){
				defaultNode = findNodeById(defaultSelectedNodeId);
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
	var type = 1;
	
    return {
    	init:function(_tree, _dirType, cb, _onInit){
    		$tree = _tree;
    		onClickCallback = cb;
    		type = _dirType;
    		onInit = _onInit;
    		
    		reload();
    	},
    	reload: function(id){
    		if(id){
    			defaultSelectedNodeId = id;
    		} else {
    			defaultSelectedNodeId = null;
    		}
    		reload();
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