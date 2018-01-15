(function (factory) {
	  if (typeof define === 'function' && define.amd) {
	    define(['jquery'], factory);
	  } else if (typeof exports === 'object') {
	    factory(require('jquery'));
	  } else {
	    factory(jQuery);
	  }
	}(function (jQuery) {
		var FormSectionCollapse = function(element){
		    this.$element = $(element);
			this.init();
		};
		FormSectionCollapse.prototype = {
			constructor: FormSectionCollapse,
			init: function(){
				this.$openBtn = $('<a class="btn btn-circle btn-default"><i class="fa"></i></a>');
				this.$openBtn.prependTo(this.$element);
				this.$angleI = this.$openBtn.find("i");
				this.opened = this.$element.hasClass("open");
				this.setUpDown();
				this.$openBtn.css({
					"width": "20px",
				    "height": "20px",
				    "padding": 0,
				    "fontSize": "16px",
				    "lineHeight": "16px"
				});
				this.$openBtn.on("click.form.section.collapse", $.proxy(this.toggle,this));
			},
			setUpDown: function(){
				if(this.opened){
					this.$angleI.addClass("fa-angle-down").removeClass("fa-angle-up");
					this.$element.css({
					    "borderBottomWidth": 1
					});
				} else {
					this.$angleI.addClass("fa-angle-up").removeClass("fa-angle-down");
					this.$element.css({
					    "borderBottomWidth": 0
					});
				}
			},
			toggle: function(){
				this.$element.toggleClass("open");
				this.opened = !this.opened;
				this.setUpDown();
				this.getItems().each(function(){
					$(this).toggleClass("hide");
				});
			},
			open: function(){
				if(this.opened) return;
				this.toggle();
			},
			close: function(){
				if(!this.opened) return;
				this.toggle();
	        },
	        getItems: function(){
	        	return this.$element.nextUntil(".form-section.collapseable");
	        }
		};
		$.fn.formsectioncollapse = function(){
			var args, option, ret;
		    option = arguments[0], args = 2 <= arguments.length ? __slice.call(arguments, 1) : [];
		    ret = this;
		    this.each(function() {
			    var $this, data;
		        $this = $(this);
		        data = $this.data("form-section-collapse");
		        if (!data) {
		          $this.data("form-section-collapse", data = new FormSectionCollapse(this));
		        }
		        if (typeof option === "string") {
		          return ret = data[option].apply(data, args);
		        }
	        });
	        return ret;
			
		};
		$(function () {
			$('.form-section.collapseable[data-toggle="collapse"]').formsectioncollapse();
		});
	})
);