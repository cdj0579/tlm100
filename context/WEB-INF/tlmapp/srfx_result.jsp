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
			.col-xs-2, .col-sm-2, .col-md-2,
			.col-xs-3, .col-sm-3, .col-md-3 {
				padding-left: 0px;
				padding-right: 0px;
			}
			
			p{
				margin: 0 0 10px;
				text-indent: 10%;
				color:#34495e;
			}
			div.tab-content {
				padding: 0 10%;
				margin: 20px 0;
			}
			.table-bordered, .table-bordered>tbody>tr>td,
			 .table-bordered>tbody>tr>th, .table-bordered>tfoot>tr>td, 
			 .table-bordered>tfoot>tr>th, .table-bordered>thead>tr>td, 
			 .table-bordered>thead>tr>th{
				border-color: #000;
			}
		</style>
	</head>
	<body > 
		<header class="mui-bar  mui-bar-nav" style="padding-right: 15px;">
			<button type="button" class="mui-left mui-action-back mui-btn  mui-btn-link mui-btn-nav mui-pull-left">
				<span class="mui-icon mui-icon-left-nav"></span>
			</button>
			<h1 class="mui-title">深入分析结果</h1>
			<button id='setting' class=" mui-pull-right mui-btn-link">重设</button>
		</header>
		<section style="margin-top: 55px;">
			<ul class="nav nav-tabs" id="myTab">
			  	<li class="active"><a href="#sx" value="1" data-toggle="tab"> 数学</a></li>
			  	<li><a href="#kx" value="2" data-toggle="tab">科学</a></li>
			  	<li><a href="#yw" value="3" data-toggle="tab">语文</a></li>
			  	<li><a href="#yy" value="4" data-toggle="tab">英语</a></li>
			  	<li><a href="#sh" value="5" data-toggle="tab">社会</a></li>
			</ul>
			<div>
				<p> 中考满分：<span name='mf'>${data.mf } </span>分</p>
			  	<p> 目标分数：<span name='mbfs'>${data.mbfs } </span>分/<span name='mf'>${data.mf }</span>分</p>
			  	<p> 分数现状：<span name='cj'>${data.sx_cj } </span>分/<span name='cjmf'>${data.sx_mf }</span>分</p>
			  	<p> 提分项目：</p>
			</div>
			<div class="tab-content" >
			  	<div class="tab-pane active">
			  		<table class="table table-bordered">
			  			<tbody>
			  				<tr><td rowspan="2">A1查漏补缺</td><td>A1.1基础知识点</td><td v="1">${data.A1_1 }</td></tr>
			  				<tr><td>A1.2重难知识点</td><td v="2">${data.A1_2 }</td></tr>
			  				<tr><td>A2同步巩固</td><td>知识点</td><td v="3">${data.A2 }</td></tr>
			  				<tr><td>A3综合训练</td><td>综合题型</td><td v="4">${data.A3 }</td></tr>
			  				<tr><td>A4压轴专题</td><td>压轴专题</td><td v="5">${data.A4 }</td></tr>
			  				<tr><td>总课时</td><td v="6"></td><td v="7">${data.total }</td></tr>
			  			</tbody>
			  		</table>
			  	</div>
			</div>
			<div>
				<p> 温馨提示您与目标的差距：</p>
				<c:if test="${data.diff_1 > 0 }">
					<p>	数学：<span name='low_sx'>${data.diff_1 }</span>分</p>
				</c:if>
				<c:if test="${data.diff_2 > 0 }">
					<p> 科学：<span name='low_kx'>${data.diff_2 } </span>分</p>
				</c:if>
				<c:if test="${data.diff_3 > 0 }">
					<p> 语文：<span name='low_yw'>${data.diff_3 } </span>分</p>
				</c:if>
				<c:if test="${data.diff_4 > 0 }">
					<p> 英语：<span name='low_yy'>${data.diff_4 } </span>分</p>
				</c:if>
				<c:if test="${data.diff_5 > 0 }">
					<p> 社会：<span name='low_sh'>${data.diff_5 } </span>分</p>
				</c:if>
				
			</div>

		</section>
		<script src="${basePath }assets/common/require.js" data-main="${basePath }assets/user/srfx_result" type="text/javascript"></script>
	</body>
</html>
