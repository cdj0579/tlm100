<%@ page contentType="text/html;charset=UTF-8"%>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
    <h4 class="modal-title" id="title">迭代知识点信息</h4>
</div>
<div class="modal-body">
	<form action="#" class="form-horizontal " id="form_remark">
		<input type="hidden" name="id" value="${info.id }"/>
		<input type="hidden" name="modifiedId" value="${info.modifiedId }"/>
		<input type="hidden" name="modifiedId" value="${info.ndId }"/>
    	<div class="form-body">
    		<div class='form-group'>
                <label class="control-label col-md-3">名称<span class="required">*</span></label>
                <div class="col-md-4">
                	<div class="input-icon right">
                     <i class="fa"></i>
                     <input type="text" name="name" value="${info.name }" class="form-control" readonly="readonly"/>
                    </div>
                </div>
            </div>
            <div class='form-group'>
                <label class="control-label col-md-3">课时(分)<span class="required">*</span></label>
                <div class="col-md-4">
                	<div class="input-icon right">
                     <i class="fa"></i>
                     <input type="number" name="ks" value="${info.ks }" class="form-control" readonly="readonly"/>
                    </div>
                </div>
            </div>
            <div class='form-group'>
                <label class="control-label col-md-3">难度<span class="required">*</span></label>
                <div class="col-md-4">
                	<div class="input-icon right">
                    	<i class="fa"></i>
                     	<input type="text" name="ndName" value="${info.ndName }" class="form-control" readonly="readonly"/>
                    </div>
                </div>
            </div>
            <div class='form-group'>
                <label class="control-label col-md-3">说明</label>
                <div class="col-md-8">
                	<div class="input-icon right">
                    	<i class="fa"></i>
                    	<textarea row="2" name="desc" class="form-control" readonly="readonly"> ${info.desc }</textarea>
                    </div>
                </div>
            </div>
   		</div>
	</form>
</div>
<div class="modal-footer">
    <button type="button" class="btn dark btn-outline" data-dismiss="modal">关闭</button>
    <button type="submit" class="btn green save">保存</button>
</div>