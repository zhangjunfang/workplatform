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
			
			$("input[name='roleId']").click(function (){ 
				$(this).attr('checked',!this.checked);
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
	<form id="validateForm" name="validateForm" action="${base}/admin/user/${(userId)!}/roles/save">
		<input id="userId" name="userId" type="hidden" value="${(userId)!}" />
		<input id="mid" name="mid" type="hidden" value="${(mid)!}" />
		<table class="l-table-edit inputTable">
            <tr>
                <th align="right" class="l-table-edit-td"><label class="requireField">*</label>登录名称:</th>
                <td class="l-table-edit-td">
                	${(user.userName)!}
                </td>
            </tr>
            <#if user.email!=null>
            <tr>
                <th align="right" class="l-table-edit-td">电子信箱:</th>
                <td class="l-table-edit-td">
                	${(user.email)!}
                </td>
            </tr>
            </#if>
            <#if user.nickName!=null>
            <tr>
                <th align="right" class="l-table-edit-td">用户昵称:</th>
                <td class="l-table-edit-td">
                	${(user.nickName)!}
                </td>
            </tr>
            </#if>
            <tr>
                <th align="right" class="l-table-edit-td">可用角色:</th>
                <td class="l-table-edit-td">
                	<#if allRoles?? && allRoles?size gt 0>
					<#list allRoles as role>
					<label class="checkbox"><input name="roleId" type="checkbox" <#if (userRoleIds?seq_contains(role.id))!>checked</#if> value="${role.id}" />${role.name}&nbsp;&nbsp;</label> 
					<#if (role_index+1)%5==0><br/></#if>
					</#list>
					<#else>
		        	您还没有添加任何角色信息
		        	</#if>
                </td>
            </tr>
		</table>
	</form> 
</body>
</html>