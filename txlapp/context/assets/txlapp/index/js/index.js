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
			var _$tbz = $("#tableBeizhu");
			var _$bz = $(".bz_content");
			if(tabId != "#txl" && (_$bz.is(':visible') || _$tbz.is(':visible') )){
				$("#card").show();
			}else{
				$("#card").hide();
				$(tabId).addClass("active");
				$returnBtn.hide();
			}
			_$bz.hide();
			_$tbz.hide();
			
		});
		$('a[data-toggle="tab"]').on('show.bs.tab', function (e) {
		 /* e.target // 激活的标签页
		  e.relatedTarget // 前一个激活的标签页*/
			$(e.relatedTarget).find("i").removeClass("fa-sort-desc");
			$(e.target).find("i").addClass("fa-sort-desc");
			tabId=$(e.target).attr("href");
			getTableInfo();
		 	$(".bz_content").hide();
			$("#card").hide();
			$("#tableBeizhu").hide();
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
		
		function telPhone(parentId){
			$(parentId +" .txl>div.tp").click(function(){
				var phone = $(this).prev("input").val(); 
//				console.info(phone);
				if(tabId == "#txl"){
					var _id = $(this).siblings("input[name='id']").val();
					var url =  App.remoteUrlPre + "updateCall";
					$.ajax({url:url, type: "post",data:{fpId : _id},
						beforeSend: function () {
						  	
						}
					});
					$("button[name='next']").removeAttr("disabled");
				}
				location.href = 'tel:' + phone;
			});
		}
		
		var zwFlag = true;
		var isBeizhu =  false;
		var _gzBei = {};
		function setEvent( parentId ){
			telPhone(parentId);
			$(parentId+" .anniu").click(function(){
				var _$this = $(this);
				var _thisName = _$this.attr("name");
				
				zwFlag = _thisName == "guanzhu";
				isBeizhu = _thisName == "beizhu";
				var tab = _$this.parent().siblings(".div_neirong");
				var _name = tab.find("p[class='name']").text();
				var _beizhu = "";
				if(zwFlag){
					_beizhu = tab.find("p.beizhu").text();
				}else{
					_beizhu = tab.find("p.gxinfo").text();
				}
				
				$(".tab-pane.active").removeClass("active");
				$("#card").hide();
				
				if(zwFlag || isBeizhu ){
					var _$bz = $("#tableBeizhu");
					_$bz.find("label").text("请对“"+_name+"”填写备注信息");
					_$bz.find(".btn-sub").text("提交"+(zwFlag?"关注":"备注"));
					var _txt = _$bz.find(":text");
					 for (var i = 0; i < _txt.length; i++) {
			        	var _obj = _txt.eq(i);
			        	var name = _obj.attr("name");
			        	var _v = "";
			        	if(_gzBei[name] != undefined ){
			        	 	_v = _gzBei[name];
			        	}
			            _obj.val( _v ); 
			        }
			        var _input = _$bz.find(":checkbox");
			        for (var i = 0; i < _input.length; i++) {
			        	var _obj = _input.eq(i);
			        	var name = _obj.attr("name");
			         	if(_gzBei[name] != undefined && _gzBei[name] == "1" ){
			          		_obj.attr("checked",'true');
			          	}else{
			           		_obj.removeAttr("checked");
			          	}
			        }
					var info_v = "";
					if(_gzBei["info"] != undefined ){
						info_v = _gzBei["info"] ;
					}
					_$bz.find("textarea[name='info']").val(info_v);
					_$bz.show();
				}else{
					var _$bz = $(".bz_content");
					_$bz.find(".btn-sub").text("提交共享");
					_$bz.find("textarea").val(_beizhu);
					_$bz.find("label").text("请对“"+_name+"”填写共享信息");
					_$bz.show();
				}
				$returnBtn.show();
			});
		}
		
		var tableStore = [];
		var _currentRowId = -1;
		function setRowEvent(){
			$(".active>.row:gt(0)").click(function(){
				var _$this = $(this);
				_$this.parent().removeClass("active");
				$returnBtn.show();
				
				var params = {};
				params.flag = "gz";
				if(tabId == "#ygx"){
					params.flag = "gx";
				}
				var rowId = _$this.find("input").val();
				_currentRowId = rowId;
				var datainfo =  tableStore[rowId];
				_cardDatas = 
//				console.info(datainfo);
				//getOneStuInfo("#card",params);
				createCard(datainfo,"#card");
				
			});
		}
		
		function createtable(arrayData,parentId,isclear){
			var _$parent = $(parentId);
			if(isclear){
				_$parent.empty();
				tableStore = [] ;
				_currentRowId = -1;
				var thead = '<div class="row "><div class="th">姓名</div>'
						+'<div class="th">学校</div><div class="th">班级</div></div>';
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
						_iClass = "fa-female";
					}
					_$child.append('<div class="td"><i class="fa '+ _iClass +'"></i>'+ data.name +'</div>');
					_$child.append('<div class="td">' + setValue(data.xuexiao) + '</div>');
					_$child.append('<div class="td">' + setValue(data.banji) + '</div>');
					_$child.append('<div class="clearfix"></div>');
					_$row.append('<input type="hidden" value="' + i + '" />');
					/*if(!$.isEmptyObject(data.gzbeizhu) ){
						_$row.append('<div class="p-info">备注：' + data.gzbeizhu + '</div>');
					}*/
					if(!$.isEmptyObject(data.gxbeizhu) ){
						_$row.append('<div class="p-info">共享信息：' + data.gxbeizhu + '</div>');
					}
					
					_$parent.append(_$row);
				});
			}
		}
		function setValue(value){
			if($.isEmptyObject(value)){
				return '&nbsp';
			}else{
				return value;
			}
		}
		function setOk(value){
			if(value == 1){
				return '<i class="fa fa-check"></i>';
			}else{
				return '&nbsp';
			}
		}
		function bzTableHtml(_bz ){
			return '<table class="table table-bordered">'
					+'<tr><th width="40px">科目</th><th>数学</th><th>科学</th><th>英语</th><th>语文</th><th>社会</th></tr>'
					+'<tr><th>分数</td><td>'+_bz.sx+'</td><td>'+_bz.kx+'</td><td>'+_bz.yy+'</td><td>'+_bz.yw+'</td><td>'+_bz.sh+'</td></tr>'
					+'<tr><th>目标</th><td colspan="5">'+_bz.mb+'</td></tr>'
					+'<tr><th rowspan="2">辅导情况</th><th>班课</th><th>教师</th><th>家教</th><th>1对1</th><th>排斥</th></tr>'
					+'<tr><td>'+setOk(_bz.bk)+'</td><td>'+setOk(_bz.js)+'</td><td>'+setOk(_bz.jj)+'</td><td>'+setOk(_bz.one)+'</td><td>'+setOk(_bz.pc)+'</tr>'
					+'<tr><th>距离</th><td colspan="5">'+_bz.jl+'</td></tr>'
					+'<tr><td colspan="6">'+_bz.info+'</td></tr>'
				+'</table>';
		}
		function reloadShowBz( _$P ){
			var _$bz =_$P.find(".card-beizhu");
			if(_$bz.text() != "" ){
				_$P.find(".table-div").empty().append(bzTableHtml( _gzBei ));
			}else{
				var _table = '<div class="card-beizhu">备注：</div><div class="table-div" style="display: none;" >'+bzTableHtml( _gzBei )+'</div>';
				_$P.find(".txl").after($(_table));
				_$P.find('.card-beizhu').click(function(){
					$(this).siblings(".table-div").slideToggle(500);
				});
			}
			
		}
		function reloadShowGx( _$P , gxInfo ){
			var _$gx =_$P.find(".gxinfo");
			if(_$gx.text() != "" ){
				_$gx.text(gxInfo);
			}else{
				_$P.find(".div_neirong").append('<div>共享信息：</div>').append('<p class="gxinfo">'+gxInfo+'</p>');
			}
		}
		
		function createCard(data ,parentId){
			var _gzStr = tabId == "#txl" ?'关注':'修改备注信息';
			var _gxStr = tabId != "#ygx" ?'共享':'修改共享信息';
			var _bzStr = tabId == "#txl" ?'备注':'修改备注信息';
			var html = '<div class="alert alert-success myAlert"></div><div class="div_ds"><div>'
			  			+(tabId != "#ygz"?'<button name="beizhu" class="btn btn-lg anniu">'+ _bzStr +'</button>':'')
			  			+(tabId != "#ygx"?'<button name="guanzhu" class="btn btn-lg anniu">'+_gzStr+'</button>':'')
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
			_gzBei = {} ;
			if( !$.isEmptyObject(data.gzbeizhu) && data.gzbeizhu.indexOf("{")=='0' ){
				var _bz = eval("("+data.gzbeizhu+")");
				_gzBei = _bz;
				reloadShowBz( _$html );
				/*var _table = '<div class="table-div" style="display: none;" >'+createGzbeizhu(_bz)+'</div>';
				_$html.find(".div_neirong").append('<div class="card-beizhu">备注：</div>').append(_table);*/
			}
			if( !$.isEmptyObject(data.gxbeizhu) ){
				reloadShowGx( _$html, data.gxbeizhu );
				/*_$html.find(".div_neirong").append('<div>共享信息：</div>').append('<p class="gxinfo">'+data.gxbeizhu+'</p>');*/
			}
			
			var _$parent = $(parentId).empty() ;
			_$parent.append(_$html).show();
			setEvent(parentId);
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
			$(this).attr("disabled","true");
			fpId = $("input[name='id']").val();
			getOneStuInfo("#txl>div",{fpId:fpId});
		}).attr("disabled","true"); 
		
		$(".bz_content .btn-sub").click(function(){
			var info_v = $(this).siblings().find("textarea[name='info']").val();
			if( info_v != ""){
				var param ={};
				param.beizhu = info_v;
				param.type = zwFlag?"gz":"gx";
				if(tabId == "#txl"){
					param.lxrId =  $("#txl input[name='lxrId']").val();
				}else{
					param.lxrId =  $("#card input[name='lxrId']").val();
				}
				saveBeizhu(param,"恭喜你，我们赢取了阶段性战果，继续加油！");
			}
			
		});
		function showPopover(target, msg) {
		  	target.text(msg).slideDown("slow");
		 	//2秒后消失提示框
		  	var id = setTimeout(
		    	function () {
			      	target.slideUp("slow");
		    	}, 2000
		  	);
		}

		function saveBeizhu(param , msg){
			var url = App.remoteUrlPre + "saveBeizhu";
				$.post(url, param, function(data, textStatus, jqXHR){
		     		App.handlerAjaxJson(data, function(){
		     			/*var alert = App.getAlert({positionClass:"toast-top-center"});
		     			alert.success("谢谢你的努力，我们的距离成功又近了一步！", "提示");*/
		     			if( param.type == "gz" ){
		     				if(tabId == "#txl"){
		     					reloadShowBz( $('#txl') );
		     				}else{
		     					reloadShowBz( $('#card') );
		     					tableStore[_currentRowId].gzbeizhu = param.beizhu;
		     				}
		     			}else{
		     				if(tabId == "#txl"){
		     					reloadShowGx( $('#txl') , param.beizhu);
		     				}else{
		     					reloadShowGx( $('#card') , param.beizhu);
		     					if(tabId == "#ygx"){
		     						tableStore[_currentRowId].gxbeizhu = param.beizhu;
		     						$('#ygx .row input[value="'+ _currentRowId +'"]').siblings('.p-info').text('共享信息：' + param.beizhu );
		     					}
		     				}
		     			}
		     			$returnBtn.click();
		     			showPopover($(".myAlert"),msg);
		     		},"保存数据失败！");
		     	},"json");
		}
		$("#tableBeizhu .btn-sub").click(function(){
			var data ={};
			var _t =$("#tableBeizhu");
			var _input = _t.find(":text");
	        for (var i = 0; i < _input.length; i++) {
	        	var _obj = _input.eq(i);
	        	var name = _obj.attr("name");
	            data[name]=_obj.val(); // 将文本框的值添加到数组中
	        }
	        var _input = _t.find(":checkbox");
	        for (var i = 0; i < _input.length; i++) {
	        	var _obj = _input.eq(i);
	        	var name = _obj.attr("name");
	            data[name]=_obj.is(':checked')?"1":"0"; // 将文本框的值添加到数组中
	        }
			var info_v = _t.find("textarea[name='info']").val();
			data["info"]= info_v;
			_gzBei = data;
			var dataString = JSON.stringify(data);
			//console.info(data);
			var param ={};
			param.beizhu = dataString;
			param.type = "gz";
			param.isBz = isBeizhu?"1":"0" ;
			if(tabId == "#txl"){
				param.lxrId =  $("#txl input[name='lxrId']").val();
			}else{
				param.lxrId =  $("#card input[name='lxrId']").val();
			}
			saveBeizhu(param,"谢谢你的努力，我们距离成功又近了一步！");
		});
	
	
	});
	
});