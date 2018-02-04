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
	                <label class="control-label col-md-3">增长速率<span class="required">*</span></label>
	                <div class="col-md-4">
	                	<div class="input-icon right">
	                     	<i class="fa"></i>
	                     	<input type="number" name="zzsl" class="form-control" validate="{required:true,max: 1, min: 0.1}" value=0.6/>
	                    </div>
	                    <span class="help-block">积分的增长速率，越小增长速度越快。</span>
	                </div>
	            </div>
	            <div class="form-group">
	                <label class="control-label col-md-3">积分上限<span class="required">*</span></label>
	                <div class="col-md-4">
	                	<div class="input-icon right">
	                     	<i class="fa"></i>
	                     	<input type="number" name="jfsx" class="form-control" validate="{required:true,max: 30, min: 10}" value=10/>
	                    </div>
	                    <span class="help-block">积分增长的上限</span>
	                </div>
	            </div>
	   		</div>
	   		<div class="form-actions">
                <div class="row">
                    <div class="col-md-offset-3 col-md-9">
                        <a type="submit" class="btn blue save disabled"><i class="fa fa-save"></i> 保存 </a>
                        <a type="submit" class="btn yellow review"><i class="fa fa-save"></i> 预览 </a>
                    </div>
                </div>
            </div>
		</form>
		<div></div>
		<h3>积分增长规律</h3>
		<ul class="list-group jfgz-review">
            
        </ul>
    </div>
</div>
<script src="${page.basePath }assets/common/require.js" data-main="${page.basePath }assets/jfgz/main" type="text/javascript"></script>