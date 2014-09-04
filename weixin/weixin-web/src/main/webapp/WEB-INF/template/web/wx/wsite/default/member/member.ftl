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
<title> 会员之家 </title>
<style type="text/css">
body{padding: 0;font-family: '宋体';background: #fff;color: #222;height: 100%;}
</style>
</head>
<body>
<#include "web/wx/wsite/default/member/header.ftl" parse="true" encoding="UTF-8">
<div class="c-top"></div>
<div class="container clearfix">&nbsp;
<!-- 
<h1><a href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx2ff401f84ea0c711&redirect_uri=http://woshang88.com/weixin/web/wx/oauth/authorization/206579160028545024&response_type=code&scope=snsapi_userinfo&state=member#wechat_redirect">微信授权</a></h1>
<h1><a href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx2ff401f84ea0c711&redirect_uri=http://woshang88.com/weixin/web/wx/oauth/authorization/206579160028545024&response_type=code&scope=snsapi_base&state=member#wechat_redirect">默认授权</a></h1>
 -->
    <div class="page-content">
		<div id="img-content">
			<div class="media" id="media">
			  <#if (article.logoPath != null)!>
			   <img src="${base}${(article.logoPath)!}">
			  </#if> 
            </div>
            <div class="text">${(article.content)!}</div>
        </div>
    </div>
</div>
<#include "web/wx/wsite/default/member/bottom.ftl" parse="true" encoding="UTF-8">
</body>
</html>