<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.unimas.web.utils.*" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title></title>
		<%PageUtils.initBasePath(request); %>
        <script>
			var basePath = "${basePath }";
		</script>
		<link href="${basePath }assets/global/plugins/font-opensans/Open-Sans.css" rel="stylesheet" type="text/css" />
		<link href="${basePath }assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
		<link href="${basePath }assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
		<link href="${basePath }assets/global/plugins/mui/css/mui.min.css" rel="stylesheet" type="text/css" />
		<link href="${basePath }assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
		<link href="${basePath }assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
		
		<link href="${basePath }assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />
        
        <link href="${basePath }assets/login/css/login-2.min.css" rel="stylesheet" type="text/css" />
        
        <link href="${basePath }assets/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
        <link href="${basePath }assets/global/css/plugins.min.css" rel="stylesheet" type="text/css" />
        <link href="${basePath }assets/global/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />

		<link href="${basePath }assets/common/theme/default/css/style.css" rel="stylesheet" id="style_default" type="text/css" />
		<style type="text/css">
			.form-horizontal .form-control {
			    margin-left: 0;
			    margin-right: 0;
			    margin-bottom: 0;
			    height:34px;
			}
			.form-horizontal .form-group.lineSpac{
				margin-bottom:15px;
			}
			.control-label {
				text-align: right;
			    margin-bottom: 0;
			    padding-top: 7px;
			}
			
		</style>
	</head>
	<body > 
		<header class="mui-bar  mui-bar-nav" style="padding-right: 15px;">
			<button type="button" class="mui-left mui-action-back mui-btn  mui-btn-link mui-btn-nav mui-pull-left">
				<span class="mui-icon mui-icon-left-nav"></span>
			</button>
			<h1 class="mui-title">个人信息</h1>
		</header>
		<div class="container-fluid " style="margin-top: 55px;">
		 	<form class="form-horizontal" action="" method="post">
	            <div class="form-group">
	                <label class="col-xs-4 col-sm-4 col-md-4 control-label ">学生姓名：</label>
	                <div class="col-xs-8 col-sm-8 col-md-8">
	                    <input class="form-control" type="text" placeholder="请输入学生姓名" value="${info.studentName }" name="xs_name"/> 
	                </div>
	            </div>
	            <div class="form-group">
	                <label class="col-xs-4 col-sm-4 col-md-4 control-label">家长姓名：</label>
	                <div class="col-xs-8 col-sm-8 col-md-8">
	                	<input class="form-control " type="text" placeholder="请输入家长姓名" value="${info.parentName }" name="jz_name"/> 
	            	</div>
	            </div>
	            <div class="form-group">
	                <label class="col-xs-4 col-sm-4 col-md-4 control-label">手机号码：</label>
	                <div class="col-xs-8 col-sm-8 col-md-8">
	                	<input class="form-control " type="text" placeholder="请输入手机号码" value="${info.contact }" name="phone" /> 
	            	</div>
	            </div>
	            <div class="form-group lineSpac ">
	                <label class="col-xs-4 col-sm-4 col-md-4 control-label">所在学校：</label>
	                <div class="col-xs-8 col-sm-8 col-md-8">
	               	 	<input class="form-control " type="text" placeholder="请输入所在学校" value="${info.school }" name="school" /> 
	            	</div>
	            </div>
	            <div class="form-group">
	                <label class="col-xs-4 col-sm-4 col-md-4 control-label">所在年级：</label>
	                <div class="col-xs-8 col-sm-8 col-md-8">
	                	<select name="nj" class="select2 form-control"  >
	                		<option value="-1">请选择...</option>
	                		<c:forEach var="nj" items="${njList }">
	                        	<option value="${nj['id'] }" ${nj['id'] == info.njId?"selected":"" }>${nj['name'] }</option>
							</c:forEach>
	                    </select>
	            	</div>
	            	<label style="padding:0 10px;text-indent:25px;color: red;">注：假如你正在过初一暑假，则应选择初一；初二、初三也一样</label>
	            </div>
	            
	          <!-- <div class="form-group">
	                <label class="col-xs-4 col-sm-4 col-md-4 control-label">考试成绩：</label>
	                <div class="col-xs-8 col-sm-8 col-md-8">
	                	<input class="form-control " type="number"  placeholder="请输入最近次考试成绩"  name="zhcj"/> 
	            	</div>
	            </div> -->
	          	<div class="form-actions">
	                <button type="submit" class="btn red btn-block uppercase">保&nbsp;&nbsp;&nbsp;&nbsp;存</button>
	            </div>
	        </form>
		</div>
		<script src="${basePath }assets/common/require.js" data-main="${basePath }assets/user/user_profile" type="text/javascript"></script>
	</body>
</html>
