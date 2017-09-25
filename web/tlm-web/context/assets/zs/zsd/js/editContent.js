define(["validate.additional", "select3", "ztree.select"], function(){
	var $form = null;
	var modal = null;
	var onSave = null;
	var editor = null;
	var isAdd = true;
	var rwId = null;
	var addUrl = "zs/zsd/content/add";
	var updateUrl = "zs/zsd/content/update";
	var id = -1;
	
	var initValidHandler = function(modal){
		var url = isAdd?addUrl:updateUrl;
    	$form.validateB({
            submitHandler: function () {
            	$form.find('textarea[name="content"]').val(editor.getData());
            	$form.ajaxSubmit({
            		url: basePath+url,
            		type: "POST",
            		dataType: "json",
            		data: {
            			rwId: rwId
            		},
            		success: function(result){
            			App.handlerAjaxJson(result, function(){
            				if($.isFunction(onSave)){
            					onSave();
            				}
            				modal.hide();
            			});
            		}
            	});
            }
        });
	};
	
	var initSaveHandler = function(modal){
		modal.$element.find('.btn.save').on("click",function(){
			$form.submit();
		});
	};
	
	var showSelectZsd = function(params, cb){
		App.ajaxModal({
			id: "selectZsd",
			scroll: true,
			width: "600",
			referer: "editContent",
			remote: basePath+"assets/zs/zsd/selectZsd.html",
			callback: function(modal, args){
				var $table = modal.$element.find('table');
				var $search = modal.$element.find('#searchInp');
				var dt = $table.dataTable( {
					ajax: {
		        		url: basePath+"zs/zsd/select",
		        		data: params,
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
		                  { title: "章节", data: "zjName"},
		                  { title: "难度", data: "ndName"},
		                  { title: "课时", data: "ks"},
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
					cb(data.id, data.name);
					modal.hide();
				});
			}
		});
	};
	
	return {
		init: function(_data, _modal, _cb){
			modal = _modal;
			onSave = _cb;
			$form = modal.$element.find('form');
			id = _data&&_data.id?_data.id:-1;
			isAdd = !(id && id > 0);
			rwId = _data&&_data.rwId?_data.rwId:-1;
			
			$form.find('select[name="isOriginal"]').select2({
	    		placeholder: "请选择"
	    	}).on("change", function(){
	    		var isOriginal = $(this).val();
	    		if(isOriginal == 1){
	    			$form.hideOrShowFormItem(['yyfs'], true);
	    		} else {
	    			$form.hideOrShowFormItem(['yyfs'], false);
	    			$form.find('input[name="yyfs"]').val(1);
	    		}
	    	});
			$form.find('select[name="isShare"]').select2({
	    		placeholder: "请选择"
	    	});
			
			if(_data){
				$form.loadForm(_data);
				if(_data.isOriginal && _data.isOriginal>0) $form.find('select[name="isOriginal"]').trigger('change');
				if(_data.isShare && _data.isShare>0) $form.find('select[name="isShare"]').trigger('change');
			}
			
			$('#cke_content').remove();
			require(["ckeditor-ckfinder"], function(){
				CKEDITOR.on( 'instanceCreated', function( event ) {
					editor = event.editor;
					CKFinder.setupCKEditor(editor, basePath+'assets/global/plugins/ckfinder/');
					editor.on('focus', function(){
						$('.cke_button_icon.cke_button__jme_icon').css({
							backgroundSize: "auto"
						});
					});
				});
				CKEDITOR.inline('zsd_content', {
					customConfig : basePath+'assets/jagl/inlineConfig.js',
					extraPlugins: 'jme'
				});
			});
			
			if(_data && _data.pid && _data.pid > 0){
				$form.find('.btn.select').addClass('hide');
			} else {
				$form.find('.btn.select').on('click', function(){
					showSelectZsd({
						kmId: _data.kmId,
						njId: _data.njId,
						dqId: _data.dqId,
						xq: _data.xq
					}, function(zsdId, zsdName){
						$form.find('input[name="pid"]').val(zsdId);
						$form.find('input[name="zsdName"]').val(zsdName);
					});
				});
			}
			
			initValidHandler(modal);
			initSaveHandler(modal);
		}
	};
});