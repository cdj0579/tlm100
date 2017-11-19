<%@ page contentType="text/html;charset=UTF-8"%>
<style>
.portlet .actions .form-group {
	margin-bottom: 0;
}
</style>
<div class="portlet light ">
    <div class="portlet-body">
    	<div class="row">
    		<div class="col-md-8 search-inp">
    			
    		</div>
    		<div class="col-md-4">
	    		<div class="btn-groups">
		            <a class="btn green add"><i class="fa fa-plus"></i> 添加</a>
		        </div>
    		</div>
    	</div>
		<table class="table table-striped table-hover table-checkable order-column" id="fpgz_table"></table>
    </div>
</div>
<script src="${page.basePath }assets/common/require.js" data-main="${page.basePath }assets/fpgzgl/main" type="text/javascript"></script>