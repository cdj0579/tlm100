<%@ page contentType="text/html;charset=UTF-8" %>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
    <h4 class="modal-title" id="title">修改密码</h4>
</div>
<div class="modal-body">
	<form action="#" class="form-horizontal " id="form_remark">
    	<div class="form-body">
            <div class="form-group">
                <label class="control-label col-md-2">旧密码<span class="required">*</span></label>
                <div class="col-md-8">
                	<div class="input-icon right">
                     <i class="fa"></i>
                     <input type="password" name="oldPassword" class="form-control" placeholder="" validate="{required:true, minlength: 6}">
                    </div>
                    <span class="help-block">请输入原始的密码.</span>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-md-2">新密码<span class="required">*</span></label>
                <div class="col-md-8">
                	<div class="input-icon right">
                     <i class="fa"></i>
                     <input type="password" name="password" id="submit_form_password" class="form-control" placeholder="" validate="{required:true, minlength: 6}">
                    </div>
                    <span class="help-block">请输入新的密码.</span>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-md-2">确认密码<span class="required">*</span></label>
                <div class="col-md-8">
                	<div class="input-icon right">
                     <i class="fa"></i>
                     <input type="password" name="rpassword" class="form-control" placeholder="" validate="{required:true, equalTo: '#submit_form_password'}"">
                    </div>
                    <span class="help-block">请重新输入新的密码.</span>
                </div>
            </div>
   		</div>
	</form>
</div>
<div class="modal-footer">
    <button type="button" class="btn dark btn-outline" data-dismiss="modal">关闭</button>
    <button type="submit" class="btn green save">保存</button>
</div>