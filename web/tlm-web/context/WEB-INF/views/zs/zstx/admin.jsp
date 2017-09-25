<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
.portlet .actions .form-group {
	margin-bottom: 0;
}
.ztree li a {
	padding-right: 19px;
}
.ztree li a.curSelectedNode {
	padding-right: 3px;
}
.ztree span.btn {
	float: right;
    margin-right: -70px;
    margin-top: -4px;
    padding: 2px 4px 1px 1px;
}
</style>
<div class="portlet light">
	<div class="portlet-title">
		<div class="actions col-md-12">
           	<form action="#" class="form-horizontal " id="form_remark">
		    	<div class="form-body">
		    		<div class="row">
		    			<div class="col-md-4">
		    				<select name="bbId" class="form-control input-medium" data-style="blue" validate="{required:true}">
				          		<option value="-1">默认版本</option>
				           		<c:forEach var="bb" items="${bbList }">
				           			<option value="${bb.id }">${bb.name }</option>
				           		</c:forEach>
				           	</select>
		    			</div>
		    			<div class="col-md-2">
		    				<div class="form-group">
				           		<label class="control-label col-md-5">地区: </label> 
				           		<div class="col-md-7">
				                	<div class="input-icon right">
				                    	<i class="fa"></i>
				                     	<select name="dqId" class="form-control" validate="{required:true}"></select>
				                    </div>
				                </div>
				       		</div>
		    			</div>
		    			<div class="col-md-2">
		    				<div class="form-group">
				           		<label class="control-label col-md-5">科目: </label>
				           		<div class="col-md-7">
				                	<div class="input-icon right">
				                    	<i class="fa"></i>
				                     	<select name="kmId" class="form-control" validate="{required:true}"></select>
				                    </div>
				                </div>
				       		</div>
		    			</div>
		    			<div class="col-md-2">
		    				<div class="form-group">
				           		<label class="control-label col-md-5">年级: </label>
				           		<div class="col-md-7">
				                	<div class="input-icon right">
				                    	<i class="fa"></i>
				                     	<select name="njId" class="form-control" validate="{required:true}"></select>
				                    </div>
				                </div>
				       		</div>
		    			</div>
		    			<div class="col-md-2">
		    				<div class="form-group">
				           		<label class="control-label col-md-5">学期: </label>
				           		<div class="col-md-7">
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
			<div class="col-md-4">
				<div class="scroller" style="height: 450px;" data-always-visible="1" data-rail-visible1="0" data-handle-color="#337AB7">
		        	<div id="zj_tree" class="ztree"></div>
		       	</div>
			</div>
		   	<div class="col-md-8">
		   		<div class="row">
       				<div class="col-md-6" id="searchInp">
             		</div>
           		</div>
		       	<table class="table table-striped table-hover"></table>
		   	</div>
		</div>
	</div>
</div>
<script>
var userNo = "${userNo }";
</script>
<script src="${page.basePath }assets/common/require.js" data-main="${page.basePath }assets/zs/zstx/adminMain" type="text/javascript"></script>