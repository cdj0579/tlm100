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
	            myChart.setOption({
	                tooltip: {
	                    trigger: 'axis'
	                },
	                color: ["#67B7DC"],
	                calculable: false,
	                xAxis: [{
	                    type: 'category',
	                    data: ['胡鑫盛', '吴琴琴', '黄玉龙', '杨扬', '黄瑞芳', '孙传宝', '胡名流', '许公飞', '吴芳', '钱山']
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
	                    data: [23, 18, 32, 56, 31, 26, 25, 35, 44, 27]
	                }]
	            });
	            var myChart2 = ec.init(document.getElementById('chart_2'));
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
	                    data: ['三角形', '方程', '抛物线', '有理数', '二次根式', '中位线', '圆', '函数', '弦切角', '多边形']
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
	                    data: [23, 26, 25, 35, 18, 32, 56, 31, 44, 27]
	                }]
	            });
	        }
	    );
	});
	
});