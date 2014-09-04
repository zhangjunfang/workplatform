
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"> 
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<link href="${base}/static/wsite/css/layout.css" rel="stylesheet" type="text/css" />
<!--组件依赖css begin-->
<link rel="stylesheet" type="text/css" href="${base}/static/wsite/skins/default/css/widget/gotop/gotop.css" />
<link rel="stylesheet" type="text/css" href="${base}/static/wsite/skins/default/css/widget/dropmenu/dropmenu.css" />
<link rel="stylesheet" type="text/css" href="${base}/static/wsite/skins/default/css/widget/dropmenu/dropmenu.default.css" /><!--皮肤文件，若不使用该皮肤，可以不加载-->
<!--组件依赖css end-->
<!--组件依赖js begin-->
<script type="text/javascript" src="${base}/static/wsite/skins/default/js/zepto.js"></script>
<script type="text/javascript" src="${base}/static/wsite/skins/default/js/extend/touch.js"></script>
<script type="text/javascript" src="${base}/static/wsite/skins/default/js/extend/offset.js"></script>
<script type="text/javascript" src="${base}/static/wsite/skins/default/js/extend/parseTpl.js"></script>
<script type="text/javascript" src="${base}/static/wsite/skins/default/js/core/gmu.js"></script>
<script type="text/javascript" src="${base}/static/wsite/skins/default/js/core/event.js"></script>
<script type="text/javascript" src="${base}/static/wsite/skins/default/js/core/widget.js"></script>
<script type="text/javascript" src="${base}/static/wsite/skins/default/js/extend/highlight.js"></script>
<script type="text/javascript" src="${base}/static/wsite/skins/default/js/extend/fix.js"></script>
<script type="text/javascript" src="${base}/static/wsite/skins/default/js/extend/throttle.js"></script>
<script type="text/javascript" src="${base}/static/wsite/skins/default/js/extend/event.scrollStop.js"></script>
<script type="text/javascript" src="${base}/static/wsite/skins/default/js/extend/event.ortchange.js"></script>
<script type="text/javascript" src="${base}/static/wsite/skins/default/js/extend/matchMedia.js"></script>
<script type="text/javascript" src="${base}/static/wsite/skins/default/js/widget/popover/popover.js"></script>
<script type="text/javascript" src="${base}/static/wsite/skins/default/js/widget/popover/arrow.js"></script>
<script type="text/javascript" src="${base}/static/wsite/skins/default/js/widget/popover/dismissible.js"></script>
<script type="text/javascript" src="${base}/static/wsite/skins/default/js/widget/dropmenu/dropmenu.js"></script>
<script type="text/javascript" src="${base}/static/wsite/skins/default/js/widget/dropmenu/placement.js"></script>
<script type="text/javascript" src="${base}/static/wsite/skins/default/js/widget/gotop/gotop.js"></script>
<!--组件依赖js end-->
<title> 服务中心  </title>
<style type="text/css">
body{}
</style>
</head>
<body>
<#include "web/wx/wsite/default/casement/header.ftl" parse="true" encoding="UTF-8">
<div class="c-top"></div>
<div class="container">
	<ul class="guide clearfix">
		<#if articleList??>
			<#list articleList as list>
				<li><a href="${base}/web/wx/casement/guide/${list.id}">
					<div class="pic fl"><img src="<#if list.logoPath??>${base}${list.logoPath}<#else>${base}/static/wsite/skins/default/images/d_time.jpg</#if>" width="60" height="60" /></div>
					<div class="desc fl">${list.title}</div></a>
				</li>
			</#list>
		</#if>
	</ul>
</div>
<#include "web/wx/wsite/default/casement/bottom.ftl" parse="true" encoding="UTF-8">
</body>
</html>