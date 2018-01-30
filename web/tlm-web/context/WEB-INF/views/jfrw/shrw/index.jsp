<%@ page contentType="text/html;charset=UTF-8"%>

<style>
.portlet .actions .form-group {
	margin-bottom: 0;
}
</style>
<div class="portlet light">
	<div class="portlet-title">
		<div class="caption">
			<div id="searchInp">
			</div>
		</div>
		<div class="actions col-md-9">
           	<form action="#" class="form-horizontal " id="form_remark">
		    	<div class="form-body">
		    		<div class="row">
		    			<div class="col-md-3">
		    				<div class="form-group">
				           		<label class="control-label col-md-4">状态: </label>
				           		<div class="col-md-8">
				                	<div class="input-icon right">
				                    	<i class="fa"></i>
				                     	<select name="status" class="form-control" validate="{required:true}">
				                     		<option value="-1" selected="selected">全部</option>
				                     		<option value="1">待审核</option>
				                     		<option value="2">审核通过</option>
				                     		<option value="3">审核不通过</option>
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
       	<table class="table table-striped table-hover"></table>
	</div>
</div>
<script src="${page.basePath }assets/common/require.js" data-main="${page.basePath }assets/jfrw/shrw/main" type="text/javascript"></script>