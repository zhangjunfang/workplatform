<!DOCTYPE html>
<html>
<head>
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<meta name="author" content="">
	<meta name="viewport" content="width=device-width" />
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="${base}/static/assets/skins/<@spring.message "default.skin"/>/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="${base}/static/assets/skins/gray/css/all.css" rel="stylesheet" type="text/css" /> 
	<link href="${base}/static/css/base.css" rel="stylesheet" type="text/css" /> 
	<script src="${base}/static/js/jquery-1.5.2.min.js" type="text/javascript"></script>
	<script src="${base}/static/js/jquery.form.js" type="text/javascript"></script>
	<script src="${base}/static/js/jquery.validate.js" type="text/javascript"></script>
	<script src="${base}/static/js/jquery.validate.methods.js" type="text/javascript"></script>
    <script src="${base}/static/js/jquery.metadata.js" type="text/javascript"></script>
    <script src="${base}/static/js/jquery.validate.cn.js" type="text/javascript"></script>
	
	<script src="${base}/static/assets/js/ligerui.min.js" type="text/javascript"></script>
	<script src="${base}/static/js/ligerui.expand.js" type="text/javascript"></script>
	
	<style type="text/css">
		
	</style>
	<script type="text/javascript">
		var $validateForm;
		$(function(){
			$.metadata.setType("attr", "validate");
			
			$validateForm = $("#validateForm").validate({
				errorPlacement: function (lable, element) {
	               if (element.hasClass("l-textarea")){
	                    element.ligerTip({content:lable.html(),target:element[0],width:90}); 
	                }else{
	                    element.parent().ligerTip({content:lable.html(),target:element[0],width:90});
	                }
	            },
	            success: function (lable) {
	                lable.ligerHideTip();
	                lable.remove();
	            }
			});
			
			$("#validateForm").ligerForm();
			
			$("input[name='appSecret']").ligerTextBox({width:280});
			
			$("#pageloading").hide();
		});
		
		function doSerialize(){
			$validateForm.form();
			if($validateForm.valid()){
				return {'data':$("#validateForm").formSerialize(),'url':$("#validateForm").attr("action")};
			}else{
				return null;
			}
    	}
	</script>
</head>
<body style="padding:10px;">
	<div id="pageloading"></div>
	<form id="validateForm" name="validateForm" action="${base}/admin/wx/config/update/valid/${id}">
		<input id="id" name="id" type="hidden" value="${(id)!}" />
		<input id="mid" name="mid" type="hidden" value="${(mid)!}" />
		<table class="l-table-edit inputTable">
			<tr>
                <th align="right" class="l-table-edit-td"><label class="requireField">*</label>微信名称:</th>
                <td class="l-table-edit-td">
                	${(wxConfig.name)!}
                </td>
            </tr>
            <tr>
                <th align="right" class="l-table-edit-td"><label class="requireField">*</label>appID:</th>
                <td class="l-table-edit-td">
                	<input id="appId" name="appId" value="${(wxConfig.appId)!}" type="text" ltype="text" validate="{required: true,maxlength: 19, messages: {required: '不能为空!',maxlength: '超过19个字符长度!'}}"/>
                </td>
            </tr>
            <tr>
                <th align="right" class="l-table-edit-td"><label class="requireField">*</label>appsecret:</th>
                <td class="l-table-edit-td">
                	<input id="appSecret" name="appSecret" value="${(wxConfig.appSecret)!}" type="text" ltype="text" validate="{required: true,maxlength: 32, messages: {required: '不能为空!',maxlength: '超过32个字符长度!'}}"/>
                </td>
            </tr>
        </table>
	</form> 
</body>
</html>