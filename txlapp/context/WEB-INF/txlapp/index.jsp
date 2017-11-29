<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.unimas.web.utils.*" %>

<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
	<!--<![endif]-->
    <head>
        <meta charset="utf-8" />
        <title>共享名片夹</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1" name="viewport" />
        <meta content="" name="description" />
        <meta content="" name="author" />
        <!-- BEGIN GLOBAL MANDATORY STYLES -->
        <%PageUtils.initBasePath(request); %>
        <script>
			var basePath = "${basePath }";
		</script>
        <link href="${basePath }assets/common/favicon.ico" type="image/x-icon" rel="icon" />
		<link href="${basePath }assets/global/plugins/font-opensans/Open-Sans.css" rel="stylesheet" type="text/css" />
		<link href="${basePath }assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
		<link href="${basePath }assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
		<link href="${basePath }assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="${basePath }assets/global/plugins/mui/css/mui.min.css" rel="stylesheet" type="text/css" />
        <link href="${basePath }assets/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
       	<link href="${basePath }assets/global/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />
		<link href="${basePath }assets/txlapp/index/css/index.css" rel="stylesheet" type="text/css" />
		<style type="text/css">
			.toast-top-center,.toast-top-right,.toast-top-left {
			  	top: 138px;
			}
			.col-xs-4, .col-sm-4 ,.col-md-4{
				padding:0 3px;
			}
			.btn-sub {
				color:#FFFFFF;
				font-weight:bold ;
				background-color: green;
			}
			.label-block {
				padding: 15px 0 5px 10px;
				color:#FFFFFF;
				font-weight:bold ;
				font-size:18px ;
				line-height: 25px;
			}
			
			.row{
				margin-right: 0;
				margin-left:0 ;
			}
			.td,.th{
				line-height: 40px;
				color: #8f8f94;
				float: left;
				font-size: 16px;
				width:33.333%;
			}
			.th{
				background-color: #000000;
				color: #FFFFFF;
				text-align: center;
			}
			.td {
				text-indent: 5px;
			}
			.p-info{
				padding: 0 10px;
				color: #8f8f94;
				text-indent: 15px;
				margin-bottom: 8px;
			}
			.odd { background-color: #f9f9f1;}
			.even{ background-color: #f3f3fa;}
			.odd,.even{border: #e5e5e5 1px solid;}
			.emptyMsg {
				text-align: center;
				line-height: 40px;
				margin-top: 100px;
				color: #8f8f94;
				font-size: 14px;
			}
			.fa { margin-right: 3px;}
			.fa-user{
				color:#007aff
			}    
			.fa-female{
				color:#e73d4a
			}
			.fa-sort-desc{margin-right: 0;}
			.nav-tabs,.mui-bar{ background-color: #303642; }
			.mui-bar{box-shadow: 0 0 0 #303642; height:46px;}
			a,a:active,a:hover,.nav-tabs>li.active>a,.mui-title,.mui-bar .mui-btn-link { color: #FFFFFF; }
			.nav-tabs>li.active>a, .nav-tabs>li.active>a:focus, .nav-tabs>li.active>a:hover{
				border:0;
				color: #FFFFFF; 
				background-color: #303642;
			}
		</style>
	</head>
    <body>
    	<header class="mui-bar mui-bar-nav" style="padding-right: 15px;">
			<button type="button" style="display: none;" class="mui-left mui-action-back mui-btn  mui-btn-link mui-btn-nav mui-pull-left">
				<span class="mui-icon mui-icon-left-nav"></span>
			</button>
			<h1 class="mui-title">共享名片夹</h1>
			<div class="mui-pull-right mui-btn-link">
			<div class="dropdown"> 
			<a  data-toggle="dropdown" data-hover="dropdown" data-close-others="true"><span class="mui-icon mui-icon-bars"></span></a>
			<ul class="dropdown-menu dropdown-menu-default">
                <li>
                    <a href="${basePath }app/setPwd"><i class="icon-key"></i> 密码修改 </a>
                </li>
                <li class="divider"> </li>
                <li>
                    <a href="${basePath }app/logout"><i class="icon-logout"></i> 退出 </a>
                </li>
             </ul>
             </div>
             </div>
		</header>
		<nav style="margin-top: 45px;">
			<ul class="nav nav-tabs " id="myTab">
			  	<li class="active col-xs-4 col-sm-4 col-md-4"><a href="#txl" data-toggle="tab"><i class="fa fa-sort-desc"></i>通讯录</a></li>
			  	<li class="col-xs-4 col-sm-4 col-md-4"><a href="#ygz" data-toggle="tab"><i class="fa"></i> 已关注</a></li>
			  	<li class="col-xs-4 col-sm-4 col-md-4"><a href="#ygx" data-toggle="tab"><i class="fa"></i> 已共享</a></li>
			</ul>
		</nav>
       	<section >
       		<div class="tab-content" >
			  	<div class="tab-pane active" id="txl">
			  		<div>
				  		<!--<div class="div_ds">
				  			<div>
				  				<button name="guanzhu" class="btn btn-lg anniu">关注</button>
				  				<button name="gongxing" class="btn btn-lg anniu">共享</button>
				  			</div>
				  			<div class="div_neirong">
					  			<div class="txl">
					  				<input type="hidden" name="phone" value="13819212336" />
					  				<div class="tp"></div>
					  				<p class="name">吴磊</p>
					  				<p>滨江区</p>
					  				<p>XX中学</p>
					  				<p>**年级</p>
					  			</div>
					  			<div>备注：</div>
					  			<p class="beizhu">备注备注备注备注备注备注备注备注备注备注备注备注</p>
				  			</div>
				  		</div>-->
			  		</div>
			  		<button name="next" class="btn btn-block btn-lg btn-danger margintop" style="display: none;">下一个</button>
			  	</div>
			  	<div class="tab-pane" id="ygz">
			  		<!--<div class="row ">
		  				<div class="th">姓名</div>
			  			<div class="th">学校</div>
			  			<div class="th">班级</div>
			  		</div>
			  		<div class="row odd">
			  			<div>
			  				<div class="td"><i class="fa fa-user"></i>吴磊吴磊</div>
				  			<div class="td">XX中学</div>
				  			<div class="td">1*年级</div>
				  			<div class="clearfix"></div>
			  			</div>
			  			<div class="p-info">备注：备注备注备注备注备注备注备注备注备注备注备注备注</div>
			  		</div>
			  		<div class="row even">
			  			<div>
			  				<div class="td"><i class="fa fa-female"></i> 吴磊</div>
				  			<div class="td">XX中学</div>
				  			<div class="td">1*年级</div>
				  			<div class="clearfix"></div>
			  			</div>
			  			<div class="p-info">备注：备注备注备注备注备注备注备注备注备注备注备注备注</div>
			  		</div>-->
			  	</div>
			  	<div class="tab-pane" id="ygx">
			  		
			  	</div>
			</div>
			<div id="card"></div>
			<div class="bz_content" style="display: none;">
				<div class="div_ds">
					<label class="label-block">请对“吴磊”填写备注</label>
					<div>
						<textarea name="info" rows="10" maxlength="200" placeholder="内容限定200字符" ></textarea>
					</div>	
				</div>
				<button class="btn btn-block btn-lg btn-sub margintop">提交关注</button>
			</div>
		</section>
		
		
		
       	<!--[if lt IE 9]>
			<script src="../assets/global/plugins/respond.min.js"></script>
			<script src="../assets/global/plugins/excanvas.min.js"></script> 
		<![endif]-->
		<script src="${basePath }assets/common/require.js" data-main="${basePath }assets/txlapp/index/js/index" type="text/javascript"></script>
	</body>
</html>