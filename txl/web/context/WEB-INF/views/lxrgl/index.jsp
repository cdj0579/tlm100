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
				           		<label class="control-label col-md-4">所在学校: </label>
				           		<div class="col-md-8">
				                	<div class="input-icon right">
				                    	<i class="fa"></i>
				                     	<select name="xxId" class="form-control" validate="{required:true}">
				                     	</select>
				                    </div>
				                </div>
				       		</div>
		    			</div>
		    			<div class="col-md-3">
		    				<div class="form-group">
				           		<label class="control-label col-md-4">所在地区: </label>
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
				           		<label class="control-label col-md-4">所在年级: </label>
				           		<div class="col-md-8">
				                	<div class="input-icon right">
				                    	<i class="fa"></i>
				                     	<select name="nj" class="form-control" validate="{required:true}">
				                     		<option value="-1"></option>
				                     		<option value="1">1年级</option>
                     						<option value="2">2年级</option>
                     						<option value="3">3年级</option>
                     						<option value="4">4年级</option>
                     						<option value="5">5年级</option>
                     						<option value="6">6年级</option>
                     						<option value="7">7年级</option>
                     						<option value="8">8年级</option>
                     						<option value="9">9年级</option>
				                     	</select>
				                    </div>
				                </div>
				       		</div>
		    			</div>
		    			<div class="col-md-3">
		    				<div class="form-group">
				           		<label class="control-label col-md-4">所在班级: </label>
				           		<div class="col-md-8">
				                	<div class="input-icon right">
				                    	<i class="fa"></i>
				                     	<select name="bj" class="form-control" validate="{required:true}">
				                     		<option value="-1"></option>
				                     		<option value="1">1班</option>
                     						<option value="2">2班</option>
                     						<option value="3">3班</option>
                     						<option value="4">4班</option>
                     						<option value="5">5班</option>
                     						<option value="6">6班</option>
                     						<option value="7">7班</option>
                     						<option value="8">8班</option>
                     						<option value="9">9班</option>
                     						<option value="10">10班</option>
                     						<option value="11">11班</option>
                     						<option value="12">12班</option>
                     						<option value="13">13班</option>
                     						<option value="14">14班</option>
                     						<option value="15">15班</option>
                     						<option value="16">16班</option>
                     						<option value="17">17班</option>
                     						<option value="18">18班</option>
                     						<option value="19">19班</option>
                     						<option value="20">20班</option>
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
		<table class="table table-striped table-hover table-checkable order-column" id="lxr_table"></table>
    </div>
</div>
<script src="${page.basePath }assets/common/require.js" data-main="${page.basePath }assets/lxrgl/main" type="text/javascript"></script>