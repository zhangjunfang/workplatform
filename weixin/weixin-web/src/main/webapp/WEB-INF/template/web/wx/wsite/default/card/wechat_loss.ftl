<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="utf-8">
	<title>卡挂失</title>
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
	<form class="form" action="${base}/web/wx/card/lossed" method="post">
		<input type="hidden" name='user' value='${user}' >
		<input type="hidden" name='wid' value='${wid}' >
		<input type="hidden" name='card' value='${bind.cardNo}' >
		<p class="frm"><input class="txt" type="password" name='passwd' placeholder="请输入你的卡密码">
		<p class="frm hint err" csstxt="">
		<p class="frm submitter"><button class="btn" type='button' data-ca='wechat-auth-auth'>挂 失</button>
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
			'accountNotMatch':'卡号或者密码有误',
			'accountNotExist':'微信账号不存在',
			'lossFail':'卡挂失失败',
			'lossExcetion':'挂失失败,发生异常'
		}
		
		$('.btn').click(function(){
			var card = $.trim($('.form input[name=card]').val()),
				passwd = $('.form input[name=passwd]').val();
			
			if (!passwd) {
				err.attr('csstxt', '请输入你的卡密码');
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
							window.location = '${base}/web/wx/card/loss_success';
						} else if (errorStatus[data.status]) {
							err.attr('csstxt', errorStatus[data.status]);
						} else {
							err.attr('csstxt', '挂失失败');
						}
					}
				});
		})
	})
</script>
</body>
</html>
