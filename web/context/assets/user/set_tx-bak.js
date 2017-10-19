var basePath = "./";
require.config({
	baseUrl: basePath
});
define(['assets/common/config'], function(config) {
	require.config(config.require);
	
	require.config({
		paths: {
			"imgareaselect":"assets/global/plugins/jquery-imgareaselect/js/jquery.imgareaselect"
		},shim: {
			"imgareaselect":['app']
		}
	});
	
	require(['domready!', 'app', 'imgareaselect'], function (doc, App){
		
		$(".mui-icon").click(function(){
			
			history.back();
		});
		
		$("#txImg").change(function(){
			var file = this;
			$("#setImg_div").hide();
			var _selectImg = $("#selectImg_div")
			var img = _selectImg.find('img') ;
			img.load(function(){
				var img = $(this);
				var _p = img.parent();
				var _w = _p.width();
				var _h = _p.height();
				var rect = clacImgZoomParam(_w, _h, img.outerWidth(), img.outerHeight());
				img.width(rect.width);
			   	img.height(rect.height);
			  	img.css({"margin-top":rect.top+'px'});
			  	selectArea(img)
			})
			var reader = new FileReader();
		    reader.onload = function(evt){
		      	img.attr("src",evt.target.result) ;
		    }
			reader.readAsDataURL(file.files[0]);
			_selectImg.show();
		});
		
		function selectArea(_obj){
			_obj.imgAreaSelect({ 
				x1:0, 
			    y1:0, 
			    x2:100, 
			    y2:100, 
			   	parent:'#selectArea',
			   	instance: true,
			  	aspectRatio: '1:1', //比例
	    	 	handles: true,
	    	 	autoHide:false,
	    	 	show:true,
		    	onSelectChange: preview
			});
		}
		var saveParam = {x1:0,y1:0,x2:100, 
					    y2:100,w:100,h:100} ;
					    
		function preview(img, selection){
			console.info(selection)
			if(!selection.width || !selection.height)   //判断选取区域不为空
   				return;   
			//分别取高宽比率    
	        var scaleX = 100 / (selection.width || 1 );       
	        var scaleY = 100 / (selection.height || 1);                
	        //给裁剪的图片定义高和宽 
	       $('#imgHead').css( {
	       		width : Math.round(scaleX * img.width),
	       		height : Math.round(scaleX * img.height),
	            marginLeft: -Math.round(scaleX * selection.x1),
	            marginTop: -Math.round(scaleY * selection.y1)
	       });   
	      	saveParam.x1=selection.x1;
	      	saveParam.y1=selection.y1;
	      	saveParam.x2=selection.x2;
	      	saveParam.y2=selection.y2;
	      	saveParam.w=selection.width;
	      	saveParam.h=selection.height;
	    }
		//设置图片显示区域为固定大小,方便后台按统一比例截取图片
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
		
	});
	

});