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
require(['app','layout','demo']);
require(['domready!','app','echarts/echarts'], function (doc,App){
	

	require(
        [
         	'echarts',
            'echarts/chart/line'
        ],
        function(ec) {
        	$(".mui-icon").click(function(){
				history.back();
			});
        	var y=['0', '10', '20', '50', '40', '50', '60', '70', '180'];
			var x=["一", "二", "三", "四", "五", "六", "七", "八", "九"];
			var createChart = function( id ,datax ,datay ){
				var newChart = ec.init(document.getElementById(id));
				var option = {
				   title: {
				       text: "总分轨迹图",
				       x: "center"
				   },
				   tooltip: {
				       trigger: "item",
				       formatter: "{a} <br/>{b} : {c}"
				   },
				   grid:{x:60,x2:30,borderWidth:1},
				   xAxis: [
				       {
				           type: "category",
				           name: "x",
				           splitLine: {show: true},
				           data: datax
				       }
				   ],
				   yAxis: [
				       {
				          	type : 'value',
				            axisLabel : {
				                formatter: '{value} 分'
				               }
				       }
				   ],
				   calculable: true,
				   series: [
				       {
				           name: "分数",
				           type: "line",
				           data: datay
				
				       }
				   ]
				};
				newChart.setOption(option);
				return newChart;
			}
			var zfChart,sxChart,ywChart,yyChart,kxChart,shChart;
			
			zfChart = createChart("zf_chart" ,x,y);
			sxChart = createChart("sx_chart" ,x,y);
			
			$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
				
				console.info(e.relatedTarget);
				
				console.info(e.target);


          	});

		});
		
	});
	

});