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
			"selectZsd": "assets/zs/zsd/selectZsd/selectZsd"
		},shim: {
		}
	});
	
	require(['app','layout','demo']);
	require(['domready!', 'app', "selectZsd", 'datatables.bt', "select3", "ztree.select"], function (doc, App, SelectZsd){
		var $form = $('form');
		var $zsdDisplay = $form.find('span.display-zsd');
		var $ztDisplay = $form.find('span.display-zt');
		
		var showSelectZsd = function(){
			var params = {};
			var zsdes = $zsdDisplay.data("datas") || [];
			SelectZsd.init({
				onSelected: function(selected){
					$zsdDisplay.html(selected.length);
					$zsdDisplay.data("datas", selected);
				},
				selected: zsdes
			});
		};
		
		var showSelectZt = function(){
			var params = {};
			var ztes = $ztDisplay.data("datas") || [];
			SelectZsd.init({
				type: "zt",
				onSelected: function(selected){
					$ztDisplay.html(selected.length);
					$ztDisplay.data("datas", selected);
				},
				selected: ztes
			});
		};
		
		var getTypes = function(){
			var types = [];
        	$form.find('input[name="type"]:checked').each(function(){
        		types.push($(this).val());
        	});
        	return types;
		};
		
		var hasType = function(type){
			var types = getTypes();
			return $.inArray(type, types) != -1;
		};
		
		$form.find('input[name="type"]').on("click", function(){
			if(hasType("2")){
				$("#select_zt_item").removeClass("hide");
			} else {
				$("#select_zt_item").addClass("hide");
			}
			if(hasType("0") || hasType("1")){
				$("#select_zsd_item").removeClass("hide");
			} else {
				$("#select_zsd_item").addClass("hide");
			}
		});
		
		$form.validateB({
            submitHandler: function () {
            	var types = getTypes();
            	var zsdes = $zsdDisplay.data("datas");
            	var ztes = $ztDisplay.data("datas");
            	var list = [];
            	if(types.length > 0){
            		for(var i=0;i<types.length;i++){
            			var type = types[i];
            			if(type == "2"){
        					if(ztes && ztes.length > 0){
        						for(var j=0;j<ztes.length;j++){
        							list.push({
        								type: type,
        								sid: ztes[j].id
        							});
        						}
        					} else {
        						App.getAlert().error("请先选择要发布任务的专题!", "提示");
        	            		return;
        					}
        				}else if(type == "0" || type == "1"){
        					if(zsdes && zsdes.length > 0){
        						for(var j=0;j<zsdes.length;j++){
        							list.push({
        								type: type,
        								sid: zsdes[j].id
        							});
        						}
        					} else {
        						App.getAlert().error("请先选择要发布任务的知识点!", "提示");
        	            		return;
        					}
        				}
            		}
            	} else {
            		App.getAlert().error("请先选择要发布的任务类型!", "提示");
            		return;
            	}
            	$form.ajaxSubmit({
            		url: basePath+"jfrw/fbrw",
            		type: "POST",
            		dataType: "json",
            		data: {
            			list: window.JSON.stringify(list)
            		},
            		success: function(result){
            			App.handlerAjaxJson(result, function(){
            				App.confirm({
            					title: '提示信息',
            					msg: '任务发布成功，需要继续发布任务吗？<br/>点击取消进入任务列表页面！',
            					okFn: function(){
            						location.reload();
            					},
            					cancerFn: function(){
            						location.href = basePath+"jfrw/view";
            					}
            				});
            			});
            		}
            	});
            }
        });
		
		$form.find('.btn.select-zt').on('click', function(){
			showSelectZt();
		});
		
		$form.find('.btn.select-zsd').on('click', function(){
			showSelectZsd();
		});
		
		$('.btn.save').on("click",function(){
			$form.submit();
		});
	});
	
});