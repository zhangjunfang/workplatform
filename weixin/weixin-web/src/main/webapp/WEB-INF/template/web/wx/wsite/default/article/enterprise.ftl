<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>${title}</title>
<meta name="viewport" content="initial-scale=1.0,user-scalable=no,maximum-scale=1,width=device-width" />
<meta name="viewport" content="initial-scale=1.0,user-scalable=no,maximum-scale=1" media="(device-height: 568px)" />
<meta name="apple-mobile-web-app-capable" content="yes"  />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="format-detection" content="telephone=no" />	
<link rel="stylesheet" href="${base}/static/wechat/css/style.css">

<style type="text/css">
    .logo {left:50%;top:29px;position:relative;overflow:hidden;display:block;width:242px;height:59px;margin:0 0 -59px -280px;text-indent:-99em;background:url(${base}/static/wechat/img/${wid}.png) no-repeat}
</style>
</head>
<body>
<a href="http://116.255.227.138/index.html" class="logo"></a>
<p class="entry">
<h2>${article.title}</h2>
<div class="main">
		<article class="intro">
		    ${(article.content)}
			<p>点击左上角“返回”，继续体验微朋服务！
		</article>		
</div>
<footer>
    <p class="cr">2013 微朋  All Rights Reserved Baoyuan Tech Co.,Ltd.
</footer>
</body>
</html>