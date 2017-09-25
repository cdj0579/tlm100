/**
 * basePath:当前页面的相对路径，如果导入common/head.jsp,则在head.jsp中设置
 */
if(basePath || basePath == ""){
	require.config({
		baseUrl: basePath
	});
}
define(['assets/common/config'], function(config) {
	require.config(config.require);
	require.config({
		paths: {
			'ckeditor-core': 'assets/global/plugins/ckeditor/full/ckeditor',
			'ckeditor-jquery':'assets/global/plugins/ckeditor/full/adapters/jquery'
		},shim: {
			'ckeditor-jquery':{
		        deps:['jquery','ckeditor-core']
		    }
		}
	});
	CKEDITOR_BASEPATH = basePath+'assets/global/plugins/ckeditor/full/';
	require(['app','layout','demo']);
	require(['domready!', 'app', 'ckeditor-jquery'], function (doc, App){
		
		$('#editor1').ckeditor({
	        customConfig : basePath+'assets/jagl/tmp/config.js',
	        extraPlugins: 'autogrow',
			autoGrow_minHeight: 200,
			autoGrow_maxHeight: 500,
			removePlugins: 'resize'/*,
	        skin:'office2003'*/
	    });
		
		var editor = null;
		CKEDITOR.on( 'instanceReady', function( ev ) {
			editor = CKEDITOR.instances.editor1;
			/*var style = new CKEDITOR.style( { element: 'body', attributes: { 'class': 'document-editor' } } );
			style.checkApplicable( editor.elementPath(), editor );
			editor.applyStyle( style );*/
			editor.document.getBody().addClass('document-editor');
			
			editor.execCommand('bold');
			editor.execCommand('font', ['微软雅黑']);
			editor.on('font', function(e){
				console.log(e);
			});
		});
		
		/*CKEDITOR.replace( 'editor1', {
			//customConfig : '/custom/ckeditor_config.js'
			on: {
				focus: onFocus,
				blur: onBlur,

				// Check for availability of corresponding plugins.
				pluginsLoaded: function( evt ) {
					var doc = CKEDITOR.document, ed = evt.editor;
					if ( !ed.getCommand( 'bold' ) )
						doc.getById( 'exec-bold' ).hide();
					if ( !ed.getCommand( 'link' ) )
						doc.getById( 'exec-link' ).hide();
				}
			}
		});*/
		
	});
	
});