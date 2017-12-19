define(["validate.additional"], function(a, b){
	var $form = null;
	var modal = null;
	
	return {
		init: function(data, _modal){
			modal = _modal;
			$form = modal.$element.find("form");
			$form.loadForm(data);
		}
	};
});