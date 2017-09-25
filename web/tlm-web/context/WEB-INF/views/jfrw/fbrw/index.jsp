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
							<select name="type" class="form-control" validate="{required:true}">
								<option value="1">习题任务</option>
								<option value="2">知识内容任务</option>
								<option value="3">专题内容任务</option>
							</select>
						</div>
						<span class="help-block">请选择任务的类型。</span>
					</div>
				</div>
				<div class="form-group hide">
					<label class="control-label col-md-3">科目地区: <span class="required">*</span></label>
					<div class="col-md-4">
						<div class="input-icon right">
							<i class="fa"></i> 
							<select name="dqId" class="form-control" validate="{required:true}">
							</select>
						</div>
						<span class="help-block">请选择知识点任务的科目地区。</span>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3">所属科目: <span class="required">*</span></label>
					<div class="col-md-4">
						<div class="input-icon right">
							<i class="fa"></i> 
							<select name="kmId" class="form-control" validate="{required:true}">
							</select>
						</div>
						<span class="help-block">请选择任务的所属科目。</span>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3">所属年级: <span class="required">*</span></label>
					<div class="col-md-4">
						<div class="input-icon right">
							<i class="fa"></i> 
							<select name="njId" class="form-control" validate="{required:true}">
							</select>
						</div>
						<span class="help-block">请选择任务的所属年级。</span>
					</div>
				</div>
				<div class="form-group hide">
					<label class="control-label col-md-3">学期: <span class="required">*</span></label>
					<div class="col-md-4">
						<div class="input-icon right">
							<i class="fa"></i> 
							<select name="xq" class="form-control" validate="{required:true}">
								<option value="1">上学期</option>
								<option value="2">下学期</option>
							</select>
						</div>
						<span class="help-block">请选择知识点任务的所属学期。</span>
					</div>
				</div>
				<div class="form-group hide">
					<label class="control-label col-md-3">章节: <span class="required">*</span></label>
					<div class="col-md-4">
						<div class="input-icon right">
							<i class="fa"></i> 
							<input type="text" name="zjId" class="form-control" validate="{required:true}" />
						</div>
						<span class="help-block">请选择知识点任务的所属章节。</span>
					</div>
				</div>
				<div class="form-group hide">
	           		<label class="control-label col-md-3">期中/期末: <span class="required">*</span></label>
	           		<div class="col-md-4">
	                	<div class="input-icon right">
	                    	<i class="fa"></i>
	                     	<select name="qzqm" class="form-control" validate="{required:true}">
	                     		<option value="1">期中</option>
	                     		<option value="2">期末</option>
	                     	</select>
	                    </div>
	                    <span class="help-block">请选择专题任务的所属期中或期末。</span>
	                </div>
	       		</div>
	       		<div class="form-group">
					<label class="control-label col-md-3">积分值: <span class="required">*</span></label>
					<div class="col-md-4">
						<div class="input-icon right">
							<i class="fa"></i> 
							<input type="number" name="jf" class="form-control" validate="{required:true, max: 10, min:1}" />
						</div>
						<span class="help-block">请设置完成任务可以获取的积分值。</span>
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