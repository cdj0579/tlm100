CKEDITOR.plugins.add( 'jme', {
    icons: 'jme',
    init: function( editor ) {
        editor.addCommand( 'jmeDialog', new CKEDITOR.dialogCommand( 'jmeDialog' ) );
        editor.ui.addButton( 'jme', {
		    label: '数学公式',
		    command: 'jmeDialog',
		    toolbar: 'insert'
		});
		CKEDITOR.dialog.add( 'jmeDialog', this.path + 'dialogs/jme.js' );
    }
});