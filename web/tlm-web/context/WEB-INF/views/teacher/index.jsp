<%@ page contentType="text/html;charset=UTF-8"%>
                        <div class="row ">
                                <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                                     <div class="widget-thumb widget-bg-color-white text-uppercase margin-bottom-20 ">
                                         <h4 class="widget-thumb-heading">我的积分</h4>
                                         <div class="widget-thumb-wrap">
                                            <i class="widget-thumb-icon bg-green icon-diamond"></i>
                                            <div class="widget-thumb-body">
                                                <span class="widget-thumb-subtitle">总量</span>
                                                <span class="widget-thumb-body-stat" data-counter="counterup" data-value="${map['jfNum'] }">0</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                                     <div class="widget-thumb widget-bg-color-white text-uppercase margin-bottom-20 ">
                                         <h4 class="widget-thumb-heading">完成任务</h4>
                                         <div class="widget-thumb-wrap">
                                            <i class="widget-thumb-icon bg-red icon-layers"></i>
                                            <div class="widget-thumb-body">
                                                <span class="widget-thumb-subtitle">数量</span>
                                                <span class="widget-thumb-body-stat" data-counter="counterup" data-value="${map['wcrwNum'] }">0</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                                   <div class="widget-thumb widget-bg-color-white text-uppercase margin-bottom-20 ">
                                        <h4 class="widget-thumb-heading">我的习题</h4>
                                        <div class="widget-thumb-wrap">
                                            <i class="widget-thumb-icon bg-blue icon-note"></i>
                                            <div class="widget-thumb-body">
                                                <span class="widget-thumb-subtitle">数量</span>
                                                <span class="widget-thumb-body-stat" data-counter="counterup" data-value="${map['xtNum'] }">0</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                                   <div class="widget-thumb widget-bg-color-white text-uppercase margin-bottom-20 ">
                                        <h4 class="widget-thumb-heading">我的教案</h4>
                                        <div class="widget-thumb-wrap">
                                            <i class="widget-thumb-icon bg-purple icon-briefcase"></i>
                                            <div class="widget-thumb-body">
                                                <span class="widget-thumb-subtitle">数量</span>
                                                <span class="widget-thumb-body-stat" data-counter="counterup" data-value="${map['jaNum'] }">0</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        <div class="row">
                                <div class="col-md-6 col-sm-6">
                                    <div class="portlet light ">
                                        <div class="portlet-title">
                                            <div class="caption font-red">
                                                未领取任务
                                            </div>
                                            <div class="actions">
                                                <a class="btn btn-circle green btn-outline btn-sm reload1">
                                                    <i class="fa fa-repeat"></i>刷新</a>
                                            </div>
                                        </div>
                                        <div class="portlet-body">
                                            <table id="table1" class="table table-hover"></table>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6 col-sm-6">
                                    <div class="portlet light ">
                                        <div class="portlet-title">
                                            <div class="caption font-red">
                                                待完成任务
                                            </div>
                                             <div class="actions">
                                                <a class="btn btn-circle green btn-outline btn-sm reload2">
                                                    <i class="fa fa-repeat"></i>刷新</a>
                                            </div>
                                        </div>
                                        <div class="portlet-body">
                                            <table id="table2" class="table table-hover"></table>
                                        </div>
                                    </div>
                                </div>
                            </div>
<script src="${page.basePath }assets/common/require.js" data-main="${page.basePath }assets/teacher/index" type="text/javascript"></script>