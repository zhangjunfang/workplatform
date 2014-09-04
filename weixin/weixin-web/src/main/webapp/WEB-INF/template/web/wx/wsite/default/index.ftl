<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<meta name="keywords" content="" />
		<meta name="description" content="" />
		<meta name="author" content="">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no,minimum-scale=1.0, maximum-scale=1.0">
		<title>微官网</title>
		<link rel="shortcut icon" href="${base}/static/wsite/skins/default/img/favicon.ico"/>
		<link rel="apple-touch-icon" href="${base}/static/wsite/skins/default/img/favicon.gif"/>
		<link rel="stylesheet" type="text/css" href="${base}/static/wsite/css/reset.css"/>
        
        <link rel="stylesheet" type="text/css" href="${base}/static/wsite/skins/default/css/widget/gotop/gotop.css"/>
        
   		<link rel="stylesheet" type="text/css" href="${base}/static/wsite/skins/default/css/widget/toolbar/toolbar.css" />
    	<link rel="stylesheet" type="text/css" href="${base}/static/wsite/skins/default/css/widget/toolbar/toolbar.default.css" />
    	<link rel="stylesheet" type="text/css" href="${base}/static/wsite/skins/default/css/widget/slider/slider.css" />
    	<link rel="stylesheet" type="text/css" href="${base}/static/wsite/skins/default/css/widget/slider/slider.default.css" />
    	
    	<link rel="stylesheet" type="text/css" href="${base}/static/wsite/skins/default/css/widget/dropmenu/dropmenu.css" />
		<link rel="stylesheet" type="text/css" href="${base}/static/wsite/skins/default/css/widget/dropmenu/dropmenu.default.css" />
 		<link rel="stylesheet" type="text/css" href="${base}/static/wsite/skins/default/css/widget/suggestion/suggestion_demo.css" />
		<link rel="stylesheet" type="text/css" href="${base}/static/wsite/skins/default/css/widget/suggestion/suggestion.css" />
		<link rel="stylesheet" type="text/css" href="${base}/static/wsite/skins/default/css/widget/suggestion/suggestion.default.css" /> 
    	<style type="text/css">
    	.fl{ float:left;}
		.fr{ float:right;}
    		#header{width:100%;display:block;height:42px;position:relative;color:white;background: #E95644;margin-bottom:1px;}
    		#header #main{width:42px;height:42px;background:url("${base}/static/wsite/skins/default/img/home.png") no-repeat;}
    		#header .title{font-size:14pt;display:block;padding-left:10px;font-weight:bolder;margin-right:67px;text-align:center;line-height:42px;text-overflow:ellipsis;white-space:nowrap;overflow:hidden;line-height:40px;}
    		#header .right{position:absolute;right:0;top:0;}
    		#header img{height:42px;width:66px;}
    		
    		#container {
				padding-left: 8px;
				padding-right: 8px;
				width: 100%;
				box-sizing: border-box;
				padding-bottom: 10px;
			}

			.menulist{
				border: 1px solid #cdcdcd;
				-webkit-box-shadow: #d4d4d4 0 1px 2px;
				-moz-box-shadow: #d4d4d4 0 1px 2px;
				box-shadow: #d4d4d4 0 1px 2px;
			}

    		.ui-slider-item {height:148px;}
    		.right {float: right;}
			.left {float: left;}
			.searchGo{padding-top:2px;line-height:30px;}
			.searchGo form .searchBtn1{
				width:52px;
				height:28px;
				background:url(${base}/static/wsite/skins/default/img/search_img.jpg) no-repeat;
				background-size:52px 28px;
				border-left:1px solid #c2c2c2;
			}
			.Active{border-bottom:1px dashed #acacac;width:98%;padding:10px;background:#FFFFFF;}
			.Active p{height:16px;padding-left:18px;background:url(${base}/static/wsite/skins/default/img/xiaoxi.jpg) no-repeat 0 2px;background-size:15px 14px;color:#4d4d4d;line-height:16px;}
			.Functional{width:100%;overflow:hidden;background:#FFFFFF;}
			.Functional a{float:left;width:24.0%;height:63px;padding-top:15px;border-left:1px solid #d2d2d2;border-bottom:1px solid #d2d2d2;font-size:14px;color:#4d4d4d;text-align:center;line-height:24px;}
			.Functional a.on{border-left:none;}
			.ItemCont{width:100%;background:#f7f7f7;}
			.shop_list{ background:#fff; box-shadow:0px 2px 3px rgba(0,0,0,.1); width:302px; margin:0 auto 10px;font-size:10px;}
			.shop_list a{ display:block;}
			.shop_list .pic{ position:relative;}
			.shop_list .pic,.shop_list .pic a,.shop_list .pic a img{display:block;width:302px;height:145px;}
			.shop_info{ height:33px; line-height:33px; padding:0 6px;}
			.s_brand_name{ width:190px; overflow:hidden; white-space:nowrap; word-break:keep-all; text-overflow: ellipsis; color:#242424; font-size:14px;}
			.discout span{ color:#F02387; font-size:14px; font-weight:bold;}
			.time_brand{ position:absolute; right:0; top:0; background:rgba(0,0,0,.5); color:#fff; padding:2px 4px;}
			.re_time,.ptb2{ padding:2px 0;}
			.re_time span{ float:left; display:block; width:11px; height:11px; background-position:-85px -95px;margin:2px 2px 0 0; }
	   		.shop_tit{margin:2px 0; background:#FF966C; width:100%; height:31px; line-height:31px;color:#FFFFFF;text-align:center; }
			.shop_tit h1{margin:0 auto;font-size:16px;color:#FFFFFF; padding:0 6px;}
	   	 	.footer { margin:0 8px;}
	    	.copright{ font-size:10px; text-align:center; padding:10px 0 0;}
	    </style>
    
        <script type="text/javascript" src="${base}/static/wsite/skins/default/js/zepto.js"></script>
        <script type="text/javascript" src="${base}/static/wsite/skins/default/js/core/gmu.js"></script>
        <script type="text/javascript" src="${base}/static/wsite/skins/default/js/core/event.js"></script>
        <script type="text/javascript" src="${base}/static/wsite/skins/default/js/core/widget.js"></script>
       
       	<script type="text/javascript" src="${base}/static/wsite/skins/default/js/extend/fix.js"></script>
        <script type="text/javascript" src="${base}/static/wsite/skins/default/js/extend/touch.js"></script>
    	<script type="text/javascript" src="${base}/static/wsite/skins/default/js/extend/iscroll.js"></script>
    	<script type="text/javascript" src="${base}/static/wsite/skins/default/js/extend/throttle.js"></script>
    	<script type="text/javascript" src="${base}/static/wsite/skins/default/js/extend/event.scrollStop.js"></script>
    	<script type="text/javascript" src="${base}/static/wsite/skins/default/js/extend/event.ortchange.js"></script>
    	<script type="text/javascript" src="${base}/static/wsite/skins/default/js/extend/parseTpl.js"></script>
    	<script type="text/javascript" src="${base}/static/wsite/skins/default/js/extend/highlight.js"></script>
     	<script type="text/javascript" src="${base}/static/wsite/skins/default/js/extend/matchMedia.js"></script>
     	<script type="text/javascript" src="${base}/static/wsite/skins/default/js/extend/offset.js"></script>
     
     	<script type="text/javascript" src="${base}/static/wsite/skins/default/js/widget/gotop/gotop.js"></script>
        <script type="text/javascript" src="${base}/static/wsite/skins/default/js/widget/toolbar/toolbar.js"></script>
    	
    	<script type="text/javascript" src="${base}/static/wsite/skins/default/js/widget/slider/slider.js"></script>
	    <script type="text/javascript" src="${base}/static/wsite/skins/default/js/widget/slider/arrow.js"></script>
	    <script type="text/javascript" src="${base}/static/wsite/skins/default/js/widget/slider/dots.js"></script>
	    <script type="text/javascript" src="${base}/static/wsite/skins/default/js/widget/slider/imgzoom.js"></script>
	    <script type="text/javascript" src="${base}/static/wsite/skins/default/js/widget/slider/$autoplay.js"></script>
	    <script type="text/javascript" src="${base}/static/wsite/skins/default/js/widget/slider/$touch.js"></script>
	    <script type="text/javascript" src="${base}/static/wsite/skins/default/js/widget/slider/$lazyloadimg.js"></script>
	    <script type="text/javascript" src="${base}/static/wsite/skins/default/js/widget/popover/popover.js"></script>
    	<script type="text/javascript" src="${base}/static/wsite/skins/default/js/widget/popover/arrow.js"></script>
    	<script type="text/javascript" src="${base}/static/wsite/skins/default/js/widget/popover/dismissible.js"></script>
	    <script type="text/javascript" src="${base}/static/wsite/skins/default/js/widget/dropmenu/dropmenu.js"></script>
		<script type="text/javascript" src="${base}/static/wsite/skins/default/js/widget/dropmenu/placement.js"></script>
		<script type="text/javascript" src="${base}/static/wsite/skins/default/js/widget/suggestion/suggestion.js"></script>
		<script type="text/javascript" src="${base}/static/wsite/skins/default/js/widget/suggestion/sendrequest.js"></script>
		<script type="text/javascript" src="${base}/static/wsite/skins/default/js/widget/suggestion/renderlist.js"></script>
	</head>
	<body>
		<div id="header">
			<a id="main" class="left" href="index"></a>
		    <span class="title">爱家大卖场</span>
		    <a id="menu" class="right"><img src="${base}/static/wsite/skins/default/img/list.png"></a>
		<div class="divider"></div>
		</div>
		
		<div id="slider">
		    <div>
		        <a href="#"><img src="${base}/static/wsite/skins/default/img/xp_1.jpg"></a>
		        <p>1,春装第一波，闪亮登场</p>
		    </div>
		    <div>
		        <a href="#"><img src="${base}/static/wsite/skins/default/img/xp_5.jpg"></a>
		        <p>2,2014劲惠体验 高端意式俊雅风度</p>
		    </div>
		    <div>
		        <a href="#"><img src="${base}/static/wsite/skins/default/img/xp_3.jpg"></a>
		        <p>3,冬日保暖，星级大比拼</p>
		    </div>
		    <div>
		        <a href="#"><img src="${base}/static/wsite/skins/default/img/xp_4.jpg"></a>
		        <p>4,雅鹿 品牌羽绒清仓 1折起 低至89元</p>
		    </div>
		</div>
		<div class="Active">
			<p><a href="lottery" style="font-size:14px;color: #E95644;text-decoration: none;">签到有礼，100%中奖等你来抢！</a></p>
		</div>
		<div class="Functional">
			<p><a class="on" href="sales"><img alt="" width="79" height="63" src="${base}/static/wsite/skins/default/img/t1_7.jpg"></a><a href="newdm"><img alt="" width="79" height="63" src="${base}/static/wsite/skins/default/img/t1_9.jpg"></a><a href="biggift"><img alt="" width="79" height="63" src="${base}/static/wsite/skins/default/img/t1_5.jpg"></a><a href="member"><img alt="" width="79" height="63" src="${base}/static/wsite/skins/default/img/t1_10.jpg"> </a><a class="on" href="function"><img alt="" width="79" height="63" src="${base}/static/wsite/skins/default/img/t1_8.jpg"> </a><a href="useful"><img alt="" width="79" height="63" src="${base}/static/wsite/skins/default/img/t1_11.jpg"> </a><a href="dandan"><img alt="" width="79" height="63" src="${base}/static/wsite/skins/default/img/t1_4.jpg"></a><a href="bespeak"><img alt="" width="79" height="63" src="${base}/static/wsite/skins/default/img/t1_2.jpg"> </a></p>
		</div>
		<div class="ItemCont">
			<div class="shop_tit clearfix"><h1>名品百货</h1></div>
			<div class="shop_list">
			    <div class="pic"> 
			       <a href="#" class="itemList"> 
			       		<img src="${base}/static/wsite/skins/default/img/mpbh_1.jpg" data-onerror="" data-brandid="136126" data-original="${base}/static/wsite/skins/default/img/mpbh_1.jpg" class="pro" style="display: block;"> 
			       </a> 
			    </div>
			    <div class="shop_info clearfix">
			      <p class="fl s_brand_name">CAROLINNA ESPINOSA女鞋专场</p>
			      <div class="fr discout"><span class="salebg2">3</span>折起</div>
			    </div>
			</div>
			<div class="shop_list">
			    <div class="pic"> 
			       <a href="#" class="itemList"> 
			       		<img src="${base}/static/wsite/skins/default/img/mpbh_2.jpg" data-onerror="" data-brandid="136429" data-original="${base}/static/wsite/skins/default/img/mpbh_2.jpg" class="pro" style="display: block;"> 
			       </a> 
			            </div>
			    <div class="shop_info clearfix">
			      <p class="fl s_brand_name">音儿YINER女装专场</p>
			      <div class="fr discout"><span class="salebg2">1.3</span>折起</div>
			    </div>
			 </div>
			 <div class="shop_list">
			    <div class="pic"> 
			       <a href="#" class="itemList"> 
			       		<img src="${base}/static/wsite/skins/default/img/mpbh_3.jpg" data-onerror="" data-brandid="137617" data-original="${base}/static/wsite/skins/default/img/mpbh_3.jpg" class="pro" style="display: block;"> 
			       </a> 
			    </div>
			    <div class="shop_info clearfix">
			      <p class="fl s_brand_name">D&amp;G手表专场</p>
			      <div class="fr discout"><span class="salebg2">2.5</span>折起</div>
			    </div>
			  </div>
			  <div class="shop_list">
			    <div class="pic"> 
			       <a href="#" class="itemList"> 
			       		<img src="${base}/static/wsite/skins/default/img/mpbh_4.jpg" data-onerror="" data-brandid="137958" data-original="${base}/static/wsite/skins/default/img/mpbh_4.jpg" class="pro" style="display: block;"> 
			       </a> 
			     </div>
			    <div class="shop_info clearfix">
			      <p class="fl s_brand_name">海贝I'HAPPY女装专场</p>
			      <div class="fr discout"><span class="salebg2">1.6</span>折起</div>
			    </div>
			  </div>
			  <div class="shop_list">
			    <div class="pic"> 
			       <a href="#" class="itemList"> 
			       		<img src="${base}/static/wsite/skins/default/img/mpbh_5.jpg" data-onerror="" data-brandid="137949" data-original="${base}/static/wsite/skins/default/img/mpbh_5.jpg" class="pro" style="display: block;"> 
			       </a> 
			    </div>
			    <div class="shop_info clearfix">
			      <p class="fl s_brand_name">玉兰油OLAY护肤品专场</p>
			      <div class="fr discout"><span class="salebg2">6.3</span>折起</div>
			    </div>
			  </div>
			<!-- item -->
		</div>
		<div id="container">
			<div class="menulist">
				
			</div>
		</div>
		<div id="footer"><p class="copright">CopyRight 2009-2014 微官网 </p></div>
		<div id="gotop"></div>
	</body>
	
	<script type="text/javascript">
	
		document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
			//WeixinJSBridge.call('hideOptionMenu');
			//WeixinJSBridge.call('hideToolbar');
		});
		
		$('#slider').slider({imgZoom:true,loop:true});
		
		$('#gotop').gotop();
		(function () {
        $('#input').suggestion({
            source: ""
        }).on('close', function () {

            // 点击关闭按钮后，sug不再显示
            $('#input').suggestion('destroy');
        });
    })();
	 var content = [
        '促销活动',
        '最新DM',
        '豪礼在线',
        '会员专享',
        {
            text: '常用信息',
            icon: 'grid'
        },
        '功能推荐', '在线调查', '小丹推荐'
    ];
	$('#menu').dropmenu({
        content: content,
        placement: 'bottom',
        align: 'right'
    });
		
	</script>
</html>