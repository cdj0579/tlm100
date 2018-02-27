<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="portlet light ">
    <div class="portlet-body">
    	<div class="row">
    		<c:forEach var="img" items="${imgList }"  varStatus="status">
		    	<div class="col-sm-6 col-md-6" >
		    		<form  class="form-horizontal" action="" method="post" enctype="multipart/form-data">
		    			<input type="hidden" name="id" value="${img.id }"/>
		    			<div class="form-group">
			                <label >背景图${status.count }:</label>
			                <div class="imgDiv">
			               		<label for="imgId${status.count }" data-toggle="tooltip" data-placement="top" title="点击可重新上传" > 
			                		<img src="${img.tupian }" >
			                	</label>
			                	<input type="file" placeholder="请上传您的等级证书" name="imgInfo" id="imgId${status.count }"/>
			                </div>
		                </div>
		                <div class="form-actions ">
		                	<button type="button" disabled="true" class="btn green center-block"> 保存${status.count } </button>
		                </div>
					 </form>
				</div>
			</c:forEach>
    </div>
</div>
<script src="${page.basePath }assets/common/require.js" data-main="${page.basePath }assets/simgset/main" type="text/javascript"></script>