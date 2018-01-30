<%@ page contentType="text/html;charset=UTF-8"%>

<style>
.portlet .actions .form-group {
	margin-bottom: 0;
}
</style>
<div class="portlet light">
	<div class="portlet-title">
		<div class="caption">发布任务</div>
		<div class="actions">
			<a class="btn blue save"><i class="fa fa-edit"></i> 发布任务</a>
		</div>
	</div>
	<div class="portlet-body">
		<form action="#" class="form-horizontal " id="form_remark">
			<div class="form-body">
				<div class='form-group'>
					<label class="control-label col-md-3">任务名称: <span class="required">*</span></label>
					<div class="col-md-4">
						<div class="input-icon right">
							<i class="fa"></i> 
							<input type="text" name="name" class="form-control" validate="{required:true}" />
						</div>
						<span class="help-block">请填写任务的名称。</span>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3">任务类型: <span class="required">*</span></label>
					<div class="col-md-4">
						<div class="input-icon right">
							<i class="fa"></i> 
							<div class="checkbox-list">
                                <label class="checkbox-inline">
                                    <input type="checkbox" name="type" value="0"> 习题任务 </label>
                                <label class="checkbox-inline">
                                    <input type="checkbox" name="type" value="1"> 知识点任务 </label>
                                <label class="checkbox-inline">
                                    <input type="checkbox" name="type" value="2"> 专题任务 </label>
                            </div>
						</div>
						<span class="help-block">请选择任务的类型,可多选。</span>
					</div>
				</div>
				<div class="form-group hide" id="select_zt_item">
					<label class="control-label col-md-3">专题: <span class="required">*</span></label>
					<div class="col-md-4">
						<div class="input-icon left input-group">
		                    <span class="control-label display-zt" style="padding: 0 15px;">0</span>
		                    <span class="input-group-btn" style="vertical-align: inherit;font-size: inherit;">
			                	<a class="btn blue select-zt"> 选择</a>
			               	</span>
	                    </div>
						<span class="help-block">请选择需要发布任务的专题。</span>
					</div>
				</div>
				<div class="form-group hide" id="select_zsd_item">
					<label class="control-label col-md-3">知识点: <span class="required">*</span></label>
					<div class="col-md-4">
						<div class="input-icon left input-group">
		                    <span class="control-label display-zsd" style="padding: 0 15px;">0</span>
		                    <span class="input-group-btn" style="vertical-align: inherit;font-size: inherit;">
			                	<a class="btn blue select-zsd"> 选择</a>
			               	</span>
	                    </div>
						<span class="help-block">请选择需要发布任务的专题。</span>
					</div>
				</div>
	       		<div class="form-group">
					<label class="control-label col-md-3">积分值: <span class="required">*</span></label>
					<div class="col-md-4">
						<div class="input-icon right">
							<i class="fa"></i> 
							<input type="number" name="jf" class="form-control" validate="{required:true, max: 10, min:1}" />
						</div>
						<span class="help-block">请设置单次完成任务可以获取的积分值。</span>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3">完成次数: <span class="required">*</span></label>
					<div class="col-md-4">
						<div class="input-icon right">
							<i class="fa"></i> 
							<input type="number" name="maxNum" class="form-control" validate="{required:true, max: 10, min:1}" />
						</div>
						<span class="help-block">请设置任务可以被完成的次数。</span>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3">任务描述: <span class="required">*</span></label>
					<div class="col-md-6">
						<div class="input-icon right">
							<i class="fa"></i>
							<textarea row="3" name="desc" class="form-control" validate="{required:true}"></textarea>
						</div>
						<span class="help-block">请填写任务的需求描述。</span>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3"></label>
					<div class="col-md-6">
						<a class="btn blue save"><i class="fa fa-edit"></i> 发布任务</a>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>
<script src="${page.basePath }assets/common/require.js" data-main="${page.basePath }assets/jfrw/fbrw/main" type="text/javascript"></script>