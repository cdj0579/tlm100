define(["validate.additional", "select3", "ztree", "ztree.select"], function(){
	var $form = null;
	var modal = null;
	var onSave = null;
	var $tree = null;
	var baseParams = null;
	
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
			check: {
				enable: true,
				chkStyle: "radio" 
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
				}
			}
		};
		treeObj = $.fn.zTree.init($tree,setting, datas);
	};
	
	var selectNode = function(node){
		/*treeObj.selectNode(node, false);
		treeObj.checkNode(node, true);*/
	};
	
	var reload = function(params){
		App.getJSON(basePath+"base/zj/tree", params, function(result){
			var rows = [];
			var bbMap = {};
			bbMap[-1] = {
				id: -1,
				bbId: -1,
				name: "默认版本",
				pid: -9999,
				type: 'bb',
				open: true
			};
			if(result.bbList && result.bbList.length > 0){
				$.each(result.bbList, function(){
					bbMap[this.id] = {
						id: "bb_"+this.id,
						bbId: this.id,
						name: this.name,
						pid: -9999,
						type: 'bb',
						open: true
					};
				});
			}
			var hasBb = [];
			$.each(result.data, function(){
				var bbNode = bbMap[this.bbId];
				if(bbNode && $.inArray(this.bbId, hasBb) == -1){
					hasBb.push(this.bbId);
					rows.push(bbNode);
				}
				this.open = true;
				if(this.bbId != -1 && this.pid == -1){
					this.pid = "bb_"+this.bbId;
				}
				this.nocheck = true;
				rows.push(this);
			});
			initTree($tree, rows);
			var defaultNode = null;
			if(!defaultNode){
				var nodes = treeObj.getNodes();
				if(nodes.length > 0){
					for(var i=0;i<nodes.length;i++){
						var node = nodes[i];
						if(i==0){
							defaultNode = node;
						}
						if(node.isParent){
							defaultNode = node;
							break;
						}
					}
				}
			}
			if(defaultNode){
				selectNode(defaultNode);
			}
		});
	};
	
	var initValidHandler = function(modal){
    	$form.validateB({
            submitHandler: function () {
            	var bbNodes = treeObj.getCheckedNodes(true);
            	if(bbNodes.length == 1){
            		baseParams.bbId = bbNodes[0].bbId;
            		$form.ajaxSubmit({
            			url: basePath+"base/zj/copy",
            			type: "POST",
            			dataType: "json",
            			data: baseParams,
            			success: function(result){
            				App.handlerAjaxJson(result, function(){
            					if($.isFunction(onSave)){
            						onSave();
            					}
            					modal.hide();
            				});
            			}
            		});
            	} else {
            		App.getAlert({
            			positionClass: 'toast-top-center'
            		}).warning("请选择要复制的版本！");
            	}
            }
        });
	};
	
	var initSaveHandler = function(modal){
		modal.$element.find('.btn.save').on("click",function(){
			$form.submit();
		});
	};
	
	return {
		init: function(_data, _modal, _cb){
			modal = _modal;
			onSave = _cb;
			$form = modal.$element.find('form');
			$tree = $form.find('#copy_zj_tree');
			baseParams = {
					newDqId: _data.dqId,
					newBbId: _data.bbId,
					kmId: _data.kmId,
					njId: _data.njId,
					xq: _data.xq
			};
			
			App.initSlimScroll('.scroller');
			
			$form.find('select[name="dqId"]').select3({
	    		placeholder: "请选择",
	    		autoLoad: true,
	    		tableName: "xzqh",
	    		idField: "code",
				nameField: "name",
				typeField: "pid",
				typeVelue: "330500",
				onLoad: function(datas){
					var rows = [];
					$.each(datas, function(){
						if(this.id != baseParams.newDqId){
							rows.push(this);
						}
					});
					return rows;
				}
	    	}).on("select.select3", function(){
	    		_data.dqId = $(this).val();
	    		reload(_data);
	    	});
			
			var $enableSwitch = $form.find('input[name="copyZsd"]');
			$enableSwitch.bootstrapSwitch().on("switchChange.bootstrapSwitch", function(){
				var checked = $enableSwitch.prop("checked");
				$form.hideOrShowFormItem(["copyXt"], checked);
			});
			
			App.initUniform();
			
			initValidHandler(modal);
			initSaveHandler(modal);
		}
	};
});