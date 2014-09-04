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
</head>
<body>
<a href="http://116.255.227.138/index.html" class="logo" title="爱家">爱家</a>
<p class="entry">
<div id="head_nav" class="clearfix fr">
  <ul class="clearfix">
	<li><a href="${base}/web/wx/casement/${(wid)!}" id="casement" class="<#if shopnav=="casement">nav_current</#if>"><span>服务项目</span></a></li>
	<li><a href="${base}/web/wx/casement/brandnav" id="brandnav" class="<#if shopnav=="brandnav">nav_current</#if>"><span>会员中心</span></a></li>
	<li><a href="${base}/web/wx/casement/guide" id="guide" class="<#if shopnav=="guide">nav_current</#if>"><span>投诉平台</span></a></li>
  </ul>
</div> 
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