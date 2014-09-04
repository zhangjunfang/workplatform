<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>统一登录中心-<@spring.message "admin_title"/></title>
    <meta name="keywords" content=""/>
	<meta name="description" content=""/>
	<meta name="viewport" content="width=device-width"/>
    <meta name="description" content="统一登录中心">
    <link rel="shortcut icon" href="${base}/static/img/favicon.ico"/>
	<link rel="apple-touch-icon" href="${base}/static/img/touchicon.png"/>
    <link rel="stylesheet" href="${base}/static/css/login.css">
    <script type="text/javascript">
	if (self != top) {
		top.location = self.location;
	};
	</script>
</head>
<body>
	<div class="login-container">
        <div class="header"></div>
        <form name="loginForm" id="loginForm" action="${base}/admin/account/check" method="post">
        	<div class="legend">统一登录中心</div>
            <hr />
            <hr class="decoration" />
            <div class="hint"><span id="error" class="error"></span></div>
            <fieldset>
                <div class="input-prepend">
                    <span class="add-on">
                        <i class="icon-user"></i>
                    </span>
                    <input type="text" id="user" name="user" value="" placeholder="域账号或工作账号" class="input" validate="{required:true,messages:{required:'请填写域账号或工作账号!'}}" />
                </div>
                <div class="input-prepend">
                    <span class="add-on">
                        <i class="icon-key"></i>
                    </span>
                    <input type="password" id="pwd" name="pwd" value="" placeholder="密　码" class="input" validate="{required:true,messages:{required:'请填写域账号或工作账号密码!'}}" />
                </div>
				 <label class="checkbox">
                    <input type="checkbox" id="rememberMe" name="rememberMe"  />
                    &nbsp;保持登录 
                </label>
                <button type="submit" class="btn-login">登录</button>
            </fieldset>
            <hr class="separator"/>
            <div class="hint hint-ul">
                <ul>
                    <li>
                        <a href="${base}/admin/account/forget">忘记密码?</a>
                    </li>
                </ul>
            </div>            
        </form>
     </div>
</body>
<script src="${base}/static/js/jquery-1.8.1.min.js"></script>
<script src="${base}/static/js/jquery.placeholder.js"></script>
<script src="${base}/static/js/jquery.form.js" type="text/javascript"></script>
<script src="${base}/static/js/jquery.metadata.js" type="text/javascript"></script>
<script src="${base}/static/js/jquery.validate.js"></script>
<script src="${base}/static/js/jquery.validate.methods.js" type="text/javascript"></script>
<script>
    $(function(){
    	$('input').placeholder();
    	
    	$.metadata.setType("attr", "validate");
    	
    	$("#loginForm").validate({
            success: function (lable) {
                $("#error").html('');
            },  
            showErrors: function(errors) {
            	if(errors) {
            		 $("#error").html('');
            		for ( var name in errors ) {
            			 $("#error").html(errors[name]);
            			 break;
            		}
            	}
            },           
			submitHandler: function () {
				var url = $("#loginForm").attr("action");
				var data = $("#loginForm").formSerialize();				
				$.ajax({
				  type:'POST',
				  dataType:'JSON',
				  url: url,
				  data: data,
				  success: function(result){ 
					  	if(result.status=='success'){
					  		 $(window.location).attr('href', '${base}'+result.url);
					  	}else{
					  		$("#error").html(result.message);
					  	}
				  }
				});
            }				
		});
    });
</script>
</html>