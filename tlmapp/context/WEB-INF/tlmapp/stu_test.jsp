<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.unimas.web.utils.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <title></title>
    	<%PageUtils.initBasePath(request); %>
        <script>
			var basePath = "${basePath }";
			var totolNum = "${num }"; //习题总数；
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
			.form-horizontal .form-group {
			    margin-left: 0;
			    margin-right: 0;
			    margin-bottom: 0;
			}
			.control-label {
				text-align: right;
			    margin-bottom: 0;
			    padding-top: 7px;
			}
			.wt_answer{
				position:relative;
				margin-top: 20px;
			}
			.wt_answer>.row {
				padding: 5px 20px 0;
				margin-bottom: 10px;
			}
			
			.wt_footer {
				position:relative;
				margin-top: 20px;
				padding:0 15px;
			}
			.checkbox input[type=checkbox], 
			.checkbox-inline input[type=checkbox],
			.radio input[type=radio], 
			.radio-inline input[type=radio] {
				position:relative;
				margin-left:0px
			}
			.text-justify{
				text-indent:15px;
				color:#000000;
			}
			
			.text-justify > lable {
				font-size: 16px;
				font-style:oblique;
				font-weight: bold;
				color:#9a5b17;
			}
		</style>
	</head>
	<body> 
		<header class="mui-bar  mui-bar-nav" style="padding-right: 15px;">
			<button type="button" class="mui-left mui-action-back mui-btn  mui-btn-link mui-btn-nav mui-pull-left">
				<span class="mui-icon mui-icon-left-nav"></span>
			</button>
			<h1 class="mui-title">测试内容</h1>
		</header>
		<div class="container-fluid " style="margin-top: 55px;">
		 	<c:if  test= "${msg != null }">
			 	<div class="alert alert-info" style="margin-top: 30px;">
				    	信息！当前题库中暂无该学科的测试题。
				</div>
		 	</c:if>
		 	<c:if  test= "${msg == null }">
		 	<div>
			 	<div class="pull-left ">
			 		总计 ${num } 题选择题
			 	</div>
			 	<div class="pull-right ">
			 		计时：<span id="time">00:00</span>
			 	</div>
		 		<div class="clearfix"></div>
			</div>
		 	<div class="wt">
			 	<div  id="wt1">
			 		<div class="row">
			 			<div class="col-xs-8 col-md-8">
			 				<p class="text-justify " ><label>1：</label> ${data.name }</p>
			 			</div>
			 			<div style="margin-top: 40px;" class="col-xs-4 col-md-4 ">
			 				<input type="hidden" name='zhuguan' value="0">
		 					<button type="button" v="1" class="btn-sm btn-block uppercase" data-toggle="button">不&nbsp;&nbsp;&nbsp;&nbsp;会 </button>
		 					<button type="button" v="2" class="btn-sm btn-block uppercase" data-toggle="button">不确定 </button>
			 			</div>
			 		</div>
			 		<div class="wt_answer">
			 			<c:forEach var="answer" items="${data.ansList }">
				 			<div class="row answer-height">
						        <label for="radio${answer.option }">
						        	<input type="radio" id="radio${answer.option }" value="${answer.option }" name="answer1">
						        	${answer.option }: ${answer.name }
						        </label>
							</div>
			 			</c:forEach>
				 	</div>
			 	</div>
		 	</div>
			<footer class="wt_footer">
	         	<button type="button" name="prev" style="display:none" class="col-xs-4 col-md-4 btn-sm btn-info pull-left uppercase">上一题</button>
	            <button type="button" name="next" class="col-xs-4 col-md-4 btn-sm btn-info pull-right uppercase">下一题</button>
			</footer>
			</c:if>
		</div>
		<script src="${basePath }assets/common/require.js" data-main="${basePath }assets/user/stu_test" type="text/javascript"></script>
</head>
<body>
	
</body>
</html>