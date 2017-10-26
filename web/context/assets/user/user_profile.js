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
		function load(){
			App.post(App.remoteUrlPre + "getStuInfo", null,function(result){
				var njList = result.nj_list;
				var schoolList = result.school_list;
				var info = result.info;
				var _form = $(".form-horizontal");
				if(njList!=null){
					var njSelect = _form.find("select[name='nj']");
					$.each(njList, function(index, value, array) {
						var selected = value.id == info.njId ? "selected" :"" ;
						njSelect.append('<option value="'+ value.id +'" '+ selected +'>'+ value.name +'</option>');
					});
				}
				if(schoolList!=null){
					var njSelect = _form.find("select[name='school']");
					$.each(schoolList, function(index, value, array) {
						var selected = value.id == info.mbxxId ? "selected" :"" ;
						njSelect.append('<option value="'+ value.id +'" '+ selected +'>'+ value.name +'</option>');
					});
				}
				_form.find("input[name='xs_name']").val(info.studentName);					
				_form.find("input[name='jz_name']").val(info.parentName);
				_form.find("input[name='phone']").val(info.contact);
				if(info.zhcj != -1){
					_form.find("input[name='zhcj']").val(info.zhcj);
				}
				
			});
		}
		load();
		
		
		$(".mui-icon").click(function(){
			history.back();
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
                school: {
                	isIntGtZero: true
                },
                nj: {
                	isIntGtZero: true
                },
                zhcj: {
                	isNumber:true
                }
            },
            messages: {
            	xs_name: {
                    required: "学生姓名不能为空."
                },
                phone: {
                	required: "手机号码不能为空."
                },
                school: {
                	isIntGtZero: "请选择具体学校."
                },
                nj: {
                	isIntGtZero: "请选择所属年级."
                },
                zhcj: {
                	isNumber:"请填写数值。"
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