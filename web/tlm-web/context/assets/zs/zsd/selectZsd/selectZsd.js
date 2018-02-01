require.config({
	paths: {
        "RememberBaseInfo": "assets/base/cookie/rememberBaseInfo",
        "ZstxTree": "assets/zs/zsd/selectZsd/ZstxTree",
        "QuickSidebar": "assets/zs/zsd/selectZsd/quick-sidebar",
        "cachesSelectedContents": "assets/zs/zsd/selectZsd/cachesSelectedContents",
		"Viewer": "assets/zs/reviewContent"
	},shim: {}
});
userNo = "T973643720";
define(['RememberBaseInfo', 'ZstxTree', 'Viewer', 'datatables.bt', 'bootstrap-select', 'QuickSidebar', 'cachesSelectedContents'],function(RememberBaseInfo, ZstxTree, Viewer){
	var modal = null;
	var inited = false;
	var defaultSetting = {
			width: 1200,
			type: "zsd"         //zsd,zt,xt,zsdContent,ztContent
	};
	var setting = {};
	var baseParams = {};
	var o = RememberBaseInfo.load();
	
	var $tree = null;
	var currentNode = null;
	var initTree = false;
	
	var $form = null;
	
	var $table = null;
	var $search = null;
	var dt = null;
	
	var $selectedContents = null;
	
	var  isSelected = function(id){
		return $selectedContents.cachesSelectedContents("has", id);
	};
	
	var getTableUrl = function(){
		if(setting.type == "zsd"){
			return "zs/zsd/list";
		} else if(setting.type == 'xt' || setting.type == 'zsdContent'){
			return "zs/zsd/content/search";
		} else if(setting.type == "zt"){
			return "zs/zt/list";
		} else if(setting.type == "ztContent"){
			return "zs/zt/content/list";
		} else if(setting.type == "user"){
			return "user/teacher/list";
		} else {
			return null;
		}
	};
	var getTableColumns = function(){
		var columns = [{ title: '<input type="checkbox" class="group-checkable"/>', className: "center", width: "8%", data: "id", render: function(data, type, full){
			  var  checked = isSelected(data)?' checked="checked"':'';
			  return '<input type="checkbox" class="checkboxes"'+checked+' value="'+data+'" name="'+data+'" />';
		}}];
		if(setting.type == "zsd"){
			columns.push({ title: "名称", width: "20%", data: "name"},
			        { title: "难度", width: "10%", data: "ndName"},
			        { title: "课时(分)", width: "10%", data: "ks"},
			        { title: "描述", width: "60%", data: "desc", render: function(data, type, full){
			        	return '<div class="tooltips" data-container="body" data-placement="top" data-original-title="'+data+'">'+data+'</div>';
			        }});
		} else if(setting.type == 'xt' || setting.type == 'zsdContent'){
			columns.push({ title: "内容名称", data: "name", width: "40%"},
	                  { title: "类型", data: "type", width: "20%", render: function(data, type, full){
	                	  if(data == 'zsd'){
	                		  return "知识点内容";
	                	  } else {
	                		  return "习题";
	                	  }
	    	          }},
	                  { title: "是否原创", data: "isOriginal", width: "14%", render: function(data, type, full){
	                	  if(data == 1){
	                		  return "原创";
	                	  } else {
	                		  return "非原创";
	                	  }
	    	          }},
	    	          { title: "状态", data: "isSelf", width: "13%", render: function(data, type, full){
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
	    	          { title: "查看", data: "isSelf", width: "13%", render: function(data, type, full){
	    	        	  return '<a href="javascript:;" class="btn yellow review"> 预览 <i class="fa fa-search"></i></a>';
	    	          }});
		} else if(setting.type == "zt"){
			columns.push({ title: "名称", width: "20%", data: "name"},
			        { title: "难度", width: "10%", data: "ndName"},
			        { title: "课时(分)", width: "10%", data: "ks"},
			        { title: "描述", width: "60%", data: "desc", render: function(data, type, full){
			        	return '<div class="tooltips" data-container="body" data-placement="top" data-original-title="'+data+'">'+data+'</div>';
			        }});
		} else if(setting.type == "user"){
			columns.push({ title: "姓名", width: "120px", data: "name"},
					{ title: "编号", width: "100px", data: "userNo"},
			        { title: "学科", width: "80px", data: "kmName"},
			        { title: "授课地址", data: "skdz"}
			       	);
		} else if(setting.type == "ztContent"){
			columns.push({ title: "所属专题", data: "zt", render: function(data, type, full){
		          	  return data.name;
		            }},
		            { title: "内容简介", data: "name", render: function(data, type, full){
		          	  var html = data;
		          	  if(full.collected && (full.collected == "true" || full.collected == true)){
		          		  html += '&nbsp;&nbsp;&nbsp;&nbsp;<span class="label label-warning">收藏</span>';
		      	  	  }
		          	  return html;
			          }},
		            { title: "是否原创", data: "isOriginal", render: function(data, type, full){
		          	  if(data == 1){
		          		  return "原创";
		          	  } else {
		          		  return "非原创";
		          	  }
		            }},
		            { title: "消耗积分数", data: "yyfs"},
		            { title: "是否隐藏", data: "isShare", render: function(data, type, full){
		          	  if(data == 1){
		          		  return "显示";
		          	  } else {
		          		  return "隐藏";
		          	  }
		            }},
			          { title: "操作", data: "id", render: function(data, type, full){
			        	  return '<a href="javascript:void(0);" data-type="zt" class="btn yellow review"><i class="fa fa-search-plus"></i> 预览</a>';
			          }});
		} else {
			return null;
		}
		return columns;
	};
	var initTable = function(){
		dt = $table.dataTable( {
			ajax: {
        		url: basePath+getTableUrl(),
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
			scrollY: "280px",
			scrollCollapse: false,
			paging: true,
			pageLength: 10,
			lengthChange: false,
			dom: "f<'table-scrollable't><'row'<'col-md-5 col-sm-5'i><'col-md-7 col-sm-7'p>>",
			drawCallback: function(){ App.handleTooltips();  App.initUniform();},
            columns: getTableColumns()
	      } );
		$tableWrapper = $table.closest('.dataTables_wrapper');
		$tableWrapper.find('.dataTables_filter').appendTo($search);
		
		$tableWrapper.on('click', '.btn.review', function(){
			var $tr = $(this).closest("tr");
			var data = dt.api().row($tr).data();
			Viewer.view(data.id, data.type || $(this).data("type"));
		});
		
		$table.closest('.dataTables_wrapper').on("change", '.group-checkable', function(){
			var checkboxes = $table.find('tr td input.checkboxes');
			var checked = false;
			if($(this).prop("checked")){
				checked = true;
			}else{
				checked = false;
			}
			checkboxes.attr("checked", checked);
			checkboxes.uniform();
			checkboxes.trigger("change");
		});
		$table.closest('.dataTables_wrapper').on("change", 'tr td input.checkboxes', function(){
			var $tr = $(this).closest('tr');
			var data = dt.api().row($tr).data();
			if($(this).prop("checked") == true){
				$selectedContents.cachesSelectedContents("add", data.id, data);
			} else {
				$selectedContents.cachesSelectedContents("remove", data.id);
			}
		});
	};
	
	var initForm = function(){
		if(setting.type == 'zsd' || setting.type == 'zsdContent' || setting.type == 'xt'){
			if(setting.bbList){
				for(var i=0;i<setting.bbList.length;i++){
					var option = setting.bbList[i];
					var selected = "";
					if(baseParams.bbId && baseParams.bbId == option.id){
						selected = ' selected="selected"';
					}
					$form.find('select[name="bbId"]').append($('<option value="'+option.id+'"'+selected+'>'+option.name+'</option>'));
				}
			}
			$form.find('select[name="bbId"]').selectpicker({
				iconBase: 'fa',
				tickIcon: 'fa-check'
			}).on("change", setBaseParams);
		}
		if(setting.type != 'user'){
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
			$form.find('select[name="qzqm"]').select2({
				placeholder: "请选择"
			}).on("change", setBaseParams);
			
			RememberBaseInfo.init($form);
		}
	};
	
	var reloadTree = function(obj){
		if(setting.type == 'zsd' || setting.type == 'zsdContent' || setting.type == 'xt'){
			if(initTree == true){
				ZstxTree.reload(baseParams, obj || currentNode);
			} else {
	    		ZstxTree.init({
	    			el: $tree,
	    			baseParams: baseParams,
	    			loadZsd: (setting.type == "xt" || setting.type == "zsdContent")?true:false,
	    			onClickCallback: function(treeNode){
	    				currentNode = treeNode;
	    				if(treeNode){
	    					reload();
	    				}
	    			},
	    			onInit: function(treeNode){
	    				currentNode = treeNode;
	    				if(!treeNode){
	    					reload();
	    				}
	    			},
	    			reloadTree: reloadTree
	    		});
				initTree = true;
			}
		} else {
			reload();
		}
	};
	
	var setBaseParams = function(o){
		if(setting.type == 'zsd' || setting.type == 'zsdContent' || setting.type == 'xt'){
			baseParams.bbId = $form.find('select[name="bbId"]').val();
		}
		baseParams.kmId = $form.find('select[name="kmId"]').val();
		baseParams.dqId = $form.find('select[name="dqId"]').val();
		baseParams.njId = $form.find('select[name="njId"]').val();
		baseParams.xq = $form.find('select[name="xq"]').val();
		if(setting.type == 'zt' || setting.type == 'ztContent'){
			baseParams.qzqm = $form.find('select[name="qzqm"]').val();
		}
		
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
			if(setting.type == 'zt' || setting.type == 'ztContent'){
				if(!baseParams.qzqm && o.qzqm){
					baseParams.qzqm = o.qzqm;
					$form.find('select[name="qzqm"]').val(baseParams.qzqm);
				}
			}
			if(setting.type == 'zsd' || setting.type == 'zsdContent' || setting.type == 'xt'){
				if((inited == false || !baseParams.bbId) && o.bbId){
					baseParams.bbId = o.bbId;
					$form.find('select[name="bbId"]').val(baseParams.bbId);
				}
			}
		}
		if(setting.type == "user"){
			reload();
		}else 
		if(baseParams.kmId && baseParams.kmId > 0 
			&& baseParams.njId && baseParams.njId > 0
			&& baseParams.dqId
			&& baseParams.xq && baseParams.xq > 0
			&& (baseParams.bbId || (setting.type != 'zsd' && setting.type != 'zsdContent' && setting.type != 'xt')) 
			&& (baseParams.qzqm || (setting.type != 'zt' && setting.type != 'ztContent'))){
			currentNode = null;
			reloadTree();
		}
		
		inited = true;
	};
	
	var getParams = function(data){
		$.extend(data, baseParams);
		if(currentNode){
			if(currentNode.type == 'zj'){
				data.zjId = currentNode.zjId;
				data.id = currentNode.zjId;
				data.type = 'zj';
			} else {
				data.id = currentNode.id;
			}
		}
		if(setting.type == 'xt' || setting.type == 'zsdContent'){
			data.stype = setting.type;
		}
		return data;
	};
	
	var reload = function(){
		if(dt){
			dt.api().ajax.reload(null, true);
		} else {
			initTable();
		}
	};
	
	var initModal = function(_modal){
		inited = false;
		initTree = false;
		dt = null;
		modal = _modal;
		$table = modal.$element.find('table');
		$tree = modal.$element.find('div.ztree');
		$search = modal.$element.find('.search-inp');
		$form = modal.$element.find('form');
		$selectedContents = modal.$element.find('.btn.selected-car');
		console.info($table);
		console.info($form);
		var $title = modal.$element.find('.modal-title');
		if(setting.title){
			$title.html(setting.title);
		} else {
			if(setting.type == 'zt'){
				$title.html("选择专题");
			} else if(setting.type == 'xt'){
				$title.html("选择习题");
			} else if(setting.type == 'zsdContent'){
				$title.html("选择知识内容");
			} else if(setting.type == 'ztContent'){
				$title.html("选择专题内容");
			} else if(setting.type == 'user'){
				$title.html("选择发布对象");
			}
		}
		
		$selectedContents.cachesSelectedContents({
			contents: setting.selected
		});
		
		$selectedContents.on("change", function(){
			reload();
		});
		
		setBaseParams(o);
		initForm();
		
		QuickSidebar.init();
		
		modal.$element.find('.btn.sure').on('click', function(){
			var datas = $selectedContents.cachesSelectedContents("getAll");
			if(!datas || datas.length == 0){
				App.getAlert().error("还没有选择的内容!", "提示");
        		return;
			}
			if($.isFunction(setting.onSelected)){
				var datas = $selectedContents.cachesSelectedContents("getAll");
				setting.onSelected([].concat(datas));
				$selectedContents.cachesSelectedContents("clear");
			}
			modal.hide();
		});
		modal.$element.find('.btn.close-btn').on('click', function(){
			var datas = $selectedContents.cachesSelectedContents("getAll");
			if(datas && datas.length > 0){
				App.confirm({
					title: '提示信息',
					msg: '已选择了'+datas.length+'条内容，确定要关闭吗？',
					okFn: function(){
						modal.hide();
					},
					cancerFn: function(){}
				});
			} else {
				modal.hide();
			}
		});
	};
	
	var getModalHtmlUrl = function(){
		if(setting.type == "zsd" || setting.type == "zsdContent" || setting.type == "xt"){
			return "assets/zs/zsd/selectZsd/selectZsd.html";
		} else if(setting.type == "zt" || setting.type == "ztContent"){
			setting.width = 950;
			return "assets/zs/zsd/selectZsd/selectZt.html";
		} else if(setting.type == "user"){
			setting.width = 950;
			return "assets/zs/zsd/selectZsd/selectUser.html";	
		} else {
			return null;
		}
	};
	
    return {
    	init:function(options){
    		setting = $.extend(setting, defaultSetting, options);
    		var htmlUrl = getModalHtmlUrl();
    		
    		var showModal = function(){
    			App.ajaxModal({
					id: "selectZsd",
					scroll: true,
					width: setting.width,
					referer: setting.referer,
					remote: basePath+htmlUrl,
					callback: function(_modal, args){
						initModal(_modal);
					}
				});
    		};
    		
    		if(setting.type == 'zsd' || setting.type == 'zsdContent' || setting.type == 'xt'){
    			App.getJSON(basePath+'zs/selected/init', {type: setting.type}, function(result){
    				setting.bbList = result.bbList;
    				showModal();
    			});
    		} else {
    			showModal();
    		}
    	}
    }
})