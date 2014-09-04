<#if !id??><#assign isAdd = true /><#else><#assign isEdit = true /></#if>
<!DOCTYPE html>
<html>
<head>
	<meta name="keywords" content="" />
	<meta name="description" content="商户分类" />
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
		
		function deleteImg()
		{
		    parent.deleteLogo('${id}','${base}${type.imgPath}');
		}
		
		function doSerialize(){
			$validateForm.form();
			if($validateForm.valid()){
				return {'data':$("#validateForm").formSerialize(),'url':$("#validateForm").attr("action"),'imgFile':$("#imgFile")};
			}else{
				return null;
			}
    	}
	</script>
</head>
<body style="padding:10px;">
	<div id="pageloading"></div>
	<form id="validateForm" name="validateForm" action="${base}/admin/wx/merchanttype/<#if isAdd??>save<#else>update/${id}</#if>">
		<input id="id" name="id" type="hidden" value="${(id)!}" />
		<input id="mid" name="mid" type="hidden" value="${(mid)!}" />
		<input id="wid" name="wid" type="hidden" value="${(wid)!type.wid}" />
		<table class="l-table-edit inputTable">
			<tr>
                <th align="right" class="l-table-edit-td"><label class="requireField">*</label>类别名称:</th>
                <td class="l-table-edit-td">
                	<input id="name" name="name" value="${(type.name)!}" type="text" ltype="text"  validate="{required: true,maxlength: 32,remote:'${base}/admin/wx/merchanttype/checkName?mid=${(mid)!}&property=name&oldValue=${(type.name)!""}', messages: {required: '不能为空!',maxlength: '超过32个字符长度!',remote:'该名称已被使用,请重新输入!'}}"/>
                </td>
            </tr>
            
            <#if (isAdd)!> 
                <tr>
                  <th align="right" class="l-table-edit-td" >类别logo:</th>
                    <td class="l-table-edit-td">
                	 <input type="file"id="imgFile" name="imgFile"  validate="{accept:'jpg|jpeg|gif|bmp|png',messages: {required: '不能为空!',accept: '请选择jpg,jpeg,gif,bmp,png格式的图片!'}}" /><br/><label style="font-size:8px;">提示：图片不能大于100KB</label>
			       </td>
                </tr> 
                <#else>
                 <tr>
                   <th align="right" class="l-table-edit-td" >类别logo:</th>
                   <td class="l-table-edit-td">
                	  <input type="file"id="imgFile" name="imgFile"  validate="{accept:'jpg|jpeg|gif|bmp|png',messages: {accept: '请选择jpg,jpeg,gif,bmp,png格式的图片!'}}" />
			         <#if (type.imgPath != null)!>
			         <br/>
			         &nbsp;<a href="${base}${type.imgPath}" target="_blank" >查看</a>
			         &nbsp;<a href="javascript:void(0);" onclick="deleteImg();">删除</a>
			         </#if> 
			       </td>
                </tr> 
            </#if>
            <tr>
                <th align="right" class="l-table-edit-td"><label class="requireField">*</label>是否启用:</th>
                <td class="l-table-edit-td">
                	<input id="enabled" name="enabled" type="radio" ltype="radio" value="0" <#if (type.enabled == 0)!> checked</#if>/>&nbsp;不启用&nbsp;&nbsp;
                	<input id="enabled" name="enabled" type="radio" ltype="radio" value="1" <#if (isAdd || type.enabled == 1)!> checked</#if>/>&nbsp;启用&nbsp;&nbsp;
                </td>
            </tr>
            <tr>
                <th align="right" class="l-table-edit-td">备注说明:</th>
                <td class="l-table-edit-td">
                	<textarea id="memo" name="memo" style="width:200px" rows="3" validate="{maxlength: 32}">${(type.memo)!}</textarea>
                </td>
            </tr>
		</table>
	</form> 
</body>
</html>