var basePath = "./";
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
		var interval = setInterval(function() {
			var _time = $("#time");
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
            }
        }, 1000);
		
		var num = 1;
		var totolNum =3; //习题总数；
		var nextBtn =$(".wt_footer button[name='next']");
		var prevBtn =$(".wt_footer button[name='prev']");
		nextBtn.click(function(){
			prevBtn.removeClass("hidden");
			var _prevwt = $("#wt_"+num);
			if(num < totolNum){
				num++;
				var _nextwt = $("#wt_"+num);
				_prevwt.addClass("hidden");
				_nextwt.removeClass("hidden");
				
			}
			if(num == totolNum){
				nextBtn.text("提交测试")
				//提交测试结果；
			}
			
		});
		prevBtn.click(function(){
			var _nextwt = $("#wt_"+num);
			if(num==totolNum){
				nextBtn.text("下一题");
			}
			num--;
			var _prevwt = $("#wt_"+num);
			_nextwt.addClass("hidden");
			_prevwt.removeClass("hidden");
			if(num == 1){
				prevBtn.addClass("hidden");
			}
		
			
		})
	});
	

});