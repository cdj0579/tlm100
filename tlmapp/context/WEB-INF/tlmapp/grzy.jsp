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
		<%PageUtils.initBasePath(request);
		request.setAttribute("host", request.getLocalAddr());
		request.setAttribute("port", request.getLocalPort());
		%>
        <script>
			var basePath = "http://${host }:${port }${basePath }";
			var userNo = "${userNo }";
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
			.headImg {
				position: relative;
				width: 104px;
				height: 104px;
				margin: 5px auto;
				border:1px solid #379082;
				border-radius: 52px !important;
				overflow: hidden;
			}
			#imgHead{
				width: 100px;
				height: 100px;
				margin: auto;
			}
			.nav>li{
				margin-right: 50px;
				display: block;
			}
			.nav>li>a{
				text-align: right;
			}
			.dropdown-menu>li>a{
				text-align: left;
			}
			.dropdown-menu .divider{
				margin:3px 0;
			}
		</style>
	</head>
	<body > 
		<header class="mui-bar  mui-bar-nav" style="padding-right: 15px;">
			<button type="button" class="mui-left mui-action-back mui-btn  mui-btn-link mui-btn-nav mui-pull-left">
				<span class="mui-icon mui-icon-left-nav"></span>
			</button>
			<h1 class="mui-title">个人主页</h1>
		</header>
		<section style="margin-top: 55px;">
			<div class="headImg">
				<img id="imgHead" src='${tx } ' />
			</div>
			<div align="center">
				<span>用户名：<label id='username'>${username }</label></span>
			</div>
			<div style="margin-top: 50px;"> 
				<ul class="nav pull-right">
					<li><a href="${basePath }set_pwd">修改密码</a></li>
					<li><a  href="${basePath }user_profile">完善个人信息</a></li>
					<li class="dropdown dropdown-user">
                         <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
                            	再做一次测试
                         </a>
                         <ul class="dropdown-menu dropdown-menu-default">
                             <li>
                             	<a href="${basePath }1/stuTest" class="nav-link ">数学</a>
                             </li>
                             <li class="divider"></li>
                             <li>
                                 <a href="${basePath }3/stuTest" class="nav-link ">语文</a>
                             </li>
                             <li class="divider"></li>
                             <li>
                                 <a href="${basePath }4/stuTest" class="nav-link ">英语</a>
                             </li>
                             <li class="divider"></li>
                             <li>
                                 <a href="${basePath }2/stuTest" class="nav-link ">科学</a>
                             </li>
                             <li class="divider"></li>
                             <li>
                                 <a href="${basePath }5/stuTest" class="nav-link ">社会</a>
                             </li>
                         </ul>
                     </li>
					<li><a  href="${basePath }test_result">查看测试结果</a></li>
					<li><a  href="${basePath }srfx"> 查看分析结果</a></li>
                    <li><a  href="${basePath }czgj">查看历史成绩轨迹图</a></li>
                 </ul>

			</div>
		</section>
		<script src="${basePath }assets/common/require.js" data-main="${basePath }assets/user/grzy" type="text/javascript"></script>
	</body>
</html>
