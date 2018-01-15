<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.unimas.web.utils.*" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title></title>
		<%PageUtils.initBasePath(request); %>
        <script>
			var basePath = "${basePath }";
		</script>
		<link href="${basePath }assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
		<link href="${basePath }assets/global/plugins/mui/css/mui.min.css" rel="stylesheet" type="text/css" />
		<link href="${basePath }assets/global/css/components.min.css" rel="stylesheet" type="text/css" />
		<link href="${basePath }assets/global/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />
		<link href="${basePath }assets/jquery.photoClip/css/default.css" rel="stylesheet" type="text/css" />
		<link href="${basePath }assets/common/theme/default/css/style.css" rel="stylesheet" id="style_default" type="text/css" />
		<style type="text/css">
			 .tx_div {
			 	border-top: 1px solid #232F44;
			 	border-bottom: 1px solid #232F44;
			 	margin-bottom: 15px;
			 	height:375px ;
			 	width:100%;
			 }
			 #setImg_div,#selectImg_div, section {
			 	height:100% ;
			 	width:100%;
			 }
		</style>
	</head>
	<body>
		<div id="setImg_div">
			<header class="mui-bar  mui-bar-nav" style="padding-right: 15px;">
				<button type="button" class="mui-left mui-action-back mui-btn  mui-btn-link mui-btn-nav mui-pull-left">
					<span class="mui-icon mui-icon-left-nav"></span>
				</button>
				<h1 class="mui-title">设置个人头像</h1>
			</header>
			<section style="margin-top: 45px;">
				<div id="view" class="tx_div"> 
				
				</div>
				<div style="width: 90%;margin: 0 auto;">
					<label for="txImg" class="btn btn-block btn-default">选择图像</label>
					<input id="txImg" type="file" class="hidden" name="tx_files">
				</div>
			</section>
		</div>
		<div id="selectImg_div" style="display: none;">
			<header class="mui-bar  mui-bar-nav" style="padding-right: 15px;">
				<button type="button" class="mui-left mui-action-back mui-btn  mui-btn-link mui-btn-nav mui-pull-left">
					<span class="mui-icon mui-icon-left-nav"></span>
				</button>
				<button id='setting' class=" mui-pull-right mui-btn-link">使用</button>
			</header>
			<section style="margin-top: 45px;">
				<div class="tx_div " id="selectArea"> 
					
				</div>
			</section>
		</div>
		<script src="${basePath }assets/common/require.js" data-main="${basePath }assets/user/set_tx" type="text/javascript"></script>
	</body>
</html>
