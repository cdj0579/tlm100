<%@ page contentType="text/html;charset=UTF-8"%>

<div class="portlet light ">
    <div class="portlet-body">
    	<ul id="myTab" class="nav nav-tabs">
			<li class="active">
				<a href="#zsd" data-toggle="tab">
					知识点
				</a>
			</li>
			<li><a href="#ja" data-toggle="tab">教案</a></li>
			<li><a href="#xt" data-toggle="tab">习题</a></li>
			<li><a href="#zt" data-toggle="tab">专题</a></li>
		</ul>
        <div id="myTabContent" class="tab-content">
			<div class="tab-pane fade in active" id="zsd">
				  <table class="table table-striped table-hover table-checkable order-column" id="zsd_table"></table>
			</div>
			<div class="tab-pane fade" id="ja">
				 <table class="table table-striped table-hover table-checkable order-column" id="ja_table"></table>
			</div>
			<div class="tab-pane fade" id="xt">
				 <table class="table table-striped table-hover table-checkable order-column" id="xt_table"></table>
			</div>
			<div class="tab-pane fade" id="zt">
				 <table class="table table-striped table-hover table-checkable order-column" id="zt_table"></table>
			</div>
		</div>
     
    </div>
</div>
<script src="${page.basePath }assets/common/require.js" data-main="${page.basePath }assets/user/gxphb/main" type="text/javascript"></script>