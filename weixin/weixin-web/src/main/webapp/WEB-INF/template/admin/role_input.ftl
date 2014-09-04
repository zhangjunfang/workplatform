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
<body style="padding:10px">
	<div id="pageloading"></div>
	<form id="validateForm" name="validateForm" action="${base}/admin/role/<#if isAdd??>save<#else>update/${id}</#if>">
		<input id="id" name="id" type="hidden" value="${(id)!}" />
		<input type="hidden" id="mid" name="mid" value="${(mid)!}" />
		<table class="l-table-edit inputTable">
            <tr>
                <th align="right" class="l-table-edit-td"><label class="requireField">*</label>角色名称:</th>
                <td class="l-table-edit-td">
                	<input id="name" name="name" value="${(role.name)!}" type="text" ltype="text" validate="{required:true,minlength:3,maxlength:10,messages:{required:'不能为空'}}" />
                </td>
            </tr>		            
            <tr>
                <th align="right" class="l-table-edit-td">是否启用:</th>
                <td class="l-table-edit-td">
                	<input id="status" name="status" type="radio" ltype="radio" value="1" <#if (isAdd || role.status == 1)!> checked</#if>/>&nbsp;启用&nbsp;&nbsp;
                	<input id="status" name="status" type="radio" ltype="radio" value="0" <#if (role.status == 0)!> checked</#if>/>&nbsp;不启用&nbsp;&nbsp;
                </td>
            </tr>
            <tr>
                <th align="right" class="l-table-edit-td">备注:</th>
                <td class="l-table-edit-td">
                	<textarea id="description" name="description" style="width:250px" rows="4" validate="{maxlength: 128}">${(role.description)!}</textarea>
                </td>
            </tr>
		</table>			
	</form> 
</body>
</html>