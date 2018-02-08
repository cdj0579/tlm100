<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html dir="ltr">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>${info.name }</title>
	<link type="text/css" rel="stylesheet" href="${basePath }assets/global/plugins/font-awesome/css/font-awesome.min.css" />
	<link type="text/css" rel="stylesheet" href="${basePath }assets/global/plugins/bootstrap/css/bootstrap.min.css" />
	<link type="text/css" rel="stylesheet" href="${basePath }assets/global/css/components.min.css" />
	<link type="text/css" rel="stylesheet" href="${basePath }assets/global/plugins/ckeditor/full/contents.css">
	<link type="text/css" rel="stylesheet" href="${basePath }assets/global/plugins/ckeditor/full/plugins/jme/mathquill-0.9.1/mathquill.css">
	<link type="text/css" rel="stylesheet" href="${basePath }assets/global/plugins/ckeditor/full/plugins/copyformatting/styles/copyformatting.css">
	<script type="text/javascript">
	window.onload = function(){
		var tables = document.body.getElementsByTagName("table");
		if(tables && tables.length > 0){
			tables[0].style.width = "";
		}
	};
	</script>
	<style type="text/css" media="print">
        .btn.print {
            display: none;
        }
    </style>
</head>
<body class="cke_editable cke_editable_themed cke_contents_ltr cke_show_borders" oncontextmenu=self.event.returnValue=false onselectstart="return false">
	<div style="position: fixed;top: 10px;left: 50px;"><a href="javascript: window.print();" class="btn blue print"><i class="fa fa-print"></i> 添加</a></div>
	${info.content }
</body>
</html>