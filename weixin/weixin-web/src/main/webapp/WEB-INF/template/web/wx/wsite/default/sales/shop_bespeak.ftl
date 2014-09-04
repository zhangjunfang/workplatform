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
<title> 促销活动广场 </title>
<style type="text/css">
.container {padding:0;border:none;}
ul.round {
border: 1px solid #C6C6C6;
background-color: rgba(255,255,255,0.9);
text-align: left;
font-size: 14px;
line-height: 24px;
border-radius: 5px;
-webkit-border-radius: 5px;
-moz-border-radius: 5px;
-moz-box-shadow: 0 1px 1px #f6f6f6;
-webkit-box-shadow: 0 1px 1px #f6f6f6;
box-shadow: 0 1px 1px #f6f6f6;
margin-bottom: 11px;
display: block;
}
ul.round li {
border: solid #C6C6C6;
border-width: 0 0 1px 0;
padding: 0 10px 0 10px;
}
.round li, .round li span, .round li a {
line-height: 22px;
}
.round li h2 {
color: #373B3E;
font-size: 16px;
font-weight: normal;
line-height: 20px;
padding: 10px 0 10px 0;
border-bottom: 1px dotted #C6C6C6;
}
.round li .text {
padding: 10px 0 10px;
}
.round li.addr {
background: url(${base}/static/wsite/skins/default/images/addr.png) no-repeat scroll 10px 13px transparent;
background-size: 15px 15px;
line-height: 22px;
padding-left: 34px;
}
.round li.tel {
background: url(${base}/static/wsite/skins/default/images/tel.png) no-repeat scroll 11px 13px transparent;
background-size: 15px 15px;
line-height: 22px;
padding-left: 34px;
}
.round li span {
display: block;
background: url(${base}/static/wsite/skins/default/images/arrow3.png) no-repeat right 50%;
-webkit-background-size: 8.5px 13px;
background-size: 8.5px 13px;
padding: 10px 20px 9px 0;
position: relative;
font-size: 14px;
min-height: 22px;
}
.submit[type="button"] {
width: 100%;
box-sizing: border-box;
-webkit-box-sizing: border-box;
-moz-box-sizing: border-box;
}
.submit {
background-color: #179F00;
padding: 10px 20px;
font-size: 16px;
text-decoration: none;
border: 1px solid #0B8E00;
background-image: linear-gradient(bottom,#179F00 0,#5DD300 100%);
background-image: -o-linear-gradient(bottom,#179F00 0,#5DD300 100%);
background-image: -moz-linear-gradient(bottom,#179F00 0,#5DD300 100%);
background-image: -webkit-linear-gradient(bottom,#179F00 0,#5DD300 100%);
background-image: -ms-linear-gradient(bottom,#179F00 0,#5DD300 100%);
background-image: -webkit-gradient(linear,left bottom,left top,color-stop(0,#179F00),color-stop(1,#5DD300));
-webkit-box-shadow: 0 1px 0 #94E700 inset,0 1px 2px rgba(0,0,0,0.5);
-moz-box-shadow: 0 1px 0 #94E700 inset,0 1px 2px rgba(0,0,0,0.5);
box-shadow: 0 1px 0 #94E700 inset,0 1px 2px rgba(0,0,0,0.5);
-webkit-border-radius: 5px;
-moz-border-radius: 5px;
-o-border-radius: 5px;
border-radius: 5px;
color: #fff;
display: block;
text-align: center;
text-shadow: 0 1px rgba(0,0,0,0.2);
}
</style>
</head>
<body>
<#include "web/wx/wsite/default/sales/header.ftl" parse="true" encoding="UTF-8">
<div class="c-top"></div>
<div class="container clearfix">
	<#list bespeakList as list>
		<ul class="round">
			<li>
				<h2>${list.name}</h2>
				<#if list.logoPath??>
					<h2><img src="${base}${list.logoPath}"/></h2>
				</#if>
				<div class="text">
					${list.description}
				</div>
			</li>
			<li>
				<span>有效期：${(list.startDate?string("yyyy-MM-dd"))!} 至 ${(list.endDate?string("yyyy-MM-dd"))!}</span>
			</li>
			<li class="addr">
				<a href="#"><span>${(list.address)!}</span></a>
			</li>
			<li class="tel">
				<a href="tel:0755-88308939">
					<span> 预订电话 ${(list.phone)!}</span>
				</a>
			</li>
			<li>
				<#if list.surplusStore?? && list.surplusStore gt 0>
				<input type="button" id="showBespeak" name="${list.id}" class="submit" value="立即预订">
				<#else>
				<input type="button" id="showFull" class="submit" value="预订已满">
				</#if>
			</li>
		</ul>
		
	</#list>
</div>
<#include "web/wx/wsite/default/sales/bottom.ftl" parse="true" encoding="UTF-8">
<script type="text/javascript">
	$("#showBespeak").click(function(){
		var bespeakId = $("#showBespeak").attr("name");
		window.location="${base}/web/wx/sales/bespeakOrder?bespeakId="+bespeakId;
	});
</script>
</body>
</html>