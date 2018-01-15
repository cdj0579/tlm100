(function( factory ) {
	if ( typeof define === "function" && define.amd ) {
		define( ["validate.additional", "jquery.multi-select", "jquery.quicksearch", "multiSelect3_1"], factory);
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
	
	var reload = function(v){
		var bmselected=this.rows;
		v = v || $(this).val() || this.value;
		var params = {tableName: this.tableName, idField: this.idField, nameField: this.nameField};
		if(this.group == true){
			params.groupField = this.groupField;
		}
		var self = this;
		$.extend(params, this.baseParams);
		$.getJSON(this.getUrl, params, function(result){
			if(result && (result.success == true || result.success == "true")){
				$(self).html("");
				if(self.allowClear == true && $(self).attr("multiple") != "multiple"){
					renderOption($(self), "", "");
				}
				$.proxy(render, self)(result.rows,bmselected);
				quicksearch(self,bmselected,result.rows);
				
				if(!v){
					$(self).val("");
				} else {
					$(self).val(v);
				}
				$(self).trigger("change");
			}
		});
	};
	
	var render = function(rows,bmselected){
		var gm = {};
		for(var i=0;i<rows.length;i++){
			var row = rows[i];
			if(this.group == true){
				var g = gm[row.rqmc];
				if(!g){
					gm[row.rqmc] = getGroup($(this),row.rqid, row.rqmc);
					g = gm[row.rqmc];
				}
				
				if (row.zyid==""){
				bmrenderOption(g, "null", row.tablename,bmselected);
				}else{
				zyrenderOption(g, row.zyid, row.zymc,bmselected,row.tablename);	
				}
				//renderOption(g, row.id, row.name);
			} else {
				if (row.zyid==""){
					renderOption($(this), row.zyid, row.tablename);
					}else{
					renderOption($(this), row.zyid, row.zymc);	
					}
			}
		}
	};
	
	var getGroup = function(c,id, type){
		var g = $('<optgroup value="'+id+'" label="'+type+'">');
		c.append(g);
		return g;
	};
	var cnum =0;
	var bmrenderOption = function(c, id, text,bmselected){
		cnum++;
		var option = $('<option id="'+id+''+cnum+'" value="'+text+'">'+text+'</option>');
		
		for(var i=0;i<bmselected.length;i++){
			if(text==bmselected[i].bm){
				 option = $('<option id="'+id+''+cnum+'" value="'+text+'" selected>'+text+'</option>');
				
				break;
			}
			
		}
		
		
		c.append(option);
	};
	var zyrenderOption = function(c, id, text,bmselected,tablename){
		var option = $('<option id="'+id+'" value="'+tablename+'">'+text+'</option>');
		for(var i=0;i<bmselected.length;i++){
			if(text==bmselected[i].zymc){
			 option = $('<option id="'+id+'" value="'+tablename+'" selected>'+text+'</option>');
			 break;
			}
			
		}
		
		
		c.append(option);
	};
	
	var quicksearch = function(mys,selectzy,allzy){
		$(mys).multiSelect({
			selectableOptgroup: true,
			selectableHeader: "<input style='width:186px;margin-bottom:10px;' type='text' class='search-input' autocomplete='off' placeholder='查询'>",
			selectionHeader: "<input style='width:186px;margin-bottom:10px;' type='text' class='search-input' autocomplete='off' placeholder='查询'>",
			  afterInit: function(ms){
			    var that = this,
			        $selectableSearch = that.$selectableUl.prev(),
			        $selectionSearch = that.$selectionUl.prev(),
			        selectableSearchString = '#'+that.$container.attr('id')+' .ms-elem-selectable:not(.ms-selected)',
			        selectionSearchString = '#'+that.$container.attr('id')+' .ms-elem-selection.ms-selected';

			    that.qs1 = $selectableSearch.quicksearch(selectableSearchString)
			    .on('keydown', function(e){
			      if (e.which === 40){
			        that.$selectableUl.focus();
			        return false;
			      }
			    });

			    that.qs2 = $selectionSearch.quicksearch(selectionSearchString)
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

			    that.qs1 = $selectableSearch.quicksearch(selectableSearchString)
			    .on('keydown', function(e){
			      if (e.which === 40){
			        that.$selectableUl.focus();
			        return false;
			      }
			    });

			    that.qs2 = $selectionSearch.quicksearch(selectionSearchString)
			    .on('keydown', function(e){
			      if (e.which == 40){
			        that.$selectionUl.focus();
			        return false;
			      }
			    });
				  this.qs1.cache();
				  this.qs2.cache();
				  //if(value.length)
				 	if(value.length==allzy.length){
			    		value=["*"];
			    	}
				 	rqid = selectzy[0].rqid==""||selectzy[0].rqid==undefined?rqid:selectzy[0].rqid;
				 	 
				 	for(var i=0;i<value.length;i++){
				 		/*if(selectzy[i].zyid&&!selectzy[i].zyid==""){
				 			id = selectzy[i].zyid;
				 		}else{
				 			id = selectzy[i].zyid==""?id:selectzy[i].zyid;
					 		id = id=="null"?null:id;
				 		}*/
				 		for(var j=0;j<selectzy.length;j++){
				 			if(value[i]==selectzy[j].bm){
				 				id = selectzy[j].zyid
				 				break;
				 			}
				 		}
				 		if(!(Object.prototype.toString.call(id) === "[object String]")){
				 			id = String(id);
				 		}
				 		var allzyid = id;
				 		if(id.substr(0,4)=="null"){
				 			id="null";
				 		}
				 		
				 		
						 $('#my_multi_select2').multiSelect3_1({
					        	getUrl: "zyres.do?action=getSjx",
								group: true,
								autoLoad: true,
								rows:selectzy,
								baseParams: {
									rqid:rqid,
									tablename:value[i],
									zyid:id,
									allzyid:allzyid
								},
					        });
				 	}

			  
			  },
			  afterDeselect: function(value,all,id,rqid){
				  var that = this,
			        $selectableSearch = that.$selectableUl.prev(),
			        $selectionSearch = that.$selectionUl.prev(),
			        selectableSearchString = '#'+that.$container.attr('id')+' .ms-elem-selectable:not(.ms-selected)',
			        selectionSearchString = '#'+that.$container.attr('id')+' .ms-elem-selection.ms-selected';

			    that.qs1 = $selectableSearch.quicksearch(selectableSearchString)
			    .on('keydown', function(e){
			      if (e.which === 40){
			        that.$selectableUl.focus();
			        return false;
			      }
			    });

			    that.qs2 = $selectionSearch.quicksearch(selectionSearchString)
			    .on('keydown', function(e){
			      if (e.which == 40){
			        that.$selectionUl.focus();
			        return false;
			      }
			    });
				  this.qs1.cache();
				  this.qs2.cache();
				  for(var i=0;i<value.length;i++){
					  $("#"+value[i].replace('.','')+"").remove();
				  }
			  
					var sjxrows = selectzy;
					var selected = [];
					for(var i=0;i<sjxrows.length;i++){
						var value = sjxrows[i].rqid+','+sjxrows[i].zyid+','+sjxrows[i].bm+','+sjxrows[i].sjxmc+','+sjxrows[i].zdm;
						selected.push(value);
					}
					$('#my_multi_select2').multiSelect('refresh');
					$('#my_multi_select2').multiSelect('select', selected);
			  }
		});
	}
	
	$.extend($.fn, {
		multiSelect3: function(options){
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