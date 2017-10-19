var Frame = function () {
	//发布时间
	var fbsj = "2016";
    var sidebarWidth = 225;
    var sidebarCollapsedWidth = 35;
    var adjustIframeHeightTimer = null; //子页面内容动态时，高度适应定时器
    var defaultLeftMenu = null;
	
	var createHeader = function(){
		var el = $(
			'<div class="page-header">'+
				'<div class="page-header-top">'+
					'<div class="container">'+
					'<div class="page-logo">'+
                    '<a href="">'+
                        '<img src="theme/assets/layouts/layout3/img/logo-default.jpg" alt="logo" class="logo-default">'+
                    '</a>'+
                '</div>'+
                ' <div class="top-menu">'+
                '</div>'+
					'</div>'+
				'</div>'+
			'<div class="page-header-menu" style="display:block;">'+
				'<div class="container">'+
				'</div>'+
			'</div>'+
			'</div>'
		);
		
		return el;
	};
	
	var createTopBar = function(config, c){
		var el = $('<ul class="nav navbar-nav pull-right" style="padding-bottom:10px"></ul>');
		el.appendTo(c);
		//createMsgDD({}, el);
		createUserDD(config, el);
	};
	
	var createMenuDDHtml = function(items){
		var html = '';
		var listenters = [];
		if(items && $.isArray(items)){
			for(var i=0;i<items.length;i++){
				var o = items[i];
				if(o == "-"){
					html += '<li class="divider"></li>';
				} else if($.isPlainObject(o)) {
					var name = "menu_"+(o.name || new Date().getTime());
					if(basePath && o.url && (o.url.indexOf("/") != 0 && o.url.indexOf("http://") != 0 && o.url.indexOf("https://") != 0)){
						o.url = basePath+o.url;
			    	}
					var href = o.url || "javascript: void(0);";
					var cls = (o.isAjax == true)?' class="ajaxify"':'';
					var icon = o.iconCls?('<i class="'+o.iconCls+'"></i>'):'';
					html += '<li id="'+name+'"><a href="'+href+'"'+cls+'>'+icon+' '+o.text+'</a></li>';
					if($.isFunction(o.handler)){
						listenters.push({id: name, handler: o.handler});
					}
				}
			}
		}
		return {html: html, listenters: listenters};
	};
	
	var createUserDD = function(config, c){
		var o = createMenuDDHtml(config.items);
		var el = $(
			'<li class="dropdown dropdown-user dropdown-dark">'+
				'<a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">'+
					//'<img alt="" width="29" height="29" src="'+$.CurrentBasePath+'lib/common/image/avatar.png" />'+
					' <img alt="" class="img-circle" src="'+$.CurrentBasePath+'theme/assets/layouts/layout3/img/avatar9.jpg">'+
					'<span class="username username-hide-mobile">&nbsp;'+config.userInfo.userName+'&nbsp;</span>'+
					//'<i class="icon-angle-down"></i>'+
				'</a>'+
				'<ul class="dropdown-menu dropdown-menu-default">'+
				o.html+
				'</ul>'+
			'</li>'
		);
		el.appendTo(c);
		 $('div.top-menu ul.nav').on('mouseenter',function(){
			$('li.dropdown-user').addClass('open');
		});
		$('div.top-menu ul.nav').on('mouseleave',function(){
			$('li.dropdown-user').removeClass('open');
		});
		for(var i=0;i<o.listenters.length;i++){
			var l = o.listenters[i];
			$("#"+l.id).click(l.handler);
		}
		// handle ajax links
       /* jQuery('.header').on('click', ' li > a.ajaxify', function (e) {
            e.preventDefault();
            App.scrollTop();

            var url = $(this).attr("href");
            setActive($(this).parents("li"));
            Frame.ajaxLoadContent(url);
        });*/
	};
	
	var createMsgDD = function(config, c){
		var el = $(
			'<li class="dropdown" id="header_task_bar">'+
				'<a href="#" class="dropdown-toggle" data-toggle="dropdown">'+
					'<i class="icon-tasks"></i>'+
					'<span class="badge">5</span>'+
				'</a>'+
				'<ul class="dropdown-menu extended tasks">'+
					'<li>'+
						'<p>You have 12 pending tasks</p>'+
					'</li>'+
					'<li>'+
						'<a href="#">'+
							'<span class="task">'+
								'<span class="desc">New release v1.2</span>'+
								'<span class="percent">30%</span>'+
							'</span>'+
								'<span class="progress progress-success ">'+
								'<span style="width: 30%;" class="bar"></span>'+
							'</span>'+
						'</a>'+
					'</li>'+
					'<li class="external">'+
						'<a href="#">See all tasks <i class="m-icon-swapright"></i></a>'+
					'</li>'+
				'</ul>'+
			'</li>'
		);
		el.appendTo(c);
	};
	
	var createrSearchRight = function(config,c){
		var el = $('<form class="search-form" action="'+config.action+'" method="GET">'+
                   ' <div class="input-group">'+
                        ' <input type="text" class="form-control" placeholder="'+config.searchText+'" name="query">'+
                      '   <span class="input-group-btn">'+
                         '    <a href="javascript:;" class="btn submit">'+
                        '         <i class="icon-magnifier"></i>'+
                           '  </a>'+
                       '  </span>'+
                   '  </div>'+
               '  </form>')
               el.appendTo(c);
		$('input.form-control').on('focus',function(){
			$('form.search-form').addClass('open');
		});
		$('input.form-control').on('blur',function(){
			$('form.search-form').removeClass('open');
		});
	}
	
	var createHorizontalNav = function(config, c){

		var el = $('<div class="hor-menu">'+
                '<ul class="nav navbar-nav" id="narTop">'+
            	'</ul>'+
				'</div>');
		el.appendTo(c);
		var navEl = $('#narTop', el);
		if(config.items && $.isArray(config.items)){
			for(var i=0;i<config.items.length;i++){
				createMenuItem(config.items[i], navEl, true, true);
			}
		}
		/*var showSearchItem = (config.showSearchItem == true || config.showSearchItem == 'true');
		if(showSearchItem){
			createSearchItem(navEl, config.searchFn, config.searchText, true);
		}*/
	};
	
	var handleHorizontalMenu = function () {
		
		// handle ajax links
        jQuery('.page-header').on('click', '.nav li > a.ajaxify', function (e) {
            e.preventDefault();
            App.scrollTop();
            
            setActive($(this).parents("li"));
            var items = $(this).closest("li").data("children");
            if(items && items.length > 0){
            	reloadLeftMenu(items);
            	var url = $(this).closest("li").data("url");
            	if(url){
            		Frame.ajaxLoadContent(url);
            	} else {
            		Frame.selectMenu(items[0].name);
            	}
            	Frame.setPageFullWidth(false);
            } else {
            	removePageSidebar();
            	if(defaultLeftMenu){
            		createVerticalNav(defaultLeftMenu, $('.page-container'));
            		Frame.setPageFullWidth(false);
            	} else {
            		Frame.setPageFullWidth(true);
            	}
            	var url = $(this).attr("href");
            	Frame.ajaxLoadContent(url);
            }
        });
		
        //handle hor menu search form toggler click
        $('.header').on('click', '.hor-menu .hor-menu-search-form-toggler', function (e) {
            if ($(this).hasClass('hide')) {
                $(this).removeClass('hide');
                $('.header .hor-menu .search-form').hide();
            } else {
                $(this).addClass('hide');
                $('.header .hor-menu .search-form').show();
            }
            e.preventDefault();
        });
    };
	
	var createVerticalNav = function(config, c){
		var el = $(
			'<div class="page-sidebar nav-collapse collapse">'+
				'<ul class="page-sidebar-menu">'+
					'<li>'+
						'<div class="sidebar-toggler hidden-phone"></div>'+
					'</li>'+
				'</ul>'+
			'</div>'
		);
		el.prependTo(c);
		var navEl = $('ul.page-sidebar-menu', el);
		var showSearchItem = (config.showSearchItem == true || config.showSearchItem == 'true');
		if(showSearchItem){
			createSearchItem(navEl, config.searchFn, config.searchText, false);
		}
		createVerticalSubMenu(config.items, navEl);
		handleVerticalMenu();
		handleSidebarAndContentHeight(); // fix content height            
        handleFixedSidebar(); // reinitialize fixed sidebar
        handleFixedSidebarHoverable(); // reinitialize fixed sidebar hover effect
	};
	
	var reloadLeftMenu = function(items, navEl){
		navEl = navEl?$(navEl):$('.page-sidebar ul.page-sidebar-menu');
		if(navEl.length == 0){
			createVerticalNav({
				showSearchItem: false,
				items: items
			}, $('.page-container'));
		} else {
			navEl.find('li[id^="menu_"]').remove();
			createVerticalSubMenu(items, navEl);
		}
	}
	
	var removePageSidebar = function(){
		var sidebar = $('.page-sidebar');
		if(sidebar){
			sidebar.remove();
		}
	};
	
	var createVerticalSubMenu = function(items, navEl){
		if(items && $.isArray(items)){
			for(var i=0;i<items.length;i++){
				createMenuItem(items[i], navEl, false, true);
			}
		}
	};
	
	var onSidebarTopMenuClick = function(el){
		el = $(el);
		if (el.next().hasClass('sub-menu') == false) {
            if ($('.btn-navbar').hasClass('collapsed') == false) {
                $('.btn-navbar').click();
            }
            return;
        }

        var parent = el.parent().parent();

        parent.children('li.open').children('a').children('.arrow').removeClass('open');
        parent.children('li.open').children('.sub-menu').slideUp(200);
        parent.children('li.open').removeClass('open');

        var sub = el.next();
        if (sub.is(":visible")) {
            jQuery('.arrow', el).removeClass("open");
            el.parent().removeClass("open");
            sub.slideUp(200, function () {
                handleSidebarAndContentHeight();
            });
        } else {
            jQuery('.arrow', el).addClass("open");
            el.parent().addClass("open");
            sub.slideDown(200, function () {
                handleSidebarAndContentHeight();
            });
        }
	};
	
	var handleVerticalMenu = function () {
		// handle sidebar show/hide
        $('.page-sidebar').on('click', '.sidebar-toggler', function (e) {            
            var body = $('body');
            var sidebar = $('.page-sidebar');

            if ((body.hasClass("page-sidebar-hover-on") && body.hasClass('page-sidebar-fixed')) || sidebar.hasClass('page-sidebar-hovering')) {
                body.removeClass('page-sidebar-hover-on');
                sidebar.css('width', '').hide().show();
                e.stopPropagation();
                App.runResponsiveHandlers();
                return;
            }

            $(".sidebar-search", sidebar).removeClass("open");

            if (body.hasClass("page-sidebar-closed")) {
                body.removeClass("page-sidebar-closed");
                if (body.hasClass('page-sidebar-fixed')) {
                    sidebar.css('width', '');
                }
            } else {
                body.addClass("page-sidebar-closed");
            }
            App.runResponsiveHandlers();
        });
        
        jQuery('.page-sidebar').on('click', 'li > a', function (e) {
        	onSidebarTopMenuClick(this);
            e.preventDefault();
        });

        // handle ajax links
        jQuery('.page-sidebar').on('click', ' li > a.ajaxify', function (e) {
            e.preventDefault();
            App.scrollTop();

            var url = $(this).attr("href");
            setActive($(this).parents("li"), true);
            Frame.ajaxLoadContent(url);
        });
    };
    
    var setActive = function(_this, isSidebar){
    	var menuContainer = $('ul.page-sidebar-menu,ul.nav');
    	if(isSidebar == true){
    		menuContainer = $('ul.page-sidebar-menu');
    	}
    	$('li.active', menuContainer).removeClass('active');
    	//menuContainer.children('li.active').removeClass('active');
        menuContainer.children('arrow.open').removeClass('open');
        
        var parents = $(_this).parents('li');
        if(parents.length > 0){
        	parents.each(function () {
        		$(this).addClass('active');
        		$(this).children('a > span.arrow').addClass('open');
        	});
        } else {
        	$(_this).children('a > span.arrow').addClass('open');
        }
        $(_this).addClass('active');
    };
	
	var createSearchItem = function(c, fn, searchText, isTopMenu){
		searchText = searchText?searchText:"Search...";
		var el = null;
		if(isTopMenu){
			el = $(
				'<li>'+
					'<span class="hor-menu-search-form-toggler">&nbsp;</span>'+
					'<div class="search-form hidden-phone hidden-tablet">'+
						'<form class="form-search">'+
							'<div class="input-append">'+
								'<input type="text" placeholder="'+searchText+'" class="m-wrap">'+
								'<button type="button" class="btn"></button>'+
							'</div>'+
						'</form>'+
					'</div>'+
				'</li>'
			);
		} else {
			el = $(
				'<li>'+
					'<form class="sidebar-search">'+
						'<div class="input-box">'+
							'<a href="javascript:;" class="remove"></a>'+
							'<input type="text" placeholder="'+searchText+'" />'+
							'<button type="button" class="submit"></button>'+
						'</div>'+
					'</form>'+
				'</li>'
			);
		}
		el.appendTo(c);
		var hideForm = function(){
			if(isTopMenu){
				$('span.hor-menu-search-form-toggler', el).click();
			}
		};
		$('.page-sidebar').on('click', '.sidebar-search .remove', function (e) {
            e.preventDefault();
            $('.sidebar-search').removeClass("open");
        });
		if($.isFunction(fn)){
			$('form button', el).on('click', function (e) {
				e.preventDefault();
				if($('body').hasClass("page-sidebar-closed") && $('.sidebar-search').hasClass('open') == false){
					if ($('.page-sidebar-hoverable').size() === 1) {
						$('.page-sidebar .sidebar-toggler').click(); //trigger sidebar toggle button
					}
					$('.sidebar-search').addClass("open");
				} else {
					fn($('form input', el));
					hideForm();
				}
				
			});
			$('form input', el).on('keypress', function (e) {
				if (e.which == 13) {
					fn($(this));
					hideForm();
					return false;
				}
			});
		}
	};
	
	var createMenuItem = function(config, c, isTopMenu, top){
		config = $.extend({
    		url: "",
    		text: "",
    		isAjax: false,
    		expanded: false,
    		iconCls: "",
    		children: [],
    		name: "m_"+(new Date().getTime()) 
    	}, config);
		var hasChildren = ($.isArray(config.children) && config.children.length > 0);
		var showSubOnLeftMenu = false;
		var isAjax = (config.isAjax == true || config.isAjax == "true");
		var subCls = isTopMenu?'dropdown-menu':'sub-menu';
		if(hasChildren && config.leftList == true && isTopMenu){
			showSubOnLeftMenu = true;
			hasChildren = false;
			isAjax = true;
		}
		if(basePath && config.url && (config.url.indexOf("/") != 0 && config.url.indexOf("http://") != 0 && config.url.indexOf("https://") != 0)){
			config.url = basePath+config.url;
    	}
		var el = $(
			'<li id="menu_'+config.name+'" class="menu-dropdown classic-menu-dropdown">'+
				(hasChildren?('<a'+(isTopMenu?' data-toggle="dropdown" class="dropdown-toggle"':'')+' href="javascript:;">'):('<a '+(isAjax?'class="ajaxify"':'')+' href="'+(config.url?config.url:"javascript:;")+'">'))+
					((config.iconCls && config.iconCls != "")?('<i class="'+config.iconCls+'"></i>'):'')+
					(top?'<span class="title">':'')+
					config.text+
					(top?'</span>':'')+
					(hasChildren?'<span class="arrow"></span>':'')+
					(top==true?'<span class="selected"></span>':'')+
				'</a>'+
				(hasChildren?'<ul class="'+subCls+'  pull-left"></ul>':'')+
			'</li>'
		);
		el.appendTo(c);
		if(showSubOnLeftMenu && isTopMenu){
			el.data("children", config.children);
			if(config.url){
				el.data("url", config.url);
			}
		}
		if(config.expanded && hasChildren && !isTopMenu){
			onSidebarTopMenuClick($('a:first', el));
		}
		if(hasChildren){
			var subEl = $('ul.'+subCls, el);
			for(var i=0;i<config.children.length;i++){
				createMenuItem(config.children[i], subEl, isTopMenu);
			}
		}
		if(config.isActive == true){
			setActive(el, !isTopMenu);
		}
	};
	
	var createPageContainer = function(){
		var iframeHtml = (Frame.useIframe && Frame.useIframe == true)?'<iframe width="100%" id="content-iframe"  height="50%" scrolling="hidden" frameborder="0" hspace="0" vspace="0" leftmargin="0" rightmargin="0" topmargin="0" marginwidth="0"  name="content-iframe" src=""></iframe>':'';
		var el = $(
			'<div class="page-container">'+
				'<div class="page-content">'+
					'<div class="container-fluid">'+
						'<div class="page-content-body">'+
						iframeHtml+
			 			'</div>'+
					'</div>'+
				'</div>'+
			'</div>'
		);
		$(document.body).append(el);
		var content = $("div.page-content", el);
		createPortletConfig(content);
		//createColorPanel(content);
		return el;
	};
	
	var createPortletConfig = function(c){
		var el = $(
			'<div id="portlet-config" class="modal hide">'+
				'<div class="modal-header">'+
					'<button data-dismiss="modal" class="close" type="button"></button>'+
					'<h3>Widget Settings</h3>'+
				'</div>'+
				'<div class="modal-body">'+
					'Widget settings form goes here'+
				'</div>'+
			'</div>'
		);
		el.appendTo(c);
	};
	
	var createColorPanel = function(c){
		var el = $(
			'<div class="color-panel hidden-phone">'+
				'<div class="color-mode-icons icon-color"></div>'+
				'<div class="color-mode-icons icon-color-close"></div>'+
				'<div class="color-mode">'+
					'<p>THEME COLOR</p>'+
					'<ul class="inline">'+
						'<li class="color-black current color-default" data-style="default"></li>'+
						'<li class="color-blue" data-style="blue"></li>'+
						'<li class="color-brown" data-style="brown"></li>'+
						'<li class="color-purple" data-style="purple"></li>'+
						'<li class="color-grey" data-style="grey"></li>'+
						'<li class="color-white color-light" data-style="light"></li>'+
					'</ul>'+
					'<label>'+
						'<span>Layout</span>'+
						'<select class="layout-option m-wrap small">'+
							'<option value="fluid" selected>Fluid</option>'+
							'<option value="boxed">Boxed</option>'+
						'</select>'+
					'</label>'+
					'<label>'+
						'<span>Header</span>'+
						'<select class="header-option m-wrap small">'+
							'<option value="fixed" selected>Fixed</option>'+
							'<option value="default">Default</option>'+
						'</select>'+
					'</label>'+
					'<label>'+
						'<span>Sidebar</span>'+
						'<select class="sidebar-option m-wrap small">'+
							'<option value="fixed">Fixed</option>'+
							'<option value="default" selected>Default</option>'+
						'</select>'+
					'</label>'+
					'<label>'+
						'<span>Footer</span>'+
						'<select class="footer-option m-wrap small">'+
							'<option value="fixed">Fixed</option>'+
							'<option value="default" selected>Default</option>'+
						'</select>'+
					'</label>'+
				'</div>'+
			'</div>'
		);
		el.prependTo(c);
		handleTheme();
	};
	
	var createFooter = function(cpm){
		cpm = cpm?cpm:"杭州合众数据技术有限公司";
		/*var footerEl = $(
			'<div class="footer">'+
				'<div class="footer-inner">'+
					fbsj+
					' &copy; '+
					cpm+
					'.'+
				'</div>'+
				'<div class="footer-tools">'+
					'<span class="go-top">'+
					'<i class="icon-angle-up"></i>'+
					'</span>'+
				'</div>'+
			'</div>'
		);*/
		var footerEl = $('  <div class="page-footer">'+
        '<div class="container"> '+
        fbsj+
				' &copy; '+
				cpm+
				'.'+
       ' </div>'+
   ' </div>'+
   ' <div class="scroll-to-top">'+
      '  <i class="icon-arrow-up"></i>'+
    '</div>');
		$(document.body).append(footerEl);
		return footerEl;
	};
	var handleGoTop = function () {
        /* set variables locally for increased performance */
        jQuery('.footer').on('click', '.go-top', function (e) {
            App.scrollTo();
            e.preventDefault();
        });
    };
    
    var handleSidebarAndContentHeight = function () {
        var content = $('.page-content');
        var sidebar = $('.page-sidebar');
        var body = $('body');
        var height;

        if (body.hasClass("page-footer-fixed") === true && body.hasClass("page-sidebar-fixed") === false) {
            var available_height = $(window).height() - $('.footer').height();
            content.attr('style', 'min-height:' + available_height + 'px !important');
        } else {
            if (body.hasClass('page-sidebar-fixed')) {
                height = _calculateFixedSidebarViewportHeight();
            } else {
                height = sidebar.height() + 20;
            }
            content.attr('style', 'min-height:' + height + 'px !important');
        }          
    };
    
    var setLayout = function(config){
    	config = $.extend({
    		layout: "fluid",          //boxed or fluid
    		header: "default",        //default or fixed
    		sidebar: "default",       //default or fixed
    		footer: "default",        //default or fixed
    		sidebarHoverable: true
    	}, config);
    	if (config.sidebar == "fixed" && config.header == "default") {
    		config.sidebar = 'default';
        }
    	if (config.layout === "boxed") {
            $("body").addClass("page-boxed");

            // set header
            $('.header > .navbar-inner > .container-fluid').removeClass("container-fluid").addClass("container");
            $('.header').after('<div class="container"></div>');

            // set content
            $('.page-container').appendTo('body > .container');

            // set footer
            if (config.footer === 'fixed' || config.sidebar === 'default') {
                $('.footer').html('<div class="container">'+$('.footer').html()+'</div>');
            } else {
                $('.footer').appendTo('body > .container');
            }            
            App.runResponsiveHandlers();
        }
        //header
        if (config.header === 'fixed') {
            $("body").addClass("page-header-fixed");
            $(".header").removeClass("navbar-static-top").addClass("navbar-fixed-top");
        } else {
            $("body").removeClass("page-header-fixed");
            $(".header").removeClass("navbar-fixed-top").addClass("navbar-static-top");
        }

        //sidebar
        if (config.sidebar === 'fixed') {
            $("body").addClass("page-sidebar-fixed");
            if(config.sidebarHoverable === true){
            	$("body").addClass("page-sidebar-hoverable");
            } else {
            	$("body").addClass("page-sidebar-closed");
            }
        } else {
            $("body").removeClass("page-sidebar-fixed");
        }

        //footer 
        if (config.footer === 'fixed') {
            $("body").addClass("page-footer-fixed");
        } else {
            $("body").removeClass("page-footer-fixed");
        }
        handleSidebarAndContentHeight(); // fix content height            
        handleFixedSidebar(); // reinitialize fixed sidebar
        handleFixedSidebarHoverable(); // reinitialize fixed sidebar hover effect
    };
    
    var handleTheme = function () {

        var panel = $('.color-panel');

        if ($('body').hasClass('page-boxed') == false) {
            $('.layout-option', panel).val("fluid");
        }
        
        $('.sidebar-option', panel).val("default");
        $('.header-option', panel).val("fixed");
        $('.footer-option', panel).val("default"); 

        //handle theme layout
        var resetLayout = function () {
            $("body").
                removeClass("page-boxed").
                removeClass("page-footer-fixed").
                removeClass("page-sidebar-fixed").
                removeClass("page-header-fixed");

            $('.header > .navbar-inner > .container').removeClass("container").addClass("container-fluid");

            if ($('.page-container').parent(".container").size() === 1) {
                $('.page-container').insertAfter('.header');
            } 

            if ($('.footer > .container').size() === 1) {                        
                $('.footer').html($('.footer > .container').html());                        
            } else if ($('.footer').parent(".container").size() === 1) {                        
                $('.footer').insertAfter('.page-container');
            }

            $('body > .container').remove(); 
        };

        var lastSelectedLayout = '';

        var setLayout2 = function () {

            var layoutOption = $('.layout-option', panel).val();
            var sidebarOption = $('.sidebar-option', panel).val();
            var headerOption = $('.header-option', panel).val();
            var footerOption = $('.footer-option', panel).val(); 

            if (sidebarOption == "fixed" && headerOption == "default") {
                alert('Default Header with Fixed Sidebar option is not supported. Proceed with Default Header with Default Sidebar.');
                $('.sidebar-option', panel).val("default");
                sidebarOption = 'default';
            }

            resetLayout(); // reset layout to default state
            
            setLayout({
            	layout: layoutOption,
        		header: sidebarOption,
        		sidebar: headerOption,
        		footer: footerOption
            });
        };

        // handle theme colors
        var setColor = function (color) {
            $('#style_color').attr("href", "assets/css/themes/" + color + ".css");
            $.cookie('style_color', color);                
        };

        $('.icon-color', panel).click(function () {
            $('.color-mode').show();
            $('.icon-color-close').show();
        });

        $('.icon-color-close', panel).click(function () {
            $('.color-mode').hide();
            $('.icon-color-close').hide();
        });

        $('li', panel).click(function () {
            var color = $(this).attr("data-style");
            setColor(color);
            $('.inline li', panel).removeClass("current");
            $(this).addClass("current");
        });

        $('.layout-option, .header-option, .sidebar-option, .footer-option', panel).change(setLayout2);
    };
    
    var handleFixedSidebar = function () {
        var menu = $('.page-sidebar-menu');

        if (menu.parent('.slimScrollDiv').size() === 1) { // destroy existing instance before updating the height
            menu.slimScroll({
                destroy: true
            });
            menu.removeAttr('style');
            $('.page-sidebar').removeAttr('style');            
        }

        if ($('.page-sidebar-fixed').size() === 0) {
            handleSidebarAndContentHeight();
            return;
        }

        if ($(window).width() >= 980) {
            var sidebarHeight = _calculateFixedSidebarViewportHeight();
            if($('body').hasClass('page-sidebar-hoverable')){
            	menu.slimScroll({
            		size: '7px',
            		color: '#a1b2bd',
            		opacity: .3,
            		position: App.isRTL() ? 'left' : ($('.page-sidebar-on-right').size() === 1 ? 'left' : 'right'),
            				height: sidebarHeight,
            				allowPageScroll: false,
            				disableFadeOut: false
            	});
            } else {
            	$('.page-sidebar > ul').css("height", sidebarHeight+"px");
            }
            handleSidebarAndContentHeight();
        }
    };
    
    var handleFixedSidebarHoverable = function () {
        if ($('body').hasClass('page-sidebar-fixed') === false || $('body').hasClass('page-sidebar-hoverable') === false) {
            return;
        }

        $('.page-sidebar').off('mouseenter').on('mouseenter', function () {
            var body = $('body');

            if ((body.hasClass('page-sidebar-closed') === false || body.hasClass('page-sidebar-fixed') === false) || $(this).hasClass('page-sidebar-hovering')) {
                return;
            }

            body.removeClass('page-sidebar-closed').addClass('page-sidebar-hover-on');
            $(this).addClass('page-sidebar-hovering');                
            $(this).animate({
                width: sidebarWidth
            }, 400, '', function () {
                $(this).removeClass('page-sidebar-hovering');
            });
        });

        $('.page-sidebar').off('mouseleave').on('mouseleave', function () {
            var body = $('body');

            if ((body.hasClass('page-sidebar-hover-on') === false || body.hasClass('page-sidebar-fixed') === false) || $(this).hasClass('page-sidebar-hovering')) {
                return;
            }

            $(this).addClass('page-sidebar-hovering');
            $(this).animate({
                width: sidebarCollapsedWidth
            }, 400, '', function () {
                $('body').addClass('page-sidebar-closed').removeClass('page-sidebar-hover-on');
                $(this).removeClass('page-sidebar-hovering');
            });
        });
    };
    
    var _calculateFixedSidebarViewportHeight = function () {
        var sidebarHeight = $(window).height() - $('.header').height() + 1;
        if ($('body').hasClass("page-footer-fixed")) {
            sidebarHeight = sidebarHeight - 34;//$('.footer').innerHeight()在页面初始化时，会多3个像素，之后就正常，不知道是什么情况
        }

        return sidebarHeight; 
    };
    
    var handleDesktopTabletContents = function () {
        // loops all page elements with "responsive" class and applies classes for tablet mode
        // For metornic  1280px or less set as tablet mode to display the content properly
        if ($(window).width() <= 1280 || $('body').hasClass('page-boxed')) {
            $(".responsive").each(function () {
                var forTablet = $(this).attr('data-tablet');
                var forDesktop = $(this).attr('data-desktop');
                if (forTablet) {
                    $(this).removeClass(forDesktop);
                    $(this).addClass(forTablet);
                }
            });
        }

        // loops all page elements with "responsive" class and applied classes for desktop mode
        // For metornic  higher 1280px set as desktop mode to display the content properly
        if ($(window).width() > 1280 && $('body').hasClass('page-boxed') === false) {
            $(".responsive").each(function () {
                var forTablet = $(this).attr('data-tablet');
                var forDesktop = $(this).attr('data-desktop');
                if (forTablet) {
                    $(this).removeClass(forTablet);
                    $(this).addClass(forDesktop);
                }
            });
        }
    };
    
    var handleSidebarState = function () {
        // remove sidebar toggler if window width smaller than 900(for table and phone mode)
        if ($(window).width() < 980) {
            $('body').removeClass("page-sidebar-closed");
        }
    };
    
    var handleResponsiveOnResize = function () {
        var resize;
        if (App.isIE8()) {
            var currheight;
            $(window).resize(function() {
                if(currheight == document.documentElement.clientHeight) {
                    return; //quite event since only body resized not window.
                }                
                if (resize) {
                    clearTimeout(resize);
                }   
                resize = setTimeout(function() {
                    handleResponsive();    
                }, 50); // wait 50ms until window resize finishes.                
                currheight = document.documentElement.clientHeight; // store last body client height
            });
        } else {
            $(window).resize(function() {
                if (resize) {
                    clearTimeout(resize);
                }   
                resize = setTimeout(function() {
                    handleResponsive();    
                }, 50); // wait 50ms until window resize finishes.
            });
        }   
    };
    
    var handleResponsive = function () {
        App.handleTooltips();
        handleSidebarState();
        handleDesktopTabletContents();
        handleSidebarAndContentHeight();
        App.handleChoosenSelect();
        handleFixedSidebar();
        App.runResponsiveHandlers();
    };

    return {
    	useIframe: true,
        //main function to initiate the module
        /**
         * @param options
         * @returns
         */
        init: function (options) {
        	options = $.extend(true, {
        		topMenu: {
        			items: [],
        			showSearchItem: false,
        			searchFn: function(){}
        		},
        		leftMenu: {
        			items: [],
        			showSearchItem: false,
        			searchFn: function(){}
        		}
        	}, options);
        	var hasLeftMenu = options.leftMenu && $.isArray(options.leftMenu.items) && options.leftMenu.items.length > 0;
        	var hasTopMenu = options.topMenu && $.isArray(options.topMenu.items) && options.topMenu.items.length > 0;
        	Frame.setPageFullWidth(!hasLeftMenu);
        	var el =createHeader();
        	$(document.body).append(el);
        	var hc =$('div.page-header-menu div', el);
        	var user = $('div.top-menu',el);
        /*	$(
        		'<a class="brand" href="javascript:;" style="cursor: default;">'+
        			'<img src="'+$.CurrentBasePath+'lib/common/images/logo.png" alt="logo"/>&nbsp;'+
        		'</a>'
			).appendTo(hc);*/
        	var showSearchItem = (options.topMenu.showSearchItem == true || options.topMenu.showSearchItem == 'true');
    		if(showSearchItem){
    			createrSearchRight(options.topMenu,hc);
    		}
        	if(hasTopMenu){
        		createHorizontalNav(options.topMenu, hc);
        		handleHorizontalMenu();
        	}
        	
        	
        	createTopBar({items:options.rightMenu||[],userInfo:options.userInfo},user);
        	
        	var container = createPageContainer();
        	if(hasLeftMenu){
        		/*createVerticalNav(options.leftMenu, container);
        		handleVerticalMenu();*/
        		defaultLeftMenu = options.leftMenu;
        	}
        	
        	createFooter(options.footerText);
        	handleGoTop();
        	
        	var layoutConfig = {};
        	if(options.layoutConfig && options.layoutConfig.layout){
        		layoutConfig.layout = options.layoutConfig.layout;
        	}
        	if(options.layoutConfig && options.layoutConfig.sidebarHoverable == false){
        		layoutConfig.sidebarHoverable = false;
        	}
        	if(options.layoutConfig && options.layoutConfig.position){
        		layoutConfig.header = options.layoutConfig.header || options.layoutConfig.position;
        		layoutConfig.sidebar = options.layoutConfig.sidebar || options.layoutConfig.position;
        		layoutConfig.footer = options.layoutConfig.footer || options.layoutConfig.position;
        	}
        	setLayout(layoutConfig);
        	if(options.title){
        		document.title = options.title;
        	}
        	handleResponsiveOnResize();
        },
        setActive: function(name){
        	setActive($("#menu_"+name));
        },
        selectMenu: function(name){
        	//console.log($("#menu_"+name+" a:first"));
        	$("#menu_"+name+" a:first").click();
        },
        setPageFullWidth: function(isFull){
        	isFull = (isFull == true)?isFull:false;
        	if(isFull){
        		$(document.body).addClass("page-full-width");
        	} else {
        		$(document.body).removeClass("page-full-width");
        	}
        },
        loadHtml: function(url, c){
        	var pageContentBody = c?c:$('.page-content .page-content-body');
        	Frame.ajax({
            	url: url,
            	type: "POST",
            	//cache: false,
            	data: {},
            	dataType: "html",
            	success: function(res){
                    pageContentBody.html(res);
                    handleSidebarAndContentHeight(); // fix content height
                    App.initUniform(); // initialize uniform elements
            	},
            	error: function(){
            		pageContentBody.html("<div>加载数据失败！</div>");
            	}
            });
        },
        loadIframe: function(url, c){
        	var pageContentBody = c?c:$('.page-content .page-content-body iframe');
            pageContentBody.attr("src", url);
            var adjustIframeHeight = function(){
            	var subBody = pageContentBody[0].contentDocument.body;
            	if(subBody){
            		var iframeHeight = subBody.offsetHeight;
            		var contentHeight = parseInt($(".page-content").css("min-height"));
            		if(contentHeight > iframeHeight){
            			iframeHeight = contentHeight;
            		}
            		pageContentBody.attr("height", iframeHeight+"px");
            		return subBody;
            	}
            };
            pageContentBody[0].onload = function(){
            	App.scrollTop();
            	
            	var subBody = adjustIframeHeight();
            	if(subBody){
            		$(subBody).bind("click.adjust", function(){
            			$(document).click();
            		});
            		$(pageContentBody[0].contentDocument).bind("click.adjust", function(){
            			$(document).click();
            		});
            	}
            };
            if(!adjustIframeHeightTimer){
            	adjustIframeHeightTimer = setInterval(adjustIframeHeight, 200);
            }
        },
        ajaxLoadContent: function(url, c){
            if(Frame.useIframe && Frame.useIframe == true){
            	Frame.loadIframe(url, c);
            } else {
            	Frame.loadHtml(url, c);
            }
        },
        ajax: function(options, miskBody){
        	miskBody = miskBody?miskBody:$('.page-content');
            App.blockUI(miskBody, false);
        	options = $.extend({
        		complete: function(){
            		App.unblockUI(miskBody);
            		$("div.blockUI").remove();
            	}
        	}, options);
        	$.ajax(options);
        },
        ajaxModal: function(options){
        	if("string" == typeof(options)) options = {url: options};
        	options = $.extend({}, options);
        	var mid = options.id?options.id:"draggable";
        	var mc = $("#"+mid);
        	if(!mc[0]){
        		mc = $('<div id="'+mid+'" class="modal fade draggable-modal" tabindex="-1" role="basic" aria-hidden="true">');
        		$(document.body).append(mc);
        		mc = $("#"+mid);
        	} else {
        		mc.html("");
        	}
        	/*if(!mc[0]){
        		mc = $('<div id="'+mid+'" class="modal hide fade" tabindex="-1" role="dialog" aria-hidden="true">');
        		$(document.body).append(mc);
        		mc = $("#"+mid);
        	} else {
        		mc.html("");
        	}*/
        	Frame.ajax({
            	url: options.url,
            	type: "POST",
            	data: {},
            	dataType: "html",
            	success: function(res){
            		mc.html(res);
            		if($.isNumeric(options.width)){
            			mc.find(".modal-body").css("height", parseInt(options.height)).css("overflow-y","scroll");
                		mc.find(".modal-dialog").css("width", parseInt(options.width));
                	}
            		mc.modal();
            		if(options.callback && $.isFunction(options.callback)){
            			var modal = mc.data('bs.modal');
            			console.log(modal)
            			options.callback(modal);
            		}
            	}
            });
        },
        alert: function(title, msg, timeout){
        	$.gritter.add({
                // (string | mandatory) the heading of the notification
                title: title || '提示',
                // (string | mandatory) the text inside the notification
                text: msg || '消息提醒。',
                // (bool | optional) if you want it to fade out on its own or just sit there
                sticky: false,
                // (int | optional) the time you want it to be alive for before fading out
                time: timeout || 2000
            });
        }
    };

}();