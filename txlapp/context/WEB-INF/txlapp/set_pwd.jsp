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
		<link href="${basePath }assets/global/plugins/font-opensans/Open-Sans.css" rel="stylesheet" type="text/css" />
		<link href="${basePath }assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
		<link href="${basePath }assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
		<link href="${basePath }assets/global/plugins/mui/css/mui.min.css" rel="stylesheet" type="text/css" />
		<link href="${basePath }assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
		<link href="${basePath }assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
		
		<link href="${basePath }assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />
        
        <link href="${basePath }assets/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
        <link href="${basePath }assets/global/css/plugins.min.css" rel="stylesheet" type="text/css" />
        <link href="${basePath }assets/global/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />
		<style type="text/css">
			.setPwd-form {
				padding: 0 15px;
			}
		</style>
	</head>
	<body>
		<header class="mui-bar  mui-bar-nav" style="padding-right: 15px;">
			<button type="button" class="mui-left mui-action-back mui-btn  mui-btn-link mui-btn-nav mui-pull-left">
				<span class="mui-icon mui-icon-left-nav"></span>
			</button>
			<h1 class="mui-title">密码修改</h1>
		</header>
		<section style="margin-top: 55px;">
			<form  class="setPwd-form" method="post" action="">
				<div class="form-group">
                    <label class="control-label visible-ie8 visible-ie9">原密码</label>
                    <input class="form-control placeholder-no-fix" type="password" autocomplete="off" id="old_password" placeholder="原密码" name="old_password" />
				</div>
				<div class="form-group">
                    <label class="control-label visible-ie8 visible-ie9">新密码</label>
                    <input class="form-control placeholder-no-fix" type="password" autocomplete="off" id="new_password" placeholder="新密码" name="password" />
				</div>
                <div class="form-group">
                    <label class="control-label visible-ie8 visible-ie9">确认新密码</label>
                    <input class="form-control placeholder-no-fix" type="password" autocomplete="off" placeholder="重新输入新密码" name="rpassword" /> 
                </div>
                 <div class="form-group">
                    <button type="submit" id="submit-btn"  class="btn btn-block red uppercase">提交</button>
                </div>
			</form>
			
		</section>
		<script src="${basePath }assets/common/require.js" data-main="${basePath }assets/txlapp/user/set_pwd" type="text/javascript"></script>
	</body>
</html>
