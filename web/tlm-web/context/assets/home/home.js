/**
 * basePath:当前页面的相对路径，如果导入common/head.jsp,则在head.jsp中设置
 */
if(basePath || basePath == ""){
	require.config({
		baseUrl: basePath
	});
}
define(['assets/common/config'], function(config) {
	require.config(config.require);
	
	require.config({
		paths: {
		},shim: {
		}
	});
	
	require(['app','layout','demo']);
	require(['domready!', 'echarts/echarts'], function (doc){
		
	    // DEMOS
	    require(
	        [
	         	'echarts',
	            'echarts/chart/bar'
	        ],
	        function(ec) {
	        	var myChart = ec.init(document.getElementById('chart_1'));
	        	function teacherChartfun(dataX,dataY){
	        		myChart.setOption({
		                tooltip: {
		                    trigger: 'axis'
		                },
		                color: ["#67B7DC"],
		                calculable: false,
		                xAxis: [{
		                    type: 'category',
		                    axisLabel: {
		                    	interval: 0,
		                    	rotate: 20
		                    	
		                    },
		                    data: dataX
		                }],
		                yAxis: [{
		                    type: 'value',
		                    splitArea: {
		                        show: true
		                    }
		                }],
		                series: [{
		                    name: '访问量',
		                    type: 'bar',
		                    data: dataY
		                }]
		            });
	        	}
	            var myChart2 = ec.init(document.getElementById('chart_2'));
				function zsdChartfun(dataX,dataY){
		            myChart2.setOption({
		                tooltip: {
		                    trigger: 'axis'
		                },
		                color: ["#67B7DC"],
		                calculable: false,
		                xAxis: [{
		                    type: 'category',
		                    axisLabel: {
		                    	interval: 0,
		                    	rotate: 20,
		                    	textStyle: {
		                    		color: "#000"
		                    	}
		                    },
		                    data: dataX
		                }],
		                yAxis: [{
		                    type: 'value',
		                    splitArea: {
		                        show: true
		                    }
		                }],
		                series: [{
		                    name: '使用次数',
		                    type: 'bar',
		                    data: dataY
		                }]
		            });
	        	}
	        	
				function getDataFun(type){
					$.post(basePath+"system/indexChart",{"type":type},function(data,status){
	        			if(data.success == true || data.success == "true"){
		  					if(type=="t"){
		  						teacherChartfun(data.x,data.y)
		  					}else if(type == "zsd"){
		  						zsdChartfun(data.x,data.y)
		  					}
					    }
	        		});
				}
				getDataFun("t");
				getDataFun("zsd");
				
        		$("[name='refer_teacher']").click(function(){
					getDataFun("t");
				});
				
				$("[name='refer_zsd']").click(function(){
					getDataFun("zsd");
				});
	        }
	    );
	});
	
});