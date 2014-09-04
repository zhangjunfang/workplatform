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
                        element.ligerTip({content:lable.html(),target:element[0],width:60}); 
                    }else{
                        element.parent().ligerTip({content:lable.html(),target:element[0],width:60});
                    }
                },
                success: function (lable) {
                    lable.ligerHideTip();
                    lable.remove();
                }
			});
			
			$("#validateForm").ligerForm();
			  			
			var $groupList = $("#groupList").ligerComboBox({
				width:180,
        		selectBoxHeight:90,
				cancelable:false,
				valueFieldID:'groupId',
				data: [
					<#list groupList as group>
                    { text: '${group.name}', id: '${group.groupId}' }<#if group_index+1!=groupList?size>,</#if>
                    </#list>
				]
			});
			
			<#if isAdd??>
				$groupList.selectValue('0');
			<#else>
				$groupList.selectValue('${(follower.groupId)!}');
  			</#if>
  			
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
	<form id="validateForm" name="validateForm" action="${base}/admin/wx/followers/<#if isAdd??>save<#else>update/${id}</#if>">
		<input id="id" name="id" type="hidden" value="${(id)!}" />
		<input id="tenantId" name="tenantId" type="hidden" value="${(tenantId)!}" />
        <input id="wid" name="wid" type="hidden" value="${(wid)!}" />
        <input id="openId" name="openId" type="hidden" value="${(follower.openId)!}" />
        <table class="l-table-edit inputTable">
        	<tr>
                <th align="right" class="l-table-edit-td"><label class="requireField">*</label>所属分组:</th>
                <td class="l-table-edit-td">
                	<input id="groupList" name="groupList" type="select" ltype="select" validate="{required:false}"/>
                </td>
            </tr>
			<tr>
                <th align="right" class="l-table-edit-td">备注:</th>
                <td class="l-table-edit-td">
                	<textarea id="description" name="description" style="width:260px" rows="3" validate="{maxlength: 32}">${(follower.description)!}</textarea>
                </td>
            </tr>
		</table>
	</form> 
</body>
</html>