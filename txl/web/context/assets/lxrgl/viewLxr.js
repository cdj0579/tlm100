define(["validate.additional"], function(a, b){
	var $form = null;
	var modal = null;
	
	return {
		init: function(_data, _modal){
			modal = _modal;
			$form = modal.$element.find('form');
			id = _data?_data.id:-1;
			_data = _data || {};
			
			if(_data.xb == 1){
				_data.xb = "女";
			} else if(_data.xb == 2){
				_data.xb = "男";
			} else {
				_data.xb = "保密";
			}
			
			if(_data.nj && _data.nj > 0){
				_data.njName = _data.nj+"年级";
      	  	} else {
      	  		_data.njName =  "";
      	  	}
			
			if(_data.bj && _data.bj > 0){
				_data.bjName = _data.bj+"班";
      	  	} else {
      	  		_data.bjName = "";
      	  	}
			
			$form.find('.form-control-static[name="shiyongzhe"]').html(_data.shiyongzhe);
			$form.find('.form-control-static[name="shijian"]').html(_data.shijian);
			var statusHtml = "";
			if(_data.status == 1){
				statusHtml = '<span class="badge badge-info badge-roundless">已分配</span>';
      	  	} else if(_data.status == 2){
      	  		statusHtml = '<span class="badge badge-danger badge-roundless">已关注</span>';
      	  	} else if(_data.status == 3){
      	  		statusHtml = '<span class="badge badge-success badge-roundless">已共享</span>';
      	  	} else {
      	  		statusHtml = '<span class="badge badge-default badge-roundless">未分配</span>';
      	  	}
			$form.find('.form-control-static[name="status"]').html(statusHtml);
			
			$form.loadForm(_data);
		}
	};
});