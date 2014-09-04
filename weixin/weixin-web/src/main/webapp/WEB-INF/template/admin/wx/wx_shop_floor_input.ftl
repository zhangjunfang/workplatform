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
	<link href="${base}/static/assets/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
	
	<link href="${base}/static/assets/skins/gray/css/all.css" rel="stylesheet" type="text/css" /> 
	<link href="${base}/static/css/base.css" rel="stylesheet" type="text/css" /> 
	<script src="${base}/static/js/jquery-1.8.1.min.js" type="text/javascript"></script>
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
		
		function deleteImg()
		{
		    parent.deleteLogo('${id}','${wxShopFloor.logoPath}');
		}
		
		function doSerialize(){
			$validateForm.form();
			if($validateForm.valid()){
				return {'data':$("#validateForm").formSerialize(),'url':$("#validateForm").attr("action"),'imageFile':$("#imageFile")};
			}else{
				return null;
			}
    	}
	</script>
</head>
<body style="padding:10px;">
	<div id="pageloading"></div>
	<form id="validateForm" name="validateForm" action="${base}/admin/wx/shopfloor/<#if isAdd??>save<#else>update/${id}</#if>">
		<input id="id" name="id" type="hidden" value="${(id)!}" />
		<input id="mid" name="mid" type="hidden" value="${(mid)!}" />
		<input id="shopId" name="shopId" type="hidden" value="${(shopId)!}" />
		<table class="l-table-edit inputTable">
			<tr>
                <th align="right" class="l-table-edit-td"><label class="requireField">*</label>楼层编号:</th>
                <td class="l-table-edit-td">
                	<input id="no" name="no" value="${(wxShopFloor.no)!}" type="text" ltype="text" validate="{required: true,integer:true,remote:'${base}/admin/wx/shopfloor/checkName?mid=${(mid)!}&property=no&oldValue=${(wxShopFloor.no)!""}&shopId=${(wxShopFloor.shopId)!shopId}',maxlength: 12, messages: {required: '不能为空!',remote:'该编号已使用，请重新输入',maxlength: '超过12个字符长度!'}}"/>
                </td>
            </tr>
             <tr>
                <th align="right" class="l-table-edit-td"><label class="requireField">*</label>楼层名称:</th>
                <td class="l-table-edit-td">
                	<input id="name" name="name" value="${(wxShopFloor.name)!}" type="text" ltype="text" validate="{required: true,maxlength: 32,remote:'${base}/admin/wx/shopfloor/checkName?mid=${(mid)!}&property=name&oldValue=${(wxShopFloor.name)!""}&shopId=${(wxShopFloor.shopId)!shopId}', messages: {required: '不能为空!',remote:'该名称已使用，请重新输入',maxlength: '超过32个字符长度!'}}"/>
                </td>
              </tr>
              <tr>
                <th align="right" class="l-table-edit-td">楼层图片:</th>
                <td class="l-table-edit-td"  colspan="3">
                    <input type="file" id="imageFile" name="imageFile" validate="{accept:'jpg|jpeg|gif|bmp|png',messages: {accept: '请选择jpg,jpeg,gif,bmp,png格式的图片!'}}"/>
                    <#if (wxShopFloor.logoPath!=null)!>
                    &nbsp;<a href="${base}${wxShopFloor.logoPath}" target="_blank" >查看</a>
                    &nbsp;<a href="javascript:void(0);" onclick="deleteImg();" target="_blank" >删除</a>
                    </#if> 
                </td>
            </tr>
               <tr>
                <th align="right" class="l-table-edit-td">描述:</th>
                <td class="l-table-edit-td">
                	<textarea id="description" name="description" style="width:250px" rows="5" validate="{maxlength: 300}">${(wxShopFloor.description)!}</textarea>
                </td>
            </tr>
		</table>
	</form> 
</body>
</html>