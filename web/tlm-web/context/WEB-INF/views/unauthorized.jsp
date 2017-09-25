<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="error">权限不足，5秒后回到首页！</div>
<script>
var basePath = "${page.basePath }";
setTimeout(function(){
	location.href = basePath;
}, 5000);
</script>
