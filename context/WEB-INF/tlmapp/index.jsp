<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.unimas.web.utils.*" %>

<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
    <!--<![endif]-->
    <head>
        <meta charset="utf-8" />
        <title>跳龙门教学网</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1" name="viewport" />
        <meta content="" name="description" />
        <meta content="" name="author" />
         <%PageUtils.initBasePath(request); %>
        <script>
			var basePath = "${basePath }";
		</script>
        <!-- BEGIN GLOBAL MANDATORY STYLES -->
        <link href="${basePath }assets/common/favicon.ico" type="image/x-icon" rel="icon" />
		<link href="${basePath }assets/global/plugins/font-opensans/Open-Sans.css" rel="stylesheet" type="text/css" />
		<link href="${basePath }assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
		<link href="${basePath }assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
		<link href="${basePath }assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
		<link href="${basePath }assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
		<link href="${basePath }assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />
        <!-- END GLOBAL MANDATORY STYLES -->
        <!-- BEGIN PAGE LEVEL PLUGINS -->
        
        <!-- END PAGE LEVEL PLUGINS -->
        <!-- BEGIN THEME GLOBAL STYLES -->
        <link href="${basePath }assets/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
        <link href="${basePath }assets/global/css/plugins.min.css" rel="stylesheet" type="text/css" />
        <!-- END THEME GLOBAL STYLES -->
        <!-- BEGIN THEME LAYOUT STYLES -->
        <link href="${basePath }assets/layouts/layout2/css/layout.min.css" rel="stylesheet" type="text/css" />
        <link href="${basePath }assets/layouts/layout2/css/themes/blue.min.css" rel="stylesheet" type="text/css" id="style_color" />
        <link href="${basePath }assets/layouts/layout2/css/custom.min.css" rel="stylesheet" type="text/css" />
        <!-- END THEME LAYOUT STYLES -->
        <link href="${basePath }assets/global/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />
        <link href="${basePath }assets/common/theme/default/css/style.css" rel="stylesheet" id="style_default" type="text/css" />
        <style type="text/css">
        	.btn-lg>i {
        		width:25px;
        	}
        </style> 
    </head>
    <body class="page-header-fixed page-sidebar-closed-hide-logo page-container-bg-solid">
        <div class="page-header navbar navbar-fixed-top">
            <div class="page-header-inner ">
                <div class="page-logo">
                    <div class="logo-text">跳龙门</div>
                    <div class="menu-toggler sidebar-toggler">
                    </div>
                </div>
                <div class="page-top">
                    <div class="top-menu">
                        <ul class="nav navbar-nav pull-right">
                            <li class="dropdown dropdown-user">
                                <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
                                    <img alt="" class="img-circle" src="${tx }" />
                                    <span id="username">${realName }</span>
                                    <i class="fa fa-angle-down"></i>
                                </a>
                                <ul class="dropdown-menu dropdown-menu-default">
                                    <li>
                                        <a href="${basePath }grzy">
                                            <i class="icon-user"></i> 我的信息 </a>
                                    </li>
                                    <li class="divider"> </li>
                                    <li>
                                        <a href="${basePath }logout">
                                            <i class="icon-key"></i> 退出 </a>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <div class="clearfix"></div>       
       <div class="page-content-wrapper">
           <div class="page-content">
               <div class="row">
                   <div class="col-md-6 col-sm-6">
                       <!-- BEGIN PORTLET-->
                       <div class="portlet light ">
                           <div class="portlet-title">
                               <div class="caption">科目测试</div>
                               <div class="actions"></div>
                           </div>
                           <div class="portlet-body util-btn-margin-bottom-5">
                               <ul class="nav">
                               
                               	<li><a href="${basePath }1/stuTest" class="btn btn-lg blue">数学测试 <i class='fa <c:if test="${check.km1==1 }">fa-check</c:if>'></i> </a></li>
                               	<li><a href="${basePath }3/stuTest" class="btn btn-lg yellow">语文测试<i class='fa <c:if test="${check.km3==3 }">fa-check</c:if>'></i></a></li>
                               	<li><a href="${basePath }4/stuTest" class="btn btn-lg purple">英语测试 <i class='fa <c:if test="${check.km4==4 }">fa-check</c:if>'></i></a></li>
                               	<li><a href="${basePath }2/stuTest" class="btn btn-lg dark">科学测试<i class='fa <c:if test="${check.km2 == 2 }">fa-check</c:if>'></i></a></li>
                               	<li><a href="${basePath }5/stuTest" class="btn btn-lg red">社会测试<i class='fa <c:if test="${check.km5 == 5 }">fa-check</c:if>'></i></a></li>
                               </ul>
                           </div>
                       </div>
                       <!-- END PORTLET-->
                   </div>
                   <div class="col-md-6 col-sm-6">
                       <!-- BEGIN PORTLET-->
                       <div class="portlet light ">
                           <div class="portlet-body util-btn-margin-bottom-5">
                              <ul class="nav">
                               	<li><a href="${basePath }srfx"  class="btn btn-lg blue-hoki">深入分析</a></li>
                              </ul>
                              
                           </div>
                       </div>
                       <!-- END PORTLET-->
                   </div>
               </div>
           </div>
       </div>
        <div class="page-footer">
            <div class="page-footer-inner"> 2017 &copy; 杭州超骥信息科技有限公司.
            </div>
            <div class="scroll-to-top">
                <i class="icon-arrow-up"></i>
            </div>
        </div>
        <!--[if lt IE 9]>
			<script src="../assets/global/plugins/respond.min.js"></script>
			<script src="../assets/global/plugins/excanvas.min.js"></script> 
		<![endif]-->
        <script src="${basePath }assets/common/require.js" data-main="${basePath }assets/index/js/index" type="text/javascript"></script>
    </body>

</html>