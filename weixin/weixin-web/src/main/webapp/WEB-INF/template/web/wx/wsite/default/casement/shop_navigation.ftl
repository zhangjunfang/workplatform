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
<!--
.navigation{text-align:left;line-height:109px;
	font-size:130%;
	padding-right:0%;
	margin-left:0 auto; 
	margin-right:0 auto; 
	display:inline-block
	}
.navi{text-align:center;
	font-size:100%;
	}		
#spanOne{display:-moz-inline-box; display:inline-block; 
	filter alpha(opacity=50);
	-moz-opacity:1;
	-khtml-opacity:0.5;
	opacity:0.8;
	border-width:1px;
	border-style:solid; 
	border-bottom-color:#aaa;
	border-right-color:#aaa;
 	border-top-color:#ddd; 
	border-left-color:#ddd; 
	border-radius:3px; 
	-moz-border-radius:3px; 
	-webkit-border-radius:3px;
	text-align:center;width:110px;
	height:100px;line-height:100px;
	color:#000000;
	background-color:#CC0066;
  }
	#spanTwo{display:-moz-inline-box; display:inline-block; 
	filter alpha(opacity=50);
	-moz-opacity:1;
	-khtml-opacity:0.5;
	opacity:0.8;
	border-width:1px;
	border-style:solid; 
	border-bottom-color:#aaa;
	border-right-color:#aaa;
 	border-top-color:#ddd; 
	border-left-color:#ddd; 
	border-radius:3px; 
	-moz-border-radius:3px; 
	-webkit-border-radius:3px;
	text-align:center;width:110px;
	height:100px;line-height:100px;
	color:#00000E;
	background-color:#FFCC00;
  }
-->
</style>
</head>
<body>
<#include "web/wx/wsite/default/casement/header.ftl" parse="true" encoding="UTF-8">
<div class="c-top"></div>  
<div class="container clearfix">
     <div class="navi">
     <div class="navigation">
     <#list shopList as list>
	  	<a href="${base}/web/wx/casement/shop/${list.id}?redirectionUrl=${base}/web/wx/casement/234758759493140480#">
	 <#if (list_index)==0||list_index==3||list_index==4||list_index==7||list_index==8> <nobr><span id="spanOne">
	  	${list.name}
	  	</span></nobr>
	  	<#else>
	  	<nobr><span id="spanTwo">
	  	${list.name}
	  	</span></nobr>
	  	</#if>
	  	</a>
	  	<#if (list_index+1)%2==0></br></#if>
	 </#list>
	  </div>
	  </div>
</div> 
</center>
</body>
</html>