<%@ page contentType="text/html;charset=UTF-8"%>

<style>
.portlet .actions .form-group {
	margin-bottom: 0;
}
</style>
<div class="portlet light">
	<div class="portlet-title">
		<div class="caption">
          	<a class="btn green" href="${page.basePath }jfrw/fbrw"><i class="fa fa-plus"></i> 新建任务</a>
		</div>
		<div class="actions col-md-9">
           	<form action="#" class="form-horizontal " id="form_remark">
		    	<div class="form-body">
		    		<div class="form-group">
		           		<label class="control-label col-md-4">状态: </label>
		           		<div class="col-md-4">
		                	<div class="input-icon right">
		                    	<i class="fa"></i>
		                     	<select name="status" class="form-control" validate="{required:true}">
		                     		<option value="-1" selected="selected">全部</option>
		                     		<option value="0">进行中</option>
		                     		<option value="1">已完成</option>
		                     		<option value="2">已关闭</option>
		                     	</select>
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
       	<table class="table table-striped table-hover"></table>
	</div>
</div>
<script src="${page.basePath }assets/common/require.js" data-main="${page.basePath }assets/jfrw/view/main" type="text/javascript"></script>