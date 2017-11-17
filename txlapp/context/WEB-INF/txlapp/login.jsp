<%@ page contentType="text/html;charset=UTF-8"%>
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
        <link href="../../assets/common/favicon.ico" type="image/x-icon" rel="icon" />
		<link href="../../assets/global/plugins/font-opensans/Open-Sans.css" rel="stylesheet" type="text/css" />
		<link href="../../assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
		<link href="../../assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
        <link href="../../assets/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
		<link href="../../assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="../../assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
       	<link href="../../assets/global/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />
		<style type="text/css">
		
		.form-horizontal .heading{
		    display: block;
		    font-size: 35px;
		    font-weight: bold;
		    color:#0fc054;
		    padding: 35px 0 20px 35px;
		    border-bottom: 1px solid #f0f0f0;
		    margin-bottom: 25px;
		}
		.form-horizontal .form-group{
		    padding: 0 40px;
		    margin: 0 0 25px 0;
		    position: relative;
		}
		.form-horizontal .form-control{
		    background: #f0f0f0;
		    border: none;
		    border-radius: 20px;
		    box-shadow: none;
		    padding: 0 20px 0 45px;
		    height: 40px;
		    transition: all 0.3s ease 0s;
		}
		.form-horizontal .form-control:focus{
		    background: #e0e0e0;
		    box-shadow: none;
		    outline: 0 none;
		}
		.form-horizontal .form-group i{
		    position: absolute;
		    top: 12px;
		    left: 60px;
		    font-size: 17px;
		    color: #c8c8c8;
		    transition : all 0.5s ease 0s;
		}
		.form-horizontal .form-control:focus + i{
		    color: #00b4ef;
		}
		
		.form-horizontal .btn{
		    font-size: 14px;
		    color: #fff;
		    background: #0fc054;
		    padding: 10px 25px;
		    border: none;
		    text-transform: capitalize;
		    transition: all 0.5s ease 0s;
		}
		.logo {
			padding-left:40px;
			background:url("../../assets/txlapp/img/logo.gif") no-repeat ;
		}
		.help-block {
			margin-top:0;
			margin-bottom:5px;
		}
		</style>
	</head>
    <body>
       <section>
	       <div class="container">
				<div class="row">
					<form class="form-horizontal" method="post" >
						<span class="heading"><span class="logo"></span>共享名片夹</span>
						<div class="form-group">
							<input type="text" class="form-control" name="username" placeholder="用户名">
							<i class="fa fa-user"></i>
						</div>
						<div class="form-group ">
							<input type="password" class="form-control" name="password"  placeholder="密　码">
							<i class="fa fa-lock"></i>							
						</div>
						<div class="form-group">
							<button type="submit" class="btn btn-block btn-default">登录</button>
						</div>
					</form>
				</div>
			</div>
		</section>
		<script src="../../assets/common/require.js" data-main="../../assets/txlapp/login/js/login" type="text/javascript"></script>
	</body>
</html>