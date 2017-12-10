<%@ page contentType="text/html;charset=UTF-8"%>
<%
	request.setAttribute("host", request.getLocalAddr());
	request.setAttribute("port", request.getLocalPort());
%>
<style>
.portlet .actions .form-group {
	margin-bottom: 0;
}
.form .form-section, .portlet-form .form-section {
	font-weight: 400;
    margin: 7px 0 8px;
    padding-bottom: 5px;
    border-bottom: 1px solid #e7ecf1;
}
</style>
<div class="portlet light ">
	<div class="portlet-title">
		<div class="caption">
			<span class="caption-subject bold">分享页面的二维码</span>
			<span class="caption-helper">联系人注册</span>
		</div>
	</div>
    <div class="portlet-body">
    	<form action="#" class="form-horizontal ">
	    	<div class="form-body">
	    		<div class="row">
		    		<div class="col-md-3" style="width: 320px;">
		    			<img alt="" src="${page.basePath }services/qrcode/lxrzc?lryId=${lryId }">
		    		</div>
		    		<div class="col-md-8" style="padding-top: 50px;">
			    		<div class='form-group'>
			                <label class="control-label col-md-2">图片地址:</label>
			                <div class="col-md-8">
			                     <p class="form-control-static">http://${host }:${port }${page.basePath }services/qrcode/lxrzc?lryId=${lryId }</p>
			                </div>
			            </div>
			            <div class='form-group'>
			            	<label class="control-label col-md-2"></label>
			                <div class="col-md-8">
				                <a href="${page.basePath }services/qrcode/lxrzc/download?lryId=${lryId }" class="btn blue download"><i class="fa fa-download"></i> 下载图片 </a>
			                </div>
			            </div>
		    		</div>
		    	</div>
	    	</div>
	    	<div class="form-actions">
                <div class="row">
                    <div class="col-md-offset-3 col-md-9">
                        
                    </div>
                </div>
            </div>
	    </form>
    </div>
</div>
<script src="${page.basePath }assets/common/require.js" data-main="${page.basePath }assets/lxrgl/admin" type="text/javascript"></script>