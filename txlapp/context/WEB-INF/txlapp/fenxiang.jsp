<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.unimas.web.utils.*" %>

<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
  	<head> 
	    <title></title>
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<%PageUtils.initBasePath(request); %>
	    <script>
			var basePath = "${basePath }";
		</script>
	    <link href="${basePath }assets/common/favicon.ico" type="image/x-icon" rel="icon" />
		<link href="${basePath }assets/global/plugins/font-opensans/Open-Sans.css" rel="stylesheet" type="text/css" />
		<link href="${basePath }assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
		<link href="${basePath }assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
		<link href="${basePath }assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
	    <link href="${basePath }assets/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
	    <link href="${basePath }assets/global/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />
		<link href="${basePath }assets/common/theme/default/css/style.css" rel="stylesheet" type="text/css" />
	 	<link href="${basePath }assets/global/css/plugins.min.css" rel="stylesheet" type="text/css" />
	    <link href="${basePath }assets/global/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />
		<link href="${basePath }assets/global/plugins/select2/css/select2.min.css" rel="stylesheet" type="text/css" />
		<link href="${basePath }assets/global/plugins/select2/css/select2-bootstrap.min.css" rel="stylesheet" type="text/css" />
		
	 	<style type="text/css">
	 		.toast-top-center,.toast-top-right,.toast-top-left {
			  	top: 138px;
			}
			.form-horizontal .form-group{
				margin-left: 0;
				margin-right: 0;				
			}
			.portlet .actions .form-group {
				margin-bottom: 0;
			}
			#form_remark{
				padding: 0 15px;
			}
			
			.mui-bar-nav {
			    top: 0;
			}
		
			.mui-bar {
			    position: fixed;
			    z-index: 10;
			    right: 0;
			    left: 0;
			    height: 44px;
			    padding-left: 10px;
			    border-bottom: 0;
			    background-color: #f7f7f7;
			    box-shadow: 0 0 1px rgba(0,0,0,.85);
			    backface-visibility: hidden;
			}
			.mui-bar .mui-title {
			    right: 40px;
			    left: 40px;
			    display: inline-block;
			    overflow: hidden;
			    width: auto;
			    margin: 0;
			    text-overflow: ellipsis;
			}
			.mui-title {
			    font-size: 17px;
			    font-weight: 500;
			    line-height: 44px;
			    position: absolute;
			    padding: 0;
			    text-align: center;
			    white-space: nowrap;
			    color: #000;
			}
			.control-label {
				padding-left:0;
				padding-right:10px;
				line-height: 32px;
				text-align: right;
			}
			.bd-left {
				padding-left: 0;
				padding-right: 0;
			}
		</style>
	</head>
	<body>
		<!--<header class="mui-bar  mui-bar-nav" style="padding-right: 15px;">
			<h1 class="mui-title">联系人信息录入</h1>
		</header>
		--><section style="margin-top: 55px;">
			<form action="#" class="form-horizontal " id="form_remark">
				<input type="hidden" name="id" value="-1"/>
				<input type="hidden" name="jgId" value="${jgId }"/>
				<input type="hidden" name="lryId" value="${lryId }"/>
		    	<div class="form-body">
		    		<div class='form-group'>
		                <label class="control-label col-md-3 col-xs-4 ">联系人姓名: <span class="required">*</span></label>
		                <div class="col-md-4 col-xs-8 bd-left">
		                	<div class="input-icon right">
		                     <i class="fa"></i>
		                     <input type="text" name="lianxiren" class="form-control" validate="{required:true}"/>
		                    </div>
		                </div>
		            </div>
		            <div class='form-group'>
		                <label class="control-label col-md-3 col-xs-4 ">联系电话: <span class="required">*</span></label>
		                <div class="col-md-4 col-xs-8 bd-left">
		                	<div class="input-icon right">
		                     <i class="fa"></i>
		                     <input type="text" name="phone" class="form-control" validate="{required:true, rangelength:[11,12]}"/>
		                    </div>
		                </div>
		            </div>
		    		<div class='form-group'>
		                <label class="control-label col-md-3 col-xs-4 ">学生姓名: </label>
		                <div class="col-md-4 col-xs-8 bd-left">
		                	<div class="input-icon right">
		                     <i class="fa"></i>
		                     <input type="text" name="name" class="form-control"/>
		                    </div>
		                </div>
		            </div>
		            <div class="form-group">
		           		<label class="control-label col-md-3 col-xs-4 ">学生性别: </label>
		           		<div class="col-md-4 col-xs-8 bd-left">
		                	<div class="input-icon right">
		                    	<i class="fa"></i>
		                     	<select name="xb" class="form-control">
		                     		<option value="0">保密</option>
		                     		<option value="1">女</option>
		       						<option value="2">男</option>
		                     	</select>
		                    </div>
		                </div>
		       		</div>
		       		<div class="form-group">
		           		<label class="control-label col-md-3 col-xs-4 ">所在地区: </label>
		           		<div class="col-md-4 col-xs-8 bd-left">
		                	<div class="input-icon right">
		                    	<i class="fa"></i>
		                     	<select name="dqId" class="form-control">
		                     		<option value="-1">请选择</option>
		                     		<c:forEach var="dq" items="${dqList}">
			                        	<option value="${dq.id }">${dq.name }</option>
									</c:forEach>
		                     	</select>
		                    </div>
		                </div>
		       		</div>
		            <div class="form-group">
		           		<label class="control-label col-md-3 col-xs-4 ">所在学校: </label>
		           		<div class="col-md-4 col-xs-8 bd-left">
		                	<div class="input-icon right">
		                    	<i class="fa"></i>
		                     	<select name="xxId" class="form-control">
		                     		<option value="-1">请选择</option>
		                     		<c:forEach var="xx" items="${xxList}">
			                        	<option value="${xx.id }">${xx.name }</option>
									</c:forEach>
		                     	</select>
		                    </div>
		                </div>
		       		</div>
		       		<div class="form-group">
		           		<label class="control-label col-md-3 col-xs-4 ">所在年级: </label>
		           		<div class="col-md-4 col-xs-8 bd-left">
		                	<div class="input-icon right">
		                    	<i class="fa"></i>
		                     	<select name="nj" class="form-control">
		                     		<option value="-1">请选择</option>
		                     		<option value="1">1年级</option>
		       						<option value="2">2年级</option>
		       						<option value="3">3年级</option>
		       						<option value="4">4年级</option>
		       						<option value="5">5年级</option>
		       						<option value="6">6年级</option>
		       						<option value="7">7年级</option>
		       						<option value="8">8年级</option>
		       						<option value="9">9年级</option>
		       						<option value="10">10年级</option>
		       						<option value="11">11年级</option>
		       						<option value="12">12年级</option>
		                     	</select>
		                    </div>
		                </div>
		       		</div>
		       		<div class="form-group">
		           		<label class="control-label col-md-3 col-xs-4 ">所在班级: </label>
		           		<div class="col-md-4 col-xs-8 bd-left">
		                	<div class="input-icon right">
		                    	<i class="fa"></i>
		                     	<select name="bj" class="form-control">
		                     		<option value="-1">请选择</option>
		                     		<option value="1">1班</option>
		       						<option value="2">2班</option>
		       						<option value="3">3班</option>
		       						<option value="4">4班</option>
		       						<option value="5">5班</option>
		       						<option value="6">6班</option>
		       						<option value="7">7班</option>
		       						<option value="8">8班</option>
		       						<option value="9">9班</option>
		       						<option value="10">10班</option>
		       						<option value="11">11班</option>
		       						<option value="12">12班</option>
		       						<option value="13">13班</option>
		       						<option value="14">14班</option>
		       						<option value="15">15班</option>
		       						<option value="16">16班</option>
		       						<option value="17">17班</option>
		       						<option value="18">18班</option>
		       						<option value="19">19班</option>
		       						<option value="20">20班</option>
		                     	</select>
		                    </div>
		                </div>
		       		</div>
					<div class="form-group">
	                    <button type="submit" id="submit-btn"  class="btn btn-block red uppercase">提交</button>
	                </div>
		   		</div>
			</form>
		</section>
		<script src="${basePath }assets/common/require.js" data-main="${basePath }assets/txlapp/editLxr" type="text/javascript"></script>
	</body>
</html>
