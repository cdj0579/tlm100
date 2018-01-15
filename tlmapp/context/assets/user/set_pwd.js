//var basePath = "./";
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
		
		$(".mui-icon").click(function(){
			location.href= App.remoteUrlPre +"grzy";
		});
		
		
		var handleLogin = function(){
			
			$('.setPwd-form').validate({
	            errorElement: 'span', //default input error message container
	            errorClass: 'help-block', // default input error message class
	            focusInvalid: true, // do not focus the last invalid input
	            rules: {
	                old_password: {
	                    required: true
	                },
	                password: {
	                    required: true
	                },
	                rpassword: {
	                    equalTo: "#new_password"
	                }
	            },
	            messages: {
	                username: {
	                    required: "原密码不能为空."
	                },
	                password: {
	                    required: "请输入新密码."
	                }
	            },
	            invalidHandler: function(event, validator) { //display error alert on form submit   
	                $('.alert-danger', $('.setPwd-form')).show();
	            },
	            highlight: function(element) { // hightlight error inputs
	                $(element)
	                    .closest('.form-group').addClass('has-error'); // set error class to the control group
	            },
	            success: function(label) {
	                label.closest('.form-group').removeClass('has-error');
	                label.remove();
	            },
	            errorPlacement: function(error, element) {
	                error.insertAfter(element.closest('.input-icon'));
	            },
	            submitHandler: function(form) {
	                form.submit(); // form validation success, call ajax form submit
	            }
	        });
			var mySubmit=function(){
				$('.setPwd-form .form-group').removeClass('has-error');
				var form =  $('.setPwd-form');
				if (form.validate().form()) {
					form.ajaxSubmit({
	            		url: App.remoteUrlPre + "updatePwd",
	            		type: "POST",
	            		dataType: "json",
	            		success: function(result){
	            			form[0].reset();
	            			App.handlerAjaxJson(result,"修改密码成功！");
	            		}
	            	});
	             }
	             return false;
			}
	        $('.setPwd-form input').keypress(function(e) {
	            if (e.which == 13) {
	                return mySubmit();
	            }
	        });
	        $('.setPwd-form button[type="submit"]').click(function(){
	        	 return mySubmit();
	        });
	    }
		handleLogin();
	});
	

});