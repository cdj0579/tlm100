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
<link href="${page.basePath }assets/layouts/layout3/css/layout.min.css" rel="stylesheet" type="text/css" />
<link href="${page.basePath }assets/layouts/layout3/css/themes/default.min.css" rel="stylesheet" type="text/css"/>
<link href="${page.basePath }assets/layouts/layout3/css/custom.min.css" rel="stylesheet" type="text/css" />

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
<body  class="page-container-bg-solid page-boxed">
	<!-- BEGIN HEADER -->
    <tiles:insertAttribute name="page-header"/>
    <!-- END HEADER -->
     <!-- BEGIN CONTAINER -->
        <div class="page-container">
            <!-- BEGIN CONTENT -->
            <div class="page-content-wrapper">
            <!-- BEGIN CONTENT BODY -->
                <!-- BEGIN PAGE HEAD-->
                <div class="page-head">
                    <div class="container">
                        <c:if test="${desc!=null}">
	                        <!-- BEGIN PAGE TITLE -->
	                        <div class="page-title">
	                            <h1>${title}
	                                <small>${desc}</small>
	                            </h1>
	                        </div>
	                        <!-- END PAGE TITLE -->
                         	<c:if test="${page.showPageToolbar==true}"><tiles:insertAttribute name="page-toolbar"/></c:if>
                        </c:if>
                    </div>
                </div>
                <!-- END PAGE HEAD-->
                <!-- BEGIN PAGE CONTENT BODY -->
                <div class="page-content">
                    <div class="container">
                    	<!-- BEGIN PAGE BREADCRUMBS -->
                    	<c:if test="${navs!=null}">
	                    	<ul class="page-breadcrumb breadcrumb">
							    <c:forEach var="view" items="${navs}">
									<a href="${page.basePath }${view.url}"> ${view.title} </a>
                            		<i class="fa fa-circle"></i>
								</c:forEach>
								<li>
							    	<span> ${title} </span>
							    </li>
							</ul>
                        </c:if>
						<!-- END PAGE BREADCRUMBS -->
						<!-- BEGIN PAGE CONTENT INNER -->
						<div class="page-content-inner">
	                       	<tiles:insertAttribute name="body"/>
						</div>
						<!-- END PAGE CONTENT INNER -->
                	</div>
            	</div>   
            
            </div>
           <!-- BEGIN CONTENT -->
           <!-- BEGIN QUICK SIDEBAR -->
           <%-- <tiles:insertAttribute name="quick-sidebar"/> --%>
        	<!-- END QUICK SIDEBAR -->
        </div>   
     <!-- END CONTAINER -->
     <!-- BEGIN FOOTER -->
     <tiles:insertAttribute name="page-footer"/>
     <!-- BEGIN PRE-FOOTER -->
</body>

</html>