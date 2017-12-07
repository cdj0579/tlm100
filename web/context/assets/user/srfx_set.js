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
		
		$(".mui-icon").click(function(){
			history.back();
		});
		
		var pageLoad = function(){
			App.post(App.remoteUrlPre + "getStuInfo", null,function(result){
				var njList = result.nj_list;
				var mbxxList = result.mbxxList;
				var _form = $(".form-horizontal");
				if(njList!=null){
					var njSelect = _form.find("select[name='nj']");
					$.each(njList, function(index, value, array) {
						var selected = value.id == result.njId ? "selected" :"" ;
						njSelect.append('<option value="'+ value.id +'" '+ selected +'>'+ value.name +'</option>');
					});
				}
				if(mbxxList!=null){
					var njSelect = _form.find("select[name='nj']");
					$.each(njList, function(index, value, array) {
						var selected = value.id == result.mbxxId ? "selected" :"" ;
						njSelect.append('<option value="'+ value.id +'" '+ selected +'>'+ value.name +'</option>');
					});
				}
			});
		}
		pageLoad();
		
		// 判断整数value是否大于0
	    jQuery.validator.addMethod("isIntGtZero", function(value, element) { 
	         value=parseInt(value);      
	         return this.optional(element) || value>0;       
	    }, "请填写大于0的整数");
	    
		var handleLogin = function(){
			var _form = $(".form-horizontal");
s			_form.validate({
	            debug:true, //不提交默认submit;
	            errorElement: 'span',
	            errorClass: 'help-block',
	            focusInvalid: false,
	            rules: {
	                nj: {
	                    isIntGtZero: true
	                },
	                yw_cj: {
	                    isIntGtZero: true
	                },
	                yw_mf: {
	                    isIntGtZero: true
	                },
	                kx_cj: {
	                    isIntGtZero: true
	                },
	                kx_mf: {
	                    isIntGtZero: true
	                },
	                yy_cj: {
	                    isIntGtZero: true
	                },
	                yy_mf: {
	                    isIntGtZero: true
	                },
	                ysx_cj: {
	                    isIntGtZero: true
	                },
	                sx_mf: {
	                    isIntGtZero: true
	                },
	                sh_cj: {
	                    isIntGtZero: true
	                },
	                sh_mf: {
	                    isIntGtZero: true
	                },
	                mbxx:{
	                    isIntGtZero: true
	                }
	            },
	            messages: {
	                nj: {
	                    required: "请选择年级."
	                },
	                mbxx: {
	                    required: "请选择目标学校."
	                }
	            },
	            invalidHandler: function(event, validator) { //display error alert on form submit   
	                $('.alert-danger', _form ).show();
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
	            }
	        });
			var mySubmit=function(){
				_form.find('.form-group').removeClass('has-error');
				if (_form.validate().form()) {
					_form.ajaxSubmit({
	            		url: App.remoteUrlPre + "saveSrfxSet",
	            		type: "POST",
	            		dataType: "json",
	            		success: function(result){
	            			_form[0].reset();
	            			App.handlerAjaxJson(result,function(){
	            				location.href="./srfx_result.html";
	            			});
	            		}
	            	});
	             }
	             return false;
			}
	      	_form.find('input').keypress(function(e) {
	            if (e.which == 13) {
	                return mySubmit();
	            }
	        });
	        _form.find('button[type="submit"]').click(function(){
	        	 return mySubmit();
	        });
	    }
		handleLogin();
	});
	

});