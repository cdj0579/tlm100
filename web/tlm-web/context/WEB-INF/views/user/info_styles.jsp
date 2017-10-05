<%@ page contentType="text/html;charset=UTF-8"%>

<link href="${page.basePath }assets/global/plugins/jquery.alert/jquery.alerts.css" rel="stylesheet" type="text/css" />
<link href="${page.basePath }assets/global/plugins/jquery-imgareaselect/css/imgareaselect-default.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	.img-position {
		/* position:relative; */
		width:100px;
		height:100px;
		margin-bottom:5px;
	}
	#upload-file{
		display:none;
	}

	.form-horizontal .checkbox, .form-horizontal .checkbox-inline, .form-horizontal .radio, .form-horizontal .radio-inline {
		padding-top: 0;
	}
	#gxd_top10>li {
		line-height:28px;
		font-size:14px;
	}
	#imghead{
		position:absolute;
	}
	#preview{
		overflow:hidden;
	 	position:relative;
		width:302px;
		height:202px;
		border: 1px solid #000;
	}
	.pre{
		position:relative;
		width:102px;
		height:102px;
		overflow:hidden;
		border: 1px solid #000;
	}
	
	.ShowZsImg{
		/* display:none; */
		position:relative;
		width:100%;
		height:200px;
		overflow:hidden;
		border: 1px solid #000;
		cursor: pointer;
	}
	#upload_djzs,#upload_jszgz,#upload_ryzs {
		display:none;
	}
	
</style>
