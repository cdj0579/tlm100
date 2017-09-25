<%@ page contentType="text/html;charset=UTF-8"%>
                        <div class="row">
                            <div class="col-md-12 page-500">
                                <div class=" number font-red"> 500 </div>
                                <div class=" details">
                                    <h3>系统内部异常！</h3>
                                    <p> 系统出现异常, 异常信息： ${exception.message}<br/> 请稍后重新访问，或联系系统维护人员处理.
                                        <br/> </p>
                                    <p>
                                        <a href="${page.basePath }" class="btn red btn-outline"> 返回首页 </a>
                                        <br> </p>
                                </div>
                            </div>
                        </div>
<script src="${page.basePath }assets/common/require.js" data-main="${page.basePath }assets/errors/js/main" type="text/javascript"></script>