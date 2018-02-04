<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="portlet light">
    <div class="portlet-title">
        <div class="caption">
            <span class="caption-subject"></span>
        </div>
        <div class="actions btn-groups">
            <a class="btn blue save"><i class="fa fa-save"></i> 保存</a>
            <c:if test="${templete==false}"><a class="btn yellow save-templete"><i class="fa fa-save"></i> 保存为模板</a></c:if>
        </div>
    </div>
    <div class="portlet-body">
    	<form action="#" class="form-horizontal">
			<input type="hidden" name="id" value="${info.id }"/>
			<input type="hidden" name="isTemplete" value="${templete }"/>
	    	<div class="form-body">
	    		<div class="form-group">
	           		<label class="control-label col-md-2">教案名称: <span class="required">*</span></label>
	           		<div class="col-md-4">
	                	<div class="input-icon right">
	                    	<i class="fa"></i>
	                     	<input type="text" name="name" class="form-control" validate="{required:true}" value="${info.name }"/>
	                    </div>
	                </div>
	       		</div>
	       		<div class="form-group">
	           		<label class="control-label col-md-2">所属目录: <span class="required">*</span></label>
	           		<div class="col-md-3">
	                	<div class="input-icon right">
	                    	<i class="fa"></i>
	                     	<input type="text" name="dirId" class="form-control" validate="{required:true}" value="${info.dirId}"/>
	                    </div>
	                </div>
	       		</div>
	       		<div class="form-group">
	           		<label class="control-label col-md-2">是否原创: </label>
	           		<div class="col-md-3">
	                	<div class="input-icon right">
	                    	<i class="fa"></i>
	                     	<select name="isOriginal" class="form-control" validate="{required:true}">
	                     		<c:if test="${info.isOriginal==1}">
		                     		<option value="1" selected="selected">原创</option>
		                     		<option value="2">非原创</option>
			                	</c:if>
			                	<c:if test="${info.isOriginal!=1}">
			                		<option value="1">原创</option>
		                     		<option value="2" selected="selected">非原创</option>
			                	</c:if>
	                     	</select>
	                    </div>
	                </div>
	       		</div>
	       		<%-- <div class="form-group <c:if test='${info.isOriginal!=1}'>hide</c:if>">
					<label class="control-label col-md-2">消耗积分数: <span class="required">*</span></label>
					<div class="col-md-3">
						<div class="input-icon right">
							<i class="fa"></i> 
							<input type="number" name="yyfs" class="form-control" validate="{required:true, max: 10, min:1}" value="${empty info || empty info.yyfs?1:info.yyfs}"/>
						</div>
					</div>
				</div> --%>
				<div class="form-group">
	           		<label class="control-label col-md-2">是否隐藏: </label>
	           		<div class="col-md-3">
	                	<div class="input-icon right">
	                    	<i class="fa"></i>
	                     	<select name="isShare" class="form-control" validate="{required:true}">
		                     	<c:if test="${info.isShare==1}">
				                	<option value="1" selected="selected">显示</option>
		                     		<option value="2">隐藏</option>
			                	</c:if>
			                	<c:if test="${info.isShare!=1}">
				                	<option value="1">显示</option>
		                     		<option value="2" selected="selected">隐藏</option>
			                	</c:if>
	                     	</select>
	                    </div>
	                </div>
	       		</div>
	            <div class='form-group'>
	                <label class="control-label col-md-2">教案内容: <span class="required">*</span></label>
	                <div class="col-md-10 btn-groups toolbar">
	                	<c:if test="${templete==false}">
		                	<a class="btn green input-zsContent"><i class="fa fa-plus"></i> 插入知识内容</a>
		                	<a class="btn green input-ztContent"><i class="fa fa-plus"></i> 插入专题内容</a>
		                	<a class="btn green input-xt"><i class="fa fa-plus"></i> 插入习题</a>
		                	<a class="btn blue change-templete"><i class="fa fa-refresh"></i> 导入教案模板</a>
	                	</c:if>
	                </div>
	            </div>
	            <div class='form-group'>
	            	<div class="col-md-12">
		                <div class="input-icon right">
		                    <i class="fa"></i>
					        <textarea id="editor1" contenteditable="true" name="content" class="form-control" validate="{required:true}">
					        	${info.content}
					        </textarea>
	                    </div>
	                </div>
	            </div>
	   		</div>
		</form>
    </div>
</div>
<script src="${page.basePath }assets/common/require.js" data-main="${page.basePath }assets/jagl/edit" type="text/javascript"></script>