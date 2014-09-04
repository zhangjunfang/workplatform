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
		var $validateForm,$className,$methodName;
		
		$(function(){
			$.metadata.setType("attr", "validate");
			
			$validateForm = $("#validateForm").validate({
				rules:{className_val:{required: true},methodName_val:{required: true}},
				messages:{className_val:{required: '必选项,请选择!'},methodName_val:{required: '必选项,请选择!'}},
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
            
			$className = $("#className").ligerComboBox({
				width:320,
				cancelable:false,
				onSelected:function(value){
					if(value==''){
						$methodName.setData([{id:'',text:'请选择...'}]);
						$methodName.selectValue('');
					}else{
						$("#className_val").valid();
						$.post("${base}/admin/logconfig/methods", {
							"className" : value,'mid':'${(mid)!}'
						}, function(data, textStatus) {
							if (data != "") {
								$methodName.setData(data);
								$methodName.selectValue('${(logConfig.methodName)!}');
							} else {
								$methodName.setData([{id:'',text:'请选择...'}]);
								$methodName.selectValue('');
							}
						});
					}
				}
			});	
	
			$methodName = $("#methodName").ligerComboBox({
				isMultiSelect:false,
				width:170,
				cancelable:false
			});	
			
			$.post("${base}/admin/logconfig/controllers",{'mid':'${(mid)!}'},function(data, textStatus) {
				$className.setData(data);
				<#if isAdd??>
					$className.selectValue('');
				<#else>
					$className.selectValue('${(logConfig.className)!}');
	  			</#if>
				
			});
                      
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
	<form id="validateForm" name="validateForm" action="${base}/admin/logconfig/<#if isAdd??>save<#else>update/${id}</#if>">
		<input id="id" name="id" type="hidden" value="${(id)!}" />
		<input id="mid" name="mid" type="hidden" value="${(mid)!}" />
		<table class="l-table-edit inputTable">
            <tr>
                <th align="right" class="l-table-edit-td"><label class="requireField">*</label>操作名称:</th>
                <td class="l-table-edit-td">
                	<input id="operation" name="operation" value="${(logConfig.operation)!}" type="text" ltype="text" validate="{required: true,maxlength: 64, messages: {required: '不能为空!',maxlength: '超过64个字符长度!'}}"/>
                </td>
            </tr>
            <tr>
                <th align="right" class="l-table-edit-td"><label class="requireField">*</label>操作类名:</th>
                <td class="l-table-edit-td">
                	<input id="className" name="className" type="select" ltype="select" validate="{required:true,messages:{required:'必选项,请选择!'}}"/>
                </td>
            </tr>
            <tr>
                <th align="right" class="l-table-edit-td"><label class="requireField">*</label>方法名称:</th>
                <td class="l-table-edit-td">
                	<input id="methodName" name="methodName" type="select" ltype="select" validate="{required:true,messages:{required:'必选项,请选择!'}}"/>
                </td>
            </tr>
            <tr>
                <th align="right" class="l-table-edit-td">是否启用:</th>
                <td class="l-table-edit-td">
                	<input id="enabled" name="enabled" type="radio" ltype="radio" value="1" <#if (isAdd || logConfig.enabled == 1)!> checked</#if>/>&nbsp;启用&nbsp;&nbsp;
                	<input id="enabled" name="enabled" type="radio" ltype="radio" value="0" <#if (logConfig.enabled == 0)!> checked</#if>/>&nbsp;不启用&nbsp;&nbsp;
                </td>
            </tr>
            <tr>
                <th align="right" class="l-table-edit-td">备注:</th>
                <td class="l-table-edit-td">
                	<textarea id="description" name="description" style="width:320px" rows="5" validate="{maxlength: 128}">${(logConfig.description)!}</textarea>
                </td>
            </tr>
		</table>
	</form> 
</body>
</html>