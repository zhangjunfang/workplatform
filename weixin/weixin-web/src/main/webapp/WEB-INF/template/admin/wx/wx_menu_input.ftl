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
			
			$("input[name='key']").ligerTextBox({width:280});
			$("input[name='url']").ligerTextBox({width:280});
			
			var $parentMenu = $("#parentMenu").ligerComboBox({
				width:180,
				cancelable:false,
				valueFieldID:'parentId',
				data: [
					{ text: '请选择...', id: '' }
					<#list parentMenus as menu>
                    ,{ text: '${menu.name}', id: '${menu.id}' }
                    </#list>
				],
				onSelected:function(value){
					
				}
			});
			
			<#if isAdd??>
				$parentMenu.selectValue('');
			<#else>
				$parentMenu.selectValue('${(menu.parentId)!}');
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
	<form id="validateForm" name="validateForm" action="${base}/admin/wx/menu/<#if isAdd??>save<#else>update/${id}</#if>">
		 <input id="id" name="id" type="hidden" value="${(id)!}" />
		<input id="tenantId" name="tenantId" type="hidden" value="${(tenantId)!}" />
        <input id="wid" name="wid" type="hidden" value="${(wid)!}" />
        <table class="l-table-edit inputTable">
			<tr>
                <th align="right" class="l-table-edit-td"><label class="requireField">*</label>菜单标题:</th>
                <td class="l-table-edit-td">
                	<input id="name" name="name" value="${(menu.name)!}" type="text" ltype="text" validate="{required: true,maxlength: 16, messages: {required: '不能为空!',maxlength: '超过16个字符长度!'}}"/>
                </td>
            </tr>
            <tr>
                <th align="right" class="l-table-edit-td"><label class="requireField">*</label>上级菜单:</th>
                <td class="l-table-edit-td">
                	<input id="parentMenu" name="parentMenu" type="select" ltype="select" validate="{required:false}"/>
                </td>
            </tr>
            <tr>
                <th align="right" class="l-table-edit-td"><label class="requireField">*</label>显示序号:</th>
                <td class="l-table-edit-td">
                	<input id="orderNo" name="orderNo" value="${(menu.orderNo)!0}" type="text" ltype="text" validate="{required: true,digits: true, messages: {required: '不能为空!'}}"/>
                </td>
            </tr>
            <tr>
                <th align="right" class="l-table-edit-td"><label class="requireField">*</label>菜单类型:</th>
                <td class="l-table-edit-td">
                	<input id="type" name="type" type="radio" ltype="radio" value="click" <#if (isAdd || menu.type == 'click')!> checked</#if>/>&nbsp;CLICK类型&nbsp;&nbsp;
                	<input id="type" name="type" type="radio" ltype="radio" value="view" <#if (menu.type == 'view')!> checked</#if>/>&nbsp;VIEW类型&nbsp;&nbsp;
                </td>
            </tr>
            <tr>
                <th align="right" class="l-table-edit-td"><label class="requireField">*</label>菜单KEY:</th>
                <td class="l-table-edit-td">
                	<input id="key" name="key" value="${(menu.key)!}" type="text" ltype="text" validate="{maxlength: 128, messages: {maxlength: '字符超长!'}}"/>
                </td>
            </tr>
            <tr>
                <th align="right" class="l-table-edit-td"><label class="requireField">*</label>网页URL:</th>
                <td class="l-table-edit-td">
                	<input id="url" name="url" value="${(menu.url)!}" type="text" ltype="text" validate="{maxlength: 16256, messages: {maxlength: '字符超长!'}}"/>
                </td>
            </tr>
            <tr>
                <th align="right" class="l-table-edit-td">备注:</th>
                <td class="l-table-edit-td">
                	<textarea id="description" name="description" style="width:260px" rows="3" validate="{maxlength: 32}">${(menu.description)!}</textarea>
                </td>
            </tr>
		</table>
	</form> 
</body>
</html>