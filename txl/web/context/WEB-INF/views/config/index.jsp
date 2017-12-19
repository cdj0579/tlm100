<%@ page contentType="text/html;charset=UTF-8"%>
<style>
.portlet .actions .form-group {
	margin-bottom: 0;
}
</style>
<div class="portlet light ">
    <div class="portlet-body">
    	<form action="#" class="form-horizontal ">
	    	<div class="form-body">
	            <div class="form-group">
	                <label class="control-label col-md-3">分配周期<span class="required">*</span></label>
	                <div class="col-md-8">
	                	<div class="input-icon right">
	                     	<i class="fa"></i>
	                     	<input type="number" name="zhouqi" class="form-control" validate="{required:true,max: 30, min: 1}"/>
	                    </div>
	                    <span class="help-block">分配规则运行的周期，单位是天</span>
	                </div>
	            </div>
	            <div class="form-group">
	                <label class="control-label col-md-3">关注时限<span class="required">*</span></label>
	                <div class="col-md-8">
	                	<div class="input-icon right">
	                     	<i class="fa"></i>
	                     	<input type="number" name="shichang" class="form-control" validate="{required:true,max: 30, min: 1}"/>
	                    </div>
	                    <span class="help-block">关注后联系人被锁定的时限，单位是天</span>
	                </div>
	            </div>
	            <div class="form-group">
	                <label class="control-label col-md-3">关注上限<span class="required">*</span></label>
	                <div class="col-md-8">
	                	<div class="input-icon right">
	                     	<i class="fa"></i>
	                     	<input type="number" name="gzShangxian" class="form-control" validate="{required:true,max: 100, min: 1}"/>
	                    </div>
	                    <span class="help-block">每个使用者关注的联系人上限</span>
	                </div>
	            </div>
	            <div class="form-group">
	                <label class="control-label col-md-3">录入上限<span class="required">*</span></label>
	                <div class="col-md-8">
	                	<div class="input-icon right">
	                     	<i class="fa"></i>
	                     	<input type="number" name="shangxian" class="form-control" validate="{required:true,max: 499, min: 1}"/>
	                    </div>
	                    <span class="help-block">通过分享注册页面录入的联系人上限</span>
	                </div>
	            </div>
	   		</div>
	   		<div class="form-actions">
                <div class="row">
                    <div class="col-md-offset-3 col-md-9">
                        <a type="submit" class="btn blue save disabled"><i class="fa fa-save"></i> 保存 </a>
                    </div>
                </div>
            </div>
		</form>
    </div>
</div>
<script src="${page.basePath }assets/common/require.js" data-main="${page.basePath }assets/config/main" type="text/javascript"></script>