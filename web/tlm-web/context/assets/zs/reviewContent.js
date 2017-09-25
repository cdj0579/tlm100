define([], function(){
	
	return {
		view: function(id, type){
			App.ajaxModal({
				id: "viewer",
				scroll: true,
				width: "900",
				required: [],
				remote: basePath+"zs/content/view?id="+id+"&type="+type,
				callback: function(modal){
					var $viewer = modal.$element.find('.viewer');
					$viewer.select(function(){
						return false;
					});
				}
			});
			
		}
	};
});