<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html dir="ltr">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>${info.name }</title>
	<link type="text/css" rel="stylesheet" href="/tlm-web/assets/global/plugins/ckeditor/full/contents.css">
	<link type="text/css" rel="stylesheet" href="/tlm-web/assets/global/plugins/ckeditor/full/plugins/jme/mathquill-0.9.1/mathquill.css">
	<link type="text/css" rel="stylesheet" href="http://localhost:8080/tlm-web/assets/global/plugins/ckeditor/full/plugins/copyformatting/styles/copyformatting.css">
</head>
<body class="cke_editable cke_editable_themed cke_contents_ltr cke_show_borders" oncontextmenu=self.event.returnValue=false onselectstart="return false">
	${info.content }
</body>
</html>