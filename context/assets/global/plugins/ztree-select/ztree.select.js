(function (factory) {
	  if (typeof define === 'function' && define.amd) {
	    define(['jquery','ztree'], factory);
	  } else if (typeof exports === 'object') {
	    factory(require('jquery'));
	  } else {
	    factory(jQuery);
	  }
	}(function (jQuery) {
		var ZtreeSelect = function(element, options, e){
			if (e) {
		      e.stopPropagation();
		      e.preventDefault();
		    }

		    this.$element = $(element);
		    this.$newElement = null,$displayElement=null,$ztreeElement=null;
		    this.treeObj = null;
		    this.options = options;
		    this.treeId = "tree_select_"+this.$element.attr("name");

		    if (this.options.title === null) {
		      this.options.title = this.$element.attr('title');
		    }
			
			this.init();
		};
		ZtreeSelect.DEFAULTS = {
			height: 200,
			data: [],
			dataParam: "rows",
			baseParams: {},
			treeView: {},
			url: null,
			elCLass: "ztree-select",
			displayClass: "display-option",
			containerClass: "ztree-container", 
			hiddenClass: 'ztree-select-hidden',
			ztreeClass: "ztree",
			emptyText: "请选择"
		};
		ZtreeSelect.prototype = {
			constructor: ZtreeSelect,
			init: function(){
				this.$newElement = this.createView();
				this.$newElement.insertAfter(this.$element);
				this.$displayElement = this.$newElement.find('button span.'+this.options.displayClass);
				this.$ztreeElement = this.$newElement.find('div.dropdown-menu div.'+this.options.containerClass+' div.'+this.options.ztreeClass);
		        this.$element.addClass(this.options.hiddenClass);
		        this.initTree();
		        //this.treeObj = $.fn.zTree.getZTreeObj(this.treeId);
		        
		        this.selectNode();
		        this.$element.on("change", $.proxy(this.onChange, this));
			},
			createView: function(){
				var html = '';
				html += '';
				html += '<div class="btn-group '+this.options.elCLass+' form-control">';
				html += '	<button type="button" class="btn dropdown-toggle btn-default" data-toggle="dropdown">';
				html += '		<span class="'+this.options.displayClass+' pull-left"></span>&nbsp;';
				html += '		<i class="fa fa-angle-down"></i>';
				html += '	</button>';
				html += '    <div class="dropdown-menu" style="overflow: hidden;">';
				html += '    	<div style="max-height: '+(this.options.height || 200)+'px;" class="'+this.options.containerClass+'">';
				html += '        	<div class="'+this.options.ztreeClass+'" id="'+this.treeId+'"></div>';
				html += '    	</div>';
				html += '    </div>';
				html += '</div>';
				return $(html);
			},
			initTree: function(){
				var _this = this;
				var cb = function(datas){
					datas = datas || [];
					_this.treeObj = $.fn.zTree.init(_this.$ztreeElement, {
						callback: {
							onClick: $.proxy(_this.onTreeNodeClick,_this)
						},
						view: _this.options.treeView,
						data: {
							simpleData: {
								enable: true,
								idKey: "id",
								pIdKey: "pid"
							}
						}
					},datas);
					var defalutValue = $(_this.$element).val();
					if(defalutValue){
						_this.selectNode();
						_this.validate();
					}
				};
				if(this.options.data && !this.options.url){
					cb(this.options.data);
				} else if(this.options.url){
					$.getJSON(this.options.url, this.options.baseParams, function(json){
						if(json.success == true && json[_this.options.dataParam]){
							cb(json[_this.options.dataParam]);
						}
					});
				}
				this.$ztreeElement.on("click", function(event){
					if($(event.target).is("span") && !$(event.target).hasClass("switch")){
						return;
					}
					event.stopPropagation();
				});
	        },
	        onTreeNodeClick: function(e, treeId, treeNode) {
	        	this.valueNode = treeNode;
	        	this.onDisplay(treeNode.name);
				this.setValue(this.options.valueParam?(treeNode[this.options.valueParam]):(treeNode.id || treeNode.name));
				this.$element.trigger("change.ztreeselect");
			},
			onDisplay: function(text){
				this.$displayElement.html(text);
			},
			selectNode: function(){
				var _value = this.$element.val();
				var displayText = _value || this.options.emptyText;
				if(_value && this.treeObj){
					var node = this.options.valueParam?(this.treeObj.getNodeByParam(this.options.valueParam, _value)):(this.treeObj.getNodeByParam("id", _value) || this.treeObj.getNodeByParam("name", _value));
					if(node){
						this.valueNode = node;
						this.treeObj.selectNode(node, false);
						displayText = node.name;
					}
				}
				this.onDisplay(displayText);
			},
			setValue: function(value){
				this.$element.val(value);
				this.validate();
			},
			onChange: function(){
				this.selectNode();
				this.validate();
				this.$element.trigger("change.ztreeselect");
			},
			getValueNode: function(){
				return this.valueNode;
			},
			validate: function(){
				var form = $(this.$element).closest("form");
				if(form.length > 0){
					var validator = $.data( form[0], "validator" );
					if ( validator ) {
						validator.element($(this.$element));
					}
				}
			}
		};
		$.fn.ztreeSelect = function(){
			var args, option, ret;
		    option = arguments[0], args = 2 <= arguments.length ? __slice.call(arguments, 1) : [];
		    ret = this;
		    this.each(function() {
			    var $this, data;
		        $this = $(this);
		        data = $this.data("ztree-select");
		        if (!data) {
		          var config = $.extend({}, ZtreeSelect.DEFAULTS, $.fn.ztreeSelect.defaults || {}, $this.data(), option);
		          $this.data("ztree-select", data = new ZtreeSelect(this, config));
		        }
		        if (typeof option === "string") {
		          return ret = data[option].apply(data, args);
		        }
	        });
	        return ret;
			
		};
	})
);