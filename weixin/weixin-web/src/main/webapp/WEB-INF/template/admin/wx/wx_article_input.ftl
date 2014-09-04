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
				cancelable:false,
				valueFieldID:'parentId',
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
				}
			});
			
			<#if isAdd??>
				$parentCategory.selectValue('');
			<#else>
				$parentCategory.selectValue('${(wxArticle.categoryId)!},${(wxArticle.categoryName)!}');
  			</#if>
			$("#articleInfo").ligerTab();
			$("#validateForm").ligerForm();
			
			$("#pageloading").hide();
			
		});
		
		function deleteImg()
		{
		    parent.deleteLogo('${id}','${wxArticle.logoPath}');
		}
		
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
	<form id="validateForm" name="validateForm" action="${base}/admin/wx/article/<#if isAdd??>save<#else>update/${id}</#if>">
		<input id="id" name="id" type="hidden" value="${(id)!}" />
		<input id="mid" name="mid" type="hidden" value="${(mid)!}" />
		<input id="shopId" name="shopId" type="hidden" value="${(shopId)!wxArticle.shopId}" />
		<input id="categoryId" name="categoryId" type="hidden" value="${(wxArticle.categoryId)!}" />
		<input id="categoryName" name="categoryName" type="hidden" value="${(wxArticle.categoryName)!}" />
		<div id="articleInfo" style="overflow:hidden; border:1px solid #A3C0E8; ">
		<div title="文章信息" lselected="true">
		<table class="l-table-edit inputTable">
             <tr>
                <th align="right" class="l-table-edit-td"><label class="requireField">*</label>文章标题:</th>
                <td class="l-table-edit-td">
                	<input id="title" name="title" value="${(wxArticle.title)!}" type="text" ltype="text" validate="{required: true,maxlength: 32,remote:'${base}/admin/wx/article/checkName?mid=${(mid)!}&property=title&oldValue=${(wxArticle.title)!""}&shopId=${(wxArticle.shopId)!shopId}', messages: {required: '不能为空!',maxlength: '超过32个字符长度!',remote:'该名称已被使用,请重新输入!'}}"/>
                </td>
                <th align="right" class="l-table-edit-td"><label class="requireField">*</label>文章分类:</th>
                <td class="l-table-edit-td">
               		<input id="parentCategory" name="parentCategory" type="select" ltype="select" validate="{required:true}"/>
                </td>
              </tr>
              <tr>
              	<th align="right" class="l-table-edit-td">文章作者:</th>
                <td class="l-table-edit-td">
                	<input id="author" name="author" value="${(wxArticle.author)!}" type="text" ltype="text" validate="{maxlength: 32, messages: {maxlength: '超过32个字符长度!'}}"/>
                </td>
                <th align="right" class="l-table-edit-td">是否发布:</th>
                <td class="l-table-edit-td">
                	<input id="isPublication" name="isPublication" type="radio" ltype="radio" value="0" <#if (isAdd || wxArticle.isPublication == 0)!> checked</#if>/>&nbsp;否&nbsp;&nbsp;
                	<input id="isPublication" name="isPublication" type="radio" ltype="radio" value="1" <#if (wxArticle.isPublication == 1)!> checked</#if>/>&nbsp;是&nbsp;&nbsp;
                </td>
              </tr>
               <tr>
                <th align="right" class="l-table-edit-td" >文章logo:</th>
                <td class="l-table-edit-td">
                	 <input type="file"id="imgFile" name="imgFile"  validate="{accept:'jpg|jpeg|gif|bmp|png',messages: {accept: '请选择jpg,jpeg,gif,bmp,png格式的图片!'}}"/>
			        <#if (wxArticle.logoPath != null)!>
			         <br/>
			         &nbsp;<a href="${base}${wxArticle.logoPath}" target="_blank" >查看</a> 
			         &nbsp;<a href="javascript:void(0);" onclick="deleteImg();">删除</a></#if>
			    </td>
			    <th align="right" class="l-table-edit-td">文章来源:</th>
                <td class="l-table-edit-td">
                	<input id="source" name="source" value="${(wxArticle.source)!}" type="text" ltype="text" validate="{maxlength: 32, messages: {maxlength: '超过32个字符长度!'}}"/>
                </td>
              </tr>
              <table>
              </div>
		<div title="文章内容" >
		<table class="l-table-edit inputTable">
               <tr>
                <th align="right" class="l-table-edit-td">文章内容</th>
                <td class="l-table-edit-td" colspan="2">
                	<textarea id="editor" name="content" class="editor {required: true" style="width:300px;height:400px;">
                	${(wxArticle.content)!}
                	</textarea>
                </td>
              </tr>
		</table>
	</div>
	</div>
	</form> 
</body>
</html>