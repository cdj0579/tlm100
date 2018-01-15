<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.unimas.web.utils.*" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title></title>
		<%PageUtils.initBasePath(request); %>
        <script>
			var basePath = "${basePath }";
			var data = {};
			data.sx = "${data.sx }";
			data.kx = "${data.kx }";
			data.yw = "${data.yw }";
			data.yy = "${data.yy }";
			data.sh = "${data.sh }";
			data.total = "${data.total }";
		</script>
		<link href="${basePath }assets/global/plugins/font-opensans/Open-Sans.css" rel="stylesheet" type="text/css" />
		<link href="${basePath }assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
		<link href="${basePath }assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
		<link href="${basePath }assets/global/plugins/mui/css/mui.min.css" rel="stylesheet" type="text/css" />
		<link href="${basePath }assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
		<link href="${basePath }assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
		<link href="${basePath }assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />
        <link href="${basePath }assets/login/css/login-2.min.css" rel="stylesheet" type="text/css" />
        <link href="${basePath }assets/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
        <link href="${basePath }assets/global/css/plugins.min.css" rel="stylesheet" type="text/css" />
		<link href="${basePath }assets/common/theme/default/css/style.css" rel="stylesheet" id="style_default" type="text/css" />
		<style type="text/css">
			
			.tab-pane > div   {
				background-color: #fff;
				width:100%;
				height:500px;
			}
		</style>
	</head>
	<body > 
		<header class="mui-bar  mui-bar-nav" style="padding-right: 15px;">
			<button type="button" class="mui-left mui-action-back mui-btn  mui-btn-link mui-btn-nav mui-pull-left">
				<span class="mui-icon mui-icon-left-nav"></span>
			</button>
			<h1 class="mui-title">历史成绩轨迹</h1>
		</header>
		<section style="margin-top: 55px;">
			<div class="tab-content" >
				<div class="tab-pane active" >
					<div id="zf_chart" ></div>		
			  	</div>
			</div>
		</section>
		<%--<script src="${basePath }assets/common/require.js" data-main="${basePath }assets/user/czgj" type="text/javascript"></script>
	--%>
	<script type="text/javascript" src="${basePath }assets/global/plugins/jquery.min.js"></script>
	<script type="text/javascript" src="${basePath }assets/global/plugins/echarts/echarts-all.js"></script>
	<script type="text/javascript">
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
		       {   name: "数学", smooth:true, type: "line",  data: eval(data.sx)  },
		       {   name: "科学", smooth:true, type: "line",  data: eval(data.kx)  },
		       {   name: "语文", smooth:true, type: "line",  data: eval(data.yw)  },
		       {   name: "英语", smooth:true, type: "line",  data: eval(data.yy)  },
		       {   name: "社会", smooth:true, type: "line",  data: eval(data.sh)  },
		       {   name: "总分", smooth:true, type: "line",  data: eval(data.total)  },
		   ]
		};
    	var newChart = echarts.init(document.getElementById("zf_chart"));
		newChart.setOption(option);

	</script>
	</body>
</html>
