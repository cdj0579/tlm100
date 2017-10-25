var basePath = "./";
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
		$("input[name='tnc']").click(function(){
			var _this = $(this);
			if( _this.prop('checked')){
				$("#register-submit-btn").attr("disabled",false); 
			}else{
				$("#register-submit-btn").attr("disabled",true); 
			}
		})
		//console.info($("input[name='tnc']"))
		var handleLogin = function(){
			
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
	                    required: "请输入用户名."
	                },
	                password: {
	                    required: "请输入密码."
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
	                form.submit(); // form validation success, call ajax form submit
	            }
	        });
			var mySubmit=function(){
				if ($('.login-form').validate().form()) {
	             	$('.login-form').attr("action", App.remoteUrlPre+"login").submit(); //form validation success, call ajax form submit
	             }
	             return false;
			}
	        $('.login-form input').keypress(function(e) {
	            if (e.which == 13) {
	                return mySubmit();
	            }
	        });
	        $('.login-form button[type="submit"]').click(function(){
	        	 return mySubmit();
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

	        $('#forget-password').click(function() {
	            $('.login-form').hide();
	            $('.forget-form').show();
	        });

	        $('#back-btn').click(function() {
	            $('.login-form').show();
	            $('.forget-form').hide();
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

	        if ($.select2 && $('#country_list').size() > 0) {
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

	                fullname: {
	                    required: true
	                },
	                email: {
	                    required: true,
	                    email: true
	                },
	                address: {
	                    required: true
	                },
	                city: {
	                    required: true
	                },
	                country: {
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
	                    required: "Please accept TNC first."
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
	      	var registerSubmit = function(){
	      		var form = $('.register-form');
	      		if (form.validate().form()) {
	      			form.ajaxSubmit({
	            		url: App.remoteUrlPre + "register",
	            		type: "POST",
	            		dataType: "json",
	            		success: function(result){
	            			App.handlerAjaxJson(result,function(){
	            				form[0].reset();
	            				location.href="./index.html";
	            			});
	            		}
	            	});
					//$('.register-form').attr("action",App.remoteUrlPre+"register").submit();
	            }
				return false;
	      	}

	        $('.register-form input').keypress(function(e) {
	            if (e.which == 13) {
	                return registerSubmit();
	            }
	        });
 			$('.register-form button[type="submit"]').click(function(){
	        	 return registerSubmit();
	        });
	        
	        $('#register-btn').click(function() {
	            var loginForm = $('.login-form')
	            loginForm.hide();
	            loginForm[0].reset();
	            $('.register-form').show();
	        });

	        $('#register-back-btn').click(function() {
	            $('.login-form').show();
	            var regForm = $('.register-form');
	            regForm.hide();
	            regForm[0].reset();
	        });
	    }
	    
	    handleLogin();
        handleForgetPassword();
        handleRegister();
	});
	
});