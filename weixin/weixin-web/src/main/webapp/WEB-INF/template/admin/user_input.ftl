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
		var validate;
		$(function(){
			
			$.metadata.setType("attr", "validate");
			
			validate = $("#validateForm").validate({
				errorPlacement: function (lable, element) {
                    if (element.hasClass("l-textarea")) {
                        element.addClass("l-textarea-invalid");
                    } else if (element.hasClass("l-text-field")) {
                        element.parent().addClass("l-text-invalid");
                    }
                    $(element).ligerHideTip();
                    $(element).ligerTip({content:lable.html(),width:60});
                },
                success: function (lable) {
                    var element = $("#" + lable.attr("for"));
                    if (element.hasClass("l-textarea")) {
                        element.removeClass("l-textarea-invalid");
                    } else if (element.hasClass("l-text-field")) {
                        element.parent().removeClass("l-text-invalid");
                    }
                    $(element).ligerHideTip();
                }
			});
			
			$("#birthday").ligerDateEditor(<#if isEdit?? && user.birthday!=null>{initValue: '${user.birthday?string("yyyy-MM-dd")}'}</#if>);
				
			$("input[name='roleId']").click(function (){ 
				$(this).attr('checked',!this.checked);
			});
			
			$("#validateForm").ligerForm();
			
			$("#pageloading").hide();
		});
		
		function doSerialize(){
			validate.form();
			if(validate.valid()){
				var result = {'data':$("#validateForm").formSerialize(),'url':$("#validateForm").attr("action")};
				return result;
			}else{
				return null;
			}
    	}
    	
	</script>
</head>
<body style="padding:10px">
	<div id="pageloading"></div>
	<form id="validateForm" name="validateForm" action="${base}/admin/user/<#if isAdd??>save<#else>update/${id}</#if>">
		<input id="id" name="id" type="hidden" value="${(id)!}" />
		<input id="mid" name="mid" type="hidden" value="${(mid)!}" />
		<table class="l-table-edit inputTable">
            <tr>
                <th align="right" class="l-table-edit-td"><label class="requireField">*</label>登录名称:</th>
                <td class="l-table-edit-td">
                	<#if isAdd??>
                	<input id="userName" name="userName" value="" type="text" ltype="text" validate="{required: true,maxlength: 32, messages: {required: '不能为空!',username: true, maxlength: '超过32个字符长度!'}}"/>
                	<#else>
                	${(user.userName)!}
                	</#if>
                </td>
                <th align="right" class="l-table-edit-td" style="width:150">电子信箱:</th>
                <td class="l-table-edit-td">
                	<input id="email" name="email" value="${(user.email)!}" type="text" ltype="text" validate="{required: false,email: true,maxlength: 32, messages: {required: '不能为空!',maxlength: '超过32个字符长度!'}}"/>
                </td>
            </tr>
          	<tr>
                <th align="right" class="l-table-edit-td"><#if isAdd??><label class="requireField">*</label></#if>登录密码:</th>
                <td class="l-table-edit-td">
                	<input id="passwd" name="passwd" type="password" ltype="password" validate="{<#if isAdd??>required: true,</#if>maxlength: 16, messages: {required: '不能为空!',username: true,maxlength: '超过16个字符长度!'}}"/>
                </td>
                <th align="right" class="l-table-edit-td">确认密码:</th>
                <td class="l-table-edit-td">
                	<input id="repasswd" name="repasswd" type="password" ltype="text" validate="{equalTo: '#passwd', messages: {equalTo: '两次密码输入不一致!'}}"/>
                </td>
            </tr>
            <tr>
                <th align="right" class="l-table-edit-td">用户昵称:</th>
                <td class="l-table-edit-td">
                	<input id="nickName" name="nickName" value="${(user.nickName)!}" type="text" ltype="text" validate="{required: false, maxlength: 16, messages: {required: '不能为空!',maxlength: '超过16个字符长度!'}}"/>
                </td>
                <th align="right" class="l-table-edit-td">用户姓名:</th>
                <td class="l-table-edit-td">
                	<input id="realName" name="realName" value="${(user.realName)!}" type="text" ltype="text" validate="{required: false, maxlength: 16, messages: {required: '不能为空!',maxlength: '超过16个字符长度!'}}"/>
                </td>
            </tr>
            <tr>
                <th align="right" class="l-table-edit-td">手机号码:</th>
                <td class="l-table-edit-td">
                	<input id="mobile" name="mobile" value="${(user.mobile)!}" type="text" ltype="text" validate="{required: false, mobile: true, maxlength: 11, messages: {required: '不能为空!',mobile: '手机号码格式错误,请重新输入!',maxlength: '超过11个字符长度!'}}"/>
                </td>
                <th align="right" class="l-table-edit-td">性别:</th>
                <td class="l-table-edit-td">
                	<input id="sex" name="sex" type="radio" ltype="radio" value="-1" <#if (isAdd || user.sex == -1)!> checked</#if>/>&nbsp;保密&nbsp;&nbsp;
                	<input id="sex" name="sex" type="radio" ltype="radio" value="0" <#if (user.sex == 0)!> checked</#if>/>&nbsp;女&nbsp;&nbsp;
                	<input id="sex" name="sex" type="radio" ltype="radio" value="1" <#if (user.sex == 1)!> checked</#if>/>&nbsp;男&nbsp;&nbsp;
                </td>
            </tr>
            <tr>
                <th align="right" class="l-table-edit-td">用户状态:</th>
                <td class="l-table-edit-td" colspan="3">
                	<input id="status" name="status" type="radio" ltype="radio" value="0" <#if (isAdd || user.status == 0)!> checked</#if>/>&nbsp;未启用&nbsp;&nbsp;
                	<input id="status" name="status" type="radio" ltype="radio" value="1" <#if (user.status == 1)!> checked</#if>/>&nbsp;启用&nbsp;&nbsp;
                	<input id="status" name="status" type="radio" ltype="radio" value="2" <#if (user.status == 2)!> checked</#if>/>&nbsp;禁用&nbsp;&nbsp;
                </td>
            </tr>
            <tr>
                <th align="right" class="l-table-edit-td">出生日期:</th>
                <td class="l-table-edit-td" colspan="3">
                	<input id="birthday" name="birthday" type="text"/>
                </td>
            </tr>
            <tr>
                <th align="right" class="l-table-edit-td">备注说明:</th>
                <td class="l-table-edit-td" colspan="3">
                	<textarea id="description" name="description" style="width:320px" rows="3" validate="{maxlength: 32}">${(user.description)!}</textarea>
                </td>
            </tr>
		</table>
	</form> 
</body>
</html>