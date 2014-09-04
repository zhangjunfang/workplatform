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
	<script charset="utf-8" src="${base}/static/js/editor/kindeditor.js"></script>
	<script charset="utf-8" src="${base}/static/js/editor/lang/zh_CN.js"></script>
	<script>
	     //编辑器
		if(typeof(KindEditor) != "undefined") {
			KindEditor.ready(function(K) {
				var editor = K.create('textarea[id="editor"]', {
					filterMode:false,
					items:[
					        'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'code', 'cut', 'copy', 'paste',
					        'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
					        'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
					        'superscript', 'clearhtml', 'quickformat','/','selectall', '|', 'fullscreen', 
					        'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
					        'italic', 'underline', 'strikethrough', 'lineheight',  'removeformat', '|', 'image', 'multiimage',
					        'flash', 'media', 'insertfile', 'table', 'hr', 'emoticons', 'baidumap','/', 'pagebreak',
					        'anchor', 'link', 'unlink', '|', 'about'
							],
					cssPath : '${base}/static/js/editor/plugins/code/prettify.css',
					uploadJson : '${base}/upload/upload_json.jsp',
                	fileManagerJson : '${base}/upload/file_manager_json.jsp',
					allowFileManager : true, 
				  	afterCreate : function() { 
        			 	this.sync();
			        }, 
			        afterBlur:function(){ 
			            this.sync(); 
			        }   
				});
			});
		}
	</script>
	
	<style type="text/css">
		
	</style>
	<script type="text/javascript">
		var $validateForm;
		$(function(){
			$.metadata.setType("attr", "validate");
			
			$validateForm = $("#validateForm").validate({
				rules:{typeList_val:{required: true}},
				messages:{typeList_val:{required: '不能为空!'}},
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
			var $typeList = $("#typeList").ligerComboBox({
				width:180,
				cancelable:false,
				valueFieldID:'parentId',
				data: [
					{ text: '请选择...', id: '' }
					<#list listType as type>
                    ,{ text: '${type.name}', id: '${type.id},${type.name}' }
                    </#list>
				],
				onSelected:function(value){
					var array = value.split(",");
					$("#typeId").val(array[0]);
					$("#typeName").val(array[1]);
				}
			});
			
			<#if isAdd??>
				$typeList.selectValue('');
			<#else>
				$typeList.selectValue('${(wxMerchant.typeId)!},${(wxMerchant.typeName)!}');
  			</#if>
  			
			$("#unionInfo").ligerTab();
			$("#validateForm").ligerForm();
			$("#pageloading").hide();
			
		});
		
		function doSerialize(){
			var html = $('#editor').html();
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
	<form id="validateForm" name="validateForm" action="${base}/admin/wx/merchant/<#if isAdd??>save<#else>update/${id}</#if>">
		<input id="id" name="id" type="hidden" value="${(id)!}" />
		<input id="mid" name="mid" type="hidden" value="${(mid)!}" />
		<input id="wid" name="wid" type="hidden" value="${(wid)!wxMerchant.wid}" />
		<input id="typeId" name="typeId" type="hidden" value="${(wxMerchant.typeId)!}" />
		<input id="typeName" name="typeName" type="hidden" value="${(wxMerchant.typeName)!}" />
		<div id="unionInfo" style="overflow:hidden; border:1px solid #A3C0E8; ">
		<div title="商户信息" lselected="true">
		<table class="l-table-edit inputTable">
             <tr>
              <th align="right" class="l-table-edit-td"><label class="requireField">*</label>商户名称:</th>
                <td class="l-table-edit-td">
                	<input id="name" name="name" value="${(wxMerchant.name)!}" type="text" ltype="text"  validate="{required: true,maxlength: 32,remote:'${base}/admin/wx/merchant/checkName?mid=${(mid)!}&property=name&oldValue=${(wxMerchant.name)!""}', messages: {required: '不能为空!',maxlength: '超过32个字符长度!',remote:'该名称已被使用,请重新输入!'}}"/>
                </td>
               
                <th align="right" class="l-table-edit-td"><label class="requireField">*</label>商户分类:</th>
                <td class="l-table-edit-td">
               		<input id="typeList" name="typeList" type="select" ltype="select" validate="{required:true, messages: {required: '不能为空!'}}"/>
                </td>
              </tr>
              </table>
              <table>
               <tr>
                <th align="right" class="l-table-edit-td">商户介绍</th>
                <td class="l-table-edit-td" colspan="2">
                	<textarea id="editor" name="introduction"  style="width:230px;height:320px;" rows="3">
                	${(wxMerchant.introduction)!}
                	</textarea>
                </td>
              </tr>
         <table>
	  </div>
	</div>
	</form> 
</body>
</html>