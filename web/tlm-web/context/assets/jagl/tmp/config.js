/**
 * @license Copyright (c) 2003-2017, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here.
	// For complete reference see:
	// http://docs.ckeditor.com/#!/api/CKEDITOR.config

	// The toolbar groups arrangement, optimized for a single toolbar row.
	/*config.toolbarGroups = [
		{ name: 'document',	   groups: [ 'mode', 'document', 'doctools' ] },
		{ name: 'clipboard',   groups: [ 'clipboard', 'undo' ] },
		{ name: 'editing',     groups: [ 'find', 'selection', 'spellchecker' ] },
		{ name: 'forms' },
		{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
		{ name: 'paragraph',   groups: [ 'list', 'indent', 'blocks', 'align', 'bidi' ] },
		{ name: 'links' },
		{ name: 'insert' },
		{ name: 'styles' },
		{ name: 'colors' },
		{ name: 'tools' },
		{ name: 'others' },
		{ name: 'about' }
	];*/
	
	config.toolbar = [
  		{ name: 'document', items: [ 'Source', 'Undo', 'Redo' ,  '-', 'Preview', 'Print', 'Templates'] },
		{ name: 'styles', items: [ 'Styles', 'Font', 'Format', 'FontSize' ] },
		{ name: 'basicstyles', items: [ 'Bold', 'Italic', 'Underline', 'Strike', 'CopyFormatting' ] },
		{ name: 'colors', items: [ 'TextColor', 'BGColor' ] },
		{ name: 'paragraph', items: [ 'NumberedList', 'BulletedList', '-', 'JustifyLeft', 'JustifyCenter', 'JustifyRight', '-', 'Blockquote'] },
		{ name: 'links', items: [ 'Link', 'Unlink'] },
		{ name: 'insert', items: [ 'Image', 'Table', 'HorizontalRule', '-', 'Maximize'] }
  	];
	
	config.font_names = "微软雅黑";
	
	config.font_defaultLabel = '微软雅黑';
	
	//文字的默认式样 plugins/font/plugin.js
	config.font_style =
	    {
	        element   : 'span',
	        styles   : { 'font-family' : '#(family)' },
	        overrides : [ { element : 'font', attributes : { 'face' : null } } ]
	    };
	
	/*config.extraPlugins = 'autogrow';
	config.autoGrow_minHeight = 500;
	config.autoGrow_maxHeight = 900;*/

	//字体默认大小 plugins/font/plugin.js
	config.fontSize_defaultLabel = '14';

	// The default plugins included in the basic setup define some buttons that
	// are not needed in a basic editor. They are removed here.
	//config.removeButtons = 'Cut,Copy,Paste,Undo,Redo,Anchor,Underline,Strike,Subscript,Superscript';

	// Dialog windows are also simplified.
	//config.removeDialogTabs = 'link:advanced';
};
