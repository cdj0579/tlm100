<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.unimas.web.utils.*" %>

<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
  	<head> 
	    <title></title>
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<%
			PageUtils.initBasePath(request); 
			request.setAttribute("host", request.getLocalAddr());
			request.setAttribute("port", request.getLocalPort()); 
		%>
	    <script>
			var basePath = "${basePath }";
		</script>
		<link href="${basePath }assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
		<link href="${basePath }assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
	    <link href="${basePath }assets/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
	 	<style type="text/css">
			.mui-bar-nav {
			    top: 0;
			}
			.mui-bar {
			    position: fixed;
			    z-index: 10;
			    right: 0;
			    left: 0;
			    height: 44px;
			    padding-left: 10px;
			    border-bottom: 0;
			    background-color: #f7f7f7;
			    box-shadow: 0 0 1px rgba(0,0,0,.85);
			    backface-visibility: hidden;
			}
			.mui-bar .mui-title {
			    right: 40px;
			    left: 40px;
			    display: inline-block;
			    overflow: hidden;
			    width: auto;
			    margin: 0;
			    text-overflow: ellipsis;
			}
			.mui-title {
			    font-size: 17px;
			    font-weight: 500;
			    line-height: 44px;
			    position: absolute;
			    padding: 0;
			    text-align: center;
			    white-space: nowrap;
			    color: #000;
			}
			.mui-icon{
				margin-left: -5px;
			    margin-right: 10px;
			    font-size: 16px;
			    line-height: 44px;
			    top: 0;
			    padding: 0;
			    /* color: #007aff; */
			    border: 0;
			    float: left;
				background-color: transparent;
			}
			.mui-bar .fa-angle-left {
				font-size:24px;
				width:24px;
				height:24px;
				position: relative;
			}
			.share{
				text-align:center;
			}
			.share .download{
				margin:30px;
			}
			
		</style>
	</head>
	<body>
		<header class="mui-bar  mui-bar-nav" style="padding-right: 15px;">
			<button type="button" class="mui-icon">
				<i class="fa fa-angle-left" aria-hidden="true"></i>
			</button>
			<h1 class="mui-title">分享页面的二维码</h1>
		</header>
		<section style="margin-top: 55px;">
			 <c:choose>
			 	<c:when test="${isnull eq 1 }">
					<div class="alert alert-info">
						未分配二维码，请联系相关管理员！
					</div>
			 	</c:when>
			 	<c:otherwise>
			 		<div class="share">
						<img alt="" src="${basePath }share/qrcode">
					</div>
					<div  class="share">
					 <a href="downloadImage:http://${host }:${port }${basePath }fx/${jgId }/${lryId }/qrcode" class="btn blue download"><i class="fa fa-download"></i> 保存图片 </a>
					</div>
			 	</c:otherwise>
			 </c:choose>
			
		</section>
		<script type="text/javascript" src="${basePath }assets/global/plugins/jquery.min.js"></script>
		<script type="text/javascript">
			$(".mui-icon").click(function(){
				history.back();
			});
		</script>
	</body>
</html>
