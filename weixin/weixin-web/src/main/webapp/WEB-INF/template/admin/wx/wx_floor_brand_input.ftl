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
			
				var $parentCategory = $("#parentCategory").ligerComboBox({
				width:180,
				selectBoxWidth: 180,
                selectBoxHeight: 120, 
                treeLeafOnly: false,
                tree:{ data:${tree},checkbox: false,idFieldName :'id',parentIDFieldName :'pid'},
				onSelected:function(value){
					if(value =="1"){
						 $parentCategory.setText(''); 
					}else{
						var array = value.split(",");
						$("#categoryId").val(array[0]);
						$("#categoryName").val(array[1]);
					}
				}
			});
			<#if isAdd??>
				$parentCategory.selectValue();
			<#else>
				<#if wxFloorBrand.categoryName!="">
					$parentCategory.selectValue('${(wxFloorBrand.categoryId)!},${(wxFloorBrand.categoryName)!}');
				<#else>
					$parentCategory.selectValue();
				</#if>
  			</#if>
			$("#validateForm").ligerForm();
			
			$("#pageloading").hide();
			
		});
		
		function deleteImg()
		{
		    parent.deleteLogo('${id}','${wxFloorBrand.logoPath}');
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
	<form id="validateForm" name="validateForm" action="${base}/admin/wx/floorbrand/<#if isAdd??>save<#else>update/${id}</#if>">
		<input id="id" name="id" type="hidden" value="${(id)!}" />
		<input id="mid" name="mid" type="hidden" value="${(mid)!}" />
		<input id="floorId" name="floorId" type="hidden" value="${(floorId)!}" />
		<input id="shopId" name="shopId" type="hidden" value="${(shopId)!}" />
		<input id="categoryId" name="categoryId" type="hidden" value="${(wxFloorBrand.categoryId)!}" />
		<input id="categoryName" name="categoryName" type="hidden" value="${(wxFloorBrand.categoryName)!}" />
		<table class="l-table-edit inputTable">
             <tr>
                <th align="right" class="l-table-edit-td"><label class="requireField">*</label>品牌名称:</th>
                <td class="l-table-edit-td">
                <input id="name" name="name" value="${(wxFloorBrand.name)!}" type="text" ltype="text" validate="{required: true,maxlength: 32, remote:'${base}/admin/wx/floorbrand/checkName?mid=${(mid)!}&property=name&oldValue=${(wxFloorBrand.name)!""}&floorId=${(wxFloorBrand.floorId)!floorId}',messages: {required: '不能为空!',maxlength: '超过32个字符长度!',remote:'该名称已被使用,请重新输入!'}}"/>
                </td>
                 </tr>
              <tr>
                 <th align="right" class="l-table-edit-td"><label class="requireField">*</label>品牌分类:</th>
                <td class="l-table-edit-td">
               		<input id="parentCategory" name="parentCategory" type="select" ltype="select" validate="{required:true}"/>
                </td>
              </tr>
              <tr>
                <th align="right" class="l-table-edit-td">logo上传:</th>
                <td class="l-table-edit-td">
                	 <input type="file"id="imgFile" name="imgFile"  validate="{accept:'jpg|gif|bmp|png',messages: {accept: '请选择jpg,gif,bmp,png格式的图片!'}}"/>
			        <#if (wxFloorBrand.logoPath != null)!>
			        &nbsp;<a href="${base}${wxFloorBrand.logoPath}" target="_blank" >查看</a>
			        &nbsp;<a href="javascript:void(0);" onclick="deleteImg();">删除</a>
			        </#if> 
			    </td>
              </tr>
               <tr>
                <th align="right" class="l-table-edit-td">描述:</th>
                <td class="l-table-edit-td">
                	<textarea id="description" name="description" style="width:300px" rows="4" validate="{maxlength: 300}">${(wxFloorBrand.description)!}</textarea>
                </td>
            </tr>
		</table>
	</form> 
</body>
</html>