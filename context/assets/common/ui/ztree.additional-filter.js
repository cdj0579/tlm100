$.extend($.fn.zTree, {
	filter: function(nodes, text, filterFn){
		var filterNode = function(node, text, filterFn){
			var children = [];
			node = $.extend(true, {}, node);
			if(node.isParent == true){
				for(var i=0;i<node.children.length;i++){
					var cNode = filterNode(node.children[i], text, filterFn);
					if(cNode){
						children.push(cNode);
					}
				}
			}
			node.children = children;
			if(children.length <= 0){
				node.isParent = false;
			}
			if(!$.isFunction(filterFn)){
				var key = filterFn;
				if(typeof(key) != 'string'){
					key = "name";
				}
				filterFn = function(node, text){
					if(node[key].indexOf(text) != -1){
						return true;
					} else {
						return false;
					}
				};
			}
			if(children.length > 0 || filterFn(node, text)){
				return node;
			} else {
				return null;
			}
		};
		var filterNodes = [];
		for(var i=0;i<nodes.length;i++){
			var node = filterNode(nodes[i], text, filterFn);
			if(node){
				filterNodes.push(node);
			}
		}
		return filterNodes;
	},
	reloadNodes: function(zTree, nodes, t, setting){
		zTree.destroy();
		return $.fn.zTree.init(t, setting, nodes);
	}
});