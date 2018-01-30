define(["validate.additional", "select3", 'RememberBaseInfo'], function(a, b, RememberBaseInfo){
	var modal = null;
	var onSelected = null;
	var $table = null;
	var $form = null;
	var $search = null;
	var dt = null;
	var selected = [];
	var baseParams = {};
	var rwData = null;
	
	var o = null;
	
	var setBaseParams = function(){
		baseParams.dqId = $form.find('select[name="dqId"]').val();
		baseParams.kmId = $form.find('select[name="kmId"]').val();
		baseParams.njId = $form.find('select[name="njId"]').val();
		baseParams.xq = $form.find('select[name="xq"]').val();
		
		if(o && !$.isEmptyObject(o)){
			if(!baseParams.dqId && o.dqId){
				baseParams.dqId = o.dqId;
			}
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
		}
		
		if(baseParams.kmId && baseParams.kmId > 0 
			&& baseParams.njId && baseParams.njId > 0
			&& baseParams.dqId
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
        		url: basePath+"zs/zsd/select",
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
			drawCallback: function(){ 
				App.handleTooltips();  
				var checkboxes = $table.find('tr td input.checkboxes');
				checkboxes.each(function(){
					var id = parseInt($(this).val());
					if($.inArray(id, selected) != -1){
						$(this).closest('tr').addClass('checked');
						$(this).attr("checked", true);
					}
				});
				App.initUniform();
			},
            columns: [
					{ title: '<input type="checkbox" class="group-checkable"/>', className: "center", width: "8%", data: "id", render: function(data, type, full){
						if(rwData && rwData.zsdId == data){
							return '<i class="fa fa-check-circle font-green"></i>';
						} else {
							return '<input type="checkbox" class="checkboxes" value="'+data+'"/>';
						}
					}, createdCell: function(td){
					  $(td).attr("align", "center");
					}},
                  { title: "名称", data: "name"},
                  { title: "章节", data: "zjName"},
                  { title: "难度", data: "ndName"},
                  { title: "课时", data: "ks"}
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
				checkboxes.closest('tr').addClass('checked');
			}else{
				checked = false;
				checkboxes.closest('tr').removeClass('checked');
			}
			checkboxes.attr("checked", checked);
			checkboxes.uniform();
		});
		$table.closest('.dataTables_wrapper').on("change", 'tr td input.checkboxes', function(){
			var $tr = $(this).closest('tr');
			if($(this).prop("checked") == true){
				$tr.addClass('checked');
			} else {
				$tr.removeClass('checked');
			}
		});
	};
	
	return {
		init: function(_data, _modal, _selected, _cb){
			modal = _modal;
			onSelected = _cb;
			selected = _selected;
			$table = modal.$element.find('table');
			$search = modal.$element.find('#searchInp');
			$form = modal.$element.find('form');
			rwData = _data;
			dt = null;
			
			o = RememberBaseInfo.load();
			
			setBaseParams(o);
		
			$form.find('select[name="dqId"]').select3({
	    		placeholder: "请选择",
	    		autoLoad: true,
	    		value: baseParams.dqId?baseParams.dqId:null,
	    		tableName: "xzqh",
	    		idField: "code",
				nameField: "name",
				typeField: "pid",
				typeVelue: "330500"
	    	}).on("select.select3", setBaseParams);
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
			
			modal.$element.find('.btn.save').on('click', function(){
				if($.isFunction(onSelected)){
					var datas = dt.api().rows(['.checked']).data();
					if(datas.length == 0 && !rwData){
						App.getAlert().warning("请至少选择一个知识点！", "提示");
						return false;
					}
					var ids = [];
					$.each(datas, function(){
						ids.push(this.id);
					});
					if(rwData){
						ids.push(rwData.zsdId);
					}
					onSelected(ids);
				}
				modal.hide();
			});
		}
	};
});