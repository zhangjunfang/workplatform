<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>联盟商户</title>
<meta name="viewport" content="initial-scale=1.0,user-scalable=no,maximum-scale=1,width=device-width" />
<meta name="viewport" content="initial-scale=1.0,user-scalable=no,maximum-scale=1" media="(device-height: 568px)" />
<meta name="apple-mobile-web-app-capable" content="yes"  />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="format-detection" content="telephone=no" />	
<link rel="stylesheet" href="${base}/static/wechat/css/style.css">
<style type="text/css">
    .brand_img
    {
        font-size: 1.5em; /* 24px */
    }
</style>
</head>
<body>
<a href="http://www.hnxym.com/" class="logo" title="爱家">爱家</a>
<p class="entry">
<h2>联盟商户</h2>
<div class="main">
		<article class="intro">
		      <#list allLIST as mer>
				<div class="brand_img">
				    <a href="${base}/web/wx/casement/merchantInfo/${wid}?id=${mer.id}"> 
					<#--<a href="http://192.168.0.200:8082/weixin-web/web/wx/casement/merchantInfo/${wid}?id=${mer.id}">-->
					  ${mer.name}
			    	</a>
				</div>
				</#list>
			<p>点击左上角“返回”，继续体验爱家服务！
		</article>		
</div>
<footer>
    <p class="cr">2013 微朋  All Rights Reserved Baoyuan Tech Co.,Ltd.
</footer>
</body>
</html>