<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>superuser</title>
</head>
<body>
superuser
     <shiro:hasAnyRoles name="super">
         <shiro:principal/>拥有角色super
     </shiro:hasAnyRoles>
</body>
</html>