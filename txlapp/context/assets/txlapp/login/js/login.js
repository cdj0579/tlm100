//var basePath = "../../";
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
	
	require(['domready!', 'app', 'validate.additional'], function (doc, App){
		
		var handleLogin = function(){
			$('.form-horizontal').validate({
	            errorElement: 'span', 
	            errorClass: 'help-block',
	            focusInvalid: true, 
	            rules: {
	                username: {
	                    required: true
	                },
	                password: {
	                    required: true
	                }
	            },
	            messages: {
	                username: {
	                    required: "用户名不能为空！"
	                },
	                password: {
	                    required: "密码不能为空！"
	                }
	            }
	        });
			var mySubmit=function(){
				if ($('.form-horizontal').validate().form()) {
	             	$('.form-horizontal').attr("action", App.remoteUrlPre+"login").submit(); //form validation success, call ajax form submit
	             }
	             return false;
			}
	        $('.form-horizontal input').keypress(function(e) {
	            if (e.which == 13) {
	                return mySubmit();
	            }
	        });
	        $('.form-horizontal button[type="submit"]').click(function(){
	        	 return mySubmit();
	        });
	    }
	    handleLogin();
	});
	
});