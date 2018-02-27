/**
 * basePath:当前页面的相对路径，如果导入common/head.jsp,则在head.jsp中设置
 */
if(basePath || basePath == ""){
	require.config({
		baseUrl: basePath
	});
}
define(['assets/common/config'], function(config) {
	require.config(config.require);
	require.config({
		paths: {
		},shim: {
		}
	});
	
	require(['app','layout']);
	require(['domready!', 'app','jquery.form'], function (doc, App){
		
		$(".form-group img").load(function(){
			var img = $(this);
			var _p = img.parent();
			var _w = _p.width();
			var _h = _p.height();
			var rect = clacImgZoomParam(_w, _h, img.outerWidth(), img.outerHeight());
			img.width(rect.width);
		   	img.height(rect.height);
		  	img.css({"margin-top":rect.top+'px'});
		});
		$(".form-group input[type='file']").change(function(){
			var id = $(this).attr("id");
			var file = this;
			var _p = $("label[for='"+ id +"']");
			var _w = _p.width();
			var _h = _p.height();
			var img = _p.find('img') ;
			var reader = new FileReader();
		    reader.onload = function(evt){
		      	img.attr("src",evt.target.result) ;
		    }
			reader.readAsDataURL(file.files[0]);
			$(this).parents("form").find("button").attr("disabled",false);
		});
		
		
		//设置图片显示区域为固定大小,
		var clacImgZoomParam = function( maxWidth, maxHeight, width, height ){
		   var param = {top:0, left:0, width:width, height:height};
		   if( width>maxWidth || height>maxHeight ){
		     rateWidth = width / maxWidth;
		     rateHeight = height / maxHeight;
		     if( rateWidth > rateHeight ){
		         param.width =  maxWidth;
		         param.height = Math.round(height / rateWidth);
		     }else {
		         param.width = Math.round(width / rateHeight);
		         param.height = maxHeight;
		     }
		   }
		   param.left = Math.round((maxWidth - param.width) / 2);
		   param.top = Math.round((maxHeight - param.height) / 2);
		   return param;
		}
		
		$(".btn.center-block").click(function(){
			var form = $(this).parents("form");
			var text = form.find(".form-group>label").text().replace(":","");
			form.ajaxSubmit({
	    		url: basePath+"simgset/saveShareImg",
	    		type: "POST",
	    		dataType: "json",
	    		success: function(result){
	    			var alert = App.getAlert({positionClass:"toast-top-center"});
	    			if(result.success){
	    				form.find("button").attr("disabled",true);
						alert.info("保存"+text+"成功！", "提示");
	    			}else{
	    				alert.info("保存"+text+"失败！", "提示");
	    			}	

	    		}
	    	});
		});
		
		
		
		
		
		
		
		
		
	});
	
});