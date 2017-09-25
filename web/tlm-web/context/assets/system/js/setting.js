define(["validate.additional"], function(){
	var $form = null;
	
	var initValidHandler = function(modal){
    	validForm = $form.validateB({
            submitHandler: function () {
            	$form.ajaxSubmit({
            		url: "venus4aconfig.do?action=saveConfig",
            		type: "POST",
            		dataType: "json",
            		success: function(result){
            			App.handlerAjaxJson(result, function(){
            				modal.hide();
            			});
            		}
            	});
            }
        });
	};
	
	var initSaveHandler = function(modal){
		modal.$element.find('.btn.save').on("click",function(){
			$form.submit();
		});
	};
	
	var toggleItems = function(show){
		var items = ["wsdlUrl", "loginUrl"];
		for(var i=0;i<items.length;i++){
			var $item = $form.find('input[name="'+items[i]+'"]').closest('.form-group');
			if(show){
				$item.removeClass('hide');
			} else {
				$item.addClass('hide');
			}
		}
	};
	
	var loadData = function($ssoEnableSwitch){
		App.getJSON("venus4aconfig.do?action=loadConfig", {}, function(result){
			$form.loadForm(result.data);
			if(result.data.ssoEnable == true || result.data.ssoEnable == "true"){
				$ssoEnableSwitch.attr("checked", true);
				toggleItems(true);
			}
			$ssoEnableSwitch.bootstrapSwitch({
				onSwitchChange: function(){
					toggleItems($ssoEnableSwitch.prop("checked"));
				}
			});
		});
	};
	
	return {
		init: function(modal){
			$form = modal.$element.find('form');
			var $ssoEnableSwitch = $form.find('input[name="ssoEnable"]');
			
			loadData($ssoEnableSwitch);
			
			initValidHandler(modal);
			initSaveHandler(modal);
		}
	};
});