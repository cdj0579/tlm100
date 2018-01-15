<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
			article p {color:#000;font-size:16px;text-align:left;text-indent:30px;}
			h3 { margin-top: 5px;font-size:16px;font-weight: 200; }
			h3 span { font-size:24px;font-weight: 300; }
		</style>
	</head>
	<body > 
		<header class="mui-bar  mui-bar-nav" style="padding-right: 15px;">
			<button type="button" class="mui-left mui-action-back mui-btn  mui-btn-link mui-btn-nav mui-pull-left">
				<span class="mui-icon mui-icon-left-nav"></span>
			</button>
			<h1 class="mui-title">PK结果</h1>
		</header>
		<section style="margin-top: 55px;">
			<article align="center">
				<h3>您的<span > ${data.kmName  } </span>测试成绩为</h3>
				<h3><span >${data.total  } </span>分</h3>
				<h3>超越了<span > ${data.overNum } </span>名同学</h3>
			</article>
			<article align="center">
				<img style="height:200px;" src="${basePath }assets/global/img/ztfbt.png">
				<p>您的所在的位置：<i style="text-indent:0px;color:red;" class="fa fa-circle" ></i></p>
			</article>
			<article align="center">
				<div id="time_chart" style="width:100%;height:150px;"></div>
				<p>您的测试用时为：<span name='ys'>${fn:replace(data.use_time,":", "分")  }秒</span></p>
			</article>
				
		</section>
		<script type="text/javascript" src="${basePath }assets/global/plugins/jquery.min.js"></script>
		<script type="text/javascript" src="${basePath }assets/global/plugins/echarts/echarts-all.js"></script>
		<script type="text/javascript">
			var speed = "${data.speed  }"
		</script>
		<script type="text/javascript" src="${basePath }assets/user/pk_result.js"></script>
	</body>
</html>
