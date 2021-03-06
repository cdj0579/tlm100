<%@ page contentType="text/html;charset=UTF-8"%>
<style>
.portlet .actions .form-group {
	margin-bottom: 0;
}
</style>
<div class="portlet light ">
    <div class="portlet-title">
        <div class="caption">
            <a class="btn blue add"><i class="fa fa-plus"></i> 添加</a>
        </div>
        <div class="actions col-md-8">
            <form action="#" class="form-horizontal " id="form_remark">
		    	<div class="form-body">
		    		<div class="row">
		    			<div class="col-md-3">
		    				<div class="form-group">
				           		<label class="control-label col-md-4">地区: </label>
				           		<div class="col-md-8">
				                	<div class="input-icon right">
				                    	<i class="fa"></i>
				                     	<select name="dqId" class="form-control" validate="{required:true}">
				                     	</select>
				                    </div>
				                </div>
				       		</div>
		    			</div>
		    			<div class="col-md-3">
		    				<div class="form-group">
				           		<label class="control-label col-md-4">科目: </label>
				           		<div class="col-md-8">
				                	<div class="input-icon right">
				                    	<i class="fa"></i>
				                     	<select name="kmId" class="form-control" validate="{required:true}">
				                     	</select>
				                    </div>
				                </div>
				       		</div>
		    			</div>
		    			<div class="col-md-3">
		    				<div class="form-group">
				           		<label class="control-label col-md-4">年级: </label>
				           		<div class="col-md-8">
				                	<div class="input-icon right">
				                    	<i class="fa"></i>
				                     	<select name="njId" class="form-control" validate="{required:true}">
				                     	</select>
				                    </div>
				                </div>
				       		</div>
		    			</div>
		    			<div class="col-md-3">
		    				<div class="form-group">
				           		<label class="control-label col-md-4">学期: </label>
				           		<div class="col-md-8">
				                	<div class="input-icon right">
				                    	<i class="fa"></i>
				                     	<select name="xq" class="form-control" validate="{required:true}">
				                     		<option value="1" selected="selected">上学期</option>
				                     		<option value="2">下学期</option>
				                     	</select>
				                    </div>
				                </div>
				       		</div>
		    			</div>
		    		</div>
		   		</div>
			</form>
        </div>
    </div>
    <div class="portlet-body">
        <div class="row">
			<div class="col-md-6" id="searchInp">
     		</div>
		</div>
		<table class="table table-striped table-hover table-checkable order-column" id="zsd_table"></table>
    </div>
</div>
<script src="${page.basePath }assets/common/require.js" data-main="${page.basePath }assets/zs/zsd/js/main" type="text/javascript"></script>