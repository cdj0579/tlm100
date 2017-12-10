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
	require(['domready!', 'app', 'select2'], function (doc, App){
		var $form = $('form');
		
		$form.find('select[name="bj"]').select2({
    		placeholder: "请选择",
    		value: -1,
    		allowClear: true
    	});
		$form.find('select[name="status"]').select2({
    		placeholder: "请选择",
    		allowClear: true
    	});
		
		$(".select2, .select2-multiple").select2({
            width: null
        });

        $(".select2-allow-clear").select2({
            allowClear: true,
            width: null
        });
        
        $('.btn.on-modal').on('click', function(){
        	App.ajaxModal({
				id: "edit",
				scroll: true,
				width: "900",
				remote: basePath+"assets/home/modal.html",
				callback: function(modal, args){
					$(".select2, .select2-multiple").select2({
			            width: null
			        });

			        $(".select2-allow-clear").select2({
			            allowClear: true,
			            width: null
			        });
				}
			});
        });
	});
	
});