<%@ page contentType="text/html;charset=UTF-8"%>
                        <div class="row ">
                                <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                                     <div class="widget-thumb widget-bg-color-white text-uppercase margin-bottom-20 ">
                                         <h4 class="widget-thumb-heading">知识点</h4>
                                         <div class="widget-thumb-wrap">
                                            <i class="widget-thumb-icon bg-green icon-diamond"></i>
                                            <div class="widget-thumb-body">
                                                <span class="widget-thumb-subtitle">数量</span>
                                                <span class="widget-thumb-body-stat" data-counter="counterup" data-value="${map['zsdNum']}">0</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                                     <div class="widget-thumb widget-bg-color-white text-uppercase margin-bottom-20 ">
                                         <h4 class="widget-thumb-heading">注册专题</h4>
                                         <div class="widget-thumb-wrap">
                                            <i class="widget-thumb-icon bg-red icon-layers"></i>
                                            <div class="widget-thumb-body">
                                                <span class="widget-thumb-subtitle">数量</span>
                                                <span class="widget-thumb-body-stat" data-counter="counterup" data-value="${map['ztNum'] }">0</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                                   <div class="widget-thumb widget-bg-color-white text-uppercase margin-bottom-20 ">
                                        <h4 class="widget-thumb-heading">创建教案</h4>
                                        <div class="widget-thumb-wrap">
                                            <i class="widget-thumb-icon bg-purple icon-briefcase"></i>
                                            <div class="widget-thumb-body">
                                                <span class="widget-thumb-subtitle">数量</span>
                                                <span class="widget-thumb-body-stat" data-counter="counterup" data-value="${map['jaNum'] }">0</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                                   <div class="widget-thumb widget-bg-color-white text-uppercase margin-bottom-20 ">
                                        <h4 class="widget-thumb-heading">注册用户</h4>
                                        <div class="widget-thumb-wrap">
                                            <i class="widget-thumb-icon bg-blue icon-user"></i>
                                            <div class="widget-thumb-body">
                                                <span class="widget-thumb-subtitle">数量</span>
                                                <span class="widget-thumb-body-stat" data-counter="counterup" data-value="${map['userNum'] }">0</span>
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
                                                <i class="icon-globe font-green-sharp"></i>
                                                <span class="caption-subject bold uppercase">活跃教师</span>
                                                <span class="caption-helper">Top10</span>
                                                <span class="font-green-sharp">
                                                                                <i class="fa fa-bell-o"></i>
                                                                            </span>
                                            </div>
                                            <div class="actions">
                                                <a href="#" class="btn btn-circle green btn-outline btn-sm">
                                                    <i class="fa fa-repeat"></i>刷新</a>
                                            </div>
                                        
                                        </div>
                                        <div class="portlet-body">
                                            <div id="chart_1" style="height: 500px;margin: -40px -65px -20px -30px;">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6 col-sm-6">
                                    <div class="portlet light ">
                                        <div class="portlet-title">
                                            <div class="caption font-red">
                                                <i class="icon-globe font-green-sharp"></i>
                                                <span class="caption-subject bold uppercase">热门知识点</span>
                                                <span class="caption-helper">Top10</span>
                                                <span class="font-green-sharp">
                                                                                <i class="fa fa-bell-o"></i>
                                                                            </span>
                                            </div>
                                             <div class="actions">
                                                <a href="#" class="btn btn-circle green btn-outline btn-sm">
                                                    <i class="fa fa-repeat"></i>刷新</a>
                                            </div>
                                        </div>
                                        <div class="portlet-body">
                                            <div id="chart_2" style="height: 500px;margin: -40px -65px -20px -30px;">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
<script src="${page.basePath }assets/common/require.js" data-main="${page.basePath }assets/home/home" type="text/javascript"></script>
<script src="${page.basePath }assets/global/plugins/echarts/echarts.js" type="text/javascript"></script>          