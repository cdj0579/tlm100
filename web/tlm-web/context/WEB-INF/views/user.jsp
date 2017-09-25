<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>this is user page</title>
</head>
<body>
    ${username }<br/>
	this is user page!
	<shiro:hasPermission name="user:add">
		<button>添加</button>
	</shiro:hasPermission>
	<shiro:hasPermission name="user:update">
		<button>修改</button>
	</shiro:hasPermission>
	<shiro:hasPermission name="user:del">
		<button>删除</button>
	</shiro:hasPermission>
</body>
</html>