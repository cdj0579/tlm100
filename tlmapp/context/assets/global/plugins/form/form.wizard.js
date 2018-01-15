(function (factory) {
	  if (typeof define === 'function' && define.amd) {
	    define(['jquery', 'bootstrap.wizard'], factory);
	  } else if (typeof exports === 'object') {
	    factory(require('jquery'));
	  } else {
	    factory(jQuery);
	  }
	}(function (jQuery) {
		var FormCard = function(options){
		    this.$element = $(options.el);
		    this.wizard = options.wizard;
		    this.index = options.index;
		    this.options = options;
			this.init();
		};
		FormCard.prototype = {
			constructor: FormCard,
			init: function(){
				this.id = this.$element.attr("id");
				if(!this.id){
					this.$element.attr("id", this.wizard.id+"_card_"+this.index);
					this.id = this.$element.attr("id");
				}
				this.$element.addClass("tab-pane");
				if(this.options.card){
					this.card = this.options.card;
					if(this.card && $.isFunction(this.card.init)){
						this.card.init(this.wizard);
					}
				}
			},
			getParent: function(){
				return this.$element.parent();
			},
			show: function(){
				if(this.card && $.isFunction(this.card.show)){
					this.card.show();
				}
			},
			brforenext: function(){
				if(this.card && $.isFunction(this.card.submit)){
					this.card.submit(this.wizard);
					return false;
				}
				return true;
			},
			ignore: function(){
				return this.options.ignore == true;
			}
		};
		var FormWizard = function(element, options){
		    this.$element = $(element);
		    this.options = options;
		    if(options.form){
		    	this.form = options.form;
		    }
		    var requireCards = [];
		    if(this.options.items) {
		    	$.each(this.options.items, function(i){
		    		if(!this.card && this.require){
		    			requireCards.push(this);
		    		}
		    	});
		    }
		    if(requireCards.length > 0){
		    	var reqs = [];
		    	$.each(requireCards, function(i){
		    		reqs.push(this.require);
		    	});
		    	var self = this;
		    	require(reqs, function(){
		    		var args = arguments;
		    		$.each(requireCards, function(i){
		    			this.card = args[i];
			    	});
		    		self.init();
		    	});
		    } else {
		    	this.init();
		    }
		};
		FormWizard.DEFAULTS = {
			tabclickable: false,
			items: [],
			buttonPreviousClass: "button-previous",
			buttonPreviousText: "上一步",
			buttonNextClass: "button-next",
			buttonNextText: "下一步",
			buttonIgnoreClass: "button-ignore",
			buttonIgnoreText: "忽略",
			buttonSubmitClass: "button-submit",
			buttonSubmitText: "提交",
			showCancer: false,
			buttonCancerClass: "button-cancer",
			buttonCancerText: "取消",
			stepTitleSelector: ".step-title",
			stepTitleText: "步骤"
		};
		FormWizard.prototype = {
			constructor: FormWizard,
			init: function(){
				this.id = this.$element.attr("id");
				if(!this.id){
					this.$element.attr("id", "wizard_"+new Date().getTime());
					this.id = this.$element.attr("id");
				}
				this.$title = this.$element.find(this.stepTitleSelector);
				this.initItems();
				this.initWizard();
				var self = this;
				this.$element.bootstrapWizard({
		            'nextSelector': '.'+this.options.buttonNextClass,
		            'previousSelector': '.'+this.options.buttonPreviousClass,
		            onTabClick: function (tab, navigation, index, clickedIndex) {
		                if(self.options.tabclickable == false){
		                	return false;
		                }
		            },
		            onNext: function (tab, navigation, index) {
		            	/*if($.isFunction(self.options.beforenext)){
		                	var status = self.options.beforenext.call(self, [self.items[index-1], index-1]);
		                	if(status == false) return false;
		                	return ;
		                }*/
		            	if(self.ignoreCheck != true){
		            		return self.items[index-1].brforenext();
		            	}
		            },
		            onPrevious: function (tab, navigation, index) {
		            	/*if($.isFunction(self.options.beforeprevious)){
		                	var status = self.options.beforeprevious.call(self, [self.items[index], index]);
		                	if(status == false) return false;
		                }*/
		            },
		            onTabShow: function (tab, navigation, index) {
		                var total = navigation.find('li').length;
		                var current = index + 1;
		                var $percent = (current / total) * 100;
		                self.$element.find('.progress-bar').css({
		                    width: $percent + '%'
		                });
		                self.handleTitle(tab, navigation, index);
		                self.items[index].show();
		                if($.isFunction(self.options.onShow)){
		                	self.options.onShow.call(self);
		                }
		            }
		        });
				this.$buttonPrevious.hide();
				this.$buttonSubmit.click(function () {
					self.items[self.items.length-1].brforenext();
		        }).hide();
				this.$buttonIgnore.click(function () {
					self.next();
		        }).hide();
				this.$buttonCancer.click(function () {
					if($.isFunction(self.options.onCancer)){
	                	self.options.onCancer.call(self);
	                }
		        });
			},
			next: function(){
				this.ignoreCheck = true;
				this.$element.bootstrapWizard('next');
				this.ignoreCheck = false;
			},
			initItems: function(){
				this.items = [];
				this.$cardParent = null;
				var self = this;
				$.each(this.options.items, function(i){
					this.index = i;
					this.wizard = self;
					var card = new FormCard(this);
					var p = card.getParent();
					if(self.cardParent){
						if(self.cardParent != p) throw Exception("error cards!");
					} else {
						self.$cardParent = p;
					}
					self.items.push(card);
				});
			},
			initWizard: function(){
				if(this.$cardParent.length > 0){
					this.$wizard = $('<div class="form-wizard">');
					this.initBody();
					this.initActions();
					this.$wizard.prependTo(this.$cardParent);
				}
			},
			initBody: function(){
				this.$wizarBody = $('<div class="form-body">');
				this.initTabNav();
				this.initProgressbar();
				this.initTabContent();
				this.$wizarBody.appendTo(this.$wizard);
			},
			initTabNav: function(){
				var nav = $('<ul class="nav nav-pills nav-justified steps">');
				if(this.items && this.items.length > 0){
					var self = this;
					$.each(this.items, function(){
						nav.append(self.createTabLi(this));
					});
				}
				nav.appendTo(this.$wizarBody);
			},
			createTabLi: function(card){
				return $(
					'<li>'+
		                '<a href="#'+card.id+'" data-toggle="tab" class="step">'+
		                    '<span class="number"> '+(card.index+1)+' </span>'+
		                    '<span class="desc">'+
		                        '<i class="fa fa-check"></i> '+card.options.text+' </span>'+
		                '</a>'+
		            '</li>'
				);
			},
			initProgressbar: function(){
				var bar = $(
					'<div class="progress progress-striped" role="progressbar">'+
	                	'<div class="progress-bar progress-bar-success"> </div>'+
		            '</div>'
		        );
				bar.appendTo(this.$wizarBody);
			},
			initTabContent: function(){
				var tabContent = $('<div class="tab-content">');
				if(this.items && this.items.length > 0){
					var self = this;
					$.each(this.items, function(){
						tabContent.append(this.$element);
						this.$element.removeClass('hide');
						//this.$element.show();
					});
				}
				tabContent.appendTo(this.$wizarBody);
			},
			initActions: function(){
				var html = '<div class="form-actions">';
				html += 		'<div class="row">';
				html +=     		'<div class="col-md-offset-3 col-md-9">';
				html +=     			'<a href="javascript:;" class="btn default '+this.options.buttonPreviousClass+'">';
				html +=     				'<i class="fa fa-angle-left"></i>&nbsp;'+this.options.buttonPreviousText+' </a>&nbsp;';
				html += 				'<a href="javascript:;" class="btn yellow '+this.options.buttonIgnoreClass+'">';
				html += 					'<i class="fa fa-mail-forward"></i>&nbsp;'+this.options.buttonIgnoreText;
				html += 				'</a>&nbsp;';
				html += 				'<a href="javascript:;" class="btn btn-outline green '+this.options.buttonNextClass+'">';
				html += 					this.options.buttonNextText+'&nbsp;<i class="fa fa-angle-right"></i>';
				html += 				'</a>&nbsp;';
				html += 				'<a href="javascript:;" class="btn green '+this.options.buttonSubmitClass+'">';
				html += 					this.options.buttonSubmitText+'&nbsp;<i class="fa fa-check"></i>';
				html += 				'</a>&nbsp;';
				html += 				'<a href="javascript:;" class="btn default '+this.options.buttonCancerClass+'">';
				html += 					'<i class="fa fa-share"></i>&nbsp;'+this.options.buttonCancerText;
				html += 				'</a>&nbsp;';
				html += 			'</div>';
				html += 		'</div>';
				html += 	'</div>';
				this.$wizarActions = $(html);
				this.$wizarActions.appendTo(this.$wizard);
				this.$buttonPrevious = this.$wizarActions.find('.btn.'+this.options.buttonPreviousClass);
				this.$buttonNext = this.$wizarActions.find('.btn.'+this.options.buttonNextClass);
				this.$buttonIgnore = this.$wizarActions.find('.btn.'+this.options.buttonIgnoreClass);
				this.$buttonSubmit = this.$wizarActions.find('.btn.'+this.options.buttonSubmitClass);
				this.$buttonCancer = this.$wizarActions.find('.btn.'+this.options.buttonCancerClass);
				if(!this.options.showCancer) this.$buttonCancer.hide();
			},
			handleTitle: function(tab, navigation, index) {
	            var total = navigation.find('li').length;
	            var current = index + 1;
	            $(this.options.stepTitleSelector, this.$element).text(' ' + (index + 1) + ' / ' + total + ' '+this.options.stepTitleText);
	            // set done steps
	            $('li', this.$element).removeClass("done");
	            var li_list = navigation.find('li');
	            for (var i = 0; i < index; i++) {
	                $(li_list[i]).addClass("done");
	            }

	            if (current == 1) {
	            	this.$buttonPrevious.hide();
	            } else {
	            	this.$buttonPrevious.show();
	            }

	            if (current >= total) {
	            	this.$buttonNext.hide();
	            	this.$buttonSubmit.show();
	            } else {
	            	this.$buttonNext.show();
	            	this.$buttonSubmit.hide();
	            }
	            if(this.items[index].ignore()){
	            	this.$buttonIgnore.show();
	            } else {
	            	this.$buttonIgnore.hide();
	            }
	        }
		};
		$.fn.formwizard = function(){
			var args, option, ret;
		    option = arguments[0], args = 2 <= arguments.length ? __slice.call(arguments, 1) : [];
		    ret = this;
		    this.each(function() {
			    var $this, data;
		        $this = $(this);
		        data = $this.data("form-wizard");
		        if (!data) {
		        	var config = $.extend({}, FormWizard.DEFAULTS, $.fn.formwizard.defaults || {}, $this.data(), option);
			    	$this.data("form-wizard", data = new FormWizard(this, config));
		        }
		        if (typeof option === "string") {
		          return ret = data[option].apply(data, args);
		        }
	        });
	        return ret;
			
		};
	})
);