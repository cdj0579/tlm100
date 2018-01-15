//var basePath = "./";
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
		
		$(".mui-icon").click(function(){
			history.back();
		});
		
		var  reg = /^\d$/;
		var _time = $("#time");
		var interval = setInterval(function() {
            var newTime = Date.parse("2017/1/1,0:"+_time.text());
            newTime = newTime+1000;
            var d = new Date();
            d.setTime(newTime);
            var m = d.getMinutes();
            m = reg.test(m) ? "0" + m + ":" : m + ":"
            var s = d.getSeconds();
            s = reg.test(s) ? "0" + s : s;
            var str = m + s
            _time.text(str)  ;
            if(str == "10:00"){
            	clearInterval(interval);
            	setAlertMsg("测试时间到，自动提交结果...");
            	var _prevwt = $("#wt"+currentNum);
    			recordResult( _prevwt );
            	testOverSumbit();
            }
        }, 1000);
		
		
		
		function zhuguanBtnClick(parentId){
			var Btns = $(parentId).find(".btn-sm");
			Btns.click(function(){
				var _this = $(this);
				_this.toggleClass("btn-info");
				var _v = 0 ; 
				if(_this.hasClass("btn-info")){
					_v = _this.attr("v");
				}
				_this.siblings("button").removeClass("btn-info");
				_this.siblings("input").val(_v);
			});
		}
		zhuguanBtnClick("#wt1");
		
		
		var loadNum = 1; //当前加载的题号
		var currentNum = 1; //当前显示的题号
		//var totolNum =3; //习题总数； 在调用页面赋值了
		var answerArray = new Array(totolNum); //答案数值
		var zhuguanArray = new Array(totolNum); //主观数值
		
		console.info(answerArray.join(";"));
		var nextBtn =$(".wt_footer button[name='next']");
		var prevBtn =$(".wt_footer button[name='prev']");
		
		function showNext( _prevwt ){
			var _nextwt = $("#wt"+currentNum);
			_prevwt.hide();
			_nextwt.show();
		}
		
		function recordResult(currentwt){
			zhuguanArray[currentNum-1] = currentwt.find(":hidden").val();
			answerArray[currentNum-1] = currentwt.find(':radio:checked').val();
		}
		
		nextBtn.click(function(){
			prevBtn.show();
			var _prevwt = $("#wt"+currentNum);
			recordResult( _prevwt );
			if(currentNum < totolNum){
				currentNum++;
				if(currentNum > loadNum){
					console.info("加载第"+loadNum);
					getOneCstInfo(loadNum, _prevwt);
					loadNum = currentNum;
				}else{
					showNext( _prevwt );
				}
				if(currentNum == totolNum){
					nextBtn.text("提交测试")
				}
			}else if(currentNum == totolNum){
				//提交测试结果；
				setAlertMsg("完成测试，提交结果...");
				testOverSumbit();
			}
			
		});
		prevBtn.click(function(){
			var _nextwt = $("#wt"+currentNum);
			nextBtn.text("下一题");
			currentNum--;
			var _prevwt = $("#wt"+currentNum);
			_nextwt.hide();
			_prevwt.show();
			if(currentNum == 1){
				prevBtn.hide();
			}
			
		});
		
		function setAlertMsg( msg ){
			$.ajaxSetup({
			   beforeSend: function(xhr){
				   if(this.showBlockUI != false){
					   App.blockUI({ message: msg });
				   }
			   }
			});
		}
		
		function testOverSumbit(){
			var params = {};
			params.time = _time.text();
			params.answerArray = answerArray.join(";");
			params.zhuguanArray = zhuguanArray.join(";");
			App.post(App.remoteUrlPre+"testOverSave", params ,function(result){
				location.href = App.remoteUrlPre +"test_result";
			});
		}
	
		function getOneCstInfo( seq , _prevwt ){
			setAlertMsg("正在获取下一题...");
			App.post(App.remoteUrlPre+"getOneCst",{seq:seq},function(result){
				var wtId = 'wt'+ currentNum ;
				var html ='<div  id="'+ wtId +'" style="display:none">'
					+'<div class="row">'
					+'	<div class="col-xs-8 col-md-8">'
					+' 		<p class="text-justify "></p>'
					+'	</div>'
					+'	<div style="margin-top: 40px;" class="col-xs-4 col-md-4 ">'
					+'		<input type="hidden" name="zhuguan" value="0">'
					+'		<button type="button" v="1" class="btn-sm btn-block uppercase" data-toggle="button">不&nbsp;&nbsp;&nbsp;&nbsp;会 </button>'   
					+'		<button type="button" v="2" class="btn-sm btn-block uppercase" data-toggle="button">不确定 </button>'
					+'	</div>'
					+'</div>'
					+'<div class="wt_answer">'
					+'</div>'
				+'</div>';
				var _html = $(html);
				_html.find(".text-justify").html('<label>'+ currentNum +'：</label>'+result.name );
				var _ans =  _html.find(".wt_answer");
				console.info(result.ansList);
				$.each(result.ansList, function(index, obj, array) {
					console.info(obj);
					var oneAns = '<div class="row answer-height">'
					        	+'	<label for="'+ obj.option + currentNum+'">'
					        	+'		<input type="radio" id="'+ obj.option + currentNum+'" value="'+ obj.option +'" name="ans'+ currentNum +'">'
					        	+		obj.option + ' : '+ obj.name 
					        	+'	</label>'
					        	+'</div>';
					 _ans.append(oneAns);
				});
				_prevwt.after( _html );
				zhuguanBtnClick("#"+ wtId );
				showNext( _prevwt );
	     	});
		}
		
	});
	

});