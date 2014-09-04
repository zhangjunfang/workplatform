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
				rules:{categoryId:{required: true}},
				messages:{categoryId:{required: '不能为空!'}},
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
			
			
			<#if isAdd??>
			var $parentCategory = $("#parentCategory").ligerComboBox({
				width:180,
				cancelable:false,
				valueFieldID:'id',
				data: [
					{ text: '请选择...', id: '' }
					<#list categoryList as category>
                    ,{ text: '${category.name}', id: '${category.id},${category.name}' }
                    </#list>
				],
				onSelected:function(value){
					var array = value.split(",");
					$("#categoryId").val(array[0]);
					$("#categoryName").val(array[1]);
					$("#id").val('');
				}
			});
				$parentCategory.selectValue('');
  			</#if>
  			
			$("#validateForm").ligerForm();
			
			$("#pageloading").hide();
			
		});
		
		function deleteImg()
		{
		    parent.deleteLogo('${id}','${wxShop.logoPath}');
		}
		
		function deleteGpsImg()
		{
		    parent.deleteGps('${id}','${wxShop.gps}');
		}
		
		function doSerialize(){
			$validateForm.form();
			if($validateForm.valid()){
				return {'data':$("#validateForm").formSerialize(),'url':$("#validateForm").attr("action"),'attachFile':$("#attachFile"),'imageFile':$("#imageFile")};
			}else{
				return null;
			}
    	}
	</script>
</head>
<body style="padding:10px;">
	<div id="pageloading"></div>
	<form id="validateForm" name="validateForm" action="${base}/admin/wx/shop/<#if isAdd??>save<#else>update/${id}</#if>">
		<input id="id" name="id" type="hidden" value="${(id)!}" />
		<input id="mid" name="mid" type="hidden" value="${(mid)!}" />
		<input id="wid" name="wid" type="hidden" value="${(wid)!}" />
		<input id="categoryId" name="categoryId" type="hidden" value="${(wxShop.categoryId)!}" />
		<input id="categoryName" name="categoryName" type="hidden" value="${(wxShop.categoryName)!}" />
		<table class="l-table-edit inputTable">
			<tr>
                <th align="right" class="l-table-edit-td"><label class="requireField">*</label>门店名称:</th>
                <td class="l-table-edit-td">
                	<input id="name" name="name" value="${(wxShop.name)!}" type="text" ltype="text" validate="{required:true,maxlength: 32,remote:'${base}/admin/wx/shop/checkName?mid=${(mid)!}&property=name&oldValue=${(wxShop.name)!""}&wid=${(wxShop.wid)!wid}', messages: {required: '不能为空!',remote:'该名称已被使用,请重新输入!',maxlength: '超过32个字符长度!'}}"/>
                </td>
                <th align="right" class="l-table-edit-td"><label class="requireField">*</label>门店类别:</th>
                <td class="l-table-edit-td">
                	<#if isAdd??>
	                	<input id="parentCategory" name="parentCategory" type="select" ltype="select" validate="{required:true}"/>
                	<#else>
                		${(wxShop.categoryName)!}
                	</#if>
                </td>
            </tr>
            <tr>
                <th align="right" class="l-table-edit-td">门店图片:</th>
                <td class="l-table-edit-td">
                    <input type="file" id="imageFile" name="imageFile" validate="{accept:'jpg|jpeg|gif|bmp|png',messages: {accept: '请选择jpg,jpeg,gif,bmp,png格式的图片!'}}"/>
                    <#if (wxShop.logoPath!=null)!>
                    <br/>&nbsp;<a href="${base}${wxShop.logoPath}" target="_blank" >查看</a>
                    &nbsp;<a href="javascript:void(0);" onclick="deleteImg();">删除</a>
                    </#if> 
                </td>
                <th align="right" class="l-table-edit-td">联系电话:</th>
                <td class="l-table-edit-td">
                	<input id="phone" name="phone" value="${(wxShop.phone)!}" type="text" ltype="text" validate="{phone:true,required: false,maxlength: 32, messages: {required: '不能为空!',maxlength: '超过32个字符长度!'}}"/>
                </td>
            </tr>
             <tr>
                <th align="right" class="l-table-edit-td">门店地址:</th>
                <td class="l-table-edit-td">
                	<input id="address" name="address" value="${(wxShop.address)!}" type="text" ltype="text" validate="{required: false,maxlength: 100, messages: {required: '不能为空!',maxlength: '超过100个字符长度!'}}"/>
                </td>
                <th align="right" class="l-table-edit-td">交通指南:</th>
                <td class="l-table-edit-td">
                	<input id="traffic" name="traffic" value="${(wxShop.traffic)!}" type="text" ltype="text" validate="{required: false,maxlength: 200, messages: {required: '不能为空!',maxlength: '超过200个字符长度!'}}"/>
                </td>
             </tr>
            <tr>
                 <th align="right" class="l-table-edit-td">营业时间:</th>
                <td class="l-table-edit-td">
                	<input id="shophors" name="shophors" value="${(wxShop.shophors)!}" type="text" ltype="text" validate="{required: false,maxlength:60, messages: {required: '不能为空!',maxlength: '超过60个字符长度!'}}"/>
                </td>
                <th align="right" class="l-table-edit-td">地理位置:</th>
                <td class="l-table-edit-td">
                    <input type="file" id="attachFile" name="attachFile" validate="{accept:'jpg|jpeg|gif|bmp|png',messages: {accept: '请选择jpg,jpeg,gif,bmp,png格式的图片!'}}"/>
                    <br/> <#if (wxShop.gps!=null)!>
                    &nbsp;<a href="${base}${wxShop.gps}" target="_blank" >查看</a>
                    &nbsp;<a href="javascript:void(0);" onclick="deleteGpsImg();">删除</a>
                    </#if> 
                </td>
            </tr>
            <tr>
                <th align="right" class="l-table-edit-td">简介:</th>
                <td class="l-table-edit-td"  colspan="3">
                	<textarea id="description" name="description" style="width:480px" rows="7" validate="">${(wxShop.description)!}</textarea>
                </td>
            </tr>
		</table>
	</form> 
</body>
</html>