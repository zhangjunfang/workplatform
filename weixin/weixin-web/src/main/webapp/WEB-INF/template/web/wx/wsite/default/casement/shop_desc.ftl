<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-

scalable=0"> 
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<link href="${base}/static/wsite/css/layout.css" rel="stylesheet" type="text/css" />
<!--组件依赖css begin-->
<link rel="stylesheet" type="text/css" 

href="${base}/static/wsite/skins/default/css/widget/gotop/gotop.css" />
<link rel="stylesheet" type="text/css" 

href="${base}/static/wsite/skins/default/css/widget/dropmenu/dropmenu.css" />
<link rel="stylesheet" type="text/css" 

href="${base}/static/wsite/skins/default/css/widget/dropmenu/dropmenu.default.css" /><!--皮肤文件，若不

使用该皮肤，可以不加载-->
<!--组件依赖css end-->
<!--组件依赖js begin-->
<script type="text/javascript" src="${base}/static/wsite/skins/default/js/zepto.js"></script>
<script type="text/javascript" src="${base}/static/wsite/skins/default/js/extend/touch.js"></script>
<script type="text/javascript" src="${base}/static/wsite/skins/default/js/extend/offset.js"></script>
<script type="text/javascript" src="${base}/static/wsite/skins/default/js/extend/parseTpl.js"></script>
<script type="text/javascript" src="${base}/static/wsite/skins/default/js/core/gmu.js"></script>
<script type="text/javascript" src="${base}/static/wsite/skins/default/js/core/event.js"></script>
<script type="text/javascript" src="${base}/static/wsite/skins/default/js/core/widget.js"></script>
<script type="text/javascript" 

src="${base}/static/wsite/skins/default/js/extend/highlight.js"></script>
<script type="text/javascript" src="${base}/static/wsite/skins/default/js/extend/fix.js"></script>
<script type="text/javascript" src="${base}/static/wsite/skins/default/js/extend/throttle.js"></script>
<script type="text/javascript" 

src="${base}/static/wsite/skins/default/js/extend/event.scrollStop.js"></script>
<script type="text/javascript" 

src="${base}/static/wsite/skins/default/js/extend/event.ortchange.js"></script>
<script type="text/javascript" 

src="${base}/static/wsite/skins/default/js/extend/matchMedia.js"></script>
<script type="text/javascript" 

src="${base}/static/wsite/skins/default/js/widget/popover/popover.js"></script>
<script type="text/javascript" 

src="${base}/static/wsite/skins/default/js/widget/popover/arrow.js"></script>
<script type="text/javascript" 

src="${base}/static/wsite/skins/default/js/widget/popover/dismissible.js"></script>
<script type="text/javascript" 

src="${base}/static/wsite/skins/default/js/widget/dropmenu/dropmenu.js"></script>
<script type="text/javascript" 

src="${base}/static/wsite/skins/default/js/widget/dropmenu/placement.js"></script>
<script type="text/javascript" 

src="${base}/static/wsite/skins/default/js/widget/gotop/gotop.js"></script>
<!--组件依赖js end-->
<title>门店导航  </title>
<style type="text/css">
body{}
</style>
</head>
<body>
<#include "web/wx/wsite/default/casement/header.ftl" parse="true" encoding="UTF-8">
<div class="c-top"></div>
<div class="container clearfix">

	
	<#if CURRENT_SHOP??>
		<#if CURRENT_SHOP.logoPath!=""><img src="${base}${CURRENT_SHOP.logoPath}"/></#if>
		<h3>${(CURRENT_SHOP.name)!}</h3>
		<p>${(CURRENT_SHOP.description)!}</p>
		<#if CURRENT_SHOP.traffic!=""><p>交通指南：${CURRENT_SHOP.traffic}</p></#if>
		<#if CURRENT_SHOP.shophors!=""><p>营业时间：${CURRENT_SHOP.shophors}</p></#if>
		<#if CURRENT_SHOP.phone!=""><p>服务热线：${CURRENT_SHOP.phone}</p></#if>
		<#if CURRENT_SHOP.address!=""><p>地理位置：${CURRENT_SHOP.address}</p></#if>
		<#if CURRENT_SHOP.gps!=""><p><img src="${base}${CURRENT_SHOP.gps}"/></p></#if>
	<#else>
	  <p>&nbsp;&nbsp;&nbsp;&nbsp;
	           在市各级政府以及相关职能部门的支持下，喜盈门在驻马店得到迅猛发展，现已拥有连锁超市五

家：喜盈门购物广场、喜盈门服饰鞋业广场、喜盈门中心店、喜盈门南海路和文明路便利店。其中喜盈门购物中心是

驻马店喜盈门商业有限公司的旗舰店，也是目前驻马店市最大的综合性商场，营业面积18000平方米，拥有电动扶梯和

冷暖中央空调等先进设施，集购物、休闲、餐饮、娱乐为一体。优雅舒适的购物环境，功能完善的软硬件设施，品种

齐全兼顾高中低档的商品结构，喜盈门已成为驿城消费者休闲购物的好去处。
	  </p> 
	</#if>
</div>
</body>
</html>