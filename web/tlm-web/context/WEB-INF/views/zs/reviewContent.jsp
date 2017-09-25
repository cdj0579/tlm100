<%@ page contentType="text/html;charset=UTF-8"%>

<style>
.viewer {
	-moz-user-select: none; /*火狐*/
	-webkit-user-select: none; /*webkit浏览器*/
	-ms-user-select: none; /*IE10*/
	-khtml-user-select: none; /*早期浏览器*/
	user-select: none;
}
</style>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
    <h4 class="modal-title" id="title">信息预览</h4>
</div>
<div class="modal-body">
	<div class="viewer" onselectstart="return false">${info.content } ${info.answer }</div>
</div>