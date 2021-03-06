<%@ page contentType="text/html;charset=UTF-8"%>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
    <h4 class="modal-title">添加字典</h4>
</div>
<div class="modal-body">
    <div class="row">
        <div class="col-md-12">
            <form id="zdadd_form_id" class="form-horizontal">
				<div class="form-group">
					<label class="control-label col-md-3">字典名称<span class="required">*</span></label>
					<div class="col-md-5">
						<div class="input-icon right">
                        	<i class="fa"></i>
							<input name="value" type="text" validate="{required:true}" class="form-control">
                        </div>
						<span class="help-block">请输入想要添加的字典名称。</span>
					</div>
				</div>
			</form>
        </div>
    </div>
</div>
<div class="modal-footer">
    <button type="button" class="btn default" data-dismiss="modal">取消</button>
    <button type="button" class="btn blue save-zd">确定</button>
</div>