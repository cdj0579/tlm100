<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="com.unimas.web.utils.*" %>
<!DOCTYPE html>
<!--[if IE 8]> <html class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html>
<!--<![endif]-->
<head>
<%
PageUtils.initGuest(request);
%>
<title>${page.title }</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<tiles:insertAttribute name="global-styles"/>
<link href="${page.basePath }assets/login/css/login.css" rel="stylesheet" type="text/css" />
<tiles:insertAttribute name="theme-global-styles"/>
<link href="${page.basePath }assets/common/theme/default/css/style.css" rel="stylesheet" id="style_default" type="text/css" />
<!--[if lt IE 9]>
<script src="${page.basePath }assets/global/plugins/respond.min.js"></script>
<script src="${page.basePath }assets/global/plugins/excanvas.min.js"></script> 
<![endif]-->
<script>
var basePath = "${page.basePath }";
</script>
<script src="${page.basePath }assets/common/require.js" defer async="true" data-main="${page.basePath }assets/login/js/login" type="text/javascript"></script>
</head>
<body class="login bg-blue-chambray bg-font-blue-chambray">
	<!-- BEGIN LOGO -->
        <div class="logo">
            <span class="logo-title">${page.title }</span>
            <!-- <a href="javascript:;">
                <img src="../assets/pages/img/logo-big.png" alt="" /> 
            </a> -->
        </div>
        <!-- END LOGO -->
        <!-- BEGIN LOGIN -->
        <div class="content">
            <!-- BEGIN LOGIN FORM -->
            <form class="login-form bg-font-white" action="" method="post">
                <h3 class="form-title">用户登录</h3>
                <div class="alert alert-danger display-hide">
                    <button class="close" data-close="alert"></button>
                    <span> 请输入正确的用户名和密码. </span>
                </div>
                <div class="form-group">
                    <!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
                    <label class="control-label visible-ie8 visible-ie9">用户名</label>
                    <div class="input-icon">
                        <i class="fa fa-user"></i>
                        <input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="用户名" name="username" /> </div>
                </div>
                <div class="form-group">
                    <label class="control-label visible-ie8 visible-ie9">密码</label>
                    <div class="input-icon">
                        <i class="fa fa-lock"></i>
                        <input class="form-control placeholder-no-fix" type="password" autocomplete="off" placeholder="密码" name="password" /> </div>
                </div>
                <div class="form-actions">
                    <label class="checkbox">
                        <input type="checkbox" name="remember" value="1" /> 记住用户名 </label>
                    <!-- <a href="javascript:;" class="btn yellow pull-right" id="register-btn"> 注册 </a> -->
                    <button type="submit" class="btn green pull-right" style="margin-right: 15px;"> 登录 </button>
                </div>
                <!-- <div class="login-options">
                    <h4>Or login with</h4>
                    <ul class="social-icons">
                        <li>
                            <a class="facebook" data-original-title="facebook" href="javascript:;"> </a>
                        </li>
                        <li>
                            <a class="twitter" data-original-title="Twitter" href="javascript:;"> </a>
                        </li>
                        <li>
                            <a class="googleplus" data-original-title="Goole Plus" href="javascript:;"> </a>
                        </li>
                        <li>
                            <a class="linkedin" data-original-title="Linkedin" href="javascript:;"> </a>
                        </li>
                    </ul>
                </div>
                <div class="forget-password">
                    <h4>Forgot your password ?</h4>
                    <p> no worries, click
                        <a href="javascript:;" id="forget-password"> here </a> to reset your password. </p>
                </div>
                <div class="create-account">
                    <p> Don't have an account yet ?&nbsp;
                        <a href="javascript:;" id="register-btn"> Create an account </a>
                    </p>
                </div> -->
            </form>
            <!-- END LOGIN FORM -->
            <!-- BEGIN FORGOT PASSWORD FORM -->
            <form class="forget-form" action="" method="post">
                <h3>Forget Password ?</h3>
                <p> Enter your e-mail address below to reset your password. </p>
                <div class="form-group">
                    <div class="input-icon">
                        <i class="fa fa-envelope"></i>
                        <input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="Email" name="email" /> </div>
                </div>
                <div class="form-actions">
                    <button type="button" id="back-btn" class="btn grey-salsa btn-outline"> Back </button>
                    <button type="submit" class="btn green pull-right"> Submit </button>
                </div>
            </form>
            <!-- END FORGOT PASSWORD FORM -->
            <!-- BEGIN REGISTRATION FORM -->
            <form class="register-form" action="${page.basePath }user/teacher/register" method="post" enctype="multipart/form-data">
                <h3>注册教师账号</h3>
                <p> 请填写您的账号信息: </p>
                <div class="form-group">
                    <label class="control-label">用户名:</label>
                    <div class="input-icon">
                        <i class="fa fa-user"></i>
                        <input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="请输入用户名" name="username" /> </div>
                </div>
                <div class="form-group">
                    <label class="control-label">密码:</label>
                    <div class="input-icon">
                        <i class="fa fa-lock"></i>
                        <input class="form-control placeholder-no-fix" type="password" autocomplete="off" id="register_password" placeholder="请输入密码" name="password" /> </div>
                </div>
                <div class="form-group">
                    <label class="control-label">确认密码:</label>
                    <div class="controls">
                        <div class="input-icon">
                            <i class="fa fa-check"></i>
                            <input class="form-control placeholder-no-fix" type="password" autocomplete="off" placeholder="请重新输入" name="rpassword" /> </div>
                    </div>
                </div>
                <p> 请填写您的个人信息: </p>
                <div class="form-group">
                    <label class="control-label">姓名:</label>
                    <div class="input-icon">
                        <i class="fa fa-font"></i>
                        <input class="form-control placeholder-no-fix" type="text" placeholder="请输入姓名" name="name"/> </div>
                </div>
                <div class="form-group">
                    <!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
                    <label class="control-label">授课地址:</label>
                    <div class="input-icon">
                        <i class="fa fa-envelope"></i>
                        <input class="form-control placeholder-no-fix" type="text" placeholder="请输入授课地址" name="skdz" /> </div>
                </div>
                <div class="form-group">
                    <label class="control-label">学科</label>
                    <select name="kmId"class="select2 form-control">
                        <c:forEach var="km" items="${kmList}">
                        	<option value="${km.id }">${km.name }</option>
						</c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label class="control-label">上传教师资格证</label>
                    <input type="file" placeholder="请上传您的教师资格证" name="jszgz" />
                </div>
                <div class="form-group">
                    <label class="control-label">上传等级证书</label>
                    <input type="file" placeholder="请上传您的等级证书" name="djzs" />
                </div>
                <div class="form-group">
                    <label class="control-label">上传荣誉证书</label>
                    <input type="file" placeholder="请您的荣誉证书" name="ryzs" /> 
                </div>
                <!-- <div class="form-group">
                    <label>
                        <input type="checkbox" name="tnc" /> 我同意
                        <a href="javascript:;"> 本团队服务 </a> 和
                        <a href="javascript:;"> 开发协议 </a>
                    </label>
                    <div id="register_tnc_error"> </div>
                </div> -->
                <div class="form-actions">
                    <button id="register-back-btn" type="button" class="btn grey-salsa btn-outline"> 取消 </button>
                    <button type="submit" id="register-submit-btn" class="btn green pull-right"> 注册 </button>
                </div>
            </form>
            <!-- END REGISTRATION FORM -->
        </div>
        <div class="copyright"> ${page.footerMsg} </div>
</body>
</html>