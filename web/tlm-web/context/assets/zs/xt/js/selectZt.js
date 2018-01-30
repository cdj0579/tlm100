define(["validate.additional", "select3", 'RememberBaseInfo'], function(a, b, RememberBaseInfo){
	var modal = null;
	var onSelected = null;
	var $table = null;
	var $form = null;
	var $search = null;
	var dt = null;
	var baseParams = {};
	
	var o = null;
	
	var setBaseParams = function(){
		baseParams.kmId = $form.find('select[name="kmId"]').val();
		baseParams.njId = $form.find('select[name="njId"]').val();
		baseParams.qzqm = $form.find('select[name="qzqm"]').val();
		baseParams.xq = $form.find('select[name="xq"]').val();
		
		if(o && !$.isEmptyObject(o)){
			if(!baseParams.kmId && o.kmId){
				baseParams.kmId = o.kmId;
			}
			if(!baseParams.njId && o.njId){
				baseParams.njId = o.njId;
			}
			if(!baseParams.xq && o.xq){
				baseParams.xq = o.xq;
				$form.find('select[name="xq"]').val(baseParams.xq);
			}
			if(!baseParams.qzqm && o.qzqm){
				baseParams.qzqm = o.qzqm;
				$form.find('select[name="qzqm"]').val(baseParams.qzqm);
			}
		}
		
		if(baseParams.kmId && baseParams.kmId > 0 
			&& baseParams.njId && baseParams.njId > 0
			&& baseParams.qzqm && baseParams.qzqm > 0
			&& baseParams.xq && baseParams.xq > 0){
			if(dt){
				reload();
			} else {
				initTable();
			}
		}
	};
	
	var reload = function(){
		dt.api().ajax.reload(null, false);
	};
	
	var initTable = function(){
		dt = $table.dataTable( {
			ajax: {
        		url: basePath+"zs/zt/select",
        		data: function(data){
        			$.extend(data, baseParams);
        		},
				dataSrc: function(result){
					var success = App.handlerGridLoad(result);
					if(success == true){
						return result.data;
					} else {
						return [];
					}
				}
        	},
			deferRender: false,
			ordering: false,
			scrollY: "200px",
			scrollCollapse: false,
			paging: true,
			pageLength: 10,
			lengthChange: false,
			dom: "f<'table-scrollable't><'row'<'col-md-5 col-sm-5'i><'col-md-7 col-sm-7'p>>",
			drawCallback: function(){ App.handleTooltips();  },
            columns: [
                  { title: "名称", data: "name"},
                  { title: "难度", data: "ndName"},
                  { title: "课时(分)", data: "ks"},
    	          { title: "操作", data: "id", render: function(data, type, full){
    	        	  return '<a href="javascript:;" class="btn green selected"><i class="fa fa-check"></i> 选择</a>';
    	          }}
    	      ]
	      } );
		$tableWrapper = $table.closest('.dataTables_wrapper');
		$tableWrapper.find('.dataTables_filter').appendTo($search);
		$table.closest('.dataTables_wrapper').on("click", ".btn.selected", function(){
			var $tr = $(this).closest("tr");
			var data = dt.api().row($tr).data();
			if($.isFunction(onSelected)){
				onSelected(data.id, data.name);
			}
			modal.hide();
		});
	};
	
	return {
		init: function(_data, _modal, _cb){
			modal = _modal;
			onSelected = _cb;
			$table = modal.$element.find('table');
			$search = modal.$element.find('#searchInp');
			$form = modal.$element.find('form');
			dt = null;
			
			o = RememberBaseInfo.load();
			if(_data){
				$.extend(o, _data);
				$form.find('select[name="kmId"]').attr("disabled", "disabled");
				$form.find('select[name="njId"]').attr("disabled", "disabled");
			}
			setBaseParams(o);
			
		
			$form.find('select[name="kmId"]').select3({
				placeholder: "请选择",
				autoLoad: true,
				value: baseParams.kmId?baseParams.kmId:null,
				tableName: "km_dic",
				idField: "id",
				nameField: "name"
			}).on("select.select3", setBaseParams);
			$form.find('select[name="njId"]').select3({
				placeholder: "请选择",
				autoLoad: true,
				value: baseParams.njId?baseParams.njId:null,
				tableName: "nj_dic",
				idField: "id",
				nameField: "name"
			}).on("select.select3", setBaseParams);
			$form.find('select[name="xq"]').select2({
	    		placeholder: "请选择"
	    	}).on("change", setBaseParams);
			$form.find('select[name="qzqm"]').select2({
				placeholder: "请选择"
			}).on("change", setBaseParams);
		}
	};
});