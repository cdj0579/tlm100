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
			"RememberBaseInfo": "assets/base/cookie/rememberBaseInfo"
		},shim: {
		}
	});
	
	require(['app','layout','demo']);
	require(['domready!', 'app', 'RememberBaseInfo', "select3", "ztree.select"], function (doc, App, RememberBaseInfo){
		var $form = $('form');
		
		var o = RememberBaseInfo.load();
		var _data = $.extend({}, o);
		
		$form.find('select[name="type"]').select2({
    		placeholder: "请选择"
    	}).on("change", function(){
    		var type = $(this).val();
    		$form.hideOrShowFormItem(['dqId', 'xq', 'qzqm'], false);
    		if("2" == type){
    			$form.hideOrShowFormItem(['dqId', 'xq'], true);
    		} else if("3" == type){
    			$form.hideOrShowFormItem(['qzqm'], true);
    		}
    	});
		$form.find('select[name="dqId"]').select3({
    		placeholder: "请选择",
    		autoLoad: true,
    		value: _data.dqId || null,
    		tableName: "xzqh",
    		idField: "code",
			nameField: "name",
			typeField: "pid",
			typeVelue: "330500"
    	});
		$form.find('select[name="kmId"]').select3({
    		placeholder: "请选择",
    		autoLoad: true,
    		tableName: "km_dic",
    		value: _data.kmId || null,
    		idField: "id",
			nameField: "name"
    	});
		$form.find('select[name="njId"]').select3({
    		placeholder: "请选择",
    		autoLoad: true,
    		tableName: "nj_dic",
    		value: _data.njId || null,
    		idField: "id",
			nameField: "name"
    	});
		$form.find('select[name="xq"]').select2({
    		placeholder: "请选择"
    	});
		$form.find('select[name="qzqm"]').select2({
    		placeholder: "请选择"
    	});
		$form.find('select[name="ndId"]').select3({
    		placeholder: "请选择",
    		autoLoad: true,
    		tableName: "nd_dic",
    		idField: "id",
			nameField: "name",
			typeField: "dic_type",
			typeVelue: "zsdnd"
    	});
		
		RememberBaseInfo.init($form);
		$form.loadForm(_data);
		if(_data.xq > 0)$form.find('select[name="xq"]').trigger('change');
		if(_data.qzqm > 0)$form.find('select[name="qzqm"]').trigger('change');
		
		$form.validateB({
            submitHandler: function () {
            	$form.ajaxSubmit({
            		url: basePath+"jfrw/fbrw",
            		type: "POST",
            		dataType: "json",
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
		$('.btn.save').on("click",function(){
			$form.submit();
		});
	});
	
});