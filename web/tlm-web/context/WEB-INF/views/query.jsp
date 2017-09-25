<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>this is query page</title>
</head>
<body>
	this is query page!
	<shiro:hasPermission name="query:view">
		<shiro:principal/>拥有权限user:view
	</shiro:hasPermission>
	<shiro:hasPermission name="query:advance">
		高级查询
	</shiro:hasPermission>
</body>
</html>