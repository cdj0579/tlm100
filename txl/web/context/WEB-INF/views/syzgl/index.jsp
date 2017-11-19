<%@ page contentType="text/html;charset=UTF-8"%>
<style>
.portlet .actions .form-group {
	margin-bottom: 0;
}
</style>
<div class="portlet light ">
	<div class="portlet-title">
		<div class="actions col-md-12 pull-left">
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
		    		</div>
		   		</div>
			</form>
		</div>
	</div>
    <div class="portlet-body">
    	<div class="row">
    		<div class="col-md-8 search-inp">
    			
    		</div>
    		<div class="col-md-4">
	    		<div class="btn-groups pull-right">
		            <a class="btn green add"><i class="fa fa-plus"></i> 添加</a>
		        </div>
    		</div>
    	</div>
		<table class="table table-striped table-hover table-checkable order-column" id="syz_table"></table>
    </div>
</div>
<script src="${page.basePath }assets/common/require.js" data-main="${page.basePath }assets/syzgl/main" type="text/javascript"></script>