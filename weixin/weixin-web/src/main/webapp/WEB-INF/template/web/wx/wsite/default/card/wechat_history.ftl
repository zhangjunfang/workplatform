<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="utf-8">
	<title>最近7天消费明细</title>
	<meta name="viewport" content="initial-scale=1.0,user-scalable=no,maximum-scale=1,width=device-width" />
	<meta name="viewport" content="initial-scale=1.0,user-scalable=no,maximum-scale=1" media="(device-height: 568px)" />
	<meta name="apple-mobile-web-app-capable" content="yes"  />
	<meta name="apple-mobile-web-app-status-bar-style" content="black" />
	<meta name="format-detection" content="telephone=no" />	
	<link rel="stylesheet" href="${base}/static/wechat/css/style.css">
</head>
<body>
<a href="http://116.255.227.138/index.html" class="logo" title="微朋">微朋</a>
<h2>最近7天的消费明细</h2>
<div class="main">
	<#list history as list>
	<article class="intro">
		<p>商户名称：${list.DSHOPNAME}
		<p>交易类型：${list.DTYPE}
		<p>交易金额：${list.DCARD_MONEY?number?string(',##0.00#')}
		<p>交易日期：${(list.DDATE?datetime('yyyy-MM-dd HH:mm:ss'))?string('yyyy-MM-dd HH:mm:ss')}
		<p>------------------------------------------
	</article>
	</#list>
</div>
<footer>
    <p class="cr">2013 微朋  All Rights Reserved Baoyuan Tech Co.,Ltd.
</footer>
</body>
</html>
