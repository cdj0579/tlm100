<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="portlet light ">
    <div class="portlet-body">
       <div class="row">
           <div class="col-md-6 col-sm-6 col-xs-12">
               <div class="portlet">
              		<div class="row">
              		 	<h4>头像设置</h4>
              		 	<hr style="width:98%" />
              		 	<div class="col-md-6">
	              		 	<div class="img-position  center-block">
	           		 			<c:if test="${not empty txImg}">
	                             	<img alt="" class="img-circle" src= "${ txImg}" />
	                           	</c:if>
	                           	<c:if test="${empty txImg}">
	                             	<img src="/assets/global/img/user_tx.jpg">
	                           	</c:if>
	           		 			
	           		 		</div>
	           		 		<label class="btn btn-default btn-xs center-block"  style="line-height:30px;width:100px;cursor:pointer;" for="upload-file">更新头像</label>
     						<input name="upload-file" id="upload-file" type="file" >
     						<p class="text-center" style="font-size:10px;color:red;">支持JPG、PNG、GIF格式，小于1MB</p>
      					</div>
      					<div class="col-md-6" >
      						<form class="form-horizontal">
								  <div class="form-group">
								    <label class="col-sm-4 control-label">ID:</label>
								    <div class="col-sm-8">
								      <p class="form-control-static">${userno }</p>
								    </div>
								  </div>
								  <div class="form-group">
								    <label class="col-sm-4 control-label">用户名:</label>
								    <div class="col-sm-8">
								      <p class="form-control-static">${loginName }</p>
								    </div>
								  </div>
								  <div class="form-group">
								    <label class="col-sm-4 control-label">积分:</label>
								    <div class="col-sm-8">
								      <p id="jf" class="form-control-static">0</p>
								    </div>
								  </div>
      						</form>
      					</div>
           		 		
              		</div>
              		<div class="row" style="margin-top:30px;">
              			<h4>排名前十的贡献点</h4>
	               		<hr  style="width:98%" />
						<ol  id="gxd_top10">
						<!-- 
						 <li>：b级 理解有理数的意义：计数、测量、标号、排序四大功能：b级 理解有理数的意义：计数、测量、标号、排序四大功能<span class="badge">知识点</span></li>
						  <li>Item 2<span class="badge">教案</span></li>
						  <li>Item 3<span class="badge">教案</span></li>
						  <li>Item 4<span class="badge">知识点</span></li>
						  <li>Item 1<span class="badge">教案</span></li>
						  <li>Item 2<span class="badge">专题</span></li> -->
						</ol>
	              		
              		</div>
               		
               </div>
           	</div>
           
           	<div class="col-md-6 col-sm-6  col-xs-12">
				<div class="row" >
               		<h4> 个人信息</h4>
	               	<hr/>
               		 	<form name="teacherFrom" class="form-horizontal" action="" method="post" enctype="multipart/form-data">
			              <input type="hidden" name="id" />
			               <div class=" form-group">
				                <label for="name" class="control-label col-md-3 ">真实姓名:</label>
				                <div class="col-md-8">
				                 	<input id="name" type="text" name="name" class="form-control" value="${realName }" placeholder="请输入姓名" />
				                </div>
			                </div>
		                	<div class="form-group">
				                <label for="sex" class="control-label col-md-3">性别:</label>
				                <div class="col-md-8">
				                	<select id="sex" name="sex" class="select2 form-control" >
				                        <option value="0">保密</option>
				                        <option value="1">男</option>
				                        <option value="2">女</option>
				                    </select>
				               		<!-- 
				               		<label class="radio-inline">
								        <input type="radio" name="sex" id="sex_1" value="1" > 男
								    </label>
								    <label class="radio-inline">
								        <input type="radio" name="sex" id="sex_2"  value="2"> 女
								    </label>
				                	<label class="radio-inline">
								        <input type="radio" name="sex" id="sex_0"  value="0" checked> 保密
								    </label>
								     -->
				                </div>
			                </div>
			                <div class="form-group">
				                <label for="skdz" class="control-label col-md-3">授课地址:</label>
				                <div class="col-md-8">
				                	<input  id="skdz" class="form-control " type="text" placeholder="请输入授课地址" name="skdz" /> 
				                </div>
			                </div>
			                <div class="form-group">
				                <label for="kmId" class="control-label col-md-3">学科:</label>
				                <div class="col-md-8">
				                 	<select id="kmId" name="kmId" class="select2 form-control" >
				                        <c:forEach var="km" items="${kmList}">
				                        	<option value="${km['id'] }">${km['name'] }</option>
										</c:forEach>
				                    </select>
				                </div>
			                </div>
			             
			                <div class="form-group">
				                <label  class="control-label col-md-3">教师资格证:</label>
				                <div class="col-md-8">
				                	<label for="upload_jszgz" class="ShowZsImg" data-toggle="tooltip" data-placement="top" title="点击可重新上传" > 
				                		<img src="/assets/global/img/user_tx.jpg" >
				                	</label>
				                	<input  type="file" placeholder="请上传您的教师资格证" name="jszgz" id="upload_jszgz"/>
				                </div>
			                </div>
			                <div class="form-group">
				                <label  class="control-label col-md-3">等级证书:</label>
				                <div class="col-md-8">
				               		<label for="upload_djzs" class="ShowZsImg" data-toggle="tooltip" data-placement="top" title="点击可重新上传" > 
				                		<img src="/assets/global/img/user_tx.jpg" >
				                	</label>
				                	<input type="file" placeholder="请上传您的等级证书" name="djzs" id="upload_djzs"/>
				                </div>
			                </div>
			                <div class="form-group">
				                <label  class="control-label col-md-3">荣誉证书:</label>
				                <div class="col-md-8">
				               		<label for="upload_ryzs" class="ShowZsImg" data-toggle="tooltip" data-placement="top" title="点击可重新上传" > 
				                		<img src="/assets/global/img/user_tx.jpg" >
				                	</label>
				                	<input  type="file" placeholder="请您的荣誉证书" name="ryzs" id="upload_ryzs"/> 
				                </div>
			                </div>
			                <div class="form-group">
			                </div>
			                <div class="form-actions ">
			                	<button type="button" id='save' class="btn green center-block"> 保存 </button>
			                </div>
           				</form>
               	</div>
           	</div>
       </div>
    </div>
</div>
<script src="${page.basePath }assets/common/require.js" data-main="${page.basePath }assets/user/info/main" type="text/javascript"></script>