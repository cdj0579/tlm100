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
			location.href= App.remoteUrlPre +"index";
		});
		
		$("#setting").click(function(){
			location.href= App.remoteUrlPre +"reSrfx";
		});
		var $number = $('input[type=number]');
		var $span7 = $("span[v='7']");
		var num = 40;
		format = function(){
			$span7.html((total/num).toFixed(1));
		}
		format(); //初始为span7赋值
		
		$('a[data-toggle="tab"]').on('show.bs.tab', function (e) {
			 /* e.target // 激活的标签页
			  e.relatedTarget // 前一个激活的标签页*/
			var _v = $(e.target).attr("value");
			var xk = $(e.target).attr("href").substring(1);
			App.post(App.remoteUrlPre+"getSrfxResult",{xkId:_v},function(result){
				var data = result.data ;
				$("span[name='mf']").html(data.mf );
				$("span[name='mbfs']").html(data.mbfs);
				$("span[name='cj']").html(data[xk+'_cj']);
				$("span[name='cjmf']").html(data[xk+'_mf']);
				$("span[v='1']").html(data.A1_1);
				$("span[v='2']").html(data.A1_2);
				$("span[v='3']").html(data.A2);
				$("span[v='4']").html(data.A3);
				$("span[v='5']").html(data.A4);
				total = data.total;
				format(); //为span7赋值
	     	});
		 	
		});
		$number.change(function(){
			var n = parseInt($number.val());
			if( n > 0){
				num = n;
				format(); //为span7赋值
			}else{
				$number.val(num)
			}
		})
		
	});
	

});