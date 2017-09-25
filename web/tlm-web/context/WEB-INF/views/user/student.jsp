<%@ page contentType="text/html;charset=UTF-8"%>

<div class="portlet light ">
    <div class="portlet-title">
        <div class="caption">
            学生家长信息列表
        </div>
        <div class="actions">
            
        </div>
    </div>
    <div class="portlet-body">
        <div class="row ">
			<div class="col-md-12">
          		<div class="row">
       				<div class="col-md-6" id="searchInp">
             		</div>
             		<div class="col-md-6">
                   		<div class="btn-groups">
                       		<!-- <a class="btn green query"><i class="fa fa-search"></i> 查询</a> -->
                       		<!-- <a class="btn blue add"><i class="fa fa-plus"></i> 添加</a> -->
                   		</div>
             		</div>
       			</div>
				<table class="table table-striped table-hover table-checkable order-column" id="student_table"></table>
			</div>
		</div>
    </div>
</div>
<script src="${page.basePath }assets/common/require.js" data-main="${page.basePath }assets/user/student/main" type="text/javascript"></script>