define(['datatables.bt', "select3", 'RememberBaseInfo', 'CachesSelectedContents'], function(a, b, RememberBaseInfo){
	var modal = null;
	var onSelected = null;
	var $table = null;
	var $form = null;
	var $search = null;
	var $selectedContents = null;
	var dt = null;
	var baseParams = {};
	
	var initTable = function(){
		dt = $table.dataTable( {
			ajax: {
        		url: basePath+"zs/zt/content/list",
        		data: function(data){
        			data.global = true;
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
			drawCallback: function(){ App.handleTooltips();  App.initUniform();},
            columns: [
                  { title: '<input type="checkbox" class="group-checkable"/>', className: "center", width: "8%", data: "id", render: function(data, type, full){
                	  var  checked = isSelected(data)?' checked="checked"':'';
                	  return '<input type="checkbox" class="checkboxes"'+checked+' value="'+data+'" name="'+data+'" />';
				  }, createdCell: function(td){
					  $(td).attr("align", "center");
				  }},
                  { title: "专题", data: "zt", render: function(data, type, full){
                	  return data.name;
                  }},
                  { title: "内容简介", data: "name"},
    	          { title: "操作", data: "id", render: function(data, type, full){
    	        	  return '<a href="javascript:;" class="btn yellow view"><i class="fa fa-search"></i> 查看</a>';
    	          }}
    	      ]
	      } );
		$tableWrapper = $table.closest('.dataTables_wrapper');
		$tableWrapper.find('.dataTables_filter').appendTo($search);
		$table.closest('.dataTables_wrapper').on("click", ".btn.view", function(){
			var data = dt.api().row($(this).closest("tr")).data();
			App.getAlert({
				positionClass: 'toast-top-center'
			}).info(data.content, "["+data.name+"]内容");
		});
		$table.closest('.dataTables_wrapper').on("change", '.group-checkable', function(){
			var checkboxes = $table.find('tr td input.checkboxes');
			var checked = false;
			if($(this).prop("checked")){
				checked = true;
			}else{
				checked = false;
			}
			checkboxes.attr("checked", checked);
			checkboxes.uniform();
			checkboxes.trigger("change");
		});
		$table.closest('.dataTables_wrapper').on("change", 'tr td input.checkboxes', function(){
			var $tr = $(this).closest('tr');
			var data = dt.api().row($tr).data();
			if($(this).prop("checked") == true){
				$selectedContents.cachesSelectedContents("add", data.id, data);
			} else {
				$selectedContents.cachesSelectedContents("remove", data.id);
			}
		});
	};
	
	var setBaseParams = function(o){
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
	
	var isSelected = function(id){
		return $selectedContents.cachesSelectedContents("has", id);
	};
	
	return {
		init: function(_modal, _cb){
			modal = _modal;
			onSelected = _cb;
			dt = null;
			$table = modal.$element.find('table');
			$form = modal.$element.find('form');
			$search = $('#searchInp');
			$selectedContents = modal.$element.find('.selected-contents');
			
			$selectedContents.cachesSelectedContents();
			
			var o = RememberBaseInfo.load();
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
			
			RememberBaseInfo.init($form);
			
			modal.$element.find('.btn.insert').on('click', function(){
				if($.isFunction(onSelected)){
					var datas = $selectedContents.cachesSelectedContents("getAll");
					var htmls = [];
					$.each(datas, function(){
						htmls.push(this.content);
					});
					onSelected(htmls.join('<br/>'));
				}
				modal.hide();
			});
		}
	};
});