<#escape x as x?html>
<!-- 网站Header-->
<header id="header" class="header clearfix">
<div class="head_logo"><a href="#" class="logo" ></a></div>
<div class="head_title"><#if tencent??>${(tencent.nickname)!}，你好！</#if></div>
<!--<div class="head_nav"><a id="nav" href="javascript:;">···</a></div>-->
<!--<div id="head_nav" class="clearfix fr">
  <ul class="clearfix">
  &nbsp;&nbsp;&nbsp;-->
	<!--<li><a href="${base}/web/wx/sales/${(wid)!}" id="sales" class="<#if shopnav=="sales">nav_current</#if>"><span>即时促销</span></a></li>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-->
	<!--<li><a href="${base}/web/wx/sales/special/${(wid)!}" id="brandsales" class="<#if shopnav=="brandsales">nav_current</#if>"><span>特惠品牌</span></a></li>
	<!--<li><a href="${base}/web/wx/sales/brandsales" id="brandsales" class="<#if shopnav=="brandsales">nav_current</#if>"><span>特惠品牌</span></a></li>
	<!--<li><a href="${base}/web/wx/sales/bespeak" id="bespeak" class="<#if shopnav=="bespeak">nav_current</#if>"><span>订购预约</span></a></li>-->
 <!-- </ul>
</div> -->
</header>
</#escape>