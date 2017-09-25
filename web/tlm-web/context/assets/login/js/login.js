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
	
	var handleContentHeight = function() {
        var height;

        $('.content').css('min-height', 0);
        if ($('body').height() < App.getViewPort().height) {            
            height = (App.getViewPort().height - $('.login-form').outerHeight())/2;

            $('.content').css('min-height', App.getViewPort().height);
            $('.login-form').css('margin-top', height+100);
        }
    };
    
	require(['domready!', 'app', 'additional-methods.min', 'js.cookie'], function (doc, app, am, Cookies){
		var $username = $('input[name="username"]');
		var $password = $('input[name="password"]');
		var $remember = $('input[name="remember"]');
		$username.focus();
		
		var setCookie = function(){
			if(!Cookies) return;
			Cookies.remove('loginname');
			if($remember.attr("checked")){
				Cookies.set('loginname', $username.val());
			}
		};
		var getCookie = function(){
			if(!Cookies) return;
			var loginname = Cookies.get('loginname');
			if(loginname){
				$username.val(loginname);
				$password.focus();
				$remember.attr("checked", true);
				$remember.uniform();
			}
		};
		handleContentHeight(); // handles content height 
        App.addResizeHandler(handleContentHeight);
		getCookie();
		var handleLogin = function() {

	        $('.login-form').validate({
	            errorElement: 'span', //default input error message container
	            errorClass: 'help-block', // default input error message class
	            focusInvalid: false, // do not focus the last invalid input
	            rules: {
	                username: {
	                    required: true
	                },
	                password: {
	                    required: true
	                },
	                remember: {
	                    required: false
	                }
	            },

	            messages: {
	                username: {
	                    required: "用户名不能为空."
	                },
	                password: {
	                    required: "密码不能为空."
	                }
	            },

	            invalidHandler: function(event, validator) { //display error alert on form submit   
	                $('.alert-danger', $('.login-form')).show();
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
	            	setCookie();
	                form.submit(); // form validation success, call ajax form submit
	            }
	        });

	        $('.login-form input').keypress(function(e) {
	            if (e.which == 13) {
	                if ($('.login-form').validate().form()) {
	                	setCookie();
	                    $('.login-form').submit(); //form validation success, call ajax form submit
	                }
	                return false;
	            }
	        });
	    }

	    var handleForgetPassword = function() {
	        $('.forget-form').validate({
	            errorElement: 'span', //default input error message container
	            errorClass: 'help-block', // default input error message class
	            focusInvalid: false, // do not focus the last invalid input
	            ignore: "",
	            rules: {
	                email: {
	                    required: true,
	                    email: true
	                }
	            },

	            messages: {
	                email: {
	                    required: "Email is required."
	                }
	            },

	            invalidHandler: function(event, validator) { //display error alert on form submit   

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
	                form.submit();
	            }
	        });

	        $('.forget-form input').keypress(function(e) {
	            if (e.which == 13) {
	                if ($('.forget-form').validate().form()) {
	                    $('.forget-form').submit();
	                }
	                return false;
	            }
	        });

	        jQuery('#forget-password').click(function() {
	            jQuery('.login-form').hide();
	            jQuery('.forget-form').show();
	        });

	        jQuery('#back-btn').click(function() {
	            jQuery('.login-form').show();
	            jQuery('.forget-form').hide();
	        });

	    }

	    var handleRegister = function() {

	        function format(state) {
	            if (!state.id) { return state.text; }
	            var $state = $(
	             '<span><img src="../assets/global/img/flags/' + state.element.value.toLowerCase() + '.png" class="img-flag" /> ' + state.text + '</span>'
	            );
	            
	            return $state;
	        }

	        if (jQuery().select2 && $('#country_list').size() > 0) {
	            $("#country_list").select2({
		            placeholder: '<i class="fa fa-map-marker"></i>&nbsp;Select a Country',
		            templateResult: format,
	                templateSelection: format,
	                width: 'auto', 
		            escapeMarkup: function(m) {
		                return m;
		            }
		        });


		        $('#country_list').change(function() {
		            $('.register-form').validate().element($(this)); //revalidate the chosen dropdown value and show error or success message for the input
		        });
	    	}

	        $('.register-form').validate({
	            errorElement: 'span', //default input error message container
	            errorClass: 'help-block', // default input error message class
	            focusInvalid: false, // do not focus the last invalid input
	            ignore: "",
	            rules: {
	                name: {
	                    required: true
	                },
	                kmId: {
	                    required: true
	                },
	                skdz: {
	                    required: true
	                },
	                username: {
	                    required: true
	                },
	                password: {
	                    required: true
	                },
	                rpassword: {
	                    equalTo: "#register_password"
	                },
	                tnc: {
	                    required: true
	                }
	            },
	            messages: { // custom messages for radio buttons and checkboxes
	                tnc: {
	                    required: "注册账号必须同意本协议."
	                },
	                name: {
	                    required: "姓名不能为空."
	                },
	                kmId: {
	                    required: "请选择学科."
	                },
	                skdz: {
	                    required: "授课地址不能为空."
	                },
	                username: {
	                    required: "用户名不能为空."
	                },
	                password: {
	                    required: "密码不能为空."
	                },
	                rpassword: {
	                    equalTo: "输入的密码不一致."
	                }
	            },

	            invalidHandler: function(event, validator) { //display error alert on form submit   

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
	                if (element.attr("name") == "tnc") { // insert checkbox errors after the container                  
	                    error.insertAfter($('#register_tnc_error'));
	                } else if (element.closest('.input-icon').size() === 1) {
	                    error.insertAfter(element.closest('.input-icon'));
	                } else {
	                    error.insertAfter(element);
	                }
	            },

	            submitHandler: function(form) {
	                form.submit();
	            }
	        });

	        $('.register-form input').keypress(function(e) {
	            if (e.which == 13) {
	                if ($('.register-form').validate().form()) {
	                    $('.register-form').submit();
	                }
	                return false;
	            }
	        });

	        jQuery('#register-btn').click(function() {
	            jQuery('.login-form').hide();
	            jQuery('.register-form').show();
	        });

	        jQuery('#register-back-btn').click(function() {
	            jQuery('.login-form').show();
	            jQuery('.register-form').hide();
	        });
	    }
	    
	    handleLogin();
        handleForgetPassword();
        handleRegister();
	});
	
});