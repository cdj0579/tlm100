<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*,com.unimas.tlm.service.SystemService" %>
<%
HashMap<String, Object> pageConfig = (HashMap<String, Object>)request.getAttribute("page");
String base = (String)pageConfig.get("basePath");
List<Map<String, Object>> menus = new SystemService().getMenus(base);
request.setAttribute("menus", menus);
%>
<!-- BEGIN SIDEBAR -->
            <div class="page-sidebar-wrapper">
                <!-- END SIDEBAR -->
                <div class="page-sidebar navbar-collapse collapse">
                    <!-- BEGIN SIDEBAR MENU -->
                    <ul class="page-sidebar-menu  page-header-fixed page-sidebar-menu-hover-submenu " data-keep-expanded="false" data-auto-scroll="true" data-slide-speed="200">
                    	<c:forEach var="item" items="${menus }" varStatus="topMenus">
							<li class="nav-item<c:if test='${topMenus.index==0}'> start</c:if><c:if test='${item.name eq name}'> active open</c:if>" 
								id="menu-${item.name}">
	                            <a href="${item.url}" class="nav-link<c:if test='${!empty item.children}'> nav-toggle</c:if>">
	                                <i class="${item.iconCls}"></i>
	                                <span class="title">${item.text}</span>
	                                <c:if test='${item.name eq name}'><span class="selected"></span></c:if>
	                                <c:if test='${!empty item.children}'><span class="arrow"></span></c:if>
	                            </a>
	                            <c:if test='${!empty item.children}'>
	                            	<ul class="sub-menu">
	                            		<c:forEach var="item1" items="${item.children }" varStatus="subMenus">
	                            			<li class="nav-item<c:if test='${topMenus.index==0}'> start</c:if><c:if test='${item1.name eq name}'> active open</c:if>" 
	                            				id="menu-${item1.name}">
					                            <a href="${item1.url}" class="nav-link<c:if test='${!empty item1.children}'> nav-toggle</c:if>">
			                                        <i class="${item1.iconCls}"></i>
					                                <span class="title">${item1.text}</span>
					                                <c:if test='${item1.name eq name}'><span class="selected"></span></c:if>
					                                <c:if test='${!empty item1.children}'><span class="arrow"></span></c:if>
			                                    </a>
			                                    <c:if test='${!empty item1.children}'>
					                            	<ul class="sub-menu">
					                            		<c:forEach var="item2" items="${item1.children }" varStatus="subMenus1">
					                            			<li class="nav-item<c:if test='${topMenus.index==0}'> start</c:if><c:if test='${item2.name eq name}'> active open</c:if>" 
					                            				id="menu-${item2.name}">
									                            <a href="${item2.url}" class="nav-link<c:if test='${!empty item2.children}'> nav-toggle</c:if>">
							                                        <i class="${item2.iconCls}"></i>
									                                <span class="title">${item2.text}</span>
									                                <c:if test='${item2.name eq name}'><span class="selected"></span></c:if>
									                                <c:if test='${!empty item2.children}'><span class="arrow"></span></c:if>
							                                    </a>
							                             	</li>
					                            		</c:forEach>
					                            	</ul>
					                            </c:if>
			                             	</li>
	                            		</c:forEach>
	                            	</ul>
	                            </c:if>
	        				</li>
		                </c:forEach>
                    </ul>
                    <!-- END SIDEBAR MENU -->
                </div>
                <!-- END SIDEBAR -->
            </div>
            <!-- END SIDEBAR -->  