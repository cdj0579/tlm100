$(function(){
	$(".mui-icon").click(function(){
		history.back();
	});
	
	var timeChart = echarts.init(document.getElementById("time_chart"));	
	var option = {
		    series : [
		        {
		            type:'gauge',
		            startAngle: 180,
		            endAngle: 0,
		            center : ['50%', '90%'],    // 默认全局居中
		            radius : 120,
		            axisLine: {            // 坐标轴线
		                lineStyle: {       // 属性lineStyle控制线条样式
		                   color: [[0.15, '#228b22'],[0.6, '#44b'],[0.85, '#48b'],[1, '#ff4500']],   
		                  width: 70
		                }
		            },
		            splitLine:{
		            	 show: false
		            },
		            axisTick:{
		            	show: false
		            },
		            axisLabel: {           // 坐标轴文本标签，详见axis.axisLabel
		                formatter: function(v){
		                    switch (v+''){
		                        case '10': return '慢';
		                        case '40': return '一般';
		                        case '70': return '快';
		                        case '90': return '非常快';
		                        default: return '';
		                    }
		                },
		                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
		                    color: '#fff',
		                    fontSize: 12
		                }
		            },
		            pointer: {
		            	color: '#fff',
		                width:5
		            },
		          /*  detail : {
		                show : true,
		                offsetCenter: [0, -40],       // x, y，单位px
		                formatter:'{value}%',
		                textStyle: {       
		                    fontSize :20
		                }
		            },*/
		            data:[{value: speed}]
		        }
		    ]
		};
		timeChart.setOption(option);
	
});
	        	
