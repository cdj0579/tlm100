<%@ page contentType="text/html;charset=UTF-8" %>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
    <h4 class="modal-title" id="title">对接4A平台</h4>
</div>
<div class="modal-body">
	<form action="#" class="form-horizontal " id="form_remark">
    	<div class="form-body">
    		<h4 class="block">与4A平台单点登录</h4>
            <div class="form-group">
                <label class="col-md-3 control-label">开启单点登录<span class="required">*</span></label>
                <div class="col-md-8">
                	<input type="checkbox" name="ssoEnable" class="make-switch" data-size="normal" data-on-text="开启" data-off-text="关闭">
                </div>
            </div>
            <div class="form-group hide">
                <label class="control-label col-md-3">单点登录服务地址<span class="required">*</span></label>
                <div class="col-md-8">
                	<div class="input-icon right">
                     <i class="fa"></i>
                     <input type="text" name="wsdlUrl" class="form-control" placeholder="" validate="{required:true, url: true}">
                    </div>
                    <span class="help-block">请单点登录服务的地址，格式如：http://127.0.0.1/Venus4A/services/Venus4AService?wsdl</span>
                </div>
            </div>
            <div class="form-group hide">
                <label class="control-label col-md-3">4A平台登录页<span class="required">*</span></label>
                <div class="col-md-8">
                	<div class="input-icon right">
                     <i class="fa"></i>
                     <input type="text" name="loginUrl" class="form-control" placeholder="" validate="{required:true, url: true}"">
                    </div>
                    <span class="help-block">需要登录时，重定向的4A平台登录页.</span>
                </div>
            </div>
   		</div>
	</form>
</div>
<div class="modal-footer">
    <button type="button" class="btn dark btn-outline" data-dismiss="modal">关闭</button>
    <button type="submit" class="btn green save">保存</button>
</div>