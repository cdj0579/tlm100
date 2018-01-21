require.config({
	baseUrl: basePath
});
define(['assets/common/config'], function(config) {
	require.config(config.require);
	
	require.config({
		paths: {
		},shim: {
		}
	});

	require(['domready!', 'app', 'validate.additional',"select3"], function(doc, App){
		var $form = null;
		var addUrl =  basePath + "fx/saveLxrInfo"
		var $schoolSelect = null;
		var _schoolOptions = null;
		var initValidHandler = function(){
			var url = addUrl;
	    	$form.validateB({
	            submitHandler: function () {
	            	$form.ajaxSubmit({
	            		url: url,
	            		type: "POST",
	            		dataType: "json",
	            		success: function(result){
	            			App.handlerAjaxJson(result, function(){
	            				$form[0].reset();
		            			App.handlerAjaxJson(result,"保存信息成功！");
	            			});
	            		}
	            	});
	            	
	            }
	        });
		};
		
		var initSaveHandler = function(){
			$form.find('#submit-btn').on("click",function(){
				$form.submit();
				return false;
			});
		};
		var dqchangeEvent = function(){
			$("select[name='dqId']").change(function(){
				var v = $(this).val();
				var option = _schoolOptions.clone();
				if(v == "330500" || v == "-1"){
					$schoolSelect.empty();
					$schoolSelect.append(option.find("option"));
				}else{
					$schoolSelect.find("option[value!='-1']").remove();
					$schoolSelect.append(option.find("option[value*='-"+v+"']"));
				}
				$schoolSelect.val("-1");
			})
		}
		var init = function(){
			$form = $("#form_remark");
			initValidHandler();
			initSaveHandler();
			$schoolSelect = $("select[name='xxId']");
			_schoolOptions = $schoolSelect.clone() ;
			dqchangeEvent();
		};
		$(".mui-icon").click(function(){
			history.back();
		});
		init();
	});
});