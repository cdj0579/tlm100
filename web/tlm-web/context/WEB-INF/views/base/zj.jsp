<%@ page contentType="text/html;charset=UTF-8"%>
<style>
.portlet .actions .form-group {
	margin-bottom: 0;
}
</style>
<div class="portlet light">
	<div class="portlet-title">
		<div class="caption">
          	<a class="btn green add-bb"><i class="fa fa-plus"></i> 添加版本</a>
          	<a class="btn blue edit-bb"><i class="fa fa-edit"></i> 编辑版本</a>
          	<a class="btn red delete-bb"><i class="fa fa-remove"></i> 删除版本</a>
		</div>
		<div class="actions col-md-8">
           	<form action="#" class="form-horizontal " id="form_remark">
		    	<div class="form-body">
		    		<div class="row">
		    			<div class="col-md-3">
		    				<div class="form-group">
				           		<label class="control-label col-md-5">地区: </label>
				           		<div class="col-md-7">
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
				           		<div class="col-md-6">
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
				           		<label class="control-label col-md-5">年级: </label>
				           		<div class="col-md-7">
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
		   		<div class="pull-left btn-groups" style="padding-bottom: 15px;">
		   			<a class="btn green add"><i class="fa fa-plus"></i> 添加章节</a>
		   			<a class="btn yellow copy-zj"><i class="fa fa-copy hide"></i> 复制其它地区章节 </a>
		   		</div>
		       	<table class="table table-striped table-hover"></table>
		   	</div>
		</div>
	</div>
</div>
<script src="${page.basePath }assets/common/require.js" data-main="${page.basePath }assets/base/zj/main" type="text/javascript"></script>