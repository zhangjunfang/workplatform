<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="utf-8">
	<title>修改密码</title>
	<meta name="viewport" content="initial-scale=1.0,user-scalable=no,maximum-scale=1,width=device-width" />
	<meta name="viewport" content="initial-scale=1.0,user-scalable=no,maximum-scale=1" media="(device-height: 568px)" />
	<meta name="apple-mobile-web-app-capable" content="yes"  />
	<meta name="apple-mobile-web-app-status-bar-style" content="black" />
	<meta name="format-detection" content="telephone=no" />	
	<link rel="stylesheet" href="${base}/static/wechat/css/style.css">
</head>
<body>
<a href="http://116.255.227.138/index.html" class="logo" title="微朋">微朋</a>
<h2>卡号:${bind.cardNo}</h2>
<div class="main">
	<form class="form" action="${base}/web/wx/card/passwd" method="post">
		<input type="hidden" name='user' value='${user}' >
		<input type="hidden" name='wid' value='${wid}' >
		<input type="hidden" name='card' value='${bind.cardNo}' >
		<p class="frm"><input class="txt" type="password" name='oldpasswd' placeholder="请输入你的旧的卡密码">
		<p class="frm"><input class="txt" type="password" name='newpasswd' placeholder="请输入你的新的卡密码">
		<p class="frm"><input class="txt" type="password" name='rnewpasswd' placeholder="重复输入你的新的卡密码">
		<p class="frm hint err" csstxt="">
		<p class="frm submitter"><button class="btn" type='button' data-ca='wechat-auth-auth'>确 定</button>
	</form>
</div>
<footer>
    <p class="cr">2013 微朋  All Rights Reserved Baoyuan Tech Co.,Ltd.
</footer>
<script type="text/javascript" src="${base}/static/wechat/js/jquery-1.9.1.min.js"></script>
<script>
	$(function(){
		var err = $(".err");
		var errorStatus = {
			'accountNotMatch':'卡校验失败,旧密码有误',
			'changePasswdFail':'卡密码修改失败',
			"accountNotExist":'微信账号不存在',
			"changeExcetion":'修改失败,发生异常'
		}
		
		$('.btn').click(function(){
			var card = $.trim($('.form input[name=card]').val()),
				oldpasswd = $('.form input[name=oldpasswd]').val(),
				rnewpasswd = $('.form input[name=rnewpasswd]').val(),
				newpasswd = $('.form input[name=newpasswd]').val();
			
			if (!oldpasswd) {
				err.attr('csstxt', '请输入你的旧的卡密码');
				return false;
			}

			if (!newpasswd) {
				err.attr('csstxt', '请输入你的新的卡密码');
				return false;
			}
			
			if (newpasswd.length != 6) {
				err.attr('csstxt', '密码长度必须为6位');
				return false;
			}
			
			if (!rnewpasswd) {
				err.attr('csstxt', '请重复输入你的新的卡密码');
				return false;
			}
			
			if (newpasswd != rnewpasswd) {
				err.attr('csstxt', '两次输入的新密码不符');
				return false;
			}
			
			$.ajax({
					url: $(".form").attr('action'),
					data: $(".form").serializeArray(),
					type: "POST",
					dataType: "json",
					cache: false,
					success: function(data) {
						if (data.status == 'ok') {
							window.location = '${base}/web/wx/card/change_passwd_success';
						} else if (errorStatus[data.status]) {
							err.attr('csstxt', errorStatus[data.status]);
						} else {
							err.attr('csstxt', '修改密码失败');
						}
					}
				});
		})
	})
</script>
</body>
</html>
