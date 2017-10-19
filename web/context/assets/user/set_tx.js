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
			"iscroll-zoom":['jquery'],
			"hammer":['jquery','iscroll-zoom'],			
			"photoClip":['jquery','iscroll-zoom','hammer']
		}
	});
	
	require(['domready!', 'jquery','iscroll-zoom', 'hammer','photoClip'], function (doc){
		
		$(".mui-icon").click(function(){
			history.back();
		});
		
		$("#txImg").change(function(){
			$("#setImg_div").hide();
			$("#selectImg_div").show();
			
		});
		
		/*$("#selectArea").photoClip({
			width: 200,
			height: 200,
			file: "#txImg",
			view: "#view",
			ok: "#setting",
			loadStart: function() {
				console.log("照片读取中");
			},
			loadComplete: function() {
				console.log("照片读取完成");
			},
			clipFinish: function(dataURL) {
				console.log(dataURL);
				$("#setImg_div").show();
				$("#selectImg_div").hide();
			}
		});*/
		
	});
	

});