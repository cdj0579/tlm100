<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.unimas.web.utils.*" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title></title>
		<%PageUtils.initBasePath(request); %>
	    <script>
			var basePath = "${basePath }";
			var slideIndex = 0;//${slideIndex==null?0:slideIndex }; //图片显示的初始索引，从0开始
			var tupIds = new Array();
		</script>
		<link href="${basePath }assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
		<link href="${basePath }assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
		<link href="${basePath }assets/global/plugins/mui/css/mui.min.css" rel="stylesheet" type="text/css" />
		<link href="${basePath }assets/global/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />
		<link href="${basePath }assets/global/plugins/Swiper/css/swiper.min.css" rel="stylesheet" type="text/css" />
		
		<style type="text/css">
			body{
				font-size: 14px; 
			}
			.toast-top-center,.toast-top-right,.toast-top-left {
			  top: 60px;
			}
			.mui-bar .mui-btn-link{
				color:#000;
			}
			section {
				margin-top: 55px;
				padding:15px;
			}
			label {
				font-weight: 400;
			}
			.swiper-container img{
				width: 100%;
				height:200px;
			}
			
			.model-head {
				margin-bottom:10px;
			  	margin-top:5px;
			}
			.left, .left-1{  
			  	float: left;  
			}  
			.left{
			  	width: calc(100% - 80px);  
			}  
			.left-1{  
			  	width: calc(100% - 30px);  
			} 
			 
			.right, .right-1 {
				float: right;  
			  	padding:0;
			  	text-align: center;
			}
			.right{  
			  width:80px;
			  color: #333;
			}
			.right-1{  
			  width:30px;
			  line-height:40px;
			}
		</style>
	</head>
	<body>
		<header class="mui-bar  mui-bar-nav" style="padding-right: 15px;">
			<button type="button" class="mui-left mui-action-back mui-btn  mui-btn-link mui-btn-nav mui-pull-left">
				<span class="mui-icon mui-icon-left-nav"></span>
			</button>
			<h1 class="mui-title">分享内容设置</h1>
			<button id='looking' class=" mui-pull-right mui-btn-link">预览</button>
		</header>
		<section style="margin-top: 55px;">
			<c:if test="${lryId <= 0  }">
				<div class="alert alert-warning">
					<a href="#" class="close" data-dismiss="alert">&times;</a>
					<strong>警告！</strong>请联系相关管理员分配二维码！
				</div>
			</c:if>
			<div>
				<label>左右滑动选择图片</label>
				<div class="swiper-container">
			        <div class="swiper-wrapper">
			        	<c:forEach var="img" items="${imgList}"  varStatus="status" >
			        		 <script>
			        		 	tupIds["${status.index }"] = "${img.id }";
			        		 	<c:if test="${img.id == tupId }">
			        		 		slideIndex = ${status.index };
			        		 	</c:if>
			        		 </script>
                        	<div class="swiper-slide">
				            	<img src="${img.name }" />
				            </div>
						</c:forEach>
					</div>
    			</div>
			</div>
			<div style="margin-top:10px;">
				<div class="form-group">
                    <input class="form-control placeholder-no-fix" value="${title_main }" type="text" autocomplete="off" placeholder="主标题内容" name="title_main" />
				</div>
				<div class="form-group">
                    <input class="form-control placeholder-no-fix" value="${title_sub }" type="text" autocomplete="off" placeholder="副标题内容" name="title_sub" />
				</div>
				<c:forEach var="model" items="${modelList}"  varStatus="status">
                     <div class="model">
						<div class="model-head">
							<div class="left">内容模块${status.count } </div>
							<a class="btn right">${status.last?'增加':'删除' }模块</a>
							<div class="clearfix"></div>
						</div>
						<div class="form-group">
		                    <input class="form-control placeholder-no-fix" value="${model.model_title }" type="text" autocomplete="off" placeholder="模块标题" name="model_title" />
						</div>
						<c:forEach var="line" items="${model.lineList}"  varStatus="lineStatus">
							<div>
								<div class="left-1">
									<div class="form-group">
					                    <input class="form-control placeholder-no-fix" value="${line }" type="text" autocomplete="off" placeholder="段落或行内容"/>
									</div>
								</div>
								<i class="fa  ${lineStatus.last?'fa-plus-square':'fa-minus' }  right-1" aria-hidden="true"></i>
								<div class="clearfix"></div>
							</div>
						</c:forEach>
					</div>
				</c:forEach>
				
				
                 <div class="form-group">
                    <button type="submit" class="btn btn-block btn-info">提交保存</button>
                </div>
			</div>
			
		</section>
		<footer ></footer>
		<script type="text/javascript" src="${basePath }assets/global/plugins/jquery.min.js"></script>
		<script type="text/javascript" src="${basePath }assets/global/plugins/bootstrap-toastr/toastr.min.js"></script>
		<script type="text/javascript" src="${basePath }assets/global/plugins/Swiper/js/swiper.jquery.min.js"></script>
		
		<script type="text/javascript">
			//var basePath="./";
			//var slideIndex = 2 ; //图片显示的初始索引，从0开始
			$(".mui-icon").click(function(){
				var url = basePath + "app/unSetShare";
				$.post(url, {}, function(data){	    }, "json");
				self.location = basePath +"app/index";
			});
			$(".close").click(function(){
				$(this).parent().slideUp("slow");
			});
			function getAlert(options){
	        	$.extend(toastr.options, {
	        		closeButton: true,
	                debug: false,
	                positionClass: 'toast-top-center'
	        	}, options);
	        	return toastr;
	        };
	        var alert = getAlert({});
	        var modelNum = 1;
			function getHangHtml( flag ){
				var className = 'fa-minus';
				if(flag == true){
					className = 'fa-plus-square';
				}
				var hangHtml ='<div>'
				+	'<div class="left-1">'
				+		'<div class="form-group">'
	            +        	'<input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="段落或行内容" />'
				+		'</div>'
				+	'</div>'
				+'	<i class="fa ' + className + ' right-1" aria-hidden="true"></i>'
				+'	<div class="clearfix"></div>'
				+'</div>';
				return hangHtml;
			}
			
			function getModelHtml(){
				var html = '<div class="model">'
					+	'<div class="model-head">'
					+		'<div class="left">内容模块'+ modelNum +' </div>'
					+		'<a class="btn right">删除模块</a>'
					+		'<div class="clearfix"></div>'
					+	'</div>'
					+	'<div class="form-group">'
	                +	    '<input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="模块标题" name="model_title" />'
					+	'</div>'
					+	 getHangHtml( true )
					+'</div>';
				return html;
			}
			
			function getModelValue(obj){
				var param = {};
				param.modelTitle = $.trim(obj.find("input[name='model_title']").val());
				var lineObj = obj.find("input[name !='model_title']");
				var str = "";
				$.each(lineObj,function(i,o){
					var v= $.trim($(o).val());
					if(v != "" ){
						str += "\u0003"+ v;
					}
				});
				param.lineValue= str.substring(1);
				return param;
			}
			
			function checkAddModel(obj){
				var param = getModelValue(obj);
				if(param.modelTitle!="" && param.lineValue != ""){
					return true;
				}else{
					return false;
				}
			}
			
			function addModel(obj){
				obj.find("a").click(function(){
					var _this =	$(this);
					var parent = _this.parent().parent();
					if(checkAddModel(parent)){
						modelNum++;	
						var addBtn = _this.clone(true);
						var html = getModelHtml(false);
						var _html = $(html);
						addHang(_html);
						var delBtn = delModel(_html);
						_this.replaceWith(delBtn.clone(true));
						delBtn.replaceWith(addBtn);
						parent.after(_html);
					}
				});
			}
			
			function delModel(obj){
				var delBtn = obj.find("a");
				delBtn.click(function(){
					$(this).parent().parent().remove();
				});
				return delBtn;
			}
			
			function addHang(obj){
				obj.find(".fa-plus-square").click(function(){
					var _this =	$(this);
					var oldInput = _this.siblings(".left-1").find("input");
					var value = $.trim(oldInput.val());
					if(value != ""){
						var html = getHangHtml(false);
						var _html = $(html);
						oldInput.val("");
						_html.find("input").val(value);
						delHang(_html);
						_this.parent().before(_html.focus());
					}		
				});
			}
			
			function delHang(obj){
				obj.find(".fa-minus").click(function(){
					$(this).parent().remove();
				});
			}
			
			var mySwiper = new Swiper('.swiper-container', {
				initialSlide : slideIndex,
     		 	loop : true
    		});
			var _section = $("section");
			addHang(_section);
			delHang(_section);
			var model = _section.find(".model-head");
			modelNum = model.length;
			$.each(model,function(i,objstr){
				var _obj = $(objstr);
				if(modelNum == (i+1)){
					addModel(_obj);
				}else{
					delModel(_obj);
				}
			});
			
			function getValue(){
				var param = {};
				var index = mySwiper.realIndex;
				param.tupId = tupIds[index];
				param.mainTitle = $.trim($("input[name='title_main']").val());
				if(param.mainTitle == ""){
					alert.error("主标题内容不能为空！");
					return false;
				}
				param.subTitle = $.trim($("input[name='title_sub']").val());
				var modelObjs = $(".model");
				var modelValue = "";
				
				$.each(modelObjs,function(i,model){
					var oneModelValue = getModelValue($(model));
					if(oneModelValue.modelTitle!="" && oneModelValue.lineValue != ""){
						modelValue +="\u0002"+oneModelValue.modelTitle+"\u0001"+ oneModelValue.lineValue;
					}
				});
				if(modelValue == ""){
					alert.error("无内容模块完整填写正确！");
					return false;
				}
				param.content = modelValue.substring(1); // "\u0002"为一个字符
				return param;
			}
			
			function save(flag){
				var param = getValue();
				if(param){
					param.flag = flag; //true:提交保存；false:提交预览
					var url = basePath + "app/saveShareInfo";
					$.post(url, param, function(data){
						if(data.success == true || data.success == 'true' ){
							if(flag){
								alert.info("保存设置成功！");
							}else{
					    		window.location.href = basePath + "app/showShare?flag=looking";
							}
						}
				    }, "json");
				}
			}
			$("#looking").click(function(){
				save(false);
			});
			$(".btn-info").click(function(){
				save(true);
			});
			
		</script>
	</body>
</html>
