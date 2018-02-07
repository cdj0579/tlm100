//var basePath = "./";
require.config({
	baseUrl: basePath
});
define(['assets/common/config'], function(config) {
	require.config(config.require);
	
	require.config({
		paths: {
			'bootstrap-datetimepicker.zh-CN':'assets/global/plugins/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN'
		},shim: {
			'bootstrap-datetimepicker':['app'],
			'bootstrap-datetimepicker.zh-CN':['bootstrap-datetimepicker']
		}
	});
	
	require(['domready!', 'app', 'validate.additional','bootstrap-datetimepicker','bootstrap-datetimepicker.zh-CN'], function (doc, App){
		
		$schoolSelect = $("select[name='mbxx']");
		_schoolOptions = $schoolSelect.clone() ;
		
		var dqchangeEvent = function(){
			$("select[name='dqId']").change(function(){
				var v = $(this).val();
				var option = _schoolOptions.clone();
				if(v == "-1"){
					$schoolSelect.empty();
					$schoolSelect.append(option.find("option"));
				}else{
					$schoolSelect.find("option[value!='-1']").remove();
					$schoolSelect.append(option.find("option[value*='|"+v+"']"));
				}
				$schoolSelect.val("-1");
			})
		}
		dqchangeEvent();
		
		$(".mui-icon").click(function(){
			history.back();
			//location.href= App.remoteUrlPre +"index";
		});
		
		$(".form_date").datetimepicker({
		 	language: "zh-CN",
	       	format: 'yyyy-mm-dd',
	        pickerPosition: "bottom-left",
	        initialDate: new Date(),
	       	todayBtn:  1,
			autoclose: 1,
			todayHighlight: 1,
			startView: 2,
			minView: 2,
			forceParse: true
		 });
		
		var date = new Date();
	    var mon = date.getMonth() + 1;
	    var day = date.getDate();
	    var mydate = date.getFullYear() + "-" + (mon < 10 ? "0" + mon : mon) + "-" + (day < 10 ? "0" + day : day);
	    $(".form_date input").val(mydate);
	    
	    
		// 判断整数value是否大于0
	    jQuery.validator.addMethod("isIntGtZero", function(value, element) { 
	         value=parseInt(value);      
	         return this.optional(element) || value>0;       
	    }, "请填写大于0的整数");
	    
		var handleLogin = function(){
			var _form = $(".form-horizontal");
			_form.validate({
	            errorElement: 'span',
	            errorClass: 'help-block',
	            focusInvalid: false,
	            rules: {
	                nj: {
	                	required: true,
	                	isIntGtZero: true
	                },
	                dateTime:{
	                	required: true
	                },
	                yw_cj: {
	                	required: true,
	                	isIntGtZero: true
	                },
	                yw_mf: {
	                	required: true,
	                    isIntGtZero: true
	                },
	                kx_cj: {
	                	required: true,
	                    isIntGtZero: true
	                },
	                kx_mf: {
	                	required: true,
	                    isIntGtZero: true
	                },
	                yy_cj: {
	                	required: true,
	                    isIntGtZero: true
	                },
	                yy_mf: {
	                	required: true,
	                    isIntGtZero: true
	                },
	                ysx_cj: {
	                	required: true,
	                    isIntGtZero: true
	                },
	                sx_mf: {
	                	required: true,
	                    isIntGtZero: true
	                },
	                sh_cj: {
	                	required: true,
	                    isIntGtZero: true
	                },
	                sh_mf: {
	                	required: true,
	                    isIntGtZero: true
	                },
	                mbxx:{
	                	required: true,
	                    isIntGtZero: true
	                }
	            },
	            messages: {
	                nj: {
	                    required: "请选择年级."
	                },
	                dateTime: {
	                    required: "请选择考试时间."
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
	            			App.handlerAjaxJson(result,function(){
	            				location.href = App.remoteUrlPre +"srfx";
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