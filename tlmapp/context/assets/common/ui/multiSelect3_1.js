(function( factory ) {
	if ( typeof define === "function" && define.amd ) {
		define( ["validate.additional", "jquery.multi-select", "jquery.quicksearch"], factory);
	} else {
		factory( jQuery );
	}
}(function(){
	var init = function(modal){
		var self = this;
		var form = $('#zdadd_form_id');
		$.proxy(initFormValidate, self)(form, modal);
    	$('.save-zd').click(function(){
    		form.submit();
		});
	};
	
	var mulitSelectObj = null;
	
	var initFormValidate = function(form, m){
		var self = this;
    	$.metadata.setType('attr','validate');
    	form.validateB({
    		ignore: ".zd-ignore-validate",
    		notMsg: true,
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
    					m.removeClass("open");
    				}
    				$.proxy(reload, self)(result.id);
    			} else {
    				msg = result && result.errors && result.errors.exception ?result.errors.exception:"提交失败！";
    				Frame.alert("错误",msg);
    			}
    		}
    	});
	};
	
	var createZdBox = function(){
		var zdForm = $(
			'<form class="zd-group" style="height: 28px;">'+
				'<div class="zdadd-wrapper control-group control">'+
					'<button class="btn green remove"><i class="icon-remove"></i></button>'+
					'<input type="text" name="value" class="value ignore-validate required" placeholder="请输入要添加的字典"/>'+
					'<button class="btn green save"><i class="icon-plus"></i></button>'+
				'</div>'+
			'</form>'
		);
		zdForm.insertAfter($(this));
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
		Frame.ajaxModal({
			id: "zdadd_modal",
			url: basePath+"lib/common/ui/zd/zdadd.jsp",
			callback: function(modal){
				$.proxy(init, self)(modal);
			}
		});
		return false;
	};
	var res =[];
	var mycount=-1;
	var a ;
	var reload = function(v){
		var sjxrows = this.rows;
		v = v || $(this).val() || this.value;
		var params = {tableName: this.tableName, idField: this.idField, nameField: this.nameField};
		if(this.group == true){
			params.groupField = this.groupField;
		}
		var self = this;
		$.extend(params, this.baseParams);
		
		
		
		$.getJSON(this.getUrl, params, function(result){
			if(result && (result.success == true || result.success == "true")){
			
			/*	mycount++;
				if(mycount==0){
					res=a =result.rows;
				}else{
					res = a.concat(result.rows);
				}*/
				
				//$(self).html("");
				if(self.allowClear == true && $(self).attr("multiple") != "multiple"){
					
				}
				$.proxy(render, self)(result.rows,sjxrows);
				//$.proxy(render, self)(res,sjxrows);				
				//render(self,res,sjxrows);
				quicksearch(self, sjxrows);
				
				/*if($(self).val()){
					console.log("**"+$(self).val());
				} else {
					if(!v){
						$(self).val("");
					} else {
						$(self).val(v);
					}
				}*/
				$(self).trigger("change");
			}
		});
	};
	
	var render = function(rows,sjxrows){
		var gm = {};
		var zymc = "";
		for(var i=0;i<rows.length;i++){
			var row = rows[i];
		if((row.zymc=="")||(row.zymc==null)||(row.zymc=="null")){
			
			zymc = row.tablename
			}else{
				zymc = row.zymc;
			}
			//if(this.group == true){
				var g = gm[row.tablename];
				if(!g){
					gm[row.tablename] = getGroup($(this), zymc,row.tablename);
					g = gm[row.tablename];
				}
				renderOption(g,row,sjxrows);
				//renderOption(g, row.id, row.name);
			//} 
			/*else {
				if (row.sjxmc==""){
					renderOption(g,row.bzd);
					}else{
					renderOption(g, row.sjxmc);	
					}
			}*/
		}
	};
	
	var getGroup = function(c, type,bm){
		var g = $('<optgroup  id="'+bm.replace('.','')+'" label="'+type+'">');
		c.append(g);
		return g;
	};
	
	var renderOption = function(c,row,sjxrows){
		var text = row.sjxmc;
		if(text==""){text=row.bzd}
		if(row.zyid==null){row.zyid=""}
		var value =row.rqid+','+row.zyid+','+row.tablename+','+row.sjxmc+','+row.bzd;
		
		var option = $('<option value="'+value+'">'+text+'</option>');
		for(var i=0;i<sjxrows.length;i++){
			var selected = sjxrows[i].rqid+','+sjxrows[i].zyid+','+sjxrows[i].bm+','+sjxrows[i].sjxmc+','+sjxrows[i].zdm;
			if(row.tablename+','+row.sjxmc+','+row.bzd==sjxrows[i].bm+','+sjxrows[i].sjxmc+','+sjxrows[i].zdm){
				option = $('<option value="'+selected+'" selected>'+text+'</option>');
			}
		}
		c.append(option);
	};
	
	var quicksearch = function(mys, sjxrows){
		if(mulitSelectObj){
			$(mys).multiSelect('refresh');
		} else {
			mulitSelectObj = $(mys).multiSelect({
				selectableOptgroup: true,
				selectableHeader: "<input style='width:186px;margin-bottom:10px;' type='text' class='search-input' autocomplete='off' placeholder='查询'>",
				quanxuanHeader:'<label class="checkbox"><span class=""><input type="checkbox" value=""></span> 全选</label>',
				selectionHeader: "<input style='width:186px;margin-bottom:10px;' type='text' class='search-input' autocomplete='off' placeholder='查询'>",
				  afterInit: function(ms){
				    var that = this,
				        $selectableSearch = that.$selectableUl.prev(),
				        $selectionSearch = that.$selectionUl.prev(),
				        selectableSearchString = '#'+that.$container.attr('id')+' .ms-elem-selectable:not(.ms-selected)',
				        selectionSearchString = '#'+that.$container.attr('id')+' .ms-elem-selection.ms-selected';

				    that.qs3 = $selectableSearch.quicksearch(selectableSearchString)
				    .on('keydown', function(e){
				      if (e.which === 40){
				        that.$selectableUl.focus();
				        return false;
				      }
				    });

				    that.qs4 = $selectionSearch.quicksearch(selectionSearchString)
				    .on('keydown', function(e){
				      if (e.which == 40){
				        that.$selectionUl.focus();
				        return false;
				      }
				    });
				  },
				  afterSelect: function(value,all,id,rqid){
					  
					  var that = this,
				        $selectableSearch = that.$selectableUl.prev(),
				        $selectionSearch = that.$selectionUl.prev(),
				        selectableSearchString = '#'+that.$container.attr('id')+' .ms-elem-selectable:not(.ms-selected)',
				        selectionSearchString = '#'+that.$container.attr('id')+' .ms-elem-selection.ms-selected';

				    that.qs3 = $selectableSearch.quicksearch(selectableSearchString)
				    .on('keydown', function(e){
				      if (e.which === 40){
				        that.$selectableUl.focus();
				        return false;
				      }
				    });

				    that.qs4 = $selectionSearch.quicksearch(selectionSearchString)
				    .on('keydown', function(e){
				      if (e.which == 40){
				        that.$selectionUl.focus();
				        return false;
				      }
				    });
					  for(var i=0;i<value.length;i++){
						  var a = value[i].split(',');
					  }
					  
					  
					  this.qs3.cache();
					  this.qs4.cache();
				  },
				  afterDeselect: function(value,all,id,rqid){
					  var that = this,
				        $selectableSearch = that.$selectableUl.prev(),
				        $selectionSearch = that.$selectionUl.prev(),
				        selectableSearchString = '#'+that.$container.attr('id')+' .ms-elem-selectable:not(.ms-selected)',
				        selectionSearchString = '#'+that.$container.attr('id')+' .ms-elem-selection.ms-selected';

				    that.qs3 = $selectableSearch.quicksearch(selectableSearchString)
				    .on('keydown', function(e){
				      if (e.which === 40){
				        that.$selectableUl.focus();
				        return false;
				      }
				    });
				    top.rows;
				    for(i=0;i<top.rows.length;i++){
				    	var a = top.rows[i].rqid+','+top.rows[i].zyid+','+top.rows[i].bm+','+top.rows[i].sjxmc+','+top.rows[i].zdm;
				    	if(a ==value){
				    		//console.log("-----"+top.rows)
				    		top.rows.splice(i,1);
				    		//console.log("+++++"+top.rows)
				    	}
				    }
				    that.qs4 = $selectionSearch.quicksearch(selectionSearchString)
				    .on('keydown', function(e){
				      if (e.which == 40){
				        that.$selectionUl.focus();
				        return false;
				      }
				    });
					  this.qs3.cache();
					  this.qs4.cache();		   
				  }
			});
		}
		var selected = [];
		for(var i=0;i<sjxrows.length;i++){
			var value = sjxrows[i].rqid+','+sjxrows[i].zyid+','+sjxrows[i].bm+','+sjxrows[i].sjxmc+','+sjxrows[i].zdm;
			selected.push(value);
		}
		$(mys).multiSelect('select', selected);
	}
	
	$.extend($.fn, {
		multiSelect3_1: function(options){
			$.extend(this, {
				getUrl: "zdgl.do?action=get",
				group: false,
				autoLoad: true,
				groupField: "type",
				//tableName: "zd_ssdq",
				idField: "id",
				nameField: "name",
				baseParams: {}
			}, options);
			if($.isFunction(this.onChange)){
				$(this).change($.proxy(this.onChange, this));
			}
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