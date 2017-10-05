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
			"imgareaselect":"assets/global/plugins/jquery-imgareaselect/js/jquery.imgareaselect",
			"alert":"assets/global/plugins/jquery.alert/jquery.alerts"
		},shim: {
			"imgareaselect":['app'],
			'alert':['app']
		}
	});
	
	require(['app','layout']);
	require(['domready!', 'app','imgareaselect','jquery.form','alert' ], function (doc, App){
		$(function () { $("[data-toggle='tooltip']").tooltip(); });
	
		function setUploadImg(fileId,value){
			if( value != null){
				$("label[for='"+fileId+"']>img").attr("src", value);
			}
		}
		
		$(".form-group img").load(function(){
			var img = $(this);
			var _p = img.parent();
			var _w = _p.width();
			var _h = _p.height();
			var rect = clacImgZoomParam(_w, _h, img.outerWidth(), img.outerHeight());
			img.width(rect.width);
		   	img.height(rect.height);
		  	img.css({"margin-top":rect.top+'px'});
		})
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
		});
		$("#save").click(function(){
			var form = $("form[name='teacherFrom']");
			form.ajaxSubmit({
        		url: basePath+"user/teacher/saveInfo",
        		type: "POST",
        		dataType: "json",
        		success: function(result){
        			if(result.success){
        				$("span.username").text($("#name").val()); //右上角的真实姓名同步更新
        				jAlert(result.msg, '提示框');
        			}else{
        				jAlert("保存信息失败！", '提示框');
        			}	

        		}
        	});
		})
		
		$.post( basePath+"user/teacher/getTeacherInfo",function(data){
			if(data.success){
				$("#jf").text(data.info.jf);
				$("input[name='id']").val( data.info.id);
				$("#name").val( data.info.name);
				$("#sex option[value='"+ data.info.sex +"']").attr("selected", true); 
				$("#skdz").val( data.info.skdz)
				$("#kmId option[value='"+ data.info.kmId +"']").attr("selected", true);   
				if( data.file.txImg){
					$(".img-position>img").attr("src", data.file.txImg);
				}
				setUploadImg("upload_jszgz",data.file.jszgz);
				setUploadImg("upload_djzs",data.file.djzs);
				setUploadImg("upload_ryzs",data.file.ryzs);
				
			}
			
		});
		
		$.post( basePath+"user/teacher/getGxd_top10",function(data){
			if(data.success){
				var rows =  data.data;
				if(rows.length > 0){
					var str = "";
					for( var i=0;i<rows.length ;i++){
						var list = rows[i];
						var type="知识点"
						if(list.name == 'xt'){
							type="习题"
						}else if(list.name == 'zt'){
							type="专题"
						}else if(list.name == 'ja'){
							type="教案"
						}
						str += '<li>'+ list.name +'<span class="badge">'+type+'</span></li>' 
						
					}
					var gxd_top10 = $("#gxd_top10");
					gxd_top10.empty() ;
					gxd_top10.append(str);
				}
				
			}
		});
		
		
		
		
		
		$("#upload-file").change(function(){
			showImage(this);
		});
		/**
		 * 表示截图位置；
		 */
		var saveParam = {x1:0,y1:0,x2:100, 
					    y2:100,w:100,h:100} ;
		
		var dealUpImg = function(obj){
		 	//图片选择处理
		  	var file = obj;
		 	var MAXWIDTH  = 300; 
		  	var MAXHEIGHT = 200;
	  		var MAXSIZE = 2048*1024;
		 	var div = document.getElementById('preview');
		 	if (file.files && file.files[0]){
	   			if (file.files[0].size > MAXSIZE) {
		      		alert("more than " + (MAXSIZE/1024/1024) + "M");
		 			return false;
				};
			
				div.innerHTML ='<img id=imghead>';
				
			
				var img = document.getElementById('imghead');
				var cutimg = document.getElementById('cutImg');
				img.onload = function(){
				   var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
				   img.width  =  rect.width;
				   img.height =  rect.height;
				   img.style.marginTop = rect.top+'px';
				   cutimg.width  =  rect.width;
				   cutimg.height =  rect.height;
				   //图片剪切区域处理
					$('#imghead').imgAreaSelect({ 
						x1:0, 
					    y1:0, 
					    x2:100, 
					    y2:100, 
					   	parent:'#preview',
					   	instance: true,
					  	aspectRatio: '1:1', //比例
			    	 	handles: true,
			    	 	autoHide:false,
			    	 	show:true,
				    	onSelectChange: preview
					});
				
			    }
			    
			    var reader = new FileReader();
			    reader.onload = function(evt){
			      	img.src = evt.target.result;
			      	cutimg.src = evt.target.result;
			    }
			    reader.readAsDataURL(file.files[0]);
			  }
		}
		
		function preview(img, selection){
			if(!selection.width || !selection.height)   //判断选取区域不为空
   				return;   
			//分别取高宽比率    
	        var scaleX = 100 / (selection.width || 1 );       
	        var scaleY = 100 / (selection.height || 1);                
	        //给裁剪的图片定义高和宽 
	       $('#cutImg').css( {
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

		var showImage = function(obj){
			App.ajaxModal({
				id: "showImage",
				scroll: true,
				width: "500",
				required: [],
				remote: basePath+"assets/user/info/cutImg.html",
				callback: function(modal){
					dealUpImg(obj);
					$("#saveImg").click(function(){
						var img64 = $("#imghead").attr("src");
						saveParam.img = img64;
						var url= basePath+"user/teacher/saveTxImg"
						$.post(url,saveParam,function(data){
							if(data.success){
								modal.hide();
						       	$(".img-position>img").attr("src", data.dataImg)
						       	$(".img-circle").attr("src", data.dataImg) //右上角的图像同步更新
							}
					    });
					});
				}
			});
		};
	});
	
});