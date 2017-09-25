<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="java.util.HashMap,com.unimas.web.utils.*" %>
<%
PageUtils.init(request);
%>
<!DOCTYPE html>
<!--[if IE 8]> <html class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html>
<!--<![endif]-->
<head>
<title>${page.title }</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<tiles:insertAttribute name="global-styles"/>
<tiles:insertAttribute name="page-plugins-styles" ignore="true"/>
<tiles:insertAttribute name="theme-global-styles"/>
<link href="${page.basePath }assets/layouts/layout2/css/layout.min.css" rel="stylesheet" type="text/css" />
<link href="${page.basePath }assets/layouts/layout2/css/themes/blue.css" rel="stylesheet" type="text/css"/>
<link href="${page.basePath }assets/layouts/layout2/css/custom.min.css" rel="stylesheet" type="text/css" />

<link href="${page.basePath }assets/common/theme/default/css/style.css" rel="stylesheet" id="style_default" type="text/css" />
<script>
var basePath = "${page.basePath }";
var userName = "${user.realName }";
</script>
<!--[if lt IE 9]>
<script src="${page.basePath }assets/global/plugins/respond.min.js"></script>
<script src="${page.basePath }assets/global/plugins/excanvas.min.js"></script> 
<![endif]-->
</head>
<body  class="page-header-fixed page-sidebar-closed-hide-logo page-container-bg-solid">
	<!-- BEGIN HEADER -->
    <tiles:insertAttribute name="page-header"/>
    <!-- END HEADER -->
    <!-- BEGIN CONTAINER -->
        <div class="page-container">
        	<tiles:insertAttribute name="page-sidebar"/>
            <!-- BEGIN CONTENT -->
            <div class="page-content-wrapper">
            <!-- BEGIN CONTENT BODY -->
                <!-- BEGIN PAGE CONTENT BODY -->
                <div class="page-content">
                	<c:if test="${desc!=null}">
                        <!-- BEGIN PAGE TITLE -->
                        <h3 class="page-title"> ${title}
	                        <small>${desc}</small>
	                    </h3>
                        <!-- END PAGE TITLE -->
                    </c:if>
                    <div class="page-bar">
                        <!-- BEGIN PAGE BREADCRUMBS -->
                    	<c:if test="${navs!=null}">
	                    	<ul class="page-breadcrumb">
							    <c:forEach var="view" items="${navs}">
							    	<li>
										<c:if test="${view.iconCls!=null}">
											<i class="${view.iconCls}"></i>
										</c:if>	
		                                <c:if test="${view.url!=null}">
			                                <a href="${page.basePath }${view.url}"> ${view.title} </a>
		                                </c:if>
		                                <c:if test="${view.url==null}">
			                                <span> ${view.title} </span>
		                                </c:if>
		                                <i class="fa fa-angle-right"></i>
                            		</li>
								</c:forEach>
								<li>
							    	<span> ${title} </span>
							    </li>
							</ul>
                        </c:if>
						<!-- END PAGE BREADCRUMBS -->
                        <!-- <div class="page-toolbar">
                            <div class="btn-group pull-right">
                                <button type="button" class="btn btn-fit-height grey-salt dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-delay="1000" data-close-others="true"> Actions
                                    <i class="fa fa-angle-down"></i>
                                </button>
                                <ul class="dropdown-menu pull-right" role="menu">
                                    <li>
                                        <a href="#">
                                            <i class="icon-bell"></i> Action</a>
                                    </li>
                                    <li>
                                        <a href="#">
                                            <i class="icon-shield"></i> Another action</a>
                                    </li>
                                    <li>
                                        <a href="#">
                                            <i class="icon-user"></i> Something else here</a>
                                    </li>
                                    <li class="divider"> </li>
                                    <li>
                                        <a href="#">
                                            <i class="icon-bag"></i> Separated link</a>
                                    </li>
                                </ul>
                            </div> -->
                    </div>
                    <tiles:insertAttribute name="body"/>
            	</div>   
           </div>
           <!-- BEGIN CONTENT -->
	 </div>   
     <!-- END CONTAINER -->
     <!-- BEGIN FOOTER -->
     <tiles:insertAttribute name="page-footer"/>
     <!-- BEGIN PRE-FOOTER -->
</body>

</html>