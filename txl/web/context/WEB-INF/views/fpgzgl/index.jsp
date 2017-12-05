<%@ page contentType="text/html;charset=UTF-8"%>
<style>
.portlet .actions .form-group {
	margin-bottom: 0;
}
.form .form-section, .portlet-form .form-section {
    font-weight: 400;
    margin: 30px 0 10px;
    padding: 0 0 5px 70px;
    border-bottom: 1px solid #e7ecf1;
}
</style>
<div class="portlet light ">
    <div class="portlet-body">
    	<div class="row">
    		<div class="col-md-8 search-inp">
    			
    		</div>
    		<div class="col-md-4">
	    		<div class="btn-groups">
		            <a class="btn green add"><i class="fa fa-plus"></i> 添加分配规则</a>
		            <!-- <a class="btn blue setting"><i class="fa fa-cog"></i> 设置分配周期</a> -->
		        </div>
    		</div>
    	</div>
		<table class="table table-striped table-hover table-checkable order-column" id="fpgz_table"></table>
    </div>
</div>
<script src="${page.basePath }assets/common/require.js" data-main="${page.basePath }assets/fpgzgl/main" type="text/javascript"></script>