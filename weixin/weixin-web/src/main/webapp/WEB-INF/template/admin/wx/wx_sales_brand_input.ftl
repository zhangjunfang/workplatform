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
				rules:{attachFile:{required: true}},
				messages:{attachFile:{required: '不能为空!'}},
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
			
			var $salesTypeLS = $("#salesTypeLS").ligerComboBox({
				width:180,
				selectBoxWidth: 180,
                selectBoxHeight: 80,
				cancelable:false,
				valueFieldID:'id',
				data: [
					{ text: '特惠品牌', id: 'isSales' }
                    ,{ text: '会员折扣', id: 'isDiscount' }
				],
				onSelected:function(value){
					$("#salesType").val(value);
				}
			});
			<#if isAdd??>
			var $salesBrand = $("#salesBrand").ligerComboBox({
				width:180,
				selectBoxWidth: 180,
                selectBoxHeight: 120, 
                treeLeafOnly: true,
                tree:{ data:${tree},checkbox: false,idFieldName :'id',parentIDFieldName :'pid'},
				onSelected:function(value){
					if(value =="1"){
						 $salesBrand.setText(''); 
					}else{
						var array = value.split(",");
						if(array[1]!="BRAND"){
						 	$salesBrand.setText(''); 
						}else{
							$("#brandId").val(array[0]);
						}
					}
				}
			});
			$salesBrand.selectValue();
			$salesTypeLS.selectValue();
			<#else>
				$salesTypeLS.selectValue('${(salesType)!}');
  			</#if>
			$("#validateForm").ligerForm();
			
			$("#pageloading").hide();
			
		});
		
		function doSerialize(){
			$validateForm.form();
			if($validateForm.valid()){
				return {'data':$("#validateForm").formSerialize(),'url':$("#validateForm").attr("action"),'attachFile':$("#attachFile")};
			}else{
				return null;
			}
    	}
	</script>
</head>
<body style="padding:10px;">
	<div id="pageloading"></div>
	<form id="validateForm" name="validateForm" action="${base}/admin/wx/salesbrand/<#if isAdd??>save<#else>update/${id}</#if>">
		<input id="id" name="id" type="hidden" value="${(id)!}" />
		<input id="mid" name="mid" type="hidden" value="${(mid)!}" />
		<input id="shopId" name="shopId" type="hidden" value="${(shopId)!}" />
		<input id="brandId" name="brandId" type="hidden" value="${(wxFloorBrand.id)!}" />
		<input id="salesType" name="salesType" type="hidden" value="${(salesType)!}"/>
		<table class="l-table-edit inputTable">
             <tr>
                <th align="right" class="l-table-edit-td"><label class="requireField">*</label>活动品牌:</th>
                <td class="l-table-edit-td">
                	<#if isAdd??>
	                	<input id="salesBrand" name="salesBrand" type="select" ltype="select" validate="{required:true}"/>
                	<#else>
                		${(wxFloorBrand.name)!}
                	</#if>
                </td>
              </tr>
              <tr>
              <th align="right" class="l-table-edit-td"><label class="requireField">*</label>活动类型:</th>
                <td class="l-table-edit-td">
	                <input id="salesTypeLS" name="salesTypeLS" type="select" ltype="select" validate="{required:true}"/>
                </td>
              </tr>
              <tr>
              	<th align="right" class="l-table-edit-td"><label class="requireField">*</label>折扣:</th>
                <td class="l-table-edit-td">
                	<input id="discount" name="discount" value="${(wxFloorBrand.discount)!}" type="text" ltype="text" validate="{number:true,required:true}"/>
                </td>
              </tr>
              <tr>
                <th align="right" class="l-table-edit-td">活动图片:</th>
                <td class="l-table-edit-td">
                    <input type="file" id="attachFile" name="attachFile" validate="{accept:'jpg|jpeg|gif|bmp|png',messages: {accept: '请选择jpg,jpeg,gif,bmp,png格式的图片!'}}"/>
			        <#if (wxFloorBrand.storePath != null)!>&nbsp;<a href="${base}${wxFloorBrand.storePath}" target="_blank" >查看</a></#if> 
			    </td>
              </tr>
		</table>
	</form> 
</body>
</html>