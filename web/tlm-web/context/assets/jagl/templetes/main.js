/**
 * basePath:当前页面的相对路径，如果导入common/head.jsp,则在head.jsp中设置
 */
if(basePath || basePath == ""){
	require.config({
		baseUrl: basePath
	});
}
define(['assets/common/config'], function(config) {
	require.config(config.require);
	require.config({
		paths: {
			"DirTree": "assets/jagl/dirTree",
			"editDir": "assets/jagl/editDir"
		},shim: {
		}
	});
	
	require(['app','layout','demo']);
	require(['domready!', 'app', 'DirTree', 'editDir', 'datatables.bt'], function (doc, App, DirTree, EditDir){
		var $tree = $('#dir_tree');
		var $table = $('#ja_table');
		var $search = $('#searchInp');
		var dt = null;
		var dirType = 2;
		var currentNode = null;
		
		DirTree.init($tree, dirType, function(treeNode){
			currentNode = treeNode;
			EditDir.setNode(currentNode);
			reload();
		}, function(treeNode){
			currentNode = treeNode;
			EditDir.setNode(currentNode);
		});
		
		EditDir.init({
			el: $tree,
			dirType: dirType,
			DirTree: DirTree,
			addSelector: ".btn.add-dir",
			editSelector: ".btn.edit-dir",
			deleteSelector: ".btn.delete-dir"
		});
		
		var initTable = function(){
			dt = $table.dataTable( {
				ajax: {
	        		url: basePath+"jagl/templetes/list",
	        		data: function(data){
	        			data.dirId = currentNode?currentNode.id:-1;
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
	                  { title: "名称", data: "name"},
	                  { title: "创建时间", data: "insertTime"},
	                  { title: "更新时间", data: "modifyTime"},
	    	          { title: "操作", data: "id", render: function(data, type, full){
	    	        	  return '<a href="javascript:;" class="btn red delete" data-id="'+data+'"> 删除 <i class="fa fa-remove"></i></a>'+
	    	        	  '<a href="'+basePath+'jagl/templetes/edit/'+full.id+'" class="btn blue edit"> 编辑 <i class="fa fa-edit"></i></a>'+
	    	        	  '<a href="javascript:;" class="btn yellow look"><i class="fa fa-search-plus"></i> 预览</a>';
	    	          }}
	    	      ]
		      } );
			$tableWrapper = $table.closest('.dataTables_wrapper');
			$tableWrapper.find('.dataTables_filter').appendTo($search);
			$tableWrapper.on("click", ".btn.delete", function(){
				var id = $(this).data("id");
				App.confirm({
					title: '提示信息',
					msg: '您确定要删除此教案模板吗？',
					okFn: function(){
						App.getJSON(basePath+'jagl/templetes/delete', {id: id}, function(){
							reload();
						});
					},
					cancerFn: function(){}
				});
			});
		};
		$('.btn.add').on("click", function(){
			location.href = basePath+"jagl/templetes/add?dirId="+(currentNode?currentNode.id:-1);
		});
		
		var reload = function(){
			if(dt){
				dt.api().ajax.reload(null, false);
			} else {
				initTable();
			}
		};
	});
	
});