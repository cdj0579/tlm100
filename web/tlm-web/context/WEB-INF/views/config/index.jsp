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
	                <label class="control-label col-md-3">课时<span class="required">*</span></label>
	                <div class="col-md-4">
	                	<div class="input-icon right">
	                     	<i class="fa"></i>
	                     	<input type="number" name="ks_pt" class="form-control" validate="{required:true,max: 60, min: 30}" value="${(empty data.ks_pt)?45:data.ks_pt }"/>
	                    </div>
	                    <span class="help-block">一课时等于多少分钟。</span>
	                </div>
	            </div>
	            <div class="form-group">
	                <label class="control-label col-md-3">测试题量<span class="required">*</span></label>
	                <div class="col-md-4">
	                	<div class="input-icon right">
	                     	<i class="fa"></i>
	                     	<select name="cstl" class="form-control" validate="{required:true}">
	                     		<option value="10" ${(data.cstl == 10)?"selected='selected'":"" }>10题</option>
	                     		<option value="20" ${(data.cstl == 20)?"selected='selected'":"" }>20题</option>
	                     		<option value="25" ${(data.cstl == 25)?"selected='selected'":"" }>25题</option>
	                     		<option value="50" ${(data.cstl == 30)?"selected='selected'":"" }>50题</option>
	                     	</select>
	                    </div>
	                    <span class="help-block">学生测试时默认生成的题量。</span>
	                </div>
	            </div>
	   		</div>
	   		<div class="form-actions">
                <div class="row">
                    <div class="col-md-offset-3 col-md-9">
                        <a type="submit" class="btn blue save"><i class="fa fa-save"></i> 保存 </a>
                    </div>
                </div>
            </div>
		</form>
    </div>
</div>
<script src="${page.basePath }assets/common/require.js" data-main="${page.basePath }assets/config/main" type="text/javascript"></script>