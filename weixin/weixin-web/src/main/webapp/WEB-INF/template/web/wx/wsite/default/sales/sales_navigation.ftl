<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width"> 
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<!--<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"> -->
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
<script type="text/javascript">
function Preview(obj){
obj.on('click',function(e){
var urls = [];
imgs = obj;
for (var i = 0; i < imgs.length; i++) {
if(imgs[i].src!='') urls.push(imgs[i].src);
}

src = e.target.src;
        WeixinJSBridge.invoke('imagePreview',{
            'current':src,
            'urls':urls
       	});
});
}

$(function()
{
   var obj =$('.showpic img');
   Preview(obj);
});

</script>
<style type="text/css">
.showpic img{
    width: 100%;
}
#head_nav{position: absolute; left: 50%;top:50%; font-size: 14px;width:98%;height:24px;background:#D90000;line-height:24px;}/* ������ �ۻ�ɫ ��#FF966C*/
#navigation{text-align:left;relative:center;left:50%;right:100;top:80%; width:98%;height:24px; border:0px solid #F0FFF0;};
</style>
</head>
<body>
<#include "web/wx/wsite/default/sales/header.ftl" parse="true" encoding="UTF-8">
   
<div class="c-top"></div> 

<#list articleList as article>
<div class="container clearfix">
            <div class="media"> <div id="navigation">
            <a href="${base}/web/wx/casement/shop/${article.shopId}?redirectionUrl=${base}/web/wx/sales/234758759493140480"> ${(article.title)}</a></br>
            </div>
</div> </div>
</#list>

</body>
</html>