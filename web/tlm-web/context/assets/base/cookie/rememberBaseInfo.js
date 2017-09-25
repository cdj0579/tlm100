define(['js.cookie'], function(Cookies){
	var params = {
			kmId: "base.kmId",
			bbId: "base.bbId",
			njId: "base.njId",
			dqId: "base.dqId",
			xq: "base.xq",
			qzqm: "base.qzqm"
	};
	
	var set = function(param, value){
		if(param){
			if(value){
				Cookies.set(param, value);
			} else {
				Cookies.remove(param);
			}
		}
	};
	
	var get = function(param){
		if(param){
			return Cookies.get(param);
		} else {
			return null;
		}
	};
	
	var load = function(){
		var o = {};
		for(var i in params){
			var v = get(i);
			if(v){
				o[i] = v;
			}
		}
		return o;
	};
	
	var write = function(options){
		if(options){
			for(var i in options){
				set(i, options[i]);
			}
		}
	};
	
	return {
		init: function($form){
			for(var i in params){
				var $el = $form.find('select[name="'+i+'"]');
				if($el.length > 0){
					$el.on('change', function(){
						set($(this).attr('name'), $(this).val());
					});
				}
			}
		},
		load: function(){
			return load();
		},
		set: function(param, value){
			if(typeof(param) == 'object'){
				write(param);
			} else {
				set(param, value);
			}
		},
		get: function(param){
			return get(param);
		}
	};
});