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
			"RememberBaseInfo": "assets/base/cookie/rememberBaseInfo",
			'CachesSelectedContents': 'assets/jagl/cachesSelectedContents',
			'SaveUtils': 'assets/jagl/saveUtils',
			'changeTemplete': 'assets/jagl/templetes/changeTemplete',
			"selectZsd": "assets/zs/zsd/selectZsd/selectZsd"
		},shim: {
			
		}
	});
	require(['app','layout','demo']);
	require(['domready!', 'app', 'SaveUtils', 'selectZsd', 'ckeditor-ckfinder', 'validate.additional', "ztree.select", "select3"], function (doc, App, SaveUtils,SelectZsd){
		var $form = $('.portlet-body form');
		var isTemplete = $form.find('input[name="isTemplete"]').val();
		var isSaveTemplete = false;
		var dirType = (isTemplete=="true" || isTemplete==true)?2:1;
		
		var keepOnline = function(){
			App.ajaxJSON({
			    url : basePath+"system/keepOnline",
			    data : {},
			    type : 'get',
			    showBlockUI: false,
			    dataType: 'json',
			    success: function(result){
			    	setTimeout(keepOnline, 1000*60*3);
			    }
			});
		};
		
		keepOnline();
		
		var IDMark_Switch = "_switch",
		IDMark_Icon = "_ico",
		IDMark_Span = "_span",
		IDMark_Input = "_input",
		IDMark_Check = "_check",
		IDMark_Edit = "_edit",
		IDMark_Remove = "_remove",
		IDMark_Ul = "_ul",
		IDMark_A = "_a";
		
		$form.find('input[name="dirId"]').ztreeSelect({
			url: basePath+"jagl/dir/tree?type="+dirType,
			treeView: {
				dblClickExpand: false,
				showLine: true,
				showIcon: true,
				selectedMulti: false
			},
			autoLoad: true,
			dataParam: function(result){
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
				return rows;
			},
			valueParam: "id"
		});
		
		$form.find('select[name="isOriginal"]').select2({
    		placeholder: "请选择"
    	}).on("change", function(){
    		var isOriginal = $(this).val();
    		if(isOriginal == 1){
    			$form.hideOrShowFormItem(['yyfs'], true);
    		} else {
    			$form.hideOrShowFormItem(['yyfs'], false);
    			$form.find('input[name="yyfs"]').val(1);
    		}
    	});
		$form.find('select[name="isShare"]').select2({
    		placeholder: "请选择"
    	});
		
		SaveUtils.init($form);
		$form.validateB({
            submitHandler: function () {
            	if(isSaveTemplete==true){
            		SaveUtils.saveTemplete(null, {isAdd: true});
            	} else {
            		if(isTemplete == true || isTemplete == 'true'){
            			SaveUtils.saveTemplete();
            		} else {
            			SaveUtils.save();
            		}
            	}
            }
        });
		$('.portlet-title .btn.save').on("click",function(){
			isSaveTemplete = false;
			$form.submit();
		});
		$('.portlet-title .btn.save-templete').on("click",function(){
			isSaveTemplete = true;
			$form.submit();
		});
		$('.portlet-body form .btn.input-zsContent').on("click",function(){
			SelectZsd.init({
				type: "zsdContent",
				onSelected: function(selected){
					var ids = [];
					$.each(selected, function(){
						ids.push(this.id);
					});
					App.getJSON(basePath+"zs/content/byids", {ids: ids.join(","),type: "zsd"}, function(result){
						var htmls = [];
						$.each(result.list, function(){
							htmls.push(this.content);
						});
						editor.insertHtml(htmls.join('<br/>'));
					});
				}
			});
		});
		$('.portlet-body form .btn.input-ztContent').on("click",function(){
			SelectZsd.init({
				type: "ztContent",
				onSelected: function(selected){
					var ids = [];
					$.each(selected, function(){
						ids.push(this.id);
					});
					App.getJSON(basePath+"zs/content/byids", {ids: ids.join(","),type: "zt"}, function(result){
						var htmls = [];
						$.each(result.list, function(){
							htmls.push(this.content);
						});
						editor.insertHtml(htmls.join('<br/>'));
					});
				}
			});
		});
		$('.portlet-body form .btn.input-xt').on("click",function(){
			SelectZsd.init({
				type: "xt",
				onSelected: function(selected){
					var ids = [];
					$.each(selected, function(){
						ids.push(this.id);
					});
					App.getJSON(basePath+"zs/content/byids", {ids: ids.join(","),type: "xt"}, function(result){
						var htmls = [];
						$.each(result.list, function(){
							htmls.push(this.content);
							htmls.push(this.answer);
						});
						editor.insertHtml(htmls.join('<br/>'));
					});
				}
			});
		});
		$('.portlet-body form .btn.change-templete').on("click",function(){
			App.ajaxModal({
				id: "changeTemplete",
				scroll: true,
				width: "600",
				required: ["changeTemplete"],
				remote: basePath+"assets/jagl/templetes/changeTemplete.html",
				callback: function(modal, args){
					args[0].init(modal, function(templeteHtml){
						editor.setData(templeteHtml);
					});
				}
			});
		});
		$('#editor1').ckeditor({
	        customConfig : basePath+'assets/jagl/ckConfig.js',
	        extraPlugins: 'autogrow,jme',
			autoGrow_minHeight: 200,
			autoGrow_maxHeight: 500,
			removePlugins: 'resize',
            allowedContent: true
	    });
		var editor = null;
		CKEDITOR.on( 'instanceReady', function( ev ) {
			editor = CKEDITOR.instances.editor1;
			
			/*editor.on( 'reload', function() {
				//editor.document.getBody().addClass('document-editor');
				//console.log('sssss');\
				console.log('sssss');
			});*/
			
			editor.on('maximize', function(){
				if(arguments[0].data == 1){
					$form.find('.btn-groups.toolbar').css({
						position: "fixed",
						top: "4px",
						right: "50px",
						width: "400px",
						zIndex: 12000
					});
					$('#cke_editor1 .cke_inner').css({
						zIndex: 10045
					});
				} else {
					$form.find('.btn-groups.toolbar').css({
						position: "",
						top: "",
						right: "",
						width: "",
						zIndex: 0
					});
				}
			});
			CKFinder.setupCKEditor(editor, basePath+'assets/global/plugins/ckfinder/');
			//editor.document.getBody().addClass('document-editor');
			$('.cke_button_icon.cke_button__jme_icon').css({
			    backgroundSize: "auto"
			});
			
		});
	});
	
});