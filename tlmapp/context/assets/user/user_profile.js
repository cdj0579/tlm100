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
		var isSave = false;
		$(".mui-icon").click(function(){
			if(isFirst == 'true' ||isFirst == true){
				if( isSave ){
					location.href= App.remoteUrlPre +"index";
				}else{
					var alert = App.getAlert({positionClass:"toast-top-center"});
					alert.warning("首次登陆请先保存完善个人信息", "提示");
				}
			}else{
			location.href= App.remoteUrlPre +"grzy";
			}
		});
		//判断大于0
		jQuery.validator.addMethod("isMobile", function(value, element) {    
	      var length = value.length;    
	      return this.optional(element) || (length == 11 && /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/.test(value));    
	    }, "请正确填写您的手机号码!");
		
		// 判断整数value是否大于0
	    jQuery.validator.addMethod("isIntGtZero", function(value, element) { 
	         value=parseInt(value);      
	         return this.optional(element) || value>0;       
	    }, "请填写大于0的整数");
	    
	    // 判断数值类型，包括整数和浮点数 值大于0
	    jQuery.validator.addMethod("isNumber", function(value, element) {       
	         return this.optional(element) || /^\d+$/.test(value) || /^\d+(\.\d+)?$/.test(value);       
	    }, "匹配数值类型，包括正整数和正浮点数"); 
		
		$(".form-horizontal").validate({
            rules: {
            	xs_name: {
                    required: true
                },
                phone: {
                    required: true,
                    isMobile: true
                },
                dqId: {
                	isIntGtZero: true
                },
                nj: {
                	isIntGtZero: true
                }
            },
            messages: {
            	xs_name: {
                    required: "学生姓名不能为空."
                },
                phone: {
                	required: "手机号码不能为空."
                },
                dqId: {
                	isIntGtZero: "请选择所属区域."
                },
                nj: {
                	isIntGtZero: "请选择所属年级."
                }
            }
		});
		
		$('.form-horizontal input').keypress(function(e) {
            if (e.which == 13) {
                return mySubmit();
            }
        });
        $('.form-horizontal button[type="submit"]').click(function(){
        	 return mySubmit();
        });
        function mySubmit(){
        	var form = $('.form-horizontal');
      		if (form.validate().form()) {
      			form.ajaxSubmit({
            		url: App.remoteUrlPre + "saveStuInfo",
            		type: "POST",
            		dataType: "json",
            		success: function(result){
            			App.handlerAjaxJson(result,function(){
            				isSave = true;
            				var alert = App.getAlert({positionClass:"toast-top-center"});
        					alert.success("保存信息成功！", "提示");
            			});
            		}
            	});
            }
			return false;
        }
	});
});