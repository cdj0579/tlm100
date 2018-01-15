define({
	require: {
		packages: [{
			"name": "libs",
			"location": "http://192.168.23.199/sjgl/lib"
		}],
		paths: {
			"jquery.ztree.core":"assets/global/plugins/ztree/jquery.ztree.core-3.5",
			"jquery.ztree.exhide":"assets/global/plugins/ztree/jquery.ztree.exhide-3.5.min",
			"ztree.additional":"assets/common/ui/ztree.additional-filter",
			"domready": "assets/common/domReady",
			"ztree": "assets/global/plugins/ztree/jquery.ztree.all-3.5",
			
			"jquery": "assets/global/plugins/jquery.min",
			"bootstrap": "assets/global/plugins/bootstrap/js/bootstrap",
			"bootstrap-switch":"assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min",
			"bootstrap-hover-dropdown":"assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min",
			"daterangepicker": "assets/global/plugins/bootstrap-daterangepicker/daterangepicker.min",
			"bootstrap-datepicker":"assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min",
			"bootstrap-timepicker":"assets/global/plugins/bootstrap-timepicker/js/bootstrap-timepicker.min",
			"bootstrap-datetimepicker":"assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min",
			"bootstrap-toastr": "assets/global/plugins/bootstrap-toastr/toastr.min",
			"bootstrap.wizard": "assets/global/plugins/bootstrap-wizard/jquery.bootstrap.wizard.min",
			"editable": "assets/global/plugins/bootstrap-editable/bootstrap-editable/js/bootstrap-editable.min",
			"context-menu": "assets/global/plugins/bootstrap-contextmenu/bootstrap-contextmenu",
			"uniform":"assets/global/plugins/uniform/jquery.uniform.min",
			"js.cookie":"assets/global/plugins/js.cookie.min",
			"jquery.slimscroll":"assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min",
			"jquery.blockui": "assets/global/plugins/jquery.blockui.min",
			"jquery-ui": "assets/global/plugins/jquery-ui/jquery-ui.min",
			"moment": "assets/global/plugins/moment.min",
			"morris": "assets/global/plugins/morris/morris.min",
			"raphael": "assets/global/plugins/morris/raphael-min",
			"fullcalendar": "assets/global/plugins/fullcalendar/fullcalendar.min",
			"flot": "assets/global/plugins/flot/jquery.flot.min",
			"flot.resize": "assets/global/plugins/flot/jquery.flot.resize.min",
			"flot.categories": "assets/global/plugins/flot/jquery.flot.categories.min",
			"jqvmap": "assets/global/plugins/jqvmap/jqvmap/jquery.vmap",
			"jqvmap.russia": "assets/global/plugins/jqvmap/jqvmap/maps/jquery.vmap.russia",
			"jqvmap.world": "assets/global/plugins/jqvmap/jqvmap/maps/jquery.vmap.world",
			"jqvmap.europe": "assets/global/plugins/jqvmap/jqvmap/maps/jquery.vmap.europe",
			"jqvmap.germany": "assets/global/plugins/jqvmap/jqvmap/maps/jquery.vmap.germany",
			"jqvmap.usa": "assets/global/plugins/jqvmap/jqvmap/maps/jquery.vmap.usa",
			"jqvmap.sampledata": "assets/global/plugins/jqvmap/jqvmap/data/jquery.vmap.sampledata",
			"waypoints": "assets/global/plugins/counterup/jquery.waypoints.min",
			"counterup": "assets/global/plugins/counterup/jquery.counterup.min",
			"easypiechart": "assets/global/plugins/jquery-easypiechart/jquery.easypiechart",
			"sparkline": "assets/global/plugins/jquery.sparkline.min",
			"app": "assets/common/app",
			"ui.frame": "assets/common/Frame",
			"jquery.gritter": "assets/global/plugins/gritter/jquery.gritter",
			'jquery.ui.custom': 'assets/global/plugins/jquery-ui-1.10.1.custom.min',
			"jquery.migrate": "assets/global/plugins/jquery-migrate-1.2.1.min",
			"validate.additional": "assets/global/plugins/form/jquery.validate.addititonal",
			"select2": "assets/global/plugins/select2/js/select2.full.min",
			"select3":"assets/common/ui/select3",
			"ztree.select": "assets/global/plugins/ztree-select/ztree.select",
			"datatables": "assets/global/plugins/datatables/jquery.dataTables.min",
			"datatables.bt": "assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap",
			
			"echarts": "assets/global/plugins/echarts",
			
			"form.wizard": "assets/global/plugins/form/form.wizard",
			"form.section.collapseable": "assets/global/plugins/form/form.section.collapseable",
			"jquery.metadata": "assets/global/plugins/form/jquery.metadata",
			"jquery.validate": "assets/global/plugins/form/jquery.validate",
			"additional-methods.min": "assets/global/plugins/form/additional-methods",
			"validate.message": "assets/global/plugins/form/validate.message_CN",
			"jquery.form": "assets/global/plugins/form/jquery.form",
			"ui-modals":"assets/pages/js/ui-modals",
			"bootstrap-modalmanager":"assets/global/plugins/bootstrap-modal/js/bootstrap-modalmanager",
			"bootstrap-modal":"assets/global/plugins/bootstrap-modal/js/bootstrap-modal",
			"ui-extended-modals":"assets/pages/js/ui-extended-modals",
			"jquery.ui":"assets/global/plugins/jquery-ui/jquery-ui.min",
			"validate.additional":"assets/global/plugins/form/jquery.validate.addititonal",
			//"":"",
			"layout.min":"assets/layouts/layout3/scripts/layout.min",
			"layout":"assets/layouts/layout3/scripts/layout",
			"demo.min":"assets/layouts/layout3/scripts/demo.min",
			"demo":"assets/layouts/layout3/scripts/demo",
		},
		shim: {
			'select2': ['jquery'],
			"select3":['select2'],
			'jquery.metadata': ['jquery'],
			'jquery.form': ['jquery'],
			'ztree':[ 'jquery' ],
			'jquery.ztree.core':[ 'jquery' ],
			"jquery.ztree.exhide": ['jquery'],
            "ztree.additional":[ 'ztree'],  	   
            'editable': ['jquery', 'bootstrap'],
			'bootstrap-datetimepicker':['bootstrap-datepicker','bootstrap-timepicker'],
			'bootstrap.wizard': [ 'jquery' ],
		    'datatables':['jquery'],
			'datatables.bt':['datatables'],
			'bootstrap': [ 'jquery'],
			'layout.min': [ 'app'],
			'demo.min': [ 'app'],
			'layout': [ 'app'],
			'demo': [ 'app'],
			'ui-extended-modals': [ 'jquery','bootstrap-modalmanager','bootstrap-modal'],
			'bootstrap-modalmanager': [ 'jquery'],
			'bootstrap-modal': [ 'jquery'],
			'bootstrap-switch': [ 'bootstrap' ],
			'bootstrap-hover-dropdown': ['jquery'],
			'context-menu': ['jquery'],
			'jquery.gritter': [ 'jquery'],
			 'uniform': [ 'jquery' ],
			 'jquery.migrate': ['jquery'],
			'jquery.ui.custom': ['jquery'],
			'daterangepicker': [
	            'jquery', 
	            'moment'     
			],
			'uniform': [ 'jquery' ],
			'jquery.slimscroll': ['jquery'],
			'jquery.blockui': ['jquery'],
			'morris': [
	            'jquery', 
	            'raphael'          
			 ],
			 'fullcalendar': [
				'jquery', 
				'moment'
			 ],
			 'bootstrap-toastr': {
				 exports: 'toastr'
			 },
			 'flot': ['jquery'],
			 'flot.resize': ['flot'],
			 'flot.categories': ['flot'],
			 'jqvmap': ['flot.resize','flot.categories'],
			 'jqvmap.russia': ['jqvmap'],
			 'jqvmap.world': ['jqvmap'],
			 'jqvmap.europe': ['jqvmap'],
			 'jqvmap.germany': ['jqvmap'],
			 'jqvmap.usa': ['jqvmap'],
			 'jqvmap.sampledata': ['jqvmap'],
			 'counterup': ['jquery', 'waypoints'],
			 'easypiechart': ['jquery'],
			 'sparkline': ['jquery'],
			 'app': {
				 deps:[
				    'jquery', 
			        'bootstrap',
				    'js.cookie',
				    'bootstrap-switch',
				    'bootstrap-hover-dropdown',
				    'uniform',
				    'jquery.slimscroll',
				    'jquery.blockui',
				    'jquery.migrate',
				    'counterup',
				    'bootstrap-toastr',
				  //  'jquery.ui',
				   // 'layout.min',
				   // 'demo.min'
				 ],
				 exports: 'App'
			 },
			 'ui.frame': {
					deps: ['app'],
					exports: 'Frame'
				}
		},
		map: {
	        '*': {
	            'css': 'assets/common/css.min'
	        }
	    },
		waitSeconds: 15
	}
});