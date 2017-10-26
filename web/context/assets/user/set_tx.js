var basePath = "./";
require.config({
	baseUrl: basePath
});
define(['assets/common/config'], function(config) {
	require.config(config.require);
	
	require.config({
		paths: {
			"photoClip":"assets/jquery.photoClip/js/jquery.photoClip",
			"hammer":"assets/jquery.photoClip/js/hammer",
			"iscroll-zoom":"assets/jquery.photoClip/js/iscroll-zoom"
		},shim: {
			"iscroll-zoom":{
				exports: 'IScroll'
			},
			"photoClip":['app']
		}
	});
	
	require(['domready!', 'app','photoClip'], function (doc,App){
		
		$(".mui-icon").click(function(){
			history.back();
		});
		
		$("#txImg").change(function(){
			$("#setImg_div").hide();
			$("#selectImg_div").show();
			
		});
		$.ajaxSetup({
		   beforeSend: function(xhr){
			   if(this.showBlockUI != false){
				   App.blockUI({ message: "正在保持中..." });
			   }
		   }
		});
		$("#selectArea").photoClip({
			width: 200,
			height: 200,
			file: "#txImg",
			view: "#view",
			ok: "#setting",
			loadStart: function() {
				//console.log("照片读取中");
			},
			loadComplete: function() {
				//console.log("照片读取完成");
			},
			clipFinish: function(dataURL) {
				//并保持提交 
				App.post(App.remoteUrlPre+"saveStuHeadImg", {"headImg":dataURL},function(result){
					$("#setImg_div").show();
					$("#selectImg_div").hide();
					var alert = App.getAlert({positionClass:"toast-top-center"});
					alert.success("保存头像图片成功！", "提示");
		     	});
			}
		});
		
	});
	

});