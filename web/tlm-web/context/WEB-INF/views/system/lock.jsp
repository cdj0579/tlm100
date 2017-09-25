<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="com.unimas.web.utils.*" %>
<!DOCTYPE html>
<!--[if IE 8]> <html class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html>
<!--<![endif]-->
<head>
<link href="" rel="stylesheet" type="text/css" />
<%
PageUtils.init(request);
%>
<title>${page.title }</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<tiles:insertAttribute name="global-styles"/>
<link href="${page.basePath }assets/system/css/lock.css" rel="stylesheet" type="text/css" />
<tiles:insertAttribute name="theme-global-styles"/>
<link href="${page.basePath }assets/common/theme/default/css/style.css" rel="stylesheet" id="style_default" type="text/css" />
<!--[if lt IE 9]>
<script src="${page.basePath }assets/global/plugins/respond.min.js"></script>
<script src="${page.basePath }assets/global/plugins/excanvas.min.js"></script> 
<![endif]-->
<script>
var basePath = "${page.basePath }";
</script>
<script src="${page.basePath }assets/common/require.js" defer async="true" data-main="${page.basePath }assets/system/js/lock" type="text/javascript"></script>
</head>
<body class="">
	<div class="page-lock">
	    <div class="page-logo">
	    	${page.title }
	        <!-- <a class="brand" href="index.html">
	            <img src="../assets/pages/img/logo-big.png" alt="logo" /> </a> -->
	    </div>
	    <div class="page-body">
	        <div class="lock-head"> 锁屏中 </div>
	        <div class="lock-body">
	            <div class="pull-left lock-avatar-block">
	                <%-- <img src="user.do?action=getUserIcon&username=${user.loginName }" class="lock-avatar">  --%>
	                <img src="${page.basePath}assets/layouts/layout/img/avatar1.jpg" class="lock-avatar"> </div>
	            	<form class="lock-form pull-left" action="${page.basePath}system/lock" method="post">
		                <h4>${user.realName }</h4>
		                <div class="form-group">
		                    <input title="${errorMsg }" class="form-control placeholder-no-fix" type="password" autocomplete="off" placeholder="密码" name="password" /> </div>
		                <div class="form-actions">
		                    <button type="submit" class="btn red">解锁</button>
		                </div>
	            	</form>
	        </div>
	        <div class="lock-bottom">
	            <!-- <a href="">Not Amanda Smith?</a> -->
	        </div>
	    </div>
	    <div class="page-footer-custom"> ${page.footerMsg } </div>
	</div>
</body>
</html>