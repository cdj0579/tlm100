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
			var option = {
				legend: {  
                    data:['数学','科学','语文','英语','社会','总分']  
                },  
                tooltip: {
                	trigger: "item",
                	formatter: "{a} <br/>{b} : {c}"
                },
                grid:{x:60,x2:30,borderWidth:1},
                xAxis: [{
                	type: "category",
                	splitLine: {show: true},
                	data: ["近6","近5","近4","近3","近2","近1"]
                }],
                yAxis: [{
                	type : 'value',
		            axisLabel : {
		                	formatter: '{value} 分'
		               	}
			       	}
			   ],
			   calculable: true,
			   series: [
			       {   name: '数学',  type: "line",  data: eval(data.sx)  },
			       {   name: "科学",  type: "line",  data: eval(data.kx)  },
			       {   name: "语文",  type: "line",  data: eval(data.yw)  },
			       {   name: "英语",  type: "line",  data: eval(data.yy)  },
			       {   name: "社会",  type: "line",  data: eval(data.sh)  },
			       {   name: "总分",  type: "line",  data: eval(data.total)  },
			   ]
			};
        	var newChart = ec.init(document.getElementById("zf_chart"));
			newChart.setOption(option);

		});
		
	});
	

});