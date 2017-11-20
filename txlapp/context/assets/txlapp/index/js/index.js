//var basePath = "../../";
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
	
	require(['domready!', 'app'], function (doc, App){
		var tabId = "#txl";
		var $returnBtn =$(".mui-bar>button");
		$returnBtn.click(function(){
			if(tabId != "#txl" && $(".bz_content").is(':visible')){
				$("#card").show();
			}else{
				$("#card").hide();
				$(tabId).addClass("active");
				$returnBtn.hide();
			}
			$(".bz_content").hide();
			
		})
		$('a[data-toggle="tab"]').on('show.bs.tab', function (e) {
		 /* e.target // 激活的标签页
		  e.relatedTarget // 前一个激活的标签页*/
			$(e.relatedTarget).find("i").removeClass("fa-sort-desc");
			$(e.target).find("i").addClass("fa-sort-desc");
			tabId=$(e.target).attr("href");
			getTableInfo();
		 	$(".bz_content").hide();
			$("#card").hide();
			$returnBtn.hide();
		 	
		});
		
		
		function getTableInfo(){
			if(tabId != "#txl"){
				var param = {"isGx":"gz"};
				var url = App.remoteUrlPre + "getgGxORGzLianxiren";
				if(tabId == "#ygx"){
					param.isGx = "gx";
				}
				$.post(url, param, function(data, textStatus, jqXHR){
		     		if(data.success ==true || data.success == "true"){
		     			var rows = data.rows;
		     		 	createtable(rows,tabId,true);
		     		 	setRowEvent();
		     		}
		     	},"json");
			}
		}
		
		function telPhone(){
			$(".txl>div.tp").click(function(){
				var phone = $(this).prev("input").val(); 
				console.info(phone);
				location.href = 'tel://' + phone;
			});
		}
		
		var zwFlag = true;
		function setEvent(){
			telPhone();
			$(".anniu").click(function(){
				var _$this = $(this);
				var _thisName = _$this.attr("name");
				
				zwFlag = _thisName == "guanzhu";
				
				var tab = _$this.parent().siblings(".div_neirong");
				console.info(tab);
				var _name = tab.find("p[class='name']").text();
				var _beizhu = "";
				if(zwFlag){
					_beizhu = tab.find("p.beizhu").text();
				}else{
					_beizhu = tab.find("p.gxinfo").text();
				}
				
				$(".tab-pane.active").removeClass("active");
				$("#card").hide();
				
				
				var _$bz = $(".bz_content");
				_$bz.find(".btn-sub").text("提交"+(zwFlag?"关注":"共享"));
				_$bz.find("textarea").val(_beizhu);
				_$bz.find("label").text("请对“"+_name+"”填写"+(zwFlag?"备注信息":"共享信息"));
				_$bz.show();
				$returnBtn.show();
			});
		}
		setEvent();
		
		function setRowEvent(){
			$("active>.row:gt(0)").click(function(){
				var _$this = $(this);
				_$this.parent().removeClass("active");
				$returnBtn.show();
				
				var params = {};
				params.flag = "gz";
				if(tabId == "#ygx"){
					params.flag = "gx";
				}
				var rowId = _$this.find("input").val();
				var datainfo =  tableStore[rowId];
				console.info(datainfo);
				//getOneStuInfo("#card",params);
				createCard(datainfo,"#card");
				
			});
		}
		var tableStore = [];
		function createtable(arrayData,parentId,isclear){
			var _$parent = $(parentId);
			if(isclear){
				_$parent.empty();
				tableStore = [] ;
				var thead = '<div class="row "><div class="th">姓名</div>'
						+'<div class="th">学校</div><div class="th">班级</div></div>'
				_$parent.append(thead);
			};
			if(arrayData.length == 0 ){
				_$parent.append('<div class="emptyMsg"> 当前无相应数据 </div>');
			}else {
				tableStore = arrayData;
				$.each(arrayData,function(i,data){
					var _$row = $('<div class="row"><div></div></div>');
					if(i%2 == 0){
						_$row.addClass("odd");
					}else{
						_$row.addClass("even");
					}
					var _$child = _$row.children();
					var _iClass = "fa-user";
					if(data.sex == "1"){
						_iClass = "fa-female"
					}
					_$child.append('<div class="td"><i class="fa '+ _iClass +'"></i>'+ data.name +'</div>');
					_$child.append('<div class="td">' + data.xuexiao + '</div>');
					_$child.append('<div class="td">' + data.banji + '</div>');
					_$child.append('<div class="clearfix"></div>');
					_$row.append('<input type="hidden" value="' + i + '" />')
					if(!$.isEmptyObject(data.gzbeizhu) ){
						_$row.append('<div class="p-info">备注：' + data.gzbeizhu + '</div>');
					}
					if(!$.isEmptyObject(data.gxbeizhu) ){
						_$row.append('<div class="p-info">共享信息：' + data.gxbeizhu + '</div>');
					}
					
					_$parent.append(_$row);
				});
			}
			
		}
		
		function createCard(data ,parentId){
			var _gzStr = tabId == "#txl" ?'关注':'修改备注信息'
			var _gxStr = tabId != "#ygx" ?'共享':'修改共享信息'
			var html = '<div class="div_ds"><div>'
			  			+'<button name="guanzhu" class="btn btn-lg anniu">'+_gzStr+'</button>'
			  			+'<button name="gongxing" class="btn btn-lg anniu">'+_gxStr+'</button>'
			  			+'</div>'
			  			+'<div class="div_neirong">'
				  		+	'<div class="txl">'
				  		+		'<input type="hidden" name="id" value="'+data.fpId+'" />'
				  		+		'<input type="hidden" name="lxrId" value="'+data.lxrId+'" />'
				  		+		'<input type="hidden" name="phone" value="'+data.phone+'" />'
				  		+		'<div class="tp"></div>'
				  		+		'<p class="name">'+data.name+'</p>'
				  		+		'<p>'+data.quyu+'</p>'
				  		+		'<p>'+data.xuexiao+'</p>'
				  		+		'<p>'+data.banji+'</p>'
				  		+	'</div>'
			  			+'</div>'
			  		+'</div>';
			var _$html =  $(html);
			if( !$.isEmptyObject(data.gzbeizhu) ){
				_$html.find(".div_neirong").append('<div>备注：</div>').append('<p class="beizhu">'+data.gzbeizhu+'</p>');
			}
			if( !$.isEmptyObject(data.gxbeizhu) ){
				_$html.find(".div_neirong").append('<div>共享信息：</div>').append('<p class="gxinfo">'+data.gxbeizhu+'</p>');
			}
			
			var _$parent = $(parentId).empty() ;
			_$parent.append(_$html).show();
			setEvent();
		}
		
		function getOneStuInfo(parentId,param){
			var url = App.remoteUrlPre + "getOneLianxiren";
			$.post(url, param, function(data, textStatus, jqXHR){
	     		App.handlerAjaxJson(data, null,"获取数据失败！");
	     		if(data.success ==true || data.success == "true"){
	     			if( data.over == true || data.success == "true" ){
	     				var _$parent = $(parentId).empty();
	     				_$parent.siblings().hide();
	     				var _overMsg  = "当前暂无分配的联系人信息"; 
	     				if(fpId  != 0 ){
	     				 	_overMsg  = "分配的联系人信息已处理完"; 
	     				}
	     				_$parent.append('<div class="emptyMsg"> ' + _overMsg + ' </div>');
	     				
	     			}else{
	     				createCard(data,parentId);
	     				if(fpId  == 0 ){
	     				 	$(parentId).siblings().show();
	     				}
	     			}
	     		
	     		}
	     	},"json");
		}
		var fpId = 0;
		getOneStuInfo("#txl>div",{fpId:fpId});
		$("button[name='next']").click(function(){
			fpId = $("input[name='id']").val();
			getOneStuInfo("#txl>div",{fpId:fpId});
		});
		
		$(".btn-sub").click(function(){
			var info_v = $("textarea[name='info']").val();
			if( info_v != ""){
				var param ={};
				param.beizhu = info_v;
				param.type = zwFlag?"gz":"gx";
				var lxrId = $("input[name='lxrId']").val();
				param.lxrId = lxrId;
				var url = App.remoteUrlPre + "saveBeizhu";
				$.post(url, param, function(data, textStatus, jqXHR){
		     		App.handlerAjaxJson(data, function(){
		     			var alert = App.getAlert({positionClass:"toast-top-center"});
		     			alert.success("保存数据成功", "提示");
		     			$returnBtn.click();
		     		},"保存数据失败！");
		     	},"json");
			/*}else{
				var alert = App.getAlert({positionClass:"toast-top-center"});
     			alert.success("保存数据成功", "提示");
     			$returnBtn.click();*/
			}
			
		});
		
	
	
	});
	
});