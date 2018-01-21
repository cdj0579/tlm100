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
		</script>
		<link href="${basePath }assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
		<link href="${basePath }assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
		<style type="text/css">
			body,pre{
				font-size: 16px;
				background-color:#f4f4f4;
			}
			.title {
				font-size: 24px;
				font-weight: bold;
				margin-top: 15px;
			}
			.title2 {
				margin-top: 5px;
				font-size: 16px;
				font-weight: normal;
				color: #999;
			}
			section{padding:0 10px;}
			.model {
				margin-top: 25px;
				padding:0 12px;
			}
			
			ul{ list-style-type :none;padding: 0; }
			ul>li { line-height: 25px;}  
			hr{margin:5px 0;border-top: 1px solid #ccc;}
			
			footer{
				background-color: #34495e;
				padding:20px 10px;
    			color: #fff;
    			height:150px;
			}
			footer .info {
				text-indent:20px;
				font-size: 12px;
				margin-bottom: 20px;
			}
			.inBtn{
				background-color: #368c7a;
			}
			.head-bar{
				position:fixed;padding:10px 20px;width:100%;top:0;
			}
			.back,.share {
				border:0;
				color:#000;
				background-color: rgba(255,255,255,0.6);
			}
			.share {
				float: right;
			}
		</style>
		<c:if test="${isNull!=null }">
			<style type="text/css">
				.head-bar{
					background-color:rgba(255,255,255,1); 
				}
			</style>
		</c:if>
	</head>
	<body>
		<header>
			<div class="head-bar">
				<button type="button" class="back" >
					<i class="fa fa-angle-left" aria-hidden="true"></i>
				</button>
				<c:if test="${isNull==null }">
				<button type="button"  class="share">
					<i class="fa fa-share-square-o" aria-hidden="true"></i>
				</button>
				</c:if>
			</div>
		</header>
		<c:choose>
		 	<c:when test="${isNull != null }">
		 		<section>
		 		<div class="alert alert-warning" style="margin-top: 60px;">
					<strong>提示！</strong><br/>
					<c:if test="${isNull == 1 }">
						无相关活动或活动已过期！
					</c:if>
					<c:if test="${isNull == 2 }">
						请联系相关管理员分配二维码！
					</c:if>
				</div>
				</section>
		 	</c:when>
		 	<c:otherwise>
		 		<div>
					<img style="width: 100%;height:248px ;" src="${basePath }assets/global/img/${data.imgName }" />
				</div>
				<section>
					
					<div class="title">
						<div >${data.mainTitle }</div>
						<div class="title2">${data.subTitle }</div>
					</div>
					<c:forEach var="model" items="${data.modelList}"  varStatus="status">
						<div class="model">
							<div>| ${model.model_title } |</div>
							<hr />
							<div>
								<ul>
									<c:forEach var="line" items="${model.lineList}"  varStatus="lineStatus">
										<li>${line }</li>
									</c:forEach>
								</ul>
							</div>
						</div>
					</c:forEach>
				</section>
				<footer>
					<div>
						<div class="col-md-5 col-xs-5">
							<img  style="float:right; width:100px;height:100px;" src="${basePath }fx/${data.jgId }/${data.lryId }/qrcode" />
						</div>
						<div class="col-md-7 col-xs-7">
							<div class="info">
								<p>扫描左方二维码或点击【加入】按钮，填写相关信息届时会有专人联系您，期待您的加入</p>
							</div>
							<button class="btn btn-block inBtn"> 加    入 </button>
						</div>
					
					</div>
				</footer>
		 	</c:otherwise>
		</c:choose>
		
		<script type="text/javascript" src="${basePath }assets/global/plugins/jquery.min.js"></script>
		<script type="text/javascript">
			$(".back").click(function(){
				//history.back();
				self.location = document.referrer;
			});
			$("footer .inBtn").click(function(){
				self.location = basePath + "fx/${data.jgId }/${data.lryId }"
			});
			$(".share").click(function(){
				//self.location = basePath + "fx/${data.jgId }/${data.lryId }/showShare"
				console.info("分享");
			});
			var bar = $(".head-bar");
			$(window.document).scroll(function(){
				var _this =  $(this);
				var top = _this.scrollTop();
				var opa = 0;
				if(top<10){
					opa = 0;
				}else if(top > 100){
					opa = 1;
				}else if(top > 80){
					opa = 0.8;
				}else if(top > 40){
					opa = 0.5;
				}
				bar.css({'background-color':'rgba(255,255,255,'+ opa +')'});
			});
			
		</script>
	</body>
</html>
