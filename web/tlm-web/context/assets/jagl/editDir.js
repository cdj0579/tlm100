define(["validate.additional"], function(){
	var addUrl = "jagl/dir/add";
	var updateUrl = "jagl/dir/update";
	var $tree = null;
	var currentNode = null;
	var dirType = 1;
	var DirTree = null;
	
	var editDir = function(data, cb){
		App.ajaxModal({
			id: "editDir",
			scroll: true,
			width: "900",
			required: [],
			remote: basePath+"assets/jagl/editDir.html",
			callback: function(modal, args){
				initEdit(data, modal, cb);
			}
		});
	};
	
	var initEdit = function(_data, _modal, _cb){
		var modal = _modal;
		var onSave = _cb;
		var $form = modal.$element.find('form');
		var id = _data?_data.id:-1;
		var isAdd = !(id && id > 0);
		
		$form.loadForm(_data);
		
		var url = isAdd?addUrl:updateUrl;
    	$form.validateB({
            submitHandler: function () {
            	$form.ajaxSubmit({
            		url: basePath+url,
            		type: "POST",
            		dataType: "json",
            		success: function(result){
            			App.handlerAjaxJson(result, function(){
            				if($.isFunction(onSave)){
            					onSave(result.id);
            				}
            				modal.hide();
            			});
            		}
            	});
            }
        });
		
		modal.$element.find('.btn.save').on("click",function(){
			$form.submit();
		});
	};
	
	return {
		init: function(options){
			$tree = options.el;
			dirType = options.dirType;
			DirTree = options.DirTree;
			
			if(options.addSelector){
				$(options.addSelector).on("click", function(){
					editDir({
						type: dirType,
						pid: currentNode.id
					}, function(id){
						DirTree.reload(id);
					});
				});
			}
			if(options.editSelector){
				$(options.editSelector).on("click", function(){
					var node = DirTree.getSelectedNode();
					if(node.id == -1){
						return ;
					}
					if(node){
						editDir({
							id: node.id,
							type: dirType,
							pid: currentNode.pid,
							name: node.name
						}, function(id){
							DirTree.reload(node.id);
						});
					} else {
						App.getAlert().warning("请选择要编辑的目录！", "提示");
					}
				});
			}
			if(options.deleteSelector){
				$(options.deleteSelector).on("click", function(){
					var node = DirTree.getSelectedNode();
					if(node.id == -1){
						return ;
					}
					if(node){
						var cNode = node.getParentNode();
						App.confirm({
							title: '提示信息',
							msg: '您确定要删除目录['+node.name+']吗？',
							okFn: function(){
								App.getJSON(basePath+'jagl/dir/delete', {id: node.id}, function(){
									currentNode = null;
									DirTree.reload(cNode?cNode.id:null);
								});
							},
							cancerFn: function(){}
						});
					} else {
						App.getAlert().warning("请选择要删除的目录！", "提示");
					}
				});
			}
		},
		setNode: function(node){
			currentNode = node;
		},
		initEdit: function(_data, _modal, _cb){
			initEdit(_data, _modal, _cb);
		}
	};
});