<#if !id??><#assign isAdd = true /><#else><#assign isEdit = true /></#if>
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
	<form id="validateForm" name="validateForm" action="${base}/admin/wx/config/<#if isAdd??>save<#else>update/${id}</#if>">
		<input id="id" name="id" type="hidden" value="${(id)!}" />
		<input id="mid" name="mid" type="hidden" value="${(mid)!}" />
		<table class="l-table-edit inputTable">
			<tr>
                <th align="right" class="l-table-edit-td"><label class="requireField">*</label>微信名称:</th>
                <td class="l-table-edit-td">
                	<input id="name" name="name" value="${(wxConfig.name)!}" type="text" ltype="text" validate="{required: true,maxlength: 16, messages: {required: '不能为空!',maxlength: '超过16个字符长度!'}}"/>
                </td>
            </tr>
            <tr>
                <th align="right" class="l-table-edit-td"><label class="requireField">*</label>账号类型:</th>
                <td class="l-table-edit-td">
                	<input id="type" name="type" type="radio" ltype="radio" value="0" <#if (isAdd || wxConfig.type == 0)!> checked</#if>/>&nbsp;测试号&nbsp;&nbsp;
                	<input id="type" name="type" type="radio" ltype="radio" value="1" <#if (wxConfig.type == 1)!> checked</#if>/>&nbsp;订阅号&nbsp;&nbsp;
                	<input id="type" name="type" type="radio" ltype="radio" value="2" <#if (wxConfig.type == 2)!> checked</#if>/>&nbsp;服务号&nbsp;&nbsp;
                </td>
            </tr>
            <tr>
                <th align="right" class="l-table-edit-td"><label class="requireField">*</label>是否认证:</th>
                <td class="l-table-edit-td">
                	<input id="auth" name="auth" type="radio" ltype="radio" value="0" <#if (isAdd || wxConfig.auth == 0)!> checked</#if>/>&nbsp;未认证&nbsp;&nbsp;
                	<input id="auth" name="auth" type="radio" ltype="radio" value="1" <#if (wxConfig.auth == 1)!> checked</#if>/>&nbsp;已认证&nbsp;&nbsp;
                </td>
            </tr>
            <tr>
                <th align="right" class="l-table-edit-td">备注:</th>
                <td class="l-table-edit-td">
                	<textarea id="description" name="description" style="width:200px" rows="3" validate="{maxlength: 32}">${(wxConfig.description)!}</textarea>
                </td>
            </tr>
		</table>
	</form> 
</body>
</html>