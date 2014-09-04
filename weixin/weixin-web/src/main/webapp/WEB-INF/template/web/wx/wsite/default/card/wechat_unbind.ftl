<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>您已成功绑定卡片</title>
<meta name="viewport" content="initial-scale=1.0,user-scalable=no,maximum-scale=1,width=device-width" />
<meta name="viewport" content="initial-scale=1.0,user-scalable=no,maximum-scale=1" media="(device-height: 568px)" />
<meta name="apple-mobile-web-app-capable" content="yes"  />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="format-detection" content="telephone=no" />	
<link rel="stylesheet" href="${base}/static/wechat/css/style.css">
</head>
<body>
<a href="http://http://116.255.227.138/index.html" class="logo" title="微朋">微朋</a>
<p class="entry">
<h2>卡号:${bind.cardNo}</h2>
<div class="main">
	<form class="form" action="${base}/web/wx/card/unbind" method="post">
		<input type="hidden" name='user' value='${user}' >
		<input type="hidden" name='wid' value='${wid}' >
		<input type="hidden" name='card' value='${bind.cardNo}' >
		<article class="intro">
			<p>您即刻享受微朋给你带来的便捷服务,秒查账单、积分、余额！
			<p>另有消费微账单、消费通知提醒！
			<p>只需您告知我们地理位置，即可享受地理位置服务！
			<p>点击"解除绑定"按钮,即可解除绑定！
			<p class="frm submitter"><button class="btn" type='submit' data-ca='wechat-auth-auth'>解除绑定</button>
			<p>点击左上角“返回”，继续体验微朋服务！
		</article>		
	</form>
</div>
<footer>
    <p class="cr">2013 微朋  All Rights Reserved Baoyuan Tech Co.,Ltd.
</footer>
</body>
</html>