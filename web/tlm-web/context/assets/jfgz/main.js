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
			
		},shim: {
		}
	});
	
	require(['app','layout','demo']);
	require(['domready!', 'app', "validate.additional"], function (doc, App){
		var $form = $('form');
		var $view = $('.list-group.jfgz-review');
		var initValidHandler = function(){
	    	validForm = $form.validateB({
	            submitHandler: function () {
	            	$form.ajaxSubmit({
	            		url: basePath+"zs/jfgz/save",
	            		type: "POST",
	            		dataType: "json",
	            		success: function(result){
	            			App.handlerAjaxJson(result, function(){
	            				App.getAlert().info("保存成功！", "提示");
	            			});
	            		}
	            	});
	            }
	        });
		};
		
		var initSaveHandler = function(){
			$form.find('.btn.save').on("click", function(){
				$form.submit();
			});
			
			$form.find('.btn.review').on("click", function(){
				showView();
			});
		};
		
		var showView = function(){
			if($form.valid()){
				$view.empty();
				var zzsl = $form.find('input[name="zzsl"]').val();
				var jfsx = $form.find('input[name="jfsx"]').val();
				App.post(basePath+"zs/jfgz/review", {jfsx: jfsx, zzsl: zzsl}, function(result){
					if(result.list){
						for(var i=0;i<result.list.length;i++){
							var d = result.list[i];
							$view.append($('<li class="list-group-item"> 收藏次数： '+d.count+'，可获取积分：'+d.jf+' </li>'));
						}
					}
				});
			}
		};
		
		initValidHandler();
		initSaveHandler();
		
		App.getJSON(basePath+"zs/jfgz/load", {}, function(result){
			$form.loadForm(result.data);
			$form.find('.btn.save').removeClass("disabled");
		});
	});
});