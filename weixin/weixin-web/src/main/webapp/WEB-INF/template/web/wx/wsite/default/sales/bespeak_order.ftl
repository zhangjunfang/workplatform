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
ul.round {border: 1px solid #C6C6C6;background-color: rgba(255,255,255,0.9);text-align: left;font-size: 14px;line-height: 24px;border-radius: 5px;-webkit-border-radius: 5px;-moz-border-radius: 5px;-moz-box-shadow: 0 1px 1px #f6f6f6;-webkit-box-shadow: 0 1px 1px #f6f6f6;box-shadow: 0 1px 1px #f6f6f6;margin-bottom: 11px;display: block;}
ul.round li {border: solid #C6C6C6;border-width: 0 0 1px 0;padding: 0 10px 0 10px;}
.round li, .round li span, .round li a {line-height: 22px;}
.round li h2 {color: #373B3E;font-size: 16px;font-weight: normal;line-height: 20px;padding: 10px 0 10px 0;border-bottom: 1px dotted #C6C6C6;}
.round li .text {padding: 10px 0 10px;}
.round li.addr {background: url(${base}/static/wsite/skins/default/images/addr.png) no-repeat scroll 10px 13px transparent;background-size: 15px 15px;line-height: 22px;padding-left: 34px;}
.round li.tel {background: url(${base}/static/wsite/skins/default/images/tel.png) no-repeat scroll 11px 13px transparent;background-size: 15px 15px;line-height: 22px;padding-left: 34px;}
.round li span {display: block;background: url(${base}/static/wsite/skins/default/images/arrow3.png) no-repeat right 50%;-webkit-background-size: 8.5px 13px;background-size: 8.5px 13px;padding: 10px 20px 9px 0;position: relative;font-size: 14px;min-height: 22px;}
.submit[type="button"] {width: 100%;box-sizing: border-box;-webkit-box-sizing: border-box;-moz-box-sizing: border-box;}
.submit {background-color: #179F00;padding: 10px 20px;font-size: 16px;text-decoration: none;border: 1px solid #0B8E00;background-image: linear-gradient(bottom,#179F00 0,#5DD300 100%);background-image: -o-linear-gradient(bottom,#179F00 0,#5DD300 100%);background-image: -moz-linear-gradient(bottom,#179F00 0,#5DD300 100%);background-image: -webkit-linear-gradient(bottom,#179F00 0,#5DD300 100%);background-image: -ms-linear-gradient(bottom,#179F00 0,#5DD300 100%);background-image: -webkit-gradient(linear,left bottom,left top,color-stop(0,#179F00),color-stop(1,#5DD300));-webkit-box-shadow: 0 1px 0 #94E700 inset,0 1px 2px rgba(0,0,0,0.5);
-moz-box-shadow: 0 1px 0 #94E700 inset,0 1px 2px rgba(0,0,0,0.5);box-shadow: 0 1px 0 #94E700 inset,0 1px 2px rgba(0,0,0,0.5);-webkit-border-radius: 5px;-moz-border-radius: 5px;-o-border-radius: 5px;border-radius: 5px;color: #fff;display: block;text-align: center;text-shadow: 0 1px rgba(0,0,0,0.2);}
.round li.nob .px {border-radius: 5px;-webkit-border-radius: 5px;-moz-border-radius: 5px;background-color: #FFF;
border: 1px solid #E8E8E8;margin: 5px 0 4px;padding: 5px 10px;}
.round li.nob {border-width: 0;}
table[Attributes Style] {width: 100%;border-top-width: 0px;border-right-width: 0px;border-bottom-width: 0px;border-left-width: 0px;border-spacing: 0px;}
.kuang th {color: #333;padding: 0;font-size: 16px;font-weight: normal;text-align: left;width: 79px;}
th {display: table-cell;vertical-align: inherit;}
.kuang td {color: #999;padding: 0;}
user agent stylesheettd, th {display: table-cell;vertical-align: inherit;}
.round li.nob .px {border-radius: 5px;-webkit-border-radius: 5px;-moz-border-radius: 5px;background-color: #FFF;border: 1px solid #E8E8E8;margin: 5px 0 4px;padding: 5px 10px;}
.px[type="text"] {width: 100%;box-sizing: border-box;-webkit-box-sizing: border-box;-moz-box-sizing: border-box;}
.px {position: relative;background-color: transparent;color: #999;display: block;width: 100%;padding: 10px;font-size: 16px;margin: 0 auto;font-family: Arial,Helvetica,sans-serif;border: 0;-webkit-appearance: none;}
.round li.title span {padding: 5px 15px 4px 0;font-size: 14px;color: #666;text-shadow: 0 1px #FFF;}
.round li span.none {background: none repeat scroll 0 0 transparent;}
.round li.title {
background-color: #E1E1E1;
background-image: linear-gradient(bottom,#E7E7E7 0,#f9f9f9 100%);
background-image: -o-linear-gradient(bottom,#E7E7E7 0,#f9f9f9 100%);
background-image: -moz-linear-gradient(bottom,#E7E7E7 0,#f9f9f9 100%);
background-image: -webkit-linear-gradient(bottom,#E7E7E7 0,#f9f9f9 100%);
background-image: -ms-linear-gradient(bottom,#E7E7E7 0,#f9f9f9 100%);
background-image: -webkit-gradient(linear,left bottom,left top,color-stop(0,#E7E7E7),color-stop(1,#f9f9f9));
-webkit-box-shadow: 0 1px 0 #FFF inset,0 1px 0 #EEE;
-moz-box-shadow: 0 1px 0 #FFF inset,0 1px 0 #EEE;
box-shadow: 0 1px 0 #FFF inset,0 1px 0 #EEE;
-webkit-border-radius: 5px 5px 0 0;
-moz-border-radius: 5px 5px 0 0;
-o-border-radius: 5px 5px 0 0;
border-radius: 5px 5px 0 0;
}
ul.round li {border: solid #C6C6C6;border-width: 0 0 1px 0;padding: 0 10px 0 10px;}
.round li.nob .pxtextarea {
border-radius: 5px;
-webkit-border-radius: 5px;
-moz-border-radius: 5px;
background-color: #FFF;
border: 1px solid #E8E8E8;
margin: 5px 0 5px;
padding: 5px 10px;
}
.pxtextarea {
position: relative;
background-color: transparent;
color: #999;
display: block;
width: 90%;
margin: 10px 0;
font-size: 14px;
padding: 0 10px;
font-family: Arial,Helvetica,sans-serif;
border: 0;
overflow: auto;
-webkit-appearance: none;
}
.round li.nob .dropdown-select {
border-radius: 5px;
-webkit-border-radius: 5px;
-moz-border-radius: 5px;
background-color: #FFF;
border: 1px solid #E8E8E8;
margin: 5px 0 4px;
padding: 5px 10px;
}
.dropdown-select {
-webkit-appearance: button;
-webkit-user-select: none;
font-size: 13px;
overflow: visible;
text-overflow: ellipsis;
white-space: nowrap;
color: #999;
display: inline;
position: relative;
margin: 0 1px 0 1px;
font-size: 16px;
width: 100%;
height: auto;
padding: 10px;
outline: none;
border: 0;
background-color: transparent;
}
</style>
</head>
<body>
<#include "web/wx/wsite/default/sales/header.ftl" parse="true" encoding="UTF-8">
<div class="c-top"></div>
<div class="container clearfix">
<input name="bespeakId" id="bespeakId" type="hidden" value="${(bespeak.id)!}" />
		<ul class="round">
			<li>
				<h2>${bespeak.name}</h2>
				<#if bespeak.logoPath??>
					<h2><img src="${base}${bespeak.logoPath}"/></h2>
				</#if>
				<div class="text">
					${bespeak.description}
				</div>
			</li>
			<li>
				<span>有效期：${(bespeak.startDate?string("yyyy-MM-dd"))!} 至 ${(bespeak.endDate?string("yyyy-MM-dd"))!}</span>
			</li>
			<li class="addr">
				<a href="#"><span>${(bespeak.address)!}</span></a>
			</li>
			<li class="tel">
				<a href="tel:0755-88308939">
					<span> 预订电话 ${bespeak.tel}</span>
				</a>
			</li>
		</ul>
		<!--粉丝填写过的信息的，直接就显示名字电话，粉丝没有填写过信息的话，这里就直接留空让粉丝填写-->
		<ul class="round">
			<li class="title mb"><span class="none">请认真填写表单</span></li>
			<li class="nob">
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="kuang">
					<tr>
						<th>联系人</th>
						<td><input name="orderName" type="text" class="px"
							id="orderName" value="" placeholder="请输入您的真实姓名"></td>
					</tr>
				</table>
			</li>
			<li class="nob">
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="kuang">
					<tr>
						<th>联系电话</th>
						<td><input name="tel" class="px" id="tel" value=""
							type="text" placeholder="请输入您的电话"></td>
					</tr>
				</table>
			</li>
			<li class="nob">
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="kuang">
					<tr>
						<th>预订日期</th>
						<td>
							<select name="orderDate" id="orderDate" class="dropdown-select">
								<#list orderDateList as date>
								<option value="${(date?string("yyyy-MM-dd"))}">${(date?string("yyyy-MM-dd"))}</option>
								</#list>
							</select>
						</td>
					</tr>
				</table>
			</li>
			<li class="nob">
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="kuang">
					<tr>
						<th>预订人数</th>
						<td><input name="number" class="px" id="number" value=""
							type="text" placeholder="请输入预订人数"></td>
					</tr>
				</table>
			</li>
			<li class="nob">
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="kuang">
					<tr>
						<th valign="top" class="thtop">备注</th>
						<td><textarea name="memo" class="pxtextarea"
								style="height: 99px; overflow-y: visible" id="memo"
								placeholder="请输入备注信息"></textarea></td>
					</tr>
				</table>
			</li>
			<li>
				<input type="button" id="showOrder" name="${bespeak.id}" class="submit" value="立即预订">
			</li>
		</ul>
</div>
<#include "web/wx/wsite/default/sales/bottom.ftl" parse="true" encoding="UTF-8">
<script type="text/javascript">
	$("#showOrder").click(function(){
		if($('#txt1').val()==''){alert('预订人数不能为空');return;}
		if($("#orderDate").val()==''){alert('请选择时间');return;}
		if($("#tel").val()==''){alert('电话不能为空');return;} 
		if($("#orderName").val()==''){alert('名字不能为空');return;} 
		var submitData = {
			token: "ozdP9jg3uiY2ZWp0EArc6VB24qiU",
			aid: 69333,
			bespeakId:$("#bespeakId").val(),
			name: $("#orderName").val(),
			memo: $("#memo").val(),
			orderDate: $("#orderDate").val(),
			number: $('#number').val(),
			tel: $("#tel").val(),
			action: "add"
		};
		$.post('operations', submitData,function(data) {
			if (data.success == true) {
				alert(data.msg || "预约成功");
				setTimeout("window.history.go(-1)",2000);
				return;
			} else {
				alert(data.msg || "预约失败");
			}
		},"json");
	});
</script>
</body>
</html>