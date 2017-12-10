<%@ page contentType="text/html;charset=UTF-8"%>
<div class="portlet light ">
	<div class="portlet-title">
        <div class="caption">
            <i class="icon-layers font-red"></i>
            <span class="caption-subject font-red bold uppercase">Select2 in modals</span>
        </div>
        <div class="tools">
            <a href="" class="collapse"> </a>
            <a href="#portlet-config" data-toggle="modal" class="config"> </a>
            <a href="" class="reload"> </a>
            <a href="" class="remove"> </a>
        </div>
    </div>
    <div class="portlet-body">
    	<form action="#" class="form-horizontal " id="form_remark">
		    	<div class="form-body">
		    		<div class="row">
		    			<div class="col-md-2">
		    				<div class="form-group">
				           		<label class="control-label col-md-4">状态: </label>
				           		<div class="col-md-8">
				                	<div class="input-icon right">
				                    	<i class="fa"></i>
				                     	<select name="status" class="form-control">
				                     		<option value="0" selected="selected">未分配</option>
				                     		<option value="1">已分配</option>
                     						<option value="2">已关注</option>
                     						<option value="3">已共享</option>
				                     	</select>
				                    </div>
				                </div>
				       		</div>
		    			</div>
		    			<div class="col-md-3">
		    				<div class="form-group">
				           		<label class="control-label col-md-4">所在学校: </label>
				           		<div class="col-md-8">
				                	<div class="input-icon right">
				                    	<i class="fa"></i>
				                     	<select name="xxId" class="form-control" validate="{required:true}">
				                     	</select>
				                    </div>
				                </div>
				       		</div>
		    			</div>
		    			<div class="col-md-3">
		    				<div class="form-group">
				           		<label class="control-label col-md-4">所在地区: </label>
				           		<div class="col-md-8">
				                	<div class="input-icon right">
				                    	<i class="fa"></i>
				                     	<select name="dqId" class="form-control" validate="{required:true}">
				                     	</select>
				                    </div>
				                </div>
				       		</div>
		    			</div>
		    			<div class="col-md-2">
		    				<div class="form-group">
				           		<label class="control-label col-md-4">年级: </label>
				           		<div class="col-md-8">
				                	<div class="input-icon right">
				                    	<i class="fa"></i>
				                     	<select name="nj" class="form-control select2" validate="{required:true}">
				                     		<option value="-1"></option>
				                     		<option value="1">1年级</option>
                     						<option value="2">2年级</option>
                     						<option value="3">3年级</option>
                     						<option value="4">4年级</option>
                     						<option value="5">5年级</option>
                     						<option value="6">6年级</option>
                     						<option value="7">7年级</option>
                     						<option value="8">8年级</option>
                     						<option value="9">9年级</option>
                     						<option value="10">10年级</option>
				       						<option value="11">11年级</option>
				       						<option value="12">12年级</option>
				                     	</select>
				                    </div>
				                </div>
				       		</div>
		    			</div>
		    			<div class="col-md-2">
		    				<div class="form-group">
				           		<label class="control-label col-md-4">班级: </label>
				           		<div class="col-md-8">
				                	<div class="input-icon right">
				                    	<i class="fa"></i>
				                     	<select name="bj" class="form-control" validate="{required:true}">
				                     		<option value="-1"></option>
				                     		<option value="1">1班</option>
                     						<option value="2">2班</option>
                     						<option value="3">3班</option>
                     						<option value="4">4班</option>
                     						<option value="5">5班</option>
                     						<option value="6">6班</option>
                     						<option value="7">7班</option>
                     						<option value="8">8班</option>
                     						<option value="9">9班</option>
                     						<option value="10">10班</option>
                     						<option value="11">11班</option>
                     						<option value="12">12班</option>
                     						<option value="13">13班</option>
                     						<option value="14">14班</option>
                     						<option value="15">15班</option>
                     						<option value="16">16班</option>
                     						<option value="17">17班</option>
                     						<option value="18">18班</option>
                     						<option value="19">19班</option>
                     						<option value="20">20班</option>
				                     	</select>
				                    </div>
				                </div>
				       		</div>
		    			</div>
		    		</div>
		   		</div>
			</form>
			
		<div class="row">
                                                <div class="col-md-6">
                                                	<button type="button" class="btn green on-modal" >on modal</button>
                                                    <button type="submit" class="btn green" data-target="#modal_demo_1" data-toggle="modal">Launch Modal 1</button>
                                                    <button type="submit" class="btn red" data-target="#ajax_modal_edit" data-toggle="modal">Launch Modal 2</button>
                                                </div>
                                            </div>
                                            <div id="modal_demo_1" class="modal fade" role="dialog" aria-hidden="true">
                                                <div class="modal-dialog">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                                            <h4 class="modal-title">Select2 Demo In Modal</h4>
                                                        </div>
                                                        <div class="modal-body">
                                                            <form action="#" class="form-horizontal">
                                                                <div class="form-group">
                                                                    <label class="control-label col-md-4">Basic</label>
                                                                    <div class="col-md-8">
                                                                        <select class="form-control select2">
                                                                            <option></option>
                                                                            <optgroup label="Alaskan">
                                                                                <option value="AK">Alaska</option>
                                                                                <option value="HI" disabled="disabled">Hawaii</option>
                                                                            </optgroup>
                                                                            <optgroup label="Pacific Time Zone">
                                                                                <option value="CA">California</option>
                                                                                <option value="NV">Nevada</option>
                                                                                <option value="OR">Oregon</option>
                                                                                <option value="WA">Washington</option>
                                                                            </optgroup>
                                                                            <optgroup label="Mountain Time Zone">
                                                                                <option value="AZ">Arizona</option>
                                                                                <option value="CO">Colorado</option>
                                                                                <option value="ID">Idaho</option>
                                                                                <option value="MT">Montana</option>
                                                                                <option value="NE">Nebraska</option>
                                                                                <option value="NM">New Mexico</option>
                                                                                <option value="ND">North Dakota</option>
                                                                                <option value="UT">Utah</option>
                                                                                <option value="WY">Wyoming</option>
                                                                            </optgroup>
                                                                            <optgroup label="Central Time Zone">
                                                                                <option value="AL">Alabama</option>
                                                                                <option value="AR">Arkansas</option>
                                                                                <option value="IL">Illinois</option>
                                                                                <option value="IA">Iowa</option>
                                                                                <option value="KS">Kansas</option>
                                                                                <option value="KY">Kentucky</option>
                                                                                <option value="LA">Louisiana</option>
                                                                                <option value="MN">Minnesota</option>
                                                                                <option value="MS">Mississippi</option>
                                                                                <option value="MO">Missouri</option>
                                                                                <option value="OK">Oklahoma</option>
                                                                                <option value="SD">South Dakota</option>
                                                                                <option value="TX">Texas</option>
                                                                                <option value="TN">Tennessee</option>
                                                                                <option value="WI">Wisconsin</option>
                                                                            </optgroup>
                                                                            <optgroup label="Eastern Time Zone">
                                                                                <option value="CT">Connecticut</option>
                                                                                <option value="DE">Delaware</option>
                                                                                <option value="FL">Florida</option>
                                                                                <option value="GA">Georgia</option>
                                                                                <option value="IN">Indiana</option>
                                                                                <option value="ME">Maine</option>
                                                                                <option value="MD">Maryland</option>
                                                                                <option value="MA">Massachusetts</option>
                                                                                <option value="MI">Michigan</option>
                                                                                <option value="NH">New Hampshire</option>
                                                                                <option value="NJ">New Jersey</option>
                                                                                <option value="NY">New York</option>
                                                                                <option value="NC">North Carolina</option>
                                                                                <option value="OH">Ohio</option>
                                                                                <option value="PA">Pennsylvania</option>
                                                                                <option value="RI">Rhode Island</option>
                                                                                <option value="SC">South Carolina</option>
                                                                                <option value="VT">Vermont</option>
                                                                                <option value="VA">Virginia</option>
                                                                                <option value="WV">West Virginia</option>
                                                                            </optgroup>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group">
                                                                    <label class="control-label col-md-4">Multi Select</label>
                                                                    <div class="col-md-8">
                                                                        <select class="form-control select2-multiple" multiple>
                                                                            <optgroup label="Alaskan">
                                                                                <option value="AK">Alaska</option>
                                                                                <option value="HI" disabled="disabled">Hawaii</option>
                                                                            </optgroup>
                                                                            <optgroup label="Pacific Time Zone">
                                                                                <option value="CA">California</option>
                                                                                <option value="NV">Nevada</option>
                                                                                <option value="OR">Oregon</option>
                                                                                <option value="WA">Washington</option>
                                                                            </optgroup>
                                                                            <optgroup label="Mountain Time Zone">
                                                                                <option value="AZ">Arizona</option>
                                                                                <option value="CO">Colorado</option>
                                                                                <option value="ID">Idaho</option>
                                                                                <option value="MT">Montana</option>
                                                                                <option value="NE">Nebraska</option>
                                                                                <option value="NM">New Mexico</option>
                                                                                <option value="ND">North Dakota</option>
                                                                                <option value="UT">Utah</option>
                                                                                <option value="WY">Wyoming</option>
                                                                            </optgroup>
                                                                            <optgroup label="Central Time Zone">
                                                                                <option value="AL">Alabama</option>
                                                                                <option value="AR">Arkansas</option>
                                                                                <option value="IL">Illinois</option>
                                                                                <option value="IA">Iowa</option>
                                                                                <option value="KS">Kansas</option>
                                                                                <option value="KY">Kentucky</option>
                                                                                <option value="LA">Louisiana</option>
                                                                                <option value="MN">Minnesota</option>
                                                                                <option value="MS">Mississippi</option>
                                                                                <option value="MO">Missouri</option>
                                                                                <option value="OK">Oklahoma</option>
                                                                                <option value="SD">South Dakota</option>
                                                                                <option value="TX">Texas</option>
                                                                                <option value="TN">Tennessee</option>
                                                                                <option value="WI">Wisconsin</option>
                                                                            </optgroup>
                                                                            <optgroup label="Eastern Time Zone">
                                                                                <option value="CT">Connecticut</option>
                                                                                <option value="DE">Delaware</option>
                                                                                <option value="FL">Florida</option>
                                                                                <option value="GA">Georgia</option>
                                                                                <option value="IN">Indiana</option>
                                                                                <option value="ME">Maine</option>
                                                                                <option value="MD">Maryland</option>
                                                                                <option value="MA">Massachusetts</option>
                                                                                <option value="MI">Michigan</option>
                                                                                <option value="NH">New Hampshire</option>
                                                                                <option value="NJ">New Jersey</option>
                                                                                <option value="NY">New York</option>
                                                                                <option value="NC">North Carolina</option>
                                                                                <option value="OH">Ohio</option>
                                                                                <option value="PA">Pennsylvania</option>
                                                                                <option value="RI">Rhode Island</option>
                                                                                <option value="SC">South Carolina</option>
                                                                                <option value="VT">Vermont</option>
                                                                                <option value="VA">Virginia</option>
                                                                                <option value="WV">West Virginia</option>
                                                                            </optgroup>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                            </form>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button class="btn dark btn-outline" data-dismiss="modal" aria-hidden="true">Close</button>
                                                            <button class="btn green" data-dismiss="modal">Save changes</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            
    </div>
</div>
<div id="modal_demo_2" class="modal fade" role="dialog" aria-hidden="true">
                                                <div class="modal-dialog">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                                            <h4 class="modal-title">Select2 Demo In Modal</h4>
                                                        </div>
                                                        <div class="modal-body">
                                                            <form action="#" class="form-horizontal">
                                                                <div class="form-group">
                                                                    <label class="control-label col-md-4">Input Groups</label>
                                                                    <div class="col-md-8">
                                                                        <div class="input-group select2-bootstrap-prepend">
                                                                            <span class="input-group-addon">
                                                                                <input type="checkbox" checked> </span>
                                                                            <select id="select2-single-append" class="form-control select2-allow-clear">
                                                                                <option></option>
                                                                                <optgroup label="Alaskan">
                                                                                    <option value="AK">Alaska</option>
                                                                                    <option value="HI" disabled="disabled">Hawaii</option>
                                                                                </optgroup>
                                                                                <optgroup label="Pacific Time Zone">
                                                                                    <option value="CA">California</option>
                                                                                    <option value="NV">Nevada</option>
                                                                                    <option value="OR">Oregon</option>
                                                                                    <option value="WA">Washington</option>
                                                                                </optgroup>
                                                                                <optgroup label="Mountain Time Zone">
                                                                                    <option value="AZ">Arizona</option>
                                                                                    <option value="CO">Colorado</option>
                                                                                    <option value="ID">Idaho</option>
                                                                                    <option value="MT">Montana</option>
                                                                                    <option value="NE">Nebraska</option>
                                                                                    <option value="NM">New Mexico</option>
                                                                                    <option value="ND">North Dakota</option>
                                                                                    <option value="UT">Utah</option>
                                                                                    <option value="WY">Wyoming</option>
                                                                                </optgroup>
                                                                                <optgroup label="Central Time Zone">
                                                                                    <option value="AL">Alabama</option>
                                                                                    <option value="AR">Arkansas</option>
                                                                                    <option value="IL">Illinois</option>
                                                                                    <option value="IA">Iowa</option>
                                                                                    <option value="KS">Kansas</option>
                                                                                    <option value="KY">Kentucky</option>
                                                                                    <option value="LA">Louisiana</option>
                                                                                    <option value="MN">Minnesota</option>
                                                                                    <option value="MS">Mississippi</option>
                                                                                    <option value="MO">Missouri</option>
                                                                                    <option value="OK">Oklahoma</option>
                                                                                    <option value="SD">South Dakota</option>
                                                                                    <option value="TX">Texas</option>
                                                                                    <option value="TN">Tennessee</option>
                                                                                    <option value="WI">Wisconsin</option>
                                                                                </optgroup>
                                                                                <optgroup label="Eastern Time Zone">
                                                                                    <option value="CT">Connecticut</option>
                                                                                    <option value="DE">Delaware</option>
                                                                                    <option value="FL">Florida</option>
                                                                                    <option value="GA">Georgia</option>
                                                                                    <option value="IN">Indiana</option>
                                                                                    <option value="ME">Maine</option>
                                                                                    <option value="MD">Maryland</option>
                                                                                    <option value="MA">Massachusetts</option>
                                                                                    <option value="MI">Michigan</option>
                                                                                    <option value="NH">New Hampshire</option>
                                                                                    <option value="NJ">New Jersey</option>
                                                                                    <option value="NY">New York</option>
                                                                                    <option value="NC">North Carolina</option>
                                                                                    <option value="OH">Ohio</option>
                                                                                    <option value="PA">Pennsylvania</option>
                                                                                    <option value="RI">Rhode Island</option>
                                                                                    <option value="SC">South Carolina</option>
                                                                                    <option value="VT">Vermont</option>
                                                                                    <option value="VA">Virginia</option>
                                                                                    <option value="WV">West Virginia</option>
                                                                                </optgroup>
                                                                            </select>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group">
                                                                    <label class="control-label col-md-4">Select2 multi append</label>
                                                                    <div class="col-md-8">
                                                                        <div class="input-group select2-bootstrap-append">
                                                                            <select id="multi-append" class="form-control select2" multiple>
                                                                                <option></option>
                                                                                <option value="A">A</option>
                                                                                <option value="B">B</option>
                                                                                <option value="C">C</option>
                                                                            </select>
                                                                            <span class="input-group-btn">
                                                                                <button class="btn btn-default" type="button" data-select2-open="multi-append">
                                                                                    <span class="glyphicon glyphicon-search"></span>
                                                                                </button>
                                                                            </span>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </form>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button class="btn dark btn-outline" data-dismiss="modal" aria-hidden="true">Close</button>
                                                            <button class="btn green" data-dismiss="modal">Save changes</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
<div class="modal fade modal-scroll" id="ajax_modal_edit" role="basic">
<div class="modal-dialog" style="width: 900px;"><div class="modal-content"><div class="modal-header">
                                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                                            <h4 class="modal-title">Select2 Demo In Modal</h4>
                                                        </div>
                                                        <div class="modal-body">
                                                            <form action="#" class="form-horizontal">
                                                                <div class="form-group">
                                                                    <label class="control-label col-md-4">Input Groups</label>
                                                                    <div class="col-md-8">
                                                                        <div class="input-group select2-bootstrap-prepend">
                                                                            <span class="input-group-addon">
                                                                                <input type="checkbox" checked=""> </span>
                                                                            <select id="select2-single-append" class="form-control select2-allow-clear select2-hidden-accessible" tabindex="-1" aria-hidden="true">
                                                                                <option></option>
                                                                                <optgroup label="Alaskan">
                                                                                    <option value="AK">Alaska</option>
                                                                                    <option value="HI" disabled="disabled">Hawaii</option>
                                                                                </optgroup>
                                                                                <optgroup label="Pacific Time Zone">
                                                                                    <option value="CA">California</option>
                                                                                    <option value="NV">Nevada</option>
                                                                                    <option value="OR">Oregon</option>
                                                                                    <option value="WA">Washington</option>
                                                                                </optgroup>
                                                                                <optgroup label="Mountain Time Zone">
                                                                                    <option value="AZ">Arizona</option>
                                                                                    <option value="CO">Colorado</option>
                                                                                    <option value="ID">Idaho</option>
                                                                                    <option value="MT">Montana</option>
                                                                                    <option value="NE">Nebraska</option>
                                                                                    <option value="NM">New Mexico</option>
                                                                                    <option value="ND">North Dakota</option>
                                                                                    <option value="UT">Utah</option>
                                                                                    <option value="WY">Wyoming</option>
                                                                                </optgroup>
                                                                                <optgroup label="Central Time Zone">
                                                                                    <option value="AL">Alabama</option>
                                                                                    <option value="AR">Arkansas</option>
                                                                                    <option value="IL">Illinois</option>
                                                                                    <option value="IA">Iowa</option>
                                                                                    <option value="KS">Kansas</option>
                                                                                    <option value="KY">Kentucky</option>
                                                                                    <option value="LA">Louisiana</option>
                                                                                    <option value="MN">Minnesota</option>
                                                                                    <option value="MS">Mississippi</option>
                                                                                    <option value="MO">Missouri</option>
                                                                                    <option value="OK">Oklahoma</option>
                                                                                    <option value="SD">South Dakota</option>
                                                                                    <option value="TX">Texas</option>
                                                                                    <option value="TN">Tennessee</option>
                                                                                    <option value="WI">Wisconsin</option>
                                                                                </optgroup>
                                                                                <optgroup label="Eastern Time Zone">
                                                                                    <option value="CT">Connecticut</option>
                                                                                    <option value="DE">Delaware</option>
                                                                                    <option value="FL">Florida</option>
                                                                                    <option value="GA">Georgia</option>
                                                                                    <option value="IN">Indiana</option>
                                                                                    <option value="ME">Maine</option>
                                                                                    <option value="MD">Maryland</option>
                                                                                    <option value="MA">Massachusetts</option>
                                                                                    <option value="MI">Michigan</option>
                                                                                    <option value="NH">New Hampshire</option>
                                                                                    <option value="NJ">New Jersey</option>
                                                                                    <option value="NY">New York</option>
                                                                                    <option value="NC">North Carolina</option>
                                                                                    <option value="OH">Ohio</option>
                                                                                    <option value="PA">Pennsylvania</option>
                                                                                    <option value="RI">Rhode Island</option>
                                                                                    <option value="SC">South Carolina</option>
                                                                                    <option value="VT">Vermont</option>
                                                                                    <option value="VA">Virginia</option>
                                                                                    <option value="WV">West Virginia</option>
                                                                                </optgroup>
                                                                            </select><span class="select2 select2-container select2-container--bootstrap" dir="ltr"><span class="selection"><span class="select2-selection select2-selection--single" role="combobox" aria-autocomplete="list" aria-haspopup="true" aria-expanded="false" tabindex="0" aria-labelledby="select2-select2-single-append-container"><span class="select2-selection__rendered" id="select2-select2-single-append-container" title=""><span class="select2-selection__clear">×</span></span><span class="select2-selection__arrow" role="presentation"><b role="presentation"></b></span></span></span><span class="dropdown-wrapper" aria-hidden="true"></span></span>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group">
                                                                    <label class="control-label col-md-4">Select2 multi append</label>
                                                                    <div class="col-md-8">
                                                                        <div class="input-group select2-bootstrap-append">
                                                                            <select id="multi-append" class="form-control select2 select2-hidden-accessible" multiple="" tabindex="-1" aria-hidden="true">
                                                                                <option></option>
                                                                                <option value="A">A</option>
                                                                                <option value="B">B</option>
                                                                                <option value="C">C</option>
                                                                            </select><span class="select2 select2-container select2-container--bootstrap" dir="ltr"><span class="selection"><span class="select2-selection select2-selection--multiple" role="combobox" aria-autocomplete="list" aria-haspopup="true" aria-expanded="false" tabindex="0"><ul class="select2-selection__rendered"><li class="select2-search select2-search--inline"><input class="select2-search__field" type="search" tabindex="-1" autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false" role="textbox" placeholder="" style="width: 0.75em;"></li></ul></span></span><span class="dropdown-wrapper" aria-hidden="true"></span></span>
                                                                            <span class="input-group-btn">
                                                                                <button class="btn btn-default" type="button" data-select2-open="multi-append">
                                                                                    <span class="glyphicon glyphicon-search"></span>
                                                                                </button>
                                                                            </span>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </form>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button class="btn dark btn-outline" data-dismiss="modal" aria-hidden="true">Close</button>
                                                            <button class="btn green" data-dismiss="modal">Save changes</button>
                                                        </div></div></div></div>
<script src="${page.basePath }assets/common/require.js" data-main="${page.basePath }assets/home/home" type="text/javascript"></script>      