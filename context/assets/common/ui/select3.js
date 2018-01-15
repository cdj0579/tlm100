(function( factory ) {
	if ( typeof define === "function" && define.amd ) {
		define( ["validate.additional", "select2"], factory);
	} else {
		factory( jQuery );
	}
}(function(){
	$.fn.select2.defaults.set("theme", "bootstrap");
	var init = function(modal){
		var self = this;
		var form = $('#zdadd_form_id');
		$.proxy(initFormValidate, self)(form, modal);
    	$('.save-zd').click(function(){
    		form.submit();
		});
	};
	
	var initFormValidate = function(form, m){
		var self = this;
    	form.validateB({
    		messages: {
    			value: {
                    required: "请输入字典！"
                },
    		},
            submitHandler: function () {
            	$.proxy(submitZd, self)(form, m);
            }
        });
	};
	
	var submitZd = function(form, m){
		var self = this;
		form.ajaxSubmit({
    		url: self.addUrl,
    		type: "POST",
    		dataType: "json",
    		data: {tableName: self.tableName, nameField: self.nameField, idField: self.idField},
    		success: function(result){
    			if(result && (result.success == true || result.success == "true")){
    				if(self.openWin == true){
    					m.hide();
    				} else {
    					form.resetForm();
    					m.removeClass("open");
    				}
    				$.proxy(reload, self)(result.id);
    			} else {
    				msg = result && result.errors && result.errors.exception ?result.errors.exception:"提交失败！";
    				App.getAlert({positionClass:"toast-top-center"}).error(msg, "错误");
    			}
    		}
    	});
	};
	
	var createZdBox = function(){
		/*var zdForm = $(
			'<form class="zd-group" style="height: 28px;">'+
				'<div class="zdadd-wrapper control-group control">'+
					'<button class="btn green remove"><i class="fa fa-remove"></i></button>'+
					'<input type="text" name="value" class="value ignore-validate required" placeholder="请输入要添加的字典"/>'+
					'<button class="btn green save"><i class="fa fa-plus"></i></button>'+
				'</div>'+
			'</form>'
		);*/
		var zdForm = $(
			'<form class="zd-group form-group">'+
				'<div class="zdadd-wrapper input-group">'+
					'<span class="input-group-btn remove"><button class="btn green remove"><i class="fa fa-remove"></i></button></span>'+
					'<div class="input-icon right"><i class="fa"></i><input type="text" name="value" class="value form-control" validate="{required: true}" placeholder="请输入要添加的字典"/></div>'+
					'<span class="input-group-btn"><button class="btn green save"><i class="fa fa-plus"></i></button></span>'+
				'</div>'+
			'</form>'
		);
		var p = $(this).closest('div[class^="col-md-"]');
		if(p.length == 0){
			p = $(this);
		}
		zdForm.insertAfter(p);
		$.proxy(initFormValidate, this)(zdForm, $(".zdadd-wrapper", zdForm));
		$(".zdadd-wrapper .save", zdForm).click(function(){
			var w = $(this).closest(".zdadd-wrapper");
			if(w.hasClass("open")){
				zdForm.submit();
			} else {
				$(this).closest(".zdadd-wrapper").addClass("open");
				$(this).prevAll(".value").val("");
			}
			return false;
		});
		$(".zdadd-wrapper .remove", zdForm).click(function(){
			$(this).closest(".zdadd-wrapper").removeClass("open");
			return false;
		});
	};
	
	var onClick = function(){
		var self = this;
		App.ajaxModal({
			id: "zdadd_modal",
			remote: basePath+"lib/common/ui/zd/zdadd.jsp",
			callback: function(modal){
				$.proxy(init, self)(modal);
			}
		});
		return false;
	};
	
	var reload = function(v, options){
		options = options || {};
		v = v || $(this).val() || this.value;
		var params = {tableName: this.tableName, idField: this.idField, nameField: this.nameField};
		if(this.group == true){
			params.groupField = this.groupField;
		}
		var self = this;
		$.extend(params, this.baseParams, options);
		App.getJSON(this.getUrl, params, function(result){
			$(self).html("");
			if(self.allowClear == true && $(self).attr("multiple") != "multiple"){
				renderOption($(self), "", "");
			}
			$.proxy(render, self)(result[self.dataParam]);
			if(!v){
				$(self).val("");
			} else {
				$(self).val(v);
			}
			$(self).trigger("change");
		});
	};
	
	var render = function(rows){
		rows = rows || [];
		var gm = {};
		for(var i=0;i<rows.length;i++){
			var row = rows[i];
			if(this.group == true){
				var g = gm[row.type];
				if(!g){
					gm[row.type] = getGroup($(this), row.type);
					g = gm[row.type];
				}
				renderOption(g, row.id, row.name);
			} else {
				renderOption($(this), row.id, row.name);
			}
		}
	};
	
	var getGroup = function(c, type){
		var g = $('<optgroup label="'+type+'">');
		c.append(g);
		return g;
	};
	
	var renderOption = function(c, id, text){
		var option = $('<option value="'+id+'">'+text+'</option>');
		c.append(option);
	};
	
	var onChange = function(){
		if(this.value != $(this).val()){
			this.value = $(this).val();
			if($.isFunction(this.onChange)){
				$.proxy(this.onChange, this);
			} else {
				var form = $(this).closest("form");
				if(form.length > 0){
					var validator = $.data( form[0], "validator" );
					if ( validator ) {
						validator.element($(this));
					}
				}
				$(this).trigger("select.select3");
			}
		}
	};
	
	$.extend($.fn, {
		select3: function(options){
			var inited = $(this).data("select3.inited");
			if(inited){
				return ;
			} else {
				$(this).data("select3.inited", true);
			}
			$.extend(this, {
				addUrl: basePath+"dic/add",
				getUrl: basePath+"dic/get",
				dataParam: "data",
				group: false,
				autoLoad: true,
				useZd: false,
				groupField: "type",
				//tableName: "zd_ssdq",
				idField: "id",
				nameField: "name",
				baseParams: {}
			}, options);
			if(this.useZd == true){
				if(this.openWin == true){
					var addBtn = $('<button class="btn green zd-add"><i class="fa fa-plus"></i></button>');
					var p = $(this).closest('div[class^="col-md-"]');
					if(p.length == 0){
						p = $(this);
					}
					addBtn.insertAfter(p);
					addBtn.click($.proxy(onClick, this));
				} else {
					$.proxy(createZdBox, this)();
				}
			}
			$(this).change($.proxy(onChange, this));
			var select2Options = $.extend({
				placeholder: ""
			}, options);
			$(this).select2(select2Options);
			if(this.autoLoad == true){
				var v = this.value || $(this).val();
				$(this).val("");
				$.proxy(reload, this)(v);
			}
			this.setParam = $.proxy(function(key, value){
				if(key && value){
					this.baseParams[key] = value;
				}
			}, this);
			this.reload = $.proxy(reload, this);
			return this;
		}
	});
}));