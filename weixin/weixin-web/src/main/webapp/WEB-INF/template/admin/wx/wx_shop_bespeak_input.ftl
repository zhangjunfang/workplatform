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
			
			$("#shopBespeakInfo").ligerTab();
			$("#validateForm").ligerForm();
			
			$("#pageloading").hide();
			
		});
		
		function doSerialize(){
			var html = $('#editor').html();
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
	<form id="validateForm" name="validateForm" action="${base}/admin/wx/shopbespeak/<#if isAdd??>save<#else>update/${id}</#if>">
		<input id="id" name="id" type="hidden" value="${(id)!}" />
		<input id="mid" name="mid" type="hidden" value="${(mid)!}" />
		<input id="shopId" name="shopId" type="hidden" value="${(shopId)!wxShopBespeak.shopId}" />
		<div id="shopBespeakInfo" style="overflow:hidden; border:1px solid #A3C0E8; ">
		<div title="预约服务信息" lselected="true">
		<table class="l-table-edit inputTable">
             <tr>
                <th align="right" class="l-table-edit-td"><label class="requireField">*</label>服务名称:</th>
                <td class="l-table-edit-td">
                	<input id="name" name="name" value="${(wxShopBespeak.name)!}" type="text" ltype="text" validate="{required: true,maxlength: 32,remote:'${base}/admin/wx/shopbespeak/checkName?mid=${(mid)!}&property=name&oldValue=${(wxShopBespeak.name)!""}&shopId=${(wxShopBespeak.shopId)!shopId}', messages: {required: '不能为空!',maxlength: '超过32个字符长度!',remote:'该名称已被使用,请重新输入!'}}"/>
                </td>
                <th align="right" class="l-table-edit-td">是否发布:</th>
                <td class="l-table-edit-td">
                	<input id="isPublication" name="isPublication" type="radio" ltype="radio" value="0" <#if (isAdd || wxShopBespeak.isPublication == 0)!> checked</#if>/>&nbsp;否&nbsp;&nbsp;
                	<input id="isPublication" name="isPublication" type="radio" ltype="radio" value="1" <#if (wxShopBespeak.isPublication == 1)!> checked</#if>/>&nbsp;是&nbsp;&nbsp;
                </td>
              </tr>
               <tr>
               	<th align="right" class="l-table-edit-td">可预约量:</th>
                <td class="l-table-edit-td">
                	<input id="store" name="store" value="${(wxShopBespeak.store)!}" type="text" ltype="text" validate="{digits: true}"/>
                </td>
                <th align="right" class="l-table-edit-td" >服务logo:</th>
                <td class="l-table-edit-td">
                	 <input type="file"id="imgFile" name="imgFile"  validate="{accept:'jpg|jpeg|gif|bmp|png',messages: {accept: '请选择jpg,jpeg,gif,bmp,png格式的图片!'}}"/>
			        <#if (wxShopBespeak.logoPath != null)!>&nbsp;<a href="${base}${wxShopBespeak.logoPath}" target="_blank" >查看</a></#if> 
			    </td>
              </tr>
              <tr>
              	 <th align="right" class="l-table-edit-td">开始日期:</th>
                <td class="l-table-edit-td">
                	<input id="startDate" name="startDate" value="${(wxShopBespeak.startDate?string("yyyy-MM-dd"))!}" type="date" ltype="text" validate="{}"/>
                </td>
                 <th align="right" class="l-table-edit-td">结束日期:</th>
                <td class="l-table-edit-td">
                	<input id="endDate" name="endDate" value="${(wxShopBespeak.endDate?string("yyyy-MM-dd"))!}" type="date" ltype="text" validate="{}"/>
                </td>
              </tr>
               <tr>
                <th align="right" class="l-table-edit-td">预订电话:</th>
                <td class="l-table-edit-td">
                	<input id="phone" name="phone" value="${(wxShopBespeak.phone)!}" type="text" ltype="text" validate="{phone:true}" />
                </td>
               	<th align="right" class="l-table-edit-td">地址:</th>
                <td class="l-table-edit-td">
                	<input id="address" name="address" value="${(wxShopBespeak.address)!}" type="text" ltype="text" validate="{maxlength: 100}"/>
                </td>
            </tr>
              <table>
            </div>
		</div>
	</form> 
</body>
</html>