<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title></title>
		<link href="assets/global/plugins/font-opensans/Open-Sans.css" rel="stylesheet" type="text/css" />
		<link href="assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
		<link href="assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
		<link href="assets/global/plugins/mui/css/mui.min.css" rel="stylesheet" type="text/css" />
		<link href="assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
		<link href="assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
		
		<link href="assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />
        
        <link href="assets/login/css/login-2.min.css" rel="stylesheet" type="text/css" />
        
        <link href="assets/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
        <link href="assets/global/css/plugins.min.css" rel="stylesheet" type="text/css" />
        

		<link href="assets/common/theme/default/css/style.css" rel="stylesheet" id="style_default" type="text/css" />
		<style type="text/css">
			.col-xs-2, .col-sm-2, .col-md-2,
			.col-xs-3, .col-sm-3, .col-md-3 {
				padding-left: 0px;
				padding-right: 0px;
			}
			
			.form-horizontal .form-group {
			    margin-left: 0;
			    margin-right: 0;
			    margin-bottom: 0;
			}
			.control-label {
				text-align: right;
			    margin-bottom: 0;
			    padding-top: 7px;
			    padding-right: 15px;
			}
		</style>
	</head>
	<body > 
		<header class="mui-bar  mui-bar-nav" style="padding-right: 15px;">
			<button type="button" class="mui-left mui-action-back mui-btn  mui-btn-link mui-btn-nav mui-pull-left">
				<span class="mui-icon mui-icon-left-nav"></span>
			</button>
			<h1 class="mui-title">深入分析</h1>
		</header>
		<div class="container-fluid" style="margin-top: 55px;">
		 	<form class="form-horizontal" action="" method="post">
		 		<div class="form-group">
	                <label class="col-xs-4 col-sm-4 col-md-4 control-label">所在年级：</label>
	                <div class="col-xs-8 col-sm-8 col-md-8">
	                	<select name="nj" class="select2 form-control" >
	                		<option value="-1">请选择...</option>
	                        <c:forEach var="nj" items="${njList}">
	                        	<option value="${nj['id'] }">${nj['name'] }</option>
							</c:forEach>
	                    </select>
	            	</div>	            	
	            </div>
	            <div class="form-group">
	            	 <div class="col-xs-8 col-sm-8 col-md-8">
	            	<label style="padding:0 10px;text-indent:25px;color: red;">最近次考试成绩：</label>
	            	</div>
	            </div>
	            <div class="row">
	                <label class="col-xs-3  col-sm-3 col-md-3 control-label ">语文:</label>
	                <div  class="col-xs-3 col-sm-3 col-md-3">
	                	<input type="text" placeholder="语文成绩" value="${n }" name="yw_cj"/> 
	                </div>
	                <label class="col-xs-2 col-sm-2 col-md-2 control-label ">满分:</label>
	                <div  class="col-xs-3 col-sm-3 col-md-3">
	                	<input type="text" placeholder="满分" value='100' name="yw_mf"/> 
	            	</div>
	            </div>
	            <div class="row">
	                <label class="col-xs-3  col-sm-3 col-md-3 control-label ">科学:</label>
	                <div  class="col-xs-3 col-sm-3 col-md-3">
	                	<input type="text" placeholder="科学成绩" name="kx_cj"/> 
	                </div>
	                <label class="col-xs-2 col-sm-2 col-md-2 control-label ">满分:</label>
	                <div  class="col-xs-3 col-sm-3 col-md-3">
	                	<input type="text" placeholder="满分" value='100' name="kx_mf"/> 
	            	</div>
	            </div>
	            <div class="row">
	                <label class="col-xs-3  col-sm-3 col-md-3 control-label ">英语:</label>
	                <div  class="col-xs-3 col-sm-3 col-md-3">
	                	<input type="text" placeholder="英语成绩" name="yy_cj"/> 
	                </div>
	                <label class="col-xs-2 col-sm-2 col-md-2 control-label ">满分:</label>
	                <div  class="col-xs-3 col-sm-3 col-md-3">
	                	<input type="text" placeholder="满分" value='100' name="yy_mf"/> 
	            	</div>
	            </div>
	            <div class="row">
	                <label class="col-xs-3  col-sm-3 col-md-3 control-label ">数学:</label>
	                <div  class="col-xs-3 col-sm-3 col-md-3">
	                	<input type="text" placeholder="数学成绩"  name="sx_cj"/> 
	                </div>
	                <label class="col-xs-2 col-sm-2 col-md-2 control-label ">满分:</label>
	                <div  class="col-xs-3 col-sm-3 col-md-3">
	                	<input type="text" placeholder="满分" value='100' name="sx_mf"/> 
	            	</div>
	            </div>
	            <div class="row">
	                <label class="col-xs-3  col-sm-3 col-md-3 control-label ">社会:</label>
	                <div  class="col-xs-3 col-sm-3 col-md-3">
	                	<input type="text" placeholder="社会成绩" name="sh_cj"/> 
	                </div>
	                <label class="col-xs-2 col-sm-2 col-md-2 control-label ">满分:</label>
	                <div  class="col-xs-3 col-sm-3 col-md-3">
	                	<input type="text" placeholder="满分" value='100'  name="sh_mf"/> 
	            	</div>
	            </div>
	            
	            <div class="form-group">
	                <label class="col-xs-4 col-sm-4 col-md-4 control-label">目标学校：</label>
	                <div class="col-xs-8 col-sm-8 col-md-8">
	                	<select name="mbxx" class="select2 form-control" >
	                		<option value="-1">请选择...</option>
	                        <c:forEach var="mbxx" items="${mbxxList}">
	                        	<option value="${mbxx['id'] }">${mbxx['name'] }</option>
							</c:forEach>
	                    </select>
	            	</div>	            	
	            </div>
	          	<div class="form-actions" style="margin-top:15px;">
	                <button type="submit" class="btn blue btn-block uppercase">保存并分析</button>
	            </div>
	        </form>
		</div>
		<script src="assets/common/require.js" data-main="assets/user/srfx_set" type="text/javascript"></script>
	</body>
</html>
