(function (factory) {
	  if (typeof define === 'function' && define.amd) {
	    define(['jquery','ztree'], factory);
	  } else if (typeof exports === 'object') {
	    factory(require('jquery'));
	  } else {
	    factory(jQuery);
	  }
	}(function (jQuery) {
		var CachesSelectedContents = function(element, options, e){
			if (e) {
		      e.stopPropagation();
		      e.preventDefault();
		    }

		    this.$element = $(element);
		    this.contents = [];
			this.contentsMap = {};
			this.options = options;
			
			this.init();
		};
		CachesSelectedContents.DEFAULTS = {
			xtemplate: '<span>您选择了<font color="red"> 0</font>条记录！</span>',
			countSelector: 'span.badge'
		};
		CachesSelectedContents.prototype = {
			constructor: CachesSelectedContents,
			init: function(){
				if(this.options.contents && this.options.contents.length > 0){
					this.contents = this.options.contents;
					for(var i=0;i<this.contents.length;i++){
						var content = this.contents[i];
						this.contentsMap[content.id] = content;
					}
				}
				this.$countElement = this.$element.find(this.options.countSelector);
				this.$contentsViewer = this.$element.closest('.modal-content').find('.page-quick-sidebar-wrapper ul.list-items');
				var self = this;
				this.$contentsViewer.on("click", '.btn.delete', function(){
					var id = $(this).data("id");
					self.remove(id);
					self.$element.trigger("change");
				});
				this.render();
			},
			render: function(){
				this.$contentsViewer.empty();
				if(this.contents && this.contents.length > 0){
					for(var i=0;i<this.contents.length;i++){
						var content = this.contents[i];
						this.$contentsViewer.append($('<li>'+
		                        '<div class="col1">'+
	                            '<div class="cont">'+
	                                '<div class="desc">'+ content.name +'</div>'+
	                            '</div>'+
	                        '</div>'+
	                        '<div class="col2">'+
	                            '<a class="btn red btn-sm delete" data-id="'+content.id+'"><i class="fa fa-remove"></i> 删除</a>'+
	                        '</div>'+
	                    '</li>'));
					}
				}
				this.viewFn();
			},
			viewFn: function(){
				this.$countElement.html(this.contents.length);
			},
			clear: function(){
				this.contents.length = 0;
				this.contentsMap = {}
				this.render();
			},
			add: function(id, obj){
				if(!this.contentsMap[id]){
					this.contents.push(obj);
					this.contentsMap[id] = obj;
					this.render();
				}
			},
			remove: function(id){
				if(this.contentsMap[id]){
					var delCentent = this.contentsMap[id];
					var newContents = [];
					for(var i=0;i<this.contents.length;i++){
						var content = this.contents[i];
						if(delCentent != content){
							newContents.push(content);
						}
					}
					this.contents = newContents;
					this.contentsMap[id] = null;
					this.render();
				}
			},
			getAll: function(){
				return this.contents;
			},
			has: function(id){
				var o = this.contentsMap[id];
				if(o){
					return true;
				} else {
					return false;
				}
			}
		};
		
		$.fn.cachesSelectedContents = function(){
			var args, option, ret;
		    option = arguments[0], args = 2 <= arguments.length ? [].slice.call(arguments, 1) : [];
		    ret = this;
		    this.each(function() {
			    var $this, data;
		        $this = $(this);
		        data = $this.data("caches-selected-contents");
		        if (!data) {
		          var config = $.extend({}, CachesSelectedContents.DEFAULTS, $.fn.cachesSelectedContents.defaults || {}, $this.data(), option);
		          $this.data("caches-selected-contents", data = new CachesSelectedContents(this, config));
		        }
		        if (typeof option === "string") {
		          return ret = data[option].apply(data, args);
		        }
	        });
	        return ret;
			
		};
	})
);