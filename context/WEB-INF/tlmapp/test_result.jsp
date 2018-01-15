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
			.form-horizontal .form-group {
			    margin-left: 0;
			    margin-right: 0;
			    margin-bottom: 0;
			}
			.control-label {
				text-align: right;
			    margin-bottom: 0;
			    padding-top: 7px;
			}
			article p {color:#000;}
		</style>
	</head>
	<body > 
		<header class="mui-bar  mui-bar-nav" style="padding-right: 15px;">
			<button type="button" class="mui-left mui-action-back mui-btn  mui-btn-link mui-btn-nav mui-pull-left">
				<span class="mui-icon mui-icon-left-nav"></span>
			</button>
			<h1 class="mui-title">测试结果</h1>
		</header>
		<section style="margin-top: 55px;">
			<c:if test="${msg != null }">
				<div class="alert alert-info" style="margin-top: 30px 20px 0;">
				    	信息！从未进行过任何科目的在线测试，无信息展示。
				</div>
			</c:if>
			<c:if test="${msg == null }">
			<article align="center">
				<h3 > 总分：<span name='total'>${data.total } 分</span></h3>
				<p> 不会的做对：<span name='bh_right'>${data.unable } 分</span></p>
				<p> 不确定的做对：<span name='bqd_right'>${data.unsure }分</span></p>
				<p> 确定的做错：<span name='qd_error'>${data.sureError }分</span></p>
				<p>测试总用时：<span name='time'>${fn:replace(data.use_time,":", "分")  }秒</span></p>
			</article>
			</c:if>
		</section>
		<footer>
		<c:if test="${msg == null }">
			<div class="col-xs-offset-1  col-xs-10 col-sm-offset-1  col-sm-10">
				<button type="button" name="pk" class="btn blue btn-block uppercase">PK一下</button>
				<button type="button" name="srfx" class="btn blue btn-block uppercase">深入分析</button>
			</div>
		</c:if>
		</footer>
		<script src="${basePath }assets/common/require.js" data-main="${basePath }assets/user/test_result" type="text/javascript"></script>
	</body>
</html>
