<%@ page contentType="text/html;charset=UTF-8"%>

<div class="portlet light ">
    <div class="portlet-title">
        <div class="caption">
            <a class="btn green add-dir"><i class="fa fa-plus"></i> 添加目录</a>
          	<a class="btn blue edit-dir"><i class="fa fa-edit"></i> 编辑目录</a>
          	<a class="btn red delete-dir"><i class="fa fa-remove"></i> 删除目录</a>
        </div>
        <div class="actions">
            
        </div>
    </div>
    <div class="portlet-body">
        <div class="row ">
        	<div class="col-md-4">
				<div class="scroller" style="height: 450px;" data-always-visible="1" data-rail-visible1="0" data-handle-color="#337AB7">
		        	<div id="dir_tree" class="ztree"></div>
		       	</div>
			</div>
		   	<div class="col-md-8">
		   		<div class="row">
       				<div class="col-md-6" id="searchInp">
             		</div>
             		<div class="col-md-6">
                   		<div class="btn-groups">
                   			<a class="btn green add"><i class="fa fa-plus"></i> 添加</a>
                   		</div>
             		</div>
       			</div>
				<table class="table table-striped table-hover table-checkable order-column" id="ja_table"></table>
		   	</div>
		</div>
    </div>
</div>
<script src="${page.basePath }assets/common/require.js" data-main="${page.basePath }assets/jagl/main" type="text/javascript"></script>