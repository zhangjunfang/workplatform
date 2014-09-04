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
			
			$("#articlecategoryInfo").ligerTab();
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
<body style="padding:10px;">
	<div id="pageloading"></div>
	<form id="validateForm" name="validateForm" action="${base}/admin/wx/articlecategory/<#if isAdd??>save<#else>update/${id}</#if>">
		<input id="id" name="id" type="hidden" value="${(id)!}" />
		<input id="mid" name="mid" type="hidden" value="${(mid)!}" />
		<input id="wid" name="wid" type="hidden" value="${(wid)!}" />
		<div id="articleCategoryInfo" style="overflow:hidden; border:1px solid #A3C0E8; ">
		<div title="文章分类" lselected="true">
		<table class="l-table-edit inputTable">
             <tr>
                <th align="right" class="l-table-edit-td"><label class="requireField">*</label>分类名称:</th>
                <td class="l-table-edit-td">
                	<input id="name" name="name" value="${(wxArticleCategory.name)!}" type="text" ltype="text" validate="{required: true,maxlength: 32,remote:'${base}/admin/wx/articlecategory/checkName?mid=${(mid)!}&property=name&oldValue=${(wxArticleCategory.name)!""}&wid=${(wxArticleCategory.wid)!wid}', messages: {required: '不能为空!',maxlength: '超过32个字符长度!',remote:'该名称已被使用,请重新输入!'}}"/>
                </td>
             </tr>
             <tr>
                <th align="right" class="l-table-edit-td"><label class="requireField">*</label>分类标识:</th>
                <td class="l-table-edit-td">
                	<input id="sign" name="sign" value="${(wxArticleCategory.sign)!}" type="text" ltype="text" validate="{required: true,maxlength: 32,remote:'${base}/admin/wx/articlecategory/checkName?mid=${(mid)!}&property=sign&oldValue=${(wxArticleCategory.sign)!""}&wid=${(wxArticleCategory.wid)!wid}', messages: {required: '不能为空!',maxlength: '超过32个字符长度!',remote:'该名称已被使用,请重新输入!'}}"/>
                </td>
              </tr>
              <tr>
                <th align="right" class="l-table-edit-td">排序:</th>
                <td class="l-table-edit-td">
                	<input id="orderList" name="orderList" value="${(wxArticleCategory.orderList)!0}" type="text" ltype="text" validate="{required: true,number:true, messages: {required: '不能为空!'}}"/>
                </td>
              </tr>
              <table>
          </div>
	</div>
	</form> 
</body>
</html>